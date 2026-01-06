package com.contac.dto.comentario;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComentarioRequestDTO {

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 5000, message = "El comentario no puede exceder 5000 caracteres")
    private String comentario;

    @NotNull(message = "El ID del tema es obligatorio")
    private Long idTema;
}