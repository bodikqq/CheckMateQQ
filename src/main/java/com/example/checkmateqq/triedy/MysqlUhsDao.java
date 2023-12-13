package com.example.checkmateqq.triedy;

import com.example.checkmateqq.UhsDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;

public class MysqlUhsDao extends UhsDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlUhsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Uhs> uhsRM() {
        return new RowMapper<Uhs>() {

            @Override
            public Uhs mapRow(ResultSet rs, int rowNum) throws SQLException {
                int user_id = rs.getInt("user_id");
                int shift_id = rs.getInt("shift_id");
                Uhs uhs = new Uhs(user_id,shift_id);
                return uhs;
            }
        };
    }
    @Override
    public void createUhsIfDoesntExist(int userId, int shiftId) {
        // Check if the user_has_shift entry already exists
        String uhsCountQuery = "SELECT COUNT(*) FROM user_has_shift WHERE user_id = ? AND shift_id = ?";
        int uhsCount = jdbcTemplate.queryForObject(uhsCountQuery, Integer.class, userId, shiftId);

        if (uhsCount == 0) {
            // If the user_has_shift entry doesn't exist, create a new one
            String insertUhsQuery = "INSERT INTO user_has_shift (user_id, shift_id) VALUES (?, ?)";
            jdbcTemplate.update(insertUhsQuery, userId, shiftId);
        }
    }
}
