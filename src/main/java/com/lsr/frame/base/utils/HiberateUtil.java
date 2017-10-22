package com.lsr.frame.base.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HiberateUtil {
	private static final Log log = LogFactory.getLog(HiberateUtil.class);
	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	
	static {
		try {
			configuration = new Configuration();
			sessionFactory = configuration.configure().buildSessionFactory();
		} catch (Throwable ex) {
		    ex.printStackTrace();
			log.error(ex.getMessage());
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Configuration getConfiguration() {
		return configuration;
	}
	
	public static void rebuildSessionFactory() {

		synchronized (sessionFactory) {
			try {
				sessionFactory = getConfiguration().buildSessionFactory();
			} catch (Exception ex) {
				log.error(ex.getMessage());

			}
		}
	}

	public static void rebuildSessionFactory(Configuration cfg) {
		synchronized (sessionFactory) {
			try {
				sessionFactory = cfg.buildSessionFactory();
				configuration = cfg;
			} catch (Exception ex) {
				log.error(ex.getMessage());

			}
		}
	}

	public static Session getSession() {
		try { 
			Session session = sessionFactory.openSession();
			return session;
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
	}
	
	public static void close() {
		try {
			sessionFactory.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	public static void closeSession(Session session) {
		try {
			if(session == null)
				return;
			session.close();
		} catch (Exception ex) {
			log.error(ex.getMessage());

		}
	}
	
	public static void rollbackTransaction(Transaction transaction) {
		try {
			if (transaction != null)
				transaction.rollback();
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
}
