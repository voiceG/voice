package com.qq.activity;
import java.io.File;


import com.qq.R;
import com.qq.fragment.DynamicFragment;
import com.qq.wifi.foregin.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ImageButton;
public class HornorActivity extends Activity{
	private Context mContext;
	private Button back;
	private TextView myHornor;
	private Activity mActivity;
	public HornorActivity() {};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hornor);
		mActivity = HornorActivity.this;
		// Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        
        back = (Button) findViewById(R.id.back);
        myHornor=(TextView)findViewById(R.id.hornor);
        mContext=mActivity.getApplicationContext();
        
        
        back.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				HornorActivity.this.finish();
			}
		});
	}
}
