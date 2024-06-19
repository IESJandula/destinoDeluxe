package com.api.apiproyectohoteles.repositories;

import com.api.apiproyectohoteles.entities.Cliente;
import com.api.apiproyectohoteles.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByClienteId(Long clienteId);

    Optional<Reserva> findById(Long id);

    @Query("SELECT r FROM Reserva r WHERE r.hotel.id = :hotelId AND ((r.fechaInicio BETWEEN :startDate AND :endDate) OR (r.fechaFin BETWEEN :startDate AND :endDate))")
    List<Reserva> findReservasDisponibles(Long hotelId, LocalDate startDate, LocalDate endDate);

    List<Reserva> findByCliente(Cliente cliente);
}
