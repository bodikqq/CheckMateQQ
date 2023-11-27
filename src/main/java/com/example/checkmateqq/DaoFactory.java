package com.example.checkmateqq;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
    INSTANCE;

    private UserDao userDao;
    private TestDao testDao;
    private ShiftDao shiftDao;
    private JdbcTemplate jdbcTemplate;

    private JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("root");
            dataSource.setPassword("heslo");
            dataSource.setUrl("jdbc:mysql://localhost:3306/checkmate");
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    public UserDao getUserDao() {
        if (userDao == null)
            userDao = new MysqlUserDao(getJdbcTemplate());
        return userDao;
    }

    public ShiftDao getSubjectDao() {
        if (shiftDao == null)
            shiftDao = (ShiftDao) new MysqlShiftDao(getJdbcTemplate());
        return shiftDao;
    }

    public TestDao getAttendanceDao() {
        if (testDao == null)
            testDao = (TestDao) new MysqlTestDao(getJdbcTemplate());
        return testDao;
    }

}
