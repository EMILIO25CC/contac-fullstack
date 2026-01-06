package com.contac.mapper.tema;

import com.contac.dto.tema.TemaRequestDTO;
import com.contac.dto.tema.TemaResponseDTO;
import com.contac.entity.Tema;
import com.contac.mapper.comentario.IComentarioMapper;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class TemaMapperImpl implements ITemaMapper {

    private final IComentarioMapper comentarioMapper;

    public TemaMapperImpl(IComentarioMapper comentarioMapper) {
        this.comentarioMapper = comentarioMapper;
    }

    @Override
    public Tema toEntity(TemaRequestDTO dto) {
        return Tema.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .tipoContenido(Tema.TipoContenido.valueOf(dto.getTipoContenido().toUpperCase()))
                .urlContenido(dto.getUrlContenido())
                .build();
    }

    @Override
    public TemaResponseDTO toResponseDTO(Tema entity) {
        return TemaResponseDTO.builder()
                .idTema(entity.getIdTema())
                .titulo(entity.getTitulo())
                .descripcion(entity.getDescripcion())
                .tipoContenido(entity.getTipoContenido().name())
                .urlContenido(entity.getUrlContenido())
                .nombreArchivo(entity.getNombreArchivo())
                .tipoArchivo(entity.getTipoArchivo())
                .tamanioArchivo(entity.getTamanioArchivo())
                .fechaCreacion(entity.getFechaCreacion())
                .usuarioCreador(entity.getUsuario().getUsername())
                .comentarios(entity.getComentarios() != null ?
                        entity.getComentarios().stream()
                                .map(comentarioMapper::toResponseDTO)
                                .collect(Collectors.toList()) : null)
                .totalComentarios(entity.getComentarios() != null ?
                        entity.getComentarios().size() : 0)
                .build();
    }
}