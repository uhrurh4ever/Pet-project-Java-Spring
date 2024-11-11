package com.example.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.exceptions.AccessDeniedException;
import com.example.model.Note;
import com.example.model.User;
import com.example.service.interfaces.NoteService;
import com.example.service.interfaces.UserService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getAllNotes(Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        List<Note> notes = noteService.getNotesByUser(user);
        model.addAttribute("notes", notes);
        model.addAttribute("username", user.getEmail());

        return "notes.html";
    }

    @GetMapping("/{id}")
    public String showNoteDetails(@PathVariable Long id, Model model) throws AccessDeniedException {
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

        if (!noteService.isNoteNameUnique(user, note)) {
            model.addAttribute("error", "A note with this name already exists. Please choose a different name.");
            model.addAttribute("note", note);
            return "note-form";
        }

        noteService.saveNote(note);
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

        if (!noteService.isNoteNameUnique(user, note)) {
            model.addAttribute("error", "A note with this name already exists. Please choose a different name.");
            model.addAttribute("note", note);
            return "edit-note-form";
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
