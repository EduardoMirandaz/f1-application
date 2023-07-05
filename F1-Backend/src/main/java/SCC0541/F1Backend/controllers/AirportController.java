package SCC0541.F1Backend.controllers;

import SCC0541.F1Backend.dtos.AirportDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.CloseAirportsDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.CloseAirportsResponseDTO;
import SCC0541.F1Backend.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/airports") // localhost:8080/airports
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping
    public List<AirportDTO> select(){
        return airportService.getAllAirports();
    }

    @GetMapping("/relatorios/exibir-aeroportos-proximos")
    public List<CloseAirportsResponseDTO> getCloserAirports(@RequestParam String nomeCidade){
        return airportService.getCloserAirports(nomeCidade);
    }


}
