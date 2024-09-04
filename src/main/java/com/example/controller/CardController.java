package com.example.controller;

import com.example.exceptions.AccessDeniedException;
import com.example.model.CardSet;
import com.example.model.Note;
import com.example.model.User;
import com.example.service.interfaces.CardService;
import com.example.service.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/card")
public class CardController {
    private CardService cardService;
    private UserService userService;

    public CardController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping
    public String getAllCardSets(Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        List<CardSet> cardSetList = cardService.getCardSetByUser(user);
        model.addAttribute("cardSet", cardSetList);
        model.addAttribute("username", user.getEmail());
        return "card";
    }

    @GetMapping("/{id}")
    public String getNoteById(@PathVariable Long id, Model model) throws AccessDeniedException {
        CardSet cardSet = cardService.getCardSetById(id);
        User user = userService.getCurrentAuthenticatedUser();

        if (!cardSet.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to access this card set");
        }

        model.addAttribute("card", cardSet);
        return "card-set-details";
    }

    @GetMapping("/new")
    public String showNewCardSetForm(Model model) {
        model.addAttribute("card", new CardSet());
        return "card-set-form";
    }

    @PostMapping("/{id}/update")
    public String updateNote(@PathVariable Long id, @ModelAttribute CardSet cardSet, Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        cardSet.setUser(user);

        List<CardSet> existingCardSets = cardService.getCardSetByUser(user);
        for (CardSet existingCardSet : existingCardSets) {
            if ((!existingCardSet.getId().equals(cardSet.getId()) && existingCardSet.getName().equals(cardSet.getName()))) {
                model.addAttribute("error", "A note with this name already exists. Please choose a different name.");
                model.addAttribute("cardSet", cardSet); // Добавить note в модель для возврата введенных данных
                return "edit-card-set-form";
            }
        }

        cardService.updateNote(note);

        return "redirect:/notes";
    }

}
