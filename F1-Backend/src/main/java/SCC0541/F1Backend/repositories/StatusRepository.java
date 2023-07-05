package SCC0541.F1Backend.repositories;

import SCC0541.F1Backend.database.SQLScripts;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.QuantidadeStatusDTO;
import SCC0541.F1Backend.models.StatusModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class StatusRepository{

    private final EntityManagerFactory entityManagerFactory;

    public StatusRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Collection<StatusModel> getAllStatus() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.FIND_ALL_STATUS
            );

            List<Object[]> tuplas = query.getResultList();

            ArrayList<StatusModel> responseList = new ArrayList<>();


            for (Object[] tupla : tuplas) {

                responseList.add(
                        StatusModel.builder()
                                .statusId(tupla[0] != null ? Integer.valueOf(tupla[0].toString()) : null)
                                .status(tupla[1] != null ? tupla[1].toString() : null)
                                .build()
                );
            }


            return responseList;

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        } finally {
            entityManager.close();
        }
    }


    public List<QuantidadeStatusDTO> recuperarRelatorioQuantidadeStatus(){

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.GET_RESULTS_COUNT_BY_STATUS
            );

            List<Object[]> tuplas = query.getResultList();

            ArrayList<QuantidadeStatusDTO> responseList = new ArrayList<>();

            for (Object[] tupla : tuplas) {
                responseList.add(
                        QuantidadeStatusDTO.builder()
                                .status(tupla[0] != null ? tupla[0].toString() : null)
                                .quantidadeDeResultados(tupla[1] != null ? Integer.valueOf(tupla[1].toString()) : null)
                                .build()
                );
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