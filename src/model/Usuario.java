/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author s.lucas
 */
public class Usuario {

    private int pkUsuario;
    private String nome;
    private String email;
    private String Senha;
    private Date dataNAsc;
    private boolean ativo;
    private Date dataNasc;
   
    public Usuario(){}

    public Usuario(int pkUsuario, String nome, String email, String Senha, Date dataNAsc, boolean ativo) {

        this.pkUsuario = pkUsuario;
        this.nome = nome;
        this.email = email;
        this.Senha = Senha;
        this.dataNasc = dataNAsc;
        this.ativo = ativo;
    }

    public int getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(int pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public Date getDataNasc() {
        return dataNAsc;
    }

    public void setDataNasc(Date dataNAsc) {
        this.dataNAsc = dataNAsc;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String ativoToString() {
        if (isAtivo()) {
            return "Ativo";
            
        } else {
            return "Inativo";
        }
    }

}
