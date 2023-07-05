package SCC0541.F1Backend.repositories;

import SCC0541.F1Backend.database.SQLScripts;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.CloseAirportsDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.CloseAirportsResponseDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.FoundCitiesByName;
import SCC0541.F1Backend.models.AirportModel;
import SCC0541.F1Backend.models.ConstructorModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AirportRepository{

    private final EntityManagerFactory entityManagerFactory;

    public AirportRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<CloseAirportsResponseDTO> getCloseAirports(List<FoundCitiesByName> foundCitiesByName){

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        if(foundCitiesByName == null) return null;

        ArrayList<CloseAirportsResponseDTO> responseList = new ArrayList<>();

        try {

            for (FoundCitiesByName fc : foundCitiesByName){
                try{

                    CloseAirportsResponseDTO responseAirportDTO = new CloseAirportsResponseDTO(fc.getNomeCidade(), fc.getLatitude(), fc.getLongitude());

                    if (!entityManager.getTransaction().isActive()) {
                        entityManager.getTransaction().begin();
                    }


                    Query query = entityManager.createNativeQuery(
                            SQLScripts.GET_CLOSE_AIRPORTS
                    );

                    query.setParameter("latitude", fc.getLatitude());
                    query.setParameter("longitude", fc.getLongitude());

                    List<Object[]> tuplas = query.getResultList();

                    responseAirportDTO.setAeroportosProximosEncontrados(tuplas.size());

                    ArrayList<CloseAirportsDTO> closeAirportsDTOS = new ArrayList<>();


                    for (Object[] tupla : tuplas) {
                        closeAirportsDTOS.add(
                                CloseAirportsDTO.builder()
                                        .nomeCidade(tupla[0] != null ? tupla[0].toString() : null)
                                        .codigoIATA(tupla[1] != null ? tupla[1].toString() : null)
                                        .nomeAeroporto(tupla[2] != null ? tupla[2].toString() : null)
                                        .cidadeAeroporto(tupla[3] != null ? tupla[3].toString() : null)
                                        .distanciaEmKM(tupla[4] != null ? Double.valueOf(tupla[4].toString()) : null)
                                        .tipoAeroporto(tupla[5] != null ? tupla[5].toString() : null)
                                        .build()
                        );
                    }


                    responseAirportDTO.setAeroportosProximos(closeAirportsDTOS);

                    responseList.add(responseAirportDTO);

                } catch (Exception exception) {
                    entityManager.getTransaction().rollback();
                    throw exception;
                }
            }
        } finally {
            entityManager.close();
        }


        return responseList;


    }


    public List<AirportModel> getAllAirports() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.GET_ALL_AIRPORTS
            );

            List<Object[]> tuplas = query.getResultList();

            ArrayList<AirportModel> responseList = new ArrayList<>();


            for (Object[] tupla : tuplas) {

                responseList.add(
                        AirportModel.builder()
                                .ident(tupla[0] != null ? tupla[0].toString() : null)
                                .type(tupla[1] != null ? tupla[1].toString() : null)
                                .name(tupla[2] != null ? tupla[2].toString() : null)
                                .latDeg(tupla[3] != null ? Double.valueOf(tupla[3].toString()) : null)
                                .longDeg(tupla[4] != null ? Double.valueOf(tupla[4].toString()) : null)
                                .elevFt(tupla[5] != null ? Integer.valueOf(tupla[5].toString()) : null)
                                .continent(tupla[6] != null ? tupla[6].toString() : null)
                                .iSOCountry(tupla[7] != null ? tupla[7].toString() : null)
                                .iSORegion(tupla[8] != null ? tupla[8].toString() : null)
                                .city(tupla[9] != null ? tupla[9].toString() : null)
                                .scheduled_service(tupla[9] != null ? tupla[9].toString() : null)
                                .gpscode(tupla[10] != null ? tupla[10].toString() : null)
                                .iatacode(tupla[11] != null ? tupla[11].toString() : null)
                                .localCode(tupla[12] != null ? tupla[12].toString() : null)
                                .homeLink(tupla[13] != null ? tupla[13].toString() : null)
                                .wikipediaLink(tupla[14] != null ? tupla[14].toString() : null)
                                .keywords(tupla[15] != null ? tupla[15].toString() : null)
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
