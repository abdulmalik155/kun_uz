package kunuz.uz.api.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kunuz.uz.api.dto.request.MessageDTO;
import kunuz.uz.api.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {
    @Value("${spring.mail.username}")
    private String fromAccount;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailHistoryService emailHistoryService;

    public void sendSimpleMessage(MessageDTO dto) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(dto.getToAccount());
        msg.setSubject(dto.getSubject());
        msg.setText(dto.getText());
        javaMailSender.send(msg);
    }

    public void sendRegistrationStyledEmail(String toAccount) {
        int smsCode = RandomUtil.fiveDigit();
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"text-align: center\">Kunuz Portaliga xush kelibsiz.</h1>\n" +
                "<br>\n" +
                "<h4>Ro'yhatdan o'tishni tugatish uchun quyidagi linkga bosing</h4>\n" +
                "<p>Confirm Code %s </p>" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        // TODO make toAccount adn smsCode encoded using jwt and send it
        body = String.format(body, smsCode);
        // send
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSubject("Registration complete");
        messageDTO.setText(body);
        messageDTO.setToAccount(toAccount);
        sendMimeMessage(messageDTO);
        // save to db
        emailHistoryService.create(toAccount, body, String.valueOf(smsCode));
    }

    private void sendMimeMessage(MessageDTO messageDTO) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(messageDTO.getToAccount());
            helper.setSubject(messageDTO.getSubject());
            helper.setText(messageDTO.getText(), true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

   /* public void sendEmailHtmlFormatted(MessageDTO dto, int verificationCode) {
        try {
            String htmlTemplate = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Tasdiqlash kodi</title>
                    </head>
                    <body style="margin:0; padding:20px; background-color:#f4f6f8; font-family:sans-serif;">
                        <div style="max-width:450px; margin:0 auto; background:#ffffff; border-radius:12px; padding:30px; box-shadow:0 4px 10px rgba(0,0,0,0.05);">
                            <h2 style="color:#4f46e5; margin-top:0;">Tasdiqlash kodi</h2>
                            <p style="color:#334155; font-size:16px;">Assalomu alaykum,</p>
                            <p style="color:#475569; font-size:14px; line-height:1.5;">
                                Tizimga kirishni tasdiqlash uchun quyidagi bir martalik faollashtirish kodidan foydalaning. Kod 10 daqiqa davomida faol bo'ladi.
                            </p>
                            <div style="text-align:center; margin:30px 0;">
                                <span style="display:inline-block; background-color:#f8fafc; border:2px dashed #cbd5e1; border-radius:8px; padding:12px 24px; font-family:monospace; font-size:32px; font-weight:bold; color:#0f172a; letter-spacing:4px;">
                                    %d
                                </span>
                            </div>
                            <div style="background-color:#fffbeb; border-left:4px solid #f59e0b; padding:12px; margin-bottom:20px; border-radius:4px;">
                                <p style="margin:0; font-size:12px; color:#b45309;">
                                    <strong>Xavfsizlik eslatmasi:</strong> Agar bu amalni siz bajarmagan bo'lsangiz, xatni o'chirib tashlang.
                                </p>
                            </div>
                            <p style="font-size:12px; color:#94a3b8; margin-bottom:0;">Hurmat bilan,<br>Tizim Ma'muriyati</p>
                        </div>
                    </body>
                    </html>
                    """.formatted(verificationCode);

            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
            helper.setTo(dto.getToAccount());
            helper.setSubject(dto.getSubject());
            helper.setText(htmlTemplate, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
