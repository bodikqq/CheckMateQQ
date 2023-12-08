package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Test;
import com.example.checkmateqq.triedy.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class MysqlTestDao implements TestDao{
    private JdbcTemplate jdbcTemplate;
    public MysqlTestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Test> testRM() {
        return new RowMapper<Test>() {

            @Override
            public Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                Time time = rs.getTime("time");
                int result = rs.getInt("result");
                int pacient_id = rs.getInt("pacient_id");
                int shift_id = rs.getInt("shift_id");
                Date date = rs.getDate("date");
                int test_type = rs.getInt("test_type");
                Test test = new Test(id,result,date,pacient_id,shift_id,time,test_type);
                return test;
            }
        };
    }
    @Override
    public List<Test> getAllUserTests(int user_id) {
        String sql = "SELECT * FROM test WHERE pacient_id = " + user_id;
        return jdbcTemplate.query(sql, testRM());
    }

}
