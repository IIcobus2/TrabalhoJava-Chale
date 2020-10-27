package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;
import db.DbException;
import model.dao.ClienteDao;
import model.entities.Cliente;

public class ClienteDaoJBC implements ClienteDao {

	private Connection conn;

	public ClienteDaoJBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Cliente obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO cliente VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?);");
			st.setInt(1, obj.getId());
			st.setString(2, obj.getName());
			st.setString(3, obj.getRG());
			st.setString(4, obj.getEndereco());
			st.setString(5, obj.getBairro());
			st.setString(6, obj.getCidade());
			st.setString(7, obj.getEstado());
			st.setString(8, obj.getCEP());
			st.setDate(9, new java.sql.Date(obj.getDataNascimento().getTime()));

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
	public void update(Cliente obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE public.cliente " + 
					"SET nomecliente=?, rgcliente=?, enderecocliente=?, bairrocliente=?, cidadecliente=?, estadocliente=?, cepcliente=?, nascimentocliente=? " + 
					"WHERE idcliente = ?;");
			st.setInt(9, obj.getId());
			st.setString(1, obj.getName());
			st.setString(2, obj.getRG());
			st.setString(3, obj.getEndereco());
			st.setString(4, obj.getBairro());
			st.setString(5, obj.getCidade());
			st.setString(6, obj.getEstado());
			st.setString(7, obj.getCEP());
			st.setDate(8, new java.sql.Date(obj.getDataNascimento().getTime()));

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

			st = conn.prepareStatement("DELETE FROM public.cliente WHERE idcliente = ?");
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
	public Cliente findById(Cliente id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT cliente.* " + "FROM cliente " + "ORDER BY IdCliente");
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			while (rs.next()) {

				Cliente cli = instantiateCliente(rs);

				list.add(cli);
			}
			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			ConnectionFactory.closeStatement(st);
			ConnectionFactory.closeResultSet(rs);
		}

	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cli = new Cliente();
		cli.setId(rs.getInt("idcliente"));
		cli.setName(rs.getString("nomecliente"));
		cli.setRG(rs.getString("rgcliente"));
		cli.setEndereco(rs.getString("enderecocliente"));
		cli.setBairro(rs.getString("bairrocliente"));
		cli.setCidade(rs.getString("cidadecliente"));
		cli.setEstado(rs.getString("estadocliente"));
		cli.setCEP(rs.getString("cepcliente"));
		cli.setDataNascimento(new java.util.Date(rs.getTimestamp("nascimentocliente").getTime()));
		return cli;

	}

}
