package com.hdyg.zhimaqb.view.service;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hdyg.zhimaqb.R;

public class XycardActivity extends Activity {


    ImageView xy_card1;
    ImageView  xy_card2;
    ImageView  xy_card3;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xycard);
        xy_card1= (ImageView) findViewById(R.id.xy_card1);
        xy_card2= (ImageView) findViewById(R.id.xy_card2);
        xy_card3= (ImageView) findViewById(R.id.xy_card3);
        bundle=getIntent().getExtras();
        String  ye= bundle.getString("ye");
        if (ye.equals("0")){
            xy_card1.setVisibility(View.VISIBLE);
        }else if(ye.equals("1")){
            xy_card2.setVisibility(View.VISIBLE);
        }else if(ye.equals("2")){
            xy_card3.setVisibility(View.VISIBLE);
        }
    }
}
