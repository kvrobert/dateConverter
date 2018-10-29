package dateconverterdemo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author rkissvincze
 */
public class DateConverter {

    protected static final String DEFAULT_OUTPUT_DATE_FORMAT = "yyyy-MM-dd.";
    private static final String[] DATE_FORMATS = {"yyyy.MM.dd", "yyyy. MMM dd", "yyyy. MMMM dd",
        "yyyy. MM. dd", "yyyy-MM-dd", "MM/dd/yyyy", "dd/MM/yyyy", "dd.MM.yyyy"};
    static String inputDate;
    static LocalDate localDate = null;

    public DateConverter(String inputDate) {
        this.inputDate = inputDate;
    }

    public LocalDate getDate() {
        System.out.println("Datefomat..." + inputDate);
        System.out.println("macth.." + inputDate.matches("([0-9]{4})\\.?([0-9]{2})\\.?([0-9]{2})"));
        if (inputDate.matches("([0-9]{4})\\.?([0-9]{2})\\.?([0-9]{2})")) {
            createLocalDate("yyyy.MM.dd");
        } else if (inputDate.matches("([0-9]{4})\\/?([0-9]{2})\\/?([0-9]{2})")) {
            createLocalDate("yyyy/MM/dd");
        }
        return localDate;
    }

    public void createLocalDate(String format) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(format);
        this.localDate = LocalDate.parse(inputDate, dateFormat);
    }
    
    
    

    protected static String normalizeDate(Object dateInString) throws Exception {
        if (dateInString == null || dateInString.toString().trim().isEmpty()) {
            return null;
        }
        String dateText = dateInString.toString().trim();
        for (String dateFormatIn : DATE_FORMATS) {
            try {
                return transformDate(dateText, dateFormatIn, DEFAULT_OUTPUT_DATE_FORMAT);
            } catch (Exception ex) {
            }
        }
        throw new Exception(String.format("Invalid date format! Valid formats: %s; Date text: %s;", Arrays.toString(DATE_FORMATS), dateInString));
    }

    private static final String DEFAULT_TIMEZONE_ID = "UTC+02:00";
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone(DEFAULT_TIMEZONE_ID);
    private static final Locale DEFAULT_LOCALE = new Locale("hu");

    protected static String transformDate(String dateInString, String dateFormatIn, String dateFormatOut) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatIn, DEFAULT_LOCALE);
        formatter.setTimeZone(DEFAULT_TIMEZONE);
        Date date = formatter.parse(dateInString);
        return dateAsString(date, dateFormatOut);
    }

    protected static String dateAsString(Date date, String dateFormatOut) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatOut, DEFAULT_LOCALE);
        formatter.setTimeZone(DEFAULT_TIMEZONE);
        return formatter.format(date);
    }

}
