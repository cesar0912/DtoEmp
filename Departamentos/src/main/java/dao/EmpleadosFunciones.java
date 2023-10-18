package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Departamento;
import model.Empleado;

public class EmpleadosFunciones {

	private Connection conn = null;
	public EmpleadosFunciones() {
		conn = BD.getConnection();
		crearTabla();
	}
	
	private void crearTabla() {
		String sql = null;
		if (BD.typeDB.equals("sqlite")) {
			sql = """
						CREATE TABLE IF NOT EXISTS empleados (
							id TEXT PRIMARY KEY,
							nombre TEXT,
							salario TEXT,
							nacimiento TEXT,
							departamentoId TEXT,
							FOREIGN KEY (departamentoId) REFERENCES departamentos(id)
						)
					""";
		}
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
		}

	}

	public String show() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Departamento buscarDepartamento(String iddep) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean add(Empleado empleado) {
		String sql = """
				INSERT INTO empleados (id, nombre, salario, nacimiento, departamentoId)
				VALUES (?, ?, ?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println("0");
			ps.setString(1, empleado.getId().toString());
			System.out.println("1");
			ps.setString(2, empleado.getNombre());
			System.out.println("2");
			ps.setString(3, Double.toString(empleado.getSalario()));
			System.out.println("3");
			ps.setString(4, empleado.getNacimiento().toString());
			System.out.println("4");
			ps.setString(5, empleado.getDepartamento().toString());
			System.out.println("5");
			System.out.println(ps.executeUpdate());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;

	}

	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
