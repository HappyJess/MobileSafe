package mobilesafe.lzf.com;

import android.app.Activity;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mobilesafe.lzf.com.db.dao.NumberAddressQueryUtils;

public class NumberAddressQueryActivity extends Activity {
    private EditText ed_phone;
    private TextView result;
    private Button subimit_find;
    /**
     * 系统提供的振动服务
     */
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_number_address_query);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        result = (TextView) findViewById(R.id.result);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        subimit_find = (Button) findViewById(R.id.subimit_find);
        ed_phone.addTextChangedListener(new TextWatcher() {

            /**
             * 当文本发生变化的时候回调
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() >= 3) {
                    //查询数据库，并且显示结果
                    String address = NumberAddressQueryUtils.queryNumber(s.toString());
                    result.setText(address);
                }

            }

            /**
             * 当文本发生变化之前回调
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            /**
             * 当文本发生变化之后回调
             */
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 查询号码归属地
     *
     * @param view
     */
    public void numberAddressQuery(View view) {
        String phones = ed_phone.getText().toString();
        Log.i("numberAddressQuery", phones);
        if (TextUtils.isEmpty(phones) || phones == null) {
            Toast.makeText(this, "号码为空", Toast.LENGTH_SHORT).show();
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            ed_phone.startAnimation(shake);

            //当电话号码为空的时候，就去振动手机提醒用户
//			 vibrator.vibrate(2000);
            long[] pattern = {200, 200, 300, 300, 500, 500};
            //-1不重复 0循环振动 1；
            vibrator.vibrate(pattern, -1);

            return;
        } else {
            String address = NumberAddressQueryUtils.queryNumber(phones);
            result.setText(address);
            //去数据库查询号码归属地
            //1.网络查询 ；2.本地的数据库--数据库
            //写一个工具类，去查询数据库
//            Log.i("NumberAddressQueryActivity", "您要查询的电话号码==" + phone);
        }

    }
}
