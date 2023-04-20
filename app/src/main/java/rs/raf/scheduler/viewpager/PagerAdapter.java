package rs.raf.scheduler.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.scheduler.fragments.AccountFragment;
import rs.raf.scheduler.fragments.CalendarFragment;
import rs.raf.scheduler.fragments.DailyPlanFragment;
import timber.log.Timber;

public class PagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;

    public static final int CALENDAR_FRAGMENT = 0;
    public static final int DAILY_PLAN_FRAGMENT = 1;
    public static final int ACCOUNT_FRAGMENT = 2;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case CALENDAR_FRAGMENT: return new CalendarFragment();
            case DAILY_PLAN_FRAGMENT: return new DailyPlanFragment();
            default: return new AccountFragment();
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case CALENDAR_FRAGMENT: return "Calendar";
            case DAILY_PLAN_FRAGMENT: return "Daily Plan";
            default: return "Account";
        }
    }
}
