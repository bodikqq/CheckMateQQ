package com.example.checkmateqq;

import com.example.checkmateqq.triedy.User;

import java.util.Date;
import java.util.List;

public interface UserDao {

    User getById(int id) throws EntityNotFoundException;

    void save(User user) throws EntityNotFoundException;
    boolean checkIfLoginExist(String login)throws EntityNotFoundException;
    boolean checkIfWorkerCodeIsReal(String code) throws EntityNotFoundException;
    boolean checkIfUserExists(String login, String password) throws EntityNotFoundException;

    User getUserByLoginAndPassword(String login, String password);

    int workersOnTime(Date date,int stationId, boolean isFirst);

    boolean isUserEmployee(int userId);

    List<User> returnEmployees();

    void deleteUserById(int userId) throws EntityNotFoundException;

    boolean checkIfUserExistsById(int userId);
}
