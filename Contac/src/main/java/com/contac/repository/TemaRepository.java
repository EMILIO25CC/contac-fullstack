package com.contac.repository;

import com.contac.entity.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
    List<Tema> findAllByOrderByFechaCreacionDesc();
    List<Tema> findByTituloContainingIgnoreCase(String titulo);
}