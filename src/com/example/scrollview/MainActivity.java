package com.example.scrollview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Scroll imageBtn2;
  Button button1 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		imageBtn2=(Scroll) this.findViewById(R.id.viewtest);
		
		
		button1 = (Button)findViewById(R.id.button1);
		
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				List<String []> data = new ArrayList<String []>();
				
				for(int i = 0 ; i < 3 ; i++)
				{
					String [] str = {"" , "" + (i + 1)};
					data.add(str);
				}
				imageBtn2.add(data);
			}
		});
		
	}

}
