package com.example.controller;

import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.exceptions.CardSetNotFoundException;
import com.example.model.Card;
import com.example.model.CardSet;
import com.example.service.interfaces.CardSetService;

@Controller
@RequestMapping("/card-sets")
public class CardSetController {

    /**
     * Сервис для работы с наборами карточек
     */
    private CardSetService cardSetService;

    public CardSetController(CardSetService cardSetService) {
        this.cardSetService = cardSetService;
    }

    /**
     * Обработка GET запроса на "/card-sets" (получение всех наборов карточек)
     *
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-sets.html"
     */
    @GetMapping
    public String getAllCardSets(Model model) {
        Collection<CardSet> cardSets = cardSetService.getAllCardSets();
        model.addAttribute("cardSets", cardSets);
        return "card-sets"; 
    }

    /**
     * Обработка GET запроса на "/card-sets/{id}" (получение конкретного набора карточек по id)
     *
     * @param id Идентификатор набора карточек
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-set-details.html"
     * @throws Exception Ошибки, возникшие при получении набора карточек 
     */
    @GetMapping("/{id}")
    public String getCardSetById(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-details";
    }

    /**
     * Обработка GET запроса на "/card-sets/new" (форма создания нового набора карточек)
     *
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-set-form.html"
     */
    @GetMapping("/new")
    public String showNewCardSetForm(Model model) {
        model.addAttribute("cardSet", new CardSet());
        return "card-set-form";
    }

    /**
     * Обработка POST запроса на "/card-sets" (сохранение нового набора карточек)
     *
     * @param cardSet Объект CardSet из формы создания набора карточек
     * @return Перенаправление на "/card-sets" (список всех наборов карточек)
     */
    @PostMapping
    public String saveCardSet(@ModelAttribute CardSet cardSet) {
        cardSetService.saveCardSet(cardSet);
        return "redirect:/card-sets";
    }

    /**
     * Обработка GET запроса на "/card-sets/{id}/edit" (форма редактирования набора карточек)
     *
     * @param id Идентификатор набора карточек
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-set-form.html"
     * @throws Exception Ошибки, возникшие при получении набора карточек
     */
    @GetMapping("/{id}/edit")
    public String showEditCardSetForm(@PathVariable Long id, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(id);
        model.addAttribute("cardSet", cardSet);
        return "card-set-form";
    }

        /**
     * Обработка POST запроса на "/card-sets/{id}/update" (обновление набора карточек)
     *
     * @param id Идентификатор набора карточек
     * @param cardSet Объект CardSet из формы редактирования набора карточек
     * @return Перенаправление на "/card-sets/{id}" (детали конкретного набора карточек)
     * @throws Exception Ошибки, возникшие при обновлении набора карточек
     */
    @PostMapping("/{id}/update")
    public String updateCardSet(@PathVariable Long id, @ModelAttribute CardSet cardSet) throws Exception {
        cardSet.setId(id); // В целях безопасности явно устанавливаем полученный id
        cardSetService.updateCardSet(cardSet);
        return "redirect:/card-sets/" + id;
    }

    /**
     * Обработка GET запроса на "/card-sets/{id}/delete" (удаление набора карточек)
     *
     * @param id Идентификатор набора карточек
     * @return Перенаправление на "/card-sets" (список всех наборов карточек)
     * @throws CardSetNotFoundException Ошибка, если набор карточек не найден
     */
    @GetMapping("/{id}/delete")
    public String deleteCardSet(@PathVariable Long id) throws CardSetNotFoundException {
        cardSetService.deleteCardSetById(id);
        return "redirect:/card-sets";
    }

    /**
     * Обработка GET запроса на "/card-sets/{cardSetId}/cards" (получение карточек в наборе)
     *
     * @param cardSetId Идентификатор набора карточек
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-set-cards.html"
     * @throws Exception Ошибки, возникшие при получении карточек
     */
    @GetMapping("/{cardSetId}/cards")
    public String getCardsInCardSet(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        Collection<Card> cards = cardSet.getCards();
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("cards", cards);
        return "card-set-cards";
    }

    /**
     * Обработка GET запроса на "/card-sets/{cardSetId}/cards/add" (форма добавления карточки в набор)
     *
     * @param cardSetId Идентификатор набора карточек
     * @param model Модель Spring MVC для передачи данных в представление
     * @return Логическое имя представления "card-set-add-card-form.html"
     * @throws Exception Ошибки, возникшие при получении набора карточек
     */
    @GetMapping("/{cardSetId}/cards/add")
    public String showAddCardToCardSetForm(@PathVariable Long cardSetId, Model model) throws Exception {
        CardSet cardSet = cardSetService.getCardSetById(cardSetId);
        model.addAttribute("cardSet", cardSet);
        model.addAttribute("card", new Card());
        return "card-set-add-card-form";
    }

    /**
     * Обработка POST запроса на "/card-sets/{cardSetId}/cards/add" (добавление карточки в набор)
     *
     * @param cardSetId Идентификатор набора карточек
     * @param card Объект Card из формы добавления карточки
     * @return Перенаправление на "/card-sets/{cardSetId}/cards" (список карточек в наборе)
     * @throws Exception Ошибки, возникшие при добавлении карточки
     */
    @PostMapping("/{cardSetId}/cards/add")
    public String addCardToCardSet(@PathVariable Long cardSetId, @ModelAttribute Card card) throws Exception {
        cardSetService.addCardToCardSet(cardSetId, card.getId());
        return "redirect:/card-sets/" + cardSetId + "/cards";
    }
  }
    

