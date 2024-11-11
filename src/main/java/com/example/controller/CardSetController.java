package com.example.controller;

import com.example.exceptions.AccessDeniedException;
import com.example.model.Card;
import com.example.model.CardSet;
import com.example.model.User;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.service.interfaces.CardSetService;
import com.example.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cardSets")
public class CardSetController {

    private final CardSetService cardSetService;
    private final UserService userService;

    @GetMapping("/new")
    public String showNewCardSetForm(Model model) {
        model.addAttribute("cardSet", new CardSet());
        return "cardSetForm";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getAllCardSets(Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        List<CardSet> cardSets = cardSetService.getCardSetsByUser(user);
        model.addAttribute("cardSets", cardSets);
        model.addAttribute("username", user.getEmail());
        return "cardSets.html";
    }

    @GetMapping("/{id}")
    public String showCardSetDetails(@PathVariable Long id, Model model) throws AccessDeniedException {
        CardSet cardSet = cardSetService.getCardSetById(id);
        User user = userService.getCurrentAuthenticatedUser();

        if (!cardSet.getUser().equals(user)) {
            throw new AccessDeniedException("You do not have permission to access this note");
        }
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("cards", cardSet.getCards()); 
        model.addAttribute("newCard", new Card());
        return "cardSetDetails";
    }

    @PostMapping
    public String saveCardSet(@ModelAttribute CardSet cardSet, Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        cardSet.setUser(user);

        if (!cardSetService.isCardSetNameUnique(user, cardSet)) {
            model.addAttribute("error", "A card set with this name already exists. Please choose a different name.");
            model.addAttribute("cardSet", cardSet);
            return "cardSetForm";
        }

        cardSetService.saveCardSet(cardSet);
        return "redirect:/cardSets";
    }

    @GetMapping("/{id}/edit")
    public String showEditCardSetForm(@PathVariable Long id, Model model) {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "editCardSetForm";
    }

    @PostMapping("/{id}/update")
    public String updateCardSet(@PathVariable Long id, @ModelAttribute CardSet cardSet, Model model) {
        User user = userService.getCurrentAuthenticatedUser();
        cardSet.setUser(user);

        if (!cardSetService.isCardSetNameUnique(user, cardSet)) {
            model.addAttribute("error", "A card set with this name already exists. Please choose a different name.");
            model.addAttribute("cardSet", cardSet);
            return "editCardSetForm";
        }

        cardSetService.updateCardSet(cardSet);
        return "redirect:/cardSets";
    }

    @GetMapping("/{id}/delete")
    public String deleteCardSet(@PathVariable Long id) {
        cardSetService.deleteCardSetById(id);
        return "redirect:/cardSets";
    }

    @PostMapping("/{id}/addCard")
    public String addCardToCardSet(@PathVariable Long id, @ModelAttribute("newCard") Card Card) {
        CardSet cardSet = cardSetService.getCardSetById(id);
        cardSetService.addCardToCardSet(cardSet, Card); 
        return "redirect:/cardSets/" + id; 
    }
}
