package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import model.Departamento;
import model.Empleado;

public class DepartamentosFunciones {

	private Connection conn = null;
	public DepartamentosFunciones() {
		conn = BD.getConnection();
	}
	public void close() {
		BD.close();
	}
	public String show() {
		String sql = """ 
				SELECT id, nombre, jefe
				FROM departamentos
				""";
		try {
			StringBuffer sb = new StringBuffer();
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				Departamento d = read(rs);
				sb.append(d.toString());
				sb.append("\n");
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
			String idjefe = rs.getString("jefe");
			Empleado jefe =buscarJefe(idjefe);
			return new Departamento(uuid, nombre, jefe);
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean add(Departamento departamento) {
		String sql = """
				INSERT INTO departamento (id, nombre, jefe)
				VALUES (?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, departamento.getId().toString());
			ps.setString(2, departamento.getNombre());
			ps.setString(3, departamento.getJefe().getId().toString());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}
	public Empleado buscarJefe(String readString) {
		
		return null;
	}

}
