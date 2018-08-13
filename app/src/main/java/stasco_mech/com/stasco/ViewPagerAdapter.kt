package stasco_mech.com.stasco

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

class ViewPagerAdapter(var context: Context?, fragmentManager: FragmentManager, val employeeList: Array<String>) : FragmentPagerAdapter(fragmentManager) {

    var size = employeeList.size


    override fun getCount() : Int {
        return size
    }

    override fun getItem(position: Int) : Fragment {
        println("GETITEMCALLED")
        return TimeFormDetailFragment.newInstance(position)
    }

    override fun getPageTitle(position: Int) : String {
        return TimeFormDetailFragment.getTitle(context, position, employeeList)
    }
}