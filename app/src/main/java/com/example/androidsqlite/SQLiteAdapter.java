package com.example.androidsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2114 on 01-08-2017.
 */

public class SQLiteAdapter
{
    MySQLiteOpenHelper mySQLiteOpenHelper;
    public SQLiteAdapter(Context context,String path)
    {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context,path);
    }

    static class MySQLiteOpenHelper extends SQLiteOpenHelper
    {
        private static final int DATA_BASE_VERSION = 1;

        private static final String TABLE_NAME = "EMPLOYEE_TABLE";
        private static final String FIRST_COLUMN_NAME = "EMP_ID";
        private static final String SECOND_COLUMN_NAME = "EMP_NAME";
        private static final String THRID_COLUMN_NAME = "EMP_ADDRESS";

        private static final String CREATE_TABLE_QUERY =
                "CREATE TABLE IF NOT EXISTS "
                +TABLE_NAME+
                " ("
                +FIRST_COLUMN_NAME+" INTEGER(4) PRIMARY KEY,"
                +SECOND_COLUMN_NAME+" VARCHAR(255),"
                +THRID_COLUMN_NAME+" VARCHAR(255));";
        private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+TABLE_NAME+";";

        public MySQLiteOpenHelper(Context context, String path)
        {
            super(context,path,null,DATA_BASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try
            {
                db.execSQL(CREATE_TABLE_QUERY);
            }
            catch (Exception e)
            {

            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            try
            {
                db.execSQL(DROP_TABLE_QUERY);
                onCreate(db);
            }
            catch (Exception e)
            {

            }
        }
    }

    public long insertData(int emp_id,String emp_name,String emp_address)
    {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.FIRST_COLUMN_NAME,emp_id);
        contentValues.put(MySQLiteOpenHelper.SECOND_COLUMN_NAME, emp_name);
        contentValues.put(MySQLiteOpenHelper.THRID_COLUMN_NAME, emp_address);

        long id = db.insert(MySQLiteOpenHelper.TABLE_NAME, null, contentValues);


        return id;
    }

    public List<EmployeeModel> readAllEmployeeDetails()
    {
        List<EmployeeModel> result = new ArrayList<>();

        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String table = MySQLiteOpenHelper.TABLE_NAME;
        String[] columns =
                {
                    MySQLiteOpenHelper.FIRST_COLUMN_NAME,
                    MySQLiteOpenHelper.SECOND_COLUMN_NAME,
                    MySQLiteOpenHelper.THRID_COLUMN_NAME
                };
        String where = null;
        String[] whereClause = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(table,columns,where,whereClause,groupBy,having,orderBy);

        if (cursor!=null && cursor.moveToFirst())
        {
            do
            {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.emp_id = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.FIRST_COLUMN_NAME));
                employeeModel.emp_name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.SECOND_COLUMN_NAME));
                employeeModel.emp_address = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.THRID_COLUMN_NAME));

                result.add(employeeModel);
            }while (cursor.moveToNext());

            cursor.close();
        }
        return result;
    }

    public List<EmployeeModel> readEmployeeDetails(int emp_id)
    {
        List<EmployeeModel> result = new ArrayList<>();

        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String table = MySQLiteOpenHelper.TABLE_NAME;
        String[] columns =
                {
                        MySQLiteOpenHelper.FIRST_COLUMN_NAME,
                        MySQLiteOpenHelper.SECOND_COLUMN_NAME,
                        MySQLiteOpenHelper.THRID_COLUMN_NAME
                };
        String where = MySQLiteOpenHelper.FIRST_COLUMN_NAME +" =? ";
        String[] whereClause = {emp_id+""};
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = db.query(table,columns,where,whereClause,groupBy,having,orderBy);

        if (cursor!=null && cursor.moveToFirst())
        {
            do
            {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.emp_id = cursor.getInt(cursor.getColumnIndex(MySQLiteOpenHelper.FIRST_COLUMN_NAME));
                employeeModel.emp_name = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.SECOND_COLUMN_NAME));
                employeeModel.emp_address = cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.THRID_COLUMN_NAME));

                result.add(employeeModel);
            }while (cursor.moveToNext());

            cursor.close();
        }
        return result;
    }

    public boolean updateEmployeeData(int emp_id,String update_value)
    {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String table = MySQLiteOpenHelper.TABLE_NAME;
        String where = MySQLiteOpenHelper.FIRST_COLUMN_NAME + " =? ";
        String[] whereClause = {emp_id+""};

        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteOpenHelper.THRID_COLUMN_NAME,update_value);

        int result = db.update(table,contentValues,where,whereClause);

        if (result>0)
        {
            return true;
        }
        return false;
    }

    public boolean deleteEmployeeData(int emp_id)
    {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();

        String table = MySQLiteOpenHelper.TABLE_NAME;
        String where = MySQLiteOpenHelper.FIRST_COLUMN_NAME + " =? ";
        String[] whereClause = {emp_id+""};

        int result = db.delete(table,where,whereClause);
        if (result>0)
        {
            return true;
        }
        return false;
    }
}
