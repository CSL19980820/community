package csl.individual.community.postText.controller;

import com.alibaba.fastjson.JSONObject;
import csl.individual.community.login.service.IndexService;
import csl.individual.community.postText.model.PostText;
import csl.individual.community.postText.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 帖子数据交互层
 */
@Controller
public class PublishController {

    private static String src ="postText/";
    @Autowired
    private PublishService publishService;

    @Autowired
    private IndexService indexService;

    @GetMapping("/publish")
    public String publish(){
        return src+"publish";
    }

    /**
     * 提交数据
     * @param postText
     * @param request
     * @param
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(PostText postText, HttpServletRequest request){

        publishService.create(postText);
        return "redirect:"+"/publish";
    }


    /**
     * 获取同类型下的标题是否重复
     * @param type
     * @param title
     * @return
     */
    @PostMapping("/getTitle")
    @ResponseBody
    public JSONObject getTitle(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "title") String title){
        return  publishService.findTitle(title, type);
    }
}
