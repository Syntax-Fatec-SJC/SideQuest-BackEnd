package com.syntax.sidequest_backend.service.interfaces;

import com.syntax.sidequest_backend.modelo.dto.LoginDTO;
import com.syntax.sidequest_backend.modelo.dto.LoginResponseDTO;
import com.syntax.sidequest_backend.modelo.dto.UsuarioDTO;
import com.syntax.sidequest_backend.modelo.entidade.Usuario;

public interface IUsuarioService extends ServicoBase<Usuario, UsuarioDTO> {
    LoginResponseDTO realizarLogin(LoginDTO loginDTO);
}
