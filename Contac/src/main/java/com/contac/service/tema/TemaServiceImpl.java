package com.contac.service.tema;

import com.contac.dto.tema.TemaRequestDTO;
import com.contac.dto.tema.TemaResponseDTO;
import com.contac.entity.Tema;
import com.contac.entity.Usuario;
import com.contac.exception.ResourceNotFoundException;
import com.contac.mapper.tema.ITemaMapper;
import com.contac.repository.TemaRepository;
import com.contac.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemaServiceImpl implements ITemaService {

    private final TemaRepository temaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ITemaMapper temaMapper;

    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024; // 30MB

    public TemaServiceImpl(TemaRepository temaRepository,
                           UsuarioRepository usuarioRepository,
                           ITemaMapper temaMapper) {
        this.temaRepository = temaRepository;
        this.usuarioRepository = usuarioRepository;
        this.temaMapper = temaMapper;
    }

    @Override
    @Transactional
    public TemaResponseDTO crearTema(TemaRequestDTO dto, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Tema tema = temaMapper.toEntity(dto);
        tema.setUsuario(usuario);

        procesarContenido(tema, dto);

        Tema temaGuardado = temaRepository.save(tema);
        return temaMapper.toResponseDTO(temaGuardado);
    }

    @Override
    @Transactional
    public TemaResponseDTO actualizarTema(Long id, TemaRequestDTO dto, String username) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));

        if (!tema.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tiene permisos para actualizar este tema");
        }

        tema.setTitulo(dto.getTitulo());
        tema.setDescripcion(dto.getDescripcion());
        tema.setTipoContenido(Tema.TipoContenido.valueOf(dto.getTipoContenido().toUpperCase()));

        procesarContenido(tema, dto);

        Tema temaActualizado = temaRepository.save(tema);
        return temaMapper.toResponseDTO(temaActualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public TemaResponseDTO buscarTema(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));
        return temaMapper.toResponseDTO(tema);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemaResponseDTO> listarTodos() {
        return temaRepository.findAllByOrderByFechaCreacionDesc().stream()
                .map(temaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemaResponseDTO> buscarPorTitulo(String titulo) {
        return temaRepository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(temaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminarTema(Long id, String username) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));

        if (!tema.getUsuario().getUsername().equals(username)) {
            throw new RuntimeException("No tiene permisos para eliminar este tema");
        }

        temaRepository.delete(tema);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] obtenerArchivo(Long id) {
        Tema tema = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));
        return tema.getContenidoArchivo();
    }

    private void procesarContenido(Tema tema, TemaRequestDTO dto) {
        MultipartFile archivo = dto.getArchivo();

        if (archivo != null && !archivo.isEmpty()) {
            if (archivo.getSize() > MAX_FILE_SIZE) {
                throw new RuntimeException("El archivo excede el tamaño máximo de 30MB");
            }

            try {
                tema.setContenidoArchivo(archivo.getBytes());
                tema.setNombreArchivo(archivo.getOriginalFilename());
                tema.setTipoArchivo(archivo.getContentType());
                tema.setTamanioArchivo(archivo.getSize());
                tema.setUrlContenido(null);
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar el archivo", e);
            }
        } else if (dto.getUrlContenido() != null && !dto.getUrlContenido().isEmpty()) {
            tema.setUrlContenido(dto.getUrlContenido());
            tema.setContenidoArchivo(null);
            tema.setNombreArchivo(null);
            tema.setTipoArchivo(null);
            tema.setTamanioArchivo(null);
        } else {
            throw new RuntimeException("Debe proporcionar un archivo o una URL de contenido");
        }
    }

    //Aplicamos una paginacion
    @Override
    @Transactional(readOnly = true)
    public Page<TemaResponseDTO> listarPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreacion").descending());
        Page<Tema> temas = temaRepository.findAll(pageable);
        return temas.map(temaMapper::toResponseDTO);
    }





}//FIN