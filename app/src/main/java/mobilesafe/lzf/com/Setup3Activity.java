package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Setup3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup3);
    }

    /**
     * 上一个页面
     * @param view
     */
    public void pre(View view){
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
    }
    /**
     *  下一个界面
     * @param view
     */
    public void next(View view){
        Intent intent = new Intent(this,Setup4Activity.class);
        startActivity(intent);
        finish();
    }
}
