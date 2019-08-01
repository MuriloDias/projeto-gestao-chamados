/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * https://www.google.com/settings/security/lesssecureapps
 */

package core.util;

/**
 *
 * @author Naveen
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;


public class EmailSend {

	public static void main(String[] args) {
		EmailSend mess = new EmailSend();
		mess.m();
	}
    public void m(){
//        try{
            String host ="smtp.gmail.com" ;
            String user = "chamaditos@gmail.com";
            String pass = "Churros.0";
            String to = "murilo-d@hotmail.com";
            String from = "chamaditos@gmail.com";
            String subject = "Teste de Email via Java";
            String messageText = "Your Is Test Email, please do not aswer it";
            boolean sessionDebug = false;
//
//            Properties props = System.getProperties();
//
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.required", "true");
//
//            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//            Session mailSession = Session.getDefaultInstance(props, null);
//            mailSession.setDebug(sessionDebug);
//            Message msg = new MimeMessage(mailSession);
//            msg.setFrom(new InternetAddress(from));
//            //
//            InternetAddress[] address = {new InternetAddress(to)};
//            msg.setRecipients(Message.RecipientType.TO, address);
//            msg.setSubject(subject); msg.setSentDate(new Date());
//            msg.setText(messageText);
//
//           Transport transport=mailSession.getTransport("smtp");
//           transport.connect(host, user, pass);
//           transport.sendMessage(msg, msg.getAllRecipients());
//           transport.close();
//            //
//           System.out.println("message send successfully");
//        }catch(Exception ex)
//        {
//            System.out.println(ex);
//        }

            Properties prop = new Properties();
    		prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS
            
            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, pass);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("chamaditos@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse("murilo-d@hotmail.com")
                );
                message.setSubject("Testing Gmail TLS");
                message.setText("Dear Mail Crawler,"
                        + "\n\n Please do not spam my email!");

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
    	
    	
    }
}
