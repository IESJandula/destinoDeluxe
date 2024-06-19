package com.api.apiproyectohoteles.controllers;

import com.api.apiproyectohoteles.entities.Hotel;
import com.api.apiproyectohoteles.entities.Reserva;
import com.api.apiproyectohoteles.repositories.HotelRepository;
import com.api.apiproyectohoteles.repositories.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/hoteles")
public class HotelController {
    private final HotelRepository hotelRepository;

    private final ReservaRepository reservaRepository;

    public HotelController(HotelRepository hotelRepository, ReservaRepository reservaRepository) {
        this.hotelRepository = hotelRepository;
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Hotel>> listarHoteles() {
        List<Hotel> hoteles = hotelRepository.findAll();
        if (hoteles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hoteles, HttpStatus.OK);
    }
    public List<Hotel> getAvailableHotelesForReserva(LocalDate startDate, LocalDate endDate) {
        List<Hotel> allHoteles = hotelRepository.findAll();
        List<Hotel> availableHoteles = new ArrayList<>();

        for (Hotel hotel : allHoteles) {
            // Lógica para verificar la disponibilidad del hotel
            List<Reserva> overlappingRentals = reservaRepository.findReservasDisponibles(hotel.getId(), startDate, endDate);

            if (overlappingRentals.isEmpty()) {
                // El hotel está disponible, agrégalo a la lista de hoteles disponibles
                availableHoteles.add(hotel);
            }
        }

        return availableHoteles;
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Hotel>> getAvailableHoteles(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        try {
            List<Hotel> availableHoteles = getAvailableHotelesForReserva(startDate, endDate);

            if (availableHoteles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No hay hoteles disponibles
            }

            return ResponseEntity.ok(availableHoteles);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerHotelPorId(@PathVariable Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);

        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Hotel no encontrado", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/buscar-por-ubicacion")
    public ResponseEntity<List<Hotel>> buscarHotelesPorUbicacion(@RequestParam String ubicacion) {
        List<Hotel> hoteles = hotelRepository.findByUbicacion(ubicacion);

        if (hoteles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hoteles, HttpStatus.OK);
    }

    @GetMapping("/buscar-por-nombre")
    public ResponseEntity<List<Hotel>> buscarHotelesPorNombre(@RequestParam String nombre) {
        List<Hotel> hoteles = hotelRepository.findByNombre(nombre);

        if (hoteles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hoteles, HttpStatus.OK);
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearHotel(@RequestBody Hotel hotel) {
        try {
            // Verificar que los campos obligatorios no estén vacíos
            if (hotel.getNombre() == null || hotel.getNombre().isEmpty() ||
                    hotel.getUbicacion() == null || hotel.getUbicacion().isEmpty() ||
                    hotel.getUrlImagen() == null || hotel.getUrlImagen().isEmpty() ||
                    hotel.getEstrellas() == null ||
                    hotel.getPrecioNoche() <= 0 ||  // Verifica que el precio por noche sea positivo
                    hotel.getNumeroDeHabitacionesTotales() <= 0) {  // Verifica que el número de habitaciones sea positivo
                return ResponseEntity.badRequest().body("Todos los campos son obligatorios y deben estar completos");
            }

            // Guardar el nuevo hotel en la base de datos
            Hotel nuevoHotel = hotelRepository.save(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHotel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el hotel: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-por-estrellas")
    public ResponseEntity<List<Hotel>> buscarHotelesPorEstrellas(@RequestParam Long estrellas) {
        List<Hotel> hoteles = hotelRepository.findByEstrellas(estrellas);

        if (hoteles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(hoteles, HttpStatus.OK);
    }


}
