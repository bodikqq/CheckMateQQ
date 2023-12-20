package com.example.checkmateqq;

import com.example.checkmateqq.EntityNotFoundException;
import com.example.checkmateqq.triedy.User;
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
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MysqlUserDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlUserDao userDao;

    @Test
    public void testSaveUser() {
        User user = new User(1, "John", "Doe", "john.doe", "password", true, false);

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class)))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> userDao.save(user));
    }

    @Test
    public void testGetUserById() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class)))
                .thenReturn(new User(userId, "John", "Doe", "john.doe", "password", true, false));

        assertDoesNotThrow(() -> {
            User user = userDao.getById(userId);
            assertNotNull(user);
            assertEquals(userId, user.getId());
        });
    }

    @Test
    public void testCheckIfLoginExist() {
        String login = "john.doe";

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyString()))
                .thenReturn(1); // Assuming login exists

        assertTrue(userDao.checkIfLoginExist(login));
    }

    @Test
    public void testCheckIfWorkerCodeIsReal() throws EntityNotFoundException {
        String code = "123";

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyString()))
                .thenReturn(1); // Assuming code exists

        assertTrue(userDao.checkIfWorkerCodeIsReal(code));
    }

    @Test
    public void testCheckIfUserExists() {
        String login = "john.doe";
        String password = "password";

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyString(), anyString()))
                .thenReturn(1); // Assuming user exists

        assertTrue(userDao.checkIfUserExists(login, password));
    }

    @Test
    public void testGetUserByLoginAndPassword() {
        String login = "john.doe";
        String password = "password";

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), anyString(), anyString()))
                .thenReturn(2);

        User user = userDao.getUserByLoginAndPassword(login, password);
        assertEquals(login, user.getLogin());
    }


    @Test
    public void testWorkersOnTime() {
        Date date = new Date();
        int stationId = 1;
        boolean isFirst = true;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt(), any(Date.class), anyBoolean()))
                .thenReturn(3); // Assuming 3 workers on time

        assertEquals(3, userDao.workersOnTime(date, stationId, isFirst));
    }

    @Test
    public void testIsUserEmployee() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt()))
                .thenReturn(true); // Assuming the user is an employee

        assertTrue(userDao.isUserEmployee(userId));
    }

    @Test
    public void testReturnEmployees() {
        // Mocking the behavior of jdbcTemplate.query method
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(new User(1, "John", "Doe", "john.doe", "password", true, false)));

        List<User> employees = userDao.returnEmployees();
        assertNotNull(employees);
        assertEquals(1, employees.size());
    }

    @Test
    public void testDeleteUserById() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyInt()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> userDao.deleteUserById(userId));
    }

    @Test
    public void testCheckIfUserExistsById() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt()))
                .thenReturn(1); // Assuming user exists

        assertTrue(userDao.checkIfUserExistsById(userId));
    }

    @Test
    public void testHasUpcomingShifts() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt(), any(Date.class)))
                .thenReturn(1); // Assuming user has upcoming shifts

        assertTrue(userDao.hasUpcomingShifts(userId));
    }

    @Test
    public void testSearchUserByNameSurnameOrId() {
        String nameSurnameOrId = "John";

        // Mocking the behavior of jdbcTemplate.query method
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString(), anyString(), anyString()))
                .thenReturn(Collections.singletonList(new User(1, "John", "Doe", "john.doe", "password", true, false)));

        List<User> result = userDao.searchUserByNameSurnameOrId(nameSurnameOrId);
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
