package com.api.apiproyectohoteles.repositories;

import com.api.apiproyectohoteles.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByEmail(String email);

    Optional<Cliente> findById(Long id);

    Cliente findByNombre(String nombre);
}
