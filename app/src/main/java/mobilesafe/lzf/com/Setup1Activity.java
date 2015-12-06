package mobilesafe.lzf.com;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class Setup1Activity extends Activity {
    //手势识别器
    private GestureDetector detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setup1);
        initView();
    }

    private void initView() {
        //实例化手势识别器
        detector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            //重写滑动的方法
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getRawX() - e2.getRawX() > 100){
                    //从右往左划
                    return true;
                }
                if (e2.getRawX() - e1.getRawX() > 100){
                    //从左往右滑
                    showNext();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public void next(View view){
        showNext();
    }

    private void showNext() {
        Intent intent = new Intent(this,Setup2Activity.class);
        startActivity(intent);
        finish();
        //偏移动画，必须在start或者finish之后使用
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }
    //3使用手势识别器

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
