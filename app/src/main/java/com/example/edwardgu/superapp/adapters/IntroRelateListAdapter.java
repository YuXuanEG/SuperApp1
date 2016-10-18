package com.example.edwardgu.superapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.model.VideoDetailIntro;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by EdwardGu on 2016/10/15.
 */

public class IntroRelateListAdapter extends RecyclerView.Adapter {

    private List<VideoDetailIntro.RelateItem> mList;
    private Context mContext;

    public IntroRelateListAdapter(List<VideoDetailIntro.RelateItem> list, Context context) {
        mList = list;
        mContext = context;
    }
    @Override
    public int getItemCount() {
        int ret = 0;
        if (mList != null) {
            ret=mList.size();
        }
        return ret;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder ret = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        itemView=inflater.inflate(R.layout.item_video_intro_relate,parent,false);
        ret=new IntroViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoDetailIntro.RelateItem relateItem=mList.get(position);
        if (holder instanceof IntroViewHolder) {
            IntroViewHolder introViewHolder = (IntroViewHolder) holder;
            introViewHolder.bindView(relateItem);
        }
    }

    private static class IntroViewHolder extends RecyclerView.ViewHolder{

        private SparseArrayCompat<View> mViews;

        public IntroViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArrayCompat<>();
        }
        void bindView(VideoDetailIntro.RelateItem relateItem){
            ImageView imagePic = (ImageView) itemView.findViewById(R.id.intro_relate_list_pic);
            Picasso.with(imagePic.getContext())
                    .load(relateItem.getPic())
                    .config(Bitmap.Config.RGB_565)
                    .into(imagePic);
            TextView title = (TextView) itemView.findViewById(R.id.intro_relate_list_title);
            title.setText(relateItem.getTitle());
            TextView name = (TextView) itemView.findViewById(R.id.intro_relate_list_name);
            name.setText(relateItem.getOwner().getName());
//            TextView danmaku = (TextView) itemView.findViewById(R.id.intro_relate_list_danmaku);
//            danmaku.setText(relateItem.getStat().getDanmaku());
//            TextView view = (TextView) itemView.findViewById(R.id.intro_relate_list_view);
//            view.setText(relateItem.getStat().getView());
        }

    }



}
