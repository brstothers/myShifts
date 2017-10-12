package comp3350.myshifts.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import comp3350.myshifts.R;
import comp3350.myshifts.business.AccessEmployees;
import comp3350.myshifts.business.AccessShifts;
import comp3350.myshifts.business.Summarize;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.EmployeeSummary;
import comp3350.myshifts.objects.Shift;

public class ManagerActivity extends Activity {

    private AccessEmployees accessEmployees;
    private ArrayList<Employee> employeeList;
    private ArrayAdapter<Employee> employeeArrayAdapter;
    private Employee selectedEmployee;
    private int selectedEmployeePosition = -1;
    private Dialog dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        accessEmployees = new AccessEmployees();
        employeeList = accessEmployees.getAllEmployees();
        Button btnSummary = (Button) findViewById(R.id.btnManagerSummary);
        btnSummary.setVisibility(View.VISIBLE);

        initListViewListener();
    }

    public void initListViewListener(){
        final ListView listView = (ListView) findViewById(R.id.listEmployees);
        resetArrayAdapter();
        listView.setAdapter(employeeArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button updateButton = (Button) findViewById(R.id.buttonEmployeeUpdate);
                Button deleteButton = (Button) findViewById(R.id.buttonEmployeeDelete);
                Button scheduleButton = (Button) findViewById(R.id.buttonShift);
                Button summaryButton = (Button) findViewById(R.id.btnManagerSummary);

                if (position == selectedEmployeePosition) {
                    listView.setItemChecked(position, false);
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                    scheduleButton.setEnabled(false);
                    summaryButton.setEnabled(false);
                    selectedEmployee = null;
                    selectedEmployeePosition = -1;
                } else {
                    listView.setItemChecked(position, true);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    scheduleButton.setEnabled(true);
                    summaryButton.setEnabled(true);
                    selectedEmployee = employeeList.get(position);
                    selectedEmployeePosition = position;
                }
            }
        });
    }

    public void buttonShiftsOnClick(View v) {
        Intent shiftIntent = new Intent(ManagerActivity.this, ManagerShiftActivity.class);
        shiftIntent.putExtra("EXTRA_SCHEDULE_ID", -1);
        shiftIntent.putExtra("EXTRA_EMPLOYEE_ID", selectedEmployee.getEmployeeID());

        ManagerActivity.this.startActivity(shiftIntent);
    }

    public void buttonCreateOnClick(View v) {
        createEmployeeDialog();
    }

    public void buttonUpdateOnClick(View v) {
        updateEmployeeDialog();
    }

    public void buttonDeleteOnClick(View v) {
        deleteEmployeePrompt();
    }

    public void btnManagerSummaryOnClick(View v){
        Employee e = selectedEmployee;
        if(e != null) {
            EmployeeSummary employSummary = Summarize.employee(e.getEmployeeID());
            dialogBuilder = new Dialog(this);
            dialogBuilder.setContentView(R.layout.summary_dialog);
            dialogBuilder.show();

            TextView summaryTitle = (TextView) dialogBuilder.findViewById(R.id.titleSummaryDialog);
            summaryTitle.setText("Employee Summary");

            TextView textName = (TextView) dialogBuilder.findViewById(R.id.textnameField);
            textName.setText(" "+e.getEmployeeName());

            TextView textInfo = (TextView) dialogBuilder.findViewById(R.id.textInfoField);
            textInfo.setText(" ID: "+e.getEmployeeID()+"    Wage: "+e.getEmployeeWage());

            TextView textAmount = (TextView) dialogBuilder.findViewById(R.id.textAmountField);
            textAmount.setText(" Included In "+employSummary.getNumScheds()+" Schedule(s)");

            TextView textShiftTotal = (TextView) dialogBuilder.findViewById(R.id.texttotalShiftField);
            textShiftTotal.setText(" Total Shifts:     "+employSummary.getNumShifts());

            TextView textHourTotal = (TextView) dialogBuilder.findViewById(R.id.textTotalHoursField);
            textHourTotal.setText(" Total Hours:     "+employSummary.getTotalHours());

            TextView textCostTotal = (TextView) dialogBuilder.findViewById(R.id.textTotalCostField);
            textCostTotal.setText(" Total Payroll:   "+employSummary.getTotalPay());


            Button btnClose = (Button) dialogBuilder.findViewById(R.id.summaryCancelBtn);
            btnClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            });
        }
        else{
            Toast employeeSelectionError = Toast.makeText(getApplicationContext(), "Error Selecting Employee", Toast.LENGTH_SHORT);
            employeeSelectionError.show();
        }
    }

    public void resetArrayAdapter() {
        //Creates new ArrayAdapter with updated db
        employeeArrayAdapter = new ArrayAdapter<Employee>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, employeeList) {
            @Override
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

    private void createEmployeeDialog() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.create_dialog);
        dialogBuilder.show();

        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleCreateDialog);
        title.setText(getResources().getString(R.string.createNewEmployee));
        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.dialogSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.dialogCancelBtn);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set new employee variable fields
                //final EditText editId = new EditText(this);
                final EditText editName =(EditText)dialogBuilder.findViewById(R.id.editEmployeeName);
                final EditText editPhone =(EditText)dialogBuilder.findViewById(R.id.editEmployeePhone);
                final EditText editWage =(EditText)dialogBuilder.findViewById(R.id.editEmployeeWage);

                if(!editName.getText().toString().isEmpty() && !editPhone.getText().toString().isEmpty() && !editWage.getText().toString().isEmpty() && editPhone.getText().toString().length() == 10 ) {
                    createInDB(
                            editName.getText().toString().trim(),
                            editPhone.getText().toString().trim(),
                            Double.parseDouble(editWage.getText().toString().trim())
                    );
                }//checks if textfields are empty & that phone number is of correct length (10 characters long)
                else
                {
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Employee not Created, form must be complete.", Toast.LENGTH_SHORT);
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

    private void updateEmployeeDialog() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.create_dialog);
        dialogBuilder.show();

        //get globally stored employee and load values into text fields
        TextView title = (TextView) dialogBuilder.findViewById(R.id.titleCreateDialog);
        title.setText(getResources().getString(R.string.updateEmployee));
        Button btnSubmit = (Button) dialogBuilder.findViewById(R.id.dialogSubmitBtn);
        Button btnCancel = (Button) dialogBuilder.findViewById(R.id.dialogCancelBtn);

        //Set dialog fields from selected
        final EditText editName =(EditText)dialogBuilder.findViewById(R.id.editEmployeeName);
        editName.setText(selectedEmployee.getEmployeeName());
        final EditText editPhone =(EditText)dialogBuilder.findViewById(R.id.editEmployeePhone);
        editPhone.setText(selectedEmployee.getEmployeePhone());
        final EditText editWage =(EditText)dialogBuilder.findViewById(R.id.editEmployeeWage);
        editWage.setText(String.valueOf(selectedEmployee.getEmployeeWage()));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editName.getText().toString().isEmpty() && !editPhone.getText().toString().isEmpty() && !editWage.getText().toString().isEmpty() && editPhone.getText().toString().length() == 10) {
                    updateInDB(
                            editName.getText().toString().trim(),
                            editPhone.getText().toString().trim(),
                            Double.parseDouble(editWage.getText().toString().trim())
                    );
                }//checks if empty & that phone number is of correct length ( 10 characters )
                else
                {
                    Toast emptyFormFields = Toast.makeText(getApplicationContext(), "Employee not updated, form must be complete.", Toast.LENGTH_SHORT);
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

    public void deleteEmployeePrompt() {
        dialogBuilder = new Dialog(this);
        dialogBuilder.setContentView(R.layout.confirm_dialog);
        dialogBuilder.show();

        //Caution for Shift Deletion
        Toast shiftCaution = Toast.makeText(getApplicationContext(), "Shifts for this employee will be removed.", Toast.LENGTH_LONG);
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

    public void createInDB(String name,String phone,Double wage) {
        //create employee
        Employee temp = accessEmployees.createEmployee(name, phone,wage);

        //update and refresh list
        final ListView listView = (ListView) findViewById(R.id.listEmployees);
        if(temp != null) {
            employeeList = accessEmployees.getAllEmployees();
            resetArrayAdapter();
            listView.setAdapter(employeeArrayAdapter);
        }//if employee created

    }

    public void updateInDB(String name,String phone,Double wage) {
        //update employee
        accessEmployees.updateEmployeeByID(selectedEmployee.getEmployeeID(), name, phone, wage);
        selectedEmployee = null;
        postDBChange();
    }

    public void deleteInDB() {

        //remove employees shifts
        AccessShifts accShift = new AccessShifts();
        ArrayList<Shift> empShifts = accShift.getShiftsByEmployeeID(selectedEmployee.getEmployeeID());
        for(int i=0;i<empShifts.size();i++){
            accShift.deleteShiftbyID(empShifts.get(i).getEmployeeID(),empShifts.get(i).getScheduleID(),empShifts.get(i).getWeekday());
        }//for each shift
        accessEmployees.deleteEmployeeByID(selectedEmployee.getEmployeeID());
        postDBChange();
    }

    public void postDBChange(){
        selectedEmployee = null;
        final ListView listView = (ListView) findViewById(R.id.listEmployees);
        final Button updateButton = (Button) findViewById(R.id.buttonEmployeeUpdate);
        final Button deleteButton = (Button) findViewById(R.id.buttonEmployeeDelete);
        employeeList = accessEmployees.getAllEmployees();
        resetArrayAdapter();
        listView.setAdapter(employeeArrayAdapter);
        listView.setItemChecked(selectedEmployeePosition, false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        selectedEmployeePosition = -1;
    }

}