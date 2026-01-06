package com.contac.mapper.comentario;

import com.contac.dto.comentario.ComentarioRequestDTO;
import com.contac.dto.comentario.ComentarioResponseDTO;
import com.contac.entity.Comentario;
import org.springframework.stereotype.Component;

@Component
public class ComentarioMapperImpl implements IComentarioMapper {

    @Override
    public Comentario toEntity(ComentarioRequestDTO dto) {
        return Comentario.builder()
                .comentario(dto.getComentario())
                .build();
    }

    @Override
    public ComentarioResponseDTO toResponseDTO(Comentario entity) {
        return ComentarioResponseDTO.builder()
                .idComentario(entity.getIdComentario())
                .comentario(entity.getComentario())
                .fechaComentario(entity.getFechaComentario())
                .idTema(entity.getTema().getIdTema())
                .build();
    }
}