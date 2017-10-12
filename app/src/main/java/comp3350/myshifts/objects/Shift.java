package comp3350.myshifts.objects;

public class Shift
{
	private int employeeID;
	private int scheduleID;
	private Weekday day;
	private double startTime;
	private double endTime;

	public Shift(int newEmployeeID, int newWorkdayID, Weekday day, double startTime, double endTime)
	{
		if(newEmployeeID < 0 || newWorkdayID < 0 || startTime < 0 || endTime < 0 || endTime > 24 || startTime > 24)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			this.employeeID = newEmployeeID;
			this.scheduleID = newWorkdayID;
			this.day = day;

			if(startTime > endTime)
			{
				this.startTime = endTime;
				this.endTime = endTime;
			}
			else
			{
				this.startTime = startTime;
				this.endTime = endTime;
			}
		}
	}

	public int getEmployeeID()
	{
		return this.employeeID;
	}

	public int getScheduleID()
	{
		return this.scheduleID;
	}

	public double getStartTime()
	{
		return this.startTime;
	}

	public double getEndTime()
	{
		return this.endTime;
	}

	public Weekday getWeekday()
	{
		return this.day;
	}

	public void setWeekday(Weekday day)
	{
		this.day = day;
	}

	public void setStartTime(double newStart)
	{
		if(newStart < 0 || newStart > 24)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			if(newStart > endTime)
			{
				this.startTime = this.endTime;
			}
			else
			{
				this.startTime = newStart;
			}
		}
	}

	public void setEndTime(double newEnd)
	{
		if(newEnd < 0 || newEnd > 24)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			if (newEnd < this.startTime)
			{
				this.endTime = this.startTime;
			}
			else
			{
				this.endTime = newEnd;
			}
		}
	}

	public String toString()
	{
		return "Shift: eId: " + this.employeeID + " sId: " + this.scheduleID + " Start: " + Double.toString(this.startTime) + " End: " + Double.toString(this.endTime);
	}
	
	public boolean equals(Object object)
	{
		boolean result;
		Shift shift;
		
		result = false;

		if(object != null)
		{
			if (object instanceof Shift)
			{
				shift = (Shift) object;
				if ((this.employeeID == shift.getEmployeeID()) && (this.scheduleID == shift.getScheduleID())
						&& (this.day == shift.getWeekday()))
				{
					result = true;
				}
			}
		}

		return result;
	}
}