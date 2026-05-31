package org.example.controlador;

import org.example.conexionBD.ConexionBD;
import org.example.modelo.Empleado;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private ConexionBD conexion;

    public EmpleadoDAO() {

        conexion = ConexionBD.getInstancia();

    }

    public boolean agregarEmpleado(Empleado empleado) {

        String sql =
                "INSERT INTO Empleado VALUES (?, ?, ?, ?, ?, ?)";

        return conexion.ejecutarPreparedStatement(
                sql,
                empleado.getSsn(),
                empleado.getNombre(),
                empleado.getDireccion(),
                empleado.getTelefono(),
                empleado.getSalario(),
                empleado.getNumeroUnion()
        );
    }

    public List<Empleado> obtenerTodos() {

        List<Empleado> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM Empleado";

        ResultSet rs =
                conexion.ejecutarQueryPrepared(sql);

        try {

            while (rs.next()) {

                Empleado empleado = new Empleado();

                empleado.setSsn(
                        rs.getString("ssn"));

                empleado.setNombre(
                        rs.getString("nombre"));

                empleado.setDireccion(
                        rs.getString("direccion"));

                empleado.setTelefono(
                        rs.getString("telefono"));

                empleado.setSalario(
                        rs.getDouble("salario"));

                empleado.setNumeroUnion(
                        rs.getString("numeroUnion"));

                lista.add(empleado);
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;
    }

    public Empleado buscarEmpleado(String ssn) {

        String sql =
                "SELECT * FROM Empleado WHERE ssn = ?";

        ResultSet rs =
                conexion.ejecutarQueryPrepared(
                        sql,
                        ssn
                );

        try {

            if (rs.next()) {

                return new Empleado(
                        rs.getString("ssn"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getDouble("salario"),
                        rs.getString("numeroUnion")
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;
    }

    public boolean eliminarEmpleado(String ssn) {

        String sql =
                "DELETE FROM Empleado WHERE ssn = ?";

        return conexion.ejecutarPreparedStatement(
                sql,
                ssn
        );
    }

    public boolean actualizarEmpleado(Empleado empleado) {

        String sql =
                "UPDATE Empleado " +
                        "SET nombre = ?, direccion = ?, telefono = ?, " +
                        "salario = ?, numeroUnion = ? " +
                        "WHERE ssn = ?";

        return conexion.ejecutarPreparedStatement(
                sql,
                empleado.getNombre(),
                empleado.getDireccion(),
                empleado.getTelefono(),
                empleado.getSalario(),
                empleado.getNumeroUnion(),
                empleado.getSsn()
        );
    }
}