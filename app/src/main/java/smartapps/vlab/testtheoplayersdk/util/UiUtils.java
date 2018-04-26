package smartapps.vlab.testtheoplayersdk.util;

import android.graphics.Rect;
import android.view.View;

/**
 * Created by Vinh Tran on 4/26/18.
 */
public class UiUtils {

    public static boolean isViewVisible(View view, float percentVisible){
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        int visibleHeight = rect.bottom - rect.top;
        return visibleHeight / (float) view.getHeight() >= percentVisible;
    }
}
