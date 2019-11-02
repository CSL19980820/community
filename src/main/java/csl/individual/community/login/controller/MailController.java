package csl.individual.community.login.controller;

import csl.individual.community.login.entity.LogEntity;
import csl.individual.community.login.providerUtil.UserUtil;
import csl.individual.community.login.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tk.mybatis.mapper.entity.Example;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2019/10/31 16:48
 */
@Controller
public class MailController {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    protected TemplateEngine thymeleaf;
    @Value("${spring.mail.username}")
    private String mailName;

    @Autowired
    private ILogService logService;

    private static MailController mailController = new MailController();

    private static String code = UserUtil.getEmailCode();

    /**
     * 发送邮件
     * @param email
     * @param title
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/send/mail")
    public String  sendEmail(@RequestParam(name = "email")String email, @RequestParam(name="title")String title,
                             HttpServletRequest request) {
        try {
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            /**
             * 接收返回的code查询类型,yes:‘正常’,no:‘插入失败’,dayMore：‘当天验证码过多’,more:‘验证码30分钟内已发送’
             */
            String rtStr = mailController.codeValidatorFind(request,email,logService);
            if(rtStr.equals("yes")){
                message.setFrom(mailName);
                message.setTo(email);
                message.setSubject(title);
                Context context = new Context();
                context.setVariable("code",code);
                String text = thymeleaf.process("email",context);
                message.setText(text,true);
                this.mailSender.send(mimeMessage);
            }
            return rtStr;
        } catch (Exception ex) {
            ex.printStackTrace();

            return "no";
        }
    }

    public String codeValidatorSet(HttpServletRequest request, String email,ILogService logService){
        LogEntity logEntity = new LogEntity();
        logEntity.setRegisterIp(UserUtil.getIpAddress(request));
        logEntity.setCode(code);
        logEntity.setRegisterEmail(email);
        logEntity.setGmtCreate(System.currentTimeMillis());
        logEntity.setGmtModified(logEntity.getGmtCreate());
        logEntity.setType(UserUtil.registerType);
        int i = logService.insert(logEntity);
        if(i > 0){
            return "yes";
        }
        return "no";
    }

    /**
     * 查询该ip或邮箱一天注册量
     * @param request
     * @param email
     * @param logService
     * @return
     */
    public String codeValidatorFind(HttpServletRequest request,String email,ILogService logService){
        String str ="no";
        Example example = new Example(LogEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("registerEmail",email);
        criteria.andEqualTo("type",UserUtil.registerType);
        example.setOrderByClause("gmt_Create desc");
        List<LogEntity> logEntities = logService.selectByExample(example);//查询验证码数量
        LogEntity logEntity = new LogEntity();
        logEntity.setRegisterIp(UserUtil.getIpAddress(request));
        int ipNum = logService.selectCount(logEntity);
        int listNum = logEntities.size();
        if( listNum > 0 ){
            Long time = (System.currentTimeMillis() - logEntities.get(0).getGmtCreate())/ 1000 / 60;
            if(time <= 30l){
                str = "more";
            }
            if(listNum >= 3){
                str = "dayMore";
            }

        }else if(listNum == 0){
            str = mailController.codeValidatorSet(request,email,logService);
        }
        if(ipNum >= 10){
            str = "ipMore";
        }
        return str;
    }


}

