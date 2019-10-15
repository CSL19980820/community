package csl.individual.community.providerUtil;

import com.alibaba.fastjson.JSON;
import csl.individual.community.model.AccessModel;
import csl.individual.community.model.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class GitHubUtil {

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
