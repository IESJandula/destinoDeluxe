package com.api.apiproyectohoteles.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;


@Entity
@Table(name="Hoteles")
public class Hotel {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String ubicacion;
    private Long estrellas;

    private int precioNoche;

    private String urlImagen;

    private int numeroDeHabitacionesTotales;


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(int precioNoche) {
        this.precioNoche = precioNoche;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(Long estrellas) {
        this.estrellas = estrellas;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Hotel(Long id, String nombre, String ubicacion, Long estrellas, int precioNoche, String urlImagen, int numeroDeHabitacionesTotales) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.estrellas = estrellas;
        this.precioNoche = precioNoche;
        this.urlImagen = urlImagen;
        this.numeroDeHabitacionesTotales = numeroDeHabitacionesTotales;
    }

    public Hotel() {
    }

    public int getNumeroDeHabitacionesTotales() {
        return numeroDeHabitacionesTotales;
    }

    public void setNumeroDeHabitacionesTotales(int numeroDeHabitacionesTotales) {
        this.numeroDeHabitacionesTotales = numeroDeHabitacionesTotales;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", estrellas=" + estrellas +
                '}';
    }
}
