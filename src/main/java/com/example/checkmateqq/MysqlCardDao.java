package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Card;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.Objects;

public class MysqlCardDao implements CardDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Card> cardRM() {
        return (rs, rowNum) -> {
            int id = rs.getInt("id");
            String cardNumber = rs.getString("cardNumber");
            int cvv = rs.getInt("cvv");
            int date = rs.getInt("date");
            return new Card(id, cardNumber, cvv, date);
        };
    }

    @Override
    public Card getCardById(int id) throws EntityNotFoundException {
        String sql = "SELECT * FROM card WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, cardRM(), id);
    }

    @Override
    public void saveCard(Card card) throws EntityNotFoundException {
        //Objects.requireNonNull(card, "Card cannot be null");
        String query = "INSERT INTO card (cardNumber, cvv, date) VALUES (?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((PreparedStatementCreator) con -> {
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, card.getCardNumber());
            statement.setInt(2, card.getCvv());
            statement.setInt(3, card.getDate());
            return statement;
        }, keyHolder);
    }
}
