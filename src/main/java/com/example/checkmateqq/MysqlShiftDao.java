package com.example.checkmateqq;

import java.sql.*;
import java.util.Date;
import java.util.List;

import com.example.checkmateqq.triedy.Shift;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

public class MysqlShiftDao implements ShiftDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlShiftDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Shift> shiftRM() {
        return new RowMapper<Shift>() {

            @Override
            public Shift mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                int station_id = rs.getInt("station_id");

                Date date = rs.getDate("date");
                boolean isFirst = rs.getBoolean("is_first");


                Shift shift = new Shift(id, station_id,date,isFirst);
                return shift;
            }
        };
    }
    @Override
    public void createShiftIfItDoesentExist(int ShiftID, Date date, boolean isFirst){
        String sql1 = "select count(*) from shift where date = ? and is_first = ?";
        int result = jdbcTemplate.queryForObject(sql1,Integer.class,date,isFirst);
        if(result >= 1) return;
        String sql = "insert into shift values(null,?,?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, ShiftID);
                statement.setDate(2, sqlDate);
                statement.setBoolean(3,isFirst);
                return statement;
            }
        });

    }



    @Override
    public Shift getShiftByDateAndIsFirst(Date date, boolean isFirst) {
        String sql = "SELECT * FROM shift WHERE date = ? AND is_first = ?";
        List<Shift> shifts = jdbcTemplate.query(sql, shiftRM(), date, isFirst);
        if(shifts.size() == 0)return null;
        return shifts.get(0);
    }

    @Override
    public List<Shift> getFutureShiftsForUser(int userId) {
        String sql = "SELECT s.* FROM shift s " +
                "JOIN user_has_shift uhs ON s.id = uhs.shift_id " +
                "WHERE uhs.user_id = ? AND s.date >= CURDATE()";

        List<Shift> futureShifts = jdbcTemplate.query(sql, shiftRM(), userId);

        return futureShifts;
    }

}
