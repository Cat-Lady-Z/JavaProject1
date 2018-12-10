package cskaoyan.java11prj.util;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User:  张娅迪
 * Date: 2018/11/14
 * Time: 下午 10:25
 * Detail requirement:
 * Method:
 */
public class SendEmailUtil {
    /**
     *@Description:
     *@Param: email是要发送的email地址
     *         emailMsg表示要发送的邮件内容
     *@return:
     *@Author: yadi.zhang
     *@date:
     */
    public static boolean sendEmail(String email, String emailMsg){
        String from = "wdandroid@163.com";   //邮件发送人的邮件地址
        String to = email;
        final String username = "wdandroid@163.com";
        final String password = "lanzhao1234";

        //定义Properties对象,设置环境信息
        Properties properties = System.getProperties();

        //设置邮件服务器的地址
        properties.setProperty("mail.smtp.host", "smtp.163.com"); // 指定的smtp服务器
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");//设置发送邮件使用的协议  smtp

        //创建Session对象,session对象表示整个邮件的环境信息
        Session session = Session.getInstance(properties);
        //设置输出调试信息
        session.setDebug(true);
        try {
            //Message的实例对象表示一封电子邮件
            //text/HTML
            //img/jpeg
            MimeMessage message = new MimeMessage(session);
            //设置发件人的地址
            message.setFrom(new InternetAddress(from));
            //设置主题
            message.setSubject("webstore注册用户激活");
            //设置邮件的文本内容
            //message.setText("Welcome to JavaMail World!");
            message.setContent((emailMsg),"text/html;charset=utf-8");
            //从session的环境中获取发送邮件的对象
            Transport transport=session.getTransport();
            //连接邮件服务器
            transport.connect("smtp.163.com",25, username, password);
            //设置收件人地址,并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(to)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}