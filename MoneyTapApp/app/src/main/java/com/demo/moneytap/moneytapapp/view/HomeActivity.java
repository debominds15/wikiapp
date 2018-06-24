package com.demo.moneytap.moneytapapp.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.demo.moneytap.moneytapapp.R;
import com.demo.moneytap.moneytapapp.adapter.WikiHomeAdapter;
import com.demo.moneytap.moneytapapp.model.Pages;
import com.demo.moneytap.moneytapapp.model.WikipediaSearchResponseModel;
import com.demo.moneytap.moneytapapp.util.AppUtil;
import com.demo.moneytap.moneytapapp.util.VolleySingleton;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Home Activity to carry out search operation
 */
public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText mEditSearch;
    private String BASE_URL = "https://en.wikipedia.org//w";
    private RelativeLayout mSearchLayout;
    private WikiHomeAdapter mAdapter;
    private List<Pages> mPages;
    private ImageView mSearch;
    private LinearLayoutManager mLayoutManager;
    private int itemMoreToBeLoaded = 10;
    private ImageView mImgWiki;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_wiki);
        mSearchLayout = findViewById(R.id.layout_search);
        mEditSearch = findViewById(R.id.edit_search);
        mSearch = findViewById(R.id.iv_search);
        mImgWiki = findViewById(R.id.iv_wiki);
        mPages = new ArrayList<Pages>();

        mSearchLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mImgWiki.setVisibility(View.VISIBLE);

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!StringUtils.isEmpty(charSequence.toString())) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    performNetworkApiCall(charSequence.toString(), false);
                } else {
                    mPages.clear();
                    mRecyclerView.setVisibility(View.GONE);
                    mImgWiki.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isEmpty(mEditSearch.getText().toString())) {
                    AppUtil.hideKeyboard(mEditSearch, HomeActivity.this);
                    AppUtil.show(HomeActivity.this, getString(R.string.text_please_wait));
                    performNetworkApiCall(mEditSearch.getText().toString(), false);
                }
            }
        });

    }

    /**
     * Network call using volley library
     * @param query
     * @param isScrolled
     */
    public void performNetworkApiCall(final String query, final boolean isScrolled) {
        RequestQueue queue = VolleySingleton.getInstance(this).getRequestQueue();
        if (isScrolled)
            itemMoreToBeLoaded += 10;
        else
            itemMoreToBeLoaded = 10;

        VolleyLog.DEBUG = true;

        String uri = BASE_URL + "/api.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new WikiApiTask().execute(response);
            }
        }, errorListener) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
                    if (cacheEntry == null) {
                        cacheEntry = new Cache.Entry();
                    }
                    final long cacheHitButRefreshed = 1000;
                    final long cacheExpired = 24 * 60 * 60 * 1000;
                    long now = System.currentTimeMillis();
                    final long softExpire = now + cacheHitButRefreshed;
                    final long ttl = now + cacheExpired;
                    cacheEntry.data = response.data;
                    cacheEntry.softTtl = softExpire;
                    cacheEntry.ttl = ttl;
                    String headerValue;
                    headerValue = response.headers.get("Date");
                    if (headerValue != null) {
                        cacheEntry.serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    headerValue = response.headers.get("Last-Modified");
                    if (headerValue != null) {
                        cacheEntry.lastModified = HttpHeaderParser.parseDateAsEpoch(headerValue);
                    }
                    cacheEntry.responseHeaders = response.headers;
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new String(jsonString), cacheEntry);
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }
            @Override
            public Priority getPriority() {
                return Priority.LOW;
            }

            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "query");
                params.put("format", "json");
                params.put("prop", "pageimages|pageterms|extracts");
                params.put("exintro", "");
                params.put("explaintext", "");
                params.put("generator", "prefixsearch");
                params.put("redirects", "1");
                params.put("formatversion", "2");
                params.put("piprop", "thumbnail");
                params.put("pithumbsize", "50");
                params.put("pilimit", "10");
                params.put("wbptterms", "description");
                params.put("gpssearch", query);
                params.put("gpslimit", String.valueOf(itemMoreToBeLoaded));

                return params;
            }

        };
        queue.add(stringRequest);
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * AsyncTask to perform json parsing to enhance UI thread performance
     */
    class WikiApiTask extends AsyncTask<String, String, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AppUtil.show(HomeActivity.this, getString(R.string.text_please_wait));
        }

        @Override
        protected void onPostExecute(Object response) {
            super.onPostExecute(response);
            AppUtil.dismiss();
            if (response instanceof WikipediaSearchResponseModel) {
                WikipediaSearchResponseModel obj = (WikipediaSearchResponseModel) response;
                mPages = obj.getQuery().getPages();

                mAdapter = new WikiHomeAdapter(HomeActivity.this, mPages, mEditSearch.getText().toString());

                mLayoutManager = new LinearLayoutManager(HomeActivity.this);
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mLayoutManager.setAutoMeasureEnabled(true);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLayoutManager.getOrientation());
                mRecyclerView.addItemDecoration(dividerItemDecoration);
                mRecyclerView.setAdapter(mAdapter);

                mImgWiki.setVisibility(View.GONE);
            }
        }

        @Override
        protected Object doInBackground(String... params) {
            String response = params[0];
            Gson g = new Gson();
            WikipediaSearchResponseModel p = g.fromJson(response, WikipediaSearchResponseModel.class);
            return p;
        }
    }
}
