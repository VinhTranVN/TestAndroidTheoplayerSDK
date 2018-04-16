package smartapps.vlab.testtheoplayersdk;

/**
 * Created by Vinh.Tran on 4/16/18.
 **/

class ItemVideo {
    String thumbNail;
    String videoLink;
    String adsLink;
    boolean isVideo = true;

    public ItemVideo(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public ItemVideo(String thumbNail, String videoLink, String adsLink) {
        this.thumbNail = thumbNail;
        this.videoLink = videoLink;
        this.adsLink = adsLink;
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
