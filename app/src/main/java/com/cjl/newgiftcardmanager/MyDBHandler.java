package com.cjl.newgiftcardmanager;

// import databased related libraries (needed for any database in Android)
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "newgiftcards.db";
    public static final String TABLE_GIFTCARDS = "giftcards";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CARDNUMBER = "cardnumber";
    public static final String COLUMN_CARDCOMPANY = "cardcompany";
    public static final String COLUMN_CARDPIN = "cardpin";
    public static final String COLUMN_CARDEXPDATE = "cardexpdate";
    public static final String COLUMN_CARDBALANCE = "cardbalance";
    public static final String COLUMN_CARDNOTES = "cardnotes";

    //used for singleton method below
    private static MyDBHandler sInstance;

    // singleton method
    public static synchronized MyDBHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDBHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     * // In any activity just pass the context and use the singleton method
     MyDBHandler dbHandler = MyDBHandler.getInstance(this);
     */

    // we need to pass database information along to superclass
    private MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //called when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_GIFTCARDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CARDCOMPANY + " TEXT, " +
                COLUMN_CARDNUMBER + " TEXT, " +
                COLUMN_CARDPIN + " TEXT, " +
                COLUMN_CARDEXPDATE + " TEXT, " +
                COLUMN_CARDBALANCE + " TEXT, " +
                COLUMN_CARDNOTES + " TEXT " +
                ");";
        db.execSQL(query); // query is written in SQL language format
    }

    //when update to the new db version, then delete the existing table, and then call onCreateAgain
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFTCARDS);
        onCreate(db);
    }

    //add a new row to the database
    public boolean addCard(Cards card){
        ContentValues values = new ContentValues(); //ContentValue is a default Java class that helps to put columns of data more easily
        values.put(COLUMN_CARDCOMPANY, card.get_cardCompany());
        values.put(COLUMN_CARDNUMBER, card.get_cardNumber());
        values.put(COLUMN_CARDPIN, card.get_cardPin());
        values.put(COLUMN_CARDEXPDATE, card.get_cardExpDate());
        values.put(COLUMN_CARDBALANCE, card.get_cardBalance());
        values.put(COLUMN_CARDNOTES, card.get_cardNotes());
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_GIFTCARDS, null, values); // insert a new row into database, use boolean result to see if inserted successfully
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    //update a row (a card) in the database
    public boolean updateCard(String cardIDinDB, Cards card){
        ContentValues values = new ContentValues(); //ContentValue is a default Java class that helps to put columns of data more easily
        values.put(COLUMN_CARDCOMPANY, card.get_cardCompany());
        values.put(COLUMN_CARDNUMBER, card.get_cardNumber());
        values.put(COLUMN_CARDPIN, card.get_cardPin());
        values.put(COLUMN_CARDEXPDATE, card.get_cardExpDate());
        values.put(COLUMN_CARDBALANCE, card.get_cardBalance());
        values.put(COLUMN_CARDNOTES, card.get_cardNotes());
        SQLiteDatabase db = getWritableDatabase();
        Integer result = db.update(TABLE_GIFTCARDS, values, COLUMN_ID + " = ? ", new String[] {cardIDinDB}); // insert a new row into database, use boolean result to see if inserted successfully
        db.close();
        if (result >= 1)
            return true;
        else
            return false;
    }


    //Delete a card from the database (need the user to input the whole card number into the field)
    public Integer deleteCard(String cardID){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_GIFTCARDS, "_id = ?", new String[] {cardID} ); // this will return the number of rows get affected, 0 means no row is deleted
        //db.execSQL("DELETE FROM " + TABLE_GIFTCARDS + " WHERE " + COLUMN_ID + "=\"" + cardID + "\";");
    }

    //Delete all cards from the database
    public void deleteAllCards(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFTCARDS); // drop the table and recreate, in order to reset ID
        onCreate(db);
    }

    //check whether an user-input card_ID is in database
    public boolean cardIDExist(String ID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GIFTCARDS + " WHERE " + COLUMN_ID + " = " + ID ;
        Cursor c = db.rawQuery(query, null);
        if(c.getCount() <= 0){
            c.close();
            db.close();
            return false;
        }
        c.close();
        db.close();
        return true;
    }

    //Return the database content based on Input ID number and Column number
    public String databaseContentString(String ID, String ColumnName) {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GIFTCARDS + " WHERE " + COLUMN_ID + " = " + ID ;

        //cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        if (c.getCount()==0){
            c.close();
            db.close();
            return "No giftcard record!";
        }

        String results = c.getString(c.getColumnIndex(ColumnName));
        c.close();
        db.close();
        return results;
    }

    //Print out the database as a string for display purpose
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_GIFTCARDS;

        //cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);

        if (c.getCount()==0){
            c.close();
            db.close();
            return "No giftcard record!";
        }

        StringBuffer buffer = new StringBuffer ();

        while (c.moveToNext()){
            buffer.append("Card ID: " + c.getString(c.getColumnIndex(COLUMN_ID)) + "\n");
            buffer.append("Gift Card Company: " + c.getString(c.getColumnIndex(COLUMN_CARDCOMPANY)) + "\n");
            buffer.append("Card Number: " + c.getString(c.getColumnIndex(COLUMN_CARDNUMBER)) + "\n");
            buffer.append("Pin: " + c.getString(c.getColumnIndex(COLUMN_CARDPIN)) + "\n");
            buffer.append("Expiration Date: " + c.getString(c.getColumnIndex(COLUMN_CARDEXPDATE)) + "\n");
            buffer.append("Balance: " + c.getString(c.getColumnIndex(COLUMN_CARDBALANCE)) + "\n");
            buffer.append("Notes: " + c.getString(c.getColumnIndex(COLUMN_CARDNOTES)) + "\n");
            buffer.append("\n");
        }

        c.close();
        db.close();
        return buffer.toString();
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnCardnumber() {
        return COLUMN_CARDNUMBER;
    }

    public static String getColumnCardcompany() {
        return COLUMN_CARDCOMPANY;
    }

    public static String getColumnCardpin() {
        return COLUMN_CARDPIN;
    }

    public static String getColumnCardexpdate() {
        return COLUMN_CARDEXPDATE;
    }

    public static String getColumnCardbalance() {
        return COLUMN_CARDBALANCE;
    }

    public static String getColumnCardnotes() {
        return COLUMN_CARDNOTES;
    }
}