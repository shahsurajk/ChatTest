package in.madscientist.chattest.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by frapp on 11/2/17.
 */

public class VolleyManager {

    private static final String TAG = VolleyManager.class.getSimpleName();
    private static final int MY_SOCKET_TIMEOUT_MS = 45000;
    private final RequestQueue mRequestQueue;
    private static VolleyManager sInstance;

    private VolleyManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public synchronized RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new VolleyManager(context);
        }
    }

    public static synchronized VolleyManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(VolleyManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
