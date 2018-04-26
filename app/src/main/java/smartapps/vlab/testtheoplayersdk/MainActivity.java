package smartapps.vlab.testtheoplayersdk;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import smartapps.vlab.testtheoplayersdk.list.VideoListFragment;
import smartapps.vlab.testtheoplayersdk.util.OrientationDetector;

public class MainActivity extends AppCompatActivity implements OrientationDetector.Listener {

    private OrientationDetector mOrientation;
    private TextView mTextInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextInfo = findViewById(R.id.tv_info);
        mOrientation = new OrientationDetector(this);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment(), "main_fragment")
                    .commit();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int backStackSize = getSupportFragmentManager().getBackStackEntryCount();
                System.out.println(">>> backStackSize " + backStackSize);
                //FragmentManager.BackStackEntry backStackEntryAt = getSupportFragmentManager().getBackStackEntryAt(backStackSize - 1);// top fragment
                if(backStackSize == 0){
                    MainFragment mainFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("main_fragment");
                    if(mainFragment != null){
                        mainFragment.updateVideoUI();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOrientation.startListening(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mOrientation.stopListening();
    }

    @Override
    public void onOrientationChanged(float pitch, float roll) {
        String orientation = "";
        // roll : rotate left/right, pith : rotate up/down
        if(Math.abs(roll) < 70){
            orientation = "PORTRAIT";
        } else {
            orientation = "LANDSCAPE";
        }

        mTextInfo.setText("Pitch : " + pitch
                + "\nRoll " + roll +
                "\n" + orientation);

    }


    public void openShareVideo(View shareView) {
        getSupportFragmentManager().beginTransaction()
                .addSharedElement(shareView, "video_container")
                .add(R.id.fragment_container, ShareVideoFragment.newInstance(), ShareVideoFragment.class.getSimpleName())
                .addToBackStack(ShareVideoFragment.class.getSimpleName())
                .commit();
    }

    public void openVideoList() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, VideoListFragment.newInstance(), VideoListFragment.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
