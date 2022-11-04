package com.example.sy7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class sy7Provider extends ContentProvider {
    public static final int CONTACTS_DIR=0;
    public static final int CONTACTS_ITEM=1;
    public static final int CATEGORY_DIR=2;
    public static final int CATEGORY_ITEM=3;

    public static final String AUTHORITY="com.example.sy7.provider";


    private static UriMatcher uriMatcher;


    private MyDatabaseHelper dbHelper;


    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"contacts",CONTACTS_DIR);
        uriMatcher.addURI(AUTHORITY,"contacts/#",CONTACTS_ITEM);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"category/#",CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper=new MyDatabaseHelper(getContext(),"db.db",null,2);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                cursor=db.query("Contacts",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CONTACTS_ITEM:
                String bookId=uri.getPathSegments().get(1);
                cursor=db.query("Contacts",projection,"id=?",new String[]{bookId},null,null,sortOrder);
                break;

            case CATEGORY_DIR:
                cursor=db.query("Category",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId=uri.getPathSegments().get(1);
                cursor=db.query("Category",projection,"id=?",new String[]{categoryId},null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
            case CONTACTS_ITEM:
                long newBookId=db.insert("Contacts",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/contacts/"+newBookId);
                break;

            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId=db.insert("Category",null,values);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/category/"+newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int updatedRows=0;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                updatedRows=db.update("Contacts",values,selection,selectionArgs);
                break;
            case CONTACTS_ITEM:
                String bookId=uri.getPathSegments().get(1);
                updatedRows=db.update("Contacts",values,"id=?",new String[]{bookId});
                break;

            case CATEGORY_DIR:
                updatedRows=db.update("Category",values,selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId=uri.getPathSegments().get(1);
                updatedRows=db.update("Category",values,"id=?",new String[]{categoryId});
                break;
            default:
                break;
        }
        return updatedRows;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int deletedRows=0;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                deletedRows=db.delete("Contacts",selection,selectionArgs);
                break;
            case CONTACTS_ITEM:
                String bookId=uri.getPathSegments().get(1);
                deletedRows=db.delete("Contacts","id=?",new String[]{bookId});
                break;

            case CATEGORY_DIR:
                deletedRows=db.delete("Category",selection,selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId=uri.getPathSegments().get(1);
                deletedRows=db.delete("Category","id=?",new String[]{categoryId});
                break;
            default:
                break;
        }
        return deletedRows;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sy7.provider.contacts";
            case CONTACTS_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sy7.provider.contacts";

            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.sy7.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.sy7.provider.category";
        }
        return null;
    }

}
