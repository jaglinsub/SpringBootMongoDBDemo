package com.thepracticaldeveloper.reactiveweb.service;

import com.thepracticaldeveloper.reactiveweb.domain.EmailStatus;
import com.thepracticaldeveloper.reactiveweb.domain.ParentUser;
import com.thepracticaldeveloper.reactiveweb.domain.User;
import com.thepracticaldeveloper.reactiveweb.repository.ParentMongoReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailStatus emailStatus;
    static {

        /** Start: Properties required for SNS/SMS**/
        System.setProperty("aws.accessKeyId", "AKIA22MDRQXPOALWFT4Q");
        System.setProperty("aws.secretKey", "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");
        //    	User name : AmazonSNSAdmin
        //    	Access key ID : AKIA22MDRQXPOALWFT4Q
        //    	Secret access key : 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
        //    	Console login link : https://743842088414.signin.aws.amazon.com/console
        /**
         * End: Properties required for SNS/SMS
         **/
    }

    // Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "jaganls@gmail.com";
    static final String FROMNAME = "Kempes";

    // Replace recipient@example.com with a "To" address. If your account
    // is still in the sandbox, this address must be verified.
//    static final String TO = "jaglinsub@gmail.com";

    // Replace smtp_username with your Amazon SES SMTP user name.
    static final String SMTP_USERNAME = "AKIA22MDRQXPA6OCAPU7";

    // Replace smtp_password with your Amazon SES SMTP password.
    static final String SMTP_PASSWORD = "BPCT2DmgjeXshH5QGpJ3PsFopS8sJ/Q7HWlI446vunGZ";

    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";

    // Amazon SES SMTP host name. This example uses the US West (Oregon) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    static final String HOST = "email-smtp.us-west-2.amazonaws.com";

    // The port you will connect to on the Amazon SES SMTP endpoint.
    static final int PORT = 587;

    static final String SUBJECT = "Welcome to 'Next Step'";
    static final String BODY = String.join(
            System.getProperty("line.separator"),
            "<h1>Welcome to 'Next Step</h1>",
            "<p>Click on the below link to buy the subscription for your child.",
            "<a href='http://ec2-18-218-102-11.us-east-2.compute.amazonaws.com/home'>Subscribe</a>."
    );

    @Async("emailExecutor")
    public void process() {
        log.info("Received request to process in ProcessServiceImpl.process()");
        try {
            Thread.sleep(15 * 1000);
            log.info("Processing complete");
        }
        catch (InterruptedException ie) {
            log.error("Error in ProcessServiceImpl.process(): {}", ie.getMessage());
        }
    }

    @Async("emailExecutor")
    public void sendWelcomeParentEmail(ParentUser parentUser, User user, ParentMongoReactiveRepository parentMongoReactiveRepository) {
        if (parentUser != null && !parentUser.isEmailSent()) {
            String subject = "Welcome to 'Next Step'";
            String body = String.join(
                    System.getProperty("line.separator"),
                    "<h1>Welcome to 'Next Step</h1>",
                    "<p>Click on the below link to buy the subscription for your child." + user.getFirstName() + " " + user.getLastName(),
                    "<a href='http://ec2-18-218-102-11.us-east-2.compute.amazonaws.com/login/1/" +user.getId() + "'>Subscribe</a>."
//                    "<a href='http://localhost:4200/login/1/" + user.getId() + "'>Subscribe</a>."
            );
            try {
                log.info("Started Sending Email");
                Future<EmailStatus> futureEmailStatus = email(parentUser.getEmail(), subject, body);
//                while (true) {
//                    if (futureEmailStatus.isDone()) {
                EmailStatus emailStatus = futureEmailStatus.get();
                log.info("Result from asynchronous process - " + emailStatus);
                if (emailStatus != null && emailStatus.isEmailSent()) {
                    parentUser.setEmailSent(true);
                }
                else {
                    parentUser.setEmailSent(false);
                    log.error("Email Error::" + emailStatus.getErrorMessage());
                }
                parentMongoReactiveRepository.save(parentUser).block();
                log.info("Email Sent=" + user.getParentUser().isEmailSent());
//                        break;
//                    }
//                    log.info("Continue doing something else. ");
//                    Thread.sleep(500);
//                }
                log.info("Done Sending Email");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Async("emailExecutor")
    public Future<EmailStatus> email(String emailTo, String subject, String body) throws Exception {

        emailStatus.setEmailSent(false);
        emailStatus.setErrorMessage("");
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");

        // Add a configuration set header. Comment or delete the
        // next line if you are not using a configuration set
        //msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try
        {
            System.out.println("Sending...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            log.info("Email sent to: " + emailTo);
            emailStatus.setEmailSent(true);
            return new AsyncResult<EmailStatus>(emailStatus);

        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            emailStatus.setEmailSent(false);
            emailStatus.setErrorMessage(ex.getMessage());
            return new AsyncResult<EmailStatus>(emailStatus);
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}
