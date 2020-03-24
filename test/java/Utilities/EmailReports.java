package Utilities;

import javax.mail.*;
import javax.activation.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailReports {

public static void SendEmail(String Username, String Password) {

    final String username = "praveenkumarvoora@gmail.com";
    final String password = "9849945090";

    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

    try {

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("praveenkumarvoora@gmail.com"));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("praveenkumar.voora@inmarsat.com"));
        message.setSubject("SAmple BTP Regression Test Report");
        message.setText("PFA");

        MimeBodyPart messageBodyPart = new MimeBodyPart();

        Multipart multipart = new MimeMultipart();

        messageBodyPart = new MimeBodyPart();
        String file= System.getProperty("user.dir")+"\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\target\\surefire-reports\\"+"emailable-report.html" ;
        String fileName = "emailable-report.html";
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        System.out.println("Sending");

        Transport.send(message);

        System.out.println("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
  }
}