package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
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
    @Override
    public Station getStationById(int stationId) {
        String sql = "SELECT * FROM station WHERE id = ?";
        List<Station> stations = jdbcTemplate.query(sql, stationRM(), stationId);

        if (stations.isEmpty()) {
            return null; // No station found with the given ID
        }
        return stations.get(0);
    }
    @Override
    public void createStation(Station station) {
        String sql = "INSERT INTO station (town, address) VALUES (?, ?)";
        jdbcTemplate.update(sql, station.getTown(), station.getAddress());
    }
    @Override
    public void deleteStation(int stationId) {
        String sql = "DELETE FROM station WHERE id = ?";
        System.out.println("qqqq");
        jdbcTemplate.update(sql, stationId);
    }


}
