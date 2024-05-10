package com.example.soloproject.controllers;

import com.example.soloproject.models.Chore;
import com.example.soloproject.models.LoginUser;
import com.example.soloproject.models.User;
import com.example.soloproject.services.ChoreService;
import com.example.soloproject.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChoreService choreService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("userId") != null;
    }

    @GetMapping("/")
    public ResponseEntity<String> index(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return ResponseEntity.ok("index.jsp");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @ModelAttribute("newUser") User newUser,
                                           BindingResult result, Model model, HttpSession session) {
        userService.register(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Creating User");
        }

        session.setAttribute("userId", newUser.getId());

        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {

        User user = userService.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Login User");
        }

        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok("Login successfully");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.setAttribute("userId", null);
        return ResponseEntity.ok("Logout successfully");
    }

    @GetMapping("/classes")
    public ResponseEntity<?> welcome(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Chore> chores = choreService.getAllChores();

        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("chores", chores);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/classes/new")
    public ResponseEntity<?> showNewChoreForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        model.addAttribute("chore", new Chore());
        return ResponseEntity.ok(new Chore());
    }

    @PostMapping("/classes/new")
    public ResponseEntity<?> saveChore(@Valid @ModelAttribute("chore") Chore chore,
                             BindingResult bindingResult, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving chores");
        }
        User user = userService.findById((Long) session.getAttribute("userId"));
        chore.setUser(user);
        choreService.saveChore(chore);
        return ResponseEntity.ok(chore);
    }

    @GetMapping("/classes/{id}/edit")
    public ResponseEntity<?> showEditChoreForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        Chore chore = choreService.getChoreById(id).orElse(null);
        if (chore == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userService.findById(chore.getUser().getId());
        model.addAttribute("chore", chore);
        model.addAttribute("user", user);
        return ResponseEntity.ok(chore);
    }

    @PostMapping("/classes/{id}/edit")
    public ResponseEntity<?> updateChore(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("chore") Chore chore,
                               BindingResult bindingResult, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating chore");
        }
        chore.setId(id);
        choreService.saveChore(chore);
        return ResponseEntity.ok(chore);
    }

    @GetMapping("/classes/{id}/delete")
    public ResponseEntity<?> deleteChore(@PathVariable("id") Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        choreService.deleteChore(id);
        return ResponseEntity.ok("Chore deleted successfully");
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<?> showChoreDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
        Chore chore = choreService.getChoreById(id).orElse(null);
        if (chore == null) {
            return ResponseEntity.notFound().build();
        }
        model.addAttribute("chore", chore);
        return ResponseEntity.ok(chore);
    }
}
