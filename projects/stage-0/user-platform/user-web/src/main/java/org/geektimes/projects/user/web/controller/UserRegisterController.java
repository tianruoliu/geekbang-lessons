package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * 用户注册逻辑处理{@link PageController}
 *
 * @author ajin
 */
@Path("/register")
//@CommonController
public class UserRegisterController implements PageController {

    @Resource(name = "bean/UserService")
    private UserServiceImpl userService;


    public static final ThreadLocal<String> ERROR_MESSAGE_HOLDER = ThreadLocal.withInitial(() -> "");

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
            return "registerSuccess.jsp";
        }
        request.setAttribute("errorInfo", "注册失败:" + ERROR_MESSAGE_HOLDER.get());
        return "error.jsp";
//        throw new RuntimeException("注册失败，请联系管理员");
    }

    @Override
    public String toString() {
        return "UserRegisterController{" +
                "userService=" + userService +
                '}';
    }
}
