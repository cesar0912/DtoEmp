package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import model.Departamento;
import model.Empleado;

public class EmpleadosFunciones {

	private Connection conn = null;
	public EmpleadosFunciones() {
		conn = BD.getConnection();
	}
	public void close() {
		BD.close();
	}
	public String show() {
		String sql = """ 
				SELECT id, nombre, salario,nacimiento,iddepartamento
				FROM empleados
				""";
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Empleado d = read(rs);
				sb.append(d.toString());
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
		}
		return "";
	}
	private Empleado read(ResultSet rs) {
		try {
			String sUuid = rs.getString("id");
			UUID uuid = UUID.fromString(sUuid);
			String nombre = rs.getString("nombre");
			Double salario = rs.getDouble("salario");
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
			LocalDate nacimiento =LocalDate.parse(rs.getString("nacimiento"),formatter);
			String iddep = rs.getString("iddepartamento");
			Departamento dep = buscarDep(iddep);
			return new Empleado(uuid, nombre, salario,nacimiento,dep);
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean add(Empleado emp) {
		String sql = """
				INSERT INTO empleados (id, nombre, salario,nacimiento,iddepartamento)
				VALUES (?, ?, ?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getId().toString());
			ps.setString(2, emp.getNombre());
			ps.setString(3, String.valueOf(emp.getSalario()));
			ps.setString(4, emp.getNacimiento().toString());
			ps.setString(5, emp.getDepartamento().getId().toString());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}
	public Departamento buscarDep(String iddep) {
		// TODO Auto-generated method stub
		return null;
	}
}
