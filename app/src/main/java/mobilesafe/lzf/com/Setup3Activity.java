package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class Setup3Activity extends Activity {
    private EditText et_savephonenum;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup3);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        et_savephonenum = (EditText) findViewById(R.id.et_savephonenum);
        et_savephonenum.setText(sp.getString("safenumber", "").toString());
        //Log.v("llll",sp.getString("safenumber", ""));
    }

    /**
     * 上一个页面
     *
     * @param view
     */
    public void pre(View view) {
        Intent intent = new Intent(this, Setup2Activity.class);
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
        //保存安全号码
        String phone = et_savephonenum.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "安全号码没有设置", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenumber", phone);
        editor.commit();

        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();

        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }

    public void selectContact(View view) {
        Intent intent = new Intent(this, SelectContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String phone = data.getStringExtra("phone").replace("-", "");
        et_savephonenum.setText(phone);
    }
}
