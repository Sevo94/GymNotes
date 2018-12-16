package am.app.gymnotes;

import am.app.gymnotes.screens.activities.HomeActivity;

public interface DateUtil {

    void addDateToCalender(HomeActivity.Date date);

    String getNextDay();

    String getPreviousDay();

    String getFormattedDate();

    String getDate();

    String getPreviousDate();

    String getCurrentDate();

    String getNextDate();
}
