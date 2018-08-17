package stasco_mech.com.stasco

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class WorkAreaTextWatcher(val editText: EditText, val workArea: WorkArea, val index: Int, var workFormViewModel: WorkFormViewModel): TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        workFormViewModel.modelUpdateText(editText, workArea, index, s)
    }

    override fun afterTextChanged(s: Editable) {

    }

}