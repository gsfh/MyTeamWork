package com.gsfh.myteamwork.vmovie.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.gsfh.myteamwork.vmovie.R;
import com.gsfh.myteamwork.vmovie.adapter.SearchHistoryAdapter;
import com.gsfh.myteamwork.vmovie.adapter.SearchResultAdapter;
import com.gsfh.myteamwork.vmovie.bean.SearchBean;
import com.gsfh.myteamwork.vmovie.dao.Customer;
import com.gsfh.myteamwork.vmovie.dao.CustomerDao;
import com.gsfh.myteamwork.vmovie.dao.DaoMaster;
import com.gsfh.myteamwork.vmovie.dao.DaoSession;
import com.gsfh.myteamwork.vmovie.util.IOKCallBack;
import com.gsfh.myteamwork.vmovie.util.OkHttpTool;
import com.gsfh.myteamwork.vmovie.util.URLConstants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GSFH on 2016-7-19.
 */
public class SearchActivity extends AppCompatActivity {

    private EditText mInputWord;
    private TextView mClearHistoryBtn;
    private ImageView mInputDelBtn;
    private List<SearchBean.DataBean> mDataBeanList = new ArrayList<>();
    private SearchResultAdapter resultAdapter;
    private String keyWord;
    private String totalNum;
    private TextView mHeaderContent;
    private PullToRefreshListView listView;
    private ListView reListView;
    private int page = 1;
    private CustomerDao customerDao;
    private List<Customer> customerList;
    private ListView historyListView;
    private SearchHistoryAdapter historyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initDataBase();
        initHistory();
        initListener();
    }

    private void initDataBase() {

        //创建一个开发环境的Helper类，如果是正式环境调用DaoMaster.OpenHelper
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this,"androidxx",null);
        //通过Handler类获得数据库对象
        SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
        //通过数据库对象生成DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(readableDatabase);
        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();
        //通过DaoSeesion对象获得CustomerDao对象
        customerDao = daoSession.getCustomerDao();
        updateHistoryList();

    }

    private void updateHistoryList(){
        customerList = customerDao.loadAll();
    }

    private void initView() {

        mInputWord = (EditText) findViewById(R.id.search_edit_text);
        mInputDelBtn = (ImageView) findViewById(R.id.search_input_del);
        mClearHistoryBtn = (TextView) findViewById(R.id.search_clear_history_btn);
        listView = (PullToRefreshListView) findViewById(R.id.search_listview);
        historyListView = (ListView) findViewById(R.id.search_history_lv);
        View mHeaderView = LayoutInflater.from(this).inflate(R.layout.search_header_view,null);
        mHeaderContent = (TextView)mHeaderView.findViewById(R.id.search_header_tv);

        reListView = listView.getRefreshableView();
        reListView.addHeaderView(mHeaderView);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void initHistory() {

        historyAdapter = new SearchHistoryAdapter(this,R.layout.search_history_item,customerList);
        historyListView.setAdapter(historyAdapter);
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
                mClearHistoryBtn.setVisibility(View.VISIBLE);
                reListView.setVisibility(View.GONE);

                historyListView.setVisibility(View.VISIBLE);
                historyAdapter.notifyDataSetChanged();

                mDataBeanList.clear();
                page = 1;
            }
        });

        //点击清空历史纪录
        mClearHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customerDao.deleteAll();
                customerList.clear();
                historyAdapter.notifyDataSetChanged();
            }
        });

        //当输入关键词并在输入法中执行回车时
        mInputWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                mClearHistoryBtn.setVisibility(View.GONE);
                reListView.setVisibility(View.VISIBLE);
                keyWord = mInputWord.getText().toString().trim();
                if (!keyWord.isEmpty()){

//                    InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                    im.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

                    historyListView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    initData(keyWord,1);
                    Customer customer = new Customer();
                    customer.setCustomerName(keyWord);
                    customerDao.insert(customer);
                    updateHistoryList();
                }
                return true;
            }
        });

        //搜索结果列表的监听
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
