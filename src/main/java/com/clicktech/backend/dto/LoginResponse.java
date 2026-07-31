package com.clicktech.backend.dto;

public class LoginResponse {

    private String token;
    private String nombre;
    private String email;
    private Integer rol;
    private Integer idUsuario;

    public LoginResponse() {}

    public LoginResponse(String token, String nombre, String email, Integer rol, Integer idUsuario) {
        this.token = token;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRol() {
        return rol;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
