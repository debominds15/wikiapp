package com.demo.moneytap.moneytapapp.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.demo.moneytap.moneytapapp.R;
import com.demo.moneytap.moneytapapp.model.Pages;
import com.demo.moneytap.moneytapapp.model.Thumbnail;
import com.demo.moneytap.moneytapapp.util.VolleySingleton;
import com.demo.moneytap.moneytapapp.view.HomeActivity;
import com.demo.moneytap.moneytapapp.view.WikipediaItemActivity;

import java.util.List;

/**
 * Adapter class to bind Wikipedia search data
 */
public class WikiHomeAdapter extends RecyclerView.Adapter<WikiHomeAdapter.WikiHolder> {

    private List<Pages> wikiResponseModelPages;
    private HomeActivity mActivity;
    private String mQuery;

    public WikiHomeAdapter(HomeActivity activity, List<Pages> wikiResponseModelList, String query){
        mActivity = activity;
        this.wikiResponseModelPages = wikiResponseModelList;
        mQuery = query;
    }
    @Override
    public WikiHomeAdapter.WikiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType)
        {
            case VIEW_TYPES.Normal:
                view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.wiki_search_item, parent, false);
                return new WikiHolderNormal(view);
            case VIEW_TYPES.Footer:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_load_more, parent, false);
                return new WikiHolderLoadButton(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.wiki_search_item, parent, false);
                return new WikiHolderNormal(view);
        }
    }

    @Override
    public void onBindViewHolder(WikiHolder holder, int position) {
        final Pages pageModel = wikiResponseModelPages.get(position);
        if(holder instanceof WikiHolderNormal){
            WikiHolderNormal normalHolder = (WikiHolderNormal) holder;
            if (pageModel.getTitle() != null)
                normalHolder.mTitle.setText(pageModel.getTitle());

            if (pageModel.getTerms() != null && pageModel.getTerms().getDescription() != null)
                normalHolder.mDescription.setText(pageModel.getTerms().getDescription().get(0).toString());

            normalHolder.networkImageView.setDefaultImageResId(R.drawable.placeholder_image);
            if (pageModel.getThumbnail() != null && pageModel.getThumbnail().getSource() != null)
                imageLoader(normalHolder.networkImageView, pageModel.getThumbnail());

            normalHolder.mLayoutWikiItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("pageModel_details", pageModel);
                    Intent intent = new Intent(mActivity, WikipediaItemActivity.class);
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.anim_enter_right_left, R.anim.anim_enter_left_right);
                }
            });
        }
        else if(holder instanceof WikiHolderLoadButton){
            WikiHolderLoadButton wikiHolderLoad = (WikiHolderLoadButton) holder;
            wikiHolderLoad.mBtnLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mActivity.performNetworkApiCall(mQuery, true);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return wikiResponseModelPages.size();
    }


    public class WikiHolderNormal extends WikiHolder{
        private TextView mDescription;
        private TextView mTitle;
        private NetworkImageView networkImageView;
        private RelativeLayout mLayoutWikiItem;

        public WikiHolderNormal(View view){
            super(view);
            mDescription = view.findViewById(R.id.tv_wiki_desc);
            mTitle = view.findViewById(R.id.tv_wiki_title);
            networkImageView = view.findViewById(R.id.iv_wiki);
            mLayoutWikiItem = view.findViewById(R.id.layout_wiki_item);
        }
    }
    public class WikiHolderLoadButton extends WikiHolder{
        private Button mBtnLoad;
        public WikiHolderLoadButton(View view){
            super(view);
            mBtnLoad = view.findViewById(R.id.btn_load_more);
        }
    }

    private void imageLoader(NetworkImageView networkImageView, Thumbnail thumbnail) {
        ImageLoader imageLoader = VolleySingleton.getInstance(mActivity)
                .getImageLoader();
        final String url = thumbnail.getSource();
        imageLoader.get(url, ImageLoader.getImageListener(networkImageView, R.drawable.placeholder_image, R.drawable.ic_action_error));
        networkImageView.setImageUrl(url, imageLoader);
    }

    private class VIEW_TYPES {
        public static final int Normal = 1;
        public static final int Footer = 2;
    }
    @Override
    public int getItemViewType(int position) {

        if(position == wikiResponseModelPages.size()-1)
            return VIEW_TYPES.Footer;
        else
            return VIEW_TYPES.Normal;

    }

    public class WikiHolder extends  RecyclerView.ViewHolder{
        public WikiHolder(View itemView) {
            super(itemView);
        }
    }
}
