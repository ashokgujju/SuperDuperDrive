package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.getAllCredentials();
    }

    public int saveCredential(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setUrl(credentialForm.getUrl());
        credential.setKey("ashok"); //KEY
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword()); // hash it
        credential.setUserId(credentialForm.getUserId());

        return credentialMapper.saveCredential(credential);
    }

    public Integer updateCredential(CredentialForm credentialForm) {
        Credential credential = new Credential();
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setKey("ashok"); //KEY
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword()); // hash it
        credential.setUserId(credentialForm.getUserId());

        return credentialMapper.updateCredential(credential);
    }

    public Integer deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }
}
