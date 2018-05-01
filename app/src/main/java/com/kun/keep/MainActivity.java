package com.kun.keep;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.kun.keep.activity.BaseActivity;
import com.kun.keep.activity.HistoryActivity;
import com.kun.keep.activity.SetPlanActivity;
import com.kun.keep.callback.UpdateUiCallBack;
import com.kun.keep.confing.Constant;
import com.kun.keep.service.KeepStepService;
import com.kun.keep.utils.KeepUtils;
import com.kun.keep.utils.SPUtils;
import com.kun.keep.view.KeepView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_data;//历史数据
    TextView tv_set;//设置
    KeepView mKeepView;
    TextView tv_isSupport;//是否支持计步
    SPUtils spUtils;

    private boolean isBind = false;


    @Override
    protected void initdata() {
        //默认设置个8000步
        spUtils.put(Constant.SP_SETP_KEY, "8000");
        /**
         * 第一个参数表示达标多少步
         * 第二个参数当前步数
         */
        mKeepView.setCurrentCount(8000, 0);
        setupService();
    }

    @Override
    protected void initView() {
        tv_data = findViewById(R.id.tv_data);
        tv_set = findViewById(R.id.tv_set);
        mKeepView = findViewById(R.id.cc);
        tv_isSupport = findViewById(R.id.tv_isSupport);
        tv_set.setOnClickListener(this);
        tv_data.setOnClickListener(this);
        spUtils = new SPUtils(Constant.SP_SETP, this);
        initSet();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    private void initSet() {
        boolean supportStepCountSensor = KeepUtils.isSupportStepCountSensor(this);
        if (supportStepCountSensor) {
            tv_isSupport.setText("计步中...");
        } else {
            tv_isSupport.setText("抱歉不支持");
        }
    }


    //程序即使退到后台再到前台时也能开启服务。
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set: //打开设置页面
                startActivity(SetPlanActivity.class);
                break;
            case R.id.tv_data: //查看历史数据
                startActivity(HistoryActivity.class);
                break;
            default:
        }
    }

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, KeepStepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            KeepStepService stepService = ((KeepStepService.StepBinder) service).getService();
            //设置初始化数据
            spUtils.put(Constant.SP_SETP_KEY, "8000");
            mKeepView.setCurrentCount(8000, stepService.getStepCount());

            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    spUtils.put(Constant.SP_SETP_KEY, "8000");
                    mKeepView.setCurrentCount(8000, stepCount);
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

}
