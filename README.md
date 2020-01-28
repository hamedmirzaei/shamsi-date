# shamsi-date
This is some work around Shamsi/Gregorian/Persian date

# what it has
## holidays txt file
Each line is a holiday date in format `yyyymmdd`

## csv file
It includes following columns in order:

`ID,DATE_KEY,FULL_DATE_KEY,YEAR,MONTH,WEEK,DAY_OF_WEEK,DAY_OF_MONTH,DAY_OF_YEAR,IS_HOLIDAY,MONTH_PERSIAN_NAME,MONTH_ENGLISH_NAME,DAY_PERSIAN_NAME,DAY_ENGLISH_NAME`

* `ID`: an auto generated id started from `1`
* `DATE_KEY`: date integer value in format `yyyymmdd` for example `13950101`
* `FULL_DATE_KEY`: date string value in format `yyyy/mm/dd` for example `1395/01/01`
* `YEAR`: year in integer value for example `1395`
* `MONTH`: month in integer value for example `12`
* `WEEK`: week in integer value for example `52`
* `DAY_OF_WEEK`: day of week in integer value while `1` is `Saturday` and `7` in `Friday`
* `DAY_OF_MONTH`: day of month in integer value while `1` is first of month and `31`, `30` or `29` is the end of month
* `DAY_OF_YEAR`: day of year in integer value while `1` is first of year and `365` or `366` is the end of year
* `IS_HOLIDAY`: is it holiday or not. for `formal holidays` and `Fridays` the value is `true` and otherwise it's `false`
* `MONTH_PERSIAN_NAME`: month persian name in string. for example `فروردین`
* `MONTH_ENGLISH_NAME`: month persian name in string. for example `Farvardin`
* `DAY_PERSIAN_NAME`: day persian name in string. for example `شنبه`
* `DAY_ENGLISH_NAME`: day persian name in string. for example `Saturday`

## java source project
The folder `taghvim-shamsi` is the java project which we used to generate the csv file. You can use it if you
need to generate a customized csv file.
