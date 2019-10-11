package com.example.userprofile.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.Interface.OnItemClickListener;
import com.example.userprofile.R;
import com.example.userprofile.model.MusicParcelable;
import com.example.userprofile.viewmodel.MusicHistoryViewModel;
import com.facebook.drawee.view.SimpleDraweeView;


public class MusicHistoryAdapter extends PagedListAdapter<MusicParcelable,MusicHistoryAdapter.MusicHistoryHolder> {
    private static final String TAG = "MusicHistoryAdapter";
    private OnItemClickListener mOnItemClickListener;


    private Observer mObserver = new Observer<PagedList<MusicParcelable>>() {
        @Override
        public void onChanged(PagedList<MusicParcelable> musicParcelables) {
            submitList(musicParcelables);
        }
    };


    public MusicHistoryAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MusicHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_history_item,parent,false);
        MusicHistoryHolder holder = new MusicHistoryHolder(view);


        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MusicHistoryHolder holder, int position) {
       // holder.itemView.setTag(position);
        holder.itemView.setOnLongClickListener(view1 -> {
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemLongClick(holder.getAdapterPosition());//注意这里使用getTag方法获取position
            }
            return true;
        });
        holder.itemView.setOnClickListener(view12 -> {
            if (mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(view12,holder.getAdapterPosition());
            }
        });




        Log.d(TAG, "onBindViewHolder: "+getCurrentList().size() );
        MusicParcelable musicParcelable = getItem(position);
        if (musicParcelable != null){
            holder.title.setText(musicParcelable.getMusicTitle());
            holder.musicHistoryCover.setImageURI(musicParcelable.getMusicCoverurl());
            holder.url.setText(musicParcelable.getMusicUrl());
        }
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

    public void setViewModel(MusicHistoryViewModel musicHistoryViewModel){
        musicHistoryViewModel.getMusicHistoryList().observeForever(mObserver);
    }

    public void removeObserver(MusicHistoryViewModel musicHistoryViewModel){
        musicHistoryViewModel.getMusicHistoryList().removeObserver(mObserver);
    }

    //更新数据
    private static DiffUtil.ItemCallback<MusicParcelable> DIFF_CALLBACK = new DiffUtil.ItemCallback<MusicParcelable>() {

        @Override
        public boolean areItemsTheSame(@NonNull MusicParcelable oldItem, @NonNull MusicParcelable newItem) {
            return oldItem.getId() == newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull MusicParcelable oldItem, @NonNull MusicParcelable newItem) {
            return oldItem.getId() == newItem.getId();
        }

    };

}
