package com.example.hotsoon_user_profiiles.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotsoon_user_profiiles.Interface.OnItemClickListener;
import com.example.hotsoon_user_profiiles.R;
import com.example.hotsoon_user_profiiles.music.MusicParcelable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MusicHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MusicParcelable> mMusicParcelableList;
    private static final int TYPE_BOTTOM_FRESH = 1;
    private static final int TYPE_NORMAL_ITEM = 2;


    //监听接口变量
    private OnItemClickListener mOnItemClickListener;

    public MusicHistoryAdapter(List<MusicParcelable> mMusicParcelableList) {
        this.mMusicParcelableList = mMusicParcelableList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_NORMAL_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_history_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, (int) view.getTag());//注意这里使用getTag方法获取position
                    }
                    return false;
                }
            });
            return viewHolder;
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_fresh_item,parent,false);
            view.setVisibility(View.GONE );
            return new BottomRefreshViewHolder(view);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            holder.itemView.setTag(position);
            MusicParcelable musicParcelable = mMusicParcelableList.get(position);
            ((ViewHolder)holder).musicHistoryCover.setImageURI(musicParcelable.getMusicCoverurl());
            ((ViewHolder) holder).title.setText(musicParcelable.getMusicTitle());
            ((ViewHolder) holder).url.setText(musicParcelable.getMusicUrl());
        }

    }


    //暴露给外部监听
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public int getItemCount() {
        if  (mMusicParcelableList != null){
            return mMusicParcelableList.size() + 1;
        }

        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position<mMusicParcelableList.size()){
            return TYPE_NORMAL_ITEM;
        }else {
            return TYPE_BOTTOM_FRESH;
        }
    }

    public boolean isBottomView(int position) {
        return position >= mMusicParcelableList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView musicHistoryCover;
        TextView title;
        TextView url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            musicHistoryCover = itemView.findViewById(R.id.music_history_cover);
            title = itemView.findViewById(R.id.music_history_title);
            url = itemView.findViewById(R.id.music_history_url);
        }
    }

    // 定义底部刷新View对应的ViewHolder
    static class BottomRefreshViewHolder extends RecyclerView.ViewHolder {
        public BottomRefreshViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
