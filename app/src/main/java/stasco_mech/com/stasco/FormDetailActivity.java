package stasco_mech.com.stasco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * An activity representing a single Form detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FormListActivity}.
 */
public class FormDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_detail);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //writeToFile(WorkFormDetailFragment.mState.getString());
                }

        });*/

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

        if (savedInstanceState == null && FormListActivity.itemSelected.equals("3")) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TimeFormFragment.Companion.getARG_ITEM_ID(),
                    getIntent().getStringExtra(TimeFormFragment.Companion.getARG_ITEM_ID()));
            TimeFormFragment fragment = new TimeFormFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.form_detail_container, fragment)
                    .commit();
        } else if (savedInstanceState == null && FormListActivity.itemSelected.equals("1")) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(FormDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(FormDetailFragment.ARG_ITEM_ID));
            FormDetailFragment fragment = new FormDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.form_detail_container, fragment)
                    .commit();
        } else if (savedInstanceState == null && FormListActivity.itemSelected.equals("2")) {
            Bundle arguments = new Bundle();
            arguments.putString(WorkFormDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(WorkFormDetailFragment.ARG_ITEM_ID));
            WorkFormDetailFragment fragment = new WorkFormDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.form_detail_container, fragment)
                    .commit();
        }
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, FormListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.subsYesRadio:
                if (checked)

                    break;
            case R.id.subsNoRadio:
                if (checked)

                    break;
        }

    }

    private void writeToFile(String data) {
        try {
            java.io.OutputStreamWriter outputStreamWriter = new java.io.OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (java.io.IOException e) {
            android.util.Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
