package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MysqlStationDaoTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MysqlStationDao stationDao;

    @Test
    public void testGetAllStations() {
        // Mocking the behavior of jdbcTemplate.query method
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(Collections.singletonList(new Station(1, "Town1", "Address1")));

        List<Station> stations = stationDao.getAll();
        assertNotNull(stations);
        assertEquals(1, stations.size());
    }

    @Test
    public void testGetStationById() {
        int stationId = 1;

        // Mocking the behavior of jdbcTemplate.query method
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(Collections.singletonList(new Station(stationId, "Town1", "Address1")));

        Station station = stationDao.getStationById(stationId);
        assertNotNull(station);
        assertEquals(stationId, station.getId());
    }

    @Test
    public void testCreateStation() {
        Station newStation = new Station(1, "NewTown", "NewAddress");

        assertDoesNotThrow(() -> stationDao.createStation(newStation));
    }

    @Test
    public void testDeleteStation() {
        int stationIdToDelete = 1;

        // Mocking the behavior of jdbcTemplate.update method
        when(jdbcTemplate.update(anyString(), anyInt()))
                .thenReturn(1); // Assuming one row was affected

        assertDoesNotThrow(() -> stationDao.deleteStation(stationIdToDelete));
    }
}
