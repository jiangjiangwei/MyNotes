package com.jiangw.interfaces;

import android.widget.EditText;
import android.widget.RelativeLayout;

public interface onPopClickCallBack
{
	/** pop被点击时所应该显示的selected图片 */
	void setOnPopClickListener(RelativeLayout tvTitle, EditText etContent);
}
