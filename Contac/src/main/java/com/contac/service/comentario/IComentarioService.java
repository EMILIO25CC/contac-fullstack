package com.contac.service.comentario;

import com.contac.dto.comentario.ComentarioRequestDTO;
import com.contac.dto.comentario.ComentarioResponseDTO;
import java.util.List;

public interface IComentarioService {
    ComentarioResponseDTO nuevoComentario(ComentarioRequestDTO dto);
    List<ComentarioResponseDTO> listarPorTema(Long idTema);
}