package challenge.forum.fforwm.controllers;

import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.domain.topic.dto.EditTopic;
import challenge.forum.fforwm.domain.topic.dto.TopicDto;
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

    private final TopicService topicService;

    @GetMapping("/list")
    public ResponseEntity<PagedModel<TopicDto>> listAllTopic(Pageable pagina, @RequestParam(defaultValue = "false") Boolean all) {
        var page = new PagedModel<>(topicService.listAll(pagina, all).map(TopicDto::new));
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) {
        var topic = new TopicDto(topicService.getById(id));
        return ResponseEntity.ok(topic);
    }

    @GetMapping("/search/{assunto}")
    public ResponseEntity<PagedModel<TopicDto>> searchTopic(Pageable pagina, @PathVariable String assunto) {
        var page = new PagedModel<>(topicService.search(pagina, assunto).map(TopicDto::new));
        return ResponseEntity.ok(page);
    }

    @PostMapping("/create")
    public ResponseEntity<TopicDto> createTopic(@RequestBody @Valid TopicDto var1, UriComponentsBuilder uriBuilder) {
        var topic = topicService.createTopic(var1.title(), var1.message());
        var uri = uriBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDto(topic));
    }

    @PostMapping("/close/{id}")
    public ResponseEntity<?> closeTopic(@PathVariable Long id) {
        topicService.closeTopic(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply/{id}")
    public ResponseEntity<MessageDto> replyTopic(@PathVariable Long id, @RequestParam(name = "message", required = false) Long messageId, @RequestBody @Valid MessageDto response, UriComponentsBuilder uriBuilder) {
        var message = (messageId == null) ? topicService.reply(id, response.content()) : topicService.replyMessage(id, messageId, response.content());
        var uri = uriBuilder.path("/content/").buildAndExpand(message.getId()).toUri();
        return ResponseEntity.created(uri).body(new MessageDto(message));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editTopic(@PathVariable Long id, @RequestBody @Valid EditTopic new_info) {
        topicService.editTopic(id, new_info.title(), new_info.message());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
