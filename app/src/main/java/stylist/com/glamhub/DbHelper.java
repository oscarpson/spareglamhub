package stylist.com.glamhub;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 10/19/2016.
 */

public class DbHelper extends SQLiteOpenHelper

{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_user";

    // Contacts table name
    private static final String TABLE_USER = "appUser";

    // Contacts Table Columns names
    public final String PHONENUMBER="phone_no";
    public final String PASSWORD="password";
    public final String STATUS="status";
    public final String IDNUMBER="idNumber";
    public final String DATEOFBIRTH="dateOfBirth";
    public final String ROLE="role";
    public final String BIODATA="bioData";
    public final String TOKEN="token";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER+ "("
                + PHONENUMBER + " VARCHAR(250)," + PASSWORD + " TEXT(1000) NOT NULL,"
                + STATUS+ " VARCHAR(250) NOT NULL," + IDNUMBER+" VARCHAR(250),"+ROLE+" VARCHAR(250),"+BIODATA+" VARCHAR(250),"+TOKEN+" TEXT(1000),"+DATEOFBIRTH+" VARCHAR(250)"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONENUMBER, user.getPhoneNumber()); // Username
        values.put(PASSWORD, user.getPassword()); // Password
        values.put(STATUS, user.getLogin_status());
        values.put(IDNUMBER,user.getIdNumber());
        values.put(ROLE,user.getRole());
        values.put(BIODATA,user.getBioData());
        values.put(TOKEN,user.getToken());
        values.put(DATEOFBIRTH,user.getDateOfBirth());
        // Login status

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    User getUser(String phoneNumber,String Password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { PHONENUMBER,
                        PASSWORD,STATUS,IDNUMBER,ROLE,BIODATA,TOKEN,DATEOFBIRTH}, PHONENUMBER + "=? AND "+PASSWORD+"= ?",
                new String[] { phoneNumber,Password}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7));
        // return contact
        return user;
    }


    // Check if a user already exists
    Boolean userExists (String Usernname,String Password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { PHONENUMBER,
                        PASSWORD, STATUS,IDNUMBER,ROLE,BIODATA,TOKEN,DATEOFBIRTH }, PHONENUMBER + "=? AND "+PASSWORD+"= ?",
                new String[] { Usernname,Password}, null, null, null, null);

        if (cursor != null)
            return true;

        else
            return false;
    }


    public void deleteAllUsers ()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_USER);
        db.close();
    }

    // Getting All Contacts
    public List<User> getAllUsers()
    {
        List<User> userList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                User contact = new User();
                contact.setPhoneNumber(cursor.getString(0));
                contact.setPassword(cursor.getString(1));
                contact.setLogin_status(cursor.getString(2));
                contact.setIdNumber(cursor.getString(3));
                contact.setRole(cursor.getString(4));
                contact.setBioData(cursor.getString(5));
                contact.setToken(cursor.getString(6));
                contact.setDateOfBirth(cursor.getString(7));

                // Adding contact to list
                userList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return contact list
        return userList;
    }

    // Updating single contact
    public int updateUserLoginStatus(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, user.getLogin_status());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }

    // Deleting single contact
    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
        db.close();
    }


    // Getting contacts Count
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count =  cursor.getCount();
        cursor.close();
        db.close();
        return count;

    }

    public void deleteTable(Context context)
    {

    }

    public int updateIdNumber(User user,String idNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IDNUMBER, idNumber);


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updatePhoneNumber(User user,String phoneNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PHONENUMBER, phoneNumber);


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updateUserBioData(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BIODATA, user.getBioData());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updateUserDob(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DATEOFBIRTH, user.getDateOfBirth());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updateStatus(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, user.getLogin_status());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updateToke(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOKEN, user.getToken());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
    public int updatePassword(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PASSWORD, user.getPassword());


        // updating row
        return db.update(TABLE_USER, values, PHONENUMBER + " = ?",
                new String[] { user.getPhoneNumber() });
    }
}
