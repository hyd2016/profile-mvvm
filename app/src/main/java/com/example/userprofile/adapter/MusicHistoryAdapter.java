package com.example.userprofile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.Interface.OnItemClickListener;
import com.example.userprofile.R;
import com.example.userprofile.model.MusicParcelable;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MusicHistoryAdapter extends RecyclerView.Adapter<MusicHistoryAdapter.MusicHistoryHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<MusicParcelable> mMusicParcelableList;

    public MusicHistoryAdapter(List<MusicParcelable> musicParcelableList) {
        mMusicParcelableList = musicParcelableList;
    }

    @NonNull
    @Override
    public MusicHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_history_item,parent,false);
        MusicHistoryHolder holder = new MusicHistoryHolder(view);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view, (int) view.getTag());//注意这里使用getTag方法获取position
                }
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicHistoryHolder holder, int position) {
        holder.itemView.setTag(position);
        MusicParcelable musicParcelable = mMusicParcelableList.get(position);
        if (musicParcelable != null){
            holder.title.setText(musicParcelable.getMusicTitle());
            holder.musicHistoryCover.setImageURI(musicParcelable.getMusicCoverurl());
            holder.url.setText(musicParcelable.getMusicUrl());
        }
    }

    @Override
    public int getItemCount() {
        return mMusicParcelableList.size();
    }


    //暴露给外部监听
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class MusicHistoryHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView musicHistoryCover;
        TextView title;
        TextView url;
        public MusicHistoryHolder(@NonNull View itemView) {
            super(itemView);
            musicHistoryCover = itemView.findViewById(R.id.music_history_cover);
            title = itemView.findViewById(R.id.music_history_title);
            url = itemView.findViewById(R.id.music_history_url);
        }
    }

}
