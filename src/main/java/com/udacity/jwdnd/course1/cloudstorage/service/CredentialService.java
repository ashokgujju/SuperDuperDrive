package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId) {
        return credentialMapper.getAllCredentialsByUserId(userId);
    }

    public Integer saveCredential(CredentialForm credentialForm) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setKey(encodedKey);
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(encryptionService.encryptValue(credentialForm.getPassword(), encodedKey));
        credential.setUserId(credentialForm.getUserId());

        if (credentialForm.getCredentialId() == null) {
            return credentialMapper.saveCredential(credential);
        } else {
            credential.setCredentialId(credentialForm.getCredentialId());
            return credentialMapper.updateCredential(credential);
        }
    }

    public Integer deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }
}
