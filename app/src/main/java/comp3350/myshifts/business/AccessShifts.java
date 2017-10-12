package comp3350.myshifts.business;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.persistence.Persistence;

public class AccessShifts
{
	private Persistence dataAccess;

	public AccessShifts()
	{
		dataAccess = (Persistence) Services.getDataAccess(Main.dbName);
	}

	public Shift createShift(int newEmployeeID, int newScheduleID, Weekday day, double startTime, double endTime)
    {
        Boolean duplicate = false;
        ArrayList<Shift> shiftsClone = getAllShifts();
        Shift toAdd = new Shift(newEmployeeID,newScheduleID,day,startTime,endTime);

        for(int i=0; i<shiftsClone.size(); i++)
        {
            if(shiftsClone.get(i).equals(toAdd))
            {
                duplicate = true;
            }
        }
        if(!duplicate)
        {
            dataAccess.addShift(toAdd);
            return toAdd;
        }
        else
        {
            return null;
        }
    }

    public ArrayList<Shift> getAllShifts()
    {
        return dataAccess.getAllShifts();
    }

    public Shift getShiftByID(int eID, int sID, Weekday day)
    {
        return dataAccess.getShiftByID(eID,sID,day);
    }

    public Boolean updateShiftbyID(int eID, int sID, Weekday day, double newStart, double newEnd)
    {
        return dataAccess.updateShiftByID(eID,sID,day,newStart,newEnd);
    }

    public Boolean deleteShiftbyID(int eID, int sID, Weekday day)
    {
        return dataAccess.deleteShiftbyID(eID,sID,day);
    }

    public ArrayList<Shift> getShiftsByEmployeeID(int eID)
    {
        ArrayList<Shift> allShifts = dataAccess.getAllShifts();
        ArrayList<Shift> result = new ArrayList<Shift>();

        if(allShifts != null)
        {
            for(int i = 0; i < allShifts.size(); i++)
            {
                if(allShifts.get(i).getEmployeeID() == eID)
                {
                    result.add(allShifts.get(i));
                }
            }
        }
        return result;
    }

    public ArrayList<Shift> getShiftsByScheduleID(int sID)
    {
        ArrayList<Shift> allShifts = dataAccess.getAllShifts();
        ArrayList<Shift> result = new ArrayList<Shift>();

        if(allShifts != null)
        {
            for(int i = 0; i < allShifts.size(); i++)
            {
                if(allShifts.get(i).getScheduleID() == sID)
                {
                    result.add(allShifts.get(i));
                }
            }
        }
        return result;
    }

}
