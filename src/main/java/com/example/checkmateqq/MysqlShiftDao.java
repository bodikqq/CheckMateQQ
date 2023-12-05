package com.example.checkmateqq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import com.example.checkmateqq.triedy.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class MysqlShiftDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlShiftDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getById(int id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void save(User user) throws EntityNotFoundException {
        //return null;
    }

    @Override
    public boolean checkIfLoginExist(String login) throws EntityNotFoundException {

        return true;
    }
    @Override
    public boolean checkIfWorkerCodeIsReal(String code) throws EntityNotFoundException {
        return false;
    }
    @Override
    public boolean checkIfUserExists(String login, String password) throws EntityNotFoundException {
        return false;
    }
}
