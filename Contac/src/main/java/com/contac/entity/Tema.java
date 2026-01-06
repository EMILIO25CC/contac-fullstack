package com.contac.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "temas")
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tema")
    private Long idTema;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contenido", nullable = false)
    private TipoContenido tipoContenido;

    @Column(name = "url_contenido", length = 500)
    private String urlContenido;

    @Lob
    @Column(name = "contenido_archivo", columnDefinition = "LONGBLOB")
    private byte[] contenidoArchivo;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Column(name = "tipo_archivo", length = 100)
    private String tipoArchivo;

    @Column(name = "tamanio_archivo")
    private Long tamanioArchivo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "tema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    public enum TipoContenido {
        IMAGEN, VIDEO
    }


}//End