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

public class HomeActivity extends AppCompatActivity {

    public interface OnScrollDirectionChangedListener {
        void onScrollDirectionChange(ScrollDirection scrollDirection);
    }

    private OnScrollDirectionChangedListener onScrollDirectionChangedListener;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private InfiniteViewPager mViewPager;
    private ScrollDirection scrollDirection = ScrollDirection.NONE;
    private int currentPagePosition;

    public void setOnScrollDirectionChangedListener(OnScrollDirectionChangedListener onScrollDirectionChangedListener) {
        this.onScrollDirectionChangedListener = onScrollDirectionChangedListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (GymNotesApplication.getmInstance().isAppFirstStart()) {
                    GymNotesApplication.getmInstance().setAppFirstStart(false);
                    currentPagePosition = position;
                    if (onScrollDirectionChangedListener != null) {
                        onScrollDirectionChangedListener.onScrollDirectionChange(scrollDirection);
                    }
                    return;
                }

                if (currentPagePosition < position) {
                    Log.i("Sev", "SWIPE TO RIGHT");
                    scrollDirection = ScrollDirection.RIGHT;
                } else {
                    Log.i("Sev", "SWIPE TO LEFT");
                    scrollDirection = ScrollDirection.LEFT;
                }
                currentPagePosition = position;
                if (onScrollDirectionChangedListener != null) {
                    onScrollDirectionChangedListener.onScrollDirectionChange(scrollDirection);
                }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a WorkoutFragment (defined as a static inner class below).
            Log.i("Sev", String.valueOf(position));
            return WorkoutFragment.newInstance(position + 1);
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
