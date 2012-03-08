
package edu.wustl.dao.newdao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.io.DOMWriter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.XMLHelper;
import org.xml.sax.InputSource;

import edu.wustl.common.util.logger.Logger;
import edu.wustl.dao.daofactory.ApplicationDAOPropertiesParser;
import edu.wustl.dao.daofactory.IDAOFactory;
import edu.wustl.dao.util.HibernateMetaDataFactory;

public class SessionFactoryHolder
{

	/**
	* LOGGER Logger - class logger.
	*/
	private static final Logger logger = Logger.getCommonLogger(SessionFactoryHolder.class);
	
	private Map<String, SessionFactory> sessionFactoryMap;

	private static SessionFactoryHolder sessionFactoryHolderInstance = new SessionFactoryHolder();
	
	private SessionFactoryHolder()
	{
	
	}

	public static synchronized SessionFactoryHolder getInstance()
	{
		if(sessionFactoryHolderInstance==null)
		{
			sessionFactoryHolderInstance = new SessionFactoryHolder();
		}
		return sessionFactoryHolderInstance;
	}
	
	public void load(String daoConfigurationFileName) throws Exception
	{
		load(initializeConfigurationMap(daoConfigurationFileName));
	}
	
	public void load(Map<String, String> hbmCfgsMap) throws Exception
	{
		for (Map.Entry<String, String> entry : hbmCfgsMap.entrySet())
		{
			createSessionFactory(entry.getKey(), entry.getValue());
		}
	}
	
//	public SessionFactoryHolder(Map<String, String> hbmCfgsMap) throws DAOException
//	{
//		initialize(hbmCfgsMap);
//	}

	public SessionFactory getSessionFactory(String name)
	{
		return sessionFactoryMap.get(name);
	}


	private void createSessionFactory(String applicationName, String hbmCfg) throws Exception
	{
		Configuration configuration = createConfiguration(hbmCfg);
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		HibernateMetaDataFactory.setHibernateMetaData(applicationName,
				configuration);
		sessionFactoryMap.put(applicationName, sessionFactory);
	}
	
	private Configuration createConfiguration(String configurationfile) throws Exception
	{

		Configuration configuration = new Configuration();
	    configuration.configure(SessionFactoryHolder.class.getResource(configurationfile));
	    return configuration;
  
	}
	
	private Map<String, String> initializeConfigurationMap(String daoConfigurationFileName) throws Exception
	{
		Map<String, String> configurationMap = new HashMap<String, String>();
		ApplicationDAOPropertiesParser applicationPropertiesParser =
			new ApplicationDAOPropertiesParser(daoConfigurationFileName);
		Map<String, IDAOFactory> daoFactoryMap = applicationPropertiesParser.getDaoFactoryMap();
		
		Iterator<String> mapKeySetIterator = daoFactoryMap.keySet().iterator();
		while(mapKeySetIterator.hasNext())
		{
			String appName = mapKeySetIterator.next();
			IDAOFactory daoFactory = (IDAOFactory)daoFactoryMap.get(appName);
			configurationMap.put(appName, daoFactory.getConfigurationFile());
		}
		return configurationMap;
	}
}