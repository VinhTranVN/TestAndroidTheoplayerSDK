package smartapps.vlab.testtheoplayersdk;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.source.SourceDescription;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private THEOplayerView mTHEOplayerView;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTHEOplayerView = view.findViewById(R.id.theoplayer_view);

        SourceDescription mSourceDescription = SourceDescription.Builder.sourceDescription("https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8")
                .poster("http://cdn.theoplayer.com/video/big_buck_bunny/poster.jpg")
                .build();

        mTHEOplayerView.getPlayer().setSource(mSourceDescription);

        view.findViewById(R.id.btn_open_share_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openShareVideo(mTHEOplayerView);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //mTHEOplayerView.getPlayer().removeEventListener(PlayerEventTypes.PLAY, mEventListener);
        mTHEOplayerView.onDestroy();
    }

}
