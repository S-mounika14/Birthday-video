package com.birthday.video.maker.agecalculator;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ChangeDateFormat {

    private static String date1;
    private static String date2;
    private static String date3;

    public ChangeDateFormat(String str) {

        String str2;
        date1 = changeCaharactersInDate(str);
        date2 = str.toUpperCase();
        if (date2.indexOf("/") > 0) {
            str2 = "/";
        } else if (date2.indexOf(".") > 0) {
            str2 = ".";
        } else if (date2.indexOf("-") > 0) {
            str2 = "-";
        } else {
            return;
        }
        date3 = str2;

    }

    static String getHintString() {
        return date2;
    }

    static String getValue1(long j) {
        return String.format(Locale.getDefault(), "%,d", new Object[]{Long.valueOf(j)});
    }

    static String chnagePatternInDateTime(DateTime dateTime) {
        return changePattern().print(dateTime).replace("/", date3);
    }

    static String chnageDateTimePattern3(DateTime dateTime, boolean z) {
        return DateTimeFormat.forPattern(z ? "HH:mm" : "hh:mm a").print(dateTime).toUpperCase();
    }

    static DateTime changeDateFormat1(int i, int i2, int i3) {
        DateTimeFormatter e = changePattern();
        String[] split = date1.split("/");
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        for (String str : split) {
            if (str.toUpperCase().equals("DD")) {
                sb.append(i3);
            }
            if (str.toUpperCase().equals("MM")) {
                sb.append(i2);
            }
            if (str.toUpperCase().equals("YYYY")) {
                sb.append(i);
            }
            if (i4 == 2) {
                break;
            }
            sb.append("/");
            i4++;
        }
        return localDateToDateTime(LocalDate.parse(sb.toString(), e));
    }

    static DateTime getDateTimeWithString(String str) {
        DateTime dateTime = new DateTime();


        String[] ssss = str.split("/");
        int year = Integer.parseInt(ssss[2]);
        int month = Integer.parseInt(ssss[1]);
        int date = Integer.parseInt(ssss[0]);

        dateTime.withYear(year);
        dateTime.withMonthOfYear(month);
        dateTime.withDayOfMonth(date);


        return localDateToDateTime(LocalDate.parse(changeCaharactersInDate(str), changePattern()));

    }

    static DateTime getDateTimeBetweenDates(DateTime dateTime, DateTime dateTime2) {
        DateTime plusYears = dateTime2.plusYears(1);
        return (!dateTime.year().isLeap() || dateTime.getDayOfMonth() != 29 || dateTime.getMonthOfYear() != 2 || !plusYears.year().isLeap()) ? plusYears : plusYears.plusDays(1);
    }


    private static DateTime localDateToDateTime(LocalDate localDate) {

        try {

            DateTime dateTime = localDate.toDateTimeAtStartOfDay();

            return dateTime;
        } catch (Exception unused) {
            return new LocalDate().toDateTimeAtStartOfDay();
        }
    }

    static boolean m2586a(int i) {
        return i % 400 == 0 || (i % 4 == 0 && i % 100 != 0);
    }

    static DateTime getDateTime() {

        try {
            return new LocalDate().toDateTimeAtCurrentTime(DateTimeZone.forID(TimeZone.getDefault().getID()));
        } catch (Exception unused) {
            return new LocalDate().toDateTimeAtCurrentTime();
        }

    }

    static String getDayOfWeek(DateTime dateTime) {
        return dateTime.dayOfWeek().getAsText(Locale.getDefault());
    }

    static DateTime getDateTime2() {
        try {
            return new LocalDate().toDateTimeAtStartOfDay().withZone(DateTimeZone.forID(TimeZone.getDefault().getID()));
        } catch (Exception unused) {
            return new LocalDate().toDateTimeAtStartOfDay();
        }
    }

    static boolean checkStringValue(String str) {
        if (str.length() <= 0) {
            return false;
        }
        try {
            DateTime a = getDateTimeWithString(str);
            int year = a.getYear();
            DateTime dateTime = new DateTime();
            if (year >= dateTime.minusYears(DateTimeConstants.MILLIS_PER_SECOND).getYear()) {
                if (year <= dateTime.plusYears(DateTimeConstants.MILLIS_PER_SECOND).getYear()) {
                    DateTimeFormatter forPattern = DateTimeFormat.forPattern("MM/dd/yyyy");
                    StringBuilder sb = new StringBuilder();
                    sb.append(a.getMonthOfYear());
                    sb.append("/");
                    sb.append(a.getDayOfMonth());
                    sb.append("/");
                    sb.append(year);
                    LocalDate.parse(sb.toString(), forPattern);
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    static String chnageDateTimePattern2(DateTime dateTime) {
        return DateTimeFormat.forPattern("MMM dd, yyyy").print(dateTime);
    }

    private static String changeCaharactersInDate(String str) {
        String str2;
        if (str.indexOf(".") > 0) {
            str2 = ".";
        } else if (str.indexOf("-") <= 0) {
            return str;
        } else {
            str2 = "-";
        }
        return str.replace(str2, "/");

    }

    static DateTime getDateTimeObjectWithOtherFormat(DateTime dateTime) {
        LocalDate now = LocalDate.now(DateTimeZone.getDefault());
        int year = now.getYear();
        int monthOfYear = dateTime.getMonthOfYear();
        int dayOfMonth = dateTime.getDayOfMonth();
        if (now.getMonthOfYear() > monthOfYear || (now.getMonthOfYear() == monthOfYear && now.getDayOfMonth() > dayOfMonth)) {
            year++;
        }
        if (!m2586a(year) && dayOfMonth == 29 && monthOfYear == 2) {
            dayOfMonth--;
        }
        return changeDateFormat1(year, monthOfYear, dayOfMonth);
    }

    private static DateTimeFormatter changePattern() {
        return DateTimeFormat.forPattern(date1);
    }

    private static String m2601f() {
        TimeZone timeZone = TimeZone.getDefault();
        int offset = timeZone.getOffset(GregorianCalendar.getInstance(timeZone).getTimeInMillis());
        String format = String.format(Locale.getDefault(), "%02d:%02d", new Object[]{Integer.valueOf(Math.abs(offset / DateTimeConstants.MILLIS_PER_HOUR)), Integer.valueOf(Math.abs((offset / DateTimeConstants.MILLIS_PER_MINUTE) % 60))});
        StringBuilder sb = new StringBuilder();
        sb.append("GMT");
        sb.append(offset >= 0 ? "+" : "-");
        sb.append(format);
        return sb.toString();
    }
}
