package hackathon.meetingroom.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationTable {
    public static Logger log  = LoggerFactory.getLogger(LocationTable.class);

    final static String  path = "/meetingrooms.csv";

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        LocationTable t = new LocationTable();
        t.build();
    }

    Pattern p = Pattern.compile(",");

    private MeetingRoomLocation parseLine(String line) {
        String[] data = p.split(line);
        if (data == null || data.length < 3) {
            throw new IllegalStateException("Invalid input: " + line);
        }
        return new MeetingRoomLocation(data[0], data[1], data[2]);
    }

    private void build() {
        URL csv = LocationTable.class.getResource(path);
        Path csvPath = getPathFromURL(csv);

        List<MeetingRoomLocation> meetingrooms = new ArrayList<MeetingRoomLocation>();
        log.info("opening csv at: " + csvPath);
        try (BufferedReader reader = Files.newBufferedReader(csvPath, Charset.forName("UTF-8"))) { // autoclose
            String line = null;
            while ((line = reader.readLine()) != null) {
                meetingrooms.add(parseLine(line));
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
        log.debug("meeting rooms" + meetingrooms);
    }

    private Path getPathFromURL(URL url) {
        try {
            return Paths.get(url.toURI());
        } catch (URISyntaxException e) {
            log.error("getPathFromURL", e);
            throw new IllegalStateException(e);
        }
    }

}
