package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) values(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int deleteNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId}")
    List<Note> getAllNotesByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE userid=#{userId} AND notetitle=#{noteTitle} AND notedescription=#{noteDescription}")
    Note getNoteWithTitleAndDescription(Integer userId, String noteTitle, String noteDescription);
}
