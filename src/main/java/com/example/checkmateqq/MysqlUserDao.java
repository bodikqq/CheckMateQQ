package com.example.checkmateqq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.example.checkmateqq.triedy.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class MysqlUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public MysqlUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRM() {
        return new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String login = rs.getString("login");
                String password = rs.getString("password");
                boolean isEmployee = rs.getBoolean("isEmployee");
                boolean isAdmin = rs.getBoolean("isEmployee");


                User user = new User(id, name, surname, login, password, isEmployee, isAdmin);
                return user;
            }
        };
    }

    @Override
    public User getById(int id) throws EntityNotFoundException {
        String sql = "SELECT id, name, surname, login, password, isEmployee, isAdmin FROM user WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, userRM());
    }




    @Override
    public void save(User user) throws EntityNotFoundException {
        Objects.requireNonNull(user, "Student cannot be null");
        Objects.requireNonNull(user.getSurname(),"Surname cannot be null");
        //INSERT
            String query = "INSERT INTO user (name, surname, login, password, isEmployee, isAdmin) "
                    + "VALUES (?,?,?,?,?,?)";
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, user.getName());
                    statement.setString(2, user.getSurname());
                    statement.setString(3, user.getLogin());
                    statement.setString(4, user.getPassword());
                    statement.setBoolean(5, user.isEmployee());
                    statement.setBoolean(6, user.isAdmin());
                    return statement;
                }
            }, keyHolder);
            int id = keyHolder.getKey().intValue();
            User saved = User.clone(user);
            saved.setId(id);
            //return saved;
    }
    @Override
    public boolean checkIfLoginExist(String login){
        String sql = "SELECT COUNT(*) FROM user WHERE login = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, login);

        if (count > 0) {
            System.out.println("User with login '" + login + "' already exists.");
            return true;
        }
        return false;
    }
    @Override
    public boolean checkIfWorkerCodeIsReal(String code) throws EntityNotFoundException {
        String sql = "select count(*) from worker_codes where workercode = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, code);

        if (count == 0) {
            System.out.println("code '" + code + "' doesnt exist.");
            return false;
        }
        return true;
    }
//    @Override
//    public void delete(long id) throws EntityNotFoundException {
//        String query = "DELETE FROM student WHERE id = ?";
//        int count = jdbcTemplate.update(query, id);
//        if (count == 0) {
//            throw new EntityNotFoundException(
//                    "Student with id " + id + " not found");
//        }
//    }
public boolean checkIfUserExists(String login, String password) {
    String sql = "SELECT COUNT(*) FROM user WHERE login = ? AND password = ?";
    int count = jdbcTemplate.queryForObject(sql, Integer.class, login, password);

    return count > 0;
}
}
