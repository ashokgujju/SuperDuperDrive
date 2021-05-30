package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotesByUserId(Integer userId) {
        return noteMapper.getAllNotesByUserId(userId);
    }

    public int saveNote(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(noteForm.getUserId());
        if (noteForm.getNoteId() == null) {
            return noteMapper.createNote(note);
        } else {
            note.setNoteId(noteForm.getNoteId());
            return noteMapper.updateNote(note);
        }
    }

    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }
}
