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


                Shift shift = new Shift(id, station_id,date);
                return shift;
            }
        };
    }
    @Override
    public void createShiftIfItDoesentExist(int ShiftID, Date date){
        String sql1 = "select count(*) from shift where date = ?";
        int result = jdbcTemplate.queryForObject(sql1,Integer.class,date);
        if(result >= 1) return;
        String sql = "insert into shift values(null,?,?)";
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, ShiftID);
                statement.setDate(2, sqlDate);
                return statement;
            }
        });

    }



    @Override
    public Shift getShiftByDate(Date date) {
        String sql = "SELECT * FROM shift WHERE date = ?";
        List<Shift> shifts = jdbcTemplate.query(sql, shiftRM(), date);
        if(shifts.size() == 0)return null;
        return shifts.get(0);
    }

}
