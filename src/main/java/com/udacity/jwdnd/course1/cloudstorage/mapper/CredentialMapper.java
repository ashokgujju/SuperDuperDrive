package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "values(#{url},#{username},#{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int saveCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key=#{key}, password=#{password}  " +
            "WHERE credentialid=#{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId}")
    int deleteCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
    List<Credential> getAllCredentialsByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId} AND url=#{url} AND username=#{username}")
    Credential getCredentialByUrlAndUsername(Integer userId, String url, String username);
}