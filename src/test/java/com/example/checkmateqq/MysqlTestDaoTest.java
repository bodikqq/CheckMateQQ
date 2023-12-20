package com.example.checkmateqq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



class MysqlTestDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlTestDao testDao;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        com.example.checkmateqq.triedy.Test test = new com.example.checkmateqq.triedy.Test(1, 1, Date.valueOf("2023-01-01"), 0, 1, Time.valueOf("12:00:00"),2);

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class)))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> testDao.save(test));
    }

    @Test
    void getAllUserTests() {
        int userId = 1;
        com.example.checkmateqq.triedy.Test test1 = new com.example.checkmateqq.triedy.Test(1, 1, Date.valueOf("2023-01-01"), 0, 1, Time.valueOf("12:00:00"),0);
        com.example.checkmateqq.triedy.Test test2 = new com.example.checkmateqq.triedy.Test(2, 1, Date.valueOf("2023-01-02"), 1, 2, Time.valueOf("14:00:00"),0);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Arrays.asList(test1, test2));

        List<com.example.checkmateqq.triedy.Test> userTests = testDao.getAllUserTests(userId);

        assertNotNull(userTests);
        assertEquals(2, userTests.size());
    }

    @Test
    void testsOnTime() {
        Time time = Time.valueOf("12:00:00");
        Date date = Date.valueOf("2023-01-01");

        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), any(Time.class), any(Date.class)))
                .thenReturn(2); // Assuming 2 tests on time

        int result = testDao.testsOnTime(time, date);

        assertEquals(2, result);
    }

    @Test
    void getTestById() {
        int testId = 1;
        com.example.checkmateqq.triedy.Test test = new com.example.checkmateqq.triedy.Test(testId, 1, Date.valueOf("2023-01-01"), 0, 1, Time.valueOf("12:00:00"),0);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyLong()))
                .thenReturn(Arrays.asList(test));

        com.example.checkmateqq.triedy.Test retrievedTest = testDao.getTestById(testId);

        assertNotNull(retrievedTest);
        assertEquals(testId, retrievedTest.getId());
    }

    @Test
    void updateTestResultById() {
        long testId = 1;
        int newResult = 2;

        when(jdbcTemplate.update(anyString(), anyInt(), anyLong()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> testDao.updateTestResultById(testId, newResult));
    }

    @Test
    void getUsersPCRTest() {
        int userId = 1;
        com.example.checkmateqq.triedy.Test pcrTest = new com.example.checkmateqq.triedy.Test(1, userId, Date.valueOf("2023-01-01"), 0, 1, Time.valueOf("12:00:00"),0);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(Arrays.asList(pcrTest));

        com.example.checkmateqq.triedy.Test retrievedPCRTest = testDao.getUsersPCRTest(userId);

        assertNotNull(retrievedPCRTest);
        assertEquals(0, retrievedPCRTest.getTest_type());
    }

    @Test
    void getUsersNAATsTest() {
        int userId = 1;
        com.example.checkmateqq.triedy.Test naatsTest = new com.example.checkmateqq.triedy.Test(1, userId, Date.valueOf("2023-01-01"), 1, 1, Time.valueOf("12:00:00"),1);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(Arrays.asList(naatsTest));

        com.example.checkmateqq.triedy.Test retrievedNAATsTest = testDao.getUsersNAATsTest(userId);

        assertNotNull(retrievedNAATsTest);
        assertEquals(1, retrievedNAATsTest.getTest_type());
    }
}
