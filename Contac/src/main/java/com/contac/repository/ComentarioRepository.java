package com.contac.repository;

import com.contac.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByTema_IdTemaOrderByFechaComentarioDesc(Long idTema);
    Long countByTema_IdTema(Long idTema);
}