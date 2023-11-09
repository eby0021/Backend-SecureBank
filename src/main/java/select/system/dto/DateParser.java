package select.system.dto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public String extractDate(String dateTimeString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = inputFormat.parse(dateTimeString);
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        } catch (ParseException e) {
            // Handle the parse exception
            e.printStackTrace();
            return null; // or some default value
        }
    }
}
