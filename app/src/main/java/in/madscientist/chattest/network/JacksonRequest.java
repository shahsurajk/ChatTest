package in.madscientist.chattest.network;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.squareup.picasso.Downloader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frapp on 11/2/17.
 */


public class JacksonRequest<T> extends JsonRequest<T> {
    private Class<T> responseType;

    /**
     * Creates a new request.
     *
     * @param method the HTTP method to use
     * @param url URL to fetch the JSON from
     * @param requestData A {@link Object} to post and convert into json as the request. Null is allowed and indicates no parameters will be posted along with request.
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JacksonRequest(int method, String url, Object requestData, Class<T> responseType, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (requestData == null) ? null : Mapper.string(requestData), listener, errorListener);
        Log.e("jacksonrequest",  method + " " + url);
        this.responseType = responseType;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        // Log.e("jackson","parse called");
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(Mapper.objectOrThrow(jsonString, responseType), HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            if (response.statusCode == 200) {
                Exception exception = new Exception(new String(response.data));
                return Response.error(new ParseError(exception));
            } else {
                return Response.error(new VolleyError("Parsing failed"));
            }
        }
    }
}
