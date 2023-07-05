package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CloseAirportsDTO {

    private String nomeCidade;
    private String codigoIATA;
    private String nomeAeroporto;
    private String cidadeAeroporto;
    private Double distanciaEmKM;
    private String tipoAeroporto;

}
