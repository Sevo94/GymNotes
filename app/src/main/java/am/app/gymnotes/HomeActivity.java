package am.app.gymnotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;
import com.antonyt.infiniteviewpager.InfiniteViewPager;

public class HomeActivity extends AppCompatActivity implements WorkoutFragment.FragmentLoadListener {

    private static final String TAG = HomeActivity.class.getCanonicalName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private InfiniteViewPager mViewPager;

    private int loadedFragmentsCount;
    private int currentPagePosition;

    public int getLoadedFragmentsCount() {
        return loadedFragmentsCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(0);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (GymNotesApplication.getmInstance().isAppFirstStart()) {
                    GymNotesApplication.getmInstance().setAppFirstStart(false);
                    CalenderManager.getInstance().addDateToCalender(0);
                    currentPagePosition = position;
                    return;
                }

                Log.i(TAG, "position = " + position + ", current position = " + currentPagePosition);

                if (currentPagePosition < position) {
                    Log.i(TAG, "SWIPE TO RIGHT");
                    CalenderManager.getInstance().addDateToCalender(1);
                } else {
                    Log.i(TAG, "SWIPE TO LEFT");
                    CalenderManager.getInstance().addDateToCalender(-1);
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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public void onFragmentLoadFinished() {
        loadedFragmentsCount++;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("Sev", String.valueOf(position));
            switch (position) {
                case 0:
                    CalenderManager.getInstance().addDateToCalender(0);
                    break;
                case 1:
                    CalenderManager.getInstance().addNextDay();
                    break;
                case 3:
                    CalenderManager.getInstance().addPreviousDay();
                    break;
            }
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
