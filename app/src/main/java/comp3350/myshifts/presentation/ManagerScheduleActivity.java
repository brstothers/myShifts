package comp3350.myshifts.presentation;

import android.app.Dialog;
import android.content.Intent;
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
import comp3350.myshifts.business.AccessSchedules;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.ScheduleSummary;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.business.Summarize;

public class ManagerScheduleActivity extends AppCompatActivity {

    private AccessSchedules accessSchedules;
    private ArrayList<Schedule> scheduleList;
    private ArrayAdapter<Schedule> scheduleArrayAdapter;
    private Schedule selectedSchedule;
    private int selectedSchedulePosition = -1;
    private Dialog dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_schedule);

        accessSchedules = new AccessSchedules();
        scheduleList = accessSchedules.getAllSchedules();
        Button btnSummary = (Button) findViewById(R.id.btnManagerSummary);
        btnSummary.setVisibility(View.VISIBLE);

        initListViewListener();
    }

    public void initListViewListener(){
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        resetArrayAdapter();
        listView.setAdapter(scheduleArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button updateButton = (Button) findViewById(R.id.buttonScheduleUpdate);
                Button deleteButton = (Button) findViewById(R.id.buttonScheduleDelete);
                Button shiftButton = (Button) findViewById(R.id.btnManagerShift);
                Button summaryButton = (Button) findViewById(R.id.btnManagerSummary);

                if (position == selectedSchedulePosition) {
                    listView.setItemChecked(position, false);
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    shiftButton.setEnabled(false);
                    summaryButton.setEnabled(false);
                    selectedSchedule = null;
                    selectedSchedulePosition = -1;
                } else {
                    listView.setItemChecked(position, true);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    shiftButton.setEnabled(true);
                    summaryButton.setEnabled(true);
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

                text1.setText("Schedule "+listItemSchedule.getSchedID()+": "+listItemSchedule.getWeek());
                text2.setText(listItemSchedule.getMonth()+" "+listItemSchedule.getYear());

                return view;
            }
        };
    }

    public void buttonScheduleCreateOnClick(View v) {
        createScheduleDialog();
    }

    public void buttonScheduleUpdateOnClick(View v) {
        updateScheduleDialog();
    }

    public void buttonScheduleDeleteOnClick(View v) {
        deleteScheduleDialog();
    }

    public void btnManagerShiftsOnClick(View v) {
        Intent shiftIntent = new Intent(getApplicationContext(), ManagerShiftActivity.class);
        shiftIntent.putExtra("EXTRA_SCHEDULE_ID", selectedSchedule.getSchedID());
        shiftIntent.putExtra("EXTRA_EMPLOYEE_ID", -1);
        ManagerScheduleActivity.this.startActivity(shiftIntent);
    }

    public void btnManagerSummaryOnClick(View v){
        Schedule s = selectedSchedule;
        if(s != null) {
            ScheduleSummary schedSummary = Summarize.schedule(s.getSchedID());
            dialogBuilder = new Dialog(this);
            dialogBuilder.setContentView(R.layout.summary_dialog);
            dialogBuilder.show();

            TextView summaryTitle = (TextView) dialogBuilder.findViewById(R.id.titleSummaryDialog);
            summaryTitle.setText("Schedule Summary");

            TextView textName = (TextView) dialogBuilder.findViewById(R.id.textnameField);
            textName.setText(" Schedule ID: "+s.getSchedID());

            TextView textInfo = (TextView) dialogBuilder.findViewById(R.id.textInfoField);
            textInfo.setText(" "+s.getWeek()+", "+s.getMonth()+" "+s.getYear());

            TextView textAmount = (TextView) dialogBuilder.findViewById(R.id.textAmountField);
            textAmount.setText(" Employees On Schedule:  "+schedSummary.getNumEmployees());

            TextView textShiftTotal = (TextView) dialogBuilder.findViewById(R.id.texttotalShiftField);
            textShiftTotal.setText(" Total Shifts:     "+schedSummary.getNumShifts());

            TextView textHourTotal = (TextView) dialogBuilder.findViewById(R.id.textTotalHoursField);
            textHourTotal.setText(" Total Hours:     "+schedSummary.getTotalHours());

            TextView textCostTotal = (TextView) dialogBuilder.findViewById(R.id.textTotalCostField);
            textCostTotal.setText(" Total Payroll:   "+schedSummary.getTotalPayroll());


            Button btnClose = (Button) dialogBuilder.findViewById(R.id.summaryCancelBtn);
            btnClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });
        }
        else{
            Toast scheduleSelectionError = Toast.makeText(getApplicationContext(), "Error Selecting Schedule", Toast.LENGTH_SHORT);
            scheduleSelectionError.show();
        }

    }

    public ArrayList<String> populateMonthList(){
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months;
    }

    public ArrayList<String> populateWeekList(){
        ArrayList<String> weeks = new ArrayList<>();
        weeks.add("Week 1");
        weeks.add("Week 2");
        weeks.add("Week 3");
        weeks.add("Week 4");
        weeks.add("Week 5");
        return weeks;
    }

    private void createScheduleDialog() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.schedule_dialog);
        dialogBuilder.show();

        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleScheduleDialog);
        title.setText(getResources().getString(R.string.createNewSchedule));


        ArrayList<String> weekList = populateWeekList();
        Spinner weekDropDown =(Spinner)dialogBuilder.findViewById(R.id.weekDropDown);

        ArrayAdapter<String> weekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weekList);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekDropDown.setAdapter(weekAdapter);


        ArrayList<String> monthList = populateMonthList();
        Spinner monthDropDown =(Spinner)dialogBuilder.findViewById(R.id.monthDropDown);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthDropDown.setAdapter(monthAdapter);

        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.scheduleSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.scheduleCancelBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set new schedule variable fields
                final Spinner weekDropDown = (Spinner) dialogBuilder.findViewById(R.id.weekDropDown);
                final Spinner monthDropDown = (Spinner) dialogBuilder.findViewById(R.id.monthDropDown);
                final EditText editYear =(EditText)dialogBuilder.findViewById(R.id.editScheduleYear);

                if(!weekDropDown.getSelectedItem().toString().isEmpty() && !monthDropDown.getSelectedItem().toString().isEmpty() && !editYear.getText().toString().isEmpty()) {
                    if(Integer.parseInt(editYear.getText().toString()) >= 2000 && Integer.parseInt(editYear.getText().toString()) <= 2100)
                    {
                        createInDB(
                                weekDropDown.getSelectedItem().toString().trim(),
                                monthDropDown.getSelectedItem().toString().trim(),
                                editYear.getText().toString().trim()
                        );
                    }
                    else
                    {
                        Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Schedule not Created, year must be between 2000 and 2100.", Toast.LENGTH_SHORT);
                        emptyFormFields.show();
                    }
                }//checks if text fields are empty && that the year is between 2000 and 2100
                else
                {
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Schedule not Created, form must be complete.", Toast.LENGTH_SHORT);
                    emptyFormFields.show();
                }
                dialogBuilder.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
    }

    private void updateScheduleDialog() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.schedule_dialog);
        dialogBuilder.show();

        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleScheduleDialog);
        title.setText(getResources().getString(R.string.updateScheduleInfo));


        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.scheduleSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.scheduleCancelBtn);

        //weekDropDown
        ArrayList<String> weekList = populateWeekList();
        Spinner weekDropDown =(Spinner)dialogBuilder.findViewById(R.id.weekDropDown);

        ArrayAdapter<String> weekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weekList);
        weekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekDropDown.setAdapter(weekAdapter);
        for(int i=0;i<weekList.size();i++){
            if(weekList.get(i).equals(selectedSchedule.getWeek()))
                weekDropDown.setSelection(i);
        }

        //monthDropDown
        ArrayList<String> monthList = populateMonthList();
        Spinner monthDropDown =(Spinner)dialogBuilder.findViewById(R.id.monthDropDown);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthDropDown.setAdapter(monthAdapter);
        for(int i=0;i<monthList.size();i++){
            if(monthList.get(i).equals(selectedSchedule.getMonth()))
                monthDropDown.setSelection(i);
        }

        final EditText editYear =(EditText)dialogBuilder.findViewById(R.id.editScheduleYear);
        editYear.setText(selectedSchedule.getYear());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set new schedule variable fields
                final Spinner weekDropDown = (Spinner) dialogBuilder.findViewById(R.id.weekDropDown);
                final Spinner monthDropDown = (Spinner) dialogBuilder.findViewById(R.id.monthDropDown);
                final EditText editYear =(EditText)dialogBuilder.findViewById(R.id.editScheduleYear);

                if(!weekDropDown.getSelectedItem().toString().isEmpty() && !monthDropDown.getSelectedItem().toString().isEmpty() && !editYear.getText().toString().isEmpty()) {
                    if(Integer.parseInt(editYear.getText().toString()) >= 2000 && Integer.parseInt(editYear.getText().toString()) <= 2100)
                    {
                        updateInDB(
                                weekDropDown.getSelectedItem().toString().trim(),
                                monthDropDown.getSelectedItem().toString().trim(),
                                editYear.getText().toString().trim()
                        );
                    }
                    else
                    {
                        Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Schedule not Updated, year must be between 2000 and 2100.", Toast.LENGTH_SHORT);
                        emptyFormFields.show();
                    }
                }//checks if fields are empty
                else
                {
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Schedule not Updated, form must be complete.", Toast.LENGTH_SHORT);
                    emptyFormFields.show();
                }
                dialogBuilder.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
    }

    public void deleteScheduleDialog() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.confirm_dialog);
        dialogBuilder.show();

        Toast shiftCaution = Toast.makeText(getApplicationContext(), "Shifts for this schedule will be removed.", Toast.LENGTH_LONG);
        shiftCaution.setGravity(1,0,400);
        shiftCaution.show();

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

    public void createInDB(String week,String month,String year) {
        //create schedule
        Schedule temp = accessSchedules.createSchedule(week, month,year);

        //update and refresh list
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        if(temp != null) {
            scheduleList = accessSchedules.getAllSchedules();
            resetArrayAdapter();
            listView.setAdapter(scheduleArrayAdapter);
        }//if schedule created
    }

    public void updateInDB(String week,String month,String year) {
        //update schedule
        accessSchedules.updateScheduleByID(selectedSchedule.getSchedID(), week, month, year);
        selectedSchedule = null;

        //update and refresh list
        postDBChange();
    }

    public void deleteInDB() {
        //remove employees shifts
        AccessShifts accShift = new AccessShifts();
        ArrayList<Shift> schedShifts = accShift.getShiftsByScheduleID(selectedSchedule.getSchedID());
        for(int i=0;i<schedShifts.size();i++){
            accShift.deleteShiftbyID(
                    schedShifts.get(i).getEmployeeID(),
                    schedShifts.get(i).getScheduleID(),
                    schedShifts.get(i).getWeekday()
            );
        }//for each shift

        //find and delete schedule in DB
        accessSchedules.deleteScheduleByID(selectedSchedule.getSchedID());
        selectedSchedule = null;

        //update and refresh list.
        postDBChange();
    }

    public void postDBChange(){
        final ListView listView = (ListView) findViewById(R.id.listSchedules);
        final Button updateButton = (Button) findViewById(R.id.buttonScheduleUpdate);
        final Button deleteButton = (Button) findViewById(R.id.buttonScheduleDelete);
        scheduleList = accessSchedules.getAllSchedules();

        resetArrayAdapter();
        listView.setAdapter(scheduleArrayAdapter);
        listView.setItemChecked(selectedSchedulePosition, false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        selectedSchedulePosition = -1;
    }

}
