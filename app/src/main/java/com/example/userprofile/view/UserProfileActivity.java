package com.example.userprofile.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.Interface.OnItemClickListener;
import com.example.userprofile.R;
import com.example.userprofile.Utils.AppConstants;
import com.example.userprofile.adapter.PublishAdapter;
import com.example.userprofile.adapter.RecommendAdapter;
import com.example.userprofile.di.DaggerUserProfileComponent;
import com.example.userprofile.viewmodel.PublishViewModel;
import com.example.userprofile.viewmodel.RecommendViewModel;
import com.example.userprofile.viewmodel.UserProfileViewModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private static boolean ISRECOMMEND = false;

    private UserProfileViewModel mUserProfileViewModel;

    private PublishViewModel mPublishViewModel;

    @Inject
    PublishAdapter mPublishAdapter;

    @Inject
    RecommendAdapter mRecommendAdapter;

    @BindView(R.id.btn_title_back)
    Button btnTitleBack;

    @BindView(R.id.text_user_name)
    TextView textUserName;

    @BindView(R.id.btn_title_share)
    Button btnTitleShare;

    @BindView(R.id.img_user_photo)
    SimpleDraweeView imgUserPhoto;

    @BindView(R.id.fans_num)
    TextView fansNum;

    @BindView(R.id.attention_num)
    TextView attentionNum;

    @BindView(R.id.hot_num)
    TextView hotNum;

    @BindView(R.id.btn_attention)
    Button btnAttentiion;

    @BindView(R.id.user_recommend)
    TextView userRecommend;

    @BindView(R.id.btn_is_attention)
    Button btnIsAttention;

    @BindView(R.id.recycler_view_recommend)
    RecyclerView recyclerViewRecommend;

    @BindView(R.id.layout_recommend)
    ConstraintLayout layoutRecommend;

    @BindView(R.id.user_img_send_fire)
    ImageView userImgSendFire;

    @BindView(R.id.user_tx_fire_num)
    TextView userTxFireNum;

    @BindView(R.id.user_btn_send_fire)
    Button userBtnSendFire;

    @BindView(R.id.recycler_view_publishes)
    RecyclerView recyclerViewPublishes;

    @BindView(R.id.user_tx_location)
    TextView userLocation;

    @BindView(R.id.user_tx_describe)
    TextView userDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        DaggerUserProfileComponent.create().inject(this);
        mUserProfileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        mPublishViewModel = ViewModelProviders.of(this).get(PublishViewModel.class);
        mUserProfileViewModel.init();
        dataChange();
        initPublished();
    }

    @Override
    protected void onDestroy() {
        mPublishViewModel.unBindMusicService();
        super.onDestroy();
    }

    private void initPublished() {
        //设置3列布局

        mPublishViewModel.init(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewPublishes.setLayoutManager(gridLayoutManager);
        mPublishViewModel.getPushlished().observe(this, publisheds -> mPublishAdapter.submitList(publisheds));
        recyclerViewPublishes.setAdapter(mPublishAdapter);
        mPublishAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(UserProfileActivity.this,"touch"+position,Toast.LENGTH_LONG).show();
                mPublishViewModel.startService(position);
            }

            @Override
            public void onItemLongClick(int position) {

            }


        });
    }

    private void initRecommend() {
        RecommendViewModel recommendViewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserProfileActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRecommend.setLayoutManager(linearLayoutManager);
        recommendViewModel.getRecommendList().observe(this, recommendUsers -> mRecommendAdapter.submitList(recommendUsers));
        recyclerViewRecommend.setAdapter(mRecommendAdapter);
    }

    private void dataChange() {
        mUserProfileViewModel.getCurrentUser().observe(this, user -> {

            if (user == null){
                Log.d(TAG, "onChanged: null");
            }

            if (user == null){
                return;
            }
            textUserName.setText(user.getNickName());
            fansNum.setText(String.valueOf(user.getTotalFansCount()));
            attentionNum.setText(String.valueOf(user.getStats().getFollowingCount()));
            hotNum.setText(String.valueOf(user.getFanTicketCount()));
            imgUserPhoto.setImageURI(Uri.parse(user.getAvatarMedium().getUrls().get(0)));
            userLocation.setText(user.getCity());
            userDescription.setText(user.getSignature());
        });
    }

    private void animateOpen() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        final int targetHeight = (int) (getResources().getDisplayMetrics().density * 190 + 0.5);
        layoutRecommend.getLayoutParams().height = targetHeight;
        layoutRecommend.setVisibility(View.VISIBLE);
        animator.addUpdateListener(valueAnimator -> {
            float value = (float) valueAnimator.getAnimatedValue();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutRecommend.getLayoutParams();
            layoutParams.height = targetHeight;
            layoutParams.bottomMargin = -targetHeight + (int) (value * targetHeight);
            layoutRecommend.setLayoutParams(layoutParams);
        });
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layoutRecommend.setVisibility(View.VISIBLE);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }

    private void animateClose() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        final int targetHeight = (int) (getResources().getDisplayMetrics().density * 190 + 0.5);
        layoutRecommend.getLayoutParams().height = targetHeight;
        layoutRecommend.setVisibility(View.VISIBLE);
        animator.addUpdateListener(valueAnimator -> {
            float value = (float) valueAnimator.getAnimatedValue();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutRecommend.getLayoutParams();
            layoutParams.height = targetHeight;
            layoutParams.bottomMargin =  - (int) (value * targetHeight);
            layoutRecommend.setLayoutParams(layoutParams);
        });
        animator.setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                layoutRecommend.setVisibility(View.INVISIBLE);
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();


    }

    public void triangleAnimator(int start,int end){
        ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.user_recommend),"rotation",start,end);
        animator.setDuration(200);
        animator.start();
    }

    //取消关注确认弹窗
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(UserProfileActivity.this);
        normalDialog.setMessage(getString(R.string.dialogIfUnattention));
        normalDialog.setPositiveButton(getString(R.string.yes),
                (dialog, which) -> {
                    btnAttentiion.setText(R.string.attention_name);
                    btnAttentiion.setBackground(getDrawable(R.drawable.btn_circle_red));
                    userRecommend.setBackground(getDrawable(R.drawable.icon_rec_arrow_unfollow));
                    btnIsAttention.setVisibility(View.GONE);
                });
        normalDialog.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {});
        // 显示
        normalDialog.show();
    }


    @OnClick(R.id.user_recommend)
    public void showRecommend(){

        if(ISRECOMMEND){
            triangleAnimator(180,0);
            animateClose();
            ISRECOMMEND = false;

        }else {
            triangleAnimator(0,180);
            animateOpen();
            initRecommend();
            ISRECOMMEND = true;
        }
    }

    @OnClick(R.id.btn_attention)
    public void changeFollow(){
        mUserProfileViewModel.getFollowStatus().setValue(AppConstants.FOLLOW_STATUS_UNFOLLOW);
        mUserProfileViewModel.getFollowStatus().observe(this, integer -> {
            btnAttentiion.setText(R.string.sendPrivateMessage);
            btnAttentiion.setBackground(getDrawable(R.drawable.btn_circle_white));
            userRecommend.setBackground(getDrawable(R.drawable.icon_rec_arrow_followed));
            triangleAnimator(0,180);
            btnIsAttention.setVisibility(View.VISIBLE);
            animateOpen();
            ISRECOMMEND = true;
        });
    }

    @OnClick(R.id.img_user_photo)
    public void showMusicHistory(){
        Intent intent = new Intent(UserProfileActivity.this,MusicHistoryActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_is_attention)
    public void unFollow(){
        showDialog();
    }

}
