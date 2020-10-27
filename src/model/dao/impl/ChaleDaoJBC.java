package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;
import db.DbException;
import model.dao.ChaleDao;
import model.entities.Chale;

public class ChaleDaoJBC implements ChaleDao {

	private Connection conn;

	public ChaleDaoJBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Chale obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO chale  VALUES " + "(?, ?, ?, ?, ?);");
			st.setInt(1, obj.getId());
			st.setString(2, obj.getLoc());
			st.setInt(3, obj.getCapacidade());
			st.setDouble(4, obj.getValorAlta());
			st.setDouble(5, obj.getValorBaixa());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Erro db");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(st);
		}

	}

	@Override
	public void update(Chale obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE public.chale"
					+ " SET localizacao=?, capacidade=?, valoraltatemporada=?, valorbaixatemporada=? "
					+ "	WHERE idchale = ?");
			st.setInt(5, obj.getId());
			st.setString(1, obj.getLoc());
			st.setInt(2, obj.getCapacidade());
			st.setDouble(3, obj.getValorAlta());
			st.setDouble(4, obj.getValorBaixa());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Erro db");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(st);
		}

	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM public.chale WHERE idchale = ?");
			st.setInt(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected == 0) {
				throw new DbException("Erro remover");
			}

			if (rowsAffected == 0) {
				throw new DbException("Erro db");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(st);
		}

	}

	@Override
	public List<Chale> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT chale.* FROM chale ORDER BY IdChale");
			rs = st.executeQuery();

			List<Chale> list = new ArrayList<>();

			while (rs.next()) {

				Chale obj = instantiateChale(rs);

				list.add(obj);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(st);
			ConnectionFactory.closeResultSet(rs);
		}

	}

	private Chale instantiateChale(ResultSet rs) throws SQLException {
		Chale obj = new Chale();
		obj.setId(rs.getInt("idchale"));
		obj.setCapacidade(rs.getInt("capacidade"));
		obj.setLoc(rs.getString("localizacao"));
		obj.setValorAlta(rs.getDouble("valoraltatemporada"));
		obj.setValorBaixa(rs.getDouble("valorbaixatemporada"));

		return obj;

	}

	@Override
	public Chale findById(Chale id) {
		// TODO Auto-generated method stub
		return null;
	}

}
