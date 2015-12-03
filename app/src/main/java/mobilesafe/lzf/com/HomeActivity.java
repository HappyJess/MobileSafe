package mobilesafe.lzf.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

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

    /**
     * 设置密码对话框
     */
    private void showSetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //自定义一个布局文件，引入布局文件
        View view = View.inflate(HomeActivity.this,R.layout.dialog_set_pwd,null);
        builder.setView(view);
        builder.show();
    }

    /**
     * 输入密码对话框
     */
    private void showEnterPwdDialog() {

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
