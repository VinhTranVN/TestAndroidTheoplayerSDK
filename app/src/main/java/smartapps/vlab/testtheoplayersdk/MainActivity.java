package smartapps.vlab.testtheoplayersdk;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;

import com.theoplayer.android.api.THEOplayerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commit();
        }
    }


    public void openShareVideo(THEOplayerView theOplayerView) {
        getSupportFragmentManager().beginTransaction()
                .addSharedElement(theOplayerView, ViewCompat.getTransitionName(theOplayerView))
                .add(R.id.fragment_container, ShareVideoFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    public void openVideoList() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, VideoListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
