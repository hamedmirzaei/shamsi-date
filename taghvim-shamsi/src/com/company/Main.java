package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private final static String HEADER_LINE =
            "ID,DATE_KEY,FULL_DATE_KEY,YEAR,DAY_OF_WEEK,IS_HOLIDAY,MONTH_PERSIAN_NAME,DAY_PERSIAN_NAME";//csv header
    private final static String CSV_DELIMITER = ",";// csv delimiter
    private final static String DATE_DELIMITER = "/";// date delimiter for example 1295/01/01
    private final static String HOLIDAYS_FILE_PATH = "data/holidays.txt";// holidays text file path
    private final static String HOLIDAY_TRUE_VALUE = "1";// value of IS_HOLIDAY column in case of being holiday
    private final static String HOLIDAY_FALSE_VALUE = "0";// value of IS_HOLIDAY column in case of not being holiday

    private final static int FROM_YEAR = 1395;// lower bound of year
    private final static int TO_YEAR = 1410;// higher bound of year

    private final static int FIRST_MONTH = 1;// first month is 1
    private final static int LAST_MONTH = 12;// last month is 12
    private final static int MONTH_31 = 6;// first six months have 31 days
    private final static int MONTH_30 = 11;// next five months have 30 days

    private final static int WEEK_DAYS = 7;// number of days in a week
    private final static int WEEK_DAY_FIRST = 1;// first day index
    private final static int WEEK_DAY_LAST = 7;// last day index
    private final static int WEEK_DAY_START_1395 = 2;// first day of 1395 which is sunday [13950101 is sunday]

    private final static int FROM_DAY = 1;// first day of month
    private final static int DAY_31 = 31;// number of days in a 31 day month
    private final static int DAY_30 = 30;// number of days in a 30 day month
    private final static int DAY_29 = 29;// number of days in a 29 day month
    private final static int FIRST_LEAP_YEAR = 3;// first leap year is 0003
    private final static int LEAP_YEAR_INTERVAL = 4;// each four year we have a leap year

    public static void main(String[] args) throws IOException {
        // read holidays to heap/ram/memory
        List<String> holidays = Files.readAllLines(Paths.get(HOLIDAYS_FILE_PATH))
                .stream().filter(line -> !line.trim().equals("")).collect(Collectors.toList());

        int weekDay = WEEK_DAY_START_1395;
        int monthDays;

        System.out.println(HEADER_LINE);
        int id = 1;
        for (int year = FROM_YEAR; year <= TO_YEAR; year++) {
            for (int month = FIRST_MONTH; month <= LAST_MONTH; month++) {
                if (month <= MONTH_31) { //31 days
                    monthDays = DAY_31;
                } else if (month <= MONTH_30) {//30 days
                    monthDays = DAY_30;
                } else {//29 or 30 days
                    if ((FIRST_LEAP_YEAR - year) % LEAP_YEAR_INTERVAL == 0) {//leap year => 30 days
                        monthDays = DAY_30;
                    } else {
                        monthDays = DAY_29;//normal year => 29 days
                    }
                }
                for (int day = FROM_DAY; day <= monthDays; day++) {
                    String fourDigitsYear = String.format("%04d", year);
                    String twoDigitsMonth = String.format("%02d", month);
                    String twoDigitsDay = String.format("%02d", day);

                    String date = fourDigitsYear + twoDigitsMonth + twoDigitsDay;
                    String fullDate = fourDigitsYear + DATE_DELIMITER + twoDigitsMonth + DATE_DELIMITER + twoDigitsDay;

                    String isHoliday = (weekDay == WEEK_DAYS || holidays.contains(date)) ? HOLIDAY_TRUE_VALUE : HOLIDAY_FALSE_VALUE;

                    System.out.println(id + CSV_DELIMITER +
                            date + CSV_DELIMITER +
                            fullDate + CSV_DELIMITER +
                            year + CSV_DELIMITER +
                            weekDay + CSV_DELIMITER +
                            isHoliday + CSV_DELIMITER +
                            getMonthNamePersian(month) + CSV_DELIMITER +
                            getDayNamePersian(weekDay));

                    weekDay++;
                    if (weekDay > WEEK_DAY_LAST)
                        weekDay = WEEK_DAY_FIRST;
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

}
