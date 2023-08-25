package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping("/cards/{id}")
    public CardDTO getCard(@PathVariable Long id){
        return cardRepository.findById(id).map(CardDTO::new).orElse(null);
    }

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentCards(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        if (authentication != null) {

            Client client = clientRepository.findByEmail(authentication.getName());


            Random random = new Random();
            StringBuilder cvv = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                cvv.append(random.nextInt(9));
            }
            Card card = new Card(cardType, cardColor, generateCardNumber(), LocalDateTime.now(), cvv.toString());
            client.addCard(card);
            cardRepository.save(card);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must to be logged to create a card", HttpStatus.FORBIDDEN);
        }

    }

    public String generateCardNumber(){
            Random random = new Random();
            StringBuilder cardNumber;
        do{
            cardNumber = new StringBuilder();

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 4; k++) {
                    cardNumber.append(random.nextInt(9));
                }
                    cardNumber.append("-");
            }
            for (int k = 0; k < 4; k++) {
                cardNumber.append(random.nextInt(9));
            }
        } while (cardRepository.findByNumber(cardNumber.toString()) != null);
        return cardNumber.toString();


/*            int i = 0;
            while (cardNumber.length() < 19) {
                cardNumber += random.nextInt(9);
                if (cardNumber.length() == 4 || cardNumber.length() == 9 || cardNumber.length() == 14) {
                    cardNumber += "-";
                }*/
    }
}