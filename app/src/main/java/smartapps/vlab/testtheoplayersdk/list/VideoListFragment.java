package smartapps.vlab.testtheoplayersdk.list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import smartapps.vlab.testtheoplayersdk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {

    private VideoListAdapter mAdapter;

    public static VideoListFragment newInstance() {
        Bundle args = new Bundle();
        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new VideoListAdapter(getVideoItems());
        recyclerView.setAdapter(mAdapter);
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

    static class OtherViewHolder extends RecyclerView.ViewHolder {

        public OtherViewHolder(View itemView) {
            super(itemView);

        }
    }
}
