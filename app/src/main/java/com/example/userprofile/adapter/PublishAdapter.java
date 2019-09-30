package com.example.userprofile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.Interface.OnItemClickListener;
import com.example.userprofile.R;
import com.example.userprofile.model.Published;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 13:09
 */
public class PublishAdapter extends PagedListAdapter<Published, PublishAdapter.PublishViewHolder> {
    private OnItemClickListener mOnItemClickListener;

    public PublishAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PublishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.published_item,parent,false);
        PublishViewHolder publishViewHolder = new PublishViewHolder(view);
        publishViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (int) view.getTag());//注意这里使用getTag方法获取position
                }
            }
        });
        return publishViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublishViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Published published = getItem(position);
        if (published != null){
            holder.productionImage.setImageURI(published.getPublishDataData().getUserVideo().getCoverMediumUrl().getUrlList().get(0));
            holder.likeNum.setText(published.getPublishDataData().getPublishStats().getDiggCount());
        }

    }

    //暴露给外部监听
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class PublishViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView productionImage;
        TextView likeNum;
        public PublishViewHolder(@NonNull View itemView) {
            super(itemView);
            productionImage = itemView.findViewById(R.id.user_production_image);
            likeNum = itemView.findViewById(R.id.like_num);
        }
    }
    //更新数据
    private static DiffUtil.ItemCallback<Published> DIFF_CALLBACK = new DiffUtil.ItemCallback<Published>() {

        @Override
        public boolean areItemsTheSame(@NonNull Published oldItem, @NonNull Published newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Published oldItem, @NonNull Published newItem) {
            return oldItem.getPublishDataData().getTitle().equals(newItem.getPublishDataData().getTitle());
        }
    };

}
