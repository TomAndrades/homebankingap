package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
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
    private ClientRepository clientRepository;
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
    public List<CardDTO> getCurrentCardsDTO(String clientEmail){
        return getCurrentCards(clientEmail).stream().map(CardDTO::new).collect(toList());
    }
    @Override
    public Set<Card> getCurrentCards(String clientEmail) {
        return clientRepository.findByEmail(clientEmail).getCards();
    }

    public Card createCard(CardType cardType, CardColor cardColor, Client client){
            Card card = new Card(cardType, cardColor, generateCardNumber(), LocalDateTime.now(), generateCvv());
            client.addCard(card);
            saveCard(card);
            return card;

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
        } while (cardRepository.existsByNumber(cardNumber.toString()));
        return cardNumber.toString();
/*            int i = 0;
            while (cardNumber.length() < 19) {
                cardNumber += random.nextInt(9);
                if (cardNumber.length() == 4 || cardNumber.length() == 9 || cardNumber.length() == 14) {
                    cardNumber += "-";
                }*/
    }
}

