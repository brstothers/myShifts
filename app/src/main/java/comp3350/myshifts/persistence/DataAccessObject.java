package comp3350.myshifts.persistence;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;

public class DataAccessObject implements Persistence
{

    private Statement st1, st2, st3;
    private Connection c1;
    private ResultSet rs2, rs3, rs4;

    private String dbName;
    private String dbType;

    private ArrayList<Employee> employees;
    private ArrayList<Schedule> schedules;
    private ArrayList<Shift> shifts;

    private int nextEmployeeID = 100;
    private int nextScheduleID = 100;

    private String cmdString;

    public DataAccessObject()
    {
        this.dbName = Main.dbName;
        dbType = "HSQLDB";
        employees = new ArrayList<Employee>();
        schedules = new ArrayList<Schedule>();
        shifts = new ArrayList<Shift>();
    }

    public DataAccessObject(String dbName)
    {
        this.dbName = dbName;
        dbType = "HSQLDB";
        employees = new ArrayList<Employee>();
        schedules = new ArrayList<Schedule>();
        shifts = new ArrayList<Shift>();
    }

    public void openDB(String dbPath)
    {
        String url;

        try
        {
            dbType = "HSQL";
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
            url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
            c1 = DriverManager.getConnection(url, "SA", "");
            st1 = c1.createStatement();
            st2 = c1.createStatement();
            st3 = c1.createStatement();
            loadEmployees();
            loadSchedules();
            loadShifts();
            //clearDb();    // This with the next line are causing the app to reseed on open and close
            seedDB();
        } catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Opened " +dbType +" database " +dbPath);
    }

    public void closeDB()
    {
        try
        {	// commit all changes to the database

            cmdString = "shutdown compact";
            rs2 = st1.executeQuery(cmdString);
            c1.close();
        }
        catch (Exception e)
        {
            processSQLError(e);
        }
        System.out.println("Closed " +dbType +" database " +dbName);
    }

    private void seedDB()
    {
        Employee employee;
        Schedule schedule;
        Shift shift;

        if(employees.isEmpty() && schedules.isEmpty() && shifts.isEmpty()) {

            if (employees != null) {
                employee = new Employee("Gary Chalmers", "2041236787", 10.50);
                addEmployee(employee);
                employee = new Employee("Jonny Redfern", "2048289489", 13.00);
                addEmployee(employee);
                employee = new Employee("Quincy Adams", "2040495738", 15.00);
                addEmployee(employee);
                employee = new Employee("Rumple Stillskin", "2049484039", 19.50);
                addEmployee(employee);
                employee = new Employee("flowey theflower", "2042222222", 20.50);
                addEmployee(employee);
            }

            if (schedules != null) {
                schedule = new Schedule("Week 1", "February", "2017");
                addSchedule(schedule);
                schedule = new Schedule("Week 2", "March", "2017");
                addSchedule(schedule);
                schedule = new Schedule("Week 1", "April", "2017");
                addSchedule(schedule);
                schedule = new Schedule("Week 2", "May", "2018");
                addSchedule(schedule);
                schedule = new Schedule("Week 1", "June", "2018");
                addSchedule(schedule);
            }

            if ((shifts != null) && (schedules != null) && (employees != null))
            {
                System.out.println(employees.get(0).getEmployeeID() + " " + schedules.get(0).getSchedID());

                shift = new Shift(employees.get(0).getEmployeeID(),
                        schedules.get(0).getSchedID(), Weekday.FRI, 10, 15);
                addShift(shift);

                shift = new Shift(employees.get(0).getEmployeeID(),
                        schedules.get(0).getSchedID(), Weekday.SAT, 10, 15);
                addShift(shift);

                shift = new Shift(employees.get(1).getEmployeeID(),
                        schedules.get(1).getSchedID(), Weekday.MON, 10, 15);
                addShift(shift);

                shift = new Shift(employees.get(1).getEmployeeID(),
                        schedules.get(1).getSchedID(), Weekday.SUN, 10, 15);
                addShift(shift);

                shift = new Shift(employees.get(2).getEmployeeID(),
                        schedules.get(2).getSchedID(), Weekday.TUE, 10, 15);
                addShift(shift);
            }
        }
    }

    private void clearDb()
    {
        employees.clear();
        schedules.clear();
        shifts.clear();
        try
        {
            System.out.println("### CLEARING DB ###");
            cmdString = "Delete from EMPLOYEES";
            rs2 = st2.executeQuery(cmdString);
            cmdString = "Delete from SCHEDULES";
            rs2 = st2.executeQuery(cmdString);
            cmdString = "Delete from SHIFTS";
            rs2 = st2.executeQuery(cmdString);
        } catch (Exception e)
        {
            processSQLError(e);
        }
    }

