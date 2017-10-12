package comp3350.myshifts.objects;

public class EmployeeSummary extends Summary
{
    private String employeeName;
    private int eID;
    private double eWage;
    private int numScheds;
    private int numShifts;
    private double totalHours;
    private double totalPay;

    public EmployeeSummary(String employeeName, int eID, double eWage, int numScheds,
                           int numShifts, double totalHours, double totalPay)
    {
        if(employeeName == null || employeeName.length() == 0 || eID < 0 || eWage < 0 || numScheds < 0 ||
                numShifts < 0 || totalHours < 0 || totalPay <0 )
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.employeeName = employeeName;
            this.eID = eID;
            this.eWage = eWage;
            this.numScheds = numScheds;
            this.numShifts = numShifts;
            this.totalHours = totalHours;
            this.totalPay = totalPay;
        }
    }

    public String getEmployeeName(){ return employeeName; }

    public int getEmployeeID(){ return eID; }

    public double getEmployeeWage(){ return eWage; }

    public int getNumScheds(){ return numScheds; }

    public int getNumShifts(){ return numShifts; }

    public double getTotalHours(){ return totalHours; }

    public double getTotalPay(){ return totalPay; }
}
