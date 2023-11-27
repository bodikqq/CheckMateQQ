package com.example.checkmateqq;
import com.example.checkmateqq.triedy.User;

public class TEST {
    static private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
    static public void testGetById() {
        // Create an instance of your MysqlUserDao with the test JdbcTemplate
        //MysqlUserDao userDao = new MysqlUserDao(testJdbcTemplate);

        // Assuming there is an existing user with ID 1 in your test database
        int existingUserId = 1;

        try {
            // Call the getById method
            User user = userDao.getById(existingUserId);
            userDao.save(user);
            // Assert that the returned user is not null
            //assertNotNull(user);
            System.out.println(user.getSurname());
            // Add more assertions based on the expected values of the user
            //assertEquals(existingUserId, user.getId());
            // Add more assertions for other user properties

        } catch (EntityNotFoundException e) {
            // If the user is not found, fail the test
           // fail("User with ID " + existingUserId + " not found");
        }
    }

    public static void main(String[] args) {
        testGetById();
    }
}