    // EMPLOYEES

    private void loadEmployees()
    {
        Employee newEmployee;
        int employeeID;
        String eName;
        String ePhone;
        double eWage;

        employees.clear();

        try
        {
            cmdString = "Select * from EMPLOYEES";
            rs2 = st2.executeQuery(cmdString);
        } catch (Exception e)
        {
            processSQLError(e);
        }

        try
        {
            while (rs2.next())
            {
                employeeID = rs2.getInt("employeeid");
                eName = rs2.getString("employeename");
                ePhone = rs2.getString("employeephone");
                eWage = rs2.getDouble("employeewage");

                newEmployee = new Employee(eName, ePhone, eWage);
                newEmployee.setUniqueID(employeeID);

                if(employeeID >= nextEmployeeID)
                {
                    nextEmployeeID = employeeID + 1;
                }
                employees.add(newEmployee);
            }
            rs2.close();
        } catch (Exception e)
        {
            processSQLError(e);
        }
    }

    public ArrayList<Employee> getAllEmployees()
    {
        ArrayList<Employee> allEmployees = (ArrayList<Employee>)employees.clone();
        return allEmployees;
    }

    public Boolean addEmployee(Employee newEmployee)
    {
        String values;
        boolean result = false;

        if (newEmployee != null)
        {
            try
            {
                newEmployee.setUniqueID(nextEmployeeID);
                nextEmployeeID++;
                System.out.println("EID TO ADD: "+newEmployee.getEmployeeID());
                values = newEmployee.getEmployeeID()
                        +", '"+newEmployee.getEmployeeName()
                        + "', '" + newEmployee.getEmployeePhone()
                        + "', " + newEmployee.getEmployeeWage();
                cmdString = "Insert into EMPLOYEES" + " Values(" + values + ")";
                st1.executeUpdate(cmdString);
                result = true;
                loadEmployees();
            } catch (Exception e)
            {
                processSQLError(e);
            }
        }

        return result;
    }

    public Employee getEmployeeByID(int eID)
    {
        Employee toCheck;
        Employee found = null;
        ArrayList<Employee> employeesClone = getAllEmployees();
        for(int i=0; i<employees.size(); i++)
        {
            toCheck = employeesClone.get(i);
            if(toCheck.getEmployeeID() == eID)
            {
                found = toCheck;
                break;
            }
        }
        return found;
    }

    public Boolean updateEmployeeByID(int eID, String newName,String newPhone,double wage)
    {
        String values, where;
        Employee toUpdate = getEmployeeByID(eID);
        boolean result = false;

        if(toUpdate != null)
        {
            if(newName == null || newName.length() == 0 ||
                    newPhone == null || newPhone.length() == 0 || newPhone.length() > 10 || !newPhone.matches("[0-9]+") ||
                    wage < 0)
            {
                throw(new IllegalArgumentException("Invalid or null data passed to a method"));
            }
            else
            {
                try
                {
                    values = "employeeid="+eID + ", employeename='" + newName + "', employeephone='" + newPhone + "', employeewage=" + wage;
                    where = "where employeeid=" + toUpdate.getEmployeeID();
                    cmdString = "Update employees " + " Set " + values + " " + where;
                    st2.executeUpdate(cmdString);
                    loadEmployees();
                    result = true;
                } catch (Exception e)
                {
                    processSQLError(e);
                }
            }
        }

        return result;
    }

    public Boolean deleteEmployeeByID(int eID)
    {
        boolean result = false;
        Employee toDelete = getEmployeeByID(eID);
        if (toDelete != null) {
            try {
                cmdString = "Delete from employees where employeeid=" + toDelete.getEmployeeID();
                st3.executeUpdate(cmdString);
                cmdString = "Delete from shifts where employeeid=" + toDelete.getEmployeeID();
                st3.executeUpdate(cmdString);
                loadEmployees();
                loadShifts();
                result = true;
            } catch (Exception e) {
                processSQLError(e);
            }
        }
        return result;
    }


    // SCHEDULES

