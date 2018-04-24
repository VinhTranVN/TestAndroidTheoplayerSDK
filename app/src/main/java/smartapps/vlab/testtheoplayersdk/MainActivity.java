package smartapps.vlab.testtheoplayersdk;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import smartapps.vlab.testtheoplayersdk.list.VideoListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
