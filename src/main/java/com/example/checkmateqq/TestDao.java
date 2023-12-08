package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Test;

import java.util.List;

public interface TestDao {
    List<Test> getAllUserTests(int user_id) throws EntityNotFoundException;
}
