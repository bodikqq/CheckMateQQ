package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;

import java.util.List;

public interface StationDao {
    List<Station> getAll();

    Station getStationById(int stationId);

    void createStation(Station station);

    void deleteStation(int stationId);
}
