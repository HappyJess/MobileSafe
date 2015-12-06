package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import mobilesafe.lzf.com.ui.SettingItemView;

public class Setup2Activity extends Activity {
    private SettingItemView siv_setup2_bdsim;
    //读取手机的sim卡的信息
    private TelephonyManager tm;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup2);
        siv_setup2_bdsim = (SettingItemView) findViewById(R.id.siv_setup2_bdsim);
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        String sim = sp.getString("sim", null);
        if (TextUtils.isEmpty(sim)) {
            //没有绑定
            siv_setup2_bdsim.setChecked(false);
        } else {
            siv_setup2_bdsim.setChecked(true);
        }
        siv_setup2_bdsim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sp.edit();
                if (siv_setup2_bdsim.isChecked()) {
                    siv_setup2_bdsim.setChecked(false);
                    //保存sim卡序列号
                    editor.putString("sim", null);
                } else {
                    siv_setup2_bdsim.setChecked(true);
                    //保存sim卡序列号
                    String sim = tm.getSimSerialNumber();
                    editor.putString("sim", sim);
                }
                editor.commit();
            }
        });
    }

    /**
     * 上一个页面
     *
     * @param view
     */
    public void pre(View view) {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();

        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }

    /**
     * 下一个界面
     *
     * @param view
     */
    public void next(View view) {
        //取出是否绑定sim卡
        String sim = sp.getString("sim", null);
        if (TextUtils.isEmpty(sim)) {
            //sim卡没有绑定
            Toast.makeText(this, "sim卡没有绑定", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();

        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
}
