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
    private final UserService userService;

    @GetMapping("/list")
    ResponseEntity<List<UserDto>> listarUsers() {
        return ResponseEntity.ok(userService.allUsers().stream().map(UserDto::new).toList());
    }

    @GetMapping("/me")
    ResponseEntity<UserDto> me() {
        return ResponseEntity.ok(new UserDto(UserService.me()));
    }

    @PatchMapping("/edit")
    ResponseEntity<?> editUser(@RequestParam Map<String,String> args) {
        args.forEach((key,value) -> {
            switch (key) {
                case "username" -> userService.changeNick(value);
                case "name" -> userService.changeName(value);
                case "password" -> userService.changePassword(value);
            }
        });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
