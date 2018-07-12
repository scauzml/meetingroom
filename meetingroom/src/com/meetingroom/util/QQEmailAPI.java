package com.meetingroom.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class QQEmailAPI {
    /**
     * 所需参数
     * 1、发件人邮箱
     * 2、邮箱授权码
     * 3、收件人邮箱
     * 4、邮件主题
     * 5、正文
     * @return
     */
    private String senderEmail;
    private String accessKey;

    public String  init(String senderEmail,String accessKey){
        this.senderEmail=senderEmail;
        this.accessKey=accessKey;
        return "Init Successful";
    }

    public String sendToOne(String receiverEmail,String subject,String content) {
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.qq.com");
        props.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,accessKey);
            }
        };
        Session session = Session.getDefaultInstance(props, authenticator);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject(subject);
            //正文形如senderName: " <br/> content </br>
            String str =content;
            message.setContent(str, "text/html;charset=UTF-8");
            //发送
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "send to one Success";
    }

    public String sendToMany(List<String> receiversEmail, final String subject, String content){
        Properties props = new Properties();
        props.setProperty("mail.host", "smtp.qq.com");
        props.setProperty("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        Authenticator authenticator = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,accessKey);
            }
        };
        final Session session = Session.getDefaultInstance(props, authenticator);

        for(final String tempReceiver : receiversEmail) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(senderEmail));
                        message.setRecipient(Message.RecipientType.TO, new InternetAddress(tempReceiver));
                        message.setSubject(subject);
                        //正文形如senderName: " <br/> content </br>
                        //String str = content;
                        String str = "ggg： <br/>" +
                                "浩二<br/>";
                        message.setContent(str, "text/html;charset=UTF-8");
                        //发送
                        Transport.send(message);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        return "Send to many success";
    }

    public static void main(String[] args){
        QQEmailAPI qqEmailAPI=new QQEmailAPI();
        qqEmailAPI.init("1025655613@qq.com","gjadawishosbbcbd");
        qqEmailAPI.sendToOne("1832577498@qq.com","Test to One","ggg： <br/>" +
                "hah<br/>");
        List<String> list=new LinkedList<String>();
        list.add("1832577498@qq.com");

        qqEmailAPI.sendToMany(list,"Test to many","ggg： <br/>" +
                "浩二<br/>");

    }

}
