package com.thepracticaldeveloper.reactiveweb.configuration;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thepracticaldeveloper.reactiveweb.domain.Opportunity;
import com.thepracticaldeveloper.reactiveweb.domain.OpportunityDetails;
import com.thepracticaldeveloper.reactiveweb.repository.OpportunityDetailsMongoReactiveRepository;
import com.thepracticaldeveloper.reactiveweb.repository.OpportunityMongoReactiveRepository;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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

        if (opportunityMongoReactiveRepository.count().block() > 0L) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        Map<String, ArrayList<LinkedHashMap<String, String>>> jsonMap = mapper.readValue(getClass().getClassLoader().getResourceAsStream("KempesOppurData.json"), Map.class);

        for (Map.Entry<String, ArrayList<LinkedHashMap<String, String>>> jsonM : jsonMap.entrySet()) {
          System.out.println("Item Start: ");
            ArrayList<LinkedHashMap<String, String>> arrList = jsonM.getValue();
            int count = 0;
            for (LinkedHashMap lhm : arrList) {
                int temp = ++count;

                System.out.println("Oppur Start: " + temp + "*********************************************");

//                if((getNotNullStr(lhm.get("OrganizationName").toString())).equalsIgnoreCase("Kaiser Permanente Mill Valley Medical Offices")) {
                    OpportunityDetails opportunityDetails = createOpportunityDetails(lhm);

                    OpportunityDetails savedOpportunityDetails = opportunityDetailsMongoReactiveRepository.save(opportunityDetails).block();
                    Opportunity opportunity = createOpportunity(lhm);
                    opportunity.setOpportunityDetails(savedOpportunityDetails);
//                    System.out.println("Opportunity = " + opportunity.toString());

                    opportunityMongoReactiveRepository.save(opportunity).block();
//                }
                System.out.println("Oppur End  : " + temp + "*********************************************");
            }
            System.out.println("Item Done: ");
        }

//        for (int i = 0; i < 10; i++) {
//            insertOpportunity(i);
//        }
    }

    private String getNotNullStr(String str) {
        String retStr = "";
        if (str != null) {
            retStr = str.toString();
        }
        return retStr;
    }
    private Opportunity createOpportunity (LinkedHashMap<String, String> lhm) {

        Opportunity opportunity = new Opportunity();
        opportunity.setOpportunityName(getNotNullStr(lhm.get("OpportunityName")));
        opportunity.setOpportunityShortDesc(getNotNullStr(lhm.get("OpportunityShortDescription")));
        opportunity.setOrganizationName(getNotNullStr(lhm.get("OrganizationName")));
        opportunity.setOrganizationAddress(getNotNullStr(lhm.get("OrganizationAddress")));
        opportunity.setWhenPosted(getNotNullStr(lhm.get("WhenPosted")));
        opportunity.setTypeofProfOppurtunity(getNotNullStr(lhm.get("TypeofOppurtunity")));

        return opportunity;
    }
    private OpportunityDetails createOpportunityDetails(LinkedHashMap<String, String> lhm) throws IOException {
        OpportunityDetails opportunityDetails = new OpportunityDetails();

        opportunityDetails.setOpportunityDtlsName(getNotNullStr(lhm.get("OpportunityName")));
        try {
            String imgStr = lhm.get("Image");
            System.out.println("imgStr= \n" + imgStr);
            if(imgStr != null) {

                if(imgStr.endsWith(".svg")) {
                    imgStr = Base64.getEncoder().encodeToString(createSVGByteArray(imgStr));
                }
                else if (imgStr.endsWith(".jpg")) {
                    BufferedImage bImage = ImageIO.read(new URL(lhm.get("Image")));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "jpg", bos);
                    byte[] imageData = bos.toByteArray();
                    imgStr = Base64.getEncoder().encodeToString(imageData);
                }
                else if (!imgStr.startsWith("data:image")) {
                    BufferedImage bImage = ImageIO.read(new URL(lhm.get("Image")));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bImage, "png", bos);
                    byte[] imageData = bos.toByteArray();
                    imgStr = Base64.getEncoder().encodeToString(imageData);
                }
            }

            opportunityDetails.setOrganizationImage(imgStr);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        opportunityDetails.setOrganizationURL(getNotNullStr(lhm.get("OrganizationURL")));
        opportunityDetails.setTypeofProfOppurtunity(getNotNullStr(lhm.get("TypeofOppurtunity")));
        opportunityDetails.setWorkLength(getNotNullStr(lhm.get("WorkLength")));
        opportunityDetails.setWorkHours(getNotNullStr(lhm.get("WorkHours")));
        opportunityDetails.setRecommendations(getNotNullStr(lhm.get("Recommendations")));
        opportunityDetails.setSchoolAttendance(getNotNullStr(lhm.get("SchoolAttendance")));
        opportunityDetails.setGpaScore(getNotNullStr(lhm.get("GPAScore")));
        opportunityDetails.setOppurtunityLongDescription(getNotNullStr(lhm.get("OppurtunityLongDescription")));

        String qualDetailsl = lhm.get("QualificationDetails") != null ? lhm.get("QualificationDetails") : "";
        String[] qualificationDetails = qualDetailsl.split("\n");
