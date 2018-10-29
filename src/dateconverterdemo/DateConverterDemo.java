package dateconverterdemo;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author rkissvincze
 */
public class DateConverterDemo {

     public static void main(String[] args) throws Exception {
         DateConverter dateConverter = new DateConverter("2018/05/10");
         
         
         //System.out.println(dateConverter.getDate().toString());
         System.out.println("The new date.." + DateConverter.normalizeDate("2018  05 10."));
         
    }
    
}
