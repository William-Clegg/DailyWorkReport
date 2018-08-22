package stasco_mech.com.stasco;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import stasco_mech.com.stasco.dummy.DummyContent;

import static stasco_mech.com.stasco.WorkArea.removedArea;

/**
 * A fragment representing a single Form detail screen.
 * This fragment is either contained in a {@link FormListActivity}
 * in two-pane mode (on tablets) or a {@link FormDetailActivity}
 * on handsets.
 */
public class WorkFormDetailFragment extends Fragment {
    private ImageButton addArea;
    private ImageButton selectEntries;
    static int labelAreaId;
    private Button deleteEntries;
    private Button cancelSelection;
    private static Bundle mState = new Bundle();
    static int areaCount = 0;
    ConstraintLayout areaLayout;
    static int areaId = 10;
    static Integer previousAreaId;
    static WorkArea[] stateTrack = new WorkArea[5];
    boolean[] restoreWorkArray = new boolean[5];
    boolean[][] restoreCostArray = new boolean[5][];
    static boolean stopped;
    ConstraintSet mainAreaSet = new ConstraintSet();

    public static final String ARG_ITEM_ID = "item_id";

    private DummyContent.DummyItem mItem;

    public WorkFormDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mState = savedInstanceState;
        previousAreaId = null;
        areaCount = 0;
        areaId = 10;
        if(savedInstanceState != null) {
            restoreWorkArray = (boolean[]) savedInstanceState.getSerializable("stateArray");
            restoreCostArray = (boolean[][]) savedInstanceState.getSerializable("costArray");
        }

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.work_form_detail, container, false);
        labelAreaId = rootView.findViewById(R.id.work_form_detail_header).getId();
        areaLayout = rootView.findViewById(R.id.work_form_detail_fragment_container);

        for(WorkArea state: stateTrack) {
            if(state != null) {
                System.out.println("!!!" + state.areaText.getText().toString());
            }
        }

        addArea = rootView.findViewById(R.id.newAreaButton);
        selectEntries = rootView.findViewById(R.id.removeAreaButton);
        deleteEntries = rootView.findViewById(R.id.deleteEntries);
        cancelSelection = rootView.findViewById(R.id.cancelSelection);

        if (mItem != null) {

            addArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(areaCount < 5) {
                        addNewArea();
                    }
                }
            });

            selectEntries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editEntries();
                }
            });

            cancelSelection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelSelection();
                }
            });

            deleteEntries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEntries();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onResume() {

        if(!restoreWorkArray[0]) {
            addNewArea();
        } else {
            for(int i = 0; i < stateTrack.length; i++) {

                if(restoreWorkArray[i]) {

                    boolean[] costArray = restoreCostArray[i];
                    addNewArea();
                    System.out.println("i currently equals " + i);
                    System.out.println("displaying..." + mState.getString(i + "areaKey"));
                    stateTrack[i].areaText.setText(mState.getString(i + "areaKey"));
                    System.out.println(stateTrack[i].areaText.getText().toString());
                    if(i>0) {
                        System.out.println(stateTrack[i-1].areaText.getText().toString());
                    }
                    stateTrack[i].initialCost.setText(mState.getString(i + "initialCostText"));
                    if(costArray[0]) {
                        stateTrack[i].addNewCost();
                        stateTrack[i].firstCost.setText(mState.getString(i + "firstCostText"));
                    }if(costArray[1]) {
                        stateTrack[i].addNewCost();
                        stateTrack[i].secondCost.setText(mState.getString(i + "secondCostText"));
                    }if(costArray[2]) {
                        stateTrack[i].addNewCost();
                        stateTrack[i].thirdCost.setText(mState.getString(i + "thirdCostText"));
                    }if(costArray[3]) {
                        stateTrack[i].addNewCost();
                        stateTrack[i].fourthCost.setText(mState.getString(i + "fourthCostText"));
                    }
                }
            }
        }

        super.onResume();
    }

    public void editEntries() {

        selectEntries.setVisibility(View.GONE);
        deleteEntries.setVisibility(View.VISIBLE);
        cancelSelection.setVisibility(View.VISIBLE);
        addArea.setVisibility(View.GONE);

        for(int i = 0; i < 5; i++) {
            if(stateTrack[i] != null) {
                stateTrack[i].editWorkEntries(i);
            }
        }
    }

    public void cancelSelection() {

        deleteEntries.setVisibility(View.GONE);
        cancelSelection.setVisibility(View.GONE);
        selectEntries.setVisibility(View.VISIBLE);
        addArea.setVisibility(View.VISIBLE);

        for(int i = 0; i < 5; i++) {
            if(stateTrack[i] != null) {
                stateTrack[i].cancelWorkEntries();
            }
        }
    }

    public void deleteEntries() {

        deleteEntries.setVisibility(View.GONE);
        cancelSelection.setVisibility(View.GONE);
        selectEntries.setVisibility(View.VISIBLE);
        addArea.setVisibility(View.VISIBLE);

        for(int i = 0; i < 5; i++) {
            if(stateTrack[i] != null) {
                stateTrack[i].deleteWorkEntries(i);
                if(removedArea) {
                    if(stateTrack[i] != null) {
                        constrainAreas(i);
                    }
                    areaCount--;
                    i--;
                    removedArea = false;
                }
            }
        }
    }

    public void constrainAreas(int num) {

        if(stateTrack[num] != null) {
            mainAreaSet.clone(areaLayout);
            mainAreaSet.connect(stateTrack[num].getNewAreaGroup().getId(), ConstraintSet.TOP, stateTrack[num - 1].getNewAreaGroup().getId(), ConstraintSet.BOTTOM, 0);
            mainAreaSet.applyTo(areaLayout);
        }
    }

    public void addNewArea() {

        WorkArea newAreaGroup = new WorkArea(getContext(), getActivity(), areaLayout);
        newAreaGroup.setId(areaId);
        stateTrack[areaCount] = newAreaGroup;

        areaCount++;
        areaId++;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        boolean[] tempBooleanArray = new boolean[5];
        boolean[][] tempCostArray = new boolean[5][];
        for(int i = 0; i < stateTrack.length; i++) {
            if(stateTrack[i] != null) {
                tempBooleanArray[i] = true;
                tempCostArray[i] = stateTrack[i].getCostArray();
            }
        }
        outState.putSerializable("stateArray", tempBooleanArray);
        outState.putSerializable("costArray", tempCostArray);

        for(int i = 0; i < stateTrack.length; i++) {
            if(stateTrack[i] != null) {
                boolean[] costArray = stateTrack[i].getCostArray();
                System.out.println("saving...." + stateTrack[i].areaText.getText().toString());
                outState.putString(i + "areaKey", stateTrack[i].areaText.getText().toString());
                outState.putString(i + "initialCostText", stateTrack[i].initialCost.getText().toString());
                if(costArray[0]) {
                    outState.putString(i + "firstCostText", stateTrack[i].firstCost.getText().toString());
                }if(costArray[1]) {
                    outState.putString(i + "secondCostText", stateTrack[i].secondCost.getText().toString());
                }if(costArray[2]) {
                    outState.putString(i + "thirdCostText", stateTrack[i].thirdCost.getText().toString());
                }if(costArray[3]) {
                    outState.putString(i + "fourthCostText", stateTrack[i].fourthCost.getText().toString());
                }
            }
        }

        for(WorkArea state: stateTrack) {
            state = null;
        }

        super.onSaveInstanceState(outState);
    }
}

