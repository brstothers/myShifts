package comp3350.myshifts.persistence;

import java.util.ArrayList;

import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;

public interface Persistence
{
    void openDB(String dbPath);
    void closeDB();
    // Employee Access
    ArrayList<Employee> getAllEmployees();
    Boolean addEmployee(Employee newEmployee);
    Employee getEmployeeByID(int eID);
    Boolean updateEmployeeByID(int eID, String newName,String newPhone,double wage);
    Boolean deleteEmployeeByID(int eID);
    // Schedule Access
    ArrayList<Schedule> getAllSchedules();
    Boolean addSchedule(Schedule newSched);
    Schedule getScheduleByID(int sID);
    Boolean updateScheduleByID(int sID, String newWeek, String newMonth, String newYear);
    Boolean deleteScheduleByID(int sID);
    // Shift Access
    ArrayList<Shift> getAllShifts();
    Boolean addShift(Shift newShift);
    Shift getShiftByID(int eID, int sID, Weekday day);
    Boolean updateShiftByID(int eID, int sID, Weekday day, double newStart, double newEnd);
    Boolean deleteShiftbyID(int eID, int sID, Weekday day);
}
