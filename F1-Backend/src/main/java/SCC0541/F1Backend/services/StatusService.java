package SCC0541.F1Backend.services;

import SCC0541.F1Backend.dtos.RelatoriosDTOs.QuantidadeStatusDTO;
import SCC0541.F1Backend.dtos.StatusDTO;
import SCC0541.F1Backend.repositories.StatusRepository;
import SCC0541.F1Backend.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TokenService tokenService;

    @Autowired
    StatusRepository statusRepository;

    public List<StatusDTO> getAllStatus() {
        return statusRepository.getAllStatus().stream()
                .map(c -> objectMapper.convertValue(c, StatusDTO.class))
                .toList();
    }

    public List<QuantidadeStatusDTO> recuperarRelatorioQuantidadeStatus(String token) {

        Claims body = tokenService.recoverBodyFromToken(token);

        Integer originalId = body.get("idOriginal", Integer.class);

        List<String> roles = body.get("roles", List.class);

        if(roles == null) return null;

        return statusRepository.recuperarRelatorioQuantidadeStatus(roles.get(0), originalId);
    }
}