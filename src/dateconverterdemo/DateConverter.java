package dateconverterdemo;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date normalizer
 *
 * @author rkissvincze
 */
class DateConverter {

    private static final String DEFAULT_OUTPUT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String[] DATE_FORMATS_HUN = {"yyyy MM dd", "yyyy MM", "yyyy"};
    private static final String[] DATE_FORMATS_ENG = {"dd MM yy", "MM yyyy", "ddMMyyyy"};
    private static final String[] DATE_FORMATS_GER = {"MM dd yy", "MM dd yyyy", "MMddyyyy"};
    private static final String HUN_FORMAT_REGEX = "^(1|2)[0-9]\\d{2}";
    private static final String FOREIGN_FORMAT_REGEX = "^\\d{2}";
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

    static String normalizeDate(String dateInString, String dateOutFormat) {
        if (dateInString == null || dateInString.trim().isEmpty()) {
            return null;
        }
        if (dateOutFormat == null || dateOutFormat.trim().isEmpty()) {
            dateOutFormat = DEFAULT_OUTPUT_DATE_FORMAT;
        }
        String dateText = dateInString.trim().toLowerCase();
        Pattern pattern = Pattern.compile(HUN_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(dateText);
        String[] dateFormants; // = matcher.find() ? DATE_FORMATS_HUN : DATE_FORMATS_ENG;
        String origText = dateText;
        dateText = normalizeDateText(dateText, dateOutFormat);
        //System.out.print("orig: " + origText + "  normalizedText: " + dateText + "\t");
        if (matcher.find()) {
            dateFormants = DATE_FORMATS_HUN;
            //System.out.print("hun..");
        } else {
            if (isEngFormat(dateText)) {
                dateFormants = DATE_FORMATS_ENG;
                //System.out.print("eng..");
            } else {
                dateFormants = DATE_FORMATS_GER;
                //System.out.print("ger..");
            }
        }
        for (String dateFormatIn : dateFormants) {
            try {
                String date = transformDate(dateText, dateFormatIn, dateOutFormat);
                //System.out.print("\t " + dateFormatIn + "  ");
                return date;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private static boolean isEngFormat(String dateText) {
        // int germanDays = Integer.parseInt(dateText.trim().substring(0, 2));
        List<String> dateTags = Arrays.asList(dateText.trim().split(" "));
        if (dateTags.size() == 3) {
            return !isMoth(dateTags.get(0)) || !isDay(dateTags.get(1));
        }

        return false;
    }

    private static boolean isMoth(String dateTag) {
        int date = Integer.parseInt(dateTag);
        return date > 0 && date <= 12;
    }

    private static boolean isDay(String dateTag) {
        int date = Integer.parseInt(dateTag);
        return date > 0 && date <= 31;
    }

    private static String normalizeDateText(String dateText, String dateOutFormat) {
        Pattern pattern;
        Matcher matcher;

        dateText = dateText
                .replaceAll("[\n\r,\\.-]", " ")
                .replaceAll("/", " ")
                .replaceAll("\\s{2}", " ");

        for (Map.Entry<String, String> entry : DATE_MONTH_REGEX.entrySet()) {
            pattern = Pattern.compile(entry.getKey());
            matcher = pattern.matcher(dateText);

            if (matcher.find()) {
                return dateText
                        .replaceAll(entry.getKey(), entry.getValue());
            }
        }
        return dateText;
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

    private static String dateAsString(Date date, String dateFormatOut) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatOut, DEFAULT_LOCALE);
        formatter.setTimeZone(DEFAULT_TIMEZONE);
        return formatter.format(date);
    }

}
