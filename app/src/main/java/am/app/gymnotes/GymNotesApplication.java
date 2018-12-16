package am.app.gymnotes;

import android.app.Application;

public class GymNotesApplication extends Application {

    private static GymNotesApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static GymNotesApplication getmInstance() {
        return mInstance;
    }
}
