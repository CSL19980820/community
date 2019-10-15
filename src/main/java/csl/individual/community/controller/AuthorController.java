package csl.individual.community.controller;

import csl.individual.community.model.AccessModel;
import csl.individual.community.model.GitHubUser;
import csl.individual.community.providerUtil.GitHubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,@RequestParam(name="state")String state){
        AccessModel accessModel = new AccessModel();
        accessModel.setCode(code);
        accessModel.setState(state);
        accessModel.setRedirect_uri(clientUri);
        accessModel.setClient_id(clientId);
        accessModel.setClient_secret(clientSecret);
        String token = gitHubUtil.getAccessToken(accessModel);
        GitHubUser gitHubUser = gitHubUtil.getUser(token);

        return "index";
    }
}
