package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TestDao {
    void save(Test test) throws EntityNotFoundException;

    List<Test> getAllUserTests(int user_id) throws EntityNotFoundException;

    int testsOnTime(Time time, java.util.Date date);
}
