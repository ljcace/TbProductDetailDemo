package com.ljc.demo;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ljc.broadcast.BroadCastManager;
import com.ljc.demo.model.TempModel;
import com.ljc.demo.model.TempParcelableModel;
import com.ljc.demo.model.TempSerializableModel;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 产品详情
 */
public class ProductDetailActivity extends AppCompatActivity {
    NestedScrollView sv_detail;
    ImageView iv_product, iv_play, iv_video_f;
    VideoView videoview;
    LinearLayout ll_pj, ll_xq, ll_tj;
    RelativeLayout rl_title;
    TextView tv_back, tv_like, tv_share;
    TabLayout tl_menu;

    private Map<String, Object> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );

        setContentView(R.layout.act_product_detail);

        sv_detail = findViewById(R.id.sv_detail);
        iv_product = findViewById(R.id.iv_product);
        iv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.isEmpty()) {
                    initData();
                }
                Map.Entry<String, Object> entry = data.entrySet().iterator().next();
                String key = entry.getKey();
                Object value = entry.getValue();
                HashMap<String, Object> h1 = new HashMap<>();
                h1.put(key, value);
                BroadCastManager.getInstance().sendBroadCast(ProductDetailActivity.this, MainActivity.BROADCAST_ACTION_EXP, h1);
                data.remove(key);
            }
        });
        iv_video_f = findViewById(R.id.iv_video_f);
        videoview = findViewById(R.id.videoview);
        iv_play = findViewById(R.id.iv_play);
        ll_pj = findViewById(R.id.ll_pj);
        ll_xq = findViewById(R.id.ll_xq);
        ll_tj = findViewById(R.id.ll_tj);
        rl_title = findViewById(R.id.rl_title);
        tv_back = findViewById(R.id.tv_back);
        tv_like = findViewById(R.id.tv_like);
        tv_share = findViewById(R.id.tv_share);
        tl_menu = findViewById(R.id.tl_menu);

        String img = "http://img1.imgtn.bdimg.com/it/u=1563980539,1672265910&fm=26&gp=0.jpg";
        String gif = "http://img.mp.itc.cn/upload/20160918/b71745885bc448399a67dc7e43b72660_th.gif";
        Glide.with(this)
                .load(gif)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(iv_product);

        String video_f_url = "https://hmmalloss.oss-cn-hangzhou.aliyuncs.com/7sNPxHPFm8iXGrwNCG8pYnbdH4epK5Kx.jpg";
        Glide.with(this).load(video_f_url).into(iv_video_f);

        String videoUrl = "https://hmmalloss.oss-cn-hangzhou.aliyuncs.com/sAteftCcxwZrSttkXWskE7iNHW5FMcmn.mp4";
        videoview.setVideoPath(videoUrl);
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isWifi(ProductDetailActivity.this)) {
                    videoview.start();
                    iv_play.setVisibility(View.GONE);
                    iv_video_f.setVisibility(View.GONE);
                } else {
                    iv_play.setVisibility(View.VISIBLE);
                    iv_video_f.setVisibility(View.VISIBLE);
                }
            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_play.setVisibility(View.VISIBLE);
                iv_video_f.setVisibility(View.VISIBLE);
            }
        });

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoview.start();
                iv_play.setVisibility(View.GONE);
                iv_video_f.setVisibility(View.GONE);
            }
        });

        initData();
        initTab();
        initTitleView();
        initScroll();
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    private void initData() {
        data = new HashMap<>();
        data.put("Byte", (byte) 1);
        data.put("byte[]", new Byte[]{1, 2, 3});
        data.put("String", "StringValue");
        data.put("String[]", new String[]{"1", "b", "3c"});
        data.put("Character", '2');
        data.put("Character[]", new Character[]{'a', '2', 'c'});
        data.put("Short", (short) 2);
        data.put("Short[]", new Short[]{3, 5, 7});
        data.put("Boolean", false);
        data.put("Boolean[]", new Boolean[]{false, true});
        data.put("Integer", 3);
        data.put("Integer[]", new Integer[]{3, 4, 5});
        data.put("Long", 41231231241312312L);
        data.put("Long[]", new Long[]{41231231241312312L, 738173871737L, 472178371234688L});
        data.put("Float", 0.23f);
        data.put("Float[]", new Float[]{0.43f, 1.25f});
        data.put("Double", 0.23d);
        data.put("Double[]", new Double[]{0.43d, 1.25d});
        Bundle b1 = new Bundle();
        b1.putString("data", "value");
        data.put("Bundle", b1);
        Bundle[] bs = new Bundle[]{b1, b1, b1};
        data.put("Bundle[]", bs);
        Parcel parcel = Parcel.obtain();
        parcel.writeLong(1);
        parcel.writeString("555");
        TempParcelableModel tempParcelableModel = new TempParcelableModel(parcel);
        data.put("Parcelable", tempParcelableModel);
        TempParcelableModel[] tempParcelableModels = new TempParcelableModel[]{tempParcelableModel, tempParcelableModel};
        data.put("Parcelable[]", tempParcelableModels);
        TempSerializableModel tempSerializableModel = new TempSerializableModel();
        data.put("Serializable", tempSerializableModel);
        TempModel tempModel = new TempModel();
        data.put("Class", tempModel);
    }

    private void initTab() {
        tl_menu.setTabMode(TabLayout.MODE_FIXED);
        TabLayout.Tab tab1 = tl_menu.newTab().setText("产品");

        TabLayout.Tab tab2 = tl_menu.newTab().setText("评价");
        TabLayout.Tab tab3 = tl_menu.newTab().setText("详情");
        TabLayout.Tab tab4 = tl_menu.newTab().setText("推荐");
        tl_menu.addTab(tab1);
        tl_menu.addTab(tab2);
        tl_menu.addTab(tab3);
        tl_menu.addTab(tab4);

        String selectedColor, normalColor;
        selectedColor = "#bc9384";
        normalColor = "#333333";

        tl_menu.setTabTextColors(Color.parseColor(normalColor), Color.parseColor(selectedColor));
        tl_menu.setSelectedTabIndicatorColor(Color.parseColor(selectedColor));
        tl_menu.setSelectedTabIndicatorHeight(3);

        for (int i = 0; i < tl_menu.getTabCount(); i++) {
            TabLayout.Tab tab = tl_menu.getTabAt(i);
            if (tab == null) return;
            Class c = tab.getClass();
            try {
                //不同android.support.design.widget版本中TabLayout.Tab的view参数名可能不同
                //具体需要去TabLayout.Tab查看参数名，参数名不一致将导致点击切换无效
                Field field = c.getDeclaredField("view");
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        //这里就可以根据业务需求处理点击事件了。
                        Log.e("点击", position + ":" + tab.getText());
                        int titleHeight = rl_title.getHeight();
                        switch (position) {
                            case 0:
                                sv_detail.smoothScrollTo(0, 0);
                                break;
                            case 1:
                                int pjY = ll_pj.getTop() - titleHeight;
                                sv_detail.smoothScrollTo(0, pjY);
                                break;
                            case 2:
                                int xqY = ll_xq.getTop() - titleHeight;
                                sv_detail.smoothScrollTo(0, xqY);
                                break;
                            case 3:
                                int tjY = ll_tj.getTop() - titleHeight;
                                sv_detail.smoothScrollTo(0, tjY);
                                break;
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initScroll() {
        rl_title.setBackgroundColor(Color.argb(0, 255, 255, 255));

        ViewTreeObserver viewTreeObserver = iv_product.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int bannerHeight = iv_product.getHeight();
                int titleHeight = rl_title.getHeight();
                sv_detail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (scrollY == 0) {   //设置标题的背景颜色
                            initTitleView();
                        } else if (scrollY > 0 && scrollY <= bannerHeight - titleHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            updateTitleColor(scrollY, bannerHeight);
                        } else {    //滑动到banner下面设置普通颜色
                            updateTitleTab(scrollY);
                        }
                    }
                });
            }
        });
    }

    private void initTitleView() {
        tv_back.setTextColor(Color.argb(255, 0, 0, 0));
        tv_share.setTextColor(Color.argb(255, 0, 0, 0));
        tv_like.setTextColor(Color.argb(255, 0, 0, 0));
        tv_back.setAlpha(1);
        tv_share.setAlpha(1);
        tv_like.setAlpha(1);
        tl_menu.setVisibility(View.INVISIBLE);
        tl_menu.setAlpha(0);
        rl_title.setBackgroundColor(Color.argb(0, 255, 255, 255));
    }

    private void updateTitleColor(int scrollY, int bannerHeight) {
        float scale = (float) scrollY / bannerHeight;
        float alpha = (255 * scale);
        if (scale <= 0.7) {
            tv_back.setTextColor(Color.argb(255, 0, 0, 0));
            tv_share.setTextColor(Color.argb(255, 0, 0, 0));
            tv_like.setTextColor(Color.argb(255, 0, 0, 0));
            tv_back.setAlpha(0.7f - scale);
            tv_share.setAlpha(0.7f - scale);
            tv_like.setAlpha(0.7f - scale);
        } else {
            tv_back.setTextColor(Color.argb(255, 255, 0, 0));
            tv_share.setTextColor(Color.argb(255, 0, 255, 0));
            tv_like.setTextColor(Color.argb(255, 0, 0, 255));
            float scale2 = (scale - 0.7f) / 0.3f;
            tv_back.setAlpha(scale2);
            tv_share.setAlpha(scale2);
            tv_like.setAlpha(scale2);
        }
        tl_menu.setAlpha(scale);
        tl_menu.setVisibility(View.VISIBLE);
        rl_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
    }

    private void updateTitleTab(int scrollY) {
        rl_title.setBackgroundColor(Color.argb(255, 255, 255, 255));
        tv_back.setAlpha(1);
        tv_share.setAlpha(1);
        tv_like.setAlpha(1);
        tl_menu.setAlpha(1);

        int titleHeight = rl_title.getHeight();
        int pjY = ll_pj.getTop() - titleHeight;
        int xqY = ll_xq.getTop() - titleHeight;
        int tjY = ll_tj.getTop() - titleHeight;

        if (scrollY < pjY) {
            if (!tl_menu.getTabAt(0).isSelected())
                tl_menu.getTabAt(0).select();
        } else if (scrollY < xqY) {
            if (!tl_menu.getTabAt(1).isSelected())
                tl_menu.getTabAt(1).select();
        } else if (scrollY < tjY) {
            if (!tl_menu.getTabAt(2).isSelected())
                tl_menu.getTabAt(2).select();
        } else {
            if (!tl_menu.getTabAt(3).isSelected())
                tl_menu.getTabAt(3).select();
        }
    }
}
