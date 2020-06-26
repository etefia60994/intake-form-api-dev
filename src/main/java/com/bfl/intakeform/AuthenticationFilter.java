package com.bfl.intakeform;


import com.bfl.intakeform.controllers.HomeController;
import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.repository.CaseManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    CaseManagerRepository caseManagerRepository;

    @Autowired
    HomeController homeController;
    private static final List<String> whitelist = Arrays.asList("/api/login", "/api/registerManager", "/logout","/css/log.css","/logo.png");
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // Don't require sign-in for whitelisted pages
        if (isWhitelisted(request.getRequestURI())) {
            System.out.println("success");
            // returning true indicates that the request may proceed
            return true;
        }

        HttpSession session = request.getSession();
        CaseManager user = homeController.getUserFromSession(session);
        Integer userId = (Integer) session.getAttribute("caseManagerUser");
        // The user is logged in
        if (user != null) {
            return true;
        }
        System.out.println(userId);
        System.out.println("fail");
        // The user is NOT logged in
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      // response.sendRedirect("/api/login");
        return false;
    }
    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

}