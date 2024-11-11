package com.example;

import com.example.controller.NoteController;
import com.example.model.Note;
import com.example.model.User;
import com.example.service.interfaces.NoteService;
import com.example.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

class NoteControllerTest {

        @Mock
        private NoteService noteService;

        @Mock
        private UserService userService;

        @InjectMocks
        private NoteController noteController;

        private MockMvc mockMvc;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
        }

        @Test
        void getAllNotesTest() throws Exception {
                User mockUser = new User();
                mockUser.setEmail("test@example.com");

                Note note1 = new Note();
                note1.setName("Note 1");
                note1.setTopic("Topic 1");
                note1.setText("This is the text of note 1.");

                Note note2 = new Note();
                note2.setName("Note 2");
                note2.setTopic("Topic 2");
                note2.setText("This is the text of note 2.");

                List<Note> mockNotes = List.of(note1, note2);

                when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
                when(noteService.getNotesByUser(mockUser)).thenReturn(mockNotes);

                mockMvc.perform(get("/notes"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("notes.html"))
                                .andExpect(model().attributeExists("notes"))
                                .andExpect(model().attribute("notes", mockNotes))
                                .andExpect(model().attributeExists("username"))
                                .andExpect(model().attribute("username", mockUser.getEmail()))
                                .andExpect(model().attribute("notes", hasItem(
                                                allOf(
                                                                hasProperty("name", is("Note 1")),
                                                                hasProperty("text",
                                                                                is("This is the text of note 1."))))))
                                .andExpect(model().attribute("notes", hasItem(
                                                allOf(
                                                                hasProperty("name", is("Note 2")),
                                                                hasProperty("text",
                                                                                is("This is the text of note 2."))))));

                verify(userService, times(1)).getCurrentAuthenticatedUser();
                verify(noteService, times(1)).getNotesByUser(mockUser);
        }
}
