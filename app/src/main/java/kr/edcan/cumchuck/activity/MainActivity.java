package kr.edcan.cumchuck.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kr.edcan.cumchuck.R;
import kr.edcan.cumchuck.model.Raid;
import kr.edcan.cumchuck.utils.CumChuckHelper;
import kr.edcan.cumchuck.utils.CumChuckNetworkHelper;
import kr.edcan.cumchuck.utils.DataManager;
import kr.edcan.cumchuck.utils.NetworkInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView one, two, three;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    RelativeLayout recommend;
    ImageView favorite, mypage;
    TextView currentRaidJoin;
    NetworkInterface service;
    DataManager manager;
    Call<List<Raid>> getSelfRaidStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
        loadData();
        setDefault();
    }


    private void checkSelfRaidInfo() {
        service = CumChuckNetworkHelper.getNetworkInstance();
        getSelfRaidStatus = service.getSelfRaidStatus(manager.getActiveUser().second.getId());
        getSelfRaidStatus.enqueue(new Callback<List<Raid>>() {
            @Override
            public void onResponse(Call<List<Raid>> call, Response<List<Raid>> response) {
                Log.e("asdf", response.code()+"");
                switch (response.code()) {
                    case 200:
                        Toast.makeText(MainActivity.this, "현재 진행중인 " + response.body().get(0).getTitle() + "레이드가 있습니다!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RaidInfoShowActivity.class).putExtra("raidId", response.body().get(0).getId()));
                        break;
                    case 403:
                        return;
                }
            }

            @Override
            public void onFailure(Call<List<Raid>> call, Throwable t) {
                    Log.e("asdf", t.getMessage());
            }
        });
    }

    private void loadData() {
        manager = new DataManager();
        manager.initializeManager(getApplicationContext());
    }

    private void setDefault() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        recommend = (RelativeLayout) findViewById(R.id.recommand_recyclerView);
        favorite = (ImageView) toolbar.findViewById(R.id.open_favorite);
        mypage = (ImageView) toolbar.findViewById(R.id.open_myaccount);
        currentRaidJoin = (TextView) findViewById(R.id.current_raid_join);
        one = (ImageView) findViewById(R.id.main_firstImage);
        two = (ImageView) findViewById(R.id.main_secondImage);
        three = (ImageView) findViewById(R.id.main_thirdImage);
        favorite.setOnClickListener(this);
        mypage.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        currentRaidJoin.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_favorite:
                startActivity(new Intent(getApplicationContext(), FavoriteViewActivity.class));
                break;
            case R.id.open_myaccount:
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                break;
            case R.id.fab:
                startActivity(new Intent(getApplicationContext(), RaidGenerateActivity.class));
                break;
            case R.id.current_raid_join:
                startActivity(new Intent(getApplicationContext(), RaidInfoShowActivity.class));
                break;
            case R.id.main_secondImage:
                startActivity(new Intent(getApplicationContext(), RaidFromFriendActivity.class));
                break;
            case R.id.main_thirdImage:
                startActivity(new Intent(getApplicationContext(), RecommendActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        one.setImageResource(CumChuckHelper.returnRandomAyano());
        two.setImageResource(CumChuckHelper.returnRandomAyano());
        three.setImageResource(CumChuckHelper.returnRandomAyano());
        checkSelfRaidInfo();
    }
}
