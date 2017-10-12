package comp3350.myshifts.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import comp3350.myshifts.objects.Employee;

public class EmployeeActivity extends Activity {

    private ArrayList<Employee> employeeList;
    private ArrayAdapter<Employee> employeeArrayAdapter;
    private int selectedStudentPosition = -1;
    private Employee selectedEmployee;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        //Hide Manager Options
        RelativeLayout managerOptions = (RelativeLayout) findViewById(R.id.btnManOptions1);
        managerOptions.setVisibility(View.GONE);

        //Enlarge Schedule Button
        Button btnSchedule = (Button) findViewById(R.id.buttonShift);
        btnSchedule.setHeight(200);
        btnSchedule.setTextSize(25);

        AccessEmployees accessEmployees = new AccessEmployees();
        employeeList = new ArrayList<>();
        employeeList = accessEmployees.getAllEmployees();

        initListViewListener();
    }

    public void initListViewListener(){
        final ListView listView = (ListView) findViewById(R.id.listEmployees);
        resetArrayAdapter();
        listView.setAdapter(employeeArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button shiftButton = (Button) findViewById(R.id.buttonShift);
                if (position == selectedStudentPosition) {
                    listView.setItemChecked(position, false);
                    shiftButton.setEnabled(false);
                    selectedStudentPosition = -1;
                    selectedEmployee = null;
                } else {
                    listView.setItemChecked(position, true);
                    shiftButton.setEnabled(true);
                    selectedStudentPosition = position;
                    selectedEmployee = employeeList.get(position);
                }
            }
        });
    }

    public void resetArrayAdapter() {
        //Creates new ArrayAdapter with updated db
        employeeArrayAdapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, employeeList) {
            public View getView(int position, View convertView, ViewGroup parent) {
                Employee listItemEmployee = employeeList.get(position);
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(" " + listItemEmployee.getEmployeeID() + ": " + listItemEmployee.getEmployeeName() + "\n $: " + listItemEmployee.getEmployeeWage());
                text2.setText(" " + listItemEmployee.getEmployeePhone()+"");

                return view;
            }
        };
    }

    public void buttonShiftsOnClick(View v) {
        Intent shiftIntent = new Intent(EmployeeActivity.this, EmployeeShiftActivity.class);
        shiftIntent.putExtra("EXTRA_SCHEDULE_ID", -1);
        shiftIntent.putExtra("EXTRA_EMPLOYEE_ID", selectedEmployee.getEmployeeID());
        EmployeeActivity.this.startActivity(shiftIntent);
    }

}