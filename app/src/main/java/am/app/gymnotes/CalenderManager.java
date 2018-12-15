package am.app.gymnotes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalenderManager implements DateUtil {

    private static class InstanceHolder {
        private static CalenderManager mInstance = new CalenderManager();
    }

    public static CalenderManager getInstance() {
        return InstanceHolder.mInstance;
    }

    private Calendar gc;
    private Calendar previousDay;
    private Calendar nextDay;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);

    private CalenderManager() {
        gc = new GregorianCalendar();
        previousDay = new GregorianCalendar();
        nextDay = new GregorianCalendar();
    }


    @Override
    public void addDateToCalender(int amount) {
        if (amount == 1) {
            previousDay.add(Calendar.DATE, amount);
            nextDay.add(Calendar.DATE, amount);
            gc = nextDay;
        } else if (amount == -1) {
            nextDay.add(Calendar.DATE, amount);
            previousDay.add(Calendar.DATE, amount);
            gc = previousDay;
        }
    }

    @Override
    public void addNextDay() {
        nextDay.add(Calendar.DATE, 1);
    }

    @Override
    public void addPreviousDay() {
        previousDay.add(Calendar.DATE, -1);
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
    public String getCurrentDate() {
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    @Override
    public String getFormattedDate() {
        return dateFormat.format(gc.getTime());
    }

    @Override
    public String getDate() {
        Calendar c = Calendar.getInstance();

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        return "" + mDay + mMonth + mYear;
    }
}
