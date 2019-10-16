package csl.individual.community.controller;

import csl.individual.community.mapper.UserMapper;
import csl.individual.community.model.AccessModel;
import csl.individual.community.model.GitHubUser;
import csl.individual.community.model.User;
import csl.individual.community.providerUtil.GitHubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request){
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
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(String.valueOf(gitHubUser.getName()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            User user1 = userMapper.find(user);
            if(user1 == null){
                userMapper.insert(user);
            }else{
                user = user1;
            }
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }

    }
}
