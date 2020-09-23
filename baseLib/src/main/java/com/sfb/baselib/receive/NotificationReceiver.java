package com.sfb.baselib.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by wuwc on 2020/9/14.
 * Author wuwc.
 * E-mail 1428943514@qq.com
 *
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: 2020/9/14 预留如果程序退出,通知栏被点击带来的体验问题优化
        String action = intent.getAction();
    }
}
