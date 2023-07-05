package SCC0541.F1Backend.dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuantidadeStatusDTO {

    private String status;
    private Integer quantidadeDeResultados;


}
