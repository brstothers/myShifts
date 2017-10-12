package comp3350.myshifts.objects;

public abstract class Unique
{
    private int uniqueID;

    public Unique()
    {
        this.uniqueID = -1;
    }

    public int getUniqueID()
    {
        return this.uniqueID;
    }

    public void setUniqueID(int id)
    {
        if(id > 0)
        {
            this.uniqueID = id;
        }
        else
        {
            throw(new IllegalArgumentException("Passed an invalid ID ( < 0)"));
        }
    }

    public boolean isDefaultID()
    {
        return this.uniqueID != -1;
    }

    @Override
    public boolean equals(Object object)
    {
        boolean result = false;

        if(object != null)
        {
            if(object instanceof Unique)
            {
                if(this.uniqueID == ((Unique) object).getUniqueID())
                {
                    result = true;
                }
            }
        }
        return result;
    }
}
