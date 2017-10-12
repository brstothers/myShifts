package comp3350.myshifts.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.EmployeeSummary;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.ScheduleSummary;
import comp3350.myshifts.objects.Shift;

public class Summarize
{
    public static EmployeeSummary employee(int eID)
    {
        EmployeeSummary result = null;
        AccessEmployees employeeAccess = new AccessEmployees();
        Employee employee = employeeAccess.getEmployeeByID(eID);

        if(employee != null)
        {
            // Access initialization
            AccessShifts shiftAccess = new AccessShifts();
            ArrayList<Shift> shiftList = shiftAccess.getShiftsByEmployeeID(eID);

            // Variables for building summary

            double totalPay = 0;
            double totalHours = 0;
            int totalSchedules = 0;
            int totalShifts = shiftList.size();
            String eName = employee.getEmployeeName();
            double eWage = employee.getEmployeeWage();

            // Calculating total unique schedules
            ArrayList<Integer> scheduleIdList = new ArrayList<Integer>();
            for(int i = 0; i<shiftList.size(); i++)
            {
                scheduleIdList.add(shiftList.get(i).getScheduleID());
            }
            totalSchedules = totalUniqueIDs(scheduleIdList);

            // Calculating total hours and total pay earned
            for(int i=0; i<shiftList.size(); i++)
            {
                double hoursWorked = shiftList.get(i).getEndTime() - shiftList.get(i).getStartTime();
                totalHours += hoursWorked;
            }

            totalPay += (eWage * totalHours);

            // Building summary object
            result = new EmployeeSummary(eName, eID, eWage, totalSchedules, totalShifts,
                    totalHours, totalPay);
        }
        else
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }

        return result;
    }

    public static ScheduleSummary schedule(int sID)
    {
        ScheduleSummary result = null;
        AccessSchedules scheduleAccess = new AccessSchedules();
        Schedule schedule = scheduleAccess.getScheduleByID(sID);

        if(schedule != null)
        {
            // Access initialization
            AccessShifts shiftAccess = new AccessShifts();
            AccessEmployees employeesAccess = new AccessEmployees();
            ArrayList<Shift> shiftList = shiftAccess.getShiftsByScheduleID(sID);

            // Variables for building summary

            double totalPay = 0;
            int totalHours = 0;
            int totalEmployees= 0;
            int totalShifts = shiftList.size();
            String week = schedule.getWeek();
            String month = schedule.getMonth();
            String year = schedule.getYear();

            // Calculating total unique employees
            ArrayList<Integer> employeeIdList = new ArrayList<Integer>();
            for(int i = 0; i<shiftList.size(); i++)
            {
                employeeIdList.add(shiftList.get(i).getEmployeeID());
            }
            totalEmployees = totalUniqueIDs(employeeIdList);

            // Calculating total hours and total payroll
            for(int i=0; i<shiftList.size(); i++)
            {
                int currEID = shiftList.get(i).getEmployeeID();
                Employee currEmployee = employeesAccess.getEmployeeByID(currEID);
                double eWage = currEmployee.getEmployeeWage();
                double hoursWorked = (shiftList.get(i).getEndTime() - shiftList.get(i).getStartTime());
                totalHours += hoursWorked;
                totalPay += (eWage * hoursWorked);
            }

            // Building summary object
            result = new ScheduleSummary(week, month, year, sID, totalEmployees, totalShifts,
                    totalHours, totalPay);
        }
        else
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }

        return result;
    }

    private static int totalUniqueIDs(ArrayList<Integer> listOfIDs)
    {
        Set<Integer> uniqueSchedIDs = new HashSet<Integer>();
        uniqueSchedIDs.addAll(listOfIDs);
        listOfIDs.clear();
        listOfIDs.addAll(uniqueSchedIDs);
        return listOfIDs.size();
    }
}
