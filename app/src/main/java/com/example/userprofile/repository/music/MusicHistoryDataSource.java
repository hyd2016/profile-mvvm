package com.example.userprofile.repository.music;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.BaseApplication;
import com.example.userprofile.Utils.AppConstants;
import com.example.userprofile.model.MusicParcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-10-08 12:00
 */
public class MusicHistoryDataSource extends PageKeyedDataSource<Integer, MusicParcelable> {
//    private List<MusicParcelable> mMusicParcelableList = new ArrayList<>();
    private static final String TAG = "MusicHistoryDataSource";

    private String title;

    private Cursor cursor;

    private int loadSize;


    public MusicHistoryDataSource(String title,int loadSize) {
        this.title = title;
        this.loadSize = loadSize;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void moveCursor(){
        try{
            cursor = BaseApplication.getContext().getContentResolver()
                    .query(Uri.parse(AppConstants.CONTENT_URI),null,title,
                            null,null,null);
        }catch (Exception e){
            Log.e(TAG, "moveCursor: "+e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MusicParcelable> callback) {

        moveCursor();

        Log.d(TAG, "cursor "+cursor.getCount());
        List<MusicParcelable> mMusicParcelableList = new ArrayList<>();

        if (cursor != null){

            int key = Math.min(cursor.getCount(),loadSize);
            cursor.moveToFirst();
            for (int i = 0; i<key;i++){
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String cover = cursor.getString(cursor.getColumnIndex("cover"));
                int position = cursor.getInt(cursor.getColumnIndex("position"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                MusicParcelable musicParcelable = new MusicParcelable(url,title,cover,position,id);
                mMusicParcelableList.add(musicParcelable);
                cursor.moveToNext();
                Log.d(TAG, "loadInitial: "+id);
            }
            callback.onResult(mMusicParcelableList,null,key);

        }
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MusicParcelable> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MusicParcelable> callback) {
        List<MusicParcelable> mMusicParcelableList = new ArrayList<>();

        if (cursor != null) {
            boolean load = false;
            int key = Math.min(cursor.getCount(), params.key + AppConstants.PAGING_NUM);
            for (int i = params.key; i < key; i++) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String cover = cursor.getString(cursor.getColumnIndex("cover"));
                int position = cursor.getInt(cursor.getColumnIndex("position"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                MusicParcelable musicParcelable = new MusicParcelable(url, title, cover, position, id);
                mMusicParcelableList.add(musicParcelable);
                cursor.moveToNext();
                load = true;
                Log.d(TAG, "loadAfter: " + id);
            }
            if (load) {
                callback.onResult(mMusicParcelableList, key);
                Log.d(TAG, "musicListSize"+mMusicParcelableList.size());
            } else {
                cursor.close();
            }
        }
    }



}
