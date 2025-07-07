package com.autobots.automanager.adaptadores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.Perfil;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
    private final Usuario usuario;

    public UserDetailsImpl(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> autoridades = new ArrayList<>();
        Perfil perfil = usuario.getPerfil();

        if (perfil != null) {
            autoridades.add(new SimpleGrantedAuthority(perfil.name()));
        } else {
            // Caso deseje lançar exceção:
            // throw new RuntimeException("Usuário não possui perfil definido.");
        }

        return autoridades;
    }

    @Override
    public String getPassword() {
        for (CredencialUsuarioSenha credencial : filtrarCredenciaisSenha()) {
            return credencial.getSenha();
        }
        return null;
    }

    @Override
    public String getUsername() {
        for (CredencialUsuarioSenha credencial : filtrarCredenciaisSenha()) {
            return credencial.getNomeUsuario();
        }
        return null;
    }

    public Perfil getPerfil() {
        return usuario.getPerfil();
    }

    private List<CredencialUsuarioSenha> filtrarCredenciaisSenha() {
        List<CredencialUsuarioSenha> credenciaisUsuarioSenha = new ArrayList<>();
        for (CredencialUsuarioSenha credencial : usuario.getCredenciais()) {
            if (credencial != null) {
                credenciaisUsuarioSenha.add(credencial);
            }
        }
        return credenciaisUsuarioSenha;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
