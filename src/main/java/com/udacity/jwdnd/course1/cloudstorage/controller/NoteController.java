package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
    public String postNote(Authentication authentication, NoteForm noteForm) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        noteForm.setUserId(userId);
        if (noteForm.getNoteId() == null) {
            noteService.createNote(noteForm);
        } else {
            noteService.updateNote(noteForm);
        }

        return "redirect:/home";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId) {
        noteService.deleteNote(Integer.parseInt(noteId));
        return "redirect:/home";
    }
}
