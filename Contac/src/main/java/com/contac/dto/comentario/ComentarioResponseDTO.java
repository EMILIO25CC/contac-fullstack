package com.contac.dto.comentario;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioResponseDTO {

    private Long idComentario;
    private String comentario;
    private LocalDateTime fechaComentario;
    private Long idTema;
}