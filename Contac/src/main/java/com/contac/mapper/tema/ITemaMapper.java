package com.contac.mapper.tema;

import com.contac.dto.tema.TemaRequestDTO;
import com.contac.dto.tema.TemaResponseDTO;
import com.contac.entity.Tema;

public interface ITemaMapper {
    Tema toEntity(TemaRequestDTO dto);
    TemaResponseDTO toResponseDTO(Tema entity);
}