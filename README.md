# shamsi-date
This is some work around Shamsi/Gregorian/Persian date

# What it includes
## holidays txt file
Each line is a holiday date in format yyyymmdd

## csv file
It includes following columns in order:

ID,DATE_KEY,FULL_DATE_KEY,YEAR,DAY_OF_WEEK,IS_HOLIDAY,MONTH_PERSIAN_NAME,DAY_PERSIAN_NAME

ID: an auto generated id started from 1
DATE_KEY: date integer value in format yyyymmdd for example 13950101
FULL_DATE_KEY: date string value in format yyyy/mm/dd for example 1395/01/01
YEAR: year in integer value for example 1395
DAY_OF_WEEK: day of week in integer value while 1 is Saturday and 7 in Friday
IS_HOLIDAY: is it holiday or not. for formal holidays and Fridays the value is true and otherwise it's false
MONTH_PERSIAN_NAME: month persian name in string. for example فروردین
DAY_PERSIAN_NAME: day persian name in string. for example شنبه

## java source project
The folder taghvim-shamsi is the java project which we used to generate the csv file. You can use it if you
need to generate a customized csv file.
