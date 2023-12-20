package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Code;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MysqlCodeDAOTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlCodeDAO codeDAO;

    @Test
    public void testAddNewWorkerCode() {
        String newCode = "123";

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyString()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> codeDAO.addNewWorkerCode(newCode));
    }

    @Test
    public void testDisableWorkerCode() {
        String workerCode = "123";

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyString()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> codeDAO.disableWorkerCode(workerCode));
    }

    @Test
    public void testCodeRM() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("workercode")).thenReturn("123");
        when(resultSet.getBoolean("isActive")).thenReturn(true);

        RowMapper<Code> codeRM = codeDAO.CodeRM();
        Code code = codeRM.mapRow(resultSet, 1);

        assertNotNull(code);
        assertEquals(1, code.getId());
        assertEquals("123", code.toString());
        assertTrue(code.getIs_active());
    }

    @Test
    public void testCodeRMWithNullValues() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("workercode")).thenReturn(null);
        when(resultSet.getBoolean("isActive")).thenReturn(false);

        RowMapper<Code> codeRM = codeDAO.CodeRM();
        Code code = codeRM.mapRow(resultSet, 1);

        assertNotNull(code);
        assertEquals(1, code.getId());
        assertNull(code.toString());
        assertFalse(code.getIs_active());
    }

}
