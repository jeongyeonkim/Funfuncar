package com.amazonaws.samples;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class CompareFacesExample {

    public static void main(String[] args) throws Exception{
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/userid/.aws/credentials), and is in valid format.", e);
        }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        compareFace(rekognitionClient, "photo1.jpg", "photo2.jpg");
        //compareFace(rekognitionClient, "photo1.jpg", "photo3.jpg");
        //compareFace(rekognitionClient, "photo1.jpg", "photo4.jpg");

    }

    private static void compareFace(AmazonRekognition rekognitionClient, String photo1, String photo2) throws IOException {
        Image source = getImageUtil(photo1);
        Image target = getImageUtil(photo2);
        String persent;
        Double to;
        Float similarityThreshold = 70F;
        CompareFacesResult compareFacesResult = callCompareFaces(source,
                target,
                similarityThreshold,
                rekognitionClient);

        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        if (faceDetails.size() > 0) {
            System.out.println("Face [" + photo1 + "] matches with [" + photo2 + "]");
        } else {
            System.out.println("Face [" + photo1 + "] doesn't matches with [" + photo2 + "]\n");
        }
        for (CompareFacesMatch match: faceDetails){
            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + face.getConfidence().toString()
                    + "% confidence.\n");
            persent = face.getConfidence().toString();
           
            
            //db 연동
            Connection con = null;
            //PreparedStatement pstmt = null;
            Statement stat = null;
            
    		String url = "jdbc:mysql://localhost:3306/funfuncar?serverTimezone=UTC";
    		try {
    			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    			System.out.println("after forName");
    			con = DriverManager.getConnection(url, "root", "as7095as");
    			System.out.println("DBms connection success");
    			System.out.println("DB load success");
    			
    			String sql;
    			stat = con.createStatement();
    	
    			sql = "insert into RESULT VALUES(1,"+persent+");";
    			stat.executeUpdate(sql);
    		
    			
    			System.out.println("cheked");
    		} catch (Exception e) {
    			System.out.println("DB load fail " + e.toString());
    		}
        
        }
    }

    private static CompareFacesResult callCompareFaces(Image sourceImage, Image targetImage,
                                                       Float similarityThreshold, AmazonRekognition amazonRekognition) {

        CompareFacesRequest compareFacesRequest = new CompareFacesRequest()
                .withSourceImage(sourceImage)
                .withTargetImage(targetImage)
                .withSimilarityThreshold(similarityThreshold);
        return amazonRekognition.compareFaces(compareFacesRequest);
    }

    private static Image getImageUtil(String key) throws IOException {
        ByteBuffer imageBytes;
        ClassLoader classLoader = new CompareFacesExample().getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(key)) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        return new Image().withBytes(imageBytes);
    }
}