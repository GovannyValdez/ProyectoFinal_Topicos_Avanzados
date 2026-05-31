package org.example.conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionBD {

    private static ConexionBD instancia;

    private Connection conexion;

    private ConexionBD() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/AeropuertoDB";

            String usuario = "root";

            String password = "govanny27";

            conexion = DriverManager.getConnection(
                    url,
                    usuario,
                    password
            );

            System.out.println("Conexion establecida con exito");

        } catch (ClassNotFoundException e) {

            System.err.println("Error al cargar el Driver");

            e.printStackTrace();

        } catch (SQLException e) {

            System.err.println("Error al conectar con MySQL");

            e.printStackTrace();

        }

    }

    public static ConexionBD getInstancia() {

        if (instancia == null) {

            instancia = new ConexionBD();

        }

        return instancia;

    }

    public Connection getConnection() {

        return conexion;

    }

    public boolean ejecutarPreparedStatement(
            String sql,
            Object... parametros
    ) {

        try {

            PreparedStatement pstm =
                    conexion.prepareStatement(sql);

            for (int i = 0; i < parametros.length; i++) {

                pstm.setObject(i + 1, parametros[i]);

            }

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {

            System.err.println("Error al ejecutar PreparedStatement");

            e.printStackTrace();

            return false;

        }

    }

    public ResultSet ejecutarQueryPrepared(
            String sql,
            Object... parametros
    ) {

        try {

            PreparedStatement pstm =
                    conexion.prepareStatement(sql);

            for (int i = 0; i < parametros.length; i++) {

                pstm.setObject(i + 1, parametros[i]);

            }

            return pstm.executeQuery();

        } catch (SQLException e) {

            System.err.println("Error al ejecutar Query");

            e.printStackTrace();

            return null;

        }

    }

    public void cerrarConexion() {

        try {

            if (conexion != null) {

                conexion.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}