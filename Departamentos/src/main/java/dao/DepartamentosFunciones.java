package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import io.IO;
import model.Departamento;
import model.Empleado;

public class DepartamentosFunciones {

	private Connection conn = null;
	public DepartamentosFunciones() {
		conn = BD.getConnection();
		createTables();
	}
	public void close() {
		BD.close();
	}
	private void createTables() {
		String sql = null;
			sql = """
						CREATE TABLE IF NOT EXISTS departamentos (
							id TEXT PRIMARY KEY,
							nombre TEXT,
							jefeId TEXT,
							FOREIGN KEY (jefeId) REFERENCES empleados(id)
							ON DELETE CASCADE
						)
					""";
		/*if (BD.typeDB.equals("mariadb")) {
			sql = """
						CREATE TABLE IF NOT EXISTS agenda (
						  uuid CHAR(36) PRIMARY KEY,
						  nombre VARCHAR(255) NOT NULL,
						  telefono VARCHAR(255) DEFAULT NULL,
						  edad INT DEFAULT NULL,
						  PRIMARY KEY (uuid)
						)
					""";
		}*/
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			IO.print(e.getMessage());
		}
	}
	public Empleado buscarJefe(String id) {
		String sql = """
				SELECT id, nombre, salario,nacimiento,departamentoId
				FROM empleados
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return readEmp(rs);
			}
		} catch (SQLException e) {
		}
		return null;
	}
	private Empleado readEmp(ResultSet rs) {
		try {
			String sUuid = rs.getString("id");
			UUID uuid = UUID.fromString(sUuid);
			String nombre = rs.getString("nombre");
			Double salario = rs.getDouble("salario");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate nacimiento =LocalDate.parse(rs.getString("nacimiento"),formatter);
			Departamento dep = null;
			return new Empleado(uuid, nombre, salario,nacimiento,dep);
		} catch (SQLException e) {
		}
		return null;
	}
	public String show() {
		String sql = """ 
				SELECT id, nombre, jefeId
				FROM departamentos
				""";
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Departamento d = read(rs);
				if(d.toString()!=null) {
				sb.append(d.toString());
				sb.append("\n");
				}
			}
			return sb.toString();
		} catch (SQLException e) {
		}
		return "";
	}
	private Departamento read(ResultSet rs) {
		try {
			String sUuid = rs.getString("id");
			UUID uuid = UUID.fromString(sUuid);
			String nombre = rs.getString("nombre");
			String idjefe = rs.getString("jefeId");
			Empleado jefe =buscarJefe(idjefe);
			return new Departamento(uuid, nombre, jefe);
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean add(Departamento departamento) {
		String sql = """
				INSERT INTO departamentos (id, nombre, jefeId)
				VALUES (?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, departamento.getId().toString());
			ps.setString(2, departamento.getNombre());
			if(departamento.getJefe()==null) {
				ps.setString(3, null);
			}else {
				ps.setString(3, departamento.getJefe().getId().toString());
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			IO.print(e.getMessage());
		}
		return false;
	}
	public boolean delete(String id) {
		String sql = """
				DELETE FROM departamentos
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			IO.println(e.getMessage());
		}
		return false;

	}

}
