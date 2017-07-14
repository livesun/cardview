package livesun.cardpager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.util.List;

import livesun.cardpager.adapter.PageAdapter;
import livesun.cardpager.transformer.ScaleTransformer;
import livesun.cardpager.ui.IndicatorView;

/**
 * Created by 29028 on 2017/7/14.
 */

public class PagerManager implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private Context context;
    private View mPagerLayout;
    private View mRootView;
    private ViewPager mViewPager;
    private IndicatorView mIndicatorView;
    static {
        manager = new PagerManager();
    }

    private static PagerManager manager;
    private ImageView close;

    public static PagerManager getInstance(){
        return manager;
    }

    public void with(Activity context){
        this.context = context;
        ViewGroup decorView = (ViewGroup) context.getWindow().getDecorView();
        mPagerLayout = LayoutInflater.from(context).inflate(R.layout.pager_layout, null);
        mRootView = mPagerLayout.findViewById(R.id.root);
        mRootView.getBackground().setAlpha(200);
        mViewPager = (ViewPager) mPagerLayout.findViewById(R.id.viewpager);
        mIndicatorView = (IndicatorView) mPagerLayout.findViewById(R.id.indicator);
        close = (ImageView) mPagerLayout.findViewById(R.id.close);
//        mViewPager.setPageMargin(10);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(false, new ScaleTransformer());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(mPagerLayout,params);
        mViewPager.addOnPageChangeListener(this);
        close.setOnClickListener(this);
    }

    public void addDatas(FragmentManager fm, List<Fragment> fragments){
        mIndicatorView.setCount(fragments.size());
        PageAdapter pageAdapter=new PageAdapter(fm,fragments);
        mViewPager.setAdapter(pageAdapter);
    }

    public void show(){
        mPagerLayout.setVisibility(View.VISIBLE);
   /*     ObjectAnimator animatorX = ObjectAnimator.ofFloat(mViewPager, "scaleX", 0.5f, 1.2f,0.7f,1.2f,0.8f,1.1f,0.9f,1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mViewPager, "scaleY", 0.5f, 1.2f,0.7f,1.2f,0.8f,1.1f,0.9f,1.0f);*/
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mViewPager, "scaleX", 0f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mViewPager, "scaleY", 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(500);
        animSet.start();


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndicatorView.setCheckPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {


        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(300);
        animationSet.setInterpolator(new AccelerateInterpolator(1));
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mRootView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mRootView.startAnimation(animationSet);


    }



}
