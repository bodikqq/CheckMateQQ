package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Uhs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MysqlUhsDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlUhsDao uhsDao;

    @Test
    public void testCreateUhsIfDoesntExist() {
        int userId = 1;
        int shiftId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt(), anyInt()))
                .thenReturn(0); // Assuming user_has_shift entry doesn't exist

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyInt(), anyInt()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> uhsDao.createUhsIfDoesntExist(userId, shiftId));
    }

    @Test
    public void testNumberOfShiftsWorked() {
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(Class.class), anyInt(), anyString()))
                .thenReturn(3); // Assuming 3 shifts worked

        int result = uhsDao.numberOfShiftsWorked(userId);
        assertEquals(3, result);
    }

    @Test
    public void testGetUhsByShiftId() {
        int shiftId = 1;
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.queryForObject method
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class)))
                .thenReturn(new Uhs(userId, shiftId));

        Uhs result = uhsDao.getUhsByShiftId(shiftId, userId);
        assertNotNull(result);
        assertEquals(userId, result.getUser_id());
        assertEquals(shiftId, result.getShift_id());
    }

    @Test
    public void testDeleteUhsByShiftIdAndUserId() {
        int shiftId = 1;
        int userId = 1;

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyInt(), anyInt()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> uhsDao.deleteUhsByShiftIdAndUserId(shiftId, userId));
    }

    @Test
    public void testGetAllUhs() {
        // Mocking the behavior of jdbcTemplate.query method
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(new Uhs(1, 1)));

        List<Uhs> result = uhsDao.getAllUhs();
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testUpdateIsConfirmed() {
        int userId = 1;
        int shiftId = 1;
        boolean isConfirmed = true;

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyBoolean(), anyInt(), anyInt()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> uhsDao.updateIsConfirmed(userId, shiftId, isConfirmed));
    }
}
