package com.hdyg.zhimaqb.view;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hdyg.zhimaqb.R;

public class GuideFirstActivity extends Activity {


    ViewPager viewpager;
    int[] pic={R.drawable.baner01,R.drawable.baner02,R.drawable.baner03};
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_first);
        initdata();
    }
    public void initdata(){
        ll= (LinearLayout) findViewById(R.id.ll);
        final ImageView[] ivs = new ImageView[pic.length];
        ivs[0] = new ImageView(this);
        ivs[0].setImageResource(pic[0]);
        ivs[1] = new ImageView(this);
        ivs[1].setImageResource(pic[1]);
        ivs[2] = new ImageView(this);
        ivs[2].setImageResource(pic[2]);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter adapter=new PagerAdapter() {
            @Override
            public int getCount() {
                return pic.length;
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView iv = ivs[position];
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(iv);
                return iv;
            }
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewpager.setAdapter(adapter);
    }
}
