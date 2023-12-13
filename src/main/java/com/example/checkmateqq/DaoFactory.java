package com.example.checkmateqq;

import com.example.checkmateqq.triedy.MysqlUhsDao;
import com.example.checkmateqq.triedy.Uhs;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
    INSTANCE;

    private UserDao userDao;
    private TestDao testDao;
    private ShiftDao shiftDao;
    private StationDao stationDao;
    private UhsDao uhsDao;
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

    public ShiftDao getShiftDao() {
        if (shiftDao == null)
            shiftDao = (ShiftDao) new MysqlShiftDao(getJdbcTemplate());
        return shiftDao;
    }

    public TestDao getTestDao() {
        if (testDao == null)
            testDao = (TestDao) new MysqlTestDao(getJdbcTemplate());
        return testDao;
    }
    public StationDao getStationDao() {
        if (stationDao == null)
            stationDao = (StationDao) new MysqlStationDao(getJdbcTemplate());
        return stationDao;
    }
    public UhsDao getUhsDao(){
        if(uhsDao == null)
            uhsDao = (UhsDao) new MysqlUhsDao(getJdbcTemplate());
        return uhsDao;
    }

}
