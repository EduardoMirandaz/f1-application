package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResultadoPorAnoDTO {

    private Integer ano;

    private List<String> titulos;

}
