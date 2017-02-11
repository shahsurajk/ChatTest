package in.madscientist.chattest.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.madscientist.chattest.model.pojo.Message;
import in.madscientist.chattest.model.pojo.User;

import static in.madscientist.chattest.db.DbConstants.*;
/**
 * Created by frapp on 11/2/17.
 */

public class DBOperations  {
    public static void enterMessageInConversation(SQLiteDatabase db, Message message)
    {
        if (!isUserInDB(db,message.getUsername()))
        {
            addUserToDB(db,message.getUser());
            addConversationToDB(db,message);
        }else
        {
            addConversationToDB(db,message);
        }
    }

    public static boolean checkIfListIsUpdated(SQLiteDatabase database, String timestamp)
    {
        List<Message>messages = getAllMessages(database);
        if (messages.size()>0 && timestamp.equalsIgnoreCase(messages.get(messages.size()-1).getMessage_time()))
        {
            return  true;
        }
        return false;
    }
    public static List<Message>getAllMessages(SQLiteDatabase db)
    {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_USERS +" INNER JOIN "+TABLE_CONVERSATIONS+ " ON "+TABLE_USERS+"."+COLUMN_USERS_NAME
                +" = "+TABLE_CONVERSATIONS+"."+COLUMN_USERS_NAME;
        Cursor cursor  = db.rawQuery(query,null);
        if (cursor!=null)
        {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                Message message = new Message();
                message.setBody(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE_BODY)));
                message.setMessage_time(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                message.setImage_url(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));
                message.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DISPLAY_NAME)));
                message.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERS_NAME)));
                message.setIsFavourite(cursor.getInt(cursor.getColumnIndex(COLUMN_MESSAGE_IS_FAV)));
                messages.add(message);
            }
        }
        return  messages;
    }
    public static void setMessageToFav(SQLiteDatabase db,Message message, int fav)
    {
        String query = "UPDATE "+TABLE_CONVERSATIONS+" SET "+COLUMN_MESSAGE_IS_FAV+" = "+fav+ " WHERE "+COLUMN_USERS_NAME+"='"+message.getUsername()+"'"
                +" AND "+COLUMN_MESSAGE_BODY +" = '"+message.getBody()+"' ";
//        AND "+COLUMN_TIMESTAMP +"= '"+message.getMessage_time()+"'
//        Log.i(TAG, "setMessageToFav: update ran q: "+query);
        db.execSQL(query);
    }

    public static List<User>getUsersFromDB(SQLiteDatabase db)
    {
        List<User>users = new ArrayList<>();
        String q = "SELECT * FROM "+TABLE_USERS;
        Cursor cursor = db.rawQuery(q,null);
        if (cursor!=null)
        {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            {
                User user = new User();
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DISPLAY_NAME)));
                user.setImage_url(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URL)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERS_NAME)));
                user.setFavCount(getFavCountForUser(db,user));
                user.setTotalMsgCount(getTotalMsgCountForUser(db,user));
                users.add(user);
            }
        }
        return users;
    }
    public static int getTotalMsgCountForUser(SQLiteDatabase db, User user)
    {
        int count =0;
        String q= "SELECT "+COLUMN_USERS_NAME+" FROM "+TABLE_CONVERSATIONS +" WHERE "+COLUMN_USERS_NAME+"= '"+user.getUsername()+"'" ;
        count = db.rawQuery(q,null).getCount();
        return count;
    }
    public static int getFavCountForUser(SQLiteDatabase db, User user){
        int count =0;
        String q= "SELECT "+COLUMN_USERS_NAME+" FROM "+TABLE_CONVERSATIONS +" WHERE "+COLUMN_USERS_NAME+"= '"+user.getUsername()+"'" +
                " AND "+COLUMN_MESSAGE_IS_FAV +"="+User.MESSAGE_TYPE_FAV;
        count = db.rawQuery(q,null).getCount();
        return count;
    }
    private static void addConversationToDB(SQLiteDatabase db, Message message)
    {
        ContentValues values= new ContentValues();
        values.put(COLUMN_USERS_NAME,message.getUsername());
        values.put(COLUMN_MESSAGE_BODY,message.getBody());
        values.put(COLUMN_TIMESTAMP,message.getMessage_time());
        values.put(COLUMN_MESSAGE_IS_FAV,message.getIsFavourite());

        db.insertWithOnConflict(TABLE_CONVERSATIONS,null,values,SQLiteDatabase.CONFLICT_NONE);
    }
    private static void addUserToDB(SQLiteDatabase db, User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_DISPLAY_NAME, user.getName());
        values.put(COLUMN_USERS_NAME,user.getUsername());
        values.put(COLUMN_IMAGE_URL,user.getImage_url());
        db.insertWithOnConflict(TABLE_USERS,null,values,SQLiteDatabase.CONFLICT_NONE);
    }
    private static final String TAG = DBOperations.class.getCanonicalName().toString();
    private static boolean isUserInDB(SQLiteDatabase db, String uname)
    {
        String query = "SELECT "+COLUMN_USERS_NAME +" FROM "+TABLE_USERS+" WHERE "+COLUMN_USERS_NAME+" = '"+uname+"'";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return count!=0;
    }
}
