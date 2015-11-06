package csc414.androidconnectionwatchdog;

//import the columns structure for our database
import android.provider.BaseColumns;
/**
 * Created by Kevin on 11/5/2015.
 *
 * TODO
 * Integrate this into the main application for storage of white-listed connections
 * This will be the main structure of our white-listed application SQLite database
 * It defines the variables that will be stored in our database
 * The class using database uses the naming convention public class xxxxxxContract {}
 */
public class ConnectionContract {
    //Not quite sure of the DB implementation for android. You guys can double check me.
    //It's not camelCased because the containing file is not camelCased, ignore the underlined part
    public static final String DB_NAME = "whatever.we.name.it.androidconnectionwatchdog.db.connections";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "connections";

    public class Columns {
        public static final String CONNECTION = "connection";
        public static final String _ID = BaseColumns._ID;
    }
}
