package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class LostFindActivity extends Activity {

    private SharedPreferences sp;
    private TextView tv_safenumber;
    private ImageView iv_protecting;
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
            tv_safenumber = (TextView) findViewById(R.id.tv_safenumber);
            iv_protecting = (ImageView) findViewById(R.id.iv_protecting);
            //得到我们设置的安全号码
            String safenumber = sp.getString("safenumber", "");
            tv_safenumber.setText(safenumber);
            //设置防盗保护的状态
            boolean protecting = sp.getBoolean("protecting", false);
            if(protecting){
                //已经开启防盗保护
                iv_protecting.setImageResource(R.drawable.lock);
            }else{
                //没有开启防盗保护
                iv_protecting.setImageResource(R.drawable.unlock);
            }
        }else {
            //跳转到设置向导页面
            Intent intent = new Intent(this,Setup1Activity.class);
            startActivity(intent);
            //关闭当前页面
            finish();
        }
    }

    /**
     * 重新进入设置向导页面
     * @param view
     */
    public void reEnterSetup(View view){
        //跳转到设置向导页面
        Intent intent = new Intent(this,Setup1Activity.class);
        startActivity(intent);
        //关闭当前页面
        finish();
    }
}
