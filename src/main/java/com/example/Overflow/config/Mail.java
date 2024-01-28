package com.example.Overflow.config;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

    Session newSession = null;
    MimeMessage mineMessage = null;

    public static void SendMail(String email) throws MessagingException {
        Mail mail = new Mail();
        mail.setupServerProperties();
        mail.draftEmail(email);
        mail.sendEmail();
    }

    private void sendEmail() throws MessagingException {
        String fromUser = "prefinal19@gmail.com";
        String fromUserPassword = "bkpr czor otkv vgvk"; // Replace with your Gmail password
        String emailHost = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailHost);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Create a new session with authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromUser, fromUserPassword);
            }
        });

        Transport transport = session.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserPassword);
        transport.sendMessage(mineMessage, mineMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!");
    }

    private MimeMessage draftEmail( String email) throws AddressException, MessagingException {
        String[] emailRecipients = {email};
        String emailSubject = "Test Mail";
       // String emailBody = "Test body of my email Hello from test new updates";
        mineMessage = new MimeMessage(newSession);

        for (int i = 0; i < emailRecipients.length; i++) {
            mineMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients[i]));
        }

        mineMessage.setSubject(emailSubject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        String emailBody = getBody(email);
        bodyPart.setContent(emailBody, "text/html");

        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mineMessage.setContent(multiPart);

        return mineMessage;
    }

    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }

    public static String getBody(String name){
        String emailTemplate = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Welcome to Our Community!</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f5f5f5;\n" +
                "        }\n" +
                "\n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            padding: 40px 0;\n" +
                "        }\n" +
                "\n" +
                "        .content {\n" +
                "            padding: 30px;\n" +
                "            background-color: #ffffff;\n" +
                "            border-radius: 10px;\n" +
                "            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            padding: 20px 0;\n" +
                "        }\n" +
                "\n" +
                "        h1, h2, h3 {\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            color: #555555;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            background-color: #3498db;\n" +
                "            color: #ffffff;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .image-container {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        .image-container img {\n" +
                "            max-width: 100%;\n" +
                "            height: auto;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Welcome to Our Community!</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <div class=\"image-container\">\n" +
                "                <img src=\"https://example.com/path/to/community-logo.png\" alt=\"Community Logo\">\n" +
                "            </div>\n" +
                "            <h2>Hello " + name +"!,</h2>\n" +
                "            <p>Welcome to our vibrant community! We are excited to have you on board.</p>\n" +
                "            <p>Here, you'll find a place to connect, learn, and collaborate with fellow members who share your interests.</p>\n" +
                "            <p>Feel free to explore and engage with the community discussions, events, and resources.</p>\n" +
                "            <p>Thank you for joining us, and we look forward to your contributions!</p>\n" +
                "            <p>Best regards,<br>Rahul</p>\n" +
                "            <div style=\"text-align: center;\">\n" +
                "                <a class=\"button\" href=\"#\">Explore Community</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2023 Our Community. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return  emailTemplate;
    }
}