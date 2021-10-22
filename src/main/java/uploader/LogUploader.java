package uploader;

import java.io.File;

public interface LogUploader {
    /**
     * Upload file at the given path/key.
     * @param path
     * @param key
     * @param file
     */
    void upload(String path, String key, File file);
}
