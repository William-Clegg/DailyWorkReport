package stasco_mech.com.stasco

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_form_detail.*
import stasco_mech.com.stasco.R.layout.time_form_detail
import stasco_mech.com.stasco.dummy.DummyContent
import java.nio.InvalidMarkException

class TimeFormDetailFragment() : Fragment() {
    private lateinit var newArea: ImageButton
    private lateinit var selectEntries: ImageButton
    private lateinit var deleteEntries: ImageButton
    private lateinit var cancelSelection: ImageButton
    private lateinit var areaLayout: ConstraintLayout
    private lateinit var mainAreaSet: ConstraintSet
    companion object {                              //Figure out later if all 'static' variables can be placed in the same Companion object or if they need their own
        var labelAreaId: Int = 0                    //and which way is better.
        var areaCount: Int = 0
        var areaId: Int = 0
        var previousAreaId: Int = -1
        var stateTrack = Array(5) {}
        var stopped: Boolean = false
        val ARG_ITEM_ID: String = "item_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        previousAreaId = -1 //these need to be reinitialized if onStart, etc rebuild layout from scratch
        areaCount = 0

        if (arguments?.containsKey(ARG_ITEM_ID) ?: false) {

            var mItem: DummyContent.DummyItem? = DummyContent.ITEM_MAP.get(arguments?.getString(ARG_ITEM_ID))
            var activity = this.activity
            var appBarLayout: CollapsingToolbarLayout? = toolbar_layout
            if(appBarLayout != null) {
                appBarLayout.title = mItem?.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        val rootView = inflater?.inflate(time_form_detail, container, false)
        areaLayout =

        return rootView
    }
}