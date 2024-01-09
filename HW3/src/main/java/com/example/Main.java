package com.example;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.openSession();
            int[] li = {1,1,2,1,3,1,4,4,5,4,6,6};
            TreesDAO.insert(session, li);
            TreeGUI treeGUI = new TreeGUI(session);
            treeGUI.setVisible(true);
            while (treeGUI.isVisible()) {
                TimeUnit.SECONDS.sleep(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
