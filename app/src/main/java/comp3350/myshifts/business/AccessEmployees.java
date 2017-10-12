package comp3350.myshifts.business;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.application.Services;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.persistence.Persistence;

public class AccessEmployees
{
	private Persistence dataAccess;

	public AccessEmployees()
	{
		dataAccess = (Persistence) Services.getDataAccess(Main.dbName);
	}

    public Employee createEmployee(String name, String phone, double wage)
    {
        if(phone == null || phone.length() != 10)
        {
            throw(new IllegalArgumentException("Error in createEmployee syntax."));
        }
        else
        {
            Employee toAdd = new Employee(name, phone, wage);
            dataAccess.addEmployee(toAdd);
            return toAdd;
        }
    }

    public ArrayList<Employee> getAllEmployees()
    {
        return dataAccess.getAllEmployees();
    }

    public Employee getEmployeeByID(int eID)
    {
        return dataAccess.getEmployeeByID(eID);
    }

    public boolean deleteEmployeeByID(int eID)
    {
        return dataAccess.deleteEmployeeByID(eID);
    }

    public boolean updateEmployeeByID(int eID, String newName, String newPhone, double newWage)
    {
        if(newPhone == null || newPhone.length() != 10)
        {
            throw(new IllegalArgumentException("Error in updateEmployeeByID syntax."));
        }
        else
        {
            return dataAccess.updateEmployeeByID(eID, newName, newPhone, newWage);
        }
    }

    public ArrayList<Employee> getEmployeesBySchedID(int sID)
    {
        boolean add;
        Employee toAdd;
        AccessShifts shiftAccess = new AccessShifts();
        ArrayList<Employee> result = new ArrayList<Employee>();
        ArrayList<Shift> schedShifts = shiftAccess.getShiftsByScheduleID(sID);

        if((schedShifts != null))
        {
            for(int i=0; i<schedShifts.size(); i++)
            {
                toAdd = getEmployeeByID(schedShifts.get(i).getEmployeeID());
                add = true;
                for(int j=0; j<result.size(); j++)
                {
                    if(toAdd.getEmployeeID() == result.get(j).getEmployeeID())
                    {
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
