package com.example.edwardgu.superapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.model.RecommendBodyItem;
import com.example.edwardgu.superapp.model.RecommendItem;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardGu on 2016/10/10.
 */

/**
 * 推荐界面列表适配器
 */
public class RecommendListAdapter extends RecyclerView.Adapter {
    public static final int TYPE_RECOMMEND = 1;
    public static final int TYPE_LIVE = 2;
    public static final int TYPE_BANGUMI = 3;
    public static final int TYPE_REGION = 4;
    public static final int TYPE_WEBLINK = 5;
    public static final int TYPE_ACTIVITY = 6;
    public static final int TYPE_AD= 8;
    private Context mContext;
    private List<RecommendItem> mItems;



    public RecommendListAdapter(Context context, List<RecommendItem> items) {
        mContext = context;
        mItems = items;
    }


    /**
     * 1.RecyclerView Adapter 以 ViewHolder 为视图复用的标准
     * 2.RecyclerView 不提供 OnItemClickListener接口
     * 3.Adapter 同BaseAdapter，都提供 多布局的支持
     */


    @Override
    public int getItemViewType(int position) {
        RecommendItem item = mItems.get(position);
        String type = item.getType();
        int ret = 0;

        switch (type) {
            case "recommend":
                ret = 1;
                break;
            case "live":
                ret = 2;
                break;
            case "bangumi_2":
                ret = 3;
                break;
            case "region":
                ret = 4;
                break;
            case "weblink":
                ret = 5;
                break;
            case "activity":
                ret = 6;
                break;
            case "ad":
                ret=8;
                break;
            default:
                ret = 7;
                break;
        }
        return ret;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        RecyclerView.ViewHolder ret = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);


        switch (viewType) {

            case TYPE_AD:
                itemView =
                        inflater.inflate(R.layout.item_recommend_ad, parent, false);
                ret = new ADViewHolder(itemView);
                break;
            case TYPE_RECOMMEND:
                // TODO: 2016/10/10 加入布局推荐
                itemView =
                        inflater.inflate(R.layout.item_recommend_recommend, parent, false);
                ret = new RecommendViewHolder(itemView);
                break;
            case TYPE_LIVE:
                // TODO: 2016/10/10 加入布局直播
                itemView =
                        inflater.inflate(R.layout.item_recommend_live, parent, false);
                ret = new LiveViewHolder(itemView);
                break;
            case TYPE_BANGUMI:
                // TODO: 2016/10/10 加入布局番剧
                itemView =
                        inflater.inflate(R.layout.item_recommend_bangumi, parent, false);
                ret = new RecommendViewHolder(itemView);
                break;
            case TYPE_REGION:
                // TODO: 2016/10/10 加入布局区域
                itemView =
                        inflater.inflate(R.layout.item_recommend_region, parent, false);
                ret = new RecommendViewHolder(itemView);
                break;
            case TYPE_WEBLINK:
                // TODO: 2016/10/10 加入布局网络连接
                itemView =
                        inflater.inflate(R.layout.item_recommend_weblink, parent, false);
                ret = new WebLinkViewHolder(itemView);
                break;
            case TYPE_ACTIVITY:
                // TODO: 2016/10/10 加入布局活动
                itemView =
                        inflater.inflate(R.layout.item_recommend_activity, parent, false);
                ret = new RecommendViewHolder(itemView);
                break;
            case 7:
//                itemView =
//                        inflater.inflate(R.layout.item_recommend_gl, parent, false);
//                ret = new GLViewHolder(itemView);
//                break;
            default:
                itemView =
                        inflater.inflate(R.layout.item_recommend_simple, parent, false);
                ret = new SimplerViewHolder(itemView);
                break;
        }


        return ret;
    }


    /**
     * 把数据绑定到ViewHolder指向的View中
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecommendItem recommendItem = mItems.get(position);
        if (holder instanceof SimplerViewHolder) {
            SimplerViewHolder simplerViewHolder = (SimplerViewHolder) holder;
            simplerViewHolder.bindView(recommendItem);
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if (mItems != null) {
            ret = mItems.size();
        }
        return ret;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ViewHolder
    ///////////////////////////////////////////////////////////////////////////
    private static class SimplerViewHolder extends RecyclerView.ViewHolder {
        /**
         * 通用的View缓存功能
         */
        private SparseArrayCompat<View> mViews;
