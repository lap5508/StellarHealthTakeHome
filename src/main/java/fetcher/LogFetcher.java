package fetcher;

import java.io.IOException;
import java.util.Collection;

public interface LogFetcher {
    /**
     * This method fetches log files from a specified path and converts the log into
     * a collection of Strings.
     * TODO: Change signature to return a File in case deserialization into string lines is not required.
     * @param path
     * @return Collection of log lines.
     * @throws IOException
     */
    Collection<String> fetch(String path) throws IOException;
}
