package com.kun.keep.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * user:kun
 * Date:2018/4/23 or 2:48 PM
 * email:hekun@gamil.com
 * Desc: 封装弹出
 */
public class ToastUtils {
    public static void showToast(String str, Context mContext){
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
    }
}
