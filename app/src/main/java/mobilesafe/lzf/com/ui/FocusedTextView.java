package mobilesafe.lzf.com.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义一个TextVIew，一出生就有焦点
 * Created by SnowMan on 2015/12/3.
 */
public class FocusedTextView extends TextView {

    public FocusedTextView(Context context) {
        super(context);
    }

    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当前没有焦点，我只是欺骗了Android系统
     * @return 是不是有焦点
     */
    public boolean isFocused(){
        return true;
    }
}
