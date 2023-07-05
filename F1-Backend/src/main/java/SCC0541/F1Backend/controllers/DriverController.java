package SCC0541.F1Backend.controllers;

import SCC0541.F1Backend.dtos.ConstructorDTO;
import SCC0541.F1Backend.dtos.CreateDriverDTO;
import SCC0541.F1Backend.dtos.DriverDTO;
import SCC0541.F1Backend.dtos.RelatoriosDTOs.relatorio_5.ResultadoPilotoDTO;
import SCC0541.F1Backend.services.DriverService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/drivers") // localhost:8080/drivers
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping
    public List<DriverDTO> findAll(){
        return driverService.list();
    }

    @GetMapping("/{id}")
    public DriverDTO findById(@PathVariable("id") Integer id){
        return driverService.findById(id);
    }

    @PostMapping("/create")
    public void createDriver(@RequestBody @Valid CreateDriverDTO createDriverDTO) {
        driverService.createDriver(createDriverDTO);
    }

    @GetMapping("/relatorios/recuperar-vitorias")
    public ResultadoPilotoDTO getDriversVictories(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String authToken = token.substring(7); // Remover o prefixo "Bearer "
            return driverService.getDriversVictories(authToken);
        }
        return null;
    }


}
