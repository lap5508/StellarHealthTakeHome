package sanitizer.impl;

import sanitizer.LogSanitizer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DateOfBirthSanitizer implements LogSanitizer {
    private static final Integer YEAR_INDEX = 2;

    public void DateOfBirthSanitizer() {

    }

    @Override
    public File sanitize(Collection<String> lines, String fileName) throws IOException {
        System.out.println("Sanitizing logs");
        List<String> sanitizedLines = new ArrayList<>();
        for (String line : lines) {
            StringBuilder sb = new StringBuilder();
            int startIdx = line.indexOf("**") + 3; // skip the **"
            int lastIdx = line.indexOf("**", startIdx) - 1; // ignore the "**
            sb.append(line.substring(0, startIdx))
                    .append(obfuscateMonthAndDay(line.substring(startIdx, lastIdx)))
                    .append(line.substring(lastIdx));
            sanitizedLines.add(sb.toString());
        }

        return writeSanitizedFile(sanitizedLines, fileName);
    }

    private File writeSanitizedFile(List<String> lines, String fileName) throws IOException {
        File log = new File(fileName);
        FileOutputStream fos = new FileOutputStream(log);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (String line : lines) {
            bw.write(line);
        }
        bw.close();
        return log;
    }

    private String obfuscateMonthAndDay(String date) {
        StringBuilder sb = new StringBuilder();
        String[] dateParts = date.split("/");
        System.out.println(dateParts.length);
        for (int i = 0; i < dateParts.length; i++) {
            if (i != YEAR_INDEX) {
                dateParts[i] = "X/";
            }
            sb.append(dateParts[i]);
        }
        return sb.toString();
    }
}
