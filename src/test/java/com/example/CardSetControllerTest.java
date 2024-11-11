package com.example;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.service.interfaces.CardSetService;
import com.example.controller.CardSetController;
import com.example.model.CardSet;
import com.example.model.User;
import com.example.service.interfaces.UserService;

public class CardSetControllerTest {

    @Mock
    private CardSetService cardSetService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CardSetController cardSetController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardSetController).build();
    }

    @Test
    void getAllCardSetsTest() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");

        CardSet cardSet1 = new CardSet();
        cardSet1.setName("Test1");
        cardSet1.setTopic("Test1");
        cardSet1.setDescription("Test1");

        CardSet cardSet2 = new CardSet();
        cardSet2.setName("Test2"); // Здесь исправление: используем cardSet2
        cardSet2.setTopic("Test2");
        cardSet2.setDescription("Test2");

        List<CardSet> mockCardSets = List.of(cardSet1, cardSet2);

        when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
        when(cardSetService.getCardSetsByUser(mockUser)).thenReturn(mockCardSets);

        mockMvc.perform(get("/cardSets"))
                .andExpect(status().isOk())
                .andExpect(view().name("cardSets.html"))
                .andExpect(model().attributeExists("cardSets"))
                .andExpect(model().attribute("cardSets", hasItem(
                        allOf(
                                hasProperty("name", is("Test1")),
                                hasProperty("description", is("Test1"))
                        ))))
                .andExpect(model().attribute("cardSets", hasItem(
                        allOf(
                                hasProperty("name", is("Test2")),
                                hasProperty("description", is("Test2"))
                        ))))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attribute("username", mockUser.getEmail()));

        verify(userService, times(1)).getCurrentAuthenticatedUser();
        verify(cardSetService, times(1)).getCardSetsByUser(mockUser);
    }
}
