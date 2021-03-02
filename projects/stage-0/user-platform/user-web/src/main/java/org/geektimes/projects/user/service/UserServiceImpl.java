package org.geektimes.projects.user.service;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.sql.DBConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author ajin
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl() {
        DBConnectionManager connectionManager = new DBConnectionManager();
        String databaseURL = "jdbc:derby:/db/user-platform;create=true";
        try {
            Connection connection = DriverManager.getConnection(databaseURL);
            connectionManager.setConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userRepository = new DatabaseUserRepository(connectionManager);
    }

    /**
     * 注册用户
     */
    @Override
    public boolean register(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return null;
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return null;
    }
}
