/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseexercises;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author windeveloper
 */
public class Main {

    private static Connection conexio;

    public static void main(String[] args) throws SQLException {
       
    }

    public static void abrirConexion() {
        String url = "jdbc:mysql://localhost:3306/sql_local001";
        String user = "root";
        String pass = "root";

        //cogemos el driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("No se encontró el driver");
        }

        //establecemos la conexión con drivermanager
        try {
            conexio = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión realizada utilizando el drivermanager");
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        }
    }

    public static void cerrarConexion() {
        try {
            conexio.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar" + ex.getMessage());
        }
    }

    public static void cerrarStatement(Statement stm) {
        try {
            if (!stm.isClosed() && stm != null) {
                stm.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar statement");
        }

    }

    public static void cerrarResultSet(ResultSet rs) {
        try {
            if (!rs.isClosed() && rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Fallo al cerrar resultset");
        }
    }

    public static void crearEstructura() {
        taulaZona();
        taulaSector();
        taulaArticle();
        taulaClient();
        taulaComercial();
        taulaSectorArticle();
    }

    public static void taulaZona() {

        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE Zona(\n"
                    + "  id VARCHAR(20) PRIMARY KEY,\n"
                    + "  descripcio VARCHAR(30)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void taulaSector() {
        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE Sector(\n"
                    + "  id VARCHAR(20) PRIMARY KEY,\n"
                    + "  descripcio VARCHAR(30)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void taulaClient() {
        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE Client(\n"
                    + "  id varchar(30) not null,\n"
                    + "  nif varchar(40),\n"
                    + "  sector varchar(30),\n"
                    + "  zona varchar(30),\n"
                    + "  constraint client_pk primary key(id),\n"
                    + "  constraint client_fk_sector foreign key(sector) references Sector(id),\n"
                    + "  constraint client_fk_zona foreign key(zona) references Zona(id)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void taulaComercial() {
        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE Comercial(\n"
                    + "  nif varchar(30) not null,\n"
                    + "  nom varchar(50),\n"
                    + "  zona varchar(30),\n"
                    + "  constraint comercial_pk primary key(nif),\n"
                    + "  constraint comercial_fk_zona foreign key(zona) references Zona(id)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void taulaSectorArticle() {
        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE SectorArticle(\n"
                    + "  sector varchar(30),\n"
                    + "  article varchar(30),\n"
                    + "  constraint sectorarticle_fk_sector foreign key(sector) references Sector(id),\n"
                    + "  constraint sectorarticle_fk_article foreign key(article) references Article(id)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void taulaArticle() {
        Statement stm = null;
        try {
            stm = conexio.createStatement();
            stm.executeUpdate("CREATE TABLE Article(\n"
                    + "  id VARCHAR(20) PRIMARY KEY,\n"
                    + "  descripcio VARCHAR(30)\n"
                    + "  );");
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void eliminarEstructura() {
        Statement stm = null;

        try {
            stm = conexio.createStatement();
            stm.executeUpdate("DROP TABLE IF EXISTS SectorArticle, "
                    + "Comercial, "
                    + "Client, "
                    + "Article, "
                    + "Sector, "
                    + "Zona;");

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarStatement(stm);
        }
    }

    public static void insertarArticulo(Article article) throws SQLException {
        Statement stm = null;

        try {
            conexio.setAutoCommit(false);
            stm = conexio.createStatement();
            stm.executeUpdate("INSERT INTO Article VALUES"
                    + " (" + article.getId() + ",\"" + article.getDescripcio() + "\")");
            conexio.commit();
        } catch (SQLException ex) {
            System.out.println("Fallo al insertar articulo");
            conexio.rollback();
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void modificarArticulo(Article article) throws SQLException {
        Statement stm = null;

        try {
            conexio.setAutoCommit(false);
            stm = conexio.createStatement();
            stm.executeUpdate("UPDATE Article SET descripcio = \"" + article.getDescripcio() + "\" "
                    + "WHERE id =" + article.getId() + ";");
            conexio.commit();
        } catch (SQLException ex) {
            System.out.println("fallo al modificar articulo: " + ex.getMessage());
            conexio.rollback();
        } finally {
            cerrarStatement(stm);
        }

    }

    public static void borrarArticulo(Article article) throws SQLException {
        Statement stm = null;

        try {
            conexio.setAutoCommit(false);
            stm = conexio.createStatement();
            stm.executeUpdate("DELETE FROM Article WHERE id = \"" + article.getId() + "\";");
            conexio.commit();

        } catch (SQLException ex) {
            System.out.println("fallo al borrar articulo: " + ex.getMessage());
            conexio.rollback();
        } finally {
            cerrarStatement(stm);
        }

    }

    public static Article recuperarNombreArticulo(String identificador) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        Article art = null;

        try {
            stm = conexio.createStatement();
            rs = stm.executeQuery("SELECT * FROM Article WHERE id = " + identificador);
            if (rs.next()) {
                String id = rs.getString(1);
                String desc = rs.getString(2);
                art = new Article(id, desc);
            }
        } catch (SQLException ex) {
            System.out.println("fallo al buscar articulo: " + ex.getMessage());
        } finally {
            cerrarStatement(stm);
            cerrarResultSet(rs);
        }

        return art;

    }

    public static ArrayList<Article> listaArticulos() throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        ArrayList<Article> list = new ArrayList<>();

        try {
            stm = conexio.createStatement();
            rs = stm.executeQuery("SELECT * FROM Article");
            while (rs.next()) {
                String id = rs.getString(1);
                String desc = rs.getString(2);
                list.add(new Article(id, desc));
            }
        } catch (SQLException ex) {
            System.out.println("fallo al buscar articulo: " + ex.getMessage());
        } finally {
            cerrarStatement(stm);
            cerrarResultSet(rs);
        }

        return list;

    }

    public static ArrayList<Article> listaArticulosDescripcion(String descripcion) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        ArrayList<Article> list = new ArrayList<>();

        try {
            stm = conexio.createStatement();
            rs = stm.executeQuery("SELECT * FROM Article WHERE descripcio LIKE \"" + descripcion + "%\"");
            while (rs.next()) {
                String id = rs.getString(1);
                String desc = rs.getString(2);
                list.add(new Article(id, desc));
            }
        } catch (SQLException ex) {
            System.out.println("fallo al buscar articulo: " + ex.getMessage());
        } finally {
            cerrarStatement(stm);
            cerrarResultSet(rs);
        }

        return list;

    }

}
