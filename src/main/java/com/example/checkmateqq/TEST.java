package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Station;
import com.example.checkmateqq.triedy.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TEST {
    static private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    static private TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    static private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    static private StationDao stationDao = DaoFactory.INSTANCE.getStationDao();
    static public void testGetById() {

        int existingUserId = 10;

        try {
            List<Test> tests = testDao.getAllUserTests(12);
            for (Test test: tests) {
                System.out.println(test.getDate());
            }
//            try {
//                System.out.println(userDao.checkIfLoginExist("john.doe"));
//                //System.out.println(user.getLogin());
//            }catch (Exception e){
//                System.out.println("error: " + e + "    probably user with this id doesnt exist");
//            }

         //   userDao.save(user);
            // Assert that the returned user is not null
            //assertNotNull(user);

            // Add more assertions based on the expected values of the user
            //assertEquals(existingUserId, user.getId());
            // Add more assertions for other user properties

        } catch (Exception e) {
            System.out.println(e);
            // If the user is not found, fail the test
           // fail("User with ID " + existingUserId + " not found");
        }
    }

    public static void main(String[] args) throws EntityNotFoundException {
        testGetById();
//        System.out.println(userDao.checkIfLoginExist("vangel"));
//        System.out.println(userDao.checkIfUserExists("ahoj","nic"));
        for(Station station : stationDao.getAll()){
            System.out.println(station.toString( ));
        }

    }
}