    private void loadSchedules()
    {
        Schedule newSchedule;
        int scheduleID;
        String week;
        String month;
        String year;

        schedules.clear();

        try
        {
            cmdString = "Select * from SCHEDULES";
            rs3 = st2.executeQuery(cmdString);
        } catch (Exception e)
        {
            processSQLError(e);
        }

        try
        {
            while (rs3.next())
            {
                scheduleID = rs3.getInt("scheduleid");
                week = rs3.getString("scheduleweek");
                month = rs3.getString("schedulemonth");
                year = rs3.getString("scheduleyear");

                newSchedule = new Schedule(week, month, year);
                newSchedule.setUniqueID(scheduleID);

                if(scheduleID >= nextScheduleID)
                {
                    nextScheduleID = scheduleID + 1;
                }
                schedules.add(newSchedule);
            }
            rs3.close();
        } catch (Exception e)
        {
            processSQLError(e);
        }
    }

    public ArrayList<Schedule> getAllSchedules()
    {
        ArrayList<Schedule> allSchedules = (ArrayList<Schedule>)schedules.clone();

        return allSchedules;
    }

    public Boolean addSchedule(Schedule newSched)
    {
        String values;
        boolean result = false;

        if (newSched != null)
        {
            try
            {
                newSched.setUniqueID(nextScheduleID);
                nextScheduleID++;
                values = newSched.getSchedID() + ", '" + newSched.getWeek()
                        +"', '"+newSched.getMonth()
                        + "', '" + newSched.getYear() + "'";
                cmdString = "Insert into schedules " + " Values(" + values + ")";
                st1.executeUpdate(cmdString);
                result = true;
                loadSchedules();
            } catch (Exception e)
            {
                processSQLError(e);
            }
        }
        return result;
    }

    public Schedule getScheduleByID(int sID)
    {
        Schedule toCheck;
        Schedule found = null;
        ArrayList<Schedule> schedulesClone = getAllSchedules();
        for(int i=0; i<schedulesClone.size(); i++)
        {
            toCheck = schedulesClone.get(i);
            if(toCheck.getSchedID() == sID)
            {
                found = toCheck;
                break;
            }
        }
        return found;
    }

    public Boolean updateScheduleByID(int sID, String newWeek, String newMonth, String newYear)
    {
        String values, where;
        Schedule toUpdate = getScheduleByID(sID);
        boolean result = false;

        if (toUpdate != null)
        {
            if(newWeek == null || newWeek.length() == 0 || newMonth == null || newMonth.length() == 0 ||
                newYear == null || newYear.length() == 0)
            {
                throw(new IllegalArgumentException("Invalid or null data passed to a method"));
            }
            else
            {
                try
                {
                    values = "scheduleid="+sID+",scheduleweek='" + newWeek + "',schedulemonth= '" + newMonth + "', scheduleyear='" + newYear + "'";
                    where = "where scheduleid=" + sID;
                    cmdString = "Update schedules" + " Set " + values + " " + where;
                    st2.executeUpdate(cmdString);
                    loadSchedules();
                    result = true;
                } catch (Exception e)
                {
                    processSQLError(e);
                }
            }
        }

        return result;
    }

    public Boolean deleteScheduleByID(int sID)
    {
        boolean result = false;
        Schedule toDelete = getScheduleByID(sID);
        if (toDelete != null) {
            try {
                cmdString = "Delete from schedules where scheduleid=" + toDelete.getSchedID();
                st2.executeUpdate(cmdString);
                cmdString = "Delete from shifts where scheduleid=" + toDelete.getSchedID();
                st2.executeUpdate(cmdString);
                loadSchedules();
                loadShifts();
                result = true;
            } catch (Exception e) {
                processSQLError(e);
            }
        }
        return result;
    }


    // SHIFTS

    private void loadShifts()
    {
        Shift newShift;
        int employeeID;
        int scheduleID;
        Weekday weekday;
        String dayString;
        double startTime;
        double endTime;

        shifts.clear();

        try
        {
            cmdString = "Select * from SHIFTS";
            rs4 = st2.executeQuery(cmdString);
        } catch (Exception e)
        {
            processSQLError(e);
        }

        try
        {
            while (rs4.next())
            {
                employeeID = rs4.getInt("employeeID");
                scheduleID = rs4.getInt("scheduleID");
                startTime = rs4.getDouble("starttime");
                endTime = rs4.getDouble("endtime");
                dayString = rs4.getString("weekday");
                weekday = stringToWeekday(dayString);
                newShift = new Shift(employeeID, scheduleID, weekday, startTime, endTime);
                shifts.add(newShift);
            }
            rs4.close();
        } catch (Exception e)
        {
            processSQLError(e);
        }
    }

    public ArrayList<Shift> getAllShifts()
    {
        ArrayList<Shift> allShifts = (ArrayList<Shift>)shifts.clone();
        return allShifts;
    }

