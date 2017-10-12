package comp3350.myshifts.objects;

public class ScheduleSummary extends Summary
{
    private String week;
    private String month;
    private String year;
    private int sID;
    private int numEmployees;
    private int numShifts;
    private double totalHours;
    private double totalPayroll;

    public ScheduleSummary(String week, String month, String year, int sID,
                           int numEmployees, int numShifts, double totalHours, double totalPayroll)
    {
        if(week == null || week.length() == 0 || month == null || month.length() == 0 || year == null || year.length() == 0 ||
                sID < 0 || numEmployees < 0 || numShifts < 0 || totalHours < 0 || totalPayroll <0 )
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.week = week;
            this.month = month;
            this.year = year;
            this.sID = sID;
            this.numEmployees = numEmployees;
            this.numShifts = numShifts;
            this.totalHours = totalHours;
            this.totalPayroll = totalPayroll;
        }
    }

    public String getSchedWeek(){ return week; }

    public String getSchedMonth(){ return month; }

    public String getSchedYear(){ return year; }

    public int getScheduleID(){ return sID; }

    public int getNumEmployees(){ return numEmployees; }

    public int getNumShifts(){ return numShifts; }

    public double getTotalHours(){ return totalHours; }

    public double getTotalPayroll(){ return totalPayroll; }

}
