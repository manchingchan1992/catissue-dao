/*
 * TODO
 */
package edu.wustl.dao;

import java.sql.Connection;

import edu.wustl.common.dao.queryExecutor.PagenatedResultData;
import edu.wustl.common.exception.ErrorKey;
import edu.wustl.common.util.QueryParams;
import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.exception.DAOException;
import edu.wustl.dao.formatmessage.OracleFormatter;
import edu.wustl.dao.util.DAOConstants;
import edu.wustl.dao.util.DatabaseConnectionParams;
import edu.wustl.query.executor.OracleQueryExecutor;


/**
 * @author kalpana_thakur
 *
 */
public class OracleDAOImpl extends AbstractJDBCDAOImpl
{

	/**
	 * Logger.
	 */
	private static org.apache.log4j.Logger logger = Logger.getLogger(OracleDAOImpl.class);

	/**
	 * Deletes the specified table.
	 * @param tableName : Name of table to be deleted.
	 * @throws DAOException : DAOException.
	 */
	public void delete(String tableName) throws DAOException
	{
		DatabaseConnectionParams databaseConnectionParams = new DatabaseConnectionParams();
		try
		{

			databaseConnectionParams.setConnection(getConnection());

			StringBuffer query = new StringBuffer("select tname from tab where tname='"
					+ tableName + "'");
			boolean isTableExists = databaseConnectionParams.isResultSetExists(query.toString());

			logger.debug("ORACLE :" + query.toString() + isTableExists);

			if (isTableExists)
			{

				logger.debug("Drop Table");
				databaseConnectionParams.executeUpdate("DROP TABLE " +
						tableName + " cascade constraints");
			}

		}
		catch(Exception sqlExp)
		{

			logger.error(sqlExp.getMessage(), sqlExp);
			ErrorKey errorKey = ErrorKey.getErrorKey("db.operation.error");
			throw new DAOException(errorKey,sqlExp,"OracleDAOImpl.java"+DAOConstants.DELETE_OBJ_ERROR);

		}
		finally
		{
			databaseConnectionParams.closeConnectionParams();
		}
	}


	/**
	 * @return : This method will return Date Pattern.
	 */
	public String getDatePattern()
	{

		return "mm-dd-yyyy";
	}

	/**
	 * @return : This method will return Time Pattern.
	 */
	public String getTimePattern()
	{

		return "hh-mi-ss";
	}
	/**
	 * @return : This method will return Date Format function.
	 */
	public String getDateFormatFunction()
	{
		return "TO_CHAR";
	}
	/**
	 * @return : This method will return Time format function.
	 */
	public String getTimeFormatFunction()
	{
		return "TO_CHAR";
	}

	/**
	 * @return : This method will return Date to String function.
	 */
	public String getDateTostrFunction()
	{
		return "TO_CHAR";
	}

	/**
	 * @return : This method will return String to Date function.
	 */
	public String getStrTodateFunction()
	{
		return "TO_DATE";
	}

	/**
	 * This method executed query, parses the result and returns List of rows after doing security checks
	 * for user's right to view a record/field.
	 * @param queryParams : TODO
	 * @return This will return the PagenatedResultData.
	 * @throws DAOException :DAOException
	 */
	public PagenatedResultData getQueryResultList(QueryParams queryParams) throws DAOException
	{
		PagenatedResultData pagenatedResultData = null;
		try
		{
			queryParams.setConnection(getConnectionManager().getConnection());
			OracleQueryExecutor oracleQueryExecutor = new OracleQueryExecutor();
			pagenatedResultData = oracleQueryExecutor.getQueryResultList(queryParams);
		}
		catch(Exception exp)
		{
			ErrorKey errorKey = ErrorKey.getErrorKey("db.operation.error");
			throw new DAOException(errorKey,exp,"OracleDAOImpl.java :"+
					DAOConstants.EXECUTE_QUERY_ERROR);
		}

		return pagenatedResultData;

	}

	/**
	 * @param excp : Exception Object.
	 * @param connection :
	 * @return : It will return the formated messages.
	 */
	public String formatMessage(Exception excp,Connection connection)
	{

		OracleFormatter oracleFormatter = new OracleFormatter();
		return oracleFormatter.getFormatedMessage(excp,connection);
	}

}
