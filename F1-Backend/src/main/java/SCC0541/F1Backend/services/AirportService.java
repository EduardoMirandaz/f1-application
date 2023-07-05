package SCC0541.F1Backend.services;

import SCC0541.F1Backend.dtos.AirportDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_2.CloseAirportsResponseDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_2.FoundCitiesByName;
import SCC0541.F1Backend.repositories.AirportRepository;
import SCC0541.F1Backend.repositories.GeoCitiesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AirportRepository airportRepository;
    @Autowired
    private GeoCitiesRepository geoCitiesRepository;

    public List<AirportDTO> getAllAirports(){
        return airportRepository.getAllAirports().stream()
                .map(a -> objectMapper.convertValue(a, AirportDTO.class))
                .toList();
    }

    public List<CloseAirportsResponseDTO> getCloserAirports(String nomeCidade) {

        List<FoundCitiesByName> geoCitiesModel = geoCitiesRepository
                .getLatitudeLongitudeByName(nomeCidade)
                .stream()
                .map(modelCity -> FoundCitiesByName.builder()
                        .nomeCidade(modelCity.getName())
                        .latitude(modelCity.getLat())
                        .longitude(modelCity.getLon())
                        .build())
                .toList();

        return airportRepository.getCloseAirports(geoCitiesModel);
    }
}
