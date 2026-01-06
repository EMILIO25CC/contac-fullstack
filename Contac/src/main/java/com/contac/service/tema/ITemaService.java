package com.contac.service.tema;

import com.contac.dto.tema.TemaRequestDTO;
import com.contac.dto.tema.TemaResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITemaService {
    TemaResponseDTO crearTema(TemaRequestDTO dto, String username);
    TemaResponseDTO actualizarTema(Long id, TemaRequestDTO dto, String username);
    TemaResponseDTO buscarTema(Long id);
    List<TemaResponseDTO> listarTodos();
    List<TemaResponseDTO> buscarPorTitulo(String titulo);
    void eliminarTema(Long id, String username);
    byte[] obtenerArchivo(Long id);
    //Para la paguinacion
    Page<TemaResponseDTO> listarPaginado(int page, int size);


}