package SCC0541.F1Backend.dtos.RelatoriosDTOs;

import jakarta.persistence.Access;
import lombok.*;


@Getter
@Setter
@Builder
public class FoundCitiesByName {

    private String nomeCidade;
    private Double latitude;
    private Double longitude;

    public FoundCitiesByName(String nomeCidade, Double latitude, Double longitude) {
        this.nomeCidade = nomeCidade;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
