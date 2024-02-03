package com.example.checkmateqq;


import com.example.checkmateqq.triedy.Card;
import com.example.checkmateqq.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MysqlCardDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlCardDao cardDao;

    @Test
    public void testGetCardById() throws EntityNotFoundException, SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("cardNumber")).thenReturn("1234567890123456");
        when(resultSet.getInt("cvv")).thenReturn(123);
        when(resultSet.getInt("date")).thenReturn(1223);

        RowMapper<Card> cardRM = cardDao.cardRM();
        Card card = cardRM.mapRow(resultSet, 1);

        assertNotNull(card);
        assertEquals(1, card.getId());
        assertEquals("1234567890123456", card.getCardNumber());
        assertEquals(123, card.getCvv());
        assertEquals(1223, card.getDate());
    }

    @Test
    public void testSaveCard() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("cardNumber")).thenReturn("1234567890123456");
        when(resultSet.getInt("cvv")).thenReturn(123);
        when(resultSet.getInt("date")).thenReturn(1223);

        RowMapper<Card> cardRM = cardDao.cardRM();
        Card card = cardRM.mapRow(resultSet, 1);

        assertNotNull(card);
        assertEquals(1, card.getId());
        assertEquals("1234567890123456", card.getCardNumber());
        assertEquals(123, card.getCvv());
        assertEquals(1223, card.getDate());
    }
    @Test
    public void testCardRM() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("cardNumber")).thenReturn("1234567890123456");
        when(resultSet.getInt("cvv")).thenReturn(123);
        when(resultSet.getInt("date")).thenReturn(1223);

        RowMapper<Card> cardRM = cardDao.cardRM();
        Card card = cardRM.mapRow(resultSet, 1);

        assertNotNull(card);
        assertEquals(1, card.getId());
        assertEquals("1234567890123456", card.getCardNumber());
        assertEquals(123, card.getCvv());
        assertEquals(1223, card.getDate());
    }

    @Test
    public void testCardRMWithNullValues() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("cardNumber")).thenReturn(null);
        when(resultSet.getInt("cvv")).thenReturn(0);
        when(resultSet.getInt("date")).thenReturn(0);

        RowMapper<Card> cardRM = cardDao.cardRM();
        Card card = cardRM.mapRow(resultSet, 1);

        assertNotNull(card);
        assertEquals(1, card.getId());
        assertNull(card.getCardNumber());
        assertEquals(0, card.getCvv());
        assertEquals(0, card.getDate());
    }
}