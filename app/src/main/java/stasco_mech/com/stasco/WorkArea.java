package stasco_mech.com.stasco;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Arrays;

import static android.support.constraint.ConstraintSet.WRAP_CONTENT;
import static stasco_mech.com.stasco.WorkFormDetailFragment.previousAreaId;
import static stasco_mech.com.stasco.WorkFormDetailFragment.stateTrack;

public class WorkArea extends ConstraintLayout {

    public int previousCostId = 1;
    ConstraintLayout areaLayout;
    boolean[] viewIds = new boolean[4];
    public int costCount = 0;
    final ConstraintSet areaSet = new ConstraintSet();
    ConstraintLayout newAreaLayout;
    View newAreaGroup;
    public static boolean removedArea;

    private LayoutInflater inflater;

    public WorkArea(Context context) {
        super(context);
        initView(context);
    }

    public WorkArea(Context context, ConstraintLayout areaLayout) {
        super(context);
        initView(context, areaLayout);
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

    private void initView(Context context, final ConstraintLayout areaLayout){

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        newAreaGroup = inflater.inflate(R.layout.area_group, areaLayout, false);
        final ImageButton addCost = newAreaGroup.findViewById(R.id.newCostButton);
        final CheckBox mainCheck = newAreaGroup.findViewById(R.id.mainCheckBox);
        mainCheck.setVisibility(View.GONE);
        newAreaLayout = newAreaGroup.findViewById(R.id.areaGroupLayout);
        newAreaLayout.setId(WorkFormDetailFragment.areaId);

        ConstraintLayout.LayoutParams areaLayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT);
        if(previousAreaId == null) {
            System.out.println("LABEL AREA ID " + WorkFormDetailFragment.labelAreaId);
            areaLayoutParams.topToBottom = WorkFormDetailFragment.labelAreaId;
        } else {
            areaLayoutParams.topToBottom = previousAreaId;
        }
        newAreaLayout.setLayoutParams(areaLayoutParams);

        previousAreaId = WorkFormDetailFragment.areaId;

        addCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(costCount < 4) {
                    addNewCost();
                }
            }
        });
        areaLayout.addView(newAreaLayout, areaLayoutParams);
    }

    public void addNewCost() {

        EditText newCostNum;
        if(!viewIds[0]) {
            newCostNum = newAreaGroup.findViewById(R.id.firstCost);
            newCostNum.setVisibility(View.VISIBLE);
            viewIds[0] = true;
        } else if(!viewIds[1]) {
            newCostNum = newAreaGroup.findViewById(R.id.secondCost);
            newCostNum.setVisibility(View.VISIBLE);
            viewIds[1] = true;
        } else if(!viewIds[2]) {
            newCostNum = newAreaGroup.findViewById(R.id.thirdCost);
            newCostNum.setVisibility(View.VISIBLE);
            viewIds[2] = true;
        } else if(!viewIds[3]) {
            newCostNum = newAreaGroup.findViewById(R.id.fourthCost);
            newCostNum.setVisibility(View.VISIBLE);
            viewIds[3] = true;
        }
        costCount++;
    }


    public void editWorkEntries(int first) {

        newAreaGroup.findViewById(R.id.newCostButton).setVisibility(View.GONE);

        CheckBox checkBox;
        if(viewIds[0]) {
            checkBox = newAreaGroup.findViewById(R.id.firstBox);
            checkBox.setVisibility(View.VISIBLE);
        } if(viewIds[1]) {
            checkBox = newAreaGroup.findViewById(R.id.secondBox);
            checkBox.setVisibility(View.VISIBLE);
        } if(viewIds[2]) {
            checkBox = newAreaGroup.findViewById(R.id.thirdBox);
            checkBox.setVisibility(View.VISIBLE);
        } if(viewIds[3]) {
            checkBox = newAreaGroup.findViewById(R.id.fourthBox);
            checkBox.setVisibility(View.VISIBLE);
        }

        final CheckBox select = newAreaGroup.findViewById(R.id.mainCheckBox);
        select.setVisibility(View.VISIBLE);
        if(first == 0) {
            select.setVisibility(View.GONE);
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (select.isChecked()) {

                    select.setChecked(true);
                    CheckBox subBox;
                    if(viewIds[0]) {
                        subBox = newAreaGroup.findViewById(R.id.firstBox);
                        subBox.setChecked(true);
                    } if(viewIds[1]) {
                        subBox = newAreaGroup.findViewById(R.id.secondBox);
                        subBox.setChecked(true);
                    } if(viewIds[2]) {
                        subBox = newAreaGroup.findViewById(R.id.thirdBox);
                        subBox.setChecked(true);
                    } if(viewIds[3]) {
                        subBox = newAreaGroup.findViewById(R.id.fourthBox);
                        subBox.setChecked(true);
                    }

                } else {

                    select.setChecked(false);

                    CheckBox subBox;
                    if(viewIds[0]) {
                        subBox = newAreaGroup.findViewById(R.id.firstBox);
                        subBox.setChecked(false);
                    } if(viewIds[1]) {
                        subBox = newAreaGroup.findViewById(R.id.secondBox);
                        subBox.setChecked(false);
                    } if(viewIds[2]) {
                        subBox = newAreaGroup.findViewById(R.id.thirdBox);
                        subBox.setChecked(false);
                    } if(viewIds[3]) {
                        subBox = newAreaGroup.findViewById(R.id.fourthBox);
                        subBox.setChecked(false);
                    }
                }
            }
        });
    }

    public void cancelWorkEntries() {

        newAreaGroup.findViewById(R.id.newCostButton).setVisibility(View.VISIBLE);

        CheckBox subBox;
        if(viewIds[0]) {
            subBox = newAreaGroup.findViewById(R.id.firstBox);
            subBox.setVisibility(View.GONE);
        } if(viewIds[1]) {
            subBox = newAreaGroup.findViewById(R.id.secondBox);
            subBox.setVisibility(View.GONE);
        } if(viewIds[2]) {
            subBox = newAreaGroup.findViewById(R.id.thirdBox);
            subBox.setVisibility(View.GONE);
        } if(viewIds[3]) {
            subBox = newAreaGroup.findViewById(R.id.fourthBox);
            subBox.setVisibility(View.GONE);
        }

        CheckBox select = newAreaGroup.findViewById(R.id.mainCheckBox);
        select.setVisibility(View.GONE);
    }

    public void deleteWorkEntries(int num) {

        if(viewIds[0]) {

        }
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

    public void saveState() {

    }
}