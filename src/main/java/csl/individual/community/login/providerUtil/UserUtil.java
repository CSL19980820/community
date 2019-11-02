package csl.individual.community.login.providerUtil;

import csl.individual.community.login.model.User;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * 用户登录需要的一些常量和方法
 */
public class UserUtil {

     //设置全局登录用户信息
    public static User user = new User();
    /**
     * 用户来源
     */
    public final static String  githubLogin = "github";
    public final static String  tencentLogin = "tencent";
    public final static String  userLogin = "user";
    public final static int loginType = 1;
    public final static int registerType = 0;
    //设置特殊来源用户默认密码
    public final static String  userPassword = putMd5Password("123456");

    /**
     * 用户账号随机生成
     * @return
     */
    public static String getUUId(){
        Integer hashCodeV = UUID.randomUUID().toString().hashCode();
        String  userId = "";
        Long longs = System.currentTimeMillis();
        if(hashCodeV <0){
            hashCodeV = -(hashCodeV);
        }
        userId = hashCodeV.toString();
        if(userId.toString().length()<10){
            String str =longs.toString().subSequence(6,7).toString();
            userId =  hashCodeV.toString()+str;
        }
        return userId;
    }

    /**
     * Md5加密
     * @param password
     * @return
     */
    public static String putMd5Password(String password){
        String newPassWord = DigestUtils.md5DigestAsHex(password.getBytes());
        /**
         * 加密密码
         */
        return newPassWord;
    }

    public static Boolean isNull(String str){
        Boolean flag = false;
        if(str.trim() == "" || str.isEmpty() || str.length() < 1 || str == null){
            flag = true;
        }
        return flag;
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }
    /**
     * 获取验证码
     * @return
     */
    public static String getEmailCode(){
        //创建一个ArrayList集合，存放生成的代码
        List<Object> list = new ArrayList();
        //for循环生成6位验证码
        for(int i = 0;i<6;i++) {
            //if语句交替判断生成数字或者字母
            if(i%2==0) {
                int b = (int)(Math.random()*10);//生成0~9的数字
                list.add(b);
            }else {
                boolean flag = false;
                do {
                    int a = (int)(Math.random()*58+65);//生成大小写字母的ASCII码
                    //if排斥多余的中间其他字符
                    if(!(a>=91&&a<=95)) {
                        String s = (char)a+"";//数字转字母
                        list.add(s);
                        flag = true;
                    }
                }while(!flag);
            }
        }
        Collections.shuffle(list);//shuffle随机排序
        StringBuilder out = new StringBuilder();
        for(Object s : list) {
            out.append(s);
        }
        return out.toString();
    }
}
