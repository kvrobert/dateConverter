package dateconverterdemo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Date normalizer
 * @author rkissvincze
 */
class DateConverter {

    private static final String DEFAULT_OUTPUT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String[] DATE_FORMATS_HUN = {"yyyy.MM.dd", "yyyy. MM. dd", "yyyy MM dd", "yyyy-MM-dd",
        "yyyy/MM/dd", "yyyy MM dd", "yyyy MM. dd", "yyyy. MM dd", "yyyy. MM dd.", "yyyy MM dd."};
    private static final String[] DATE_FORMATS_ENG = {"MM/dd/yyyy", "dd.MM.yyyy", "MM. dd yyyy", "dd MM. yyyy"};
    private static final String DATE_MATCH_REGEX = "[a-zA-Z]";
    private static final String HUN_FORMAT_REGEX = "^\\d{4}";
    private static final Map<String, String> DATE_MONTH_REGEX = new HashMap<>();

    static {
        DATE_MONTH_REGEX.put("jan[.]?[á-úa-z]*", "01");
        DATE_MONTH_REGEX.put("feb[.]?[á-úa-z]*", "02");
        DATE_MONTH_REGEX.put("mar[.]?\\w*|már[.]?\\w*|marc[.]?\\w*|márc[.]?\\w*", "03");
        DATE_MONTH_REGEX.put("apr[.]?\\w*|ápr[.]?\\w*", "04");
        DATE_MONTH_REGEX.put("maj[.]?\\w*|máj[.]?\\w*|may[.]?\\w*", "05");
        DATE_MONTH_REGEX.put("jun[.]?\\w*|jún[.]?\\w*", "06");
        DATE_MONTH_REGEX.put("jul[.]?\\w*|júl[.]?\\w*", "07");
        DATE_MONTH_REGEX.put("au[.]?\\w*|aug[.]?\\w*", "08");
        DATE_MONTH_REGEX.put("szep[.]?\\w*|sep[.]?\\w*", "09");
        DATE_MONTH_REGEX.put("okt[.]?[á-úa-z]*", "10");
        DATE_MONTH_REGEX.put("nov[.]?\\w*", "11");
        DATE_MONTH_REGEX.put("dec[.]?\\w*", "12");
    }
/*** Normalize the incoming dates
 * 
 * @param dateInString Date in String format
 * @param dateOutFormat Date output format, defalult: yyyy-MM-dd
 * @return Normalized Date in String, null if date is not suitable
 * @throws -
 */

    static String normalizeDate(String dateInString, String dateOutFormat) throws Exception {
        if (dateInString == null || dateInString.trim().isEmpty()) {
            return null;
        }
        if(dateOutFormat == null || dateOutFormat.trim().isEmpty()){
            dateOutFormat = DEFAULT_OUTPUT_DATE_FORMAT;
        }
        String dateText = dateInString.trim().toLowerCase();
        Pattern pattern = Pattern.compile(HUN_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(dateText);
        String[] dateFormants = matcher.find() ? DATE_FORMATS_HUN : DATE_FORMATS_ENG;
//        System.out.println("used date for.. " + dateText +" is " + Arrays.toString(dateFormants));
//        System.out.println("A MAP:" + DATE_MONTH_REGEX.size());
        for (String dateFormatIn : dateFormants) {
            try {
                return transformDate(dateText, dateFormatIn, dateOutFormat);
            } catch (Exception ignored) {
            }
        }
        pattern = Pattern.compile(DATE_MATCH_REGEX);
        matcher = pattern.matcher(dateText);
        if (matcher.find()) {
//            System.out.println("FINDE by..." + dateText);
            try {
                return transformDateWithText(dateText, dateOutFormat);
            } catch (Exception ignored) {
            }
        }
//        throw new Exception(String.format("Invalid date format! Valid formats: %s; Date text: %s;", Arrays.toString(dateFormants), dateInString));
// Because of I don't want Exception...simple drop the unsuitable date
        return null;
    }

    private static String transformDateWithText(String dateText, String dateOutFormat) throws Exception {
        Pattern pattern;
        Matcher matcher;
        for (Map.Entry<String, String> entry : DATE_MONTH_REGEX.entrySet()) {
            pattern = Pattern.compile(entry.getKey());
            matcher = pattern.matcher(dateText);
            if (matcher.find()) {
                return normalizeDate(dateText.replaceAll(entry.getKey(), entry.getValue() ), dateOutFormat);
            }
        }
        return null;
    }

    private static final String DEFAULT_TIMEZONE_ID = "UTC+02:00";
    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone(DEFAULT_TIMEZONE_ID);
    private static final Locale DEFAULT_LOCALE = new Locale("hu");

    private static String transformDate(String dateInString, String dateFormatIn, String dateFormatOut) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatIn, DEFAULT_LOCALE);
        formatter.setTimeZone(DEFAULT_TIMEZONE);
        Date date = formatter.parse(dateInString);
        return dateAsString(date, dateFormatOut);
    }

    private static String dateAsString(Date date, String dateFormatOut) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatOut, DEFAULT_LOCALE);
        formatter.setTimeZone(DEFAULT_TIMEZONE);
        return formatter.format(date);
    }

}
