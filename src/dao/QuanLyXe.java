package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entities.Account;
import entities.Xe;

public class QuanLyXe {
	public static Xe timMa(String ma) {
		Xe accs = null;
		EntityManager manager = Persistence.createEntityManagerFactory("DeAnQuanLyXeFix").createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = manager.getTransaction();
			transaction.begin();
			accs=manager.find(Xe.class, ma);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
		return accs;
	}
	public static List<Xe> showTatCaXe() {
		List<Xe> accs = null;
		EntityManager manager = Persistence.createEntityManagerFactory("DeAnQuanLyXeFix").createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = manager.getTransaction();
			transaction.begin();
			accs=manager.createQuery("select s from Xe s",Xe.class).getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
		} finally {
			manager.close();
		}
		return accs;
	}
	public static boolean xoaXe(String username) {
		EntityManager manager = Persistence.createEntityManagerFactory("DeAnQuanLyXeFix").createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = manager.getTransaction();
			transaction.begin();
			Xe acc=manager.find(Xe.class, username);//			manager.r
			manager.remove(acc);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}
	public static boolean themXe(Xe acc) {
		
		EntityManager manager = Persistence.createEntityManagerFactory("DeAnQuanLyXeFix").createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(acc);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
			return false;
		} finally {
			manager.close();
		}
		return true;
	}
}