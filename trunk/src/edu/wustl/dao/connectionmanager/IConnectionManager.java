package edu.wustl.dao.connectionmanager;

import java.sql.Connection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.wustl.common.util.dbmanager.DAOException;

public interface IConnectionManager
{

	Connection getConnection() throws HibernateException;
	
	void closeConnection() throws HibernateException;
	
	Session newSession() throws HibernateException;
	
	void closeSession() throws HibernateException;
	
	Session currentSession() throws HibernateException;
	
	Session getCleanSession() throws DAOException;
	
	void setApplicationName(String applicationName);
	
	String getApplicationName();
	
	void setSessionFactory(SessionFactory sessionFactory);
	
	SessionFactory getSessionFactory();
	
	void setConfiguration(Configuration cfg);
	
	Configuration getConfiguration();
	
}
