package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
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
@RequestMapping("/home")
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(NoteForm noteForm, Model model) {
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }

    @PostMapping
    public String postNote(Authentication authentication, NoteForm noteForm, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        noteForm.setUserId(userId);
        if (noteForm.getNoteId() == null) {
            noteService.createNote(noteForm);
        } else {
            noteService.updateNote(noteForm);
        }

        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }

    @GetMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") String noteId) {
        noteService.deleteNote(Integer.parseInt(noteId));
        return "redirect:/home";
    }
}
