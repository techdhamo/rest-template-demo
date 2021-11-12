package in.otomate.adminloginservice.service.implementation;

import java.text.DecimalFormat;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import in.otomate.adminloginservice.util.MailTemplate;
import in.otomate.common.Exceptions.InvalidDataException;
import in.otomate.common.Exceptions.SystemException;

@Component
public class OTPSender {
	// Find your Account Sid and Auth Token at twilio.com/console
	public static final String username = "AC39f64a69c3ee98cd49c86cc8abd86a6e";
	public static final String ACCOUNT_SID = "AC39f64a69c3ee98cd49c86cc8abd86a6e";
	public static final String AUTH_TOKEN = "42fbda9b33f164d5a845429d45fc0f7b";
	@Autowired
	private JavaMailSender sender;
	@Autowired
	MailTemplate template;

	public String send(String to) {
		Twilio.init(username, AUTH_TOKEN, ACCOUNT_SID);
		String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
		if (to.contains("+")) {
			try {
				Message.creator(new PhoneNumber(to), // to
						new PhoneNumber("+12166000675"), // from
						"Your Otomate Verification code is: " + otp).create();
				return otp;
			} catch (RuntimeException e) {
				throw new SystemException(null, this, e.getMessage());
			}
		} else if (to.contains("@")) {
			try {
				template.setHtml(otp);
				javax.mail.internet.MimeMessage mimeMessage = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
				helper.setFrom("no-reply@otomate.in");
				helper.setSubject("Verify Your Account");
				helper.setText(template.getHtml(), true);
				helper.setTo(to);
				sender.send(mimeMessage);
				return otp;
			} catch (MessagingException e) {
				throw new SystemException(null, this, e.getMessage());
			} catch (RuntimeException e) {
				throw new SystemException(null, this, e.getMessage());
			}
		} else {
			throw new InvalidDataException(null, this, "Invalid Contact Details");
		}

	}
}