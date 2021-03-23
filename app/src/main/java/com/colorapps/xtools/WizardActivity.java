package com.colorapps.xtools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.colorapps.xtools.utils.Tools;

public class WizardActivity extends AppCompatActivity {

    private static final int MAX_STEP = 3;
    private ViewPager viewPager;

    private final String[] wizard_title_array = {
            "Welcome to MytoolsApp",
            "Pick a Tool",
            "Share with others"
    };


    private final String[] wizard_description_array = {
            "You are welcome to MytoolsApp. Looks like you are new here. A little intro please",
            "Select a tool from the list and view its uses. Cool right?",
            "Share with your friends the great tools you find."
    };

    private final int[] wizard_images_array = {
            R.drawable.ic_wizard_1,
            R.drawable.img_wizard_2,
            R.drawable.ic_wizard_share
    };

    private final int[] bg_images_array = {
            R.drawable.petrol,
            R.drawable.relax,
            R.drawable.share,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        bottomProgressDots(0);

        WizardAdapter myWizardAdapter = new WizardAdapter(this);
        viewPager.setAdapter(myWizardAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);

    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };



    public class WizardAdapter extends PagerAdapter{

        Context context;

        public WizardAdapter (Context context){
            this.context = context;
        }
        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View view = layoutInflater.inflate(R.layout.item_card_wizard_bg, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(wizard_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(wizard_description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(wizard_images_array[position]);
            ((ImageView) view.findViewById(R.id.image_bg)).setImageResource(bg_images_array[position]);

            Button btnNext = (Button) view.findViewById(R.id.btn_next);

            if (position == wizard_title_array.length - 1) {
                btnNext.setText("Get Started");
            } else {
                btnNext.setText("Next");
            }


            btnNext.setOnClickListener(v -> {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    SharedPreferences.Editor onboardEditor =
                            PreferenceManager.getDefaultSharedPreferences(context).edit();
                    onboardEditor.putBoolean("xtoolsWiz",true);
                    onboardEditor.apply();
                    Intent intent = new Intent(context, ToolsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return wizard_title_array.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