//        private TextView textView;

        SimplerViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArrayCompat<>();
        }


        View getChildView(int rid) {
            View ret;
            ret = mViews.get(rid);
            if (ret == null) {
                ret = itemView.findViewById(rid);
                if (ret != null) {
                    mViews.put(rid, ret);
                }
            }
            return ret;
        }

        /**
         * @param recommendItem
         */
        void bindView(RecommendItem recommendItem) {
            TextView txt = (TextView) getChildView(R.id.item_recommend_text);
            txt.setText(recommendItem.getType());
        }

        /**
         * 利用反射，获取视图
         *
         * @param name
         * @return
         */
        View getChildView(String name) {
            View ret = null;
            if (name != null) {
                Class aClass = R.id.class;
                int id = 0;
                try {
                    Field field = aClass.getDeclaredField(name);
                    field.setAccessible(true);
                    id = field.getInt(aClass);//获取类成员的数值
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (id != 0) {
                    ret = getChildView(id);
                }
            }

            return ret;
        }


    }

    private static class RecommendViewHolder extends SimplerViewHolder implements View.OnClickListener {

        public RecommendViewHolder(View itemView) {
            super(itemView);

            for (int i = 0; i < 4; i++) {
                View view = getChildView("item_recommend_card_view_" + i);
                if (view != null) {

                    //每一个Body内部的视频点击事件处理
                    view.setOnClickListener(this);
                }
            }
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag != null) {
                if (tag instanceof RecommendBodyItem) {
                    RecommendBodyItem bodyItem = (RecommendBodyItem) tag;
                    //EventBus 传递对象给 当前Fragment ，由Fragment来跳转到详情
                    EventBus.getDefault().post(bodyItem);
                }
            }
        }

        @Override
        void bindView(RecommendItem recommendItem) {
            TextView txtTitle = (TextView) getChildView(R.id.item_recommend_header_title);
            if (txtTitle != null) {
                String headTitle = recommendItem.getHeadTitle();
                txtTitle.setText(headTitle);
            }
            //------------------
            //条目
            List<RecommendBodyItem> body = recommendItem.getBody();
            //默认4个
            if (body.size() >= 4) {
                //使用反射动态获取多个控件ID
                for (int i = 0; i < 4; i++) {
                    //获取条目
                    RecommendBodyItem bodyItem = body.get(i);

                    View view = getChildView("item_recommend_card_view_" + i);
                    view.setTag(bodyItem);

                    ImageView imageView = (ImageView) getChildView("item_recommend_body_icon_" + i);
                    if (imageView != null) {
                        String cover = bodyItem.getCover();
                        if (cover != null) {
                            // TODO: 2016/10/11 显示图片
                            Context context = imageView.getContext();
                            Picasso.with(context)
                                    .load(cover)
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(320, 200)
//                                    .noFade()
                                    .into(imageView);
                        }
                    }
                    TextView txtBodyTitle = (TextView) getChildView("item_recommend_body_title_" + i);
                    if (txtBodyTitle != null) {
                        String title = bodyItem.getTitle();
                        if (title != null) {
                            txtBodyTitle.setText(title);
                        }
                    }
                    TextView txtCount = (TextView) getChildView("item_recommend_count_" + i);
                    if (txtCount != null) {
                        String play = bodyItem.getPlay();
                        if (play != null) {
                            txtCount.setText(play);
                        }
                    }
                    TextView txtDanmaku = (TextView) getChildView("item_recommend_danmaku_" + i);
                    if (txtDanmaku != null) {
                        String danmaku = bodyItem.getDanmaku();
                        if (danmaku != null) {
                            txtDanmaku.setText(danmaku);
                        }
                    }
                }
            } else {
                // TODO: 2016/10/11 未来需要根据个数进行适配，代码动态添加布局
            }
        }


    }

    private static class LiveViewHolder extends SimplerViewHolder implements View.OnClickListener {

        public LiveViewHolder(View itemView) {
            super(itemView);
            for (int i = 0; i < 4; i++) {
                View view = getChildView("item_live_card_view_" + i);
                if (view != null) {

                    //每一个Body内部的视频点击事件处理
                    view.setOnClickListener(this);
                }
            }
        }

        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            if (tag != null) {
                if (tag instanceof RecommendBodyItem) {
                    RecommendBodyItem bodyItem = (RecommendBodyItem) tag;
                    //EventBus 传递对象给 当前Fragment ，由Fragment来跳转到详情
                    EventBus.getDefault().post(bodyItem);
                }
            }
        }

        @Override
        void bindView(RecommendItem recommendItem) {
            TextView txtTitle = (TextView) getChildView(R.id.item_live_header_title);
            if (txtTitle != null) {
                String headTitle = recommendItem.getHeadTitle();
                txtTitle.setText(headTitle);
            }
            //------------------
            //条目
            List<RecommendBodyItem> body = recommendItem.getBody();
            //默认4个
            if (body.size() >= 4) {
                //使用反射动态获取多个控件ID
                for (int i = 0; i < 4; i++) {
                    //获取条目
                    RecommendBodyItem bodyItem = body.get(i);

                    View view = getChildView("item_live_card_view_" + i);
                    view.setTag(bodyItem);

                    ImageView imageView = (ImageView) getChildView("item_live_body_icon_" + i);
                    if (imageView != null) {
                        String cover = bodyItem.getCover();
                        if (cover != null) {
                            // TODO: 2016/10/11 显示图片
                            Context context = imageView.getContext();
                            Picasso.with(context)
                                    .load(cover)
                                    .config(Bitmap.Config.RGB_565)
                                    .resize(320, 200)
//                                    .noFade()
                                    .into(imageView);
                        }
                    }
                    TextView txtBodyTitle = (TextView) getChildView("item_live_body_title_" + i);
                    if (txtBodyTitle != null) {
                        String title = bodyItem.getTitle();
                        if (title != null) {
                            txtBodyTitle.setText(title);
                        }
                    }
                    TextView txtCount = (TextView) getChildView("item_live_count_" + i);
                    if (txtCount != null) {
                        String play = bodyItem.getPlay();
                        if (play != null) {
                            txtCount.setText(play);
                        }
                    }
                    TextView txtDanmaku = (TextView) getChildView("item_live_danmaku_" + i);
                    if (txtDanmaku != null) {
                        String danmaku = bodyItem.getDanmaku();
                        if (danmaku != null) {
                            txtDanmaku.setText(danmaku);
                        }
                    }
                }
            } else {
                // TODO: 2016/10/11 未来需要根据个数进行适配，代码动态添加布局
            }
        }

    }

    private static class Bangumi2ViewHolder extends SimplerViewHolder {

        public Bangumi2ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class RegionViewHolder extends SimplerViewHolder {

        public RegionViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class WebLinkViewHolder extends SimplerViewHolder implements View.OnClickListener {

        private RecommendBodyItem mBodyItem;

        public WebLinkViewHolder(View itemView) {
            super(itemView);

            View view = getChildView("item_recommend_weblink_pic");
            if (view != null) {
                //每一个Body内部的视频点击事件处理
                view.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {

            if (mBodyItem != null) {
                //EventBus 传递对象给 当前Fragment ，由Fragment来跳转到详情
                EventBus.getDefault().post(mBodyItem);
            }
        }

        @Override
        void bindView(RecommendItem recommendItem) {
            mBodyItem = recommendItem.getBody().get(0);
            String cover = mBodyItem.getCover();

            ImageView weblink_pic = (ImageView) getChildView("item_recommend_weblink_pic");
            if (weblink_pic != null) {
                Picasso.with(weblink_pic.getContext())
                        .load(cover)
                        .config(Bitmap.Config.RGB_565)
                        .into(weblink_pic);
            }

        }


    }

    private static class HuodongViewHolder extends SimplerViewHolder {

        public HuodongViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class GLViewHolder extends SimplerViewHolder {

        public GLViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindView(RecommendItem recommendItem) {
            List<RecommendBodyItem> body = recommendItem.getBody();
            RecommendBodyItem bodyItem = body.get(0);
            String cover = bodyItem.getCover();

            ImageView gl_pic = ((ImageView) getChildView("item_recommend_gl_pic"));
            if (gl_pic != null) {
                Picasso.with(gl_pic.getContext())
                        .load(cover)
                        .config(Bitmap.Config.RGB_565)
                        .into(gl_pic);
            }
        }
    }

    private static class ADViewHolder extends SimplerViewHolder {

        public ADViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        void bindView(RecommendItem recommendItem) {
            ViewPager adPager = (ViewPager) getChildView(R.id.ad_pager);
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.game_1);
            images.add(R.drawable.game_2);
            images.add(R.drawable.game_3);
            PicturePageAdapter pageAdapter = new PicturePageAdapter(adPager.getContext(), images);
            adPager.setAdapter(pageAdapter);
        }
    }


}
