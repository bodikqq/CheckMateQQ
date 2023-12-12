package com.example.checkmateqq;

import com.example.checkmateqq.triedy.Shift;
import com.example.checkmateqq.triedy.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TEST {
    static private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    static private TestDao testDao = DaoFactory.INSTANCE.getTestDao();
    static private ShiftDao shiftDao = DaoFactory.INSTANCE.getShiftDao();
    static public void testGetById() {
        int existingUserId = 10;

        try {
            String dateString = "2023-12-09";
                // Define the date format of your input string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Parse the string and convert it to a java.util.Date object
                Date utilDate = dateFormat.parse(dateString);
            System.out.println(shiftDao.getShiftByDate(utilDate).getId());
            System.out.println("mmm");
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
    }
}
