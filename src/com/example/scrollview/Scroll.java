package com.example.scrollview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Scroll extends RelativeLayout {
	
	private int period = 2000;               // 定时器 

	Context context;

	static List<String[]> mdata = new ArrayList<String[]>();

	LinearLayout ly1;
	LinearLayout ly2;

	TextView textView1;
	TextView textView2;
	
	private int flag = 0;                   // [0][初次开启定时，显示静态布局1]   [1][布局1] [2][布局2]
	AnimationSet animationSet1;             // 滑出动画
	AnimationSet animationSet2;             // 滑入动画
	
	private Timer timer = null;
	private TimerTask task;
	
	public Scroll(Context context) {
		this(context, null); 
	}

	public Scroll(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.scroll, this);

		ly1 = (LinearLayout) findViewById(R.id.ly1);
		ly2 = (LinearLayout) findViewById(R.id.ly2);

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		
		init();
	}
	
	public void setVisibility(boolean Visibility)
	{
		if(Visibility)
		{
			this.setVisibility(View.VISIBLE);
		}
		else
		{
			this.setVisibility(View.GONE);
		}
	}
	
	public void setPeriod(int period)
	{
		this.period = period;
	}
	
	private void init()
	{
		animationSet1 = new AnimationSet(true);
		animationSet2 = new AnimationSet(true);
		
		TranslateAnimation ta1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,-1f, Animation.RELATIVE_TO_SELF, 0f,Animation.RELATIVE_TO_SELF, 0f);
		ta1.setDuration(1000);
		animationSet1.addAnimation(ta1);
		animationSet1.setFillAfter(true);

		TranslateAnimation ta2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,0f, Animation.RELATIVE_TO_SELF, 0f);
		ta2.setDuration(1000);
		animationSet2.addAnimation(ta2);
		animationSet2.setFillAfter(true);
		
	}
	
	public void add(List<String[]> data) {
		
		if(null == data)
		{
			return;
		}
		
		if(mdata.size() < 1)
		{
			for(int i = 0 ; i < data.size() ; i++)
			{
				mdata.add(data.get(i));
			}
			task = new TimerTask() {
				@Override
				public void run() {
					
					if (mdata.size() < 1) {
						Message message = new Message();
						message.what = 3;
						handler.sendMessage(message);
						timer.cancel();
						return;
					}

					String img = mdata.get(0)[0];
					String text = mdata.get(0)[1];

					if (flag == 0) {
						Message message = new Message();
						message.what = 0;
						Bundle b = new Bundle();
						b.putString("data", text);
						message.setData(b);
						handler.sendMessage(message);
						flag = 1;
					} else if (flag == 1) {
						Message message = new Message();
						message.what = 1;
						Bundle b = new Bundle();
						b.putString("data", text);
						message.setData(b);
						handler.sendMessage(message);
						flag = 2;
					} else {
						Message message = new Message();
						message.what = 2;
						Bundle b = new Bundle();
						b.putString("data", text);
						message.setData(b);
						handler.sendMessage(message);
						flag = 1;
					}
					mdata.remove(0);
				}
			};
			timer = new Timer();
			setVisibility(true);
			timer.schedule(task, 0, period);
		}
		else
		{
			for(int i = 0 ; i < data.size() ; i++)
			{
				mdata.add(data.get(i));
			}
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			Bundle b = msg.getData();  
            String data = b.getString("data");
            
			switch(msg.what)
			{
			case 0:
				ly1.setVisibility(0);
				ly2.setVisibility(4);
				textView1.setText(data);
				break;
			case 1:
				ly1.setVisibility(0);
				ly2.setVisibility(4);
				textView2.setText(data);
				ly1.startAnimation(animationSet1);
				ly2.startAnimation(animationSet2);
				break;
			case 2:
				ly1.setVisibility(4);
				ly2.setVisibility(0);
				textView1.setText(data);
				ly2.startAnimation(animationSet1);
				ly1.startAnimation(animationSet2);
				break;
			case 3:
				setVisibility(false);
				break;
			}
		}
	};
}
