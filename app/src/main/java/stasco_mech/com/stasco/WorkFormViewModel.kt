package stasco_mech.com.stasco

import android.arch.lifecycle.ViewModel
import android.widget.EditText

class WorkFormViewModel : ViewModel() {

    lateinit var areaStatus: AreaStatus

    fun modelUpdateText(editText: EditText, workArea: WorkArea, index: Int, s: CharSequence) {

        areaStatus.updateText(editText, workArea, index, s)
    }
}