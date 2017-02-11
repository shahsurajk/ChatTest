package in.madscientist.chattest.db;

/**
 * Created by frapp on 11/2/17.
 */

public class DbConstants {
    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "messages";
    public static final String TABLE_USERS="users";
    public static final String TABLE_CONVERSATIONS="conversations";

    public static final String COLUMN_MESSAGE_BODY="message_body";
    public static final String COLUMN_MESSAGE_IS_FAV = "is_fav";
    public static final String COLUMN_TIMESTAMP ="timestamp";

    public static final String COLUMN_USERS_NAME="uname";
    public static final String COLUMN_USER_DISPLAY_NAME="u_displayname";
    public static final String COLUMN_IMAGE_URL ="u_image";
    private static final String COLUMN_COVERSATION_ID = "c_id";

    public static final String CREATE_TABLE_USERS="CREATE TABLE "+TABLE_USERS
            +"("
            + COLUMN_USERS_NAME +" TEXT PRIMARY KEY, "
            + COLUMN_IMAGE_URL +" TEXT, "
            + COLUMN_USER_DISPLAY_NAME+" TEXT "
            + ");";

    public static final String CREATE_TABLE_CONVERSATIONS ="CREATE TABLE "+TABLE_CONVERSATIONS
            +"("
            + COLUMN_COVERSATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERS_NAME+" TEXT, "
            + COLUMN_MESSAGE_BODY+ " TEXT, "
            + COLUMN_TIMESTAMP +" TEXT, "
            + COLUMN_MESSAGE_IS_FAV +" INTEGER"
            + ");";

}
