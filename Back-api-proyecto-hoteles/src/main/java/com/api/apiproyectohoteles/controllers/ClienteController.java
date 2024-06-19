package com.api.apiproyectohoteles.controllers;

import com.api.apiproyectohoteles.dto.CambioContrasenaDTO;
import com.api.apiproyectohoteles.entities.Cliente;
import com.api.apiproyectohoteles.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarCliente(@RequestBody Cliente cliente) {
        try {
            // Verificar que los campos no estén vacíos
            if (cliente.getNombre() == null || cliente.getNombre().isEmpty() ||
                    cliente.getEmail() == null || cliente.getEmail().isEmpty() ||
                    cliente.getContrasena() == null || cliente.getContrasena().isEmpty() ||
                    cliente.getNacionalidad() == null || cliente.getNacionalidad().isEmpty()) {
                return new ResponseEntity<>("Todos los campos son obligatorios", HttpStatus.BAD_REQUEST);
            }

            // Verificar si ya existe un cliente con el mismo correo electrónico
            Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
            if (clienteExistente != null) {
                return new ResponseEntity<>("Ya existe un cliente registrado con este correo electrónico", HttpStatus.BAD_REQUEST);
            }

            Cliente nuevoCliente = clienteRepository.save(cliente);
            return new ResponseEntity<>("Cliente registrado correctamente con ID: " + nuevoCliente.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al registrar el cliente: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<Map<String, Object>> iniciarSesion(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String contrasena = body.get("contrasena");
        // Buscar el cliente por su email
        Cliente cliente = clienteRepository.findByEmail(email);

        // Verificar si el cliente existe y si la contraseña es correcta
        if (cliente != null && cliente.getContrasena().equals(contrasena)) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Inicio de sesión exitoso para el cliente: " + cliente.getNombre());
            respuesta.put("idUsuario", cliente.getId()); // Agregar el ID del cliente al mapa
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } else {
            return new ResponseEntity("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestBody CambioContrasenaDTO cambioContrasenaDTO) {
        // Buscar el cliente por su nombre de usuario
        Cliente cliente = clienteRepository.findByEmail(cambioContrasenaDTO.getEmail());

        // Verificar si el cliente existe
        if (cliente == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }

        // Verificar si la contraseña actual coincide con la contraseña almacenada
        if (!cliente.getContrasena().equals(cambioContrasenaDTO.getContrasenaActual())) {
            return new ResponseEntity<>("La contraseña actual es incorrecta", HttpStatus.UNAUTHORIZED);
        }

        // Actualizar la contraseña del cliente con la nueva contraseña
        cliente.setContrasena(cambioContrasenaDTO.getNuevaContrasena());
        clienteRepository.save(cliente);

        return new ResponseEntity<>("Contraseña actualizada correctamente", HttpStatus.OK);
    }

    @PutMapping("/{id}/actualizar-datos")
    public ResponseEntity<String> actualizarDatosCliente(@PathVariable Long id, @RequestBody Cliente nuevoCliente) {
        // Buscar el cliente por su ID
        Cliente cliente = (Cliente) clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Actualizar los datos del cliente con los nuevos datos
        cliente.setNombre(nuevoCliente.getNombre());
        cliente.setEmail(nuevoCliente.getEmail());
        cliente.setNacionalidad(nuevoCliente.getNacionalidad());
        cliente.setEdad(nuevoCliente.getEdad());
        cliente.setIdioma(nuevoCliente.getIdioma());

        // Guardar los cambios en la base de datos
        clienteRepository.save(cliente);

        return new ResponseEntity<>("Datos del cliente actualizados correctamente", HttpStatus.OK);
    }
}
