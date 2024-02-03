package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Card;

public interface CardDao {
    Card getCardById(int id) throws EntityNotFoundException;

    int saveCard(Card card) throws EntityNotFoundException;

 //   Card getCardByUserId(int userId) throws EntityNotFoundException;
}
