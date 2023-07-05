package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ResultadoPilotoDTO {

    private List<ResultadoGeralDTO> geral;
    private List<ResultadoPorAnoDTO> porAno;
    private List<ResultadoPorCorridaDTO> porCorrida;

}
