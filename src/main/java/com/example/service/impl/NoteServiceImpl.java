package com.example.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Note;
import com.example.model.User;
import com.example.repository.NoteRepository;
import com.example.service.interfaces.NoteService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).get();
    }

    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }
}

