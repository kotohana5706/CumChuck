package kr.edcan.cumchuck.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import kr.edcan.cumchuck.R;
import kr.edcan.cumchuck.activity.RaidGenerateActivity;
import kr.edcan.cumchuck.activity.RaidGenerateInputActivity;
import kr.edcan.cumchuck.model.CommonRecycleData;
import kr.edcan.cumchuck.utils.CumChuckHelper;
import kr.edcan.cumchuck.utils.ImageSingleTon;

/**
 * Created by MalangDesktop on 2016-06-04.
 */
public class CommonRecyclerAdapter extends RecyclerView.Adapter<CommonRecyclerAdapter.ViewHolder> {

    /*
    * 0 : RankingShowActivity - Sort by Rating
    * 1 : RankingShowActivity - Sort by VisitCount
    * 2 : RecommendActivity - Full View
    * 3: NewRaidActivity - SearchView
    **/
    int fragmentPageType;
    int star[] = {R.drawable.btn_fav_favstar, R.drawable.btn_fav_favstar_off};
    Context context;
    ArrayList<CommonRecycleData> arrayList;
    CommonRecycleData data;

    public CommonRecyclerAdapter(Context context, int fragmentPageType, ArrayList<CommonRecycleData> items) {
        this.context = context;
        this.arrayList = items;
        this.fragmentPageType = fragmentPageType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_restaurant_cardview, null);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        data = arrayList.get(position);
        holder.background.setImageResource(CumChuckHelper.returnRandomAyano());
        holder.background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        View.OnClickListener starClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setFavorite(!data.isFavorite());
                holder.star.setImageResource(star[(data.isFavorite()) ? 0 : 1]);
            }
        };
        switch (fragmentPageType) {
            case 0:
                holder.star.setOnClickListener(starClick);
                holder.ratingScore.setVisibility(View.VISIBLE);
                holder.star.setVisibility(View.VISIBLE);
                holder.title.setText(data.getTitle());
                holder.content.setText(data.getContent());
                holder.address.setText(data.getAddress());
                holder.ratingScore.setText(new DecimalFormat("#0.0").format(data.getRating()));
                if (data.getRankingCount() != -1) {
                    holder.ranking.setVisibility(View.VISIBLE);
                    holder.ranking.setText(data.getRankingCount() + "위");
                    holder.ranking.setBackground(context.getResources().getDrawable(getRatingRes(data.getRankingCount())));
                }
                break;
            case 1:
                holder.star.setOnClickListener(starClick);
                holder.visitorsCount.setVisibility(View.VISIBLE);
                holder.star.setVisibility(View.VISIBLE);
                holder.title.setText(data.getTitle());
                holder.content.setText(data.getContent());
                holder.address.setText(data.getAddress());
                holder.visitorsCount.setText(data.getVisitorsCount() + "명이 방문");
                if (data.getRankingCount() != -1) {
                    holder.ranking.setVisibility(View.VISIBLE);
                    holder.ranking.setText(data.getRankingCount() + "위");
                    holder.ranking.setBackground(context.getResources().getDrawable(getRatingRes(data.getRankingCount())));
                }
                break;
            case 2:
                holder.star.setOnClickListener(starClick);
                holder.star.setVisibility(View.VISIBLE);
                holder.ratingScore.setVisibility(View.VISIBLE);
                holder.title.setText(data.getTitle());
                holder.content.setText(data.getContent());
                holder.address.setText(data.getAddress());
                holder.ratingScore.setText(new DecimalFormat("#0.0").format(data.getRating()));
                break;
            case 3:
                holder.background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = arrayList.get(position).getResId();
                        view.getContext().startActivity(new Intent(context, RaidGenerateInputActivity.class).putExtra("resId", s));
                        RaidGenerateActivity.activity.finish();
                    }
                });
                holder.ratingScore.setVisibility(View.VISIBLE);
                holder.title.setText(data.getTitle());
                holder.reseId.setText(data.getResId());
                holder.address.setText(data.getAddress());
                holder.ratingScore.setText(new DecimalFormat("#0.0").format(data.getRating()));
                holder.background.setImageUrl(data.getUrl(), ImageSingleTon.getInstance(context).getImageLoader());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public int getRatingRes(int rankingCount) {
        switch (rankingCount) {
            case 1:
                return R.drawable.rating_1st;
            case 2:
                return R.drawable.rating_2st;
            case 3:
                return R.drawable.rating_3st;
        }
        return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView star;
        NetworkImageView background;
        TextView title, address, content, reseId;
        TextView ratingScore, ranking, visitorsCount;

        public ViewHolder(View itemView) {
            super(itemView);
            reseId = (TextView) itemView.findViewById(R.id.common_resId);
            background = (NetworkImageView) itemView.findViewById(R.id.common_background);
            title = (TextView) itemView.findViewById(R.id.common_title);
            address = (TextView) itemView.findViewById(R.id.common_address);
            content = (TextView) itemView.findViewById(R.id.common_content);
            ratingScore = (TextView) itemView.findViewById(R.id.common_rating);
            ranking = (TextView) itemView.findViewById(R.id.common_visitor_rankingst);
            visitorsCount = (TextView) itemView.findViewById(R.id.common_visitor_count);
            star = (ImageView) itemView.findViewById(R.id.common_star);
        }
    }
}