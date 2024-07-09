package challenge.forum.fforwm.controllers;

import challenge.forum.fforwm.domain.user.dto.UserDto;
import challenge.forum.fforwm.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/list")
    ResponseEntity<List<UserDto>> listarUsers() {
        return ResponseEntity.ok(service.allUsers().stream().map(UserDto::new).toList());
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(new UserDto(service.me()));
    }

    @PatchMapping("/edit")
    ResponseEntity<?> editUser(@RequestParam Map<String,String> args) {
        args.forEach((key,value) -> {
            switch (key) {
                case "username" -> service.mudarNick(value);
                case "name" -> service.mudarName(value);
                case "password" -> service.mudarPassword(value);
            }
        });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    ResponseEntity<?> deleteUser(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
