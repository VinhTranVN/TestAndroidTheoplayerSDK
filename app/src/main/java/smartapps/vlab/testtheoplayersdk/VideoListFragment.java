package smartapps.vlab.testtheoplayersdk;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.source.SourceDescription;
import com.theoplayer.android.api.source.addescription.THEOplayerAdDescription;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoListFragment extends Fragment {

    private VideoAdapterList mAdapter;

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

        mAdapter = new VideoAdapterList(getVideoItems());
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
        mAdapter.notifyDataSetChanged();
    }

    //    @Override
//    protected void onPause() {
//        super.onPause();
//        tpv.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        tpv.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        tpv.getPlayer().removeEventListener(PlayerEventTypes.PLAY, mEventListener);
//        tpv.onDestroy();
//    }


    static class VideoAdapterList<T extends ItemVideo> extends RecyclerView.Adapter {

        private List<T> mItems;

        public VideoAdapterList(List<T> items) {
            mItems = items;
        }

        @Override
        public int getItemViewType(int position) {
            return mItems.get(position).isVideo ? 1 : 0; // 1 is video
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == 1){
                View videoView = LayoutInflater.from(parent.getContext()).inflate(VideoViewHolder.getLayoutRes(), parent, false);
                return new VideoViewHolder(videoView);
            }

            View otherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other, parent, false);
            return new OtherViewHolder(otherView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            if(holder instanceof VideoViewHolder){
                ((VideoViewHolder) holder).bindData(mItems.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        //
        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            System.out.println(">>> onViewDetachedFromWindow " + holder.getAdapterPosition());
            if(holder instanceof VideoViewHolder){
                ((VideoViewHolder) holder).mTHEOplayerView.onPause();
            }
        }
    }

    static class VideoViewHolder<T extends ItemVideo> extends RecyclerView.ViewHolder {

        public THEOplayerView mTHEOplayerView;
        private SourceDescription mSourceDescription;

        public static int getLayoutRes(){
            return R.layout.item_video;
        }

        public VideoViewHolder(View itemView) {
            super(itemView);
            //setIsRecyclable(false);

            mTHEOplayerView = itemView.findViewById(R.id.theoplayer_view);
            //mTHEOplayerView.getSettings().setFullScreenOrientationCoupled(true);

            itemView.findViewById(R.id.fullscreen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTHEOplayerView.getFullScreenManager().requestFullScreen();
                }
            });
        }

        public void bindData(T item){
            System.out.println(">>> bindData");
            initVideoSource(item);
        }

        private void initVideoSource(T item) {
            if(mSourceDescription == null){
                mSourceDescription = SourceDescription.Builder
                        .sourceDescription(item.getVideoLink())
                        .ads(
                                THEOplayerAdDescription.Builder.adDescription(item.getAdsLink())
                                        .timeOffset("10")
                                        .skipOffset("3")
                                        .build())
                        .poster(item.getThumbNail())
                        .build();

                mTHEOplayerView.getPlayer().setSource(mSourceDescription);

                mTHEOplayerView.getPlayer().play();
            } else {
                mTHEOplayerView.onResume();
            }
        }
    }

    static class OtherViewHolder extends RecyclerView.ViewHolder {

        public OtherViewHolder(View itemView) {
            super(itemView);

        }
    }
}
