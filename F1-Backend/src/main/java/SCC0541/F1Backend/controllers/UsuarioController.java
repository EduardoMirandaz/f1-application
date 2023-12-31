package SCC0541.F1Backend.controllers;

import SCC0541.F1Backend.dtos.TokenDTO;
import SCC0541.F1Backend.dtos.UsuarioLoginDTO;
import SCC0541.F1Backend.dtos.UsuarioRespostaDTO;
import SCC0541.F1Backend.models.UsuarioModel;
import SCC0541.F1Backend.security.TokenService;
import SCC0541.F1Backend.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Login inválido."),
                    @ApiResponse(responseCode = "500", description = "Erro ao autenticar.")
            }
    )
    @Operation(summary = "Autenticar usuário com login e senha.", description = "Caso o usuário exista na base, " +
            "retorna um token que poderá ser utilizado para acessar recursos da API.")
    public TokenDTO login(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO) {

        log.info(usuarioLoginDTO.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        usuarioLoginDTO.getUsername(),
                        usuarioLoginDTO.getPassword()
                );

        Authentication authentication = authenticationManager.
                authenticate(usernamePasswordAuthenticationToken);

        return tokenService.getToken((UsuarioModel) authentication.getPrincipal());
    }

    @GetMapping("/listar-usuarios")
    public List<UsuarioRespostaDTO> listarUsuariosDoSistema(@RequestParam(required = false)  String tipoPessoa){
        if(tipoPessoa != null){
            return usuarioService.listarUsuariosDoSistemaPorTipo(tipoPessoa);
        }
        return usuarioService.listarUsuariosDoSistema();

    }


}
