package challenge.forum.fforwm.controllers;

import challenge.forum.fforwm.domain.Arg;
import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.domain.topic.Topic;
import challenge.forum.fforwm.domain.topic.dto.*;
import challenge.forum.fforwm.services.TopicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topic")
@AllArgsConstructor
public class TopicController {

    private final TopicService service;

    private final UriComponentsBuilder uriBuilder;

    @GetMapping("/list")
    public ResponseEntity<PagedModel<TopicDto>> topic(Pageable pagina, @RequestParam(name = "all", defaultValue = "false") Boolean all) {
        var page = new PagedModel<>(service.allTopic(pagina, all));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> topicById(@PathVariable Long id) {
        var topic = service.topicById(id);
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/search/{assunto}")
    public ResponseEntity<PagedModel<Topic>> topic(Pageable pagina, @PathVariable String assunto) {
        var page = new PagedModel<>(service.allTopic(pagina, assunto));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    public PagedModel<Topic> search(Pageable pagina, Arg arg) {
        return new PagedModel<>(service.searchBetweenData(arg.start(), arg.end(), pagina));
    }

    @PostMapping("/create")
    public ResponseEntity<TopicDto> createTopic(@RequestBody @Valid TopicDto var1) {
        var topic = service.createTopic(var1.titulo(), var1.message());
        var uri = uriBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<?> closeTopic(@PathVariable Long id) {
        service.closeTopic(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply/{id}")
    public ResponseEntity<MessageDto> reply(@PathVariable Long id, @RequestBody MessageDto resposta) {
        var message = service.reply(id, resposta.message());
        var uri = uriBuilder.path("/message/").buildAndExpand(message.getId()).toUri();
        return ResponseEntity.created(uri).body(new MessageDto(message));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editarTopic(@PathVariable Long id, @RequestBody @Valid EditTopic new_info) {
        service.editTopic(id, new_info);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        service.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
