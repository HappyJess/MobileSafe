package mobilesafe.lzf.com.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobilesafe.lzf.com.R;

/**
 * 自定义设置界面的主控件，包括两个Textview，一个CheckBox和一个View分割线
 * Created by SnowMan on 2015/12/3.
 */
public class SettingItemView extends RelativeLayout {
    private CheckBox cb_status;
    private TextView tv_update_now;
    private TextView tv_update_title;
    private String desc_on;
    private String desc_off;

    /**
     * 初始化布局文件
     * 把一个布局文件加载进SettingItemView
     *
     * @param context
     */
    private void initView(Context context) {
        View.inflate(context, R.layout.setting_item_view, this);
        cb_status = (CheckBox) this.findViewById(R.id.cb_status);
        tv_update_now = (TextView) this.findViewById(R.id.tv_update_now);
        tv_update_title = (TextView) this.findViewById(R.id.tv_update_title);
    }

    public SettingItemView(Context context) {
        super(context);
        initView(context);
    }

    /**
     * 带有两个参数的构造方法，布局文件使用的时候调用
     *
     * @param context
     * @param attrs
     */
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        String titlename = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto",
                "titlename");
        desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc_on");
        desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "desc_off");
        tv_update_title.setText(titlename);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 校验组合控件是否有焦点
     */
    public boolean isChecked() {
        return cb_status.isChecked();
    }

    /**
     * 设置组合控件的状态
     */
    public void setChecked(boolean checked) {
        if (checked) {
            setDesc(desc_on);
        } else {
            setDesc(desc_off);
        }
        cb_status.setChecked(checked);
    }

    /**
     * 设置组合控件的描述信息
     */
    public void setDesc(String text) {
        tv_update_now.setText(text);
    }
}
