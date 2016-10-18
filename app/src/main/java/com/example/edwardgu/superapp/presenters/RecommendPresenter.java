package com.example.edwardgu.superapp.presenters;

import com.example.edwardgu.superapp.fragments.IRecomendView;
import com.example.edwardgu.superapp.model.IRecomendItemModel;

/**
 * Created by EdwardGu on 2016/10/10.
 */

public class RecommendPresenter {
    private IRecomendView mRecomendView;
    private IRecomendItemModel mModel;

    public RecommendPresenter(IRecomendView recomendView) {
        mRecomendView = recomendView;
    }
    public void setModel(IRecomendItemModel model){
        mModel=model;
    }
    public void updateTypeText(){
        if (mModel != null) {
            mRecomendView.setTypeText(mModel.getType());
        }
    }
}
