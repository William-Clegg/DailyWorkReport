package stasco_mech.com.stasco;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import stasco_mech.com.stasco.databinding.AreaGroupBinding;

import static android.support.constraint.ConstraintSet.WRAP_CONTENT;
import static stasco_mech.com.stasco.WorkFormDetailFragment.previousAreaId;
import static stasco_mech.com.stasco.WorkFormDetailFragment.stateTrack;

public class WorkArea extends ConstraintLayout {

    private WorkArea workArea = this;

    ConstraintLayout areaLayout;
    public int costCount = 0;
    ConstraintLayout newAreaLayout;
    View newAreaGroup;
    public static boolean removedArea;
    private LayoutInflater inflater;

    CheckBox mainBox;
    CheckBox firstBox;
    CheckBox secondBox;
    CheckBox thirdBox;
    CheckBox fourthBox;
    EditText initialCost;
    EditText firstCost;
    EditText secondCost;
    EditText thirdCost;
    EditText fourthCost;
    ImageButton newCostButton;
    View areaDivider;

    EditText[] costFields;
    CheckBox[] costBoxes;
    ArrayList<String> costStrings = new ArrayList<String>();
    boolean[] viewIds = new boolean[4];

    public WorkArea(Context context) {
        super(context);
        initView(context);
    }

    public WorkArea(Context context, Activity activity, ConstraintLayout areaLayout) {
        super(context);
        initView(context, activity, areaLayout);
    }

    public WorkArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WorkArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context){

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        areaLayout.addView(inflater.inflate(R.layout.area_group, areaLayout, false));
    }

    private void initView(Context context, Activity activity, final ConstraintLayout areaLayout){

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        costStrings.add("");
        costStrings.add("");

        newAreaGroup = inflater.inflate(R.layout.area_group, areaLayout, false);

        mainBox = newAreaGroup.findViewById(R.id.mainCheckBox);
        firstBox = newAreaGroup.findViewById(R.id.firstBox);
        secondBox = newAreaGroup.findViewById(R.id.secondBox);
        thirdBox = newAreaGroup.findViewById(R.id.thirdBox);
        fourthBox = newAreaGroup.findViewById(R.id.fourthBox);
        initialCost = newAreaGroup.findViewById(R.id.costInitial);
        firstCost = newAreaGroup.findViewById(R.id.firstCost);
        secondCost = newAreaGroup.findViewById(R.id.secondCost);
        thirdCost = newAreaGroup.findViewById(R.id.thirdCost);
        fourthCost = newAreaGroup.findViewById(R.id.fourthCost);
        newCostButton = newAreaGroup.findViewById(R.id.newCostButton);
        areaDivider = newAreaGroup.findViewById(R.id.areaDivider);

        costFields = new EditText[]{firstCost, secondCost, thirdCost, fourthCost};
        costBoxes = new CheckBox[]{firstBox, secondBox, thirdBox, fourthBox};

        mainBox.setVisibility(View.GONE);
        newAreaLayout = newAreaGroup.findViewById(R.id.areaGroupLayout);
        newAreaLayout.setId(WorkFormDetailFragment.areaId);

        ConstraintLayout.LayoutParams areaLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
        if(previousAreaId == null) {
            areaDivider.setVisibility(View.GONE);
            areaLayoutParams.topToBottom = WorkFormDetailFragment.labelAreaId;
        } else {
            areaLayoutParams.topToBottom = previousAreaId;
        }
        newAreaLayout.setLayoutParams(areaLayoutParams);

        previousAreaId = WorkFormDetailFragment.areaId;

        newCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(costCount < 4) { addNewCost(); }
            }
        });
        areaLayout.addView(newAreaLayout, areaLayoutParams);
    }

    public void addNewCost() {

        if(!viewIds[0]) {
            firstCost.setVisibility(View.VISIBLE);
            viewIds[0] = true;
        } else if(!viewIds[1]) {
            secondCost.setVisibility(View.VISIBLE);
            viewIds[1] = true;
        } else if(!viewIds[2]) {
            thirdCost.setVisibility(View.VISIBLE);
            viewIds[2] = true;
        } else if(!viewIds[3]) {
            fourthCost.setVisibility(View.VISIBLE);
            viewIds[3] = true;
        }
        costCount++;
    }

    public void editWorkEntries(int areaIndex) {

        newCostButton.setVisibility(View.GONE);
        for(int i = 0; i < costBoxes.length; i++) {
            if(viewIds[i]) {
                costBoxes[i].setVisibility(View.VISIBLE);
            }
        }

        mainBox.setVisibility(View.VISIBLE);
        if(areaIndex == 0) { //Hides box for first area since there should always be at least one area and cost item
            mainBox.setVisibility(View.GONE);
        }

        mainBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mainBox.isChecked()) {

                    mainBox.setChecked(true);
                    for(int i = 0; i < costBoxes.length; i++) {
                        costBoxes[i].setChecked(true);
                    }
                } else {

                    mainBox.setChecked(false);
                    for(int i = 0; i < costBoxes.length; i++) {
                        costBoxes[i].setChecked(false);
                    }
                }
            }
        });
    }

    public void cancelWorkEntries() {

        newCostButton.setVisibility(View.VISIBLE);
        mainBox.setVisibility(View.GONE);

        for(int i = 0; i < costBoxes.length; i++) {
            costBoxes[i].setVisibility(View.GONE);
        }
    }

    public void deleteWorkEntries(int num) {

        if(mainBox.isChecked()) {

            ((ViewGroup) newAreaGroup.getParent()).removeView(newAreaGroup);

            for(int i = num; i < stateTrack.length-1; i++) {
                stateTrack[i] = stateTrack[i+1];
            }

            stateTrack[stateTrack.length-1] = null;
            removedArea = true;

            for(int i = stateTrack.length-1; i >= 0; i--) {

                if(stateTrack[i] != null) {
                    previousAreaId = stateTrack[i].getId();
                    i = -1;
                }
            }
        } else {

            for (int i = 0; i < costBoxes.length; i++) {

                if (viewIds[i] && !costBoxes[i].isChecked()) {
                    costStrings.add(costFields[i].getText().toString());
                }

                viewIds[i] = false;
                costBoxes[i].setChecked(false);
            }
            mainBox.setVisibility(View.GONE);

            for (int i = costFields.length - 1; i >= 0; i--) {

                if (i < costStrings.size()) {
                    costFields[i].setText(costStrings.get(i));
                    costBoxes[i].setVisibility(View.GONE);
                    viewIds[i] = true;

                } else {
                    costFields[i].setVisibility(View.GONE);
                    costBoxes[i].setVisibility(View.GONE);
                    costCount--;
                }
            }
            costStrings.clear();
        }
        newCostButton.setVisibility(View.VISIBLE);
    }

    public boolean[] getCostArray() {
        return this.viewIds;
    }

    public int getAreaNameId() {
        return newAreaGroup.findViewById(R.id.areaNameInitial).getId();
    }

    public View getNewAreaGroup() {
        return newAreaGroup;
    }
}