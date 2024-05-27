package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Note;
import com.example.model.User;

public interface NoteRepository extends JpaRepository<Note,Long> {

  List<Note> findByUser(User user);
}
