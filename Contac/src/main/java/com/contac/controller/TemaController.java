package com.contac.controller;
import com.contac.dto.tema.TemaRequestDTO;
import com.contac.dto.tema.TemaResponseDTO;
import com.contac.service.tema.ITemaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/temas")
@CrossOrigin(origins = "*")
public class TemaController {

    private final ITemaService temaService;

    public TemaController(ITemaService temaService) {
        this.temaService = temaService;
    }

    // Crear tema con URL (JSON)
    @PostMapping("/url")
    public ResponseEntity<TemaResponseDTO> crearTemaConUrl(
            @Valid @RequestBody TemaRequestDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        TemaResponseDTO response = temaService.crearTema(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Crear tema con archivo (form-data)
    @PostMapping(value = "/archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemaResponseDTO> crearTemaConArchivo(
            @Valid @ModelAttribute TemaRequestDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        TemaResponseDTO response = temaService.crearTema(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Actualizar tema con URL (JSON)
    @PutMapping("/url/{id}")
    public ResponseEntity<TemaResponseDTO> actualizarTemaConUrl(
            @PathVariable Long id,
            @Valid @RequestBody TemaRequestDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        TemaResponseDTO response = temaService.actualizarTema(id, dto, username);
        return ResponseEntity.ok(response);
    }

    // Actualizar tema con archivo (form-data)
    @PutMapping(value = "/archivo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TemaResponseDTO> actualizarTemaConArchivo(
            @PathVariable Long id,
            @Valid @ModelAttribute TemaRequestDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        TemaResponseDTO response = temaService.actualizarTema(id, dto, username);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/listar")
    public ResponseEntity<Map<String, Object>> listarTemas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<TemaResponseDTO> temas = temaService.listarPaginado(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", temas.getContent());
        response.put("currentPage", temas.getNumber());
        response.put("totalItems", temas.getTotalElements());
        response.put("totalPages", temas.getTotalPages());

        return ResponseEntity.ok(response);
    }





    /*
    @GetMapping("/listar")
    public ResponseEntity<List<TemaResponseDTO>> listarTemas() {
        List<TemaResponseDTO> temas = temaService.listarTodos();
        return ResponseEntity.ok(temas);
    }*/

    @GetMapping("/ver/{id}")
    public ResponseEntity<TemaResponseDTO> verTema(@PathVariable Long id) {
        TemaResponseDTO tema = temaService.buscarTema(id);
        return ResponseEntity.ok(tema);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<TemaResponseDTO>> buscarTemas(@RequestParam String titulo) {
        List<TemaResponseDTO> temas = temaService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(temas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTema(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        temaService.eliminarTema(id, username);
        return ResponseEntity.ok().body("Tema eliminado exitosamente");
    }

    /// ================================


    @GetMapping("/archivo/{id}")
    public ResponseEntity<byte[]> obtenerArchivo(@PathVariable Long id) {
        TemaResponseDTO tema = temaService.buscarTema(id);
        byte[] archivo = temaService.obtenerArchivo(id);

        if (archivo == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(tema.getTipoArchivo()));

        // Limpiar el nombre del archivo para evitar caracteres inválidos
        String nombreArchivo = limpiarNombreArchivo(tema.getNombreArchivo());
        headers.setContentDispositionFormData("attachment", nombreArchivo);

        return ResponseEntity.ok()
                .headers(headers)
                .body(archivo);
    }

    // Metodo auxiliar para limpiar nombres de archivo
    private String limpiarNombreArchivo(String nombreArchivo) {
        if (nombreArchivo == null) {
            return "archivo";
        }

        // Eliminar caracteres no ASCII y reemplazar espacios
        String limpio = nombreArchivo
                .replaceAll("[^\\x00-\\x7F]", "") // Eliminar caracteres no ASCII
                .replaceAll("\\s+", "_")           // Reemplazar espacios con guiones bajos
                .replaceAll("[^a-zA-Z0-9._-]", ""); // Solo permitir caracteres seguros

        // Si después de limpiar está vacío, usar nombre genérico
        return limpio.isEmpty() ? "archivo" : limpio;
    }


    ///=====================================



}//FIN