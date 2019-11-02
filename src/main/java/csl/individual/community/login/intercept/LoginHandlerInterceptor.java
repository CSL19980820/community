package csl.individual.community.login.intercept;

import csl.individual.community.login.model.User;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.login.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截未登录用户
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private IndexService indexService;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        User user = (User)session.getAttribute("user");
        String account = "";
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            cookies = new Cookie[0];
        };
        for (Cookie cook:cookies){
            if(cook.getName().equals("account")){
                account = cook.getValue();
            }
            continue;
        }
        if(account.trim() =="" && user == null){
            response.sendRedirect("/");
            return  false;
        }else if(account.trim() !="" && user == null){
            User user1 = new User();
            user1.setAccountId(account);
            UserUtil.user = indexService.selectOne(user1);
            if(UserUtil.user  != null){
                request.getSession().setAttribute("user",UserUtil.user);
            }

        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
