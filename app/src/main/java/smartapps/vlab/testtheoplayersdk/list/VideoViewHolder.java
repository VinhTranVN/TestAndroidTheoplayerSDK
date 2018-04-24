package smartapps.vlab.testtheoplayersdk.list;

import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.source.SourceDescription;
import com.theoplayer.android.api.source.addescription.THEOplayerAdDescription;

import smartapps.vlab.testtheoplayersdk.R;

/**
 * Created by Vinh.Tran on 4/24/18.
 **/
class VideoViewHolder<T extends ItemVideo> extends RecyclerView.ViewHolder {

    private FrameLayout mTHEOplayerViewContainer;
    private THEOplayerView mTHEOplayerView;
    private SourceDescription mSourceDescription;
    private Rect mViewRect;
    private Handler mVideoHandle = new Handler();
    private boolean isAddListener;

    public static int getLayoutRes() {
        return R.layout.item_video;
    }

    public VideoViewHolder(final View itemView) {
        super(itemView);

        mTHEOplayerViewContainer = itemView.findViewById(R.id.theoplayer_view_container);

        itemView.findViewById(R.id.fullscreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTHEOplayerView.getFullScreenManager().requestFullScreen();
            }
        });

        itemView.findViewById(R.id.remove_add_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTHEOplayerViewContainer.getChildCount() > 0){
                    mTHEOplayerViewContainer.removeAllViews();
                } else {
                    mTHEOplayerView.getFullScreenManager().exitFullScreen();
                    mTHEOplayerViewContainer.addView(mTHEOplayerView);
                }
            }
        });
    }

    public void bindData(T videoItem) {
        System.out.println(">>> bindData " + getAdapterPosition());
        videoItem.init(itemView.getContext());
        mTHEOplayerView = videoItem.getTheoPlayerView();

        if(mTHEOplayerView.getParent() != null){
            ((ViewGroup)mTHEOplayerView.getParent()).removeView(mTHEOplayerView);
        }

        mTHEOplayerViewContainer.removeAllViews();
        mTHEOplayerViewContainer.addView(mTHEOplayerView);
        mTHEOplayerViewContainer.invalidate();
        System.out.println(">>> w: " + mTHEOplayerView.getLayoutParams().width + " h: " + mTHEOplayerView.getLayoutParams().height);
    }

    private void initVideoSource(T item) {
        if (mSourceDescription == null) {
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
        } else {
            mTHEOplayerView.onResume();
        }
    }

    public void onAttach(){
        System.out.println(">>> onAttach mOnScrollListener.hashCode() " + mOnScrollListener.hashCode());

        if(!isAddListener){
            //register onScroll change
            itemView.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollListener);
            itemView.getViewTreeObserver().addOnScrollChangedListener(mOnScrollListener);
            isAddListener = true;
        }
    }

    public void onDetachFromWindow() {
        // remove video handle
        mVideoHandle.removeCallbacksAndMessages(null);
        System.out.println(">>> onDetachFromWindow isAddListener " + isAddListener);
        if(isAddListener){
            // remove scroll listener
            itemView.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollListener);
            isAddListener = false;
        }
    }

    private static final float PERCENT_VISIBLE = 0.5f;

    private ViewTreeObserver.OnScrollChangedListener mOnScrollListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            if(mTHEOplayerView == null){
                return;
            }

            if (isViewVisible(PERCENT_VISIBLE)) {
                System.out.println(">>> isViewVisible >= 50% " + getAdapterPosition());
                if(mTHEOplayerView.getPlayer().isPaused()){
                    mVideoHandle.removeCallbacksAndMessages(null);
                    mVideoHandle.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isViewVisible(PERCENT_VISIBLE)){
                                mTHEOplayerView.getPlayer().play();
                            }
                        }
                    }, 500);
                }

            } else {
                System.out.println(">>> isViewVisible < 50% " + getAdapterPosition());
                if(!isViewVisible(PERCENT_VISIBLE) && !mTHEOplayerView.getPlayer().isPaused()){
                    mTHEOplayerView.getPlayer().pause();
                }
            }
        }
    };

    private boolean isViewVisible(float percentVisible) {
        if(mViewRect == null){
            mViewRect = new Rect();
        }
        itemView.getLocalVisibleRect(mViewRect);
        int visibleHeight = mViewRect.bottom - mViewRect.top;
        return visibleHeight / (float) itemView.getHeight() >= percentVisible;
    }
}
