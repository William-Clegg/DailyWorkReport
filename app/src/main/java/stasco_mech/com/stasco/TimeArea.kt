package stasco_mech.com.stasco

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton

import java.util.ArrayList

import android.support.constraint.ConstraintSet.WRAP_CONTENT
import kotlinx.android.synthetic.main.area_group.view.*
import kotlinx.android.synthetic.main.time_form_area.view.*
import stasco_mech.com.stasco.WorkFormDetailFragment.previousAreaId
import stasco_mech.com.stasco.WorkFormDetailFragment.stateTrack

class TimeArea : ConstraintLayout {

    internal var areaLayout: ConstraintLayout? = null
    lateinit var newAreaLayout: ConstraintLayout
    lateinit var newAreaGroup: View
    private var inflater: LayoutInflater? = null

    lateinit var costFields: Array<EditText?>
    internal var costStrings = ArrayList<String>()

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, areaLayout: ConstraintLayout?) : super(context) {
        initView(context, areaLayout)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView(context)
    }

    private fun initView(context: Context) {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        areaLayout!!.addView(inflater!!.inflate(R.layout.time_form_area, areaLayout, false))
    }

    private fun initView(context: Context, areaLayout: ConstraintLayout?) {

        val timeFirstCost: EditText? = timeFirstCost
        val timeSecondCost: EditText? = timeSecondCost
        val timeThirdCost: EditText? = timeThirdCost
        val timeFourthCost: EditText? = timeFourthCost

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        newAreaGroup = inflater!!.inflate(R.layout.time_form_area, areaLayout, false)

        costFields = arrayOf(timeFirstCost, timeSecondCost, timeThirdCost, timeFourthCost)

        val areaLayoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT)

        areaLayout?.addView(newAreaLayout, areaLayoutParams)
    }
}