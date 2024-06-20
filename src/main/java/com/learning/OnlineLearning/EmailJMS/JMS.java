package com.learning.OnlineLearning.EmailJMS;

import com.learning.OnlineLearning.Entities.User;
import com.learning.OnlineLearning.Events.CourseCreatedEvent;
import com.learning.OnlineLearning.Events.UserRegistrationEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JMS {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String mailFrom;


    @EventListener
    public String sendWelcomeMail(UserRegistrationEvent userEvent) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message);

        mimeMessageHelper.setFrom(mailFrom,"Amir For Online Learning");
        mimeMessageHelper.setTo(userEvent.getEmail());
        mimeMessageHelper.setSubject("Registration Successful in Amir Website");
        mimeMessageHelper.setText("<h1> Welcome "+userEvent.getEmail()+ "</h1> <br>" +
                "<b>We are Happy Cause of your Registration in our Website.</b> <br>" +
                "we Wish you learn and gain experience in the field you like.<br>" +
                "Amir bi7ebk.  ",true);
        System.out.println("hb3t email");
        System.out.println("from email: "+mailFrom);
        System.out.println("to email: "+userEvent.getEmail());

        javaMailSender.send(message);
        System.out.println("Mail is Sent..");
        return "Mail is Sent..";
    }

    @EventListener
    public String sendNotificationAddedNewCourse(CourseCreatedEvent courseEvent) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message);

        mimeMessageHelper.setFrom(mailFrom,"Amir For Online Learning");
        mimeMessageHelper.setTo(courseEvent.getMailTo());
        mimeMessageHelper.setSubject("A new Course is Added");
        mimeMessageHelper.setText("<h1> Welcome "+courseEvent.getMailTo()+ "</h1> <br>" +
                "<b>We are Happy Notify you that the new "+courseEvent.getCourseName() +" .</b> <br>" +
                "we Wish you learn and excited for this course.<br>" +
                "Amir bi7ebk.  ",true);
        System.out.println("hb3t email");
        System.out.println("from email: "+mailFrom);
        System.out.println("to email: "+courseEvent.getMailTo());

        javaMailSender.send(message);
        System.out.println("Mail is Sent..");
        return "Mail is Sent..";
    }


}
