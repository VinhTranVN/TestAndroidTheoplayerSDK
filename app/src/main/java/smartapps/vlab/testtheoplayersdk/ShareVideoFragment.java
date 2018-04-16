package smartapps.vlab.testtheoplayersdk;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theoplayer.android.api.THEOplayerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareVideoFragment extends Fragment {


    private THEOplayerView mTheOplayerView;

    public static ShareVideoFragment newInstance() {
        Bundle args = new Bundle();
        ShareVideoFragment fragment = new ShareVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(
                    TransitionInflater
                            .from(getContext())
                            .inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_video, container, false);
        mTheOplayerView = view.findViewById(R.id.theoplayer_view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
