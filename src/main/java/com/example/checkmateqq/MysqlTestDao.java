package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
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
                Date date = rs.getDate("date");
                int test_type = rs.getInt("test_type");
                int pacient_id = rs.getInt("pacient_id");
                int shift_id = rs.getInt("shift_id");
                Test test = new Test(id,result,date,pacient_id,shift_id,time,test_type);
                return test;
            }
        };
    }
    @Override
    public void save(Test test) throws EntityNotFoundException {
        String query = "INSERT INTO test (time,pacient_id,date,test_type,shift_id) "
                + "VALUES (?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setTime(1, test.getTime());
                statement.setInt(2, test.getPatient_id());
                statement.setDate(3, test.getDate());
                statement.setInt(4, test.getTest_type());
                statement.setInt(5, test.getShift_id());

                return statement;
            }
        }, keyHolder);
    }
    @Override
    public List<Test> getAllUserTests(int user_id) {
        String sql = "SELECT * FROM test WHERE pacient_id = " + user_id;
        return jdbcTemplate.query(sql, testRM());
    }
    @Override
    public int testsOnTime(Time time, Date date) {
        String sql = "SELECT COUNT(*) FROM test WHERE time = ? AND date = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, time, date);
}
    @Override

    public Test getTestById(long testId) {
        String sql = "SELECT * FROM test WHERE id = ?";
        List<Test> result = jdbcTemplate.query(sql, testRM(), testId);

        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);

    }
    @Override
    public void updateTestResultById(long testId, int newResult) throws EntityNotFoundException {
        String sql = "UPDATE test SET result = ? WHERE id = ?";

        int rowsAffected = jdbcTemplate.update(sql, newResult, testId);

        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Test with ID " + testId + " not found.");
        }
    }

    @Override
    public Test getUsersPCRTest(int userId) {
        String sql = "SELECT * FROM test WHERE pacient_id = ? AND test_type = 0 LIMIT 1";
        List<Test> result = jdbcTemplate.query(sql, testRM(), userId);

        return result.isEmpty() ? null : result.get(0);
    }
    @Override
    public Test getUsersNAATsTest(int userId) {
        String sql = "SELECT * FROM test WHERE pacient_id = ? AND test_type = 1 LIMIT 1";
        List<Test> result = jdbcTemplate.query(sql, testRM(), userId);

        return result.isEmpty() ? null : result.get(0);
    }
}
