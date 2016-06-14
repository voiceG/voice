package com.qq.activity;
import java.io.File;


import com.qq.R;
import com.qq.fragment.DynamicFragment;
import com.qq.wifi.foregin.Globals;
import com.voice.study.StudyActivity;

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
public class DifficultyActivity extends Activity{
	private Context mContext;
	public RadioGroup difficulty;
	public RadioButton easy,middle,hard;
	private Button sure;
	private Button cancle;
	private Activity mActivity;
	public DifficultyActivity() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.difficulitychoice);
		mActivity = DifficultyActivity.this;
		// Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        difficulty = (RadioGroup) findViewById(R.id.difficultygroup);
        easy= (RadioButton) findViewById(R.id.easy);        
        middle= (RadioButton) findViewById(R.id.middle); 
        middle= (RadioButton) findViewById(R.id.middle); 
        hard = (RadioButton) findViewById(R.id.hard);
        sure = (Button) findViewById(R.id.difficultysure);
        cancle = (Button) findViewById(R.id.difficultyback);
        mContext=mActivity.getApplicationContext();
        
        sure.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, StudyActivity.class);
				startActivity(intent);
				DifficultyActivity.this.finish();
			}
		});
        cancle.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				DifficultyActivity.this.finish();
			}
		});
	}
}
