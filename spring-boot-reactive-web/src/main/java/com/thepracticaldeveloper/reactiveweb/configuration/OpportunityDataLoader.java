package com.thepracticaldeveloper.reactiveweb.configuration;

import com.mongodb.gridfs.GridFS;
import com.thepracticaldeveloper.reactiveweb.domain.Opportunity;
import com.thepracticaldeveloper.reactiveweb.domain.OpportunityDetails;
import com.thepracticaldeveloper.reactiveweb.domain.QualificationDetails;
import com.thepracticaldeveloper.reactiveweb.repository.OpportunityDetailsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.OpportunityMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.UserTypeMongoReactiveRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Date;

@Component
public class OpportunityDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(OpportunityDataLoader.class);

    private OpportunityMongoReactiveRepository opportunityMongoReactiveRepository;
    private OpportunityDetailsMongoReactiveRepository opportunityDetailsMongoReactiveRepository;

    OpportunityDataLoader(final OpportunityMongoReactiveRepository opportunityMongoReactiveRepository, final OpportunityDetailsMongoReactiveRepository opportunityDetailsMongoReactiveRepository) {
        this.opportunityMongoReactiveRepository = opportunityMongoReactiveRepository;
        this.opportunityDetailsMongoReactiveRepository = opportunityDetailsMongoReactiveRepository;
    }

    @Override
    public void run(final String... args) throws Exception {

        for (int i = 0; i < 10; i++) {
//            insertOpportunity(i);
        }
    }

    private void insertOpportunity(int count) throws Exception {

        OpportunityDetails opportunityDetails = new OpportunityDetails();
        int incr = 395 + count;
        opportunityDetails.setOpportunityDtlsName("Pharmacy Intern - Grade " + incr);


        String imageName = "sample_pharmacy.png";
        if(count % 2 == 0)
        {
            imageName = "AllinaHealth.jpg";
        }
        BufferedImage bImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(imageName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", bos );
        byte [] imageData = bos.toByteArray();
//        new Binary(BsonBinarySubType.BINARY, imageData);
//        opportunityDetails.setOrganizationImage(new Binary(BsonBinarySubType.BINARY, imageData));

        opportunityDetails.setOrganizationImage(Base64.getEncoder().encodeToString(imageData));

        opportunityDetails.setOrganizationURL("");
        opportunityDetails.setTypeofProfOppurtunity("Paid Internship ($20/hour)");
        opportunityDetails.setWorkLength("3 months (open for extension)");
        opportunityDetails.setWorkHours("10-25 hours a week");
        opportunityDetails.setRecommendations("Greatly preferred (optional)");
        opportunityDetails.setSchoolAttendance("150 / 180 School Days preferred");
        opportunityDetails.setGpaScore("3.0 or higher (preferred)");
        opportunityDetails.setOppurtunityLongDescription("Under direct supervision of a licensed Pharmacist, dispenses, compounds, procures, stores, and distributes pharmacy products. Consults with patients and medical personnel regarding medication therapy. Provides pharmaceutical care for hospitalized or ambulatory patients as assigned. Provides medical personnel and patients with medication information and product identification. Performs other duties as required.");

        String[] qualificationDetails = {
                "Ability to either type 30 WPM/6000 KPH, or type prescription labels required.",
                "Excellent verbal & written communication skills",
                "Ability to learn and operate pharmacy computer systems and other equipment, stand for long periods of time read fine print, communicate with co-workers and patients, and lift/transport items from 1-50 lbs., light to moderate carrying, pulling, pushing, walking, frequent bending, stooping, reaching.",
                "Must be willing to work in a Labor Management Partnership environment." + incr,
                "MUST PASS BACKGROUND CHECK."
        };

        opportunityDetails.setQualificationDetails(qualificationDetails);
        OpportunityDetails savedOpportunityDetails = opportunityDetailsMongoReactiveRepository.save(opportunityDetails).block();

        Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityName("Pharmacy Intern - Grade " + incr);
        opportunity.setOpportunityShortDesc("Pharmacy Intern - Grade " + incr);
        opportunity.setOrganizationName("Kaiser Permanente");
        opportunity.setOrganizationAddress("San Mateo, CA 95014");
        opportunity.setWhenPosted(new Date().toString());
        opportunity.setTypeofProfOppurtunity("Paid Internship ($20/hour)");
        opportunity.setOpportunityDetails(opportunityDetails);

        opportunityMongoReactiveRepository.save(opportunity).block();
    }
}
