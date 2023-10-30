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
							salario REAL,
							nacimiento TEXT,
							departamentoId TEXT,
							FOREIGN KEY (departamentoId) REFERENCES departamentos(id)
							ON DELETE CASCADE
						)
					""";
		}
		if (BD.typeDB.equals("mariadb")) {
			sql = """
						CREATE TABLE IF NOT EXISTS empleados (
						  id UUID NOT NULL,
						  nombre VARCHAR(255),
						  salario DOUBLE,
						  nacimiento DATE,
						  departamentoId UUID,
						  PRIMARY KEY (id),
						  foreign key (departamentoId) references departamentos(id)
						)
					""";
		}
		try {
			conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			IO.println(e.getMessage());
		}

	}

	public String show() {
		String sql = """
				SELECT *
				FROM empleados
				""";
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Empleado emp = read(rs);
				sb.append(emp.toString());
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
		}
		return "";

	}
	private Empleado read(ResultSet rs) {
		try {
			String id = rs.getString("id");
			UUID uuid = UUID.fromString(id);
			String nombre = rs.getString("nombre");
			Double salario = Double.parseDouble(rs.getString("salario"));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate nacimiento = LocalDate.parse(rs.getString("nacimiento"),formatter);
			Departamento departamento = buscarDepartamento(rs.getString("departamentoId"));
			return new Empleado(uuid, nombre, salario, nacimiento, departamento);
		} catch (SQLException e) {
		}
		return null;
	}

	
	public Departamento buscarDepartamento(String id) {
		String sql = """
				SELECT id, nombre, jefeId
				FROM departamentos
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return readDep(rs);
			}
		} catch (SQLException e) {
		}
		return null;
		
	}
	
	
	private Departamento readDep(ResultSet rs) {
		try {
			String sUuid = rs.getString("id");
			UUID uuid = UUID.fromString(sUuid);
			String nombre = rs.getString("nombre");
			Empleado jefe =null;
			return new Departamento(uuid, nombre, jefe);
		} catch (SQLException e) {
		}
		return null;
	}
	public boolean add(Empleado empleado) {
		String sql = """
				INSERT INTO empleados (id, nombre, salario, nacimiento, departamentoId)
				VALUES (?, ?, ?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, empleado.getId().toString());
			ps.setString(2, empleado.getNombre());
			ps.setDouble(3, empleado.getSalario());
			ps.setString(4, empleado.getNacimiento().toString());
			if(empleado.getDepartamento()==null) {
				ps.setString(5, null);
			}else {
				ps.setString(5, empleado.getDepartamento().getId().toString());
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;

	}

	public boolean delete(String id) {
		String sql = """
				DELETE FROM empleados
				WHERE id = ?
				""";
		String sqlupdate = """
				UPDATE departamentos
				SET jefeId=null
				WHERE jefeId = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
			PreparedStatement ps2 = conn.prepareStatement(sqlupdate);
			ps2.setString(1, id);
			return  ps2.executeUpdate()> 0;
		} catch (SQLException e) {
			IO.println(e.getMessage());
		}
		return false;

	}

	public void close() {
		BD.close();
		
	}

	public boolean update(Empleado empleado) {
		String sql = """
				UPDATE empleados
				SET nombre = ?, salario = ?, nacimiento = ?, departamentoId = ?
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(5, empleado.getId().toString());
			ps.setString(1, empleado.getNombre());
			ps.setString(2, Double.toString(empleado.getSalario()));
			ps.setString(3, empleado.getNacimiento().toString());
			if(empleado.getDepartamento()==null) {
				ps.setString(4, null);
			}else {
				ps.setString(4, empleado.getDepartamento().getId().toString());
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			IO.print(e.getMessage());
		}
		return false;

	}

}