    public Boolean addShift(Shift newShift)
    {
        String values, dayString;
        boolean result = false;

        if (newShift != null)
        {
            try
            {
                dayString = weekDayToString(newShift.getWeekday());
                values = newShift.getEmployeeID()
                        + ", " + newShift.getScheduleID()
                        + ", '" + dayString
                        + "', " + newShift.getStartTime()
                        + ", " + newShift.getEndTime();

                cmdString = "Insert into shifts " + " Values(" + values + ")";
                st3.executeUpdate(cmdString);
                result = true;
                loadShifts();
            } catch (Exception e)
            {
                processSQLError(e);
            }
        }
        return result;
    }

    public Shift getShiftByID(int eID, int sID, Weekday day)
    {
        Shift toCheck;
        Shift found = null;
        ArrayList<Shift> shiftsClone = getAllShifts();
        for(int i=0; i<shiftsClone.size(); i++)
        {
            toCheck = shiftsClone.get(i);
            if(		(toCheck.getEmployeeID() == eID)
                    && (toCheck.getScheduleID() == sID)
                    && (toCheck.getWeekday() == day))
            {
                found = toCheck;
                break;
            }
        }
        return found;
    }

    public Boolean updateShiftByID(int eID, int sID, Weekday day, double newStart, double newEnd)
    {
        String values, where, dayString;
        Shift toUpdate = getShiftByID(eID, sID, day);
        boolean result = false;

        if (toUpdate != null)
        {
            if(newStart < 0 || newStart > 24 || newEnd < 0 || newEnd > 24)
            {
                throw(new IllegalArgumentException("Invalid or null data passed to a method"));
            }
            else
            {
                try
                {
                    dayString = weekDayToString(day);
                    values = "employeeid="+eID + ", scheduleid=" + sID + ", weekday='" + dayString + "', starttime=" + newStart + ", endtime=" + newEnd;
                    where = "where employeeid=" + toUpdate.getEmployeeID()
                            + " and scheduleid=" + toUpdate.getScheduleID()
                            + " and weekday='" + dayString + "'";
                    cmdString = "Update shifts " + " Set " + values + " " + where;
                    st3.executeUpdate(cmdString);
                    loadShifts();
                    result = true;
                } catch (Exception e)
                {
                    processSQLError(e);
                }
            }
        }

        return result;
    }

    public Boolean deleteShiftbyID(int eID, int sID, Weekday day)
    {
        boolean result = false;
        String dayString;
        Shift toDelete = getShiftByID(eID, sID, day);
        if (toDelete != null) {
            try {
                dayString = weekDayToString(day);
                cmdString = "Delete from shifts where employeeid=" + toDelete.getEmployeeID()
                            + " and scheduleid=" + toDelete.getScheduleID()
                            + " and weekday='" + dayString + "'";
                st3.executeUpdate(cmdString);
                loadShifts();
                result = true;
            } catch (Exception e) {
                processSQLError(e);
            }
        }
        return result;
    }

    // MISC

    public void processSQLError(Exception e)
    {
        System.out.println("*** SQL Error: " + e.getMessage());

        e.printStackTrace();
    }

    private String weekDayToString(Weekday day)
    {
        String dayString = null;
        if (day == Weekday.MON)
        {
            dayString = "MON";
        }
        else if (day == Weekday.TUE)
        {
            dayString = "TUE";
        }
        else if (day == Weekday.WED)
        {
            dayString = "WED";
        }
        else if (day == Weekday.THR)
        {
            dayString = "THR";
        }
        else if (day == Weekday.FRI)
        {
            dayString = "FRI";
        }
        else if (day == Weekday.SAT)
        {
            dayString = "SAT";
        }
        else if (day == Weekday.SUN)
        {
            dayString = "SUN";
        }
        return dayString;
    }

    private Weekday stringToWeekday(String dayString)
    {
        Weekday day = null;
        if (dayString.equals("MON"))
        {
            day = Weekday.MON;
        }
        else if (dayString.equals("TUE"))
        {
            day = Weekday.TUE;
        }
        else if (dayString.equals("WED"))
        {
            day = Weekday.WED;
        }
        else if (dayString.equals("THR"))
        {
            day = Weekday.THR;
        }
        else if (dayString.equals("FRI"))
        {
            day = Weekday.FRI;
        }
        else if (dayString.equals("SAT"))
        {
            day = Weekday.SAT;
        }
        else if (dayString.equals("SUN"))
        {
            day = Weekday.SUN;
        }
        return  day;
    }

}
