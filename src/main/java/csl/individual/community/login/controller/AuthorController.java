package csl.individual.community.login.controller;

import csl.individual.community.login.model.AccessModel;
import csl.individual.community.login.model.GitHubUser;
import csl.individual.community.login.model.User;
import csl.individual.community.login.providerUtil.GitHubUtil;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.login.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * github??Controller
 */
@Controller
public class AuthorController {

    @Autowired
    private GitHubUtil gitHubUtil;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.uri}")
    private String clientUri;

    @Autowired
    private IndexService indexService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessModel accessModel = new AccessModel();
        accessModel.setCode(code);
        accessModel.setState(state);
        accessModel.setRedirect_uri(clientUri);
        accessModel.setClient_id(clientId);
        accessModel.setClient_secret(clientSecret);
        String token = gitHubUtil.getAccessToken(accessModel);
        GitHubUser gitHubUser = gitHubUtil.getUser(token);
        if(gitHubUser != null){
            User user = new User();
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(String.valueOf(gitHubUser.getName()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(gitHubUser.getBio());
            user.setSource(UserUtil.githubLogin);
            user.setPassword(UserUtil.userPassword);
            User user1 = indexService.selectOne(user);
            if(user1 == null){
                indexService.insert(user);
            }else{
                user = user1;
            }
            Cookie cookie = new Cookie("account",user.getAccountId());
            cookie.setMaxAge(1296000);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:/";
        }
        else {
            return "redirect:/";
        }

    }


}
