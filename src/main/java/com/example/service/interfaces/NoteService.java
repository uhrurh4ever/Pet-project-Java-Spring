package com.example.service.interfaces;




import java.util.List;

import com.example.model.Note;
import com.example.model.User;

public interface NoteService {

  List <Note> getAllNotes();
  
  Note saveNote(Note note);

  Note getNoteById(Long id);

  Note updateNote(Note note);

  void deleteNoteById(Long id);

  List<Note> getNotesByUser(User user);
}
