package csc414.androidconnectionwatchdog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kevin on 11/5/2015.
 * Helper class for the Database
 *
 */
public class ConnectionDBHelper extends SQLiteOpenHelper {

    public ConnectionDBHelper(Context context) {
        super(context, ConnectionContract.DB_NAME, null, ConnectionContract.DB_VERSION);
    }
    //Build a query to create the table for us
    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT)", ConnectionContract.TABLE,
                                    ConnectionContract.Columns.CONNECTION);
        sqlDB.execSQL(sqlQuery);
    }
    //If the table already exists, drop it and call onCreate()
    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int j) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + ConnectionContract.TABLE);
        onCreate(sqlDB);
    }
}

