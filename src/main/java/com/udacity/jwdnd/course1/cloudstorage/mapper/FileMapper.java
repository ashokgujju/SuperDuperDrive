package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId}")
    int deleteFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getAllFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{fileName} AND userid=#{userId}")
    File getFileByName(String fileName, Integer userId);
}
