package com.generation.blogpessoal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.blogpessoal.model.Postagem;


@Repository //necessária por boas práticas e validação do robo
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
   public List<Postagem> findAllByTituloContainingIgnoreCase (String titulo);
   
}
