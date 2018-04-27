package smartapps.vlab.testtheoplayersdk;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.event.EventListener;
import com.theoplayer.android.api.event.player.EndedEvent;
import com.theoplayer.android.api.event.player.LoadedDataEvent;
import com.theoplayer.android.api.event.player.PlayEvent;
import com.theoplayer.android.api.event.player.PlayerEventTypes;
import com.theoplayer.android.api.player.track.mediatrack.MediaTrack;
import com.theoplayer.android.api.player.track.mediatrack.quality.VideoQuality;
import com.theoplayer.android.api.source.SourceDescription;

import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private THEOplayerView mTHEOplayerView;
    private FrameLayout mTHEOplayerViewContainer;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println(">>> MainFragment -> onCreateView : ");

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mTHEOplayerViewContainer = view.findViewById(R.id.theoplayer_view_container);
        mTHEOplayerView = view.findViewById(R.id.theoplayer_view);

        SourceDescription mSourceDescription = SourceDescription.Builder
                .sourceDescription("https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8")
                .poster("http://cdn.theoplayer.com/video/big_buck_bunny/poster.jpg")
                .build();

        mTHEOplayerView.getPlayer().setSource(mSourceDescription);

        mTHEOplayerView.getPlayer().addEventListener(PlayerEventTypes.LOADEDDATA, mLoadVideoEventListener);
        mTHEOplayerView.getPlayer().addEventListener(PlayerEventTypes.ENDED, mEndedEventListener);

        handleEvent(view);

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(">>> MainFragment -> onViewCreated : " + savedInstanceState);
    }


    private void handleEvent(View view) {
        view.findViewById(R.id.btn_open_share_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTHEOplayerViewContainer.removeView(mTHEOplayerView);
                // keep current view in application
                MyApplication.getInstance().setCurrentPlayerView(mTHEOplayerView);

                ((MainActivity) getActivity()).openShareVideo(mTHEOplayerViewContainer);
            }
        });

        view.findViewById(R.id.btn_open_video_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openVideoList();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mTHEOplayerView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTHEOplayerView.onResume();

        if(mTHEOplayerView != MyApplication.getInstance().getCurrentPlayerView()){
            MyApplication.getInstance().setCurrentPlayerView(mTHEOplayerView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println(">>> MainFragment -> onDestroy : ");

        mTHEOplayerView.getPlayer().removeEventListener(PlayerEventTypes.PLAY, mPlayEventListener);
        mTHEOplayerView.getPlayer().removeEventListener(PlayerEventTypes.LOADEDDATA, mLoadVideoEventListener);
        mTHEOplayerView.getPlayer().removeEventListener(PlayerEventTypes.ENDED, mEndedEventListener);

        mTHEOplayerView.onDestroy();
    }

    private EventListener<PlayEvent> mPlayEventListener = new EventListener<PlayEvent>() {
        @Override
        public void handleEvent(PlayEvent playEvent) {

        }
    };

    private EventListener<LoadedDataEvent> mLoadVideoEventListener = new EventListener<LoadedDataEvent>() {
        @Override
        public void handleEvent(LoadedDataEvent loadedDataEvent) {
            System.out.println(">>> loadedDataEvent");

            String qualityList = "";

            Iterator<MediaTrack<VideoQuality>> iterator = mTHEOplayerView.getPlayer().getVideoTracks().iterator();
            while (iterator.hasNext()){
                MediaTrack<VideoQuality> mediaTrack = iterator.next();

                Iterator<VideoQuality> videoQualityIterator = mediaTrack.getQualities().iterator();
                while (videoQualityIterator.hasNext()){
                    VideoQuality videoQuality = videoQualityIterator.next();
                    qualityList += videoQuality.getWidth() +  "," ;
                }
            }

            System.out.println(">>> qualities : " + qualityList);
        }
    };

    private EventListener<EndedEvent> mEndedEventListener = new EventListener<EndedEvent>() {
        @Override
        public void handleEvent(EndedEvent endedEvent) {
            System.out.println(">>> EndedEvent");
        }
    };

    public void updateVideoUI() {
        if(mTHEOplayerViewContainer.getChildCount() == 0){
            ((ViewGroup)mTHEOplayerView.getParent()).removeAllViews();
            mTHEOplayerViewContainer.addView(mTHEOplayerView);
        }
    }
}
