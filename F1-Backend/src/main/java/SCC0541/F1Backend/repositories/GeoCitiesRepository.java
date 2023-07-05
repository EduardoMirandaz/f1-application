package SCC0541.F1Backend.repositories;

import SCC0541.F1Backend.database.SQLScripts;
import SCC0541.F1Backend.models.GeoCitiesModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GeoCitiesRepository{

    private final EntityManagerFactory entityManagerFactory;

    public GeoCitiesRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<GeoCitiesModel> getLatitudeLongitudeByName(String nomeCidade){

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.FIND_CITY_BY_NAME
            );

            query.setParameter("nomeCidade", nomeCidade);

            List<Object[]> tuplas = query.getResultList();

            ArrayList<GeoCitiesModel> responseList = new ArrayList<>();

            if(tuplas != null){
                for (Object[] tupla : tuplas) {
                    responseList.add(
                            GeoCitiesModel.builder()
                            .name(tupla[1].toString())
                            .lat(Double.valueOf(tupla[4].toString()))
                            .lon(Double.valueOf(tupla[5].toString()))
                            .build()
                    );
                }
            }

            return responseList;

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        } finally {
            entityManager.close();
        }
    }

}