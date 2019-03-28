package sisa.vinos.com.vinos.adaptador;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import sisa.vinos.com.vinos.fragmentos.FragmentoHome;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static int TAB_COUNT = 1;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("posicion ", "pos: "+position);
        switch (position) {
            case 0:
                return FragmentoHome.newInstance();
            //case 2:
            //  return SettingsFragment.newInstance();
        }
        return null;
    }

}
