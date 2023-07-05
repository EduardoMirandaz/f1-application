package SCC0541.F1Backend.repositories;

import SCC0541.F1Backend.database.SQLScripts;
import SCC0541.F1Backend.dtos.CreateDriverDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_2.CloseAirportsDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5.ResultadoGeralDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5.ResultadoPilotoDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5.ResultadoPorAnoDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5.ResultadoPorCorridaDTO;
import SCC0541.F1Backend.models.DriverModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class DriverRepository{
    private final EntityManagerFactory entityManagerFactory;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DriverRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createDriver(CreateDriverDTO driverDTO) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            jakarta.persistence.Query query = entityManager.createNativeQuery(
                    SQLScripts.DRIVER_INSERT
            );

            query.setParameter("driverRef",driverDTO.getDriverRef());
            query.setParameter("number",driverDTO.getNumber());
            query.setParameter("code",driverDTO.getCode());
            query.setParameter("forename",driverDTO.getForename());
            query.setParameter("surname",driverDTO.getSurname());
            query.setParameter("dob",driverDTO.getDob());
            query.setParameter("nationality",driverDTO.getNationality());

            query.executeUpdate();

            entityManager.getTransaction().commit();

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        } finally {
            entityManager.close();
        }
    }

    public ArrayList<DriverModel> findAll() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.FIND_ALL_DRIVERS
            );

            List<Object[]> tuplas = query.getResultList();

            ArrayList<DriverModel> responseList = new ArrayList<>();


            for (Object[] tupla : tuplas) {

                LocalDate dob = null;

                String[] parts = tupla[6].toString().split("-");

                if(parts.length > 0){
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    dob = LocalDate.of(year, month, day);
                }

                responseList.add(
                        DriverModel.builder()
                                .driverId(tupla[0] != null ? Integer.valueOf(tupla[0].toString()) : null)
                                .driverRef(tupla[1] != null ? tupla[1].toString() : null)
                                .number(tupla[2] != null ? Integer.valueOf(tupla[2].toString()) : null)
                                .code(tupla[3] != null ? tupla[3].toString() : null)
                                .forename(tupla[4] != null ? tupla[4].toString() : null)
                                .surname(tupla[5] != null ? tupla[5].toString() : null)
                                .dob(
                                    dob
                                )
                                .nationality(tupla[7] != null ? tupla[7].toString() : null)
                                .url(tupla[8] != null ? tupla[8].toString() : null)
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

    public Optional<DriverModel> findById(Integer id) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{

            entityManager.getTransaction().begin();

            Query query = entityManager.createNativeQuery(
                    SQLScripts.FIND_DRIVER_BY_ID
            );

            query.setParameter("id", id);

            Object[] singleResult = (Object[]) query.getSingleResult();


            LocalDate dob = null;

            String[] parts = singleResult[6].toString().split("-");

            if(parts.length > 0){
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                dob = LocalDate.of(year, month, day);
            }
            return Optional.ofNullable(DriverModel.builder()
                    .driverId(singleResult[0] != null ? Integer.valueOf(singleResult[0].toString()) : null)
                    .driverRef(singleResult[1] != null ? singleResult[1].toString() : null)
                    .number(singleResult[2] != null ? Integer.valueOf(singleResult[2].toString()) : null)
                    .code(singleResult[3] != null ? singleResult[3].toString() : null)
                    .forename(singleResult[4] != null ? singleResult[4].toString() : null)
                    .surname(singleResult[5] != null ? singleResult[5].toString() : null)
                    .dob(
                        dob
                    )
                    .nationality(singleResult[7] != null ? singleResult[7].toString() : null)
                    .url(singleResult[8] != null ? singleResult[8].toString() : null)
                    .build());

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        } finally {
            entityManager.close();
        }

    }


    public ResultadoPilotoDTO getDriversVictories(Integer idPiloto) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try{

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }


            Query query = entityManager.createNativeQuery(
                    SQLScripts.GET_PILOT_VICTORIES
            );
            query.setParameter("idPiloto", idPiloto);

            List<Object[]> tuplas = query.getResultList();


            ArrayList<ResultadoGeralDTO> resultadosGerais = new ArrayList<>();
            ArrayList<ResultadoPorAnoDTO> resultadosPorAno = new ArrayList<>();
            ArrayList<ResultadoPorCorridaDTO> resultadosPorCorrida = new ArrayList<>();


            ArrayList<String> corridasDosTitulos = new ArrayList<>();
            ArrayList<Integer> anosDosTitulos = new ArrayList<>();

            for (Object[] tupla : tuplas) {
                if(tupla[0] != null && tupla[1] != null){
                    resultadosGerais.add(new ResultadoGeralDTO(
                        (tupla[1] != null ? tupla[1].toString() : null),
                        (tupla[0] != null ? Integer.valueOf(tupla[0].toString()) : null)
                        )
                    );
                    corridasDosTitulos.add((tupla[1] != null ? tupla[1].toString() : null));
                    anosDosTitulos.add((tupla[0] != null ? Integer.valueOf(tupla[0].toString()) : null));
                }
            }

            for (Integer ano: anosDosTitulos) {
                if(ano != null) {
                    ResultadoPorAnoDTO resultadoNoAno = new ResultadoPorAnoDTO();
                    resultadoNoAno.setAno(ano);
                    for(ResultadoGeralDTO r : resultadosGerais){
                        if(Objects.equals(r.getAnoCorrida(), ano)){
                            resultadoNoAno.setTitulos(r.getNomeCorrida());
                        }
                    }
                    resultadosPorAno.add(resultadoNoAno);
                }
            }

            for (String corrida: corridasDosTitulos) {
                if(corrida != null){
                    ResultadoPorCorridaDTO resultadoNaCorrida = new ResultadoPorCorridaDTO();
                    resultadoNaCorrida.setNomeCorrida(corrida);
                    for(ResultadoGeralDTO r : resultadosGerais){
                        if(Objects.equals(r.getNomeCorrida(), corrida)){
                            resultadoNaCorrida.setAnos(r.getAnoCorrida());
                        }
                    }
                    resultadosPorCorrida.add(resultadoNaCorrida);
                }
            }

            return ResultadoPilotoDTO.builder()
                    .geral(resultadosGerais)
                    .porAno(resultadosPorAno)
                    .porCorrida(resultadosPorCorrida)
                    .build();

        } catch (Exception exception) {
            entityManager.getTransaction().rollback();
            throw exception;
        } finally {
            entityManager.close();
        }

    }
}
