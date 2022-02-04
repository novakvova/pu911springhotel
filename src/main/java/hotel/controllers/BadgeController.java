package hotel.controllers;

import entities_postgres.BadgeP;
import hotel.entities.Badge;
import hotel.entities.Region;
import hotel.mapper.RegionMapper;
import hotel.repositories.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.HibernateSessionFactoryUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges/")
public class BadgeController {
    private final BadgeRepository regionRepository;
    //private final RegionMapper regionMapper;

    @GetMapping("/{id}")
    public Badge getById(@PathVariable("id") int id) {
        return regionRepository.findById(id).get();
    }

    @GetMapping("/all")
    public double getById(int page, int count) {
        Sort sort = Sort.by("Id");
        long time = System.nanoTime();

        Pageable pageable = PageRequest.of(page,count,sort);
        Page<Badge> list = regionRepository.findAll(pageable);

        SessionFactory sessionFactory =null;
        Session session = null;
        Transaction tx=null;
        try {
            sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
            session = sessionFactory.openSession();
            tx=session.beginTransaction();
            for (Badge badge: list.getContent()) {
                BadgeP badgeP = new BadgeP();
                badgeP.setId(badge.getId());
                badgeP.setName(badge.getName());
                badgeP.setDate(badge.getDate());
                badgeP.setUserId(badge.getUserId());
                session.save(badgeP);
            }
            tx.commit();

        }catch(Exception ex) {
            System.out.println("Exception "+ ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed())
            {
                System.out.println("Closing SessionFactory");
                //sessionFactory.close();
            }
        }

        long result = System.nanoTime() - time;
        double second=result/1000000000.0;
        System.out.println((System.nanoTime() - time) + "ns per million");
        System.out.println(second+ "seconds");

        return second;
    }
}
