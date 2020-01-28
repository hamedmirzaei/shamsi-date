package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String HEADER_LINE = "ID,DATE_KEY,FULL_DATE_KEY,YEAR,SEASON,MONTH,WEEK,DAY_OF_WEEK," +
            "DAY_OF_MONTH,DAY_OF_YEAR,IS_HOLIDAY,SEASON_PERSIAN_NAME,SEASON_ENGLISH_NAME,MONTH_PERSIAN_NAME," +
            "MONTH_ENGLISH_NAME,DAY_PERSIAN_NAME,DAY_ENGLISH_NAME";//csv header
    
    private static final String CSV_DELIMITER = ",";// csv delimiter
    private static final String DATE_DELIMITER = "/";// date delimiter for example 1295/01/01
    private static final String STRING_QUALIFIER = "";// qualifier for strings

    private static final String HOLIDAYS_FILE_PATH = "data/holidays.txt";// holidays text file path
    private static final String HOLIDAY_TRUE_VALUE = "1";// value of IS_HOLIDAY column in case of being holiday
    private static final String HOLIDAY_FALSE_VALUE = "0";// value of IS_HOLIDAY column in case of not being holiday
    private static final String HOLIDAYS_QUALIFIER = "";// qualifier for is_holiday field

    private static final int FROM_YEAR = 1395;// lower bound of year
    private static final int TO_YEAR = 1410;// higher bound of year

    private static final int SEASON_MONTHS = 3;// number of months per season

    private static final int FIRST_MONTH = 1;// first month is 1
    private static final int LAST_MONTH = 12;// last month is 12
    private static final int MONTH_31 = 6;// first six months have 31 days
    private static final int MONTH_30 = 11;// next five months have 30 days

    private static final int WEEK_DAYS = 7;// number of days in a week
    private static final int WEEK_DAY_FIRST = 1;// first day index
    private static final int WEEK_DAY_LAST = 7;// last day index
    private static final int WEEK_DAY_START_1395 = 2;// first day of 1395 which is sunday [13950101 is sunday]

    private static final int FIRST_DAY_OF_MONTH = 1;// first day of month
    private static final int DAY_31 = 31;// number of days in a 31 day month
    private static final int DAY_30 = 30;// number of days in a 30 day month
    private static final int DAY_29 = 29;// number of days in a 29 day month
    private static final int FIRST_LEAP_YEAR = 3;// first leap year is 0003
    private static final int LEAP_YEAR_INTERVAL = 4;// each four year we have a leap year

    public static void main(String[] args) throws IOException {
        // read holidays to heap/ram/memory
        List<String> holidays = Files.readAllLines(Paths.get(HOLIDAYS_FILE_PATH))
                .stream().filter(line -> !line.trim().equals("")).collect(Collectors.toList());

        int dayOfWeek = WEEK_DAY_START_1395;
        int dayOfYear;
        int monthDays;

        System.out.println(HEADER_LINE);
        int id = 1;
        for (int year = FROM_YEAR; year <= TO_YEAR; year++) {
            dayOfYear = 0;
            for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
                if (month <= MONTH_31) { // 31 days
                    monthDays = DAY_31;
                } else if (month <= MONTH_30) {// 30 days
                    monthDays = DAY_30;
                } else {// 29 or 30 days
                    if ((FIRST_LEAP_YEAR - year) % LEAP_YEAR_INTERVAL == 0) {// leap year => 30 days
                        monthDays = DAY_30;
                    } else {
                        monthDays = DAY_29;// normal year => 29 days
                    }
                }
                for (int dayOfMonth = FIRST_DAY_OF_MONTH; dayOfMonth <= monthDays; dayOfMonth++) {
                    dayOfYear++;
                    String fourDigitsYear = String.format("%04d", year);
                    String twoDigitsMonth = String.format("%02d", month);
                    String twoDigitsDay = String.format("%02d", dayOfMonth);

                    String date = fourDigitsYear + twoDigitsMonth + twoDigitsDay;
                    String fullDate = fourDigitsYear + DATE_DELIMITER + twoDigitsMonth + DATE_DELIMITER + twoDigitsDay;

                    String holidayValue = (dayOfWeek == WEEK_DAYS || holidays.contains(date)) ? HOLIDAY_TRUE_VALUE : HOLIDAY_FALSE_VALUE;

                    int week = (dayOfYear % WEEK_DAYS == 0) ? (dayOfYear / WEEK_DAYS) : (dayOfYear / WEEK_DAYS + 1);
                    int season = (month % SEASON_MONTHS == 0) ? (dayOfYear / SEASON_MONTHS) : (dayOfYear / SEASON_MONTHS + 1);

                    System.out.println(id + CSV_DELIMITER +
                            date + CSV_DELIMITER +
                            STRING_QUALIFIER + fullDate + STRING_QUALIFIER + CSV_DELIMITER +
                            year + CSV_DELIMITER +//year number
                            season + CSV_DELIMITER +// season of year
                            month + CSV_DELIMITER +// month of year
                            week + CSV_DELIMITER +// week of year
                            dayOfWeek + CSV_DELIMITER +// day of week
                            dayOfMonth + CSV_DELIMITER +// day of month
                            dayOfYear + CSV_DELIMITER +// day of year
                            HOLIDAYS_QUALIFIER + holidayValue + HOLIDAYS_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getSeasonNamePersian(season) + STRING_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getSeasonNameEnglish(season) + STRING_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getMonthNamePersian(month) + STRING_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getMonthNameEnglish(month) + STRING_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getDayNamePersian(dayOfWeek) + STRING_QUALIFIER + CSV_DELIMITER +
                            STRING_QUALIFIER + getDayNameEnglish(dayOfWeek) + STRING_QUALIFIER
                    );

                    dayOfWeek++;
                    if (dayOfWeek > WEEK_DAY_LAST)
                        dayOfWeek = WEEK_DAY_FIRST;
                    id++;
                }
            }
        }
    }

    private static String getMonthNamePersian(Integer monthNumber) {
        switch (monthNumber) {
            case 1:
                return "فروردین";
            case 2:
                return "اردیبهشت";
            case 3:
                return "خرداد";
            case 4:
                return "تیر";
            case 5:
                return "مرداد";
            case 6:
                return "شهریور";
            case 7:
                return "مهر";
            case 8:
                return "آبان";
            case 9:
                return "آذر";
            case 10:
                return "دی";
            case 11:
                return "بهمن";
        }
        return "اسفند";
    }

    private static String getMonthNameEnglish(Integer monthNumber) {
        switch (monthNumber) {
            case 1:
                return "Farvardin";
            case 2:
                return "Ordibehesht";
            case 3:
                return "Khordad";
            case 4:
                return "Tir";
            case 5:
                return "Mordad";
            case 6:
                return "Shahrivar";
            case 7:
                return "Mehr";
            case 8:
                return "Aban";
            case 9:
                return "Azar";
            case 10:
                return "Dey";
            case 11:
                return "Bahman";
        }
        return "Esfand";
    }

    private static String getDayNamePersian(Integer dayNumber) {
        switch (dayNumber) {
            case 1:
                return "شنبه";
            case 2:
                return "یکشنبه";
            case 3:
                return "دوشنبه";
            case 4:
                return "سه شنبه";
            case 5:
                return "چهارشنبه";
            case 6:
                return "پنجشنبه";
        }
        return "جمعه";
    }

    private static String getDayNameEnglish(Integer dayNumber) {
        switch (dayNumber) {
            case 1:
                return "Saturday";
            case 2:
                return "Sunday";
            case 3:
                return "Monday";
            case 4:
                return "Tuesday";
            case 5:
                return "Wednesday";
            case 6:
                return "Thursday";
        }
        return "Friday";
    }

    private static String getSeasonNamePersian(Integer seasonNumber) {
        switch (seasonNumber) {
            case 1:
                return "بهار";
            case 2:
                return "تابستان";
            case 3:
                return "پاییز";
        }
        return "زمستان";
    }

    private static String getSeasonNameEnglish(Integer seasonNumber) {
        switch (seasonNumber) {
            case 1:
                return "Spring";
            case 2:
                return "Summer";
            case 3:
                return "Fall";
        }
        return "Winter";
    }

}
