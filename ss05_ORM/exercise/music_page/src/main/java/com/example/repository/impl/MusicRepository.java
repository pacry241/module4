package com.example.repository.impl;

import com.example.model.Music;
import com.example.repository.IMusicRepository;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MusicRepository implements IMusicRepository {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Music> findAll() {
        String queryStr = "Select s from Music as s";
        TypedQuery<Music> query = entityManager.createQuery(queryStr, Music.class);
        return query.getResultList();
    }

    @Override
    public void save(Music music) {
        entityManager.getTransaction().begin();
        entityManager.persist(music);
        entityManager.getTransaction().commit();
    }

    @Override
    public Music findById(int id) {
        return entityManager.find(Music.class, id);
    }


    @Override
    public void update(Music music) {
        entityManager.merge(music);
    }

    @Override
    public void delete(Music music) {
        entityManager.getTransaction().begin();
        entityManager.remove(music);
        entityManager.getTransaction().commit();
    }
}
