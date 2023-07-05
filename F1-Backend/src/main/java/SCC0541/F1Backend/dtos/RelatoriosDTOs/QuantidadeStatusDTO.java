package SCC0541.F1Backend.dtos.RelatoriosDTOs;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/*
ESTE DTO FOI UTILIZADO PARA A RESPOSTA DOS RELATÓRIOS 1 E 6
 */

@Getter
@Setter
@Builder
public class QuantidadeStatusDTO {

    private String status;
    private Integer quantidadeDeResultados;

}
