package com.hdyg.zhimaqb.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.view.service.XycardActivity;

public class XinYongCardActivity extends Activity implements View.OnClickListener {


    TextView top_context;
    LinearLayout top_left_ll, minsheng_card, xingye_card, top_right_ll, pingan_card;
    ImageView ka_layout, ka1_layout, ka2_layout, ka3_layout, ka4_layout, ka5_layout, ka6_layout, ka7_layout, ka8_layout, ka9_layout, ka10_layout, ka11_layout;
    ViewPager vp_banner;
    int[] pic = {R.drawable.car001, R.drawable.car002, R.drawable.car003};
    int count;
    Handler had = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vp_banner.setCurrentItem(count % pic.length);
            count++;
            had.sendEmptyMessageDelayed(123, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xin_yong_card);
        initdata();
        final ImageView[] ivs = new ImageView[pic.length];
        ivs[0] = new ImageView(this);
        ivs[0].setImageResource(pic[0]);
        ivs[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initent = new Intent(XinYongCardActivity.this, XycardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ye", "0");
                initent.putExtras(bundle);
                startActivity(initent);
            }
        });
        ivs[1] = new ImageView(this);
        ivs[1].setImageResource(pic[1]);
        ivs[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initent = new Intent(XinYongCardActivity.this, XycardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ye", "1");
                initent.putExtras(bundle);
                startActivity(initent);
            }
        });
        ivs[2] = new ImageView(this);
        ivs[2].setImageResource(pic[2]);
        ivs[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initent = new Intent(XinYongCardActivity.this, XycardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ye", "2");
                initent.putExtras(bundle);
                startActivity(initent);
            }
        });
        PagerAdapter pa = new PagerAdapter() {
            @Override
            public int getCount() {
                return pic.length;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
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
        vp_banner.setAdapter(pa);
        had.sendEmptyMessageDelayed(123, 2000);
    }
    private void initdata() {
        pingan_card = (LinearLayout) findViewById(R.id.pingan_card);
        top_right_ll = (LinearLayout) findViewById(R.id.top_right_ll);
        top_right_ll.setVisibility(View.INVISIBLE);
        top_context = (TextView) findViewById(R.id.top_context);
        top_context.setText("信用卡办理");
        ka_layout = (ImageView) findViewById(R.id.ka_layout);
        ka1_layout = (ImageView) findViewById(R.id.ka1_layout);
        ka2_layout = (ImageView) findViewById(R.id.ka2_layout);
        ka3_layout = (ImageView) findViewById(R.id.ka3_layout);
        ka4_layout = (ImageView) findViewById(R.id.ka4_layout);
        ka5_layout = (ImageView) findViewById(R.id.ka5_layout);
        ka6_layout = (ImageView) findViewById(R.id.ka6_layout);
        ka7_layout = (ImageView) findViewById(R.id.ka7_layout);
        ka8_layout = (ImageView) findViewById(R.id.ka8_layout);
        ka9_layout = (ImageView) findViewById(R.id.ka9_layout);
        ka10_layout = (ImageView) findViewById(R.id.ka10_layout);
        ka11_layout = (ImageView) findViewById(R.id.ka11_layout);
        xingye_card = (LinearLayout) findViewById(R.id.xingye_card);
        xingye_card.setOnClickListener(this);
        minsheng_card = (LinearLayout) findViewById(R.id.minsheng_card);
        minsheng_card.setOnClickListener(this);
        top_left_ll = (LinearLayout) findViewById(R.id.top_left_ll);
        top_left_ll.setOnClickListener(this);
        vp_banner = (ViewPager) findViewById(R.id.vp_banner);
        vp_banner.setOnClickListener(this);
        ka_layout.setOnClickListener(this);
        ka1_layout.setOnClickListener(this);
        ka2_layout.setOnClickListener(this);
        ka3_layout.setOnClickListener(this);
        ka4_layout.setOnClickListener(this);
        ka5_layout.setOnClickListener(this);
        ka6_layout.setOnClickListener(this);
        ka7_layout.setOnClickListener(this);
        ka8_layout.setOnClickListener(this);
        ka9_layout.setOnClickListener(this);
        ka10_layout.setOnClickListener(this);
        ka11_layout.setOnClickListener(this);
        pingan_card.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pingan_card:
                Intent intent15 = new Intent(this, ShareH5WebViewActivity.class);
                intent15.putExtra("url", "https://rmb.pingan.com.cn/t/vdcz5dBd?SSLSOURCE=BROP-CMP&activity_FlowId=m_Q0LAv16wPpw3vw5b2988");
                intent15.putExtra("topRight", " ");
                intent15.putExtra("topContext", "平安银行");
                startActivity(intent15);
                break;
            case R.id.ka11_layout:
                Intent intent14 = new Intent(this, ShareH5WebViewActivity.class);
                intent14.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent14.putExtra("topRight", " ");
                intent14.putExtra("topContext", "兴业银行");
                startActivity(intent14);
                break;
            case R.id.ka10_layout:
                Intent intent13 = new Intent(this, ShareH5WebViewActivity.class);
                intent13.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent13.putExtra("topRight", " ");
                intent13.putExtra("topContext", "兴业银行");
                startActivity(intent13);
                break;
            case R.id.ka9_layout:
                Intent intent12 = new Intent(this, ShareH5WebViewActivity.class);
                intent12.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent12.putExtra("topRight", " ");
                intent12.putExtra("topContext", "兴业银行");
                startActivity(intent12);
                break;
            case R.id.ka8_layout:
                Intent intent11 = new Intent(this, ShareH5WebViewActivity.class);
                intent11.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent11.putExtra("topRight", " ");
                intent11.putExtra("topContext", "兴业银行");
                startActivity(intent11);
                break;
            case R.id.ka7_layout:
                Intent intent10 = new Intent(this, ShareH5WebViewActivity.class);
                intent10.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent10.putExtra("topRight", " ");
                intent10.putExtra("topContext", "兴业银行");
                startActivity(intent10);
                break;
            case R.id.ka6_layout:
                Intent intent9 = new Intent(this, ShareH5WebViewActivity.class);
                intent9.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent9.putExtra("topRight", " ");
                intent9.putExtra("topContext", "民生银行");
                startActivity(intent9);
                break;
            case R.id.ka5_layout:
                Intent intent8 = new Intent(this, ShareH5WebViewActivity.class);
                intent8.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent8.putExtra("topRight", " ");
                intent8.putExtra("topContext", "民生银行");
                startActivity(intent8);
                break;
            case R.id.ka4_layout:
                Intent intent7 = new Intent(this, ShareH5WebViewActivity.class);
                intent7.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent7.putExtra("topRight", " ");
                intent7.putExtra("topContext", "民生银行");
                startActivity(intent7);
                break;
            case R.id.ka3_layout:
                Intent intent6 = new Intent(this, ShareH5WebViewActivity.class);
                intent6.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent6.putExtra("topRight", " ");
                intent6.putExtra("topContext", "民生银行");
                startActivity(intent6);
                break;
            case R.id.ka2_layout:
                Intent intent5 = new Intent(this, ShareH5WebViewActivity.class);
                intent5.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent5.putExtra("topRight", " ");
                intent5.putExtra("topContext", "民生银行");
                startActivity(intent5);
                break;
            case R.id.ka1_layout:
                Intent intent4 = new Intent(this, ShareH5WebViewActivity.class);
                intent4.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent4.putExtra("topRight", " ");
                intent4.putExtra("topContext", "民生银行");
                startActivity(intent4);
                break;
            case R.id.ka_layout:
                Intent intent3 = new Intent(this, ShareH5WebViewActivity.class);
                intent3.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent3.putExtra("topRight", " ");
                intent3.putExtra("topContext", "民生银行");
                startActivity(intent3);
                break;
            case R.id.xingye_card:
                Intent intent2 = new Intent(this, ShareH5WebViewActivity.class);
                intent2.putExtra("url", "https://wm.cib.com.cn/application/cardapp/newfast/ApplyCard/toSelectCard?id=e98ebfc845db456390f2ed041090ef03&from=singlemessage&isappinstalled=0");
                intent2.putExtra("topRight", " ");
                intent2.putExtra("topContext", "兴业银行");
                startActivity(intent2);
                break;
            case R.id.minsheng_card:
                Intent intent = new Intent(this, ShareH5WebViewActivity.class);
                intent.putExtra("url", "https://creditcard.cmbc.com.cn/wsv2/?etr=EKFhUTqs3dsF9ErAPuLWOHE4yFgQtz5SlmhxaO0DH9UiXVdd1eUj6zpa%20ZpC1kCXb2o7pJFVedbsZmx98Hjszf9p9gXLNtuLB3fp3g98%20pGxcCFZUYUYfUJKAOUvAjFyLDo30NjRMTLMYuqAFfIehFZbVe%20nXK6OL2xTrm/C26ZxIPUAv3j15Sp%20Vh8BpaGkkGtGu01PysoYMWoVFa00wKhWrPKTsAa2sVtxsZvOFnvs%20qi2OqZ6y5f30XG2XTCUBn3fB10VxU/GsHl4UF97oUr4HbCQ1UTHAwSXO6c0YeE0z60FTtWibNptiRcQPrDJ1vwMJhFBjM8TIAw145VBAkr4NVlvdJbnww32o3Pv6ap5SIhO2fmCR%20AYexC/wPvlWEBK4XWeBRIn67rJMACB5sXSI%20aI73aHbmiv%20snJTRNinzJD/tPFJToqbdsPzEsiooZGh/pdJ%20AUXabYfrpC2%20fOYfF1o2/F9wwC3SxMksdVbVaDUNCqSPBb8a74vaCo&time=1502687209580&from=singlemessage&isappinstalled=0");
                intent.putExtra("topRight", " ");
                intent.putExtra("topContext", "民生银行");
                startActivity(intent);
                break;
            case R.id.top_left_ll:
                finish();
                break;
        }
    }
}
