package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;

public interface UserDao {

    User getById(int id) throws EntityNotFoundException;

    void save(User user) throws EntityNotFoundException;
    boolean checkIfLoginExist(String login)throws EntityNotFoundException;
    boolean checkIfWorkerCodeIsReal(String code) throws EntityNotFoundException;
    boolean checkIfUserExists(String login, String password) throws EntityNotFoundException;
}
