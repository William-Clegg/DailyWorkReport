package stasco_mech.com.stasco

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(context: Context?, fragmentManager: FragmentManager, private val employeeList: Array<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return TimeFormDetailFragment.newInstance(employeeList[pos])
    }

    override fun getCount(): Int {
        return employeeList.size
    }
}