package smartapps.vlab.testtheoplayersdk;

import android.app.Application;

import com.theoplayer.android.api.THEOplayerView;

/**
 * Created by Vinh.Tran on 4/24/18.
 **/
public class MyApplication extends Application {

    private static MyApplication sInstance;
    private THEOplayerView mCurrentPlayerView;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public THEOplayerView getCurrentPlayerView() {
        return mCurrentPlayerView;
    }

    public void setCurrentPlayerView(THEOplayerView currentPlayerView) {
        mCurrentPlayerView = currentPlayerView;
    }


}
