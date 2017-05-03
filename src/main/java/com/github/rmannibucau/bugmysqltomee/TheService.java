package com.github.rmannibucau.bugmysqltomee;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@WebService
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TheService implements TheWebService {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private DBController db;

    public TheEntity newInstance() {
        final TheEntity entity = new TheEntity();
        em.persist(entity);
        db.stop();
        return entity;
    }
}
