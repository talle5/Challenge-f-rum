package challenge.forum.fforwm.controllers;

import challenge.forum.fforwm.domain.message.Message;
import challenge.forum.fforwm.domain.message.dto.MessageDto;
import challenge.forum.fforwm.services.MessageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService service;

    @GetMapping("/search/{str}")
    public PagedModel<MessageDto> searchMessage(@PathVariable String str, Pageable page) {
        return new PagedModel<>(service.searchMessage(str, page));
    }

    @PostMapping("/reply/{id}")
    public ResponseEntity<Message> replyMessage(@PathVariable Long id,
                                                     @RequestBody @Valid MessageDto message,
                                                     UriComponentsBuilder uriBuilder) {
        var newmessage = service.replyMessage(id, message);
        var uri = uriBuilder.path("/message/").buildAndExpand(newmessage.getId()).toUri();
        return ResponseEntity.created(uri).body(newmessage);
    }

    @PatchMapping("/editar/{id}")
    ResponseEntity<?> editMessage(@PathVariable Long id, @RequestBody @Valid MessageDto message) {
        service.editMessage(id, message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
