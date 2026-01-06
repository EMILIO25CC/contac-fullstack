package com.contac.dto.tema;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.contac.dto.comentario.ComentarioResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemaResponseDTO {

    private Long idTema;
    private String titulo;
    private String descripcion;
    private String tipoContenido;
    private String urlContenido;
    private String nombreArchivo;
    private String tipoArchivo;
    private Long tamanioArchivo;
    private LocalDateTime fechaCreacion;
    private String usuarioCreador;
    private List<ComentarioResponseDTO> comentarios;
    private Integer totalComentarios;
}