package smartapps.vlab.testtheoplayersdk.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.source.SourceDescription;
import com.theoplayer.android.api.source.addescription.THEOplayerAdDescription;

import smartapps.vlab.testtheoplayersdk.R;

/**
 * Created by Vinh.Tran on 4/16/18.
 **/

class ItemVideo {
    private String thumbNail;
    private String videoLink;
    private String adsLink;
    private THEOplayerView mTheoPlayerView;
    boolean isVideo = true;

    public ItemVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public ItemVideo(String thumbNail, String videoLink, String adsLink) {
        this.thumbNail = thumbNail;
        this.videoLink = videoLink;
        this.adsLink = adsLink;
    }

    public void init(Context context){

        if(mTheoPlayerView != null){
            return;
        }

        View view = LayoutInflater.from(context).inflate(R.layout.item_video_player, null);
        mTheoPlayerView = view.findViewById(R.id.theoplayer_view);

        SourceDescription sourceDescription = SourceDescription.Builder
                .sourceDescription(videoLink)
                .ads(
                    THEOplayerAdDescription.Builder
                            .adDescription(adsLink)
                            .timeOffset("10")
                            .skipOffset("3")
                            .build()
                )
                .poster(thumbNail)
                .build();

        mTheoPlayerView.getPlayer().setSource(sourceDescription);
    }

    public THEOplayerView getTheoPlayerView() {
        return mTheoPlayerView;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getAdsLink() {
        return adsLink;
    }

    public void setAdsLink(String adsLink) {
        this.adsLink = adsLink;
    }
}
