package com.example.checkmateqq;
import com.example.checkmateqq.triedy.User;

public class TEST {
    static private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    static public void testGetById() {
        int existingUserId = 10;

        try {
            System.out.println( userDao.checkIfWorkerCodeIsReal("12qwert"));
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
        System.out.println(userDao.checkIfLoginExist("vangel"));
        System.out.println(userDao.checkIfUserExists("ahoj","nic"));
    }
}
