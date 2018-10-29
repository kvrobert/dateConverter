package dateconverterdemo;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rkissvincze
 */
public class DateConverterDemo {

     public static void main(String[] args) throws Exception {
         String date = "dec 11 2015";
         System.out.println("The NEW DATE.." + DateConverter.normalizeDate(date));

    }
    
}