//        String[] qualificationDetails = {
//                "Ability to either type 30 WPM/6000 KPH, or type prescription labels required.",
//                "Excellent verbal & written communication skills",
//                "Ability to learn and operate pharmacy computer systems and other equipment, stand for long periods of time read fine print, communicate with co-workers and patients, and lift/transport items from 1-50 lbs., light to moderate carrying, pulling, pushing, walking, frequent bending, stooping, reaching.",
//                "Must be willing to work in a Labor Management Partnership environment.",
//                "MUST PASS BACKGROUND CHECK."
//        };

        opportunityDetails.setQualificationDetails(qualificationDetails);
//        System.out.println("opportunityDetails = " + opportunityDetails.toString());

        return opportunityDetails;
    }

    private byte[] createSVGByteArray(String imgStr) throws Exception {

        TranscoderInput input_svg_image = new TranscoderInput(imgStr);
        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
        OutputStream png_ostream = new ByteArrayOutputStream();
        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
        // Step-3: Create PNGTranscoder and define hints if required
        ImageTranscoder my_converter = new PNGTranscoder();
        if(imgStr.endsWith(".jpg")) {
            my_converter = new JPEGTranscoder();
        }
        // Step-4: Convert and Write output
        my_converter.transcode(input_svg_image, output_png_image);
        byte[] imageData = ((ByteArrayOutputStream) png_ostream).toByteArray();

        // Step 5- close / flush Output Stream
        png_ostream.flush();
        png_ostream.close();

        //String imgPNGStr = Base64.getEncoder().encodeToString(imageData);
        //System.out.println("imgPNGStr= \n" + imgPNGStr);
        //writePNGBArrtoFile(imgPNGStr);
        return imageData;
    }

    private void writePNGBArrtoFile(String imgStr) {
        try {
            String encodedImg = imgStr;
            String partSeparator = ",";
            if (imgStr.contains(partSeparator)) {
                encodedImg = imgStr.split(partSeparator)[1];
            }
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(encodedImg));
            BufferedImage bImage2 = ImageIO.read(bis);
            ImageIO.write(bImage2, "png", new File("C:/Users/jaganls/AppData/Local/Temp/testImages/" + "svgpng" + ".jpg"));
        }
        catch (Exception ex1) {
            ex1.printStackTrace();
        }
    }
//    private HttpEntity retrieveGraphicFromUrl(String url) throws IOException {
//        HttpGet httpGet = new HttpGet(url);
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        HttpResponse response = httpClient.execute(httpGet);
//        if (response.getStatusLine().getStatusCode() != 200) {
//            throw new IOException("Can't retrieve image from " + url);
//        }
//
//        return response.getEntity();
//    }

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
