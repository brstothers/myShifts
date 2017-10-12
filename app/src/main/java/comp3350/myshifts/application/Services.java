package comp3350.myshifts.application;

import comp3350.myshifts.persistence.DataAccessObject;
import comp3350.myshifts.persistence.Persistence;

public class Services
{
	private static Persistence dataAccessService = null;

	public static Persistence createDataAccess(String dbName)
	{
		if (dataAccessService == null)
		{
			//Change between next two lines to switch between StubDB and RealDB
			dataAccessService = new DataAccessObject(dbName);

			dataAccessService.openDB(Main.getDBPathName());
		}
		return dataAccessService;
	}

	public static Persistence createDataAccess(Persistence dataAccess)
	{
		if (dataAccessService == null)
		{
			dataAccessService = dataAccess;
			dataAccessService.openDB(Main.getDBPathName());
		}
		return dataAccessService;
	}

	public static Persistence getDataAccess(String dbName)
	{
		if (dataAccessService == null)
		{
			System.out.println("Connection to data access has not been established.");
			System.exit(1);
		}
		return dataAccessService;
	}

	public static void closeDataAccess()
	{
		if (dataAccessService != null)
		{
			dataAccessService.closeDB();
		}
		dataAccessService = null;
	}
}
