package in.madscientist.chattest.ui;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.madscientist.chattest.R;
import in.madscientist.chattest.utils.ViewPagerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

   @BindView(R.id.viewPager)ViewPager viewPager;
    @BindView(R.id.tabLayout)TabLayout tabLayout;

    @Override
    protected void attachBaseContext(Context tp) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(tp));
    }
public static final String TAG = MainActivity.class.getCanonicalName().toString();
    private ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
              Fragment fragment= viewPagerAdapter.getItem(viewPager.getCurrentItem());
                if (fragment instanceof Fragment_UserDetails && state == ViewPager.SCROLL_STATE_SETTLING)
                {
                    ((Fragment_UserDetails) fragment).refreshList();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new Fragment_Chats(), "Coversations");
        viewPagerAdapter.addFragment(new Fragment_UserDetails(), "Users");
        viewPager.setAdapter(viewPagerAdapter);
    }

}
