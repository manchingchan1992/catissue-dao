/*L
 * Copyright Washington University in St. Louis, SemanticBits, Persistent Systems, Krishagni.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/catissue-dao/LICENSE.txt for details.
 */

/**
 *<p>Title: IConnectionManager Interface>
 * <p>Description:	Pluggable IConnectionManager allows developer to select
 * ConnectionManager at run time.It handles all
 * hibernate specific operations like opening and closing of hibernate connection, session etc</p>
 * @author kalpana_thakur
 * @version 1.0
 */
package edu.wustl.dao.connectionmanager;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.wustl.dao.exception.DAOException;


/**
 * @author kalpana_thakur
 * Manages connection (session) with a specific
 * database.
 */
public interface IConnectionManager
{

	/**
	 * A connection (session) with a specific database
	 * having auto-commit mode as disabled.
	 * mandatory to call commit to update the changes.
	 * @return : the connection.
	 * @throws DAOException :database exception.
	 */
	Connection getConnection() throws DAOException;

	/**
	 * Releases a Connection's database and JDBC resources.
	 * @throws DAOException :database exception.
	 */
	void closeConnection() throws DAOException;

	/**
	 * Create database connection having auto-commit mode as disabled.
	 * Open a Session on the given connection.
	 * mandatory to call commit to update the changes.
	 * @return : the session
	 * @throws DAOException :
	 */
	Session newSession() throws DAOException;

	/**
	 * End the session by releasing the JDBC connection and cleaning up.
	 * @throws DAOException : database exception.
	 */
	void closeSession() throws DAOException;

	/**
	 * Obtains the current session.
	 * connection associated to the session have auto-commit mode as disabled.
	 * mandatory to call commit to update the changes.
	 * @return : the session.
	 * @throws DAOException : database exception.
	 */
	Session currentSession() throws DAOException;

	/**
	 * Set the application name.
	 * @param applicationName : Name of the Application
	 */
	void setApplicationName(String applicationName);

	/**
	 * @return This will return the application name.
	 */
	String getApplicationName();

	/**
	 * Set the sessionFactory.
	 * Application obtain Sessions from the factory.
	 * @param sessionFactory :sessionFactory
	 */
	void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * It will returns the sessionFactory.
	 * @return SessionFactory
	 */
	SessionFactory getSessionFactory();

	/**
	 * Set the configuration.
	 * @param cfg : Configuration
	 */
	void setConfiguration(Configuration cfg);

	/**
	 * Get the configuration.
	 * @return Configuration.
	 */
	Configuration getConfiguration();

	/**
	 * This method will be called to get the data source.
	 * @return dataSource
	 */
	 String getDataSource();

	 /**
	 * This method will be called to set the data source.
	 * @param dataSource : JDBC connection name.
	 */
	 void setDataSource(String dataSource);

	 /**
	 * Makes all changes made since the previous
     * commit/rollback.
     * This method should be used only when auto-commit mode has been disabled.
	 * @throws DAOException : It will throw DAOException.
	 */
	 void commit() throws DAOException;

	 /**
	 * Undoes all changes made in the current transaction
	 * This method should be used only when auto-commit mode has been disabled.
	 * @throws DAOException : It will throw DAOException.
	 */
	 void rollback() throws DAOException;

	 /**
	 *This method will be called to retrieved the clean connection.
	 *connections will be in auto-commit mode.
	 *@return Connection object
	 *@throws DAOException :Generic DAOException.
	 */
	 Connection getCleanConnection() throws DAOException;

	 /**
	 *Releases this Connection object's database and JDBC resources.
	 *@throws DAOException :Generic DAOException.
	 */
	void closeCleanConnection() throws DAOException;

	/**
	 *End the session by releasing the JDBC connection and cleaning up.
	 *@throws DAOException :Generic DAOException.
	 */
	void closeCleanSession() throws DAOException;

	/**
	 * Create database connection and open the new session on the given connection.
	 * connections will be in auto-commit mode.
	 * @return : clean session.
	 * @throws DAOException :
	 */
	Session getCleanSession() throws DAOException;

}
