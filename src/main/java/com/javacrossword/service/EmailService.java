package com.javacrossword.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(
            String email,
            String token
    ) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Recuperação de senha");

        String resetLink =
                "http://localhost:5173/reset-password?token=" + token;

        String html = """
                <html>
                    <body style="font-family: Arial, sans-serif;">

                        <h2>Recuperação de senha</h2>

                        <p>
                            Você solicitou a recuperação da sua senha.
                        </p>

                        <p>
                            Clique no botão abaixo para criar uma nova senha:
                        </p>

                        <p>
                            <a href="%s"
                               style="
                                   display: inline-block;
                                   padding: 10px 20px;
                                   background-color: #007bff;
                                   color: white;
                                   text-decoration: none;
                                   border-radius: 5px;
                               ">
                                Redefinir minha senha
                            </a>
                        </p>

                        <p>
                            Este link será válido por 15 minutos.
                        </p>

                        <p>
                            Se você não solicitou a recuperação de senha,
                            ignore este e-mail.
                        </p>

                    </body>
                </html>
                """.formatted(resetLink);

        helper.setText(html, true);

        mailSender.send(message);
    }
}