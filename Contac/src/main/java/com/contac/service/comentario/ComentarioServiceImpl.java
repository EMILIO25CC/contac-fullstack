package com.contac.service.comentario;

import com.contac.dto.comentario.ComentarioRequestDTO;
import com.contac.dto.comentario.ComentarioResponseDTO;
import com.contac.entity.Comentario;
import com.contac.entity.Tema;
import com.contac.exception.ResourceNotFoundException;
import com.contac.mapper.comentario.IComentarioMapper;
import com.contac.repository.ComentarioRepository;
import com.contac.repository.TemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements IComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TemaRepository temaRepository;
    private final IComentarioMapper comentarioMapper;

    public ComentarioServiceImpl(ComentarioRepository comentarioRepository,
                                 TemaRepository temaRepository,
                                 IComentarioMapper comentarioMapper) {
        this.comentarioRepository = comentarioRepository;
        this.temaRepository = temaRepository;
        this.comentarioMapper = comentarioMapper;
    }

    @Override
    @Transactional
    public ComentarioResponseDTO nuevoComentario(ComentarioRequestDTO dto) {
        Tema tema = temaRepository.findById(dto.getIdTema())
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));

        Comentario comentario = comentarioMapper.toEntity(dto);
        comentario.setTema(tema);

        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        return comentarioMapper.toResponseDTO(comentarioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponseDTO> listarPorTema(Long idTema) {
        return comentarioRepository.findByTema_IdTemaOrderByFechaComentarioDesc(idTema).stream()
                .map(comentarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}