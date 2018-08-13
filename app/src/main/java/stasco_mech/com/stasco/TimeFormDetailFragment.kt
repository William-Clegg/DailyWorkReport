package stasco_mech.com.stasco

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_form_detail.*
import kotlinx.android.synthetic.main.time_form_area.view.*
import kotlinx.android.synthetic.main.time_form_detail.*
import kotlinx.android.synthetic.main.time_form_detail.view.*
import stasco_mech.com.stasco.R.layout.time_form_area
import stasco_mech.com.stasco.R.layout.time_form_detail
import stasco_mech.com.stasco.WorkFormDetailFragment.stateTrack
import stasco_mech.com.stasco.dummy.DummyContent
import java.nio.InvalidMarkException
import java.util.*

class TimeFormDetailFragment : Fragment() {
    private lateinit var viewPager: ViewPager

    companion object {
        val ARG_ITEM_ID: String = "item_id"
        private val KEY_POSITION = "position"
        var previousTimeAreaId = 0

        fun newInstance(position: Int) : TimeFormDetailFragment {

            val fragment = TimeFormDetailFragment()
            val args = Bundle()
            args.putInt(KEY_POSITION, position)
            fragment.arguments = args
            return fragment
        }

        fun getTitle(context: Context?, position: Int, employeeList: Array<String>) : String {
            return employeeList[position]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        val rootView = inflater.inflate(time_form_detail, container, false)
        val areaLayout: ConstraintLayout? = rootView.time_form_detail_fragment_container
        for (area in stateTrack.indices) {

            if (stateTrack[area] != null) {
                println("area number " + area)
                println(Arrays.toString(stateTrack[area].costArray))
                TimeArea(context!!, areaLayout, stateTrack[area].costArray.filter { it == true }.size, area)
            }
        }

        return rootView
    }
}