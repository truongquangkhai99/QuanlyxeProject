package uis;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import dao.QuanLyAccount;
import dao.QuanLyNhanVien;
import entities.Account;
import entities.NhanVien;


public class MainClass {
	public static void main(String[] args) {
//		List<Account> accs=QuanLyAccount.showTatCaAccount();
		List<NhanVien> accs=QuanLyNhanVien.showTatCaNhanVien();
		accs.forEach(t->{
			System.out.println(t);
		});
	}
	
	public static List<Account> showTatCaAccount() {
		List<Account> accs = null;
		EntityManager manager = Persistence.createEntityManagerFactory("DeAnQuanLyXeFix").createEntityManager();
		EntityTransaction transaction = null;
		try {
			transaction = manager.getTransaction();
			transaction.begin();
			accs=manager.createQuery("select s from Account s",Account.class).getResultList();
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
}
