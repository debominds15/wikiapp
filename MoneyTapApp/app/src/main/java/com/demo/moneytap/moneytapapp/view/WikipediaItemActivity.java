package com.demo.moneytap.moneytapapp.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.demo.moneytap.moneytapapp.R;
import com.demo.moneytap.moneytapapp.model.Pages;
import com.demo.moneytap.moneytapapp.model.WikipediaSearchResponseModel;
import com.demo.moneytap.moneytapapp.util.VolleySingleton;

public class WikipediaItemActivity extends AppCompatActivity {
    private ImageView mBackView;
    private TextView mDescription;
    private NetworkImageView mNetworkImageView;
    private Pages mPages;
    private TextView mHeaderTitle;
    private ImageView mBackBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_wikipedia);
        mBackView = findViewById(R.id.btn_back);
        mBackView.setVisibility(View.VISIBLE);
        mDescription = findViewById(R.id.tv_wiki_desc);
        mHeaderTitle = findViewById(R.id.tv_title);
        mNetworkImageView = findViewById(R.id.iv_wiki);
        mBackBtn = findViewById(R.id.btn_back);

        mPages =(Pages) getIntent().getExtras().getSerializable("pageModel_details");

        if(mPages.getTitle() != null)
        mHeaderTitle.setText(mPages.getTitle());

        if(mPages.getExtract() != null && mPages.getExtract() != null)
        mDescription.setText(mPages.getExtract());

        mNetworkImageView.setDefaultImageResId(R.drawable.placeholder_image);
        if(mPages.getThumbnail() != null && mPages.getThumbnail().getSource() != null)
        imageLoader();

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                WikipediaItemActivity.this.overridePendingTransition(R.anim.anim_left_to_right,R.anim.anim_right_to_left);
            }
        });
    }

    private void imageLoader() {
        ImageLoader imageLoader = VolleySingleton.getInstance(this.getApplicationContext())
                .getImageLoader();
        final String url = mPages.getThumbnail().getSource();
        imageLoader.get(url, ImageLoader.getImageListener(mNetworkImageView, R.drawable.placeholder_image, R.drawable.ic_action_error));
        mNetworkImageView.setImageUrl(url, imageLoader);
    }
}
