package org.sherman.tony.habittracker.Adapters


import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.sherman.tony.habittracker.Fragments.ActivityFragment
import org.sherman.tony.habittracker.Fragments.ReportFragment


class TabbedMenuAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): android.support.v4.app.Fragment {
        when(position) {
            0 -> return ActivityFragment()
            1 -> return ReportFragment()
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            0 -> return "HABIT"
            1 -> return "REPORTS"
        }
        return null!!
    }

}