package SCC0541.F1Backend.controllers;

import SCC0541.F1Backend.dtos.AirportDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_2.CloseAirportsResponseDTO;
import SCC0541.F1Backend.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@CrossOrigin(origins = "http://localhost:3000")
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
