package SCC0541.F1Backend.dtos.RelatoriosDTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CloseAirportsResponseDTO {

    private String nomeCidade;
    private Double latitude;
    private Double longitude;
    private Integer aeroportosProximosEncontrados;
    private List<CloseAirportsDTO> aeroportosProximos;

    public CloseAirportsResponseDTO(String nomeCidade, Double latitude, Double longitude) {
        this.nomeCidade = nomeCidade;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
