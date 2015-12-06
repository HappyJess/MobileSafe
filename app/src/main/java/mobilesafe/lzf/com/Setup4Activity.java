package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Setup4Activity extends Activity {
    private SharedPreferences sp ;
    private CheckBox cb_proteting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup4);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        cb_proteting = (CheckBox) findViewById(R.id.cb_proteting);

        boolean  protecting = sp.getBoolean("protecting", false);
        if(protecting){
            //手机防盗已经开启了
            cb_proteting.setText("手机防盗已经开启");
            cb_proteting.setChecked(true);
        }else{
            //手机防盗没有开启
            cb_proteting.setText("手机防盗没有开启");
            cb_proteting.setChecked(false);

        }

        cb_proteting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb_proteting.setText("手机防盗已经开启");
                }else{
                    cb_proteting.setText("手机防盗没有开启");
                }

                //保存选择的状态
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting", isChecked);
                editor.commit();
            }
        });
    }

    /**
     * 上一个页面
     * @param view
     */
    public void pre(View view){
        Intent intent = new Intent(this,Setup3Activity.class);
        startActivity(intent);
        finish();

        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_pre_in,R.anim.tran_pre_out);
    }
    /**
     *  下一个界面
     * @param view
     */
    public void next(View view){
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("configed",true);
        editor.commit();
        Intent intent = new Intent(this,LostFindActivity.class);
        startActivity(intent);
        finish();

        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }
}
