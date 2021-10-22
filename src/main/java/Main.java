import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import fetcher.LogFetcher;
import fetcher.impl.S3LogFetcher;
import sanitizer.LogSanitizer;
import sanitizer.impl.DateOfBirthSanitizer;
import uploader.LogUploader;
import uploader.impl.S3LogUploader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    private static final String BUCKET_NAME = "s3.amazonaws.com/stellar.health.test.luke.pinto/";
    private static final String OBJECT_NAME = "patients.log";

    public static void main(String[] args) {
        AmazonS3 s3 = buildS3Client();
        // Fetch log files
        LogFetcher s3LogFetcher = new S3LogFetcher(s3);
        Collection<String> logLines = new ArrayList<>();
        try {
            logLines = s3LogFetcher.fetch(BUCKET_NAME + OBJECT_NAME);
        } catch (IOException e) {
            System.out.println("Exception fetching log file");
            e.printStackTrace();
        }

        // Sanitize log files
        LogSanitizer dateOfBirthSanitizer = new DateOfBirthSanitizer();
        File cleanedLog = null;
        try {
            cleanedLog = dateOfBirthSanitizer.sanitize(logLines, OBJECT_NAME);
        } catch (IOException ioe) {
            System.out.println(String.format("Exception sanitizing file"));
            ioe.printStackTrace();
        }

        // Upload log files
        LogUploader s3LogLogUploader = new S3LogUploader(s3);
        s3LogLogUploader.upload(BUCKET_NAME, OBJECT_NAME, cleanedLog);
    }

    private static AmazonS3 buildS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIARX7DSUMEAZHCBGQD",
                "X9Ekf9LL3fhtgIq51yAx0pqSrRSrYHhfmK+WGf4c"
        );
        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        return s3client;
    }
}
