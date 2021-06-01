package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String saveNote(Authentication authentication, NoteForm noteForm, Model model) {
        if (noteForm.getNoteDescription().length() > 1000) {
            model.addAttribute("isSuccess", false);
            model.addAttribute("errorMessage", "Note can't be saved as description exceed 1000 characters.");
        } else {
            Integer userId = userService.getUser(authentication.getName()).getUserId();
            noteForm.setUserId(userId);
            boolean status = noteService.saveNote(noteForm) > 0;
            model.addAttribute("isSuccess", status);
        }
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId, Model model) {
        boolean status = noteService.deleteNote(Integer.parseInt(noteId)) > 0;
        model.addAttribute("isSuccess", status);
        return "result";
    }
}
