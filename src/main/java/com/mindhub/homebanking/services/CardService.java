package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CardService {
    void saveCard(Card card);
    List<CardDTO> getCardsDTO();
    List<Card> getCards();
    CardDTO getCardDTO(Long id);
    Optional<Card> getCard(Long id);
    List<CardDTO> getCurrentCardsDTO(String clientEmail);
    Set<Card> getCurrentCards(String clientEmail);

    Card createCard(CardType cardType, CardColor cardColor, Client client);
    String generateCvv();
    String generateCardNumber();
}
