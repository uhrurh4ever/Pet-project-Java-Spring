package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exceptions.AccessDeniedException;
import com.example.model.Note;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.interfaces.NoteService;
import com.example.service.interfaces.UserService;


@Controller
@RequestMapping("/notes")
public class NoteController {

    private NoteService noteService;
    private UserRepository userRepository;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService, UserRepository userRepository) {
        this.noteService = noteService;
        this.userService = userService;
        this.userRepository = userRepository;

    }


    @GetMapping
    public String getAllNotes(Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        List<Note> notes = noteService.getNotesByUser(user);
        model.addAttribute("notes", notes);
        model.addAttribute("username", user.getEmail());
        return "notes";
    }

    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) throws AccessDeniedException {
        Note note = noteService.getNoteById(id);
        User user = userService.getCurrentAuthenticatedUser();

        if (!note.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to access this note");
        }

        model.addAttribute("note", note);
        return "note-details";
    }

    @GetMapping("/new")
    public String showNewNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "note-form";
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note, Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        note.setUser(user);

        List<Note> existingNotes = noteService.getNotesByUser(user);
        for (Note existingNote : existingNotes) {
            if (existingNote.getName().equals(note.getName())) {
                model.addAttribute("error", "A note with this name already exists. Please choose a different name.");
                model.addAttribute("note", note);
                return "note-form";
            }
        }

        if (note.getId() != null) {
            noteService.updateNote(note);
        } else {
            noteService.saveNote(note);
        }

        return "redirect:/notes";
    }


    @GetMapping("/{id}/edit")
    public String showEditNoteForm(@PathVariable Long id, Model model) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "edit-note-form";
    }


    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute Note note, Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        note.setUser(user);

        List<Note> existingNotes = noteService.getNotesByUser(user);
        for (Note existingNote : existingNotes) {
            if ((!existingNote.getId().equals(note.getId()) && existingNote.getName().equals(note.getName()))) {
                model.addAttribute("error", "A note with this name already exists. Please choose a different name.");
                model.addAttribute("note", note); // Добавить note в модель для возврата введенных данных
                return "edit-note-form";
            }
        }

        noteService.updateNote(note);

        return "redirect:/notes";
    }


    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }


}

