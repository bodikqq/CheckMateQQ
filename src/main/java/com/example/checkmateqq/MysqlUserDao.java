package com.example.checkmateqq;

import java.sql.*;
import java.util.Date;
import java.util.List;
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

    RowMapper<User> userRM() {
        return new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String login = rs.getString("login");
                String password = rs.getString("password");
                boolean isEmployee = rs.getBoolean("isEmployee");
                boolean isAdmin = rs.getBoolean("isAdmin");
                double balance = rs.getDouble("balance");
                int card_id = rs.getInt("card_id");
                User user = new User(id, name, surname, login, password, isEmployee, isAdmin,card_id,balance);
                return user;
            }
        };
    }

    @Override
    public User getById(int id) throws EntityNotFoundException {
        String sql = "SELECT * FROM user WHERE id = " + id;
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
    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        if(!checkIfUserExists(login,password)){
            return null;
        }
        String sql = "SELECT * FROM user WHERE login = ? AND password = ?";
        List<User> list = jdbcTemplate.query(sql, userRM(), login, password);
        if(list.isEmpty())return null;
        else return list.get(0);
    }
    @Override
    public int workersOnTime(Date date, int stationId, boolean isFirst) {
        String sql = "SELECT COUNT(DISTINCT u.id) AS number_of_workers FROM `user` u JOIN `user_has_shift` uhs ON u.id = uhs.user_id JOIN `shift` s ON uhs.shift_id = s.id WHERE s.station_id = ? AND s.date = ? AND is_first = ?";

        int qq = jdbcTemplate.queryForObject(sql, Integer.class, stationId,date, isFirst);
        return qq;
    }
    @Override
    public boolean isUserEmployee(int userId) {
        String sql = "SELECT isEmployee FROM user WHERE id = ?";
        Boolean isEmployee = jdbcTemplate.queryForObject(sql, Boolean.class, userId);

        return isEmployee != null && isEmployee;
    }
    @Override
    public List<User> returnEmployees() {
        String sql = "SELECT * FROM user WHERE isEmployee = true AND isAdmin = false";
        return jdbcTemplate.query(sql, userRM());
    }

    @Override
    public void deleteUserById(int userId) throws EntityNotFoundException {
        // Check if the user exists
        if (!checkIfUserExistsById(userId)) {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }

        // Delete user
        String sql = "DELETE FROM user WHERE id = ?";
        int count = jdbcTemplate.update(sql, userId);

        if (count == 0) {
            throw new EntityNotFoundException("Failed to delete user with ID " + userId);
        }
    }

    @Override
    public boolean checkIfUserExistsById(int userId) {
        String sql = "SELECT COUNT(*) FROM user WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            if(count == null) {
                return false;
            }
        return count > 0;
    }
    @Override
    public boolean hasUpcomingShifts(int userId) {
        // Get the current date
        java.util.Date currentDate = new java.util.Date();
        Date today = new Date(currentDate.getTime());

        // Query to check if the user has any upcoming shifts
        String sql = "SELECT COUNT(*) FROM shift s " +
                "JOIN user_has_shift uhs ON s.id = uhs.shift_id " +
                "WHERE uhs.user_id = ? AND s.date >= ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, today);

        return count > 0;
    }
    @Override
    public List<User> searchUserByNameSurnameOrId(String nameSurnameOrId) {
        String sql = "SELECT * " +
                "FROM user " +
                "WHERE (CONCAT(name, ' ', surname) LIKE ? OR CONCAT(surname, ' ', name) LIKE ? OR id = ?) " +
                "AND isEmployee = true AND isAdmin = false";

        String nameSurnameOrIdMod = "%" + nameSurnameOrId + "%";

        try {
            int userId = Integer.parseInt(nameSurnameOrId);
            return jdbcTemplate.query(sql, userRM(), nameSurnameOrIdMod, nameSurnameOrIdMod, userId);
        } catch (NumberFormatException e) {
            return jdbcTemplate.query(sql, userRM(), nameSurnameOrIdMod, nameSurnameOrIdMod, nameSurnameOrId);
        }
    }

}
