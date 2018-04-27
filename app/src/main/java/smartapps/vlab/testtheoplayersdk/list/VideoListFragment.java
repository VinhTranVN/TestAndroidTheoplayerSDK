package smartapps.vlab.testtheoplayersdk.list;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theoplayer.android.api.THEOplayerView;

import java.util.ArrayList;

import smartapps.vlab.testtheoplayersdk.MyApplication;
import smartapps.vlab.testtheoplayersdk.R;
import smartapps.vlab.testtheoplayersdk.util.UiUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {

    private VideoListAdapter mAdapter;
    private Handler mVideoPlayHandler;
    private LinearLayoutManager mLinearLayoutManager;

    public static VideoListFragment newInstance() {
        Bundle args = new Bundle();
        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVideoPlayHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new VideoListAdapter(getVideoItems());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                System.out.println(">>> onScrollStateChanged");
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    checkPlayVideo(mLinearLayoutManager, mAdapter);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLinearLayoutManager.scrollToPosition(0);
    }

    private void checkPlayVideo(LinearLayoutManager layoutManager, VideoListAdapter adapter) {
        Log.d(getClass().getSimpleName(), ">>> checkPlayVideo: ");
        int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        for (int adapterPosition = firstVisibleItemPos; adapterPosition <= lastVisibleItemPosition; adapterPosition++) {
            try {
                if(!adapter.hasVideoView(adapterPosition)){
                    continue;
                }
                View itemView = layoutManager.findViewByPosition(adapterPosition);
                THEOplayerView videoView = itemView.findViewById(R.id.theoplayer_view);

                Log.d(getClass().getSimpleName(), ">>> checkPlayVideo hasVideoView : " + adapterPosition + " : videoView " + videoView);

                if(videoView != null){
                    if (UiUtils.isViewVisible(videoView, 0.5f)) {

                        if(videoView != MyApplication.getInstance().getCurrentPlayerView()){
                            MyApplication.getInstance().setCurrentPlayerView(videoView);
                        }

                        mVideoPlayHandler.postDelayed(() -> {
                            if(UiUtils.isViewVisible(videoView, 0.5f)){
                                System.out.println(">>> play");
                                videoView.getPlayer().play();
                            }
                        }, 500);
                    } else {
                        System.out.println(">>> pause");
                        videoView.getPlayer().pause();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private ArrayList getVideoItems() {
        ArrayList videos = new ArrayList();
        for (int i = 0; i < 8; i++) { // 8 items
            if(i % 2 == 0){
                if(i == 2 || i == 6){
                    videos.add(new ItemVideo(
                            "https://res.cloudinary.com/mbc-net/image/upload/v1523336570/2018/04/10/ihjsgt1mbmji0jttnohy.jpg",
                            //"https://vamvideos.s3.amazonaws.com/hls/2018/04/10/Justin+Bieber+-+One+Time_574919825.m3u8",
                            "https://vamvideos.s3.amazonaws.com/hls/2018/03/30/NuHonDanhRoiThangNamRucRoOst-HoangYenChibi-5414366_570692921.m3u8",
                            "https://cdn.theoplayer.com/demos/preroll.xml"
                    ));
                } else {
                    // demo source
                    videos.add(new ItemVideo(
                            "http://cdn.theoplayer.com/video/big_buck_bunny/poster.jpg",
                            "https://cdn.theoplayer.com/video/elephants-dream/playlist.m3u8",
                            "https://cdn.theoplayer.com/demos/preroll.xml"
                    ));
                }
            } else {
                videos.add(new ItemVideo(false)); // not video
            }
        }

        return videos;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println(">>> onResume");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        mVideoPlayHandler.removeCallbacksAndMessages(null);
    }

    static class OtherViewHolder extends RecyclerView.ViewHolder {

        public OtherViewHolder(View itemView) {
            super(itemView);

        }
    }
}
