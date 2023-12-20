package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Code;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlCodeDAO implements CodeDAO{
    private JdbcTemplate jdbcTemplate;
    public MysqlCodeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    RowMapper<Code> CodeRM() {
        return new RowMapper<Code>() {

            @Override
            public Code mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String code_string = rs.getString("workercode");
                Boolean is_active = rs.getBoolean("isActive");
                Code code = new Code(id,code_string,is_active);
                return code;
            }
        };
    }
    @Override
    public void addNewWorkerCode(String newCode) {
        String sql = "INSERT INTO worker_codes (workercode, isActive) VALUES (?, true)";
        jdbcTemplate.update(sql, newCode);
    }

    @Override
    public void disableWorkerCode(String worker_code) {
        String sql = "UPDATE worker_codes SET isActive = false WHERE workercode = ?";
        jdbcTemplate.update(sql, worker_code);
    }


}
