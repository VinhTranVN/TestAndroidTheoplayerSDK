package smartapps.vlab.testtheoplayersdk.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.theoplayer.android.api.THEOplayerView;
import com.theoplayer.android.api.source.SourceDescription;

import java.lang.ref.WeakReference;

import smartapps.vlab.testtheoplayersdk.R;

/**
 * Created by Vinh.Tran on 4/16/18.
 **/

class ItemVideo {
    private String thumbNail;
    private String videoLink;
    private String adsLink;
    private WeakReference<THEOplayerView> mWeakReferView;
    boolean isVideo = true;

    public ItemVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public ItemVideo(String thumbNail, String videoLink, String adsLink) {
        this.thumbNail = thumbNail;
        this.videoLink = videoLink;
        this.adsLink = adsLink;
        mWeakReferView = new WeakReference<>(null);
    }

    public void init(Context context){

        if(mWeakReferView.get() != null){
            return;
        }

        String text = "inflate video view @" + hashCode();
        System.out.println(">>> init video " + text);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_player, null);
        THEOplayerView theoPlayerView = view.findViewById(R.id.theoplayer_view);
        mWeakReferView = new WeakReference<>(theoPlayerView);

        SourceDescription sourceDescription = SourceDescription.Builder
                .sourceDescription(videoLink)
//                .ads(
//                    THEOplayerAdDescription.Builder
//                            .adDescription(adsLink)
//                            .timeOffset("10")
//                            .skipOffset("3")
//                            .build()
//                )
                .poster(thumbNail)
                .build();

        theoPlayerView.getPlayer().setSource(sourceDescription);
    }

    public WeakReference<THEOplayerView> getWeakReferView() {
        return mWeakReferView;
    }

    public THEOplayerView getTheoPlayerView() {
        return mWeakReferView.get();
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
