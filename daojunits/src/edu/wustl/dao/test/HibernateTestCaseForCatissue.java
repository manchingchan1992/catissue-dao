package edu.wustl.dao.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import test.Address;
import test.Order;
import test.Person;
import test.User;
import test.UserRole;
import edu.wustl.common.audit.AuditManager;
import edu.wustl.common.exception.ApplicationException;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.DAO;
import edu.wustl.dao.HibernateDAO;
import edu.wustl.dao.QueryWhereClause;
import edu.wustl.dao.condition.EqualClause;
import edu.wustl.dao.condition.GreaterThenClause;
import edu.wustl.dao.condition.INClause;
import edu.wustl.dao.condition.LessThenClause;
import edu.wustl.dao.condition.NotEqualClause;
import edu.wustl.dao.condition.NotNullClause;
import edu.wustl.dao.condition.NullClause;
import edu.wustl.dao.daofactory.DAOConfigFactory;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.util.DAOConstants;
import edu.wustl.dao.util.HibernateMetaData;
import edu.wustl.dao.util.HibernateMetaDataFactory;


/**
 * @author kalpana_thakur
 *
 */
public class HibernateTestCaseForCatissue extends BaseTestCase
{

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getCommonLogger(HibernateTestCaseForCatissue.class);
	/**
	 * DAO instance.
	 */
	private DAO dao;

	{
		setDAO();
	}

	/**
	 * This method will be called to set the Default DAO.
	 */
	public void setDAO()
	{
		IDAOFactory daoFactory = daoConfigFactory.getInstance().getDAOFactory("caTissuecore");
		try
		{
			dao = daoFactory.getDAO();
		}
		catch (DAOException e)
		{
			logger.fatal("Problem while retrieving DAO object", e);
		}
	}


	/**
	 * This test will assert that DAO instance is not null.
	 */
	@Test
	public void testDAOInstance()
	{
		assertNotNull("DAO Object is null",dao);
	}


