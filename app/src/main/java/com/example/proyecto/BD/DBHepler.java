package com.example.proyecto.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.proyecto.Entidades.Producto;

import java.sql.SQLClientInfoException;

public class DBHepler extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public DBHepler(Context context){
        super(context, "G101.db",null,1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRODUCTS("+
                "id TEXT PRIMARY KEY," +
                "name TEXT,"+
                "description TEXT,"+
                "price TEXT," +
                "image TEXT" +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");

    }

    public void insertData (Producto product){
        String sql = "INSERT INTO PRODUCTS VALUES(?,?,?,?,?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, product.getId());
        statement.bindString(2, product.getName());
        statement.bindString(3, product.getDescription());
        statement.bindString(4, String.valueOf(product.getPrice()));
        statement.bindString(5, product.getImage());

        statement.executeInsert();
    }

    public Cursor getData(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PRODUCTS", null);
        return cursor;
    }
}
