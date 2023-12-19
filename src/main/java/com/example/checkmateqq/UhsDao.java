package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Uhs;

import java.util.List;

public abstract class UhsDao {
    public abstract void createUhsIfDoesntExist(int userId, int shiftId);

    public abstract int numberOfShiftsWorked(int userId);

    public abstract Uhs getUhsByShiftId(int shiftId, int userId);

    public abstract void deleteUhsByShiftIdAndUserId(int shiftId, int userId);

    public abstract List<Uhs> getAllUhs();
}
