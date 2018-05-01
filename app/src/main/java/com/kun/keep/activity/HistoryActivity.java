package com.kun.keep.activity;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kun.keep.KeepApplicon;
import com.kun.keep.R;
import com.kun.keep.adapter.CommonAdapter;
import com.kun.keep.adapter.CommonViewHolder;
import com.kun.keep.greendao.KeepDataDao;
import com.kun.keep.keep.bean.KeepData;

import java.util.List;

/**
 * user:kun
 * Date:2018/4/23 or 3:01 PM
 * email:hekun@gamil.com
 * Desc:
 */
public class HistoryActivity extends BaseActivity {

    ListView mListView;
    KeepDataDao dataDao;

    @Override
    protected void initdata() {
        setEmptyView(mListView);
        List<KeepData> list = dataDao.queryBuilder().list();
        mListView.setAdapter(new CommonAdapter<KeepData>(this, list, R.layout.item) {
            @Override
            protected void convertView(View item, KeepData keepData) {
                TextView tv_date = CommonViewHolder.get(item, R.id.tv_date);
                TextView tv_step = CommonViewHolder.get(item, R.id.tv_step);
                tv_date.setText(keepData.getToday());
                tv_step.setText(keepData.getStep() + "步");
            }
        });
    }

    @Override
    protected void initView() {
        mListView = findViewById(R.id.his_listView);
        setTitle("历史记录");
        dataDao = KeepApplicon.getInstances().getKeepDao();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_history;
    }

    protected <T extends View> T setEmptyView(ListView listView) {
        TextView emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
        return (T) emptyView;
    }
}
