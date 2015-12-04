package mobilesafe.lzf.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mobilesafe.lzf.com.utils.MD5Tools;

public class HomeActivity extends Activity {

    private static final String TAG = "HomeActivity";
    private GridView gv_list_home;
    private Myadapter adapter;
    private SharedPreferences sp;
    private static String[] NAMES = {
            "手机防盗", "通讯卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒",
            "缓存清理", "高级工具", "设置中心"
    };
    private static int[] IMGS = {
            R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
            R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan,
            R.drawable.sysoptimize, R.drawable.atools, R.drawable.settings
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
        initEvents();

    }

    private void initEvents() {
        adapter = new Myadapter();
        gv_list_home.setAdapter(adapter);
        gv_list_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: //进入手机防盗页面
                        showLostFindDialog();
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8://进入设置中心
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showLostFindDialog() {
        //判断是否设置过密码
        if (isSetPwd()) {
            //已经设置过密码了，弹出输入密码对话框
            showEnterPwdDialog();
        } else {
            //没有设置密码，弹出设置密码对话框
            showSetPwdDialog();
        }
    }

    private EditText et_set_pwd;
    private EditText et_reset_pwd;
    private Button submit_set_pwd;
    private Button cancle_set_pwd;
    private AlertDialog dialog;

    /**
     * 设置密码对话框
     */
    private void showSetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //自定义一个布局文件，引入布局文件
        View view = View.inflate(HomeActivity.this, R.layout.dialog_set_pwd, null);
        et_set_pwd = (EditText) view.findViewById(R.id.et_set_pwd);
        et_reset_pwd = (EditText) view.findViewById(R.id.et_reset_pwd);
        submit_set_pwd = (Button) view.findViewById(R.id.submit_set_pwd);
        cancle_set_pwd = (Button) view.findViewById(R.id.cancle_set_pwd);
        cancle_set_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消对话框
                dialog.dismiss();
            }
        });
        submit_set_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取出密码并且判断
                String password = et_set_pwd.getText().toString().trim();
                String repassword = et_reset_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
                    Toast.makeText(HomeActivity.this, "密码为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断是否一致才去保存
                if (password.equals(repassword)) {
                    //一致的话，保存密码，把对话框消掉，还要进入手机防盗页面
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Tools.getMd5Str(password));
                    editor.commit();
                    dialog.dismiss();
                    Log.i(TAG, "进入手机防盗页面");
                    Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        builder.setView(view);
        dialog = builder.show();
    }

    /**
     * 输入密码对话框
     */
    private void showEnterPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //自定义一个布局文件，引入布局文件
        View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_pwd, null);
        et_set_pwd = (EditText) view.findViewById(R.id.et_set_pwd);
        submit_set_pwd = (Button) view.findViewById(R.id.submit_set_pwd);
        cancle_set_pwd = (Button) view.findViewById(R.id.cancle_set_pwd);
        cancle_set_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消对话框
                dialog.dismiss();
            }
        });
        submit_set_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取出密码并且判断
                String password = et_set_pwd.getText().toString().trim();
                String savepassword = sp.getString("password","");
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(HomeActivity.this, "输入的密码为空，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (MD5Tools.getMd5Str(password).equals(savepassword)){
                    //输入的密码和以前设置的相同，把对话框消掉，进入主页面
                    dialog.dismiss();
                    Log.i(TAG,"输入的密码和以前设置的相同，把对话框消掉，进入手机页面");
                    Intent intent = new Intent(HomeActivity.this,LostFindActivity.class);
                    startActivity(intent);
                }else{
                    //不相同
                    Toast.makeText(HomeActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                    et_set_pwd.setText("");
                    return;
                }
            }
        });
        builder.setView(view);
        dialog = builder.show();
    }

    private boolean isSetPwd() {
        String password = sp.getString("password", null);
        /*if (TextUtils.isEmpty(password)){
            return false;
        }else {
            return true;
        }*/
        return !TextUtils.isEmpty(password);
    }

    private void initialize() {

        gv_list_home = (GridView) findViewById(R.id.gv_list_home);
        sp = getSharedPreferences("config", MODE_PRIVATE);
    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return NAMES.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
            ImageView iv_item = (ImageView) view.findViewById(R.id.iv_item);
            TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
            iv_item.setImageResource(IMGS[position]);
            tv_item.setText(NAMES[position]);
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
