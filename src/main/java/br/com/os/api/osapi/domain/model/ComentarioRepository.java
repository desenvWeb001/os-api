package br.com.os.api.osapi.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

}
