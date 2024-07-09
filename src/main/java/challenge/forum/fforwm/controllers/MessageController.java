package challenge.forum.fforwm.controllers;

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

    private final MessageService messageService;

    @GetMapping("/search/{str}")
    public PagedModel<MessageDto> searchMessage(@PathVariable String str, Pageable page) {
        return new PagedModel<>(messageService.searchMessage(str, page).map(MessageDto::new));
    }

    @PostMapping("/reply/{id}")
    public ResponseEntity<MessageDto> replyMessage(@PathVariable Long id,
                                                     @RequestBody @Valid MessageDto message,
                                                     UriComponentsBuilder uriBuilder) {
        var newmessage = messageService.replyMessage(id, message.content());
        var uri = uriBuilder.path("/content/").buildAndExpand(newmessage.getId()).toUri();
        return ResponseEntity.created(uri).body(new MessageDto(newmessage));
    }

    @PatchMapping("/edit/{id}")
    ResponseEntity<?> editMessage(@PathVariable Long id, @RequestBody @Valid MessageDto message) {
        messageService.editMessage(id, message.content());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
