package comp3350.myshifts.objects;

public class Employee extends Unique
{
	private String eName;
	private String ePhone;
	private double eWage;


	public Employee()
	{
		eName = "";
		ePhone = "";
		eWage = 0.0;
	}

	public Employee(String newName, String newPhone)
	{
		if(newName == null || newName.length() == 0 ||
				newPhone == null || newPhone.length() == 0 ||
				newPhone.length() > 10 || !newPhone.matches("[0-9]+"))
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			eName = newName;
			ePhone = newPhone;
			eWage = 0.0;
		}
	}

	public Employee(String newName, String newPhone, double newWage)
	{
		if(newName == null || newName.length() == 0 ||
				newPhone == null || newPhone.length() == 0 ||
				newPhone.length() > 10 || !newPhone.matches("[0-9]+") || newWage < 0)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method!!!"));
		}
		else
		{
			eName = newName;
			ePhone = newPhone;
			eWage = newWage;
		}
	}

	public int getEmployeeID()
	{
		return this.getUniqueID();
	}

	public String getEmployeeName()
	{
		return this.eName;
	}

	public String getEmployeePhone()
	{
		return this.ePhone;
	}

	public double getEmployeeWage()
	{
		return this.eWage;
	}

	public void setEmployeeName(String name)
	{
		if(name == null || name.length() == 0)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			this.eName = name;
		}
	}

	public void setEmployeePhone(String phone)
	{
		if(phone == null || phone.length() == 0 || phone.length() > 10 || !phone.matches("[0-9]+"))
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			this.ePhone = phone;
		}
	}

	public void setEmployeeWage(double wage)
	{
		if(wage < 0)
		{
			throw(new IllegalArgumentException("Invalid or null data passed to a method"));
		}
		else
		{
			this.eWage = wage;
		}
	}

	public String toString()
	{
		return "Employee: " + getUniqueID() + " " + eName + " " + ePhone + " " + eWage;
	}

	public boolean equals(Object object)
	{
		boolean result;
		Employee employee;
		result = false;

		if(object != null)
		{
			if (object instanceof Employee)
			{
				employee = (Employee) object;
				if (((employee.getUniqueID() == 0) && (this.getUniqueID() == 0) && (this.eWage == 0))
						|| (employee.getUniqueID() == this.getUniqueID()))
				{
					result = true;
				}
			}
		}
		return result;
	}
}