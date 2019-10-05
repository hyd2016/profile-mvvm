package com.example.userprofile.repository;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-04 16:09
 */
public class MusicHistoryContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.hotsoon.provider";
    private static final int MUSIC_DIR = 0;
    private static final int MUSIc_ITEM = 1;
    private static UriMatcher uriMatcher;
    private MusicHistoryDBHelper musicHistoryDBHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"music",MUSIC_DIR);
        uriMatcher.addURI(AUTHORITY,"music/#",MUSIc_ITEM);
    }


    public MusicHistoryContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = musicHistoryDBHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)){
            case MUSIC_DIR:
                deleteRows = db.delete("Music",selection,selectionArgs);
                break;
            case MUSIc_ITEM:
                String musicId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Music","id = ?",new String[]{musicId});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case MUSIC_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.hotsoon.provider.music";
            case MUSIc_ITEM:
                return  "vnd.android.cursor.item/vnd.com.example.hotsoon.provider.music";
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = musicHistoryDBHelper.getWritableDatabase();
        Uri uriReturn = null;
        long newMusicId = db.insert("Music",null,values);
        uriReturn = Uri.parse("content://"+AUTHORITY+"/music/"+newMusicId);
        return uriReturn;
    }

    @Override
    public boolean onCreate() {
        musicHistoryDBHelper = new MusicHistoryDBHelper(getContext(),"MusicHistory.db",null,2);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = musicHistoryDBHelper.getWritableDatabase();
        Cursor cursor = null;
        switch(uriMatcher.match(uri)){
            case MUSIC_DIR:
                cursor = db.query("Music",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MUSIc_ITEM:
                String musicId = uri.getPathSegments().get(1);
                cursor = db.query("Music",projection,"id = ?",new String[]{musicId},null,null,sortOrder);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