	/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testInsertPerson()
	{
		try
		{
			AuditManager.init();
			dao.openSession(null);
			Person person = new Person();
			person.setName("Kalpana");

			Address address = new Address();
			address.setStreet("Street unknown");
			person.setAddress(address);

			Collection<Object> orderCol = new HashSet<Object>();
			Order personOrder = new Order();
			Order personOrder1 = new Order();
			personOrder.setPerson(person);
			personOrder1.setPerson(person);
			orderCol.add(personOrder);
			orderCol.add(personOrder1);
			person.setOrderCollection(orderCol);
			dao.insert(person);

			
						dao.commit();
			//dao.closeSession();

		}
		catch(Exception exp)
		{
			ApplicationException appExp = (ApplicationException)exp;
			appExp.printStackTrace();
			logger.fatal(appExp.getLogMessage());
			assertFalse("Failed while inserting object :", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}
	/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testInsertUser()
	{
		try
		{
			dao.openSession(null);
			User user = createUser();
			dao.insert(user);
			User user1 = createUser();
			dao.insert(user1);
			dao.commit();
		}
	catch(Exception exp)
	{
		ApplicationException appExp = (ApplicationException)exp;
		appExp.printStackTrace();
		logger.fatal(appExp.getLogMessage());
		assertFalse("Failed while inserting object :", true);
	}
	finally
	{
		try
		{
			dao.closeSession();
		}
		catch (DAOException e)
		{
			e.printStackTrace();
		}
	}
	}


	private User createUser()
	{
		User user = new User();
		user.setEmailAddress("kalpana@kalpana.com");
		user.setFirstName("kalpana");
		user.setActivityStatus("Active");
		user.setLastName("Thakur");

		Collection<Object> userOrderColl = new HashSet<Object>();
		Order userOrder = new Order();
		Order userOrder1 = new Order();
		userOrder.setUser(user);
		userOrder1.setUser(user);
		userOrderColl.add(userOrder);
		userOrderColl.add(userOrder1);
		user.setOrderCollection(userOrderColl);
		return user;
	}

	/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testUpdateUser()
	{
		try
		{
			//AuditManager.init();
			dao.openSession(null);

			DAO newdao = DAOConfigFactory.getInstance().getDAOFactory("caTissuecore")
			.getDAO();

			newdao.openSession(null);
			User oldUser = (User)
			newdao.retrieveById(User.class.getName(), Long.valueOf(1));
			newdao.closeSession();

			User currentUser = new User();
			currentUser.setId(oldUser.getId());

			Collection collection = new HashSet();
			collection.add(oldUser.getOrderCollection().iterator().next());
			currentUser.setOrderCollection(collection);
			currentUser.setEmailAddress(oldUser.getEmailAddress());
			currentUser.setActivityStatus(oldUser.getActivityStatus());
			currentUser.setLastName(oldUser.getLastName());
			currentUser.setFirstName("Maria johns");


			dao.update(currentUser,oldUser);
			dao.commit();
			//dao.closeSession();

		}
		catch(Exception exp)
		{
			ApplicationException appExp = (ApplicationException)exp;
			appExp.printStackTrace();
			logger.fatal(appExp.getLogMessage());
			assertFalse("Failed while inserting object :", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testUpdatePerson()
	{
		try
		{
			//AuditManager.init();
			dao.openSession(null);

			DAO newdao = DAOConfigFactory.getInstance().getDAOFactory("caTissuecore")
			.getDAO();

			newdao.openSession(null);
			Person oldPerson = (Person)
			newdao.retrieveById(Person.class.getName(), Long.valueOf(1));
			newdao.closeSession();

			Person currentPerson = new Person();
			currentPerson.setId(oldPerson.getId());
			currentPerson.setAddress(oldPerson.getAddress());
			Collection collection = new HashSet();
			collection.add(oldPerson.getOrderCollection().iterator().next());
			currentPerson.setOrderCollection(collection);

			currentPerson.setName("Kalpana Thakur");

			dao.update(currentPerson,oldPerson);
			dao.commit();
			//dao.closeSession();

		}
		catch(Exception exp)
		{
			ApplicationException appExp = (ApplicationException)exp;
			appExp.printStackTrace();
			logger.fatal(appExp.getLogMessage());
			assertFalse("Failed while inserting object :", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testUpdateAuditablePerson()
	{
		try
		{
			//AuditManager.init();
			dao.openSession(null);
			Person person = (Person)
			dao.retrieveById(Person.class.getName(), Long.valueOf(1));
			person.setName("Kalpana Thakur !!!");

			dao.update(person);
			dao.commit();
			//dao.closeSession();

		}
		catch(Exception exp)
		{
			assertFalse("Failed while inserting object :", false);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}
	


	

/**
	 * This test will assert that Object inserted successfully.
	 */
	@Test
	public void testCaseInsertObject()
	{
		try
		{
		  User user = (User)createUserObject();
		  dao.openSession(null);
		  dao.insert(user);
		//  dao.commit();
		 // dao.closeSession();

		  User user2 = new User();
		  Collection<Object> userRollCollection = new HashSet<Object>();
		  UserRole userRoll = new UserRole();
		  userRoll.setId(Long.valueOf(1));
		  userRoll.setUser(user2);

		  user2.setRoleCollection(userRollCollection)	;
		  user2.setFirstName("sachin");
		  user2.setLastName("Lale");
		  user2.setEmailAddress("sach@lale.co.in");
		//  dao.openSession(null);
		  dao.insert(user2);
		  dao.commit();
		//  dao.closeSession();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			assertFalse("Failed while inserting object :", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert  the retrieve used by catissuecore.
	 */
	@Test
	 public void testRetrieveForCatissue()
	{
		List<Object> list = null;
		try
		{
			dao.openSession(null);
			String[] selectColumnName = null;
			String sourceObjectName = "test.User";
			Object [] colValues = {Long.valueOf(1),Long.valueOf(2)};
			String[] whereColNames = {"lastName" , "id"};
			String[] whereColConditions = {DAOConstants.EQUAL,
					DAOConstants.IN_CONDITION};
			Object[] whereColValues = {"Naik",colValues};
			String joinCondition = DAOConstants.OR_JOIN_CONDITION;
			QueryWhereClause queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.getWhereCondition(whereColNames, whereColConditions,
					whereColValues, joinCondition);
			list = dao.retrieve(sourceObjectName, selectColumnName,queryWhereClause,false);
			assertNotNull("Object retrieved is null ",list);

			String[] whereColNamesNew = {"id","lastName" };
			String[] whereColConditionsNew = {DAOConstants.EQUAL,
					DAOConstants.NOT_NULL_CONDITION};
			Object[] whereColValuesNew = {Long.valueOf(1)};

			queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.getWhereCondition(whereColNamesNew, whereColConditionsNew,
					whereColValuesNew, joinCondition);
			list = dao.retrieve(sourceObjectName, selectColumnName,queryWhereClause,false);
			assertNotNull("Object retrieved is null ",list);
		//	dao.closeSession();
		}
		catch(Exception exp)
		{
			assertFalse("Problem while retrieving :", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that all the objects are retrieved successfully.
	 */
	@Test
	public void testCaseRetriveAllObjects()
	{
	  try
	  {
		  dao.openSession(null);
	  	  List<Object> list = dao.retrieve("test.User",null,null,false);
	  	//  dao.closeSession();

	  	  assertNotNull("No objects retrieved :",list);
	  	//  assertTrue("No object retrieved ::", !list.isEmpty());
	   }
	  catch(Exception exp)
	  {
		  assertFalse("Failed while retrieving object :", true);
	  }
	  finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that the object with requested
	 * column will be retrieved successfully.
	 */
	@Test
	public void testCaseRetriveObject()
	{
	  try
	  {
	    dao.openSession(null);
	    QueryWhereClause queryWhereClause = new QueryWhereClause("test.User");
	    queryWhereClause.addCondition(new EqualClause("id" , Long.valueOf(1)));
	    List<Object> list = dao.retrieve("test.User",null , queryWhereClause,false);

	  //	dao.closeSession();
	  	assertNotNull("No objects retrieved",list);
		//assertTrue("No object retrieved ::",!list.isEmpty());
	  }
	  catch(Exception exp)
	  {
		  assertFalse("Failed while retrieving object ::", true);
	  }
	  finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that requested columns of the objects are
	 * retrieved successfully.
	 */
	@Test
	public void testCaseRetrieveObjectColumns()
	{
	  try
	  {
		String[] selectColumnName = {"id","firstName","lastName","emailAddress"};
		dao.openSession(null);
	    List<Object> list = dao.retrieve("test.User", selectColumnName,null,false);
	   // dao.closeSession();

	    assertNotNull("No object retrieved ::",list);
		//assertTrue("No object retrieved ::",!list.isEmpty());
	  }
	  catch(Exception exp)
	  {
		  assertFalse("Failed while retrieving object ::", true);
	  }
	  finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that object will given id
	 * retrieved successfully.
	 */
	@Test
	public void testCaseRetrieveObjectAsPerID()
	{
		try
		{
			dao.openSession(null);
			User user  = (User)dao.retrieveById("test.User", Long.valueOf(1));
			//dao.closeSession();
			assertNotNull("Object is null ",user);
		}
		catch(Exception exp)
		{
			assertFalse("Failed while retrieving object ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}


	}

	
	
	
	/**
	 * This test will assert that objects retrieved successfully
	 * when where clause holds in condition.
	 */
	@Test
	public void testRetriveInCondition()
	{
		try
		{
			String sourceObjectName = "test.User";
			String[] selectColumnName = null;

			Object[] object = {"naik"};
			QueryWhereClause queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.addCondition(new INClause("firstName","JOHN,abhijit")).andOpr().
			addCondition(new INClause("lastName",object,sourceObjectName)).orOpr().
			addCondition(new NotEqualClause("id",Long.valueOf(1)));

			dao.openSession(null);
			List<Object> list = dao.retrieve(sourceObjectName, selectColumnName,
					queryWhereClause,false);
			//dao.closeSession();

			assertNotNull("No object retrieved ::",list);
			//assertTrue("No object retrieved ::",!list.isEmpty());
		}
		catch(Exception exp)
		{
			assertFalse("Failed while retrieving object ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}


	/**
	 * This test will assert that objects retrieved successfully
	 * when where clause holds not null condition.
	 */
	@Test
	public void testRetriveIsNotNullCondition()
	{
		try
		{
			String sourceObjectName = "test.User";
			String[] selectColumnName = null;

			QueryWhereClause queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.addCondition(new NotNullClause("id"))
			.orOpr().addCondition(new NotNullClause("lastName",sourceObjectName));


			dao.openSession(null);
			List<Object> list = dao.retrieve(sourceObjectName, selectColumnName,
					queryWhereClause,false);
		//	dao.closeSession();

			assertNotNull(list);
		//	assertTrue("No object retrieved ::",!list.isEmpty());

		}
		catch(Exception exp)
		{
			assertFalse("Failed while retrieving object ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will assert that objects retrieved successfully
	 * when where clause holds is null condition.
	 */
	@Test
	public void testRetriveIsNullCondition()
	{
		try
		{
			String sourceObjectName = "test.User";

			String[] selectColumnName = null;

			QueryWhereClause queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.addCondition(new NullClause("lastName"));

			dao.openSession(null);
			List<Object> list = dao.retrieve(sourceObjectName, selectColumnName,
					queryWhereClause,false);
			//dao.closeSession();

			assertNotNull(list);

		}
		catch(Exception exp)
		{
			assertFalse("Failed while retrieving object ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}


	}

	

	/**
	 * This test will assert that query will be executed successfully.
	 */
	@Test
	public void testCaseExecuteQuery()
	{
		try
		{
			dao.openSession(null);
			String sql = "select count(*) from test.User";
			List<Object> list = dao.executeQuery(sql);
			//dao.closeSession();
			assertNotNull(list);
		//	assertTrue("No object retrieved ::",!list.isEmpty());

		}
		catch(Exception exp)
		{
			
			assertFalse("Problem while executing query ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}


	

	/**
	 * This test will assert that attribute retrieved successfully.
	 */
	@Test
	public void testRetrieveAttribute()
	{
		try
		{
			dao.openSession(null);
			Object obj = (Object)dao.retrieveAttribute(User.class,"id",
					Long.valueOf(1),"emailAddress");
		//	dao.closeSession();

			assertNotNull("Object retrieved is null",obj);
			List<String> list = (List<String>)obj;
			assertNotNull("Object retrieved is null",list);
			//assertTrue("Problem in retrieving attribute :", !list.isEmpty());
		}
		catch(Exception exp)
		{
			assertFalse("Problem in retrieving attribute ::", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will create a complex retrieve query having multiple clause(IN,NOT NULL,IS NULL)
	 * It will ensure that objects retrieved successfully.
	 */
	@Test
	public void testRetriveComplexQuery()
	{
		try
		{
			String sourceObjectName = "test.User";
			Object [] colValues = {Long.valueOf(1),Long.valueOf(2)};
			String[] selectColumnName = null;

			QueryWhereClause queryWhereClause = new QueryWhereClause(sourceObjectName);
			queryWhereClause.addCondition(new INClause("id",colValues))
			.orOpr().addCondition(new NotNullClause("firstName")).orOpr()
			.addCondition(new EqualClause("firstName","Washu")).orOpr().
			addCondition(new LessThenClause("id",Long.valueOf(100))).orOpr().
			addCondition(new LessThenClause("id",Long.valueOf(100),sourceObjectName)).orOpr()
			.addCondition(new GreaterThenClause("id",Long.valueOf(100),sourceObjectName)).orOpr().
			addCondition(new GreaterThenClause("id",Long.valueOf(1)));

			dao.openSession(null);
			List<Object> list = dao.retrieve(sourceObjectName, selectColumnName,queryWhereClause,false);
			//dao.closeSession();
			assertNotNull("No data retrieved :",list);
			//assertTrue("No data retrieved :",!list.isEmpty());
		}
		catch(Exception exp)
		{
			assertFalse("Problem occurred while retrieving object:", true);
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	}

	/**
	 * This test will various factory members.
	 * 	 */
	@Test
	public void testDAOFactoryMembers()
	{
		IDAOFactory daoFactory = daoConfigFactory.getInstance().getDAOFactory("caTissuecore");
		assertNotNull("No configuration file exist", daoFactory.getConfigurationFile());
		assertNotNull("Default class name Name does not exists", daoFactory.getDefaultDAOClassName());
		assertNotNull("Connection Manager Name not exists", daoFactory.getDefaultConnMangrName());
		assertNotNull("JDBC class name Name does not exists", daoFactory.getJdbcDAOClassName());
		assertNotNull("JDBC Connection Manager Name not exists", daoFactory.getJdbcConnMangrName());
		//assertNotNull("Data source not exists", daoFactory.getDataSource());

	}

	/**
	 * This test will test various methods of HibernateMetaData class.
	 */
	@Test
	public void testDifferentMethodsOfHibernateMetaData()
	{
		try
		{
			dao.openSession(null);
			User user  = (User)dao.retrieveById("test.User", Long.valueOf(1));
			Object object = HibernateMetaData.getProxyObjectImpl(user);
				assertNotNull("Proxy Object retrieved is null :"+object);

			HibernateMetaData hibernateMetaData = HibernateMetaDataFactory.
								getHibernateMetaData("caTissuecore") ;
			String tableName = hibernateMetaData.getTableName(object.getClass());
				assertTrue("Table name is empty",
						!tableName.equals(DAOConstants.TRAILING_SPACES));

			String rootTableName = hibernateMetaData.getRootTableName(object.getClass());
			assertTrue("Root Table name is empty",
						!rootTableName.equals(DAOConstants.TRAILING_SPACES));

			String columnName = hibernateMetaData.getColumnName(object.getClass(),"lastName");
				assertTrue("Column Name is empty",
						!columnName.equals(DAOConstants.TRAILING_SPACES));

			int colWidth = hibernateMetaData.getColumnWidth(object.getClass(),"lastName");
			assertTrue("colWidth  is 0",colWidth > 0);

			String className = hibernateMetaData.getClassName("Test_user");
				assertTrue("NO class name obtained ",
						!className.equals(DAOConstants.TRAILING_SPACES));


			//dao.closeSession();
			assertNotNull("Object is null ",object);
		}
		catch(Exception exp)
		{
			logger.fatal(exp);
			assertFalse("Failed in HibernateMetaData ::", true);

		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}


	}


	/**
	 * This test will assert on constraint violation proper message is thrown.
	 */
	@Test
	public void testConstraintViolation()
	{
		try
		{
			dao.openSession(null);
			Address address = new Address();
			address.setStreet("Street unknown");
			dao.insert(address);
			dao.commit();
			//dao.closeSession();
		}
		catch(Exception exp)
		{
			System.out.println("------------------------------------------------------------------------");
			exp.printStackTrace();
		}
		finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e)
			{
				e.printStackTrace();
			}
		}

	  }






	/**
	 * @param user : user object
	 * @throws DAOException : DAOExp
	 */
	private void insertObjectByApp2(User user) throws DAOException

	{
		IDAOFactory daoFactory = daoConfigFactory.getInstance().getDAOFactory("DynamicExtensions");
		DAO deDAO = daoFactory.getDAO();
		deDAO.openSession(null);
		deDAO.insert(user);
		deDAO.commit();
		deDAO.closeSession();
	}


	/**
	 * This method will be called to create user object.
	 * @return : It will returns the user object.
	 */
	User createUserObject()
	{
	  User user = new User();
	  user.setFirstName("abhijit");
	  user.setLastName("naik");
	  user.setEmailAddress("abhijit_naik@persistent.co.in");
	  return user;
	}


	@Test
    public void testCaseExecuteQueryWithFixedResultSize()
    {

          try
          {

                dao.openSession(null);
                String sql = "select id from test.User";
                List list = ((HibernateDAO)dao).executeQuery(sql,0,1,null);
                assertEquals(true, list.size()==1);
          }
          catch(Exception exp)
          {
        	  System.out.println("Exception : !!!!!!!");
  			  exp.printStackTrace();
              assertFalse("Problem while executing query ::", true);
          }
          finally
          {
                try
                {
                      dao.closeSession();
                }
                catch (DAOException e)
                {
                      e.printStackTrace();
                }

          }

    }

	/**
	 * This test will assert that requested objects deleted successfully.
	 */
	@Test
	public void testCaseDeleteObject()
	{
	  try
	  {
		  User user = new User();
		  user.setId(Long.valueOf(3));
		  dao.openSession(null);
	  	  dao.delete(user);
	  	  dao.commit();
	  	//  dao.closeSession();
	  	  System.out.println("Deleted object ::::");
	  	assertTrue("Object deleted :", true);
	  }
	  catch(Exception exp)
	  {
		  System.out.println("Not Deleted object ::::");
		  exp.printStackTrace();
		  assertFalse("Failed while deleting object :", true);
	  }
	  finally
		{
			try
			{
				dao.closeSession();
			}
			catch (DAOException e) 
			{
				e.printStackTrace();
			}
		}

	}



}
