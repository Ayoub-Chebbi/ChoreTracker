package com.example.soloproject.controllers;

import com.example.soloproject.models.Chore;
import com.example.soloproject.models.LoginUser;
import com.example.soloproject.models.User;
import com.example.soloproject.services.ChoreService;
import com.example.soloproject.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChoreService choreService;

    private boolean isLoggedIn(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return userId != null;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser,
                           BindingResult result, Model model, HttpSession session) {

        userService.register(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }

        session.setAttribute("userId", newUser.getId());

        return "redirect:/classes";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {

        User user = userService.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }

        session.setAttribute("userId", user.getId());

        return "redirect:/classes";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userId", null);
        return "redirect:/";
    }

    @GetMapping("/classes")
    public String welcome(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        Long userId = (Long) session.getAttribute("userId");
        User user = userService.findById(userId);
        List<Chore> chores = choreService.getAllChores();
        model.addAttribute("user", user);
        model.addAttribute("chores", chores);
        return "dashboard.jsp";
    }

    
    // Chore related mappings

    @GetMapping("/classes/{id}/add")
    public String showAddChoreForm(@PathVariable("id") Long id, Model model, HttpSession session) {
      if (!isLoggedIn(session)) {
        return "redirect:/";
      }
      Chore chore = choreService.getChoreById(id).orElse(null);
      if (chore == null) {
        return "redirect:/classes";
      }
      model.addAttribute("chore", chore);
      return "dashboard.jsp";
    }



    @PostMapping("/classes/{id}/add")
    public String addChoreToUser(@PathVariable("id") Long id, HttpSession session) {
      if (!isLoggedIn(session)) {
        return "redirect:/";
      }
      Chore chore = choreService.getChoreById(id).orElse(null);
      if (chore == null) {
        return "redirect:/classes";
      }
      Long userId = (Long) session.getAttribute("userId");
      User user = userService.findById(userId);
      user.getChores().add(chore);
      userService.save(user);
      chore.setUser(user);
      choreService.saveChore(chore);

      
      choreService.removeFromAvailableChores(chore.getId());

      return "redirect:/classes";
    }


    
    @GetMapping("/classes/new")
    public String showNewChoreForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        model.addAttribute("chore", new Chore());
        return "newChore.jsp";
    }

    @PostMapping("/classes/new")
    public String saveChore(@Valid @ModelAttribute("chore") Chore chore,
                             BindingResult bindingResult, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "newChore.jsp";
        }
        User user = userService.findById((Long) session.getAttribute("userId"));
        chore.setUser(user);
        choreService.saveChore(chore);
        return "redirect:/classes";
    }

    @GetMapping("/classes/{id}/edit")
    public String showEditChoreForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        Chore chore = choreService.getChoreById(id).orElse(null);
        if (chore == null) {
            return "redirect:/classes";
        }
        User user = userService.findById(chore.getUser().getId());
        model.addAttribute("chore", chore);
        model.addAttribute("user", user);
        return "editChore.jsp";
    }


    @PostMapping("/classes/{id}/edit")
    public String updateChore(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("chore") Chore chore,
                               BindingResult bindingResult, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "editChore.jsp";
        }
        chore.setId(id);
        choreService.saveChore(chore);
        return "redirect:/classes";
    }
    

    @GetMapping("/classes/{id}/delete")
    public String deleteChore(@PathVariable("id") Long id, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        choreService.deleteChore(id);
        return "redirect:/classes";
    }

    @GetMapping("/classes/{id}")
    public String showChoreDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) {
            return "redirect:/";
        }
        Chore chore = choreService.getChoreById(id).orElse(null);
        if (chore == null) {
            return "redirect:/classes";
        }
        model.addAttribute("chore", chore);
        return "choreDetails.jsp";
    }
}
