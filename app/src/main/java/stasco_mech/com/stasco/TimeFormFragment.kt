package stasco_mech.com.stasco

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_form_detail.*
import kotlinx.android.synthetic.main.time_form.*
import kotlinx.android.synthetic.main.time_form.view.*
import kotlinx.android.synthetic.main.time_form_detail.*
import stasco_mech.com.stasco.dummy.DummyContent
import java.util.*
import kotlin.collections.ArrayList

class TimeFormFragment() : Fragment() {
    private lateinit var areaLayout: ConstraintLayout
    private lateinit var mainAreaSet: ConstraintSet
    private lateinit var viewPager: ViewPager
    private val employeeIndex = 0
    private lateinit var employeeList: Array<String>

    companion object {                              //Figure out later if all 'static' variables can be placed in the same Companion object or if they need their own
        val ARG_ITEM_ID: String = "item_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);


        if (arguments?.containsKey(ARG_ITEM_ID) ?: false) {

            var mItem: DummyContent.DummyItem? = DummyContent.ITEM_MAP.get(arguments?.getString(ARG_ITEM_ID))
            var activity = this.activity
            var appBarLayout: CollapsingToolbarLayout? = activity?.toolbar_layout
            println("???????????????????")
            if(appBarLayout != null) {
                println("!!!!!!!!!!!!!!!!!" + mItem?.content)
                appBarLayout.title = mItem?.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        val rootView = inflater.inflate(R.layout.time_form, container, false)
        var viewPager = rootView.pager

        employeeList = resources.getStringArray(R.array.employee_array)
        viewPager.adapter = createAdapter()

        return rootView
    }

    private fun createAdapter() : PagerAdapter {
        return ViewPagerAdapter(this.context, childFragmentManager, employeeList)
    }
}