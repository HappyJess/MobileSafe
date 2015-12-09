package mobilesafe.lzf.com;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import mobilesafe.lzf.com.utils.SmsUtils;
import mobilesafe.lzf.com.utils.UIUtils;

public class AtoolsActivity extends Activity {
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_atools);
    }

    /**
     * 进入号码归属地查询
     * @param view
     */
    public void numberQuery(View view){
        Intent intent = new Intent(this,NumberAddressQueryActivity.class);
        startActivity(intent);
    }


    /**
     * 短信的备份
     * @param view
     */
    public void smsBackup(View view){
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setTitle("提示");
        pd.setMessage("稍安勿躁，正在备份...");
        pd.show();
        new Thread(){
            public void run() {
                try {
                    boolean result = SmsUtils.backUpSms(getApplicationContext(), new SmsUtils.BackupStatusCallback() {
                        @Override
                        public void onSmsBackup(int process) {
                            pd.setProgress(process);
                        }

                        @Override
                        public void beforeSmsBackup(int size) {
                            pd.setMax(size);
                        }
                    });
                    if(result){
                        UIUtils.showToast(AtoolsActivity.this, "备份成功");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    UIUtils.showToast(AtoolsActivity.this, "文件生成失败");
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    UIUtils.showToast(AtoolsActivity.this, "sd卡不可用，或者存储空间不足");
                } catch (IOException e) {
                    e.printStackTrace();
                    UIUtils.showToast(AtoolsActivity.this, "读写错误");
                }finally{
                    pd.dismiss();
                }
            };
        }.start();
    }
    /**
     * 短信的还原
     * @param view
     */
    public void smsRestore(View view){
        Toast.makeText(AtoolsActivity.this,"功能还在开发中~",Toast.LENGTH_SHORT).show();
    }
    /**
     * 打开程序锁
     * @param view
     */
    public void openAppLock(View view){

        /*Intent intent = new Intent(this,AppLockActivity.class);
        startActivity(intent);*/
        Toast.makeText(AtoolsActivity.this,"功能还在开发中~",Toast.LENGTH_SHORT).show();
    }

}
