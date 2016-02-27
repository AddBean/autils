package com.addbean.demo.autilsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.addbean.autils.utils.multiadapter.AdapterHelper;
import com.addbean.autils.utils.multiadapter.ListItemEx;
import com.addbean.autils.utils.multiadapter.MultiAdapter;
import com.addbean.autils.views.hoverlist.HoverListView;
import com.addbean.autils.views.hoverlist.IHoverAdpterListener;
import com.addbean.autils.views.listview.IOnPullListener;
import com.addbean.demo.autilsdemo.demo.AListViewDemoActivity;
import com.addbean.demo.autilsdemo.demo.AWaveLayoutActivity;
import com.addbean.demo.autilsdemo.demo.BitmapCacheDemoActivity;
import com.addbean.demo.autilsdemo.demo.BlurDemoActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private List<ListItemEx> mData = new ArrayList<>();

    private List<Integer> mHoverList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HoverListView list = (HoverListView) findViewById(R.id.list);
        list.setTypeList(new int[]{R.layout.item_1, R.layout.item_2});
        list.setAdpterListener(new IHoverAdpterListener() {
            @Override
            public List<Integer> onHoverChanage(LinearLayout view, Object data, int position, int itemResId) {
                Log.e("MainActivity", "MainActivity:" + position + " itemResId：" +
                        "" + itemResId);
                MData mData = (MData) ((ListItemEx) data).getmData();
                switch (itemResId) {
                    case R.layout.item_1:
                        if (!mHoverList.contains(position)) {
                            LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                            LinearLayout ll = (LinearLayout) mInflater.inflate(itemResId, null);
                            ((TextView) ll.findViewById(R.id.tv1)).setText(mData.getMsg());
                            view.removeAllViews();
                            view.addView(ll);
                            mHoverList.add(position);
                            view.setBackgroundColor(0xff0000ff);
                        }
                        break;
                }
                return mHoverList;
            }

            @Override
            public void convert(AdapterHelper helper, MultiAdapter.ConvertViewInf data) {
                MData mData = (MData) data.getData();
                switch (data.getLayoutId()) {
                    case R.layout.item_1:
                        helper.setText(R.id.tv1, mData.getMsg());
                        break;
                    case R.layout.item_2:
                        helper.setText(R.id.tv2, mData.getMsg());
                        break;
                }
            }
        });
        for(int i=0;i<1;i++){
            mData.add(new ListItemEx(0, new MData("自定义控件")));
            mData.add(new ListItemEx(1, new MData("AListView")));
            mData.add(new ListItemEx(1, new MData("AWaveLayout")));
            mData.add(new ListItemEx(0, new MData("自定义效果")));
            mData.add(new ListItemEx(1, new MData("Blur")));
            mData.add(new ListItemEx(0, new MData("缓存框架")));
            mData.add(new ListItemEx(1, new MData("Bitmap")));
            mData.add(new ListItemEx(1, new MData("File")));
        }


        list.setDate(mData);
        list.setHoverResId(R.layout.item_1);
        list.setRefreshAndLoadingEnable(true, true);
        list.setup();
        list.setOnItemClickListener(this);
        list.setOnPullListener(new IOnPullListener() {
            @Override
            public void onRefresh() {
                AsyncTask<Void,Void,Boolean> Asytask=new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        list.addData(0, new MData("自定义控件"));
                        list.setRefreshComplete();
                    }
                };
                Asytask.execute();
            }

            @Override
            public void onLoading() {
                AsyncTask<Void,Void,Boolean> Asytask=new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        super.onPostExecute(aBoolean);
                        list.setLoadingComplete();
                    }
                };
                Asytask.execute();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 2:
                startActivity(new Intent(MainActivity.this, AListViewDemoActivity.class));
                break;
            case 3:
                startActivity(new Intent(MainActivity.this, AWaveLayoutActivity.class));
                break;
            case 5:
                startActivity(new Intent(MainActivity.this, BlurDemoActivity.class));
                break;
            case 7:
                startActivity(new Intent(MainActivity.this, BitmapCacheDemoActivity.class));
                break;

        }
    }

    public class MData {
        private String msg;

        public MData(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

}

