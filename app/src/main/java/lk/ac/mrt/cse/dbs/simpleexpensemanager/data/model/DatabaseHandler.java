package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "170096L";

    //table names
    private static final String account = "account";
    private static final String trancation = "trancation";

    private static final String bankName = "bankName";
    private static final String account_holder_name = "account_holder_name";
    private static final String balance = "balance";

    private static final String account_NO = "account_no";

    private static final String EXPENSE_TYPE = "expense_type";
    private static final String DATE = "date";

    private static final String CREATE_TABLE_account = "CREATE TABLE " + account + "(" +
            bankName + "String," +
            account_holder_name + "String," +


            AMOUNT + "double" +
            DATE + "DATETIME," +
            "FOREIGN KEY(" + account_NO + ") REFERENCES " + account + "(" + account_NO + "));";

    public DatabaseHandler(Context context) {

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_account);
        db.execSQL(CREATE_TABLE_trancation);


    @Override
        //on upgrade drop older tables
        db.execSQL(("DROP TABLE IF EXISTS " + trancation));

    }

    //insert new account to database

        values.put(account_NO, account.getaccountNo());
        values.put(bankName, account.getBankName());
        values.put(account_holder_name, account.getaccountHolderName());


        db.close();
    }

    //show account related to given account no
    public account showaccount(String accountNo) {
        SQLiteDatabase db = this.getReadableDatabase();  //get readable database as we are not inserting anything

        Cursor cursor = db.query(account,
                new String[]{account_NO, bankName, account_holder_name, balance},
                account_NO + "=?",
                new String[]{accountNo}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        //prepare account object
        account account = new account(
                cursor.getString(cursor.getColumnIndex(account_NO)),
                cursor.getString(cursor.getColumnIndex(bankName)),
                cursor.getString(cursor.getColumnIndex(account_holder_name)),
                cursor.getDouble(cursor.getColumnIndex(balance)));

        cursor.close();

        return account;
    }


    //show all the accounts
    public List<account> showaccountList() {
        List<account> accounts = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + account + " ORDER BY " +
                account_NO + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                account acc = new account(cursor.getString(cursor.getColumnIndex(account_NO)),
                        cursor.getString(cursor.getColumnIndex(bankName)),
                        cursor.getString(cursor.getColumnIndex(account_holder_name)),
                        cursor.getDouble(cursor.getColumnIndex(balance)));

                accounts.add(acc);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return accounts;
    }


    //show all the account numbers
    public List<String> showaccountNumberList() {
        List<String> accountnumbers = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + account_NO + " FROM " + account + " ORDER BY " +
                account_NO + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String accnum = cursor.getString(cursor.getColumnIndex(account_NO));
                accountnumbers.add(accnum);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return accountnumbers;
    }


    //delete the account
    public void deleteaccount(String accountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(account, account_NO + " = ?",
                new String[]{accountNo});
        db.close();
    }


    //update the balance
    public void updateDetails(String accountNo, ExpenseType expenseType, double amount) {
        account account = showaccount(accountNo);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        switch (expenseType) {
            case EXPENSE:
                values.put(balance, (Double.toString(Double.valueOf(account.getbalance()) - amount)));
                break;
            case INCOME:
                values.put(balance, (Double.toString(Double.valueOf(account.getbalance()) + amount)));
                break;
        }
        db.update(account, values, account_NO + " = ?", new String[]{accountNo});
    }

    // trancation logs
    public void logtrancation(String date, String accNo, String expenceType, String amount){
        values.put(account_NO,accNo);
        values.put(EXPENSE_TYPE,expenceType);
        values.put(AMOUNT,amount);
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();

    public List getAlltrancationLogs() throws ParseException {
        while (cursor.moveToNext()) {
            String date = cursor.getString(1);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 =formatter.parse(date);

            trancation trancation = new trancation(date2,accNo,expenseType,amount);
    }
    //get limited trancation logs
        while (cursor.moveToNext()) {
            String exType = cursor.getString(2);
            ExpenseType expenseType = ExpenseType.valueOf(exType);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 =formatter.parse(date);
            calendar.setTime(date1);

            trancation trancation = new trancation(date2,accNo,expenseType,amount);
            trancations.add(trancation);