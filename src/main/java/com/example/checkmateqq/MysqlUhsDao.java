package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Uhs;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    @Override
    public int numberOfShiftsWorked(int userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date beginningOfMonth = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String beginningOfMonthStr = dateFormat.format(beginningOfMonth);


        String countShiftsQuery = "SELECT COUNT(*) FROM user_has_shift uhs " +
                "JOIN shift s ON uhs.shift_id = s.id " +
                "WHERE uhs.user_id = ? AND s.date >= ? AND s.date <= NOW()";

        // Execute the query and return the result
        return jdbcTemplate.queryForObject(countShiftsQuery, Integer.class, userId, beginningOfMonthStr);
    }
    @Override
    public Uhs getUhsByShiftId(int shiftId, int userId){
        String query = "Select * from user_has_shift where shift_id = " + shiftId + " and user_id = " + userId;
        return jdbcTemplate.queryForObject(query,uhsRM());

    }
}
