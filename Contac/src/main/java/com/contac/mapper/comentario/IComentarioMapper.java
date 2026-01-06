package com.contac.mapper.comentario;

import com.contac.dto.comentario.ComentarioRequestDTO;
import com.contac.dto.comentario.ComentarioResponseDTO;
import com.contac.entity.Comentario;

public interface IComentarioMapper {
    Comentario toEntity(ComentarioRequestDTO dto);
    ComentarioResponseDTO toResponseDTO(Comentario entity);
}