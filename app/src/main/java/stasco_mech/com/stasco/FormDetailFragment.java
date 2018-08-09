package stasco_mech.com.stasco;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import stasco_mech.com.stasco.dummy.DummyContent;

/**
 * A fragment representing a single Form detail screen.
 * This fragment is either contained in a {@link FormListActivity}
 * in two-pane mode (on tablets) or a {@link FormDetailActivity}
 * on handsets.
 */
public class FormDetailFragment extends Fragment {
    Spinner jobSpinner;
    Spinner subSpinner;
    TextView mDateDisplay;
    Button mPickDate;
    TextView selectedTemp;
    EditText numPlumbersText;
    EditText numApprenticesText;
    EditText numLaborersText;
    EditText unlistedSubText;
    RadioGroup subRadio;
    RadioButton subYes;
    RadioButton subNo;
    RadioGroup rainRadio;
    RadioButton noRain;
    RadioButton lightRain;
    RadioButton heavyRain;
    SeekBar seekBar;
    public static boolean stopped;
    public static Bundle mState = new Bundle();

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FormDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.form_detail, container, false);

        mDateDisplay = (TextView) rootView.findViewById(R.id.showMyDate);

        //fName1 = (TextView) rootView.findViewById(R.id.fName);
        numPlumbersText = (EditText) rootView.findViewById(R.id.numPlumbersField);
        numPlumbersText.setGravity(Gravity.CENTER);
        numApprenticesText = (EditText) rootView.findViewById(R.id.numApprenticesField);
        numApprenticesText.setGravity(Gravity.CENTER);
        numLaborersText = (EditText) rootView.findViewById(R.id.numLaborersField);
        numLaborersText.setGravity(Gravity.CENTER);
        unlistedSubText = (EditText) rootView.findViewById(R.id.subNameField);
        unlistedSubText.setGravity(Gravity.CENTER);
        subRadio = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        subYes = (RadioButton) rootView.findViewById(R.id.subsYesRadio);
        subNo = (RadioButton) rootView.findViewById(R.id.subsNoRadio);
        rainRadio = (RadioGroup) rootView.findViewById(R.id.rainRadioGroup);
        noRain = (RadioButton) rootView.findViewById(R.id.noRainRadio);
        lightRain = (RadioButton) rootView.findViewById(R.id.lightRainRadio);
        heavyRain = (RadioButton) rootView.findViewById(R.id.heavyRainRadio);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {

            jobSpinner = (Spinner) rootView.findViewById(R.id.job_spinner);
            ArrayAdapter<CharSequence> jobAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.job_array, android.R.layout.simple_spinner_item);
            jobAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            jobSpinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(jobAdapter, R.layout.spinner_not_selected, this.getActivity()));

            jobSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView mTextView = (TextView) rootView.findViewById(R.id.jobSpinnerText);
                    mTextView.setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    // sometimes you need nothing here
                }
            });

            subSpinner = (Spinner) rootView.findViewById(R.id.sub_spinner);
            ArrayAdapter<CharSequence> subAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sub_array, android.R.layout.simple_spinner_item);
            subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //spinner.setPrompt("Select your favorite Planet!");
            subSpinner.setAdapter(
                    new NothingSelectedSpinnerAdapter(subAdapter, R.layout.sub_spinner_not_selected, this.getActivity()));

            subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TextView mTextView = (TextView) rootView.findViewById(R.id.subSpinnerText);
                    mTextView.setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    // sometimes you need nothing here
                }
            });


            mPickDate = (Button) rootView.findViewById(R.id.myDatePickerButton);

            mPickDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker();
                }
            });

            int step = 5;
            int max = 100;
            int min = 20;

            seekBar = rootView.findViewById(R.id.seekBar);
            seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
            int progress = ((seekBar.getProgress() / 100) * 80) + 20;
            selectedTemp = rootView.findViewById(R.id.selectedTempLabel);
            selectedTemp.setText(progress + "\u00B0 F");
            seekBar.setMax( (max - min) / step );

        }

        System.out.println("ABOUT TO GO IN onCreateView");
        if(stopped) {
            mDateDisplay.setText(mState.getString("date"));
            if(mState.getString("jobName") != null) {
                String[] jobs;
                jobs = getResources().getStringArray(R.array.job_array);
                int num = 0;
                while (!jobs[num].equals(mState.getString("jobName"))) {
                    num++;
                }
                System.out.println("This is num for jobs " + num);
                jobSpinner.setSelection(num + 1);
            }
            numPlumbersText.setText(mState.getString("numPlumbers"));
            numApprenticesText.setText(mState.getString("numApprentices"));
            numLaborersText.setText(mState.getString("numLaborers"));
            if(mState.getString("subsYesOrNo") != null) {
                if (mState.getString("subsYesOrNo").equals("Yes")) {
                    subYes.setChecked(true);
                } else if (mState.getString("subsYesOrNo").equals("No")) {
                    subNo.setChecked(true);
                }
            }
            if(mState.getString("subName") != null) {
                String[] subs;
                subs = getResources().getStringArray(R.array.sub_array);
                int num2 = 0;
                while (!subs[num2].equals(mState.getString("subName"))) {
                    System.out.println(subs[num2]);
                    num2++;
                }
                subSpinner.setSelection(num2 + 1);
                if (mState.getString("subName").equals("Other")) {
                    unlistedSubText.setText(mState.getString("otherSubName"));
                }
            }
            selectedTemp.setText(mState.getString("AvgTemp"));
            seekBar.setProgress(mState.getInt("seekBar"));

            if(mState.getString("rainType") != null) {
                if (mState.getString("rainType").equals("Clear")) {
                    noRain.setChecked(true);
                } else if (mState.getString("rainType").equals("Light Rain")) {
                    lightRain.setChecked(true);
                } else if (mState.getString("rainType").equals("Heavy Rain")) {
                    heavyRain.setChecked(true);
                }
            }
            System.out.println("INSIDE OF onCreateView");
            stopped = false;
        }

        return rootView;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        int step = 5;
        int min = 20;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            selectedTemp.setText(min + (progress * step) + "\u00B0 F");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /*
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /*
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            mDateDisplay = (TextView) getActivity().findViewById(R.id.showMyDate);
            mDateDisplay.setText( String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth)
                    + "-" + String.valueOf(year));
        }
    };

    @Override
    public void onStop() {

        stopped = true;

        mState.putString("jobName", (String)jobSpinner.getSelectedItem());
        mState.putString("date", mDateDisplay.getText().toString());
        mState.putString("numPlumbers", numPlumbersText.getText().toString());
        mState.putString("numApprentices", numApprenticesText.getText().toString());
        mState.putString("numLaborers", numLaborersText.getText().toString());
        /*mState.putString("totalMen", ((Integer.parseInt(fragment.numPlumbersText.getText().toString()))
                + (Integer.parseInt(fragment.numApprenticesText.getText().toString()))
                + (Integer.parseInt(fragment.numLaborersText.getText().toString()))) + ""); */
        if(subRadio.getCheckedRadioButtonId() == R.id.subsYesRadio) {

            mState.putString("subsYesOrNo", "Yes");
        } else if(subRadio.getCheckedRadioButtonId() == R.id.subsYesRadio){

            mState.putString("subsYesOrNo", "No");
        } else {

            mState.putString("subsYesOrNo", "");
        }

        mState.putString("subName", (String)subSpinner.getSelectedItem());

        if(subSpinner.getSelectedItem() != null) {

            if (subSpinner.getSelectedItem().toString().equals("Other")) {

                mState.putString("otherSubName", unlistedSubText.getText().toString());
            } else {

                mState.putString("otherSubName", "");
            }
        }
        mState.putString("AvgTemp", selectedTemp.getText().toString());

        mState.putInt("seekBar", seekBar.getProgress());

        if(rainRadio.getCheckedRadioButtonId() == R.id.noRainRadio) {

            mState.putString("rainType", "Clear");

        } else if(rainRadio.getCheckedRadioButtonId() == R.id.lightRainRadio) {

            mState.putString("rainType", "Light Rain");
        } else if(rainRadio.getCheckedRadioButtonId() == R.id.heavyRainRadio){

            mState.putString("rainType", "Heavy Rain");
        } else {

            mState.putString("rainType", "");
        }

        super.onStop();
    }
}