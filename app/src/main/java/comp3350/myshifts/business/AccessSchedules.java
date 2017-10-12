package comp3350.myshifts.business;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.persistence.Persistence;

import static android.R.attr.y;

public class AccessSchedules
{

    private Persistence dataAccess;

    public AccessSchedules()
    {
        dataAccess = (Persistence) Services.getDataAccess(Main.dbName);

    }

    public Schedule createSchedule(String week, String month, String year)
    {
        if(Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2100)
        {
            throw(new IllegalArgumentException("Error in createSchedule syntax."));
        }
        else
        {
            Schedule temp = new Schedule(week, month, year);
            dataAccess.addSchedule(temp);
            return temp;
        }
    }

    public ArrayList<Schedule> getAllSchedules()
    {
        return dataAccess.getAllSchedules();
    }

    public Schedule getScheduleByID(int sID)
    {
        return dataAccess.getScheduleByID(sID);
    }

    public boolean deleteScheduleByID(int sID)
    {
        return dataAccess.deleteScheduleByID(sID);
    }

    public boolean updateScheduleByID(int sID, String newWeek, String newMonth, String newYear)
    {
        if(Integer.parseInt(newYear) < 2000 || Integer.parseInt(newYear) > 2100)
        {
            throw(new IllegalArgumentException("Error in updateScheduleByID syntax."));
        }
        else
        {
            return dataAccess.updateScheduleByID(sID, newWeek, newMonth, newYear);
        }
    }

    public ArrayList<Schedule> getSchedulesByEmployeeID(int eID)
    {
        boolean add;
        Schedule toAdd;
        AccessShifts shiftAccess = new AccessShifts();
        ArrayList<Schedule> result = new ArrayList<Schedule>();
        ArrayList<Shift> employeeShifts = shiftAccess.getShiftsByEmployeeID(eID);

        if((employeeShifts != null))
        {
            for(int i=0; i<employeeShifts.size(); i++)
            {
                toAdd = getScheduleByID(employeeShifts.get(i).getScheduleID());
                add = true;
                for(int j=0; j<result.size(); j++)
                {
                    if(toAdd.getSchedID() == result.get(j).getSchedID()){
                        add = false;
                    }
                }
                if(add)
                {
                    result.add(toAdd);
                }
            }
        }
        return result;
    }

}
