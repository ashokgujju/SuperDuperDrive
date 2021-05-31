package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public HomeController(NoteService noteService, CredentialService credentialService,
                          FileService fileService, UserService userService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homeView(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();

        model.addAttribute("notes", noteService.getAllNotesByUserId(userId));
        model.addAttribute("credentials", credentialService.getAllCredentialsByUserId(userId));
        model.addAttribute("files", fileService.getAllFilesByUserId(userId));
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}
