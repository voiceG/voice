package com.voice.study;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



import com.qq.R;
import com.voice.model.Word;
import com.voice.business.OperationOfBooks;
import com.voice.business.TTS;
import com.voice.database.DataAccess;
import com.voice.model.WordList;



import com.qq.fragment.DynamicFragment;
import com.qq.wifi.foregin.Globals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ImageButton;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class StudyActivity extends Activity implements OnClickListener{
	private Context mContext;
	private TextView grade;
	private TextView mean;//info
	private ImageView hand;
	private ImageView word;
	private ImageView mouth;
	private VideoView example;
	private VideoView you;
	private ImageButton last;//beforeone
	private ImageButton next;
	private Activity mActivity;
	private String listnum;
	private String imagename;
	private int currentnum;
	private int numoflist;
	private ArrayList<Word> list = new ArrayList<Word>();
    private Button add;
    private Button nextone;
    private Button beforeone;
    private Button camera;//lingxue
    private String strVideoPath = "";// 视频文件的绝对路径
    private TextView spelling;
    private TextView info;
	ViewGroup viewGroup ;
	public StudyActivity() {}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.study);
		currentnum=0;
		Intent intent=getIntent();
		Bundle b = intent.getExtras();
		String name = b.getString("list");
		listnum=name;
		this.setTitle("学习LIST-"+name);
		DataAccess data = new DataAccess(this);
		list=data.QueryWord("LIST = '"+name+"'", null);
		numoflist = list.size();
		initWidgets();
		UpdateView();
		
		mActivity = StudyActivity.this;
		// Set the application-wide context global, if not already set
        Context myContext = Globals.getContext();
        if (myContext == null) {
            myContext = mActivity.getApplicationContext();
            if (myContext == null) {
                throw new NullPointerException("Null context!?!?!?");
            }
            Globals.setContext(myContext);
        }

        grade = (TextView) findViewById(R.id.yourpoint);
        hand = (ImageView) findViewById(R.id.examplehand);
        
        mouth= (ImageView) findViewById(R.id.examplemouth);
        example = (VideoView) findViewById(R.id.examplevideo);
        you = (VideoView) findViewById(R.id.yourvideo);
        mContext=mActivity.getApplicationContext();
        
	}
	private void initWidgets() {
		// TODO Auto-generated method stub
		this.mean=(TextView) this.findViewById(R.id.mean);
		this.word = (ImageView) this.findViewById(R.id.word);
		this.last=(ImageButton) this.findViewById(R.id.gotolast);
		last.setOnClickListener(this);
		this.next=(ImageButton) this.findViewById(R.id.gotonext);
		next.setOnClickListener(this);
		this.spelling=(TextView) this.findViewById(R.id.spelling);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/SEGOEUI.TTF");
		mean.setTypeface(tf);
		DisplayMetrics dm = new DisplayMetrics(); 
		   dm = getApplicationContext().getResources().getDisplayMetrics(); 
		   int screenWidth = dm.widthPixels; 
		   //add.setWidth(screenWidth/3);
		   last.setMaxWidth(screenWidth/3);
		   next.setMaxWidth(screenWidth/3);
		   camera=(Button) this.findViewById(R.id.camera);
		   camera.setOnClickListener(this);
	
	}
	  public void onClick(View v) {
			 Log.i("3", "3");
			 this.UpdateView();
		    Log.i("3", "3");
			if (v==next){
				if(currentnum<numoflist){
				    currentnum++;
				    this.UpdateView();
				}
				
			}
			else if(v==last){
				currentnum--;
				this.UpdateView();
			}else if(v==camera){
				 videoMethod();
			}
			Log.i("3", "3");
		}
	  public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			 if (keyCode == KeyEvent.KEYCODE_BACK) {
				 Dialog dialog = new AlertDialog.Builder(this)
		            .setIcon(R.drawable.dialog_icon)
		            .setTitle("学习未完成")
		            .setMessage("你确定现在结束学习吗？这将导致本次学习无效！")
		            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                    /* User clicked OK so do some stuff */
		                	finish();
		                	Intent intent = new Intent();
		            		intent.setClass(StudyActivity.this, StudyChoice.class);
		            		startActivity(intent);
		                }
		            })
		            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                    /* User clicked OK so do some stuff */
		                }
		            }).create();
					dialog.show();
			 }
				 
			return true;
		}
	  private void UpdateView() {
			if(currentnum==0){
				last.setEnabled(false);
			}
			else if(currentnum>0){
				last.setEnabled(true);
			}
			SharedPreferences setting = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
			if(setting.getBoolean("iftts", false)){
				Thread thread =new Thread(new Runnable(){
		              public void run(){
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
					thread.start();
			}
			
			
			// TODO Auto-generated method stub
			if (currentnum<numoflist){
				mean.setText(list.get(currentnum).getID()+"."+list.get(currentnum).getSpelling());
				imagename=list.get(currentnum).getPhonetic_alphabet();
				spelling.setText(list.get(currentnum).getMeanning());
				downloadFile();
		        }
			else if(currentnum>=numoflist){
					currentnum--;
					Dialog dialog = new AlertDialog.Builder(this)
		            .setIcon(R.drawable.dialog_icon)
		            .setTitle("学习已完成")
		            .setMessage("您可以依照复习计划进行本单元的复习")
		            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int whichButton) {
		                    /* User clicked OK so do some stuff */
		                	finish();
		                	Intent intent = new Intent();
		            		intent.setClass(StudyActivity.this, StudyChoice.class);
		            		startActivity(intent);
		            		
		                }
		            }).create();
					dialog.show();
				}
			

		}
	  private void downloadFile(){
	    	 //word.setVisibility(View.VISIBLE);
				String imgUrl = "http://115.159.215.125/voicepicture/"+imagename+".jpg";
				new DownImgAsyncTask().execute(imgUrl);
	    }
	     class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap>{


			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				word.setImageBitmap(null);
				showProgressBar();//显示进度条提示框

			}

			@Override
			protected Bitmap doInBackground(String... params) {
				// TODO Auto-generated method stub
				Bitmap b = getImageBitmap(params[0]);
				return b;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(result!=null){
					word.setImageBitmap(result);
				}
			}



		}
	    
	    private Bitmap getImageBitmap(String url){
			URL imgUrl = null;
			Bitmap bitmap = null;
			try {
				imgUrl = new URL(url);
				HttpURLConnection conn = (HttpURLConnection)imgUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(is);
				is.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}
			return bitmap;
		}
	    private void showProgressBar(){
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_IN_PARENT,  RelativeLayout.TRUE);
			Context context = getApplicationContext();
			viewGroup = (ViewGroup)findViewById(R.id.parent_view);	
		}
	  @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
		}
	  
	  
	     private void videoMethod() {
             Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
             intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
             intent.putExtra("camerasensortype", 2); // 调用前置摄像头  
             intent.putExtra("autofocus", true); // 自动对焦  
             intent.putExtra("fullScreen", false); // 全屏  
             intent.putExtra("showActionIcons", false); 

             startActivityForResult(intent, 0);
              
             

    }
	     @Override
         protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                 super.onActivityResult(requestCode, resultCode, data);
               
                         if (resultCode == RESULT_OK) {
                                 Uri uriVideo = data.getData();
                                 Cursor cursor=this.getContentResolver().query(uriVideo, null, null, null, null);
                                 if (cursor.moveToNext()) {
                                         /* _data：文件的绝对路径 ，_display_name：文件名 */
                                         strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                                         Toast.makeText(this, strVideoPath, Toast.LENGTH_SHORT).show();
                                 }
                         }            
         }
}
