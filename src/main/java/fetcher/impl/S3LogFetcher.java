package fetcher.impl;

import com.amazonaws.services.s3.AmazonS3;
import fetcher.LogFetcher;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class S3LogFetcher implements LogFetcher {
    private final AmazonS3 s3;

    public S3LogFetcher(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public Collection<String> fetch(String path) throws IOException {
        System.out.println("Fetching logs from S3");
        List<String> logLines = new ArrayList<>();
        try (InputStream is = s3.getObject(path, "/").getObjectContent()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                logLines.add(line);
            }
        }
        return logLines;
    }
}
