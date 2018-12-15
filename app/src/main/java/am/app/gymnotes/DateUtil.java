package am.app.gymnotes;

public interface DateUtil {

    void addDateToCalender(int amount);

    void addNextDay();

    void addPreviousDay();

    String getNextDay();

    String getPreviousDay();

    String getCurrentDate();

    String getFormattedDate();

    String getDate();
}
