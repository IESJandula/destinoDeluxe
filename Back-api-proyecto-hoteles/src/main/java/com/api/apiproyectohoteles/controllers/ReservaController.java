package com.api.apiproyectohoteles.controllers;

import com.api.apiproyectohoteles.dto.CrearReserva;
import com.api.apiproyectohoteles.entities.Cliente;
import com.api.apiproyectohoteles.entities.Hotel;
import com.api.apiproyectohoteles.entities.Reserva;
import com.api.apiproyectohoteles.repositories.ClienteRepository;
import com.api.apiproyectohoteles.repositories.HotelRepository;
import com.api.apiproyectohoteles.repositories.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reservas")
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final HotelRepository hotelRepository;

    public ReservaController(ReservaRepository reservaRepository, ClienteRepository clienteRepository, HotelRepository hotelRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.hotelRepository = hotelRepository;
    }

    public boolean isHotelAvailableForRent(Hotel hotel, LocalDate startDate, LocalDate endDate) {
        // Lógica para verificar la disponibilidad del hotel
        List<Reserva> findReservasDisponibles = reservaRepository.findReservasDisponibles(hotel.getId(), startDate, endDate);
        return findReservasDisponibles.isEmpty();
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearReserva(@RequestBody CrearReserva reservaRequest) {
        // Obtener el cliente y el hotel por sus IDs
        Cliente cliente = clienteRepository.findById(reservaRequest.getIdUsuario()).orElse(null);
        Hotel hotel = hotelRepository.findById(reservaRequest.getIdHotel()).orElse(null);

        // Verificar si se encontraron el cliente y el hotel
        if (cliente == null || hotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar el cliente o el hotel.");
        }

        // Calcular el precio total de la reserva
        double precioPorDia = hotel.getPrecioNoche();
        double precioTotal = reservaRequest.calcularPrecioTotal(precioPorDia);

        // Crear una nueva instancia de Reserva
        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setCliente(cliente);
        nuevaReserva.setHotel(hotel);
        nuevaReserva.setPrecioTotal(precioTotal);
        nuevaReserva.setFechaInicio(reservaRequest.getFechaInicio());
        nuevaReserva.setFechaFin(reservaRequest.getFechaFin());

        // Guardar la reserva en la base de datos
        nuevaReserva = reservaRepository.save(nuevaReserva);

        // Retornar una respuesta exitosa
        return ResponseEntity.status(HttpStatus.CREATED).body("Reserva creada exitosamente con ID: " + nuevaReserva.getId());
    }


    @GetMapping("/cliente/reservas")
    public ResponseEntity<List<Reserva>> obtenerReservasPorCliente(@RequestParam String email) {
        // Suponiendo que tienes un método en tu repositorio para encontrar reservas por email
        Cliente cliente= clienteRepository.findByEmail(email);
        List<Reserva> reservas = reservaRepository.findByCliente(cliente);

        if (reservas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    /**@DeleteMapping("/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long id) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);

        if (reservaOptional.isPresent()) {
            reservaRepository.delete(reservaOptional.get());
            return new ResponseEntity<>("Reserva cancelada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reserva no encontrada", HttpStatus.NOT_FOUND);
        }
    }*/
}
