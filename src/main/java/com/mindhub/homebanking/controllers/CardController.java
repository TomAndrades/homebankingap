package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardService cardService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards(){ return cardService.getCardsDTO(); }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardService.getCardDTO(id);
    }
    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentCards(Authentication authentication){ return cardService.getCurrentCardsDTO(authentication); }

    @RequestMapping(method = RequestMethod.POST, value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        return cardService.createCard(cardType, cardColor, authentication);
    }

}