package com.contac.controller;


import com.contac.dto.comentario.ComentarioRequestDTO;
import com.contac.dto.comentario.ComentarioResponseDTO;
import com.contac.service.comentario.IComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioController {

    private final IComentarioService comentarioService;

    public ComentarioController(IComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> nuevoComentario(@Valid @RequestBody ComentarioRequestDTO dto) {
        ComentarioResponseDTO response = comentarioService.nuevoComentario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/tema/{idTema}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentarios(@PathVariable Long idTema) {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarPorTema(idTema);
        return ResponseEntity.ok(comentarios);
    }
}