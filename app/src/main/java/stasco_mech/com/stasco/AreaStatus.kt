package stasco_mech.com.stasco

import android.widget.EditText

class AreaStatus() {

    var areaTrack : ArrayList<WorkArea?> = ArrayList()
    var costTrack : ArrayList<ArrayList<String?>> = ArrayList()

    constructor(areaArray: ArrayList<WorkArea?>, costArray: ArrayList<ArrayList<String?>>) : this() {

        areaTrack = areaArray
        costTrack = costArray
    }

    fun addArea(workArea: WorkArea) {
        if(areaTrack.size < 5) {
            areaTrack.add(workArea)
            costTrack.get(areaTrack.size - 1).add("")
            costTrack.get(areaTrack.size - 1).add("")
        }
    }

    fun addCost(workArea: WorkArea) {
        if(costTrack.get(areaTrack.indexOf(workArea)).size < 6) {
            costTrack.get(areaTrack.indexOf(workArea)).add("")
        }
    }

    fun removeArea(workArea: WorkArea) {
        areaTrack.remove(workArea)
    }

    fun removeCost(workArea: WorkArea, index: Int) {
        costTrack.get(areaTrack.indexOf(workArea)).removeAt(index)
    }

    fun updateText(editText: EditText, workArea: WorkArea) {

    }
}