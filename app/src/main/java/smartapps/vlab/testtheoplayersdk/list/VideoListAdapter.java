package smartapps.vlab.testtheoplayersdk.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import smartapps.vlab.testtheoplayersdk.R;

/**
 * Created by Vinh.Tran on 4/24/18.
 **/
class VideoListAdapter<T extends ItemVideo> extends RecyclerView.Adapter {

    private List<T> mItems;

    public VideoListAdapter(List<T> items) {
        mItems = items;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isVideo ? 1 : 0; // 1 is video
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View videoView = LayoutInflater.from(parent.getContext()).inflate(VideoViewHolder.getLayoutRes(), parent, false);
            return new VideoViewHolder(videoView);
        }

        View otherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other, parent, false);
        return new VideoListFragment.OtherViewHolder(otherView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).bindData(mItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        System.out.println(">>> onViewAttachedToWindow " + holder.getAdapterPosition());
        if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).onAttach();
        }
    }

    //
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        System.out.println(">>> onViewDetachedFromWindow " + holder.getAdapterPosition());
        if (holder instanceof VideoViewHolder) {
            ((VideoViewHolder) holder).onDetachFromWindow();
        }
    }
}
