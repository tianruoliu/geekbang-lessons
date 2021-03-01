package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 用户注册逻辑处理{@link PageController}
 *
 * @author ajin
 */
@Path("/register")
public class UserRegisterController implements PageController {

    private  UserService userService;

    /**
     * 使用SPI机制注入UserService实现类
     */
    public UserRegisterController() {
        ServiceLoader<UserService> userServiceServiceLoader = ServiceLoader.load(UserService.class);

        Iterator<UserService> iterator = userServiceServiceLoader.iterator();
        try {
            while (iterator.hasNext()) {
                UserService userService = iterator.next();
                if (null != userService) {
                    this.userService = userService;
                    break;
                }
            }
        } catch (Throwable t) {

        }
    }


    /**
     * 1. 保存用户注册信息
     * 2. 跳转注册成功页面
     */
    @POST
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);

        boolean register = userService.register(user);
        if (register) {
            return "registerSuccess";
        }
        throw new RuntimeException("注册失败，请联系管理员");
    }
}
