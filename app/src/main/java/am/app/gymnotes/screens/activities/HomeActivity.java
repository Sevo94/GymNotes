package am.app.gymnotes.screens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;

import am.app.gymnotes.CalenderManager;
import am.app.gymnotes.R;
import am.app.gymnotes.screens.fragments.WorkoutFragment;

public class HomeActivity extends AppCompatActivity implements WorkoutFragment.FragmentLoadListener {

    public enum Date {
        PREVIOUS, CURRENT, NEXT
    }

    private static final String TAG = HomeActivity.class.getCanonicalName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private InfiniteViewPager mViewPager;

    private int loadedFragmentsCount;
    private int currentPagePosition;

    private boolean appFirstStart;

    public int getLoadedFragmentsCount() {
        return loadedFragmentsCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i(TAG, "onCreate");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //when activity recreated
        appFirstStart = true;
        CalenderManager.getInstance().setDate(Date.CURRENT);
        loadedFragmentsCount = 0;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (appFirstStart) {
                    appFirstStart = false;
                    currentPagePosition = position;
                    return;
                }

                Log.i(TAG, "position = " + position + ", current position = " + currentPagePosition);

                if (currentPagePosition < position) {
                    Log.i(TAG, "SWIPE TO RIGHT");
                    CalenderManager.getInstance().addDateToCalender(Date.NEXT);
                } else {
                    Log.i(TAG, "SWIPE TO LEFT");
                    CalenderManager.getInstance().addDateToCalender(Date.PREVIOUS);
                }
                currentPagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                }

            }
        });
        mViewPager.setAdapter(wrappedAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(HomeActivity.this, ExerciseChooserActivity.class));
            }
        });
    }

    public void updateViewPager(int year, int month, int day) {
        CalenderManager.getInstance().updateDates(year, month, day);
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentLoadFinished() {
        loadedFragmentsCount++;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("Sev", String.valueOf(position));
            return WorkoutFragment.newInstance(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
