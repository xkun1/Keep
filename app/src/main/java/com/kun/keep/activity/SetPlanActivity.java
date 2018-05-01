package com.kun.keep.activity;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kun.keep.R;
import com.kun.keep.confing.Constant;
import com.kun.keep.utils.SPUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * user:kun
 * Date:2018/4/23 or 3:07 PM
 * email:hekun@gamil.com
 * Desc:
 */
public class SetPlanActivity extends BaseActivity implements View.OnClickListener {

    EditText mEditText;// 设置步数
    CheckBox mCheckBox;//是否开启
    TextView tv_remind_time;
    Button mButton;
    SPUtils spUtils;
    private String remind;


    @Override
    protected void initdata() {
        String planWalk_QTY = spUtils.getString(Constant.SP_SETP_KEY);
        String remind = spUtils.getString(Constant.COLOR_ARC_WIDE);
        String achieveTime = spUtils.getString(Constant.SP_SETP_TIME);
        if (!planWalk_QTY.isEmpty()) {
            if ("0".equals(planWalk_QTY)) {
                mEditText.setText("7000");
            } else {
                mEditText.setText(planWalk_QTY);
            }
        }
        if (remind != null) {
            if (!remind.isEmpty()) {
                if ("0".equals(remind)) {
                    mCheckBox.setChecked(false);
                } else if ("1".equals(remind)) {
                    mCheckBox.setChecked(true);
                }
            }
        }
        if (achieveTime != null) {
            if (!achieveTime.isEmpty()) {
                tv_remind_time.setText(achieveTime);
            }
        }
    }

    @Override
    protected void initView() {
        mEditText = findViewById(R.id.tv_step_number);
        mCheckBox = findViewById(R.id.cb_remind);
        mButton = findViewById(R.id.btn_save);
        tv_remind_time = findViewById(R.id.tv_remind_time);
        setTitle("锻炼计划");
        spUtils = new SPUtils(Constant.SP_SETP, this);
        mButton.setOnClickListener(this);
        tv_remind_time.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_plan;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.tv_remind_time:
                showTimeDialog();
                break;
            default:
        }
    }

    private void showTimeDialog() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        final DateFormat df = new SimpleDateFormat("HH:mm");
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String remaintime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                Date date = null;
                try {
                    date = df.parse(remaintime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (null != date) {
                    calendar.setTime(date);
                }
                tv_remind_time.setText(df.format(date));
            }
        }, hour, minute, true).show();
    }

    private void save() {
        String trim = mEditText.getText().toString().trim();
        String achieveTime = tv_remind_time.getText().toString().trim();
        if (mCheckBox.isChecked()) {
            remind = "1";
        } else {
            remind = "0";
        }
        if (trim.isEmpty() || "0".equals(trim)) {
            spUtils.put(Constant.SP_SETP_KEY, "8000");
        } else {
            spUtils.put(Constant.SP_SETP_KEY, trim);
        }
        spUtils.put(Constant.COLOR_ARC_WIDE, remind);
        if (achieveTime.isEmpty()) {
            spUtils.put(Constant.SP_SETP_TIME, "21:00");
        } else {
            spUtils.put("achieveTime", achieveTime);
        }
        finish();
    }
}
