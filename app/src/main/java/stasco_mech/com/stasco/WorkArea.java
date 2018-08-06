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

import static android.support.constraint.ConstraintSet.WRAP_CONTENT;
import static stasco_mech.com.stasco.WorkFormDetailFragment.previousAreaId;
import static stasco_mech.com.stasco.WorkFormDetailFragment.stateTrack;

public class WorkArea extends ConstraintLayout {

    public int previousCostId = 1;
    ConstraintLayout areaLayout;
    int[] viewIds = new int[7];
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
        final ImageButton addCost = (ImageButton) newAreaGroup.findViewById(R.id.newCostButton);
        final CheckBox mainCheck = (CheckBox) newAreaGroup.findViewById(R.id.mainCheckBox);
        mainCheck.setVisibility(View.GONE);
        viewIds[0] = newAreaGroup.findViewById(R.id.areaNameInitial).getId();
        viewIds[1] = newAreaGroup.findViewById(R.id.costInitial).getId();
        viewIds[2] = addCost.getId();
        newAreaLayout = (ConstraintLayout) newAreaGroup.findViewById(R.id.areaGroupLayout);
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

        System.out.println(newAreaGroup.getId());

        System.out.println(previousAreaId);
    }

    public void addNewCost() {

        EditText newCostNum = new EditText(getContext());
        newCostNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        newCostNum.setHint(R.string.costHint);
        newCostNum.setGravity(Gravity.CENTER);

        CheckBox newCheckBox = new CheckBox(getContext());

        int costID = -1;
        int checkId = -1;

        for(int i = 3; i < 7; i++) {
            if(viewIds[i] == 0) {

                System.out.println(i);
                costID = i;
                checkId = i + 100;
                viewIds[i] = costID;
                i = 7;
            }
        }

        int topId;
        newCheckBox.setId(checkId);
        newCostNum.setId(costID);
        if(costCount == 0) {
            topId = R.id.costInitial;
        } else {
            topId = previousCostId;
        }

        newAreaLayout.addView(newCostNum);
        newAreaLayout.addView(newCheckBox);
        newCheckBox.setVisibility(View.GONE);
        areaSet.clone(newAreaLayout);
        areaSet.connect(costID, ConstraintSet.TOP, topId, ConstraintSet.BOTTOM, 0);
        areaSet.connect(costID, ConstraintSet.LEFT, R.id.costInitial, ConstraintSet.LEFT, 0);
        areaSet.connect(checkId, ConstraintSet.START, costID, ConstraintSet.END, 75);
        areaSet.connect(checkId, ConstraintSet.TOP, costID, ConstraintSet.TOP, 35);
        areaSet.applyTo(newAreaLayout);

        System.out.println("addcost costid " + costID);

        previousCostId = costID;
        costCount++;
    }

    public void editWorkEntries(int first) {

        newAreaGroup.findViewById(viewIds[2]).setVisibility(View.GONE);

        for (int i = 3; i < 7; i++) {

            if (viewIds[i] != 0) {
                CheckBox select = (CheckBox) newAreaGroup.findViewById(i+100);
                select.setVisibility(View.VISIBLE);
            }
        }

        final CheckBox select = (CheckBox) newAreaGroup.findViewById(R.id.mainCheckBox);
        select.setVisibility(View.VISIBLE);
        if(first == 0) {
            select.setVisibility(View.GONE);
        }

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (select.isChecked()) {
                    System.out.println("HELLO");
                    select.setChecked(true);

                    for (int i = 3; i < 7; i++) {

                        if(viewIds[i] != 0) {

                            CheckBox subBox = (CheckBox) newAreaGroup.findViewById(viewIds[i]+100);
                            subBox.setChecked(true);
                        }
                    }
                } else {

                    select.setChecked(false);

                    for (int i = 3; i < 7; i++) {

                        if (viewIds[i] != 0) {

                            CheckBox subBox = (CheckBox) newAreaGroup.findViewById(viewIds[i]+100);
                            subBox.setChecked(false);
                        }
                    }
                }
            }
        });
    }

    public void cancelWorkEntries() {

        newAreaGroup.findViewById(viewIds[2]).setVisibility(View.VISIBLE);

        for (int i = 3; i < 7; i++) {

            if (viewIds[i] != 0) {
                CheckBox select = (CheckBox) newAreaGroup.findViewById(i+100);
                select.setVisibility(View.GONE);
            }
        }

        CheckBox select = (CheckBox) newAreaGroup.findViewById(R.id.mainCheckBox);
        select.setVisibility(View.GONE);
    }

    public void deleteWorkEntries(int num) {

        CheckBox select = (CheckBox) newAreaGroup.findViewById(R.id.mainCheckBox);
        select.setVisibility(View.GONE);
        if(!select.isChecked()) {
            for (int i = 3; i < 7; i++) {

                if (viewIds[i] != 0) {
                    CheckBox selectBox = (CheckBox) newAreaGroup.findViewById(i + 100);
                    selectBox.setVisibility(View.GONE);
                    if(selectBox.isChecked()) {

                        EditText costItem = (EditText) newAreaGroup.findViewById(i);

                        newAreaLayout.removeView(costItem);
                        newAreaLayout.removeView(selectBox);
                        ((ViewManager)newAreaGroup.getParent()).removeView(costItem);
                        ((ViewManager)newAreaGroup.getParent()).removeView(selectBox);

                        for(int j = i; j < 6; j++) {

                            if(viewIds[j+1] != 0) {
                                EditText nextCost = (EditText) newAreaGroup.findViewById(j + 1);
                                CheckBox nextBox = (CheckBox) newAreaGroup.findViewById(j + 101);
                                nextCost.setId(j);
                                nextBox.setId(j + 100);
                                nextBox.setVisibility(View.GONE);
                            }
                            viewIds[j] = viewIds[j + 1];
                        }
                        viewIds[6] = 0;

                        if(i != 3) {
                            areaSet.clone(newAreaLayout);
                            areaSet.connect(i, ConstraintSet.TOP, i - 1, ConstraintSet.BOTTOM, 0);
                            areaSet.connect(i, ConstraintSet.LEFT, R.id.costInitial, ConstraintSet.LEFT, 0);
                            areaSet.applyTo(newAreaLayout);
                        } else {
                            areaSet.clone(newAreaLayout);
                            areaSet.connect(i, ConstraintSet.TOP, R.id.costInitial, ConstraintSet.BOTTOM, 0);
                            areaSet.connect(i, ConstraintSet.LEFT, R.id.costInitial, ConstraintSet.LEFT, 0);
                            areaSet.applyTo(newAreaLayout);
                        }
                        costCount--;
                    }


                }
            }
        } else {

            newAreaLayout.removeView(newAreaGroup);
            ((ViewManager)newAreaGroup.getParent()).removeView(newAreaGroup);

            for(int i = num; i < 4; i++) {
                stateTrack[i] = stateTrack[i+1];
            }
            stateTrack[4] = null;

            removedArea = true;

        }

        newAreaGroup.findViewById(viewIds[2]).setVisibility(View.VISIBLE);
    }

    public int[] getCostArray() {

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