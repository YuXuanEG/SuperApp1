package com.example.edwardgu.superapp.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.edwardgu.superapp.R;

import java.util.List;


/**
 * PagerAdapter 实现：
 * 1. 必须要重写／实现 四个方法；不能够只有两个
 */
public class PicturePageAdapter extends PagerAdapter {

    private static final String TAG = "PicturePageAdapter";
    private Context mContext;
    private List<Integer> mImageResources;
    private View.OnClickListener mOnClickListener;

    public PicturePageAdapter(Context context, List<Integer> imageResources) {
        mContext = context;
        mImageResources = imageResources;
    }

    public void setOnJumpNextListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }

    /**
     * 返回ViewPager一共有多少页
     *
     * @return
     */
    @Override
    public int getCount() {
        int ret;
        ret = 3000;
        return ret;
    }

    /**
     * View 参数 是否 和 Object 有关系
     * <p/>
     * ViewPager 会进行对象的管理，利用对象来管理和定位View
     *
     * @param view
     * @param object 这个参数，就是 instantiateItem 方法的返回值对象
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        Log.d(TAG, "isViewFromObject");
        // TODO: 说明
        return view == object;
    }

    /**
     * 当ViewPager 需要显示一页的时候，会调用这个方法，传递指定的页数
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d(TAG, "instantiateItem " + position);

        View ret = null;

        // 加载布局或者创建控件，然后添加到 ViewGroup
        ImageView imageView = new ImageView(mContext);
        imageView
                .setImageResource(
                        mImageResources
                                .get(position % mImageResources.size()));

        // !!! 必须把创建／加载的视图，手动添加到ViewGroup
        ret = imageView;


        container.addView(ret);

        return ret;
    }

    /**
     * 当 ViewPager 把一个页面，左右移动的时候，达到一个限制的位置之后，就会删除这个页面
     * 调用这个方法
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        Log.d(TAG, "destroyItem " + position);

        // 必须手动的把创建的View，从ViewGroup删除
        container.removeView((View) object);

    }
}
