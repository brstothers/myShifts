package comp3350.myshifts.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comp3350.myshifts.R;
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.objects.Schedule;

public class EmployeeScheduleActivity extends AppCompatActivity {

    private ArrayList<Schedule> scheduleList;
    private ArrayAdapter<Schedule> scheduleArrayAdapter;
    private Schedule selectedSchedule;
    private int selectedSchedulePosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);

        //Hide Manager Options
        RelativeLayout managerOptions = (RelativeLayout) findViewById(R.id.btnManagerScheduleOptions);
        managerOptions.setVisibility(View.GONE);

        //Enlarge Schedule Button
        Button btnShift = (Button) findViewById(R.id.btnManagerShift);
        btnShift.setHeight(200);
        btnShift.setTextSize(25);

        AccessSchedules accessSchedules = new AccessSchedules();
        scheduleList = new ArrayList<>();
        scheduleList = accessSchedules.getAllSchedules();

        initListViewListener();
    }

    public void initListViewListener(){
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        resetArrayAdapter();
        listView.setAdapter(scheduleArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button shiftButton = (Button) findViewById(R.id.btnManagerShift);
                if (position == selectedSchedulePosition) {
                    listView.setItemChecked(position, false);
                    shiftButton.setEnabled(false);
                    selectedSchedule = null;
                    selectedSchedulePosition = -1;
                } else {
                    listView.setItemChecked(position, true);
                    shiftButton.setEnabled(true);
                    selectedSchedule = scheduleList.get(position);
                    selectedSchedulePosition = position;
                }
            }
        });
    }

    public void resetArrayAdapter() {
        //Creates new ArrayAdapter with updated db
        scheduleArrayAdapter = new ArrayAdapter<Schedule>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, scheduleList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Schedule listItemSchedule = scheduleList.get(position);
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(" "+listItemSchedule.getSchedID()+": "+listItemSchedule.getWeek());// + listItemSchedule.getEmployeeID() + ": " + listItemSchedule.getEmployeeName() + "\n $: " + listItemSchedule.getEmployeeWage());
                text2.setText(listItemSchedule.getMonth()+" "+listItemSchedule.getYear());

                return view;
            }
        };
    }

    public void btnManagerShiftsOnClick(View v) {
        Intent shiftIntent = new Intent(getApplicationContext(), EmployeeShiftActivity.class);
        shiftIntent.putExtra("EXTRA_SCHEDULE_ID", selectedSchedule.getSchedID());
        shiftIntent.putExtra("EXTRA_EMPLOYEE_ID", -1);
        EmployeeScheduleActivity.this.startActivity(shiftIntent);
    }

}
