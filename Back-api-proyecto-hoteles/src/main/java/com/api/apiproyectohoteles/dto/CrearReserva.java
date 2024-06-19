package com.api.apiproyectohoteles.dto;

import java.time.LocalDate;

public class CrearReserva {
    public Integer idUsuario;
    public Integer idHotel;
    public LocalDate fechaInicio;
    public LocalDate fechaFin;

    public CrearReserva(Integer idUsuario, Integer idHotel, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idUsuario = idUsuario;
        this.idHotel = idHotel;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double calcularPrecioTotal(double precioPorDia) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return dias * precioPorDia;
    }
}
