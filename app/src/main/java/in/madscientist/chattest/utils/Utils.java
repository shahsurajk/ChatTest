package in.madscientist.chattest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import in.madscientist.chattest.R;

/**
 * Created by frapp on 11/2/17.
 */

public class Utils {


    public static MaterialDialog.Builder getNoInternetButtonDialog(Context context, boolean cancelable, MaterialDialog.ButtonCallback singleButtonCallback) {
        MaterialDialog.Builder materialDialogBuilder = new MaterialDialog.Builder(context);
        materialDialogBuilder
                .cancelable(cancelable)
//            .typeface(Utils.getRegularFont(context), Utils.getMediumFont(context))
                .title(Html.fromHtml(context.getString(R.string.no_network)))
                .content(Html.fromHtml(context.getString(R.string.no_network_message)))
                .icon(ContextCompat.getDrawable(context, R.drawable.nonet_icon))
                .positiveText(context.getString(R.string.retry))
                .autoDismiss(true)
                .callback(singleButtonCallback);
        return materialDialogBuilder;
    }

    public static String parseDate(String date)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String finalDate ="";
        DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("h:mm a");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d MMM, h:mm a");

        try {
            String simpleDate = df.format(simpleDateFormat.parse(date));
            if(simpleDate.equals(df.format(calendar.getTime())))
            {
                Date date2 = simpleDateFormat.parse(date);
                finalDate = simpleDateFormat1.format(date2);
                // Log.e("TAG", "the time is "+final_date);
            }
            else
            {
                Date date1 = simpleDateFormat.parse(date);
                finalDate = simpleDateFormat2.format(date1);
                //  Log.e("TAG", "the date is "+final_date);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }
    public static MaterialDialog initProgressDialog(Context context, String message) {

        MaterialDialog.Builder dialog = new MaterialDialog.Builder(context)
                .title(null)
                .content(message == null ? "Please wait.." : message)
                .cancelable(false)
                .progress(true, 0);
        return dialog.build();
    }

    public static boolean isOnline (Context context) {
        boolean result = false;
        try {
            if (context != null) {
                ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                result = netInfo != null && netInfo.isConnectedOrConnecting();
                Log.i("Utils", "isOnline: "+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
