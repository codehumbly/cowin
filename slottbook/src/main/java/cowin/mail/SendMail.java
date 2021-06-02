package cowin.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import cowin.slottbook.BaseClass;

public class SendMail extends BaseClass {

	public void sendBeep() throws MessagingException {
		// Create object to add multimedia type content
		BodyPart messageBodyPart1 = new MimeBodyPart();
		messageBodyPart1.setText("Stay vaccinated..Stay safe!!");

		// Create another object to add another content
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		String filename = System.getProperty("user.dir") + "\\homePageScreenshot.png";

		// Create data source and pass the filename
		DataSource source = new FileDataSource(filename);

		// set the handler
		messageBodyPart2.setDataHandler(new DataHandler(source));

		// set the file
		messageBodyPart2.setFileName(filename);

		// Create object of MimeMultipart class
		Multipart multipart = new MimeMultipart();

		// add body part 1
		multipart.addBodyPart(messageBodyPart2);

		// add body part 2
		multipart.addBodyPart(messageBodyPart1);

		Config.send("InnovationDriven1990@gmail.com", "Test@12345#", email, "Co-Win Slot Alert",
				multipart);
	}

}
