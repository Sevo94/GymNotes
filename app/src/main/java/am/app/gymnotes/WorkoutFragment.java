package am.app.gymnotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class WorkoutFragment extends Fragment implements HomeActivity.OnScrollDirectionChangedListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int num = 0;

    private ScrollDirection scrollDirection;

    public static WorkoutFragment newInstance(int sectionNumber) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WorkoutFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomeActivity mActivity = (HomeActivity) getActivity();
        mActivity.setOnScrollDirectionChangedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);

        Log.i("Sev", "onCreateView!!!");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss 'GMT'Z yyyy");
//            System.out.println(dateFormat.format(cal.getTime()));
////            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            textView.setText(dateFormat.format(cal.getTime()));
////            textView.setText(String.valueOf(num++));

        GregorianCalendar gc = new GregorianCalendar();
        if (scrollDirection == null) {
            gc.add(Calendar.DATE, num);
        } else {
            switch (scrollDirection) {
                case NONE:
                    gc.add(Calendar.DATE, num);
                    break;
                case RIGHT:
                    gc.add(Calendar.DATE, ++num);
                    break;
                case LEFT:
                    gc.add(Calendar.DATE, --num);
                    break;
            }
        }

        textView.setText(dateFormat.format(gc.getTime()));
        return rootView;
    }

    @Override
    public void onScrollDirectionChange(ScrollDirection scrollDirection) {
        this.scrollDirection = scrollDirection;
    }
}
