package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

import mobilesafe.lzf.com.ui.SettingItemView;

public class SettingActivity extends Activity implements View.OnClickListener {

    private SettingItemView id_siv_update;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initViews();
        initEvents();
    }

    private void initViews() {
        id_siv_update = (SettingItemView) findViewById(R.id.id_siv_update);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        boolean update = sp.getBoolean("update",true);
        if (update){
            //检测更新已经开启
            id_siv_update.setChecked(true);
//            id_siv_update.setDesc("自动检测最新版本状态：开启");

        }else{
            //检测更新已经关闭
            id_siv_update.setChecked(false);
//            id_siv_update.setDesc("自动检测最新版本状态：关闭");

        }
    }

    private void initEvents() {
        id_siv_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor = sp.edit();
        //判断是否有选中
        if (id_siv_update.isChecked()){
            //已经打开自动升级了
            id_siv_update.setChecked(false);
//            id_siv_update.setDesc("自动检测最新版本状态：关闭");
            editor.putBoolean("update",false);
        }else {
            //没有打开自动升级
            id_siv_update.setChecked(true);
//            id_siv_update.setDesc("自动检测最新版本状态：开启");
            editor.putBoolean("update",true);
        }
        editor.commit();
    }
}
