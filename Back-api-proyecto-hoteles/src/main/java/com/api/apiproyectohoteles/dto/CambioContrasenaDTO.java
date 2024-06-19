package com.api.apiproyectohoteles.dto;

public class CambioContrasenaDTO {
    private String email;
    private String contrasenaActual;
    private String nuevaContrasena;

    public CambioContrasenaDTO(String email, String contrasenaActual, String nuevaContrasena) {
        this.email = email;
        this.contrasenaActual = contrasenaActual;
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenaActual() {
        return contrasenaActual;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }
}
