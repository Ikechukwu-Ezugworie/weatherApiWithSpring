package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.models.PortalAccount;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.PortalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class PortalAccountServiceImpl implements PortalAccountService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    AccessService accessService;




    public PortalAccount findbyPortalUser(){
//        entityManager.createQuery("select p from  PortalUser p where p.username = :name", PortalUser.class).setParameter("name","tobi");
        PortalUser portalUser = accessService.getPrincipal();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PortalAccount> cq = criteriaBuilder.createQuery(PortalAccount.class);
        Root<PortalAccount> portalAccountRoot = cq.from(PortalAccount.class);
        cq.select(portalAccountRoot).where(criteriaBuilder.equal(portalAccountRoot.get("portalUser"),portalUser));
        List<PortalAccount> portalAccounts = entityManager.createQuery(cq).getResultList();
        if(portalAccounts.isEmpty()){
            return null;
        }


        return entityManager.find(PortalAccount.class,portalUser.getPortalAccount().getId());

    }

}
