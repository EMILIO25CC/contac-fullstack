package com.contac.dto.tema;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemaRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El tipo de contenido es obligatorio")
    private String tipoContenido; // "IMAGEN" o "VIDEO"

    private String urlContenido;

    private MultipartFile archivo;
}