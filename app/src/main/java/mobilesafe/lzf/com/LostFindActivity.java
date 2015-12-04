package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class LostFindActivity extends Activity {

    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        //判断一下是否设置过设置向导，如果没有，就跳转到设置页面，有的话就留在当前页面
        boolean configed = sp.getBoolean("configed",false);
        if (configed){
            //手机防盗页面
            setContentView(R.layout.activity_lost_find);
        }else {
            //跳转到设置向导页面
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            //关闭当前页面
            finish();
        }
    }
}
