package com.clicktech.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ClickTechApplication {

    private final Environment env;

    public ClickTechApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClickTechApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String port = env.getProperty("server.port", "8080");
        System.out.println("  ClickTech Backend está corriendo");
        System.out.println("  URL:     http://localhost:" + port);
        System.out.println("  Auth:    http://localhost:" + port + "/api/auth");
        System.out.println("  Swagger: http://localhost:" + port + "/swagger-ui.html");
    }

}
