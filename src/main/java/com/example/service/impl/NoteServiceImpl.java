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

    /**
     * Репозиторий для работы с сущностью Note
     */
    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Получение всех заметок
     *
     * @return Список объектов Note, представляющий все заметки в базе данных
     */
    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Сохранение заметки в базе данных
     *
     * @param note Объект Note, представляющий сохраняемую заметку
     * @return Сохраненная заметка (объект Note)
     */
    @Override
    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Получение заметки по идентификатору
     *
     * @param id Идентификатор заметки
     * @return Объект Note, представляющий найденную заметку,
     *         или null, если заметка с таким идентификатором не найдена
     */
    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).get();
    }

    /**
     * Обновление заметки в базе данных
     *
     * @param note Объект Note, содержащий обновленные данные заметки
     * @return Обновленная заметка (объект Note)
     */
    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Удаление заметки из базы данных по идентификатору
     *
     * @param id Идентификатор заметки
     */
    @Override
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }
}

