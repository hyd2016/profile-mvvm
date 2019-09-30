package com.example.userprofile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.R;
import com.example.userprofile.model.RecommendUser;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-29 16:06
 */
public class RecommendAdapter extends PagedListAdapter<RecommendUser,RecommendAdapter.RecommendViewHolder> {

    Context mContext;

    public RecommendAdapter(Context context){
        super(DIFF_CALLBACK);
        mContext = context;
    }

    @NonNull
    @Override
    public RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommend_item,parent,false);
        return new RecommendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendViewHolder holder, int position) {
        RecommendUser recommendUser = getItem(position);
        if (recommendUser != null){
            holder.recommend_nick.setText(recommendUser.getUser().getNickName());
            holder.recommend_reason.setText(recommendUser.getRecommend_reason());
            holder.recommend_item_photo.setImageURI(recommendUser.getUser().getAvatarMedium().urls.get(0));
        }
    }

    static class RecommendViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView recommend_item_photo;
        TextView recommend_nick;
        TextView recommend_reason;
        TextView attention_item;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            recommend_item_photo = itemView.findViewById(R.id.recommend_item_photo);
            recommend_nick = itemView.findViewById(R.id.recommend_nick);
            recommend_reason = itemView.findViewById(R.id.recommend_reason);
            attention_item = itemView.findViewById(R.id.attention_item);
        }
    }

   //更新数据
    private static DiffUtil.ItemCallback<RecommendUser> DIFF_CALLBACK = new DiffUtil.ItemCallback<RecommendUser>() {
        @Override
        public boolean areItemsTheSame(@NonNull RecommendUser oldItem, @NonNull RecommendUser newItem) {
            return oldItem.getUser().getId() == newItem.getUser().getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull RecommendUser oldItem, @NonNull RecommendUser newItem) {
            return oldItem.equals(newItem);
        }
    };
}
