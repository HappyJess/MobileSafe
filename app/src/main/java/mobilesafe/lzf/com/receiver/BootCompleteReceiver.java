package mobilesafe.lzf.com.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by SnowMan on 2015/12/6.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private SharedPreferences sp;
    private TelephonyManager tm;
    @Override
    public void onReceive(Context context, Intent intent) {
        sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //读取之前保存的SIm卡信息
        String savesim = sp.getString("sim","");
        //读取之前保存的sim卡信息
        String realsim = tm.getSimSerialNumber();
        //比较是否一样
        if (savesim.equals(realsim)){
            //sim卡没有变更

        }else {
            //sim卡已经变更，发短信给安全号码
            Toast.makeText(context,"sim卡已经变更",Toast.LENGTH_SHORT).show();
            SmsManager.getDefault().sendTextMessage(sp.getString("safenumber", ""), null, "sim changing....", null, null);

        }
    }
}
