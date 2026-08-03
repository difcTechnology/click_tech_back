package com.clicktech.backend.service;

import com.clicktech.backend.dto.DetallePedidoRequest;
import com.clicktech.backend.dto.PedidoRequest;
import com.clicktech.backend.dto.PedidoResponse;
import com.clicktech.backend.dto.ProductoRequest;
import com.clicktech.backend.entity.*;
import com.clicktech.backend.enums.OPERACIONES_INVENTARIO;
import com.clicktech.backend.repository.PedidoRepository;
import com.clicktech.backend.repository.ProductoRepository;
import com.clicktech.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    private final UsuarioRepository usuarioRepository;

    private final ProductoService productoService;


    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoService = productoService;
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public PedidoResponse crear(PedidoRequest pedidoRequest) {

        Pedido pedido = pedidoRequest.toEntity();

        asignaUsuario(pedido);

        validaStock(pedido.getDetalles());

        //Calculo de subtotales
        for(DetallePedido detallePedido : pedido.getDetalles()) {
            calcularSubTotal(detallePedido);
        }

        validarTotalPedido(pedido);

        //Validar precio unitario del detalle con respecto al precio del producto
        for(DetallePedido detallePedido : pedido.getDetalles()) {

            validarPrecioUnitario(detallePedido);
        }

        //Actualiza el inventario para cada producto de cada detalle

        for(DetallePedido detallePedido : pedido.getDetalles()){

            actualizarInventario(detallePedido, OPERACIONES_INVENTARIO.DESCONTAR);
        }

        return PedidoResponse.fromEntity(pedidoRepository.save(pedido));
    }

    private void validarPrecioUnitario(DetallePedido detallePedido) {
        Producto producto = productoService.obtenerPorId(detallePedido.getProducto().getIdProducto());
        if(producto.getPrecio().compareTo(detallePedido.getPrecioUnitario()) != 0){
            throw new RuntimeException("El precio del producto no coincide con el precio unitario del detalle");
        }
    }

    private ProductoRequest crearProductoRequest(Producto producto){
        ProductoRequest request = new ProductoRequest();
        request.setNombre(producto.getNombre());
        request.setDescripcion(producto.getDescripcion());
        request.setPrecio(producto.getPrecio());
        request.setStock(producto.getStock());
        request.setImagen(producto.getImagen());
        request.setIdCategoria(producto.getCategoria().getIdCategorias());

        return request;
    }

    @Transactional
    public PedidoResponse actualizar(Integer id, PedidoRequest request) {

        Pedido pedido = obtenerPorId(id);

        if(request.getDireccion().equalsIgnoreCase(pedido.getDireccion())){
            pedido.setDireccion(request.getDireccion());
        }

        if(request.getMetodoPago().equalsIgnoreCase(pedido.getMetodoPago())){
            pedido.setMetodoPago(request.getMetodoPago());
        }

        modificarDetallePago(pedido.getDetalles(),request.getDetallePedidoRequestList());

        //Calculo de subtotales

        //Valido total del pedido

        //Validar precio unitario del detalle con respecto al precio del producto

        //Actualizo el inventario

        return PedidoResponse.fromEntity(pedido);
    }

    private void modificarDetallePago(List<DetallePedido> detalles, List<DetallePedidoRequest> detallePedidoRequestList) {
        for(DetallePedidoRequest detallePedidoRequest: detallePedidoRequestList){
            Optional<DetallePedido> detallePedidoOptional = detalles.stream().filter(detallePedido -> Objects.equals(detallePedido.getId(), detallePedidoRequest.getId())).findFirst();
            if(detallePedidoOptional.isPresent()) {
                DetallePedido detallePedidoEncontrado = detallePedidoOptional.get();
                if (detallePedidoRequest.getCantidad() > detallePedidoEncontrado.getCantidad()) {
                    detallePedidoEncontrado.setCantidad(detallePedidoRequest.getCantidad());
                }
                if (detallePedidoRequest.getPrecioUnitario() != detallePedidoEncontrado.getPrecioUnitario()) {
                    detallePedidoEncontrado.setPrecioUnitario(detallePedidoRequest.getPrecioUnitario());
                }
            }
        }

    }

    private void asignaUsuario(Pedido pedido){
        Integer idUsuario = pedido.getUsuario().getIdUsuario();

        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrada con el ID: " + idUsuario));
        pedido.setUsuario(usuario);
    }

    private void actualizarInventario(DetallePedido detallePedido, OPERACIONES_INVENTARIO operacion){
        Integer idProducto = detallePedido.getProducto().getIdProducto();
        Producto producto = productoService.obtenerPorId(idProducto);
        int cantidad = detallePedido.getCantidad();

        if(operacion == OPERACIONES_INVENTARIO.AGREGAR){
            producto.setStock(producto.getStock()+cantidad);
        }else if(operacion == OPERACIONES_INVENTARIO.DESCONTAR){
            producto.setStock(producto.getStock()-cantidad);
        }
        ProductoRequest request = crearProductoRequest(producto);
        Producto productoActualizado = productoService.actualizar(idProducto,request);
        detallePedido.setProducto(productoActualizado);
    }

    public Pedido obtenerPorId(Integer id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + id));
    }

    private void validaStock(List<DetallePedido> detallePedidoList){
        for(DetallePedido detallePedido : detallePedidoList) {

            //validar que exista el producto
            Integer idProducto = detallePedido.getProducto().getIdProducto();

            //validar el stock del producto
            if (!productoService.hayStockProducto(idProducto, detallePedido.getCantidad())) {
                throw new RuntimeException("No hay suficiente stock para crear el pedido");
            }
        }
    }

    private void validarTotalPedido(Pedido pedido){
        BigDecimal totalDetalles = BigDecimal.ZERO;

        for(DetallePedido detallePedido : pedido.getDetalles()){
            totalDetalles = totalDetalles.add(detallePedido.getSubtotal());
        }

        if(pedido.getTotal().compareTo(totalDetalles) != 0){
            throw new RuntimeException("El total del pedido no coincide con la suma de los subtotales de los detalles");
        }

    }

    private void calcularSubTotal(DetallePedido detallePedido){
        double subtotal = detallePedido.getCantidad()*detallePedido.getPrecioUnitario().doubleValue();
        detallePedido.setSubtotal(BigDecimal.valueOf(subtotal));
    }

}
