package com.api.apiproyectohoteles.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;


import java.time.LocalDate;

@Entity
@Table(name="Reservas")
public class Reserva {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private double precioTotal;

    private int numHabitaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Hotel hotel;


    public Reserva(Long id, LocalDate fechaInicio, LocalDate fechaFin, double precioTotal, int numHabitaciones, Cliente cliente, Hotel hotel) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.precioTotal = precioTotal;
        this.numHabitaciones = numHabitaciones;
        this.cliente = cliente;
        this.hotel = hotel;
    }

    public Reserva() {

    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
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


    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
