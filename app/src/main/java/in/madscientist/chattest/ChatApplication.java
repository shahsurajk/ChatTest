package in.madscientist.chattest;

import android.app.Application;

import in.madscientist.chattest.db.DBSqliteHelper;
import in.madscientist.chattest.db.DatabaseManager;
import in.madscientist.chattest.network.VolleyManager;

/**
 * Created by frapp on 11/2/17.
 */

public class ChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    private void init()
    {
        VolleyManager.initializeInstance(this);
        DatabaseManager.initializeInstance(new DBSqliteHelper(this));
    }
}
