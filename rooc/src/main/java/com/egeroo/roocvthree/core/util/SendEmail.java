package com.egeroo.roocvthree.core.util;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;

import com.egeroo.roocvthree.core.error.CoreException;
import com.egeroo.roocvthree.roocconfig.RoocConfig;
import com.egeroo.roocvthree.roocconfig.RoocConfigMapper;
import com.sun.mail.smtp.SMTPTransport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Properties;

public class SendEmail {
	
	private String SMTP_SERVER = "smtp.gmail.com";
    private String USERNAME = "";
    private String PASSWORD = "";

    private  String EMAIL_FROM = "email@server.com";
    private  String EMAIL_TO = "email@server.com";
    //private  final String EMAIL_TO_CC = "";

    private  String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private  String EMAIL_TEXT = "<h1>Hello Rooc Mail \\n Holla</h1>";
    
    
    public void sendMailbak20092019(String SMTPSERVER,String SMTPPort,String Username,String Password,String Subject,String EmailMessage,String EmailFrom,String EmailTo)
    {
    	Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");
        //prop.put("mail.smtp.port", SMTPPort);

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        
        this.EMAIL_SUBJECT = Subject;
        this.EMAIL_FROM = EmailFrom;
        this.EMAIL_TO = EmailTo;
        this.EMAIL_TEXT = EmailMessage;
        this.SMTP_SERVER = SMTPSERVER;

        try {

            msg.setFrom(new InternetAddress(this.EMAIL_FROM));

            msg.setRecipients(RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));

            msg.setSubject(EMAIL_SUBJECT);
			
			// TEXT email
            //msg.setText(EMAIL_TEXT);

			// HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));

            
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			
			// connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
			
			// send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    
    

    public void sendMailauth(RoocConfig roocconfig,RoocConfigMapper rcMapper,String Subject,String EmailMessage)
    {
        
        this.EMAIL_SUBJECT = Subject;
        //this.EMAIL_FROM = EmailFrom;
        //this.EMAIL_TO = EmailTo;
        this.EMAIL_TEXT = EmailMessage;
        //this.SMTP_SERVER = SMTPSERVER;
        
        roocconfig = rcMapper.findByconfigkey("username");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no username found.");
			//System.out.print("System not using email");
        }
		this.USERNAME = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("password");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no password found.");
			//System.out.print("System not using email");
        }
		this.PASSWORD = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("EmailSender");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no email sender found.");
			//System.out.print("System not using email");
        }
		this.EMAIL_FROM = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("EmailRecipient");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no email recipient found.");
			//System.out.print("System not using email");
        }
		this.EMAIL_TO = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("SMTPServer");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no smtp server found.");
			//System.out.print("System not using email");
        }
		this.SMTP_SERVER = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("SMTPPort");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no smtp port found.");
			//System.out.print("System not using email");
        }
		String smtpport = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("SMTPAuth");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no smtp SMTPAuth found.");
			//System.out.print("System not using email");
        }
		String smtpauth = roocconfig.getConfigvalue();
		
		InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
		//String returndata ="Success";
		final String username = this.USERNAME;
		final String password = this.PASSWORD;
		System.out.print(username);
		System.out.println("Connecting...");
		Properties props = new Properties();
		
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", this.SMTP_SERVER);//smtp.gmail.com
		props.put("mail.smtp.port", smtpport);
		props.put("mail.smtp.auth", smtpauth);
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
		
		try {
            fromAddress = new InternetAddress(this.EMAIL_FROM);
            //toAddress = new InternetAddress("arif.chohan@egeroo.ai");
        } catch (AddressException e) {
        	System.out.print("Invalid from address");
            e.printStackTrace();
        }
        
        try {
            //fromAddress = new InternetAddress("jane_chatbot@generali.co.id");
            toAddress = new InternetAddress(this.EMAIL_TO);
        } catch (AddressException e) {
        	System.out.print("Invalid to address");
            e.printStackTrace();
        }
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(fromAddress);
			//message.setRecipients(Message.RecipientType.TO,
			//		toAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setSubject(this.EMAIL_SUBJECT);
			message.setText(this.EMAIL_TEXT);
			Transport.send(message);
			System.out.println("Done");
			//returndata ="Success";
		} catch (MessagingException e) {
			System.out.println("failed");
			//returndata ="Failed";
			throw new RuntimeException(e);
		}
		

    	/*Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            msg.setFrom(new InternetAddress(this.EMAIL_FROM));

            msg.setRecipients(RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));

            msg.setSubject(EMAIL_SUBJECT);
			
			// TEXT email
            //msg.setText(EMAIL_TEXT);

			// HTML email
            msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));

            
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			
			// connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
			
			// send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }*/

    }
    


    public void sendMailnoauth(RoocConfig roocconfig,RoocConfigMapper rcMapper,String Subject,String EmailMessage)
    {
    	roocconfig = rcMapper.findByconfigkey("SMTPServer");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no smtp server found.");
			//System.out.print("System not using email");
        }
		this.SMTP_SERVER = roocconfig.getConfigvalue();
		
    	
        
        roocconfig = rcMapper.findByconfigkey("SMTPPort");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no smtp port found.");
			//System.out.print("System not using email");
        }
		String smtpport = roocconfig.getConfigvalue();
        
        
        
        this.EMAIL_SUBJECT = Subject;
        //this.EMAIL_FROM = EmailFrom;
        //this.EMAIL_TO = EmailTo;
        this.EMAIL_TEXT = EmailMessage;
        //this.SMTP_SERVER = SMTPSERVER;
        
        roocconfig = rcMapper.findByconfigkey("username");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no username found.");
			//System.out.print("System not using email");
        }
		this.USERNAME = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("password");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no password found.");
			//System.out.print("System not using email");
        }
		this.PASSWORD = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("EmailSender");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no email sender found.");
			//System.out.print("System not using email");
        }
		this.EMAIL_FROM = roocconfig.getConfigvalue();
		
		roocconfig = rcMapper.findByconfigkey("EmailRecipient");
		if (roocconfig == null) {
            throw new CoreException(HttpStatus.EXPECTATION_FAILED, "no email recipient found.");
			//System.out.print("System not using email");
        }
		this.EMAIL_TO = roocconfig.getConfigvalue();
		
		Properties props = new Properties();
        props.put("mail.smtp.host", this.SMTP_SERVER);
        props.put("mail.smtp.port", smtpport);
        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(this.EMAIL_FROM);
            //toAddress = new InternetAddress("arif.chohan@egeroo.ai");
        } catch (AddressException e) {
        	System.out.print("Invalid from address");
            e.printStackTrace();
        }
        
        try {
            //fromAddress = new InternetAddress("jane_chatbot@generali.co.id");
            toAddress = new InternetAddress(this.EMAIL_TO);
        } catch (AddressException e) {
        	System.out.print("Invalid to address");
            e.printStackTrace();
        }

        try {
        	System.out.print("Trying to send");
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(RecipientType.TO, toAddress);
            simpleMessage.setSubject(this.EMAIL_SUBJECT);
            simpleMessage.setText(this.EMAIL_TEXT);    
            Transport.send(simpleMessage);
            System.out.print("send....");
            
        } catch (MessagingException e) {
        	
        	System.out.print("failed to send");
            e.printStackTrace();
        }
        /*try {

            msg.setFrom(new InternetAddress(this.EMAIL_FROM));

            //msg.setRecipients(RecipientType.TO,
            //        InternetAddress.parse(EMAIL_TO, false));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL_TO));

            msg.setSubject(EMAIL_SUBJECT);
			
			// TEXT email
            //msg.setText(EMAIL_TEXT);

			// HTML email
            //msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
            msg.setText(EMAIL_TEXT);
            
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			
			// connect
            //t.connect(SMTP_SERVER, USERNAME, PASSWORD);
			
			// send
            //t.sendMessage(msg, msg.getAllRecipients());
			t.send(msg);
			
            System.out.println("Response: " + t.getLastServerResponse());

            //t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }*/

    }
    

    
    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
    
    //public static void main(String[] args) {
    public void sendemail() {
    	Properties prop = System.getProperties();
    	prop.put("mail.smtp.host", "https://webmail.generali.co.id");
        //prop.put("mail.smtp.auth", "false");
        
        prop.put("mail.smtp.port", "25");
        
        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);
        
    	try {

            msg.setFrom(new InternetAddress());

            //msg.setRecipients(RecipientType.TO,
            //        InternetAddress.parse(EMAIL_TO, false));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("robby.kurniawan87@gmail.com"));

            msg.setSubject("Test");
			
			// TEXT email
            //msg.setText(EMAIL_TEXT);

			// HTML email
            //msg.setDataHandler(new DataHandler(new HTMLDataSource(EMAIL_TEXT)));
            msg.setText("hallo,from roocvthree");
            
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			
			// connect
            //t.connect(SMTP_SERVER, USERNAME, PASSWORD);
			
			// send
            //t.sendMessage(msg, msg.getAllRecipients());
			t.send(msg);
			
            System.out.println("Response: " + t.getLastServerResponse());

            //t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
