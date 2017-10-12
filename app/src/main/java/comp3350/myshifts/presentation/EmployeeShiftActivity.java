package comp3350.myshifts.presentation;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
import comp3350.myshifts.business.AccessEmployees;
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.objects.Shift;

public class EmployeeShiftActivity extends AppCompatActivity {
    private ArrayList<Shift> shiftList;
    private ArrayAdapter<Shift> shiftArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);

        //Hide Schedule Button
        Button btnShift = (Button) findViewById(R.id.btnManagerShift);
        btnShift.setVisibility(View.GONE);

        //Hide Manager Layout Buttons
        RelativeLayout btnManOptions = (RelativeLayout) findViewById(R.id.btnManagerScheduleOptions);
        btnManOptions.setVisibility(View.GONE);

        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        AccessShifts accessShifts = new AccessShifts();

        Bundle b = getIntent().getExtras();
        int schedID = b.getInt("EXTRA_SCHEDULE_ID");
        int empID = b.getInt("EXTRA_EMPLOYEE_ID");


        shiftList = new ArrayList<>();
        if(schedID != -1){ shiftList = accessShifts.getShiftsByScheduleID(schedID);}
        else{ shiftList = accessShifts.getShiftsByEmployeeID(empID);}

        resetArrayAdapter();
        listView.setAdapter(shiftArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do nothing on click
                listView.setItemChecked(position,false);
            }
        });
    }

    public void resetArrayAdapter() {
        //Creates new ArrayAdapter with updated db
        shiftArrayAdapter = new ArrayAdapter<Shift>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, shiftList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Shift listItemShift = shiftList.get(position);

                AccessSchedules accessSched = new AccessSchedules();
                String schedule =
                        accessSched.getScheduleByID(listItemShift.getScheduleID()).getWeek()+",\t "
                                +accessSched.getScheduleByID(listItemShift.getScheduleID()).getMonth()+" "
                                +accessSched.getScheduleByID(listItemShift.getScheduleID()).getYear()
                        ;

                AccessEmployees accessEmp = new AccessEmployees();
                String empName = accessEmp.getEmployeeByID(listItemShift.getEmployeeID()).getEmployeeName();

                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(" Schedule: \t"+schedule+"");
                text2.setText(" Employee: "+empName+" \t\t\t\t "+listItemShift.getWeekday()+"  Start: "+listItemShift.getStartTime()+"  End: "+listItemShift.getEndTime());

                return view;
            }
        };
    }

}
