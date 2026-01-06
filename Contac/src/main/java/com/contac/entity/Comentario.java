package com.contac.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha_comentario", nullable = false, updatable = false)
    private LocalDateTime fechaComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tema", nullable = false)
    private Tema tema;

    @PrePersist
    protected void onCreate() {
        fechaComentario = LocalDateTime.now();
    }
}//End