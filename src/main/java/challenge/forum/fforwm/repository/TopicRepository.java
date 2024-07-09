package challenge.forum.fforwm.repository;

import challenge.forum.fforwm.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    Page<Topic> findAllByEstado(Topic.Estado estado, Pageable pagina);

    @Query("select t from Topic t where t.message.conteudo ilike %:assunto% or t.titulo ilike %:assunto%")
    Page<Topic> findAllByAssunto(String assunto, Pageable pagina);

    Page<Topic> findAllByDataCriacaoBetween(Date dataCriacao, Date dataCriacao2, Pageable pageable);
}
