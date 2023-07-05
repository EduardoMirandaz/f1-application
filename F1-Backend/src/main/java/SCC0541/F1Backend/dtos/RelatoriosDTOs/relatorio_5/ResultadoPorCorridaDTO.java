package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResultadoPorCorridaDTO {

    private String nomeCorrida;

    private List<Integer> anos;

    public ResultadoPorCorridaDTO() {
        this.anos = new ArrayList<>();
    }

    public void setAnos(Integer ano) {
        if(ano == null) return;
        this.anos.add(ano);
    }
}
