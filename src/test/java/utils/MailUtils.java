package utils;

import mail.MailDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class MailUtils {
    private final String FILE_PATH = "src/test/resources/fileWithData.yml";
    protected static final Logger logger = (Logger) LogManager.getLogger(MailUtils.class);

    String email = ReadProperties.getEmail();
    String password = ReadProperties.getPasswordGmail();
    MailDTO messageData = new MailDTO();

    public MailDTO getMessageFromEmail() {
        Properties properties = setPropertiesForMail(email, password);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        logger.info("Getting information about the last message");
        try {
            Store store = session.getStore("imap");
            store.connect(email, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message message = folder.getMessage(folder.getMessageCount());
            messageData.setEmailFrom(Arrays.stream(message.getFrom()).toArray()[0].toString());
            messageData.setSubject(message.getSubject());

            Multipart mp = (Multipart) message.getContent();
            BodyPart bp = mp.getBodyPart(0);
            messageData.setBody(bp.getContent().toString());

            folder.close(true);
            store.close();
            return messageData;
        } catch (MessagingException | IOException e) {
            logger.debug("Information about the message cannot be taken: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Properties setPropertiesForMail (String email, String password){
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.debug", "false");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.user", email);
        properties.put("mail.imap.password", password);

        return properties;
    }

    public String getMessageData(String element) {
        Map<String, Object> messageElementTitles = new YmlReader().readYaml(FILE_PATH);
        return (String) messageElementTitles.get(element);
    }
}
