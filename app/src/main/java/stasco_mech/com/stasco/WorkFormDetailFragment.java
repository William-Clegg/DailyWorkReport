package stasco_mech.com.stasco;

import android.app.Activity;
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
    public ImageButton addArea;
    public ImageButton selectEntries;
    public static int labelAreaId;
    public Button deleteEntries;
    public Button cancelSelection;
    public static Bundle mState = new Bundle();
    public static int areaCount = 0;
    ConstraintLayout areaLayout;
    public static int areaId = 10;
    public static Integer previousAreaId;
    public static WorkArea[] stateTrack = new WorkArea[5];
    public static boolean stopped;
    ConstraintSet mainAreaSet = new ConstraintSet();

    public static final String ARG_ITEM_ID = "item_id";

    private DummyContent.DummyItem mItem;

    public WorkFormDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previousAreaId = null;
        areaCount = 0;
        System.out.println("AREA COUNT JUST GOT ZERO");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.work_form_detail, container, false);
        labelAreaId = rootView.findViewById(R.id.work_form_detail_header).getId();
        areaLayout = (ConstraintLayout) rootView.findViewById(R.id.work_form_detail_fragment_container);

        if(!stopped) {
            addNewArea();
        }

        addArea = (ImageButton) rootView.findViewById(R.id.newAreaButton);
        selectEntries = (ImageButton) rootView.findViewById(R.id.removeAreaButton);
        deleteEntries = (Button) rootView.findViewById(R.id.deleteEntries);
        cancelSelection = (Button) rootView.findViewById(R.id.cancelSelection);

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

        /*
        if(stopped) {
            for(int i = 0; i < 5; i++) {

                if(stateTrack[i] != null) {
                    int[] arr = stateTrack[i].getCostArray();

                    addNewArea();

                    EditText areaName = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[0]);
                    areaName.setText(mState.getString("field" + i + 0));

                    EditText areaCost = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[1]);
                    areaCost.setText(mState.getString("field" + i + 1));

                    for (int j = 3; j < arr.length; j++) {
                        if(arr[j] != 0) {

                            stateTrack[i].addNewCost();
                            EditText addedCost = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[j]);
                            addedCost.setText(mState.getString("field" + i + j));
                        }
                    }
                }
            }
        }*/
        return rootView;
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

        WorkArea newAreaGroup = new WorkArea(getContext(), areaLayout);
        newAreaGroup.setId(areaId);
        stateTrack[areaCount] = newAreaGroup;

        areaCount++;
        areaId++;
    }
/*
    @Override
    public void onStop() {

        stopped = true;
        for(int i = 0; i < 5; i++) {
            if(stateTrack[i] != null) {
                int[] arr = stateTrack[i].getCostArray();

                EditText areaName = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[0]);
                mState.putString("field" + i + 0, areaName.getText().toString());

                EditText areaCost = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[1]);
                mState.putString("field" + i + 1, areaCost.getText().toString());

                for(int j = 3; j < arr.length; j++) {
                    if(arr[j] != 0) {

                        EditText costNum = (EditText) stateTrack[i].getNewAreaGroup().findViewById(arr[j]);
                        mState.putString("field" + i + j, costNum.getText().toString());
                    }
                }
            }
        }
        super.onStop();
    }*/
}

