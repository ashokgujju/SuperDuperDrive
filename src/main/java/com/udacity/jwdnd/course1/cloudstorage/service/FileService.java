package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getAllFilesByUserId(Integer userId) {
        return fileMapper.getAllFilesByUserId(userId);
    }

    public Integer saveFile(Integer userId, MultipartFile multipartFile) {
        File file = new File();
        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(String.valueOf(multipartFile.getSize()));
        file.setUserId(userId);
        try {
            file.setFileData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileMapper.saveFile(file);
    }

    public File getFile(Integer fileId) {
        return fileMapper.getFileById(fileId);
    }

    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }
}
