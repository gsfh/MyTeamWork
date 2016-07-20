package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SearchResultAdapter;
import com.gsfh.myteamwork.vmovie.bean.SearchBean;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GSFH on 2016-7-19.
 */
public class SearchActivity extends AppCompatActivity {

    private EditText mInputWord;
    private TextView mClearBtn;
    private ImageView mInputDelBtn;
    private List<SearchBean.DataBean> mDataBeanList = new ArrayList<>();
    private SearchResultAdapter resultAdapter;
    private String keyWord;
    private String totalNum;
    private TextView mHeaderContent;
    private PullToRefreshListView listView;
    private ListView reListView;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initListener();
    }

    private void initView() {

        mInputWord = (EditText) findViewById(R.id.search_edit_text);
        mInputDelBtn = (ImageView) findViewById(R.id.search_input_del);
        mClearBtn = (TextView) findViewById(R.id.search_clear_history_btn);
        listView = (PullToRefreshListView) findViewById(R.id.search_listview);
        View mHeaderView = LayoutInflater.from(this).inflate(R.layout.search_header_view,null);
        mHeaderContent = (TextView)mHeaderView.findViewById(R.id.search_header_tv);

        reListView = listView.getRefreshableView();
        reListView.addHeaderView(mHeaderView);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initListener() {

        //当输入关键词时
        mInputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mInputDelBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (TextUtils.isEmpty(s)){
                    mInputDelBtn.setVisibility(View.GONE);
                }
            }
        });

        //点击清空输入
        mInputDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputWord.setText("");
                mInputDelBtn.setVisibility(View.GONE);
                mClearBtn.setVisibility(View.VISIBLE);
                reListView.setVisibility(View.GONE);
                mDataBeanList.clear();
                page = 1;
            }
        });

        //当输入关键词并在输入法中执行回车时
        mInputWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                mClearBtn.setVisibility(View.GONE);
                reListView.setVisibility(View.VISIBLE);
                keyWord = mInputWord.getText().toString().trim();
                if (!keyWord.isEmpty()){

                    initData(keyWord,1);
                }

                return false;
            }
        });

        reListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String postid = mDataBeanList.get(position-2).getPostid();
                Intent intent = new Intent(SearchActivity.this,FirstDetailActivity.class);
                intent.putExtra("id",postid);
                startActivity(intent);
            }
        });

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                mDataBeanList.clear();
                initData(keyWord,1);
                listView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                page++;
                initData(keyWord,page);
                listView.onRefreshComplete();
            }
        });
    }

    private void initData(String keyWord,int page) {

        String p = String.valueOf(page);

        OkHttpTool.newInstance().start(URLConstants.SEARCH_URL+"?p="+p+"&size=10&kw="+keyWord).callback(new IOKCallBack() {
            @Override
            public void success(String result) {

                if (null == result){
                    return;
                }

                Gson gson = new Gson();
                SearchBean searchBean = gson.fromJson(result,SearchBean.class);

                totalNum = searchBean.getTotal();
                mDataBeanList.addAll(searchBean.getData());

                initAdapter();

            }
        });

    }

    private void initAdapter() {

        mHeaderContent.setText("搜索到"+totalNum+"条关于“"+keyWord+"”的内容");
        resultAdapter = new SearchResultAdapter(SearchActivity.this,R.layout.search_item,mDataBeanList);
        reListView.setAdapter(resultAdapter);
    }

    public void cancel(View view){

        finish();
    }

}
