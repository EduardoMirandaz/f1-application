package SCC0541.F1Backend.controllers;

import SCC0541.F1Backend.dtos.RelatoriosDTOs.QuantidadeStatusDTO;
import SCC0541.F1Backend.dtos.StatusDTO;
import SCC0541.F1Backend.services.StatusService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/status") // localhost:8080/status
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping
    public List<StatusDTO> getAllStatus(){
        return statusService.getAllStatus();
    }

    @GetMapping("/relatorios/quantidade-por-resultado")
    public List<QuantidadeStatusDTO> recuperarRelatorioQuantidadeStatus(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String authToken = token.substring(7); // Remover o prefixo "Bearer "
            return statusService.recuperarRelatorioQuantidadeStatus(authToken);
        }
        return null;
    }


}
