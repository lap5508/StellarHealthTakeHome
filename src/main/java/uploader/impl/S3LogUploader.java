package uploader.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import uploader.LogUploader;

import java.io.File;

public class S3LogUploader implements LogUploader {
    private final AmazonS3 s3;

    public S3LogUploader(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void upload(String path, String key, File file) {
        System.out.println("Uploading file to S3");
        s3.putObject(new PutObjectRequest(path, key, file));
    }
}
