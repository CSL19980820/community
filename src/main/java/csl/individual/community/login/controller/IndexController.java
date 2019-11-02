package csl.individual.community.login.controller;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.login.model.User;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.login.service.ILogService;
import csl.individual.community.login.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 主页调用的controller
 */
@Controller
public class IndexController {
    @Autowired
    private IndexService indexService;

    @Autowired
    private ILogService logService;

    private   String registerSrc = "register_Login/";

    /**
     * 主页面
     * @param request
     * @param account
     * @return
     */
    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        @CookieValue(name = "account",defaultValue = "")String account){
        if(account.trim() != "" && account != null && account.length() >0){
            User user1 = new User();
            user1.setAccountId(account);
            UserUtil.user = indexService.selectOne(user1);
            if(UserUtil.user != null){
                request.getSession().setAttribute("user",UserUtil.user);
            }
        }
        return "index";
    }

    /**
     * 退出
     * @param request
     * @param account
     * @param response
     * @return
     */
    @GetMapping("/quit")
    public String destroyCookie(HttpServletRequest request, @CookieValue(name = "account",defaultValue = "")String account
            , HttpServletResponse response){
        if(account.trim() != "" && account != null && account.length() >0){
            Cookie cookie = new Cookie("account","");
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        request.getSession().invalidate();
        return "redirect:/";
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public String toRegister(HttpServletRequest request
            , HttpServletResponse response, User user,
                             @RequestParam(name = "code") String code){
        if(UserUtil.isNull(user.getName()) || UserUtil.isNull(user.getEmail()) || UserUtil.isNull(user.getPassword()) || UserUtil.isNull(code)){
        }
        JSONObject json = logService.findCode(code,user.getEmail());
        if((Boolean)json.get("valid") == false)
        {
        }else{
            String  accountId = indexService.toRegister(user);
            if(accountId.length() > 0 ){
                Cookie cookie = new Cookie("account",accountId);
                cookie.setPath("/");
                cookie.setMaxAge(1296000);
                response.addCookie(cookie);
            }
        }

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/getEmail")
    public JSONObject getEmail(@RequestParam(name = "email") String email){
        return indexService.findEmail(email);
    }

    @ResponseBody
    @PostMapping("/contrastCode")
    public JSONObject contrastCode(@RequestParam(name = "code") String code,@RequestParam(name = "email") String email){
        return logService.findCode(code,email);
    }
}
