package com.api.apiproyectohoteles.repositories;


import com.api.apiproyectohoteles.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByUbicacion(String ubicacion);
    Optional<Hotel> findById(Long id);

    List<Hotel> findByNombre(String nombre);


    List<Hotel> findByEstrellas(Long estrellas);
}
