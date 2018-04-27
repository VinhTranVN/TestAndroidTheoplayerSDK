package smartapps.vlab.testtheoplayersdk;

import android.app.Application;
import android.provider.Settings;
import android.widget.Toast;

import com.theoplayer.android.api.THEOplayerView;

import smartapps.vlab.testtheoplayersdk.util.UiUtils;

import static smartapps.vlab.testtheoplayersdk.util.OrientationDetector.LANDSCAPE;
import static smartapps.vlab.testtheoplayersdk.util.OrientationDetector.PORTRAIT;

/**
 * Created by Vinh.Tran on 4/24/18.
 **/
public class MyApplication extends Application {

    private int mCurrentOrientation = -1;

    private static MyApplication sInstance;
    private THEOplayerView mCurrentPlayerView;
    private boolean mIsRotation;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        mIsRotation = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

    public static MyApplication getInstance() {
        return sInstance;
    }

    public THEOplayerView getCurrentPlayerView() {
        return mCurrentPlayerView;
    }

    public void setCurrentPlayerView(THEOplayerView currentPlayerView) {
        if(mCurrentPlayerView != currentPlayerView){
            System.out.println(">>> MyApplication setCurrentPlayerView " + currentPlayerView.hashCode());
            mCurrentPlayerView = currentPlayerView;
        }
    }

    /**
     *
     * @param direction 0: portrait, 1: landscape
     */
    public void rotateScreen(int direction){

        boolean isRotationOn = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
        if (isRotationOn) {
            if(isRotationOn != mIsRotation){
                Toast.makeText(getApplicationContext(), "Auto Rotate is ON", Toast.LENGTH_SHORT).show();
            }
            mIsRotation = isRotationOn;
        } else {
            if(isRotationOn != mIsRotation){
                Toast.makeText(getApplicationContext(), "Auto Rotate is OFF", Toast.LENGTH_SHORT).show();
            }
            mIsRotation = isRotationOn;
            return;
        }

        if(mCurrentPlayerView == null){
            return;
        }

        // check visible 100%
        boolean isFullVisible = UiUtils.isViewVisible(mCurrentPlayerView, 1);
        if(!isFullVisible){
            return;
        }

        if(direction == PORTRAIT && mCurrentOrientation != PORTRAIT){

            System.out.println("*** PORTRAIT  exitFullScreen getReadyState " + mCurrentPlayerView.getPlayer().getReadyState().name());

            mCurrentOrientation = PORTRAIT;

            if (mCurrentPlayerView.getFullScreenManager().isFullScreen()) {
                mCurrentPlayerView.getFullScreenManager().exitFullScreen();
            }
        } else if (direction == LANDSCAPE && mCurrentOrientation != LANDSCAPE){
            System.out.println("*** LANDSCAPE requestFullScreen getReadyState " + mCurrentPlayerView.getPlayer().getReadyState().name());

            mCurrentOrientation = LANDSCAPE;
            if(!mCurrentPlayerView.getFullScreenManager().isFullScreen()){
                mCurrentPlayerView.getFullScreenManager().requestFullScreen();
            }
        }
    }

}
