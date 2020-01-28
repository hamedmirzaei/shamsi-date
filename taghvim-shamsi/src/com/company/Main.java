package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> holidays = Files.readAllLines(Paths.get("data/holidays.txt")).stream().filter(line -> !line.trim().equals("")).collect(Collectors.toList());

        Integer leapYear = 1395;
        Integer weekDay = 2;// first day of 1395 which is sunday
        Integer monthDays = 31;

        System.out.println("ID,DATE_KEY,FULL_DATE_KEY,YEAR,DAY_OF_WEEK,IS_HOLIDAY,MONTH_PERSIAN_NAME,DAY_PERSIAN_NAME");
        int id = 1;
        for (int year = 1395; year <= 1410; year++) {
            for (int month = 01; month <= 12; month++) {
                if (month <= 6) { //31 days
                    monthDays = 31;
                } else if (month <= 11) {//30 days
                    monthDays = 30;
                } else if (month == 12) {//29 or 30 days
                    if ((leapYear - year) % 4 == 0) {//leap year => 30 days
                        monthDays = 30;
                    } else {
                        monthDays = 29;//normal year => 29 days
                    }
                }
                for (int day = 01; day <= monthDays; day++) {
                    String date = year + String.format("%02d", month) + String.format("%02d", day);
                    String fullDate = year + "/" + String.format("%02d", month) + "/" + String.format("%02d", day);
                    Integer isHoliday = (weekDay == 7 || holidays.contains(date)) ? 1 : 0;
                    System.out.println(id + "," +
                            date + "," +
                            fullDate + "," +
                            year + "," +
                            weekDay + "," +
                            isHoliday + "," +
                            getMonthNamePersian(month) + "," +
                            getDayNamePersian(weekDay));
                    weekDay++;
                    if (weekDay == 8)
                        weekDay = 1;
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
