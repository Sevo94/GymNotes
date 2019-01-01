package am.app.gymnotes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import am.app.gymnotes.screens.activities.HomeActivity;

public class CalenderManager implements DateUtil {

    private static class InstanceHolder {
        private static CalenderManager mInstance = new CalenderManager();
    }

    public static CalenderManager getInstance() {
        return InstanceHolder.mInstance;
    }

    private Calendar currentDay;
    private Calendar previousDay;
    private Calendar nextDay;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);

    private HomeActivity.Date date = HomeActivity.Date.CURRENT;

    public void setDate(HomeActivity.Date date) {
        this.date = date;
    }

    private CalenderManager() {
        currentDay = new GregorianCalendar();
        previousDay = new GregorianCalendar();
        nextDay = new GregorianCalendar();

        previousDay.add(Calendar.DATE, -1);
        nextDay.add(Calendar.DATE, 1);
    }

    @Override
    public void addDateToCalender(HomeActivity.Date date) {
        switch (date) {
            case PREVIOUS:
                nextDay.add(Calendar.DATE, -1);
                currentDay.add(Calendar.DATE, -1);
                previousDay.add(Calendar.DATE, -1);
                break;
            case NEXT:
                previousDay.add(Calendar.DATE, 1);
                nextDay.add(Calendar.DATE, 1);
                currentDay.add(Calendar.DATE, 1);
                break;
        }
        this.date = date;
    }

    @Override
    public String getNextDay() {
        return dateFormat.format(nextDay.getTime());
    }

    @Override
    public String getPreviousDay() {
        return dateFormat.format(previousDay.getTime());
    }

    @Override
    public String getFormattedDate() {
        switch (date) {
            case PREVIOUS:
                return dateFormat.format(previousDay.getTime());
            case CURRENT:
                return dateFormat.format(currentDay.getTime());
            case NEXT:
                return dateFormat.format(nextDay.getTime());
            default:
                return "";
        }
    }

    @Override
    public String getDate() {

        switch (date) {
            case PREVIOUS:
                return getPreviousDate();
            case CURRENT:
                return getCurrentDate();
            case NEXT:
                return getNextDate();
            default:
                return "";
        }
    }

    @Override
    public String getPreviousDate() {
        int mYear, mMonth, mDay;

        mYear = previousDay.get(Calendar.YEAR);
        mMonth = previousDay.get(Calendar.MONTH) + 1;
        mDay = previousDay.get(Calendar.DAY_OF_MONTH);

        return "" + mDay + mMonth + mYear;
    }

    @Override
    public String getCurrentDate() {
        int mYear, mMonth, mDay;

        mYear = currentDay.get(Calendar.YEAR);
        mMonth = currentDay.get(Calendar.MONTH) + 1;
        mDay = currentDay.get(Calendar.DAY_OF_MONTH);

        return "" + mDay + mMonth + mYear;

    }

    @Override
    public String getNextDate() {
        int mYear, mMonth, mDay;

        mYear = nextDay.get(Calendar.YEAR);
        mMonth = nextDay.get(Calendar.MONTH) + 1;
        mDay = nextDay.get(Calendar.DAY_OF_MONTH);

        return "" + mDay + mMonth + mYear;
    }

    @Override
    public void updateDates(int year, int month, int day) {
        currentDay.set(Calendar.YEAR, year);
        currentDay.set(Calendar.MONTH, month);
        currentDay.set(Calendar.DAY_OF_MONTH, day);

        if (month == 0 && day == 1) {
            previousDay.set(Calendar.YEAR, year - 1);
            previousDay.set(Calendar.MONTH, 11);
            previousDay.set(Calendar.DAY_OF_MONTH, 31);
        } else {
            previousDay.set(Calendar.YEAR, year);
            previousDay.set(Calendar.MONTH, month);
            previousDay.set(Calendar.DAY_OF_MONTH, day - 1);
        }

        if (month == 11 && day == 31) {
            nextDay.set(Calendar.YEAR, year + 1);
            nextDay.set(Calendar.MONTH, 0);
            nextDay.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            nextDay.set(Calendar.YEAR, year);
            nextDay.set(Calendar.MONTH, month);
            nextDay.set(Calendar.DAY_OF_MONTH, day + 1);
        }
    }

    @Override
    public DateModel getSelectedPageDate() {
        return new DateModel(currentDay.get(Calendar.YEAR),
                currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH));
    }

    public static class DateModel {

        int year;
        int month;
        int day;

        public DateModel(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }
}
