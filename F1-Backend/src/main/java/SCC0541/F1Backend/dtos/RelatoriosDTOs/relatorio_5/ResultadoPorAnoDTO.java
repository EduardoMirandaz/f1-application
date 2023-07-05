package SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ResultadoPorAnoDTO {

    private Integer ano;

    private List<String> titulos;

    public ResultadoPorAnoDTO() {
        this.titulos = new ArrayList<>();
    }

    public void setTitulos(String titulos) {
        if(titulos == null) return;
        this.titulos.add(titulos);
    }
}
