package in.madscientist.chattest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by frapp on 11/2/17.
 */

public class DBSqliteHelper extends SQLiteOpenHelper {
    public DBSqliteHelper(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbConstants.CREATE_TABLE_USERS);
//        Log.i("db", "onCreate: table created "+DbConstants.CREATE_TABLE_CONVERSATIONS);
        sqLiteDatabase.execSQL(DbConstants.CREATE_TABLE_CONVERSATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_USERS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DbConstants.TABLE_CONVERSATIONS);
        onCreate(sqLiteDatabase);
    }

}
