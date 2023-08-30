package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toList;
@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientService clientService;
    @Override
    public List<CardDTO> getCardsDTO() {
        return getCards().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public void saveCard(Card card){
        cardRepository.save(card);
    }
    @Override
    public List<Card> getCards() {
        return cardRepository.findAll();
    }

    @Override
    public CardDTO getCardDTO(Long id) {
        return getCard(id).map(CardDTO::new).orElse(null);
    }
    @Override
    public Optional<Card> getCard(Long id){
    return cardRepository.findById(id);
    }

    @Override
    public List<CardDTO> getCurrentCardsDTO(Authentication authentication){
        return getCurrentCards(authentication).stream().map(CardDTO::new).collect(toList());
    }
    @Override
    public Set<Card> getCurrentCards(Authentication authentication) {
        return clientService.getCurrentClient(authentication).getCards();
    }

    public ResponseEntity<Object> createCard(CardType cardType, CardColor cardColor, Authentication authentication){
        if (authentication != null) {
            Client client = clientService.getCurrentClient(authentication);
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
            Card card = new Card(cardType, cardColor, generateCardNumber(), LocalDateTime.now(), generateCvv());
            client.addCard(card);
            saveCard(card);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("You must to be logged to create a card", HttpStatus.FORBIDDEN);
        }
    }

    public String generateCvv(){
        Random random = new Random();
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(9));
        }
        return cvv.toString();
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

