package com.qq.fragment;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.qq.R;
import com.qq.activity.DifficultyActivity;
import com.qq.activity.HornorActivity;
import com.qq.activity.TestActivity;
import com.qq.view.TitleBarView;
import com.voice.study.StudyActivity;
import com.voice.study.StudyChoice;
import com.voice.study.StudyMain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DynamicFragment extends Fragment {

	private Context mContext;
	private View mBaseView;
	private TitleBarView mTitleBarView;
	private RelativeLayout study;
	private RelativeLayout difficulty;
	private RelativeLayout test;
	private RelativeLayout hornor;
	
	//折线图
	  private XYSeries series;                     
	    private XYMultipleSeriesDataset mDataset;  //
	    private GraphicalView chart;
	    private XYMultipleSeriesRenderer renderer;
	    private Context context;
	    private int addX = -1, addY;
	    //只记录当前界面显示的图的点 
	    int[] xx = new int[50];
		int[] yy = new int[50];
	//折线图
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext=getActivity();
		mBaseView=inflater.inflate(R.layout.fragment_dynamic, null);
		findView();
		initTitleView();
		init();
		return mBaseView;
	}
	
	private void findView(){
		mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
		study=(RelativeLayout) mBaseView.findViewById(R.id.ll_dynamic);
		difficulty=(RelativeLayout) mBaseView.findViewById(R.id.difficulty);
		test=(RelativeLayout) mBaseView.findViewById(R.id.test);
		hornor=(RelativeLayout) mBaseView.findViewById(R.id.hornor);
	}
	
	private void initTitleView(){
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
		mTitleBarView.setTitleText(R.string.dynamic);
	}
	private void init(){
		//折线图
		 LinearLayout layout = (LinearLayout)mBaseView.findViewById(R.id.linearmian); 
        layout.setBackgroundResource(R.drawable.online_bj);
		 series = new XYSeries("动态折线测试");
	        //创建一个数据集的实例，这个数据集将被用来创建图表
	        mDataset = new XYMultipleSeriesDataset();  
	        //将点集添加到这个数据集中
	        mDataset.addSeries(series);
	        //renderer - 渲染器
	        renderer = buildRenderer(Color.BLUE, PointStyle.CIRCLE );
	        //设置图标样式
	        setChartSettings(renderer, "X", "Y", 0, 100, 0, 90, Color.WHITE, Color.WHITE);
	        //生成图表
			chart = ChartFactory.getLineChartView(mContext, mDataset, renderer);
	        //将图表添加到布局中去
			layout.addView(chart, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	        th.start();
		//折线图
		study.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, StudyMain.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});
		difficulty.setOnClickListener(new OnClickListener() {
			
			@Override
			
			public void onClick(View v) {
				Intent intent=new Intent(mContext, DifficultyActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
				
			}
		});
		test.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, TestActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
		
			}
		});
		hornor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, HornorActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
		
			}
		});
	}
	
	
	private Thread th = new Thread(){
		public void run() {
			try {
				while(true){					
					Thread.sleep(1000);
					handler.sendMessage(new Message());
				}
			} catch (Exception e) {
			}	
		};	
	};
		
	 //这里的Handler实例将配合下面的Timer实例，完成定时更新图表的功能
	private Handler handler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		//刷新图表
    		updateUI();
    	}
    };
    
   
   private void updateUI() {
   	addX = 0;
		addY = (int)(Math.random() * 90);
		//移除数据集中旧的点集
		mDataset.removeSeries(series);
		
		//判断当前点集中到底有多少点
		int length = series.getItemCount();
		
		if(length > 50){
			length = 50;
		}
		//  X Y轴
		for (int i = 0; i < length; i++) {
			//  X坐标每次的增量 + 5
			xx[i] = (int) series.getX(i) + 5;
			yy[i] = (int) series.getY(i);
		}	
		series.clear();
		//新出来的点肯定首先画，加到第一个
		series.add(addX, addY);
		//原来的点按照顺序加入
		for (int k = 0; k < length; k++) {
   		series.add(xx[k], yy[k]);
   	}
		mDataset.addSeries(series);
		//视图更新,如果在非UI主线程中，需要调用postInvalidate()
		chart.invalidate();
   }

   
 /**
  * 下面这里的直接从 AbstractDemoChart 中拷出来就好了。
  * @param colors
  * @param styles
  * @return
  */

   //设置渲染器
   protected XYMultipleSeriesRenderer buildRenderer(int color, PointStyle style){
   	XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
   	//设置图表中折线的样式
   	XYSeriesRenderer r = new XYSeriesRenderer();
   	r.setColor(color);   //线条颜色
   	r.setPointStyle(style); //点样式
   	r.setLineWidth(3); //线宽
   	renderer.addSeriesRenderer(r); //添加
   	return renderer;
   }
   
   // 设置图表的显示
   protected void setChartSettings(XYMultipleSeriesRenderer renderer, String xTitle, String yTitle,
   								double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {

   	renderer.setChartTitle("");
   	renderer.setXTitle(xTitle);
   	renderer.setYTitle(yTitle);
   	renderer.setXAxisMin(xMin);
   	renderer.setXAxisMax(xMax);
   	renderer.setYAxisMin(yMin);
   	renderer.setYAxisMax(yMax);
   	renderer.setAxesColor(axesColor);
   	renderer.setLabelsColor(labelsColor);  
   	renderer.setShowGrid(true);       //是否显示网格
   	renderer.setGridColor(Color.RED); //网格的颜色
   	renderer.setXLabels(20);
   	renderer.setYLabels(10);
   	renderer.setXTitle("");            //设置title
   	renderer.setYTitle("");
   	renderer.setYLabelsAlign(Align.RIGHT); //Y周文字对齐方式
   	renderer.setPointSize((float)2); 
//     renderer.setShowLegend(false);
//   	renderer.setLabelsTextSize(15);
//     renderer.setLegendTextSize(15);
   	renderer.setBackgroundColor(Color.parseColor("#00000000"));
   	renderer.setMarginsColor(Color.argb(0, 0xF3, 0xF3, 0xF3)); //图表与周围四周的颜色
   	renderer.setMargins(new int[] { 20, 30, 15, 20 }); //设置图表的边距
   }
   
   public void onDestroy() {
   	th.interrupt();
   	super.onDestroy();
   }

}
