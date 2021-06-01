package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String postCredential(Authentication authentication, CredentialForm credentialForm, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        if (credentialService.getCredentialByUrlAndUsername(userId,
                credentialForm.getUrl(), credentialForm.getUsername()) != null) {
            model.addAttribute("isSuccess", false);
            model.addAttribute("errorMessage", "Credential already available.");
        } else {
            credentialForm.setUserId(userId);
            boolean status = credentialService.saveCredential(credentialForm) > 0;
            model.addAttribute("isSuccess", status);
        }
        return "result";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") String credentialId, Model model) {
        boolean status = credentialService.deleteCredential(Integer.parseInt(credentialId)) > 0;
        model.addAttribute("isSuccess", status);
        return "result";
    }
}
