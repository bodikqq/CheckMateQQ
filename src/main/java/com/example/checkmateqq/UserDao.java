package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;

public interface UserDao {

    User getById(int id) throws EntityNotFoundException;

    User save(User user) throws EntityNotFoundException;
}
