package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/home")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private FileService fileService;

    public CredentialController(CredentialService credentialService, UserService userService, FileService fileService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Model model) {
        model.addAttribute("notes", new ArrayList<Note>());
        model.addAttribute("credentials", credentialService.getAllCredentials());
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @PostMapping
    public String postCredential(Authentication authentication, NoteForm noteForm, CredentialForm credentialForm,
                                 Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        credentialForm.setUserId(userId);
        if (credentialForm.getCredentialId() == null) {
            credentialService.saveCredential(credentialForm);
        } else {
            credentialService.updateCredential(credentialForm);
        }

        model.addAttribute("notes", new ArrayList<Note>());
        model.addAttribute("credentials", credentialService.getAllCredentials());
        return "home";
    }

    @GetMapping("/delete-credential/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId) {
        credentialService.deleteCredential(Integer.parseInt(credentialId));
        return "redirect:/home";
    }
}
