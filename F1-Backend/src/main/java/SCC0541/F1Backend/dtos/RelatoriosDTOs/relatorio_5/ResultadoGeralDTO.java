package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultadoGeralDTO {

    private String nomeCorrida;
    private Integer anoCorrida;

    public ResultadoGeralDTO(String nomeCorrida, Integer anoCorrida) {
        this.nomeCorrida = nomeCorrida;
        this.anoCorrida = anoCorrida;
    }
}
