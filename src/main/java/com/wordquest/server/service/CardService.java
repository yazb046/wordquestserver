package com.wordquest.server.service;

import com.wordquest.server.entity.Card;
import com.wordquest.server.entity.User;
import com.wordquest.server.repository.CardRepository;
import com.wordquest.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }


    public void save(Long userId, String contextTitle, String contextDescription, Long textId) {
        User user = userRepository.getReferenceById(userId);
        Long version = 0L;
        Card card = new Card(contextTitle, contextDescription, textId, version);
        card.setUser(user);
        List<Card> temp = user.getCards();
        if(temp == null){
            temp = new ArrayList<>();
        }
        temp.add(card);
        user.setCards(temp);
        userRepository.save(user);
    }
}