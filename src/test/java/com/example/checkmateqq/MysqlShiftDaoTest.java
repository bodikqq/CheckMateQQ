package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.RowMapper;



import java.sql.*;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MysqlShiftDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlShiftDao shiftDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createShiftIfItDoesntExist_ShouldCreateShiftWhenNotExist() {
        int shiftId = 1;
        Date date = Date.valueOf("2023-01-01");
        boolean isFirst = true;

        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), any(Date.class), anyBoolean()))
                .thenReturn(0); // Assuming no shift exists

        assertDoesNotThrow(() -> shiftDao.createShiftIfItDoesentExist(shiftId, date, isFirst));

        verify(jdbcTemplate, times(1)).update(any(PreparedStatementCreator.class));
    }

    @Test
    void createShiftIfItDoesntExist_ShouldNotCreateShiftWhenExists() {
        int shiftId = 1;
        Date date = Date.valueOf("2023-01-01");
        boolean isFirst = true;

        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), any(Date.class), anyBoolean()))
                .thenReturn(1); // Assuming one shift exists

        assertDoesNotThrow(() -> shiftDao.createShiftIfItDoesentExist(shiftId, date, isFirst));

        verify(jdbcTemplate, times(0)).update(any(PreparedStatementCreator.class));
    }

    @Test
    void getShiftByDateAndIsFirst() {
        Date date = Date.valueOf("2023-01-01");
        boolean isFirst = true;
        Shift shift = new Shift(1, 1, date, isFirst);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), any(Date.class), anyBoolean()))
                .thenReturn(Arrays.asList(shift));

        Shift retrievedShift = shiftDao.getShiftByDateAndIsFirst(date, isFirst);

        assertNotNull(retrievedShift);
        assertEquals(date, retrievedShift.getDate());
        assertTrue(retrievedShift.isFirst());
    }

    @Test
    void getFutureShiftsForUser() {
        int userId = 1;
        Shift futureShift1 = new Shift(1, 1, Date.valueOf("2023-01-01"), true);
        Shift futureShift2 = new Shift(2, 2, Date.valueOf("2023-01-02"), false);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(Arrays.asList(futureShift1, futureShift2));

        List<Shift> futureShifts = shiftDao.getFutureShiftsForUser(userId);

        assertNotNull(futureShifts);
        assertEquals(2, futureShifts.size());
    }

    // Add similar tests for getPastShiftsForUser, getShiftByShiftID, etc.

}
