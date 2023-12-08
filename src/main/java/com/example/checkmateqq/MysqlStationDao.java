package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlStationDao implements StationDao{
    private JdbcTemplate jdbcTemplate;

    public MysqlStationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Station> stationRM() {
        return new RowMapper<Station>() {

            @Override
            public Station mapRow(ResultSet rs, int rowNum) throws SQLException {
                int id = rs.getInt("id");
                String town = rs.getString("town");
                String address = rs.getString("address");

                Station station = new Station(id, town, address);
                return station;
            }
        };

    }

    @Override
    public List<Station> getAll() {
        String sql = "SELECT * from station";
        return jdbcTemplate.query(sql, stationRM());
    }
}
