package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;

    @RequestMapping("/cards")
    public List<CardDTO> getCards(){ return cardService.getCardsDTO(); }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardService.getCardDTO(id);
    }
    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentCards(Authentication authentication){ return cardService.getCurrentCardsDTO(authentication.getName()); }

    @RequestMapping(method = RequestMethod.POST, value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        if (authentication != null) {
            Client client = clientService.getCurrentClient(authentication.getName());
            switch (cardType) {
                case DEBIT:
                    if (client.getDebitCards().size() < 3) {
                        break;
                    } else {
                        return new ResponseEntity<>(
                                "You have the maximum number of debit cards available", HttpStatus.FORBIDDEN);
                    }
                case CREDIT:
                    if (client.getCreditCards().size() < 3) {
                        break;
                    } else {
                        return new ResponseEntity<>(
                                "You have the maximum number of credit cards available", HttpStatus.FORBIDDEN);
                    }
            }
            cardService.createCard(cardType, cardColor, client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must to be logged to create a card", HttpStatus.FORBIDDEN);
        }


    }

}