package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ShiftDao {

    void createShiftIfItDoesentExist(int ShiftID, Date date);
      Shift getShiftByDate(Date date);
}
