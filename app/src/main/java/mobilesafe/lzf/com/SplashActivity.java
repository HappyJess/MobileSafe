package mobilesafe.lzf.com;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mobilesafe.lzf.com.utils.StreamTools;

public class SplashActivity extends Activity {
    private static final int ENTER_HOME = 0;
    private static final int SHOW_DIALOG = 1;
    private static final int NETWORK_REEOR = 2;
    private static final int JSON_REEOR = 3;
    private static final int URL_REEOR = 4;

    private String TAG = "SplashActivity";

    private TextView tv_splash_version;
    private String description;
    private String apkurl;
    private TextView tv_update_info;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        //初始化应用程序
        initialize();
        copyDB("antivirus");
        copyDB("address");
        //copyDB("raw/antivirus.db");
        //渐变动画
        AlphaAnimation aa = new AlphaAnimation(0.2f, 1.0f);
        aa.setDuration(1000);
        findViewById(R.id.tv_splash_version).setAnimation(aa);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_DIALOG://显示升级对话框
                    Log.v(TAG, "升级吧");
                    showUpdateDialog();
                    break;
                case ENTER_HOME://进入主页面
                    enterHome();
                    break;
                case URL_REEOR://
                    Toast.makeText(getApplicationContext(), "URL错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case NETWORK_REEOR://
                    Toast.makeText(getApplicationContext(), "网络错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case JSON_REEOR://
                    Toast.makeText(SplashActivity.this, "JSON错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
            }
        }
    };

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒升级");
        //builder.setCancelable(false);//强制升级
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                dialog.dismiss();
            }
        });
        builder.setMessage(description);
        builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //下载APP，并且替换安装
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //scard存在,使用afinal框架
                    FinalHttp finalhttp = new FinalHttp();
                    finalhttp.download(apkurl, Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/mobilesafeupdate.apk", new AjaxCallBack<File>() {
                        @Override
                        public void onSuccess(File file) {
                            super.onSuccess(file);
                            installApk(file);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            super.onFailure(t, errorNo, strMsg);
                            t.printStackTrace();
                            Toast.makeText(getApplicationContext(), "下载失败，请稍后重试", Toast.LENGTH_SHORT)
                                    .show();
                        }

                        @Override
                        public void onLoading(long count, long current) {
                            super.onLoading(count, current);
                            tv_update_info.setVisibility(View.VISIBLE);
                            //计算当前下载百分比
                            int progress = (int) (current * 100 / count);
                            tv_update_info.setText("下载进度" + progress + "%");
                        }
                    });
                } else {
                    //不存在
                    Toast.makeText(getApplicationContext(), "Scard卡不存在，请手动升级", Toast
                            .LENGTH_SHORT).show();
                    return;
                }
            }
        });
        builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterHome();//进入主界面
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * //path 把address.db这个数据库拷贝到data/data/《包名》/files/address.db
     */
    private void copyDB(String dbName) {
        //只要你拷贝了一次，我就不要你再拷贝了
        try {
            File file = new File(getFilesDir(), dbName + ".db");
            if (file.exists() && file.length() > 0) {
                //正常了，就不需要拷贝了
                Log.i(TAG, "正常了，就不需要拷贝了");
            } else {
                Log.i(TAG, "开始复制数据库");

                file.createNewFile();
                InputStream in;
                if (dbName.equals("adress")) {
                    in = this.getResources().openRawResource(R.raw.address);
                }else{
                    in = this.getResources().openRawResource(R.raw.antivirus);
                }

                int size = in.available();
                byte buf[] = new byte[size];
                in.read(buf);
                in.close();
                FileOutputStream out = new FileOutputStream(file);
                out.write(buf);
                out.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 安装apk
     */
    private void installApk(File t) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void enterHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    /**
     * 检查是否有新版本，有的话就升级
     */
    private void checkupdate() {
        new Thread() {
            @Override
            public void run() {
                //URL：http://www.iiii.name/apkupdate/update.json
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL(getString(R.string.serverurl));
                    //联网
                    try {
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(4000);
                        int code = conn.getResponseCode();
                        if (code == 200) {
                            //联网成功,得到输入流
                            InputStream is = conn.getInputStream();
                            //把流转换成字符串
                            String rs = StreamTools.readFromStream(is);
                            Log.i(TAG, "联网成功了" + rs);
                            //json解析
                            try {
                                JSONObject obj = new JSONObject(rs);
                                //得到服务器版本信息
                                String version = obj.get("version").toString();
                                //得到描述信息
                                description = obj.get("description").toString();
                                //得到更新的url
                                apkurl = obj.get("apkurl").toString();
                                //校验是否有新版本
                                if (getVersionName().equals(version)) {
                                    //版本一直，没有新版本，进入主页面
                                    msg.what = ENTER_HOME;
                                } else {
                                    //有新版本，弹出对话框提醒升级
                                    msg.what = SHOW_DIALOG;
                                }
                            } catch (JSONException e) {
                                msg.what = JSON_REEOR;
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        msg.what = NETWORK_REEOR;
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    msg.what = URL_REEOR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long dTime = endTime - startTime;//计算联网需要的时间
                    if (dTime < 2000) {
                        try {
                            Thread.sleep(2000 - dTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    private void initialize() {
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        tv_update_info = (TextView) findViewById(R.id.tv_update_info);
        tv_splash_version.setText("版本：" + getVersionName());
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean update = sp.getBoolean("update", true);
        if (update) {
            //检查升级
            checkupdate();
        } else {
            //检测升级关闭，直接进入主页面
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //进入主页面
                    enterHome();
                }
            }, 2000);
        }
    }

    private String getVersionName() {
        //初始化包管理器
        PackageManager pm = getPackageManager();
        try {
            //获取包管理信息
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
