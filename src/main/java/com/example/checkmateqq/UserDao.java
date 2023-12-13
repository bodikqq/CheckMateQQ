package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;

import java.util.Date;

public interface UserDao {

    User getById(int id) throws EntityNotFoundException;

    void save(User user) throws EntityNotFoundException;
    boolean checkIfLoginExist(String login)throws EntityNotFoundException;
    boolean checkIfWorkerCodeIsReal(String code) throws EntityNotFoundException;
    boolean checkIfUserExists(String login, String password) throws EntityNotFoundException;

    User getUserByLoginAndPassword(String login, String password);

    int workersOnTime(Date date,int stationId, boolean isFirst);

    boolean isUserEmployee(int userId);
}
