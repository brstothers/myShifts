package comp3350.myshifts.tests.persistence;

import java.util.ArrayList;

import comp3350.myshifts.application.Main;
import comp3350.myshifts.objects.Employee;
import comp3350.myshifts.objects.Schedule;
import comp3350.myshifts.objects.Shift;
import comp3350.myshifts.objects.Weekday;
import comp3350.myshifts.persistence.Persistence;

public class DataAccessStub implements Persistence
{
	private String dbName;
	private String dbType = "stub";
	private ArrayList<Employee> employees;
	private ArrayList<Schedule> schedules;
	private ArrayList<Shift> shifts;

	private int nextEmployeeID = 100;
	private int nextScheduleID = 100;

	public DataAccessStub(String dbName)
	{
		this.dbName = dbName;
		employees = new ArrayList<Employee>();
		schedules = new ArrayList<Schedule>();
		shifts = new ArrayList<Shift>();
	}

	public DataAccessStub()
	{
		this(Main.dbName);
		employees = new ArrayList<Employee>();
		schedules = new ArrayList<Schedule>();
		shifts = new ArrayList<Shift>();
	}

	public void openDB(String dbPath)
	{
        Employee employee;
		Schedule schedule;
		Shift shift;

		if(employees != null)
		{
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

		if(schedules != null)
		{
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

		if((shifts != null) && (schedules != null) && (employees != null))
		{
			System.out.println(employees.get(0).getEmployeeID()+" / "+schedules.get(0).getSchedID());

			shift = new Shift(employees.get(0).getEmployeeID(),
					schedules.get(0).getSchedID(), Weekday.FRI, 10, 15);
			shifts.add(shift);

			shift = new Shift(employees.get(0).getEmployeeID(),
					schedules.get(0).getSchedID(), Weekday.SAT, 10, 15);
			shifts.add(shift);

			shift = new Shift(employees.get(1).getEmployeeID(),
					schedules.get(1).getSchedID(), Weekday.MON, 10, 15);
			shifts.add(shift);

			shift = new Shift(employees.get(1).getEmployeeID(),
					schedules.get(1).getSchedID(), Weekday.SUN, 10, 15);
			shifts.add(shift);

			shift = new Shift(employees.get(2).getEmployeeID(),
					schedules.get(2).getSchedID(), Weekday.TUE, 10, 15);
			shifts.add(shift);
		}
	}

	public void closeDB()
	{
		employees.clear();
		schedules.clear();
		shifts.clear();
	}

	public ArrayList<Employee> getAllEmployees()
	{
		ArrayList<Employee> allEmployees = (ArrayList<Employee>)employees.clone();
		return allEmployees;
	}

	public Boolean addEmployee(Employee newEmployee)
	{
		Boolean result = false;
		if(newEmployee != null)
		{
			newEmployee.setUniqueID(nextEmployeeID);
			nextEmployeeID++;
			employees.add(newEmployee);
			result = true;
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
			toCheck = employees.get(i);
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
		Boolean result = false;
		Employee toUpdate = getEmployeeByID(eID);
		if(toUpdate != null)
		{
			toUpdate.setEmployeeName(newName);
			toUpdate.setEmployeePhone(newPhone);
			toUpdate.setEmployeeWage(wage);
			result = true;
		}
		return result;
	}

	public Boolean deleteEmployeeByID(int eID)
	{
		Boolean result = false;
		Employee toDelete = getEmployeeByID(eID);
		if(toDelete != null)
		{
			employees.remove(toDelete);
			toDelete = getEmployeeByID(eID);
			if(toDelete == null)
			{
				result = true;
			}
		}
		return result;
	}

	public ArrayList<Schedule> getAllSchedules()
	{
		ArrayList<Schedule> allSchedules = (ArrayList<Schedule>)schedules.clone();
		return allSchedules;
	}

	public Boolean addSchedule(Schedule newSched)
	{
		Boolean result = false;
		if(newSched != null)
		{
			newSched.setUniqueID(nextScheduleID);
			nextScheduleID++;
			schedules.add(newSched);
			result = true;
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
		Boolean result = false;
		Schedule toUpdate = getScheduleByID(sID);
		if(toUpdate != null)
		{
			toUpdate.setWeek(newWeek);
			toUpdate.setMonth(newMonth);
			toUpdate.setYear(newYear);
			result = true;
		}
		return result;
	}

	public Boolean deleteScheduleByID(int sID)
	{
		Boolean result = false;
		Schedule toDelete = getScheduleByID(sID);
		if(toDelete != null)
		{
			schedules.remove(toDelete);
			toDelete = getScheduleByID(sID);
			if(toDelete == null)
			{
				result = true;
			}
		}
		return result;
	}

	public ArrayList<Shift> getAllShifts()
	{
		ArrayList<Shift> allShifts = (ArrayList<Shift>)shifts.clone();
		return allShifts;
	}

	public Boolean addShift(Shift newShift)
	{
		Boolean result = false;
		if(newShift != null)
		{
			shifts.add(newShift);
			result = true;
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
		Boolean result = false;
		Shift toUpdate = getShiftByID(eID, sID, day);
		if(toUpdate != null)
		{
			toUpdate.setStartTime(newStart);
			toUpdate.setEndTime(newEnd);
			result = true;
		}
		return result;
	}

	public Boolean deleteShiftbyID(int eID, int sID, Weekday day)
	{
		Boolean result = false;
		Shift toDelete = getShiftByID(eID, sID, day);
		if(toDelete != null)
		{
			shifts.remove(toDelete);
			toDelete = getShiftByID(eID, sID, day);
			if(toDelete == null)
			{
				result = true;
			}
		}
		return result;
	}
}
