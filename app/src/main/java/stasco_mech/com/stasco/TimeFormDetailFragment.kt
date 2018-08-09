package stasco_mech.com.stasco

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_form_detail.*
import kotlinx.android.synthetic.main.time_form_detail.*
import stasco_mech.com.stasco.R.layout.time_form_detail
import stasco_mech.com.stasco.dummy.DummyContent
import java.nio.InvalidMarkException
import java.util.*

class TimeFormDetailFragment() : Fragment() {
    private lateinit var areaLayout: ConstraintLayout
    private lateinit var mainAreaSet: ConstraintSet
    private lateinit var viewPager: ViewPager
    private val employees = Arrays.asList(R.array.employee_array)

    companion object {                              //Figure out later if all 'static' variables can be placed in the same Companion object or if they need their own
        val ARG_ITEM_ID: String = "item_id"
        private val KEY_POSITION = "position"

        fun newInstance(employee: String): TimeFormDetailFragment {

            val args = Bundle()
            args.putString("KEY_POSITION", employee)
            val fragment = TimeFormDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        val employeeList = Arrays.asList(R.array.employee_array)
        val employeeIndex = 0

        if (arguments?.containsKey(ARG_ITEM_ID) ?: false) {

            var mItem: DummyContent.DummyItem? = DummyContent.ITEM_MAP.get(arguments?.getString(ARG_ITEM_ID))
            var activity = this.activity
            var appBarLayout: CollapsingToolbarLayout? = toolbar_layout
            if(appBarLayout != null) {
                appBarLayout.title = mItem?.content
            }
        }
    }*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        val rootView = inflater.inflate(time_form_detail, container, false)
        pos = arguments?.getInt(KEY_POSITION, -1)
        editor.setHint(getTitle(activity, pos))

        return rootView
    }
}