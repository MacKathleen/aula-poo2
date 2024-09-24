package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Usuario;

/**
 *
 * @author s.lucas
 */
public class UsuarioController {

    public boolean autenticar(String email, String senha) {
        String sql = "SELECT * from tbl_usuarios "
                + "WHERE email = ? and senha = ? "
                + "and ativo = true";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            comando = gerenciador.prepararComando(sql);
            comando.setString(1, email);
            comando.setString(2, senha);

            resultado = comando.executeQuery();

            if (resultado.next()) {
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, resultado);

        }
        return false;
    }

    public boolean inserirUsuario(Usuario usu) {
        String sql = "INSERT into tbl_usuarios (nome, email, senha, dataNasc, ativo) VALUES (?,?,?,?,?)";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;

        try {
            comando = gerenciador.prepararComando(sql);
            comando.setString(1, usu.getNome());
            comando.setString(2, usu.getEmail());
            comando.setString(3, usu.getSenha());
            comando.setDate(4, new java.sql.Date(usu.getDataNasc().getTime()));
            comando.setBoolean(5, usu.isAtivo());

            comando.executeUpdate();

            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, null);
        }

        return false;
    }

    public List<Usuario> consultar(int tipoFiltro, String filtro) {
        String sql = "SELECT * FROM tbl_usuarios";

        List<Usuario> userlist = new ArrayList<>();
        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;
        ResultSet resultado = null;

        // BUSCA
        if (!filtro.equals("")) {

            if (tipoFiltro == 0 || tipoFiltro == 1) { // BUSCA PELO NOME
                sql += " WHERE nome LIKE ? ";

            } else { // BUSCA PELO EMAIL
                sql += " WHERE email LIKE ? ";

            }

        }

        try {
            comando = gerenciador.prepararComando(sql); // prepara o comando

            // SETA A STRING CASO O USUÁRIO TENHA INSERIDO ALGO NA BUSCA
            if (!filtro.equals("")) {
                if (!filtro.equals("")) {

                    switch (tipoFiltro) {
                        case 0: // BUSCA PELO PRIMEIRO NOME
                            comando.setString(1, filtro + "%");
                            break;

                        case 1: // BUSCA PELO NOME INTEIRO
                            comando.setString(1, "%" + filtro + "%");
                            break;

                        case 2: // BUSCA PELO INÍCIO DO EMAIL
                            comando.setString(1, filtro + "%");
                            break;

                        case 3: // BUSCA PELO EMAIL INTEIRO
                            comando.setString(1, "%" + filtro + "%");
                            break;
                    }
                }
            }

            // executa a query construída
            resultado = comando.executeQuery();

            while (resultado.next()) {
                Usuario usu = new Usuario();
                usu.setPkUsuario(resultado.getInt("id_usuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setEmail(resultado.getString("email"));
                usu.setSenha(resultado.getString("senha"));
                usu.setDataNasc(resultado.getDate("datanasc"));
                usu.setAtivo(resultado.getBoolean("ativo"));

                userlist.add(usu);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        } finally {
            gerenciador.fecharConexao(comando, resultado);

        }
        return userlist;
    }

    public boolean excluir(int id_usuarios) {

        String sql = "DELETE from tbl_usuarios WHERE id_usuario = ?";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;

        try {
            comando = gerenciador.prepararComando(sql);
            comando.setInt(1, id_usuarios);

            comando.executeUpdate();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir:" + ex);

        } finally {
            gerenciador.fecharConexao(comando);
        }

        return false;
    }

    public Usuario buscarPorPk(int pkUsuario) {
        String sql = "SELECT * FROM tbl_usuarios WHERE PKUSUARIO = ?";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;
        ResultSet resultado = null;

        Usuario usu = new Usuario();

        try {
            comando = gerenciador.prepararComando(sql);

            comando.setInt(1, pkUsuario);

            resultado = comando.executeQuery();

            if (resultado.next()) {
                usu.setPkUsuario(resultado.getInt("pkusuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setEmail(resultado.getString("Email"));
                usu.setSenha(resultado.getString("Senha"));
                usu.setDataNasc(resultado.getDate("datanasc"));
                usu.setAtivo(resultado.getBoolean("ativo"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            gerenciador.fecharConexao(comando, resultado);
        }
        return usu;
    }

    public boolean alterarUsuario(Usuario u) {
        String sql = "UPDATE tbl_usuario SET nome = ?, email = ?, senha = ?, datanasc = ?, ativo = ? WHERE pkusuario = ?";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;

        try {
            comando = gerenciador.prepararComando(sql);

            comando.setString(1, u.getNome());
            comando.setString(2, u.getEmail());
            comando.setString(3, u.getSenha());
            comando.setDate(4, new java.sql.Date(u.getDataNasc().getTime()));
            comando.setBoolean(5, u.isAtivo());
            comando.setInt (6, u.getPkUsuario());
            comando.executeUpdate();

            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar:" + ex);
        } finally {
            gerenciador.fecharConexao(comando);
        }
        return false;
    }

}
