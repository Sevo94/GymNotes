package am.app.gymnotes;

import android.app.Application;

public class GymNotesApplication extends Application {

    private static GymNotesApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        final Thread.UncaughtExceptionHandler eh = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                eh.uncaughtException(thread, ex);
            }
        });
    }

    public static GymNotesApplication getmInstance() {
        return mInstance;
    }
}
