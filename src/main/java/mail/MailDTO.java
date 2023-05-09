package mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO {
    String emailFrom;
    String emailTo;
    String sender;
    String subject;
    String body;
}
