package smartapps.vlab.testtheoplayersdk.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.theoplayer.android.api.THEOplayerView;

import smartapps.vlab.testtheoplayersdk.R;

/**
 * Created by Vinh.Tran on 4/24/18.
 **/
class VideoViewHolder<T extends ItemVideo> extends RecyclerView.ViewHolder {

    private FrameLayout mTHEOplayerViewContainer;
    private THEOplayerView mTHEOplayerView;

    public static int getLayoutRes() {
        return R.layout.item_video;
    }

    public VideoViewHolder(final View itemView) {
        super(itemView);

        mTHEOplayerViewContainer = itemView.findViewById(R.id.theoplayer_view_container);

        itemView.findViewById(R.id.fullscreen)
                .setOnClickListener(v -> mTHEOplayerView.getFullScreenManager().requestFullScreen());

        itemView.findViewById(R.id.remove_add_view)
                .setOnClickListener(v -> {
                    if (mTHEOplayerViewContainer.getChildCount() > 0) {
                        mTHEOplayerViewContainer.removeAllViews();
                    } else {
                        mTHEOplayerViewContainer.addView(mTHEOplayerView);
                    }
                });
    }

    public void bindData(T videoItem) {
        System.out.println(">>> bindData " + getAdapterPosition());
        videoItem.init(itemView.getContext());
        mTHEOplayerView = videoItem.getWeekReferView();

        // check for remove parent view of video view first
        if(mTHEOplayerView.getParent() != null){
            ((ViewGroup)mTHEOplayerView.getParent()).removeView(mTHEOplayerView);
        }

        if(mTHEOplayerViewContainer.getChildCount() > 0){
            mTHEOplayerViewContainer.removeAllViews();
        }

        // add video view into container
        mTHEOplayerViewContainer.addView(mTHEOplayerView);
        mTHEOplayerView.onResume();
    }

    public void onAttach(){
        // TODO
    }

    public void onDetachFromWindow() {
        // TODO
        if (!mTHEOplayerView.getPlayer().isPaused()) {
            mTHEOplayerView.getPlayer().pause();
        }
    }
}
