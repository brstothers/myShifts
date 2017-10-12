package comp3350.myshifts.objects;

public class Schedule extends Unique
{
    private String week;
    private String month;
    private String year;

    public Schedule()
    {
        this.week = null;
        this.month = null;
        this.year = null;
    }

    public Schedule(String week, String month, String year)
    {
        if(     week == null || week.length() == 0 ||
                month == null || month.length() == 0 ||
                year == null || year.length() == 0)
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.week = week;
            this.month = month;
            this.year = year;
        }
    }

    public boolean equals(int compareID)
    {
        return (this.getUniqueID() == compareID);
    }

    public String toString()
    {
        return this.getUniqueID()+"";
    }

    public int getSchedID()
    {
        return this.getUniqueID();
    }

    public String getWeek()
    {
        return this.week;
    }

    public void setWeek(String week)
    {
        if(week == null || week.length() == 0)
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.week = week;
        }
    }

    public String getMonth()
    {
        return this.month;
    }

    public void setMonth(String month)
    {
        if(month == null || month.length() == 0)
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.month = month;
        }
    }

    public String getYear()
    {
        return this.year;
    }

    public void setYear(String year)
    {
        if(year == null || year.length() == 0)
        {
            throw(new IllegalArgumentException("Invalid or null data passed to a method"));
        }
        else
        {
            this.year = year;
        }
    }
}
