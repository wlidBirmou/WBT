/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nrz.fairHandler.jpaController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import nrz.fairHandler.jpa.Weight;
import nrz.fairHandler.jpaController.exceptions.NonexistentEntityException;
import nrz.java.persistance.FQuery;

/**
 *
 * @author rahimAdmin
 */
public class WeightJpaController implements Serializable {

    public WeightJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Weight weight) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(weight);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Weight weight) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            weight = em.merge(weight);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = weight.getIdWeight();
                if (findWeight(id) == null) {
                    throw new NonexistentEntityException("The weight with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weight weight;
            try {
                weight = em.getReference(Weight.class, id);
                weight.getIdWeight();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The weight with id " + id + " no longer exists.", enfe);
            }
            em.remove(weight);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Weight> findWeightEntities() {
        return findWeightEntities(true, -1, -1);
    }

    public List<Weight> findWeightEntities(int maxResults, int firstResult) {
        return findWeightEntities(false, maxResults, firstResult);
    }

    private List<Weight> findWeightEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Weight.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Weight findWeight(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Weight.class, id);
        } finally {
            em.close();
        }
    }

    public int getWeightCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Weight> rt = cq.from(Weight.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List getWeightStep(int step, String filter, String dateDebut, String dateFin) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery(FQuery.getQueryDateSearch("Weight w left join w.article a", "w.reference w.type a.articleCode "
                + "a.designation a.categorie", filter, "w.firstTicketDate", dateDebut, dateFin) + " ORDER by w.idWeight DESC");
        query.setFirstResult(step);
        query.setMaxResults(50);

        return query.getResultList();
    }
    public List getWeights(String filter, String dateDebut, String dateFin){
         EntityManager em = this.getEntityManager();
        Query query = em.createQuery(FQuery.getQueryDateSearch("Weight w left join w.article a", "w.reference w.type a.articleCode "
                + "a.designation a.categorie", filter, "w.firstTicketDate", dateDebut, dateFin) + " ORDER by w.idWeight DESC");
        query.setFirstResult(0);
        query.setMaxResults(Integer.MAX_VALUE);
        return query.getResultList();
    }

    public static List getWeights() {
        List weights=new ArrayList();
        Weight weight1=new Weight();
        weight1.setNetWeight(9000.);
        weight1.setTare(10000.);
        weight1.setReference("1111");
        weights.add(weight1);
        return weights;
    }

    public Weight getWeight(int weightID) {
        EntityManager em = this.getEntityManager();
        Query query = em.createQuery("SELECT w FROM Weight w where w.idWeight=" + weightID);
        return (Weight) query.getSingleResult();
    }
}
