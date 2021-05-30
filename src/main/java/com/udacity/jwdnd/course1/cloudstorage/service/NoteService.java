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

    public int createNote(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(noteForm.getUserId());
        return noteMapper.createNote(note);
    }

    public int updateNote(NoteForm noteForm) {
        Note note = new Note();
        note.setNoteId(noteForm.getNoteId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(noteForm.getUserId());
        return noteMapper.updateNote(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
