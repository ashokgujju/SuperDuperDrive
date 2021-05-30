package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequestMapping("/files")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String homeView(NoteForm noteForm, CredentialForm credentialForm, Model model) {
        model.addAttribute("notes", new ArrayList<Note>());
        model.addAttribute("credentials", new ArrayList<Credential>());
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file,
                             NoteForm noteForm, CredentialForm credentialForm, Model model) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        fileService.saveFile(userId, file);

        model.addAttribute("notes", new ArrayList<Note>());
        model.addAttribute("credentials", new ArrayList<Credential>());
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

    @GetMapping("/delete-file/{fileId}")
    public String deleteFile(@PathVariable("fileId") String fileId) {
        fileService.deleteFile(Integer.parseInt(fileId));
        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) {
        File file = fileService.getFile(Integer.parseInt(fileId));

        ByteArrayResource byteArrayResource = new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getFileName())
                .contentLength(Long.parseLong(file.getFileSize()))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}