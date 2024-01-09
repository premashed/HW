package org.example.server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/trees")
public class TreeController {
    private final SessionFactory sessionFactory;

    TreeController() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        int[] li = {1,1,2,1,3,1,4,4,5,4,6,6};
        TreesDAO.insert(sessionFactory.openSession(), li);
    }

    @PostMapping
    public void write(@RequestBody List<TransferNode> nodes) {
        try {TreesDAO.writeTable(sessionFactory.openSession(), nodes);
        } catch (Exception e) {
            int[] li = {1,1,2,1,3,1,4,4,5,4,6,6};
            TreesDAO.insert(sessionFactory.openSession(), li);
        }

    }

    @GetMapping
    public List<TransferNode> read() {
        return TreesDAO.readTable(sessionFactory.openSession());
    }
}