package org.geektimes.projects.user.service;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.UserRepository;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author ajin
 */
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl() {
        ServiceLoader<UserRepository> userServiceServiceLoader = ServiceLoader.load(UserRepository.class);

        Iterator<UserRepository> iterator = userServiceServiceLoader.iterator();
        try {
            while (iterator.hasNext()) {
                UserRepository userRepository = iterator.next();
                if (null != userRepository) {
                    this.userRepository = userRepository;
                    break;
                }
            }
        } catch (Throwable t) {

        }
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
