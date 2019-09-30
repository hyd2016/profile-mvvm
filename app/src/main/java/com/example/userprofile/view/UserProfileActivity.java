package com.example.userprofile.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userprofile.Interface.OnItemClickListener;
import com.example.userprofile.R;
import com.example.userprofile.adapter.PublishAdapter;
import com.example.userprofile.adapter.RecommendAdapter;
import com.example.userprofile.model.Published;
import com.example.userprofile.model.RecommendUser;
import com.example.userprofile.model.User;
import com.example.userprofile.viewmodel.PublishViewModel;
import com.example.userprofile.viewmodel.RecommendViewModel;
import com.example.userprofile.viewmodel.UserProfileViewModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private static boolean ISRECOMMEND = false;
    //关注状态
    private static boolean ATTENTION = false;


    private UserProfileViewModel mUserProfileViewModel;

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
        mUserProfileViewModel = new UserProfileViewModel();
        mUserProfileViewModel.init();
        dataChange();
        initPublished();
    }

    private void initPublished() {
        //设置3列布局
        PublishViewModel publishViewModel = ViewModelProviders.of(this).get(PublishViewModel.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewPublishes.setLayoutManager(gridLayoutManager);
        PublishAdapter adapter = new PublishAdapter();
        publishViewModel.getPushlished().observe(this, new Observer<PagedList<Published>>() {
            @Override
            public void onChanged(PagedList<Published> publisheds) {
                adapter.submitList(publisheds);
            }
        });
        recyclerViewPublishes.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(UserProfileActivity.this,"touch"+position,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecommend() {
        RecommendViewModel recommendViewModel = ViewModelProviders.of(this).get(RecommendViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserProfileActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewRecommend.setLayoutManager(linearLayoutManager);
        RecommendAdapter recommendUserAdapt = new RecommendAdapter(this);
        recommendViewModel.getRecommendList().observe(this, new Observer<PagedList<RecommendUser>>() {
            @Override
            public void onChanged(PagedList<RecommendUser> recommendUsers) {
                recommendUserAdapt.submitList(recommendUsers);
            }
        });
        recyclerViewRecommend.setAdapter(recommendUserAdapt);
    }

    private void dataChange() {
        mUserProfileViewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null){
                    Log.d(TAG, "onChanged: null");
                }
//                Log.d(TAG, "onChanged: " + user.getNickName());
                textUserName.setText(user.getNickName());
                fansNum.setText(String.valueOf(user.getTotalFansCount()));
                attentionNum.setText(String.valueOf(user.getStats().getFollowingCount()));
                hotNum.setText(String.valueOf(user.getFanTicketCount()));
                imgUserPhoto.setImageURI(Uri.parse(user.getAvatarMedium().getUrls().get(0)));
                userLocation.setText(user.getCity());
                userDescription.setText(user.getSignature());
            }
        });
    }

    private void animateOpen() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        final int targetHeight = (int) (getResources().getDisplayMetrics().density * 190 + 0.5);
        layoutRecommend.getLayoutParams().height = targetHeight;
        layoutRecommend.setVisibility(View.VISIBLE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutRecommend.getLayoutParams();
                layoutParams.height = targetHeight;
                layoutParams.bottomMargin = -targetHeight + (int) (value * targetHeight);
                layoutRecommend.setLayoutParams(layoutParams);
            }
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
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutRecommend.getLayoutParams();
                layoutParams.height = targetHeight;
                layoutParams.bottomMargin =  - (int) (value * targetHeight);
                layoutRecommend.setLayoutParams(layoutParams);
            }
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

    @OnClick(R.id.user_recommend)
    public void showRecommend(){

        if(ISRECOMMEND){
            if(ATTENTION){
                ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.user_recommend),"rotation",0,180);
                animator.setDuration(200);
                animator.start();
            }else {
                ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.user_recommend),"rotation",180,0);
                animator.setDuration(200);
                animator.start();
            }
            animateClose();
            ISRECOMMEND = false;

        }else {
            if(ATTENTION){
                ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.user_recommend),"rotation",180,0);
                animator.setDuration(200);
                animator.start();
            }else {
                ObjectAnimator animator = ObjectAnimator.ofFloat(findViewById(R.id.user_recommend),"rotation",0,180);
                animator.setDuration(200);
                animator.start();
            }

            animateOpen();
            initRecommend();
            ISRECOMMEND = true;
        }
    }
}
