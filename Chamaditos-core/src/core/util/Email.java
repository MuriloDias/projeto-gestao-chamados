package core.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dominio.EntidadeDominio;
import dominio.Usuario;
import org.apache.commons.lang3.StringUtils;

public class Email {

	
	public void enviar(Usuario para, String assunto, String mensagem) {
        try{

            String host ="smtp.gmail.com" ;
            String user = "chamaditos@gmail.com";
            String pass = "Churros.0";
            String to = para.getEmail();//
            String from = "chamaditos@gmail.com";
            String subject = assunto;//
            String messageText = mensagem;//
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject); msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
        }catch(Exception ex)
        {
            System.out.println(ex);
        }
	}
	public void formular(List<EntidadeDominio> usuarios, 		//lista de usuarios pertencentes ao grupo atribuido
			Usuario sessionated, 								//Obg com usuario sessionado
			EntidadeDominio tipoEntidade,						//chamado ou followup?
			String operacao)									//salvar ou alterar?
		{	
		Usuario usuario = new Usuario();
		for(EntidadeDominio e : usuarios) {
			usuario = (Usuario)e;
			String mensagem = "Olá " + StringUtils.capitalize(usuario.getNome()) + " " + StringUtils.capitalize(usuario.getSobrenome()) + ", o " + 
					StringUtils.capitalize(sessionated.getNome()) + " " + StringUtils.capitalize(sessionated.getSobrenome()) + " do grupo " + StringUtils.capitalize(sessionated.getGrupo().getNomeGrupo())
					+ " " + operacao + " de ID: " + tipoEntidade.getId() + " para você e seu grupo!";
			String assunto = StringUtils.capitalize(sessionated.getGrupo().getNomeGrupo()) + " " + operacao + " para voce!";  
					
			enviar(usuario, assunto, mensagem);
		}
		
		
		
		
	}
	
	
}
