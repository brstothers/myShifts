package comp3350.myshifts.presentation;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.myshifts.R;
import comp3350.myshifts.business.AccessEmployees;
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;

public class ManagerShiftActivity extends AppCompatActivity {

    private int schedID = -1;
    private int empID = -1;

    private AccessShifts accessShifts;
    private AccessEmployees accessEmployees;
    private AccessSchedules accessSchedules;
    private ArrayList<Shift> shiftList;
    private ArrayAdapter<Shift> shiftArrayAdapter;
    private Shift selectedShift;
    private int selectedShiftPosition = -1;
    private Dialog dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);

        //Hide Schedule Button
        Button btnShift = (Button) findViewById(R.id.btnManagerShift);
        btnShift.setVisibility(View.GONE);

        accessShifts = new AccessShifts();
        accessEmployees = new AccessEmployees();
        accessSchedules = new AccessSchedules();

        Bundle b = getIntent().getExtras();
        schedID = b.getInt("EXTRA_SCHEDULE_ID");
        empID = b.getInt("EXTRA_EMPLOYEE_ID");

        shiftList = new ArrayList<>();
        if(schedID != -1)
            shiftList = accessShifts.getShiftsByScheduleID(schedID);
        else
            shiftList = accessShifts.getShiftsByEmployeeID(empID);

        initListViewListener();
    }

    public void initListViewListener(){
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        resetArrayAdapter();
        listView.setAdapter(shiftArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button updateButton = (Button) findViewById(R.id.buttonScheduleUpdate);
                Button deleteButton = (Button) findViewById(R.id.buttonScheduleDelete);
                Button shiftButton = (Button) findViewById(R.id.btnManagerShift);

                if (position == selectedShiftPosition) {
                    listView.setItemChecked(position, false);
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    shiftButton.setEnabled(false);
                    selectedShift = null;
                    selectedShiftPosition = -1;
                } else {
                    listView.setItemChecked(position, true);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    shiftButton.setEnabled(true);
                    selectedShift = shiftList.get(position);
                    selectedShiftPosition = position;
                }
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

                text1.setText(" Schedule "+listItemShift.getScheduleID()+": \t"+schedule);
                text2.setText(" Employee: "+empName+" \t\t\t\t "+listItemShift.getWeekday()+"  Start: "+listItemShift.getStartTime()+"  End: "+listItemShift.getEndTime());

                return view;
            }
        };
    }

    public void buttonScheduleCreateOnClick(View v) {
        createShiftDialog();
    }

    public void buttonScheduleUpdateOnClick(View v) {
        updateShiftDialog();
    }

    public void buttonScheduleDeleteOnClick(View v) {
        deleteShiftDialog();
    }

    public void btnManagerShiftsOnClick(View v) {
        //hidden button- no function needed
    }

    public ArrayList<String> weekdayArrayList(){
        ArrayList<String> weekdays = new ArrayList<>();
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
        weekdays.add("Saturday");
        weekdays.add("Sunday");
        return weekdays;
    }

    public void createShiftDialog(){
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.shift_create_dialog);
        dialogBuilder.show();

        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleShiftDialog);
        title.setText(getResources().getString(R.string.createNewShift));

        //list of schedules
        ArrayList<Schedule> schedList = accessSchedules.getAllSchedules();
        ArrayList<String> scheduleStrings = new ArrayList<>();
        if(schedList != null){
            for(int j=0;j<schedList.size();j++)
                scheduleStrings.add(" "+Integer.toString(schedList.get(j).getSchedID()));
        }
        Spinner schedSpinner = (Spinner) dialogBuilder.findViewById(R.id.scheduleDropDown);
        ArrayAdapter<String> schedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, scheduleStrings);
        schedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedSpinner.setAdapter(schedAdapter);

        //list of employees
        ArrayList<Employee> empList = accessEmployees.getAllEmployees();
        ArrayList<String> employeeStrings = new ArrayList<>();
        if(empList != null){
            for(int j=0;j<empList.size();j++)
                employeeStrings.add(Integer.toString(empList.get(j).getEmployeeID())+" "+empList.get(j).getEmployeeName());
        }
        Spinner empSpinner = (Spinner) dialogBuilder.findViewById(R.id.employeeDropDown);
        ArrayAdapter<String> empAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeStrings);
        empAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empSpinner.setAdapter(empAdapter);

        //lock in value based on previous activity
        Boolean itemFound = false;
        int i=0;
        if(schedID != -1){
            Schedule s = accessSchedules.getScheduleByID(schedID);
            while(i<schedList.size() && !itemFound){
                if(schedList.get(i).getSchedID() == s.getSchedID())
                    itemFound = true;
                else
                    i++;
            }
            schedSpinner.setSelection(i);
            schedSpinner.setEnabled(false);
        }//prev activity from schedule
        else{
            Employee e = accessEmployees.getEmployeeByID(empID);
            while(i<empList.size() && !itemFound){
                if(empList.get(i).getEmployeeID() == e.getEmployeeID())
                    itemFound = true;
                else
                    i++;
            }
            empSpinner.setSelection(i);
            empSpinner.setEnabled(false);
        }//prev activity from employee

        //list of weekdays
        ArrayList<String> weekdayStrings = weekdayArrayList();
        Spinner weekdaySpinner = (Spinner) dialogBuilder.findViewById(R.id.weekDayDropDown);
        ArrayAdapter<String> weekdayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weekdayStrings);
        weekdayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekdaySpinner.setAdapter(weekdayAdapter);


        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.shiftSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.shiftCancelBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set new shift variables
                final Spinner schedDropDown = (Spinner) dialogBuilder.findViewById(R.id.scheduleDropDown);
                final Spinner empDropDown = (Spinner) dialogBuilder.findViewById(R.id.employeeDropDown);
                final Spinner weekdayDropDown = (Spinner) dialogBuilder.findViewById(R.id.weekDayDropDown);
                final EditText editStart =(EditText)dialogBuilder.findViewById(R.id.editStartTimeField);
                final EditText editEnd =(EditText)dialogBuilder.findViewById(R.id.editEndTimeField);

                if(schedDropDown.getSelectedItem() != null &&
                        empDropDown.getSelectedItem() != null &&
                        weekdayDropDown.getSelectedItem() != null &&
                        !editStart.getText().toString().isEmpty() &&
                        !editEnd.getText().toString().isEmpty()) {

                    String[] employeeItem = empDropDown.getSelectedItem().toString().split(" ");
                    Double dStart = Double.parseDouble(editStart.getText().toString().trim());
                    Double dEnd = Double.parseDouble(editEnd.getText().toString().trim());
                    if((dStart < 0.0 || dStart > 24.0) || (dEnd < 0.0 || dEnd > 24.0)) {
                        Toast incorrectTime = Toast.makeText(getApplicationContext(), "Start and End Time Must Be Between 0 and 24.", Toast.LENGTH_LONG);
                        incorrectTime.show();
                    }
                    else if(dStart >= dEnd || dEnd <= dStart){
                        Toast incorrectTime = Toast.makeText(getApplicationContext(), "Start time must be less than End time", Toast.LENGTH_LONG);
                        incorrectTime.show();
                    }
                    else{
                        createInDB(
                                Integer.parseInt(employeeItem[0]),
                                Integer.parseInt(schedDropDown.getSelectedItem().toString().trim()),
                                Weekday.values()[weekdayDropDown.getSelectedItemPosition()],
                                dStart,
                                dEnd
                        );
                        dialogBuilder.dismiss();
                    }//Correct time input
                }//checks if fields are empty
                else{
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Shift not Created, form must be complete.", Toast.LENGTH_SHORT);
                    emptyFormFields.show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
    }

    public void updateShiftDialog(){
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.shift_editor_dialog);
        dialogBuilder.show();

        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleShiftDialog);
        title.setText(getResources().getString(R.string.updateShift));

        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.shiftSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.shiftCancelBtn);

        final EditText editEmployeeName =(EditText)dialogBuilder.findViewById(R.id.editEmployeeNameField);
        AccessEmployees accessEmp = new AccessEmployees();
        Employee tempEmp = accessEmp.getEmployeeByID(selectedShift.getEmployeeID());
        if(tempEmp != null){
            editEmployeeName.setText(tempEmp.getEmployeeName());
        }//if name found
        editEmployeeName.setEnabled(false);

        final EditText editScheduleID =(EditText)dialogBuilder.findViewById(R.id.editScheduleIDField);
        editScheduleID.setText(Integer.toString(selectedShift.getScheduleID()));
        editScheduleID.setEnabled(false);

        final EditText editWeekday =(EditText)dialogBuilder.findViewById(R.id.editWeekdayField);
        editWeekday.setText(selectedShift.getWeekday().toString());
        editWeekday.setEnabled(false);

        final EditText editStart =(EditText)dialogBuilder.findViewById(R.id.editStartTimeField);
        editStart.setText(Double.toString(selectedShift.getStartTime()));

        final EditText editEnd =(EditText)dialogBuilder.findViewById(R.id.editEndTimeField);
        editEnd.setText(Double.toString(selectedShift.getEndTime()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set new shift time variable fields
                final EditText editStart =(EditText)dialogBuilder.findViewById(R.id.editStartTimeField);
                final EditText editEnd =(EditText)dialogBuilder.findViewById(R.id.editEndTimeField);

                if(!editStart.getText().toString().isEmpty() && !editEnd.getText().toString().isEmpty()) {
                    Double dStart = Double.parseDouble(editStart.getText().toString().trim());
                    Double dEnd = Double.parseDouble(editEnd.getText().toString().trim());
                    if((dStart < 0.0 || dStart > 24.0) || (dEnd < 0.0 || dEnd > 24.0)){
                        Toast incorrectTime = Toast.makeText(getApplicationContext(), "Start and End Time Must Be Between 0 and 24.", Toast.LENGTH_LONG);
                        incorrectTime.show();
                    }
                    else if(dStart >= dEnd || dEnd <= dStart){
                        Toast incorrectTime = Toast.makeText(getApplicationContext(), "Start time must be less than End time", Toast.LENGTH_LONG);
                        incorrectTime.show();
                    }
                    else{
                        updateInDB(
                                selectedShift.getEmployeeID(),
                                selectedShift.getScheduleID(),
                                selectedShift.getWeekday(),
                                dStart,
                                dEnd
                        );
                        dialogBuilder.dismiss();
                    }
                }//checks if text fields are within range
                else
                {
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Shift not Updated, form must be complete.", Toast.LENGTH_SHORT);
                    emptyFormFields.show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
    }

    public void deleteShiftDialog(){
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.confirm_dialog);
        dialogBuilder.show();
        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.confirmDialogSubmitBtn);
        btnSubmit.setText(getResources().getString(R.string.delete));
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.confirmDialogCancelBtn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteInDB();
                dialogBuilder.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

    }

    public void createInDB(int eID, int sID, Weekday day, double start,double end) {
        //create shift
        accessShifts.createShift(eID,sID,day,start,end);
        postDBChanges();
    }

    public void updateInDB(int eID, int sID, Weekday day, double start,double end) {
        //update shift times
        accessShifts.updateShiftbyID(eID,sID,day,start,end);
        postDBChanges();
    }

    public void deleteInDB() {
        //find and delete shift in DB
        accessShifts.deleteShiftbyID(selectedShift.getEmployeeID(),selectedShift.getScheduleID(),selectedShift.getWeekday());
        postDBChanges();
    }

    public void postDBChanges(){
        selectedShift = null;
        //update and refresh list.
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        final Button updateButton = (Button) findViewById(R.id.buttonScheduleUpdate);
        final Button deleteButton = (Button) findViewById(R.id.buttonScheduleDelete);
        if(schedID != -1){
            shiftList = accessShifts.getShiftsByScheduleID(schedID);
        }
        else{
            shiftList = accessShifts.getShiftsByEmployeeID(empID);
        }
        resetArrayAdapter();
        listView.setAdapter(shiftArrayAdapter);
        listView.setItemChecked(selectedShiftPosition, false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        selectedShiftPosition = -1;
    }

}
