package smartapps.vlab.testtheoplayersdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.event.EventListener;
import com.theoplayer.android.api.event.player.PlayEvent;
import com.theoplayer.android.api.event.player.PlayerEventTypes;
import com.theoplayer.android.api.source.SourceDescription;
import com.theoplayer.android.api.source.addescription.THEOplayerAdDescription;

public class MainActivity extends AppCompatActivity {

    private THEOplayerView tpv;

    EventListener<PlayEvent> mEventListener = new EventListener<PlayEvent>() {
        @Override
        public void handleEvent(PlayEvent event) {
            System.out.println(event.getCurrentTime());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tpv = findViewById(R.id.theoplayer_view);

        SourceDescription sourceDescription = SourceDescription.Builder.sourceDescription("https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8")
                .ads(
                        THEOplayerAdDescription.Builder.adDescription("https://cdn.theoplayer.com/demos/preroll.xml")
                                .timeOffset("10")
                                .skipOffset("3")
                                .build())
                .poster("http://cdn.theoplayer.com/video/big_buck_bunny/poster.jpg")
                .build();

        tpv.getPlayer().setSource(sourceDescription);

        tpv.getPlayer().addEventListener(PlayerEventTypes.PLAY, mEventListener);

        findViewById(R.id.fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpv.getFullScreenManager().requestFullScreen();
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        tpv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tpv.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        tpv.getPlayer().removeEventListener(PlayerEventTypes.PLAY, mEventListener);
        tpv.onDestroy();
    }
}
