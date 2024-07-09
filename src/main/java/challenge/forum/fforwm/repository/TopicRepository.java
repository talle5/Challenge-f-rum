package challenge.forum.fforwm.repository;

import challenge.forum.fforwm.domain.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TopicRepository extends JpaRepository<Topic, Long>, JpaSpecificationExecutor<Topic> {

    Page<Topic> findAllByActive(Boolean state, Pageable pagina);

    @Query("select t from Topic t where t.message.content ilike %:assunto% or t.title ilike %:assunto%")
    Page<Topic> findAllByAssunto(String assunto, Pageable pagina);
}
