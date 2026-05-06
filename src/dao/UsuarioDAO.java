package dao;

import conexion.Conexion;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para la tabla USUARIO
 * Contiene las operaciones CRUD: Crear, Leer, Actualizar, Eliminar
 */
public class UsuarioDAO {

    // ══════════════════════════════════════════════════════════════
    //  CREATE - Insertar nuevo usuario
    // ══════════════════════════════════════════════════════════════
    public boolean insertar(Usuario u) {
        String sql = "INSERT INTO USUARIO VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?)";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrarConexion(con);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  READ - Listar todos los usuarios
    // ══════════════════════════════════════════════════════════════
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO ORDER BY ID";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion(con);
        }
        return lista;
    }

    // ══════════════════════════════════════════════════════════════
    //  READ - Buscar usuario por ID
    // ══════════════════════════════════════════════════════════════
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM USUARIO WHERE ID = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("ID"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("EMAIL"),
                    rs.getString("TELEFONO")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        } finally {
            Conexion.cerrarConexion(con);
        }
        return null;
    }

    // ══════════════════════════════════════════════════════════════
    //  UPDATE - Actualizar usuario
    // ══════════════════════════════════════════════════════════════
    public boolean actualizar(Usuario u) {
        String sql = "UPDATE USUARIO SET NOMBRE=?, APELLIDO=?, EMAIL=?, TELEFONO=? WHERE ID=?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setInt(5, u.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrarConexion(con);
        }
    }

    // ══════════════════════════════════════════════════════════════
    //  DELETE - Eliminar usuario por ID
    // ══════════════════════════════════════════════════════════════
    public boolean eliminar(int id) {
        String sql = "DELETE FROM USUARIO WHERE ID = ?";
        Connection con = null;
        try {
            con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        } finally {
            Conexion.cerrarConexion(con);
        }
    }
}
