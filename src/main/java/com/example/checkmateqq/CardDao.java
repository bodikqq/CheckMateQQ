package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Card;

public interface CardDao {
    Card getCardById(int id) throws EntityNotFoundException;

    void saveCard(Card card) throws EntityNotFoundException;
}
