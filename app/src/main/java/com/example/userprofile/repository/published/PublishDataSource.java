package com.example.userprofile.repository.published;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.userprofile.di.DaggerUserProfileComponent;
import com.example.userprofile.model.Published;
import com.example.userprofile.model.PublishedPackage;
import com.example.userprofile.repository.WebServer;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @descriptioon:
 * @author: dinghaoyu
 * @date: 2019-09-30 11:33
 */
public class PublishDataSource extends PageKeyedDataSource<String, Published> {
    private static final String TAG = "PublishDataSource";
 //   private WebServer mWebServer;
    @Inject
    WebServer mWebServer;

    public PublishDataSource() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.huoshan.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        mWebServer = retrofit.create(WebServer.class);
        DaggerUserProfileComponent.create().inject(this);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull LoadInitialCallback<String, Published> callback) {


        mWebServer.getPublishList("/hotsoon/item/profile/published_list/?to_user_id=MS4wLjABAAAAf5wGmBGkNsAAurWjy36emnVqx5oBUmdwIwu0LMyjufF-xqcOSLdBUzpjTNesNqlI&min_time=0&offset=0&count=20&req_from=enter_auto&feed_relate_search=0&ad_user_agent=com.ss.android.ugc.live%2F756+%28Linux%3B+U%3B+Android+9%3B+zh_CN%3B+MI+8%3B+Build%2FPKQ1.180729.001%3B+Chrome%29&last_ad_items=%5B%7B%22id%22%3A6740972633225055500%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6743051240776813828%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A1646115246548004%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6734191253820427524%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6743098442459008263%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6741852305014017287%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6741999483959266572%2C%22show_time%22%3A-1%7D%2C%7B%22id%22%3A6740214630066572551%2C%22show_time%22%3A-1%7D%2C%7B%22id%22%3A6741945705415462158%2C%22show_time%22%3A-1%7D%5D&minor_control_status=0&live_sdk_version=756&iid=86601211949&device_id=67703969166&ac=wifi&channel=xiaomi&aid=1112&app_name=live_stream&version_code=756&version_name=7.5.6&device_platform=android&ssmix=a&device_type=MI+8&device_brand=Xiaomi&language=zh&os_api=28&os_version=9&uuid=860758049787957&openudid=327cb6e9b00fd723&manifest_version_code=756&resolution=1080*2029&dpi=440&update_version_code=7560&_rticket=1570073554458&ab_version=1143691%2C1145841%2C661938%2C712301%2C1166255%2C994817%2C1195873%2C1168131%2C1197943%2C1184820%2C692223%2C1191457%2C889328%2C1019139%2C1114546%2C1143745%2C1193558%2C1095512%2C1039076%2C1197798%2C1192272%2C841788%2C955277%2C1048486%2C1152364%2C947985%2C1199363%2C1197657%2C1048437%2C1030027%2C1050089%2C848690%2C1182061%2C1080023%2C1200114%2C1106061%2C792536%2C1092636%2C929433%2C1174148%2C1146639%2C841998%2C1181284%2C1153992%2C1172220%2C1063522%2C1202674%2C1193008%2C988864%2C1038565%2C1143559%2C1192144%2C1184124%2C1199256%2C1188775%2C1196892%2C1104584%2C1028657%2C1175196%2C1017639%2C1019391%2C1096188%2C689931%2C1159999%2C1198955%2C1127415%2C1002041%2C1119160%2C1165215%2C1170481%2C1194778%2C975749%2C1133591%2C557631%2C1169772%2C1167795%2C1153954%2C1176955%2C1075333%2C1149085%2C1184023%2C1060649%2C1032070%2C1069234%2C1072545%2C1143605%2C1184466%2C1005399%2C1169500%2C1178922%2C956108%2C1046183%2C643984%2C1143672%2C1179244%2C662293%2C1143730%2C1180945%2C1153180%2C1195736%2C1176130%2C1194899%2C1165209%2C1186144%2C1185390%2C1182057%2C768603%2C1132751%2C682009%2C1177124%2C1189945%2C1169258&client_version_code=756&jssdk_version=2.13.3.t7_t28_h3&mcc_mnc=46000&process_name=com.ss.android.ugc.live&new_nav=1&ws_status=CONNECTED&ts=1570073555")
                .enqueue(new Callback<PublishedPackage>() {
            @Override
            public void onResponse(Call<PublishedPackage> call, Response<PublishedPackage> response) {
                if (response.body() != null){
                    Log.d(TAG, "onResponse: "+response.toString());
                    Log.d(TAG, "onResponse: "+response.body().getPublishedList().get(0).getPublishDataData().getSong().getTitle());
                    callback.onResult(response.body().getPublishedList(),null,
                            response.body().getPublishExtra().getMaxTime());
                }
            }

            @Override
            public void onFailure(Call<PublishedPackage> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.toString());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Published> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Published> callback) {

        String address = "/hotsoon/item/profile/published_list/?to_user_id=MS4wLjABAAAAf5wGmBGkNsAAurWjy36emnVqx5oBUmdwIwu0LMyjufF-xqcOSLdBUzpjTNesNqlI&" +
                "max_time=" +params.key+
                "&offset=0&count=20&req_from=enter_auto&feed_relate_search=0&ad_user_agent=com.ss.android.ugc.live%2F756+%28Linux%3B+U%3B+Android+9%3B+zh_CN%3B+MI+8%3B+Build%2FPKQ1.180729.001%3B+Chrome%29&last_ad_items=%5B%7B%22id%22%3A6740972633225055500%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6743051240776813828%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A1646115246548004%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6734191253820427524%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6743098442459008263%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6741852305014017287%2C%22show_time%22%3A1570073534%7D%2C%7B%22id%22%3A6741999483959266572%2C%22show_time%22%3A-1%7D%2C%7B%22id%22%3A6740214630066572551%2C%22show_time%22%3A-1%7D%2C%7B%22id%22%3A6741945705415462158%2C%22show_time%22%3A-1%7D%5D&minor_control_status=0&live_sdk_version=756&iid=86601211949&device_id=67703969166&ac=wifi&channel=xiaomi&aid=1112&app_name=live_stream&version_code=756&version_name=7.5.6&device_platform=android&ssmix=a&device_type=MI+8&device_brand=Xiaomi&language=zh&os_api=28&os_version=9&uuid=860758049787957&openudid=327cb6e9b00fd723&manifest_version_code=756&resolution=1080*2029&dpi=440&update_version_code=7560&_rticket=1570073554458&ab_version=1143691%2C1145841%2C661938%2C712301%2C1166255%2C994817%2C1195873%2C1168131%2C1197943%2C1184820%2C692223%2C1191457%2C889328%2C1019139%2C1114546%2C1143745%2C1193558%2C1095512%2C1039076%2C1197798%2C1192272%2C841788%2C955277%2C1048486%2C1152364%2C947985%2C1199363%2C1197657%2C1048437%2C1030027%2C1050089%2C848690%2C1182061%2C1080023%2C1200114%2C1106061%2C792536%2C1092636%2C929433%2C1174148%2C1146639%2C841998%2C1181284%2C1153992%2C1172220%2C1063522%2C1202674%2C1193008%2C988864%2C1038565%2C1143559%2C1192144%2C1184124%2C1199256%2C1188775%2C1196892%2C1104584%2C1028657%2C1175196%2C1017639%2C1019391%2C1096188%2C689931%2C1159999%2C1198955%2C1127415%2C1002041%2C1119160%2C1165215%2C1170481%2C1194778%2C975749%2C1133591%2C557631%2C1169772%2C1167795%2C1153954%2C1176955%2C1075333%2C1149085%2C1184023%2C1060649%2C1032070%2C1069234%2C1072545%2C1143605%2C1184466%2C1005399%2C1169500%2C1178922%2C956108%2C1046183%2C643984%2C1143672%2C1179244%2C662293%2C1143730%2C1180945%2C1153180%2C1195736%2C1176130%2C1194899%2C1165209%2C1186144%2C1185390%2C1182057%2C768603%2C1132751%2C682009%2C1177124%2C1189945%2C1169258&client_version_code=756&jssdk_version=2.13.3.t7_t28_h3&mcc_mnc=46000&process_name=com.ss.android.ugc.live&new_nav=1&ws_status=CONNECTED&ts=1570073555";

        mWebServer.getPublishList(address).enqueue(new Callback<PublishedPackage>() {
            @Override
            public void onResponse(Call<PublishedPackage> call, Response<PublishedPackage> response) {
                if (response.body() != null){
//                    Log.d(TAG, "onResponse: "+response.body().getPublishedList().get(0).getPublishDataData().getTitle());
                    callback.onResult(response.body().getPublishedList(),
                            response.body().getPublishExtra().getMaxTime());
                }
            }

            @Override
            public void onFailure(Call<PublishedPackage> call, Throwable t) {

            }
        });
    }
}
