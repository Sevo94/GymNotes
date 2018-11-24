package am.app.gymnotes;

import android.app.Application;

public class GymNotesApplication extends Application {

    private boolean appFirstStart;
    private static GymNotesApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        appFirstStart = true;
    }

    public static GymNotesApplication getmInstance() {
        return mInstance;
    }

    public boolean isAppFirstStart() {
        return appFirstStart;
    }

    public void setAppFirstStart(boolean appFirstStart) {
        this.appFirstStart = appFirstStart;
    }
}
