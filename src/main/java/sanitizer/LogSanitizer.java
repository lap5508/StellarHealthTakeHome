package sanitizer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public interface LogSanitizer {
    /**
     * This method takes in a list of log lines and sanitizes them.
     * TODO: Change signature to accept a reader and a file for reading the input stream of a file (instead of lines).
     * @param lines
     * @param fileName
     * @return Sanitized log lines converted to a file.
     * @throws IOException
     */
    File sanitize(Collection<String> lines, String fileName) throws IOException;
}
