package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResultadoPilotoDTO {

    private ResultadoGeralDTO geral;
    private ResultadoPorAnoDTO porAno;
    private ResultadoPorCorridaDTO porCorrida;

}
