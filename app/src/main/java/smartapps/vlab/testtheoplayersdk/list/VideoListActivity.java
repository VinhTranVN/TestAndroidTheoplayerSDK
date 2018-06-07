package smartapps.vlab.testtheoplayersdk.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import smartapps.vlab.testtheoplayersdk.R;

public class VideoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, VideoListFragment.newInstance())
                .commit();
    }
}
