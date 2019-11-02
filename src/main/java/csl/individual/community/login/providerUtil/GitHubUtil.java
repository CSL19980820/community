package csl.individual.community.login.providerUtil;

import com.alibaba.fastjson.JSON;
import csl.individual.community.login.model.AccessModel;
import csl.individual.community.login.model.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

/**
 * gitHub登录调用
 */
@Component
public class GitHubUtil {

    /**
     * 获取可以获取github信息的token
     * @param accessModel
     * @return
     */
    public String getAccessToken(AccessModel accessModel)  {

        MediaType json = MediaType.get("application/json; charset=utf-8");
        String url ="https://github.com/login/oauth/access_token";

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(json, com.alibaba.fastjson.JSON.toJSONString(accessModel));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str =response.body().string();
            return str;
        }catch (Exception e){}
        return null;
    }

    /**
     * 根据token获取gitHub用户信息
     * @param accessToken
     * @return
     */
    public GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://api.github.com/user?"+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                GitHubUser gitHubUser = JSON.parseObject(string, GitHubUser.class);
                return gitHubUser;
            }catch (Exception e){

            }

        return null;
    }
}
