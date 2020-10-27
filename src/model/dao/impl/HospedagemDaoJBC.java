package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.ConnectionFactory;
import db.DbException;
import model.dao.HospedagemDao;
import model.entities.Chale;
import model.entities.Cliente;
import model.entities.Hospedagem;

public class HospedagemDaoJBC implements HospedagemDao {

	private Connection conn;

	public HospedagemDaoJBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Hospedagem obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO public.hospedagem(" + 
					"idhospedagem, idchale, estado, datainicio, datafim, qtdpessoas, desconto, valorfinal, idcliente) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			
			st.setInt(1, obj.getId());
			st.setInt(2, obj.getCha().getId());
			st.setString(3, obj.getEstado());
			st.setDate(4, new java.sql.Date(obj.getDataInicio().getTime()));
			st.setDate(5, new java.sql.Date(obj.getDataFim().getTime()));
			st.setInt(6, obj.getQtdPessoa());
			st.setDouble(7, obj.getDesconto());
			st.setDouble(8, obj.getValorFinal());
			st.setInt(9, obj.getCli().getId());


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
	public void update(Hospedagem obj) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE public.hospedagem " + 
					" SET idchale=?, estado=?, datainicio=?, datafim=?, qtdpessoas=?, desconto=?, valorfinal=?, idcliente=?" + 
					" WHERE idhospedagem = ?");
			
			st.setInt(1, obj.getCha().getId());
			st.setString(2, obj.getEstado());
			st.setDate(3, new java.sql.Date(obj.getDataInicio().getTime()));
			st.setDate(4, new java.sql.Date(obj.getDataFim().getTime()));
			st.setInt(5, obj.getQtdPessoa());
			st.setDouble(6, obj.getDesconto());
			st.setDouble(7, obj.getValorFinal());
			System.out.println(obj.getCli().getId());
			st.setInt(8, obj.getCli().getId());
			st.setInt(9, obj.getId());

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

			st = conn.prepareStatement("DELETE FROM public.hospedagem WHERE idhospedagem = ?");
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
	public List<Hospedagem> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT hospedagem.*, chale.localizacao, chale.capacidade, chale.valoraltatemporada, chale.valorbaixatemporada, " + 
					"cliente.nomecliente, cliente.rgcliente, cliente,enderecocliente, cliente.bairrocliente, " + 
					"cliente.cidadecliente, cliente.cepcliente, cliente.nascimentocliente, cliente.estadocliente  " + 
					"FROM ((hospedagem  " + 
					"INNER JOIN Chale ON  hospedagem .IdChale = Chale.IdChale)" + 
					"INNER JOIN Cliente ON hospedagem .IdChale = Cliente.IdCliente)" + 
					"ORDER BY IdHospedagem;");
			rs = st.executeQuery();

			List<Hospedagem> list = new ArrayList<>();
			Map<Integer, Chale> map = new HashMap<>();
			Map<Integer, Cliente> map2 = new HashMap<>();
			
			while (rs.next()) {
				
				Chale cha = map.get(rs.getInt("idchale"));
				Cliente cli = map2.get(rs.getInt("idcliente"));
				cha = instantiateChale(rs);
				cli = instantiateCliente(rs);
						
				Hospedagem obj = instantiateHospedagem(rs, cli, cha);

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

	private Hospedagem instantiateHospedagem(ResultSet rs, Cliente cli, Chale cha) throws SQLException {
		Hospedagem obj = new Hospedagem();
		obj.setId(rs.getInt("idhospedagem"));
		obj.setEstado(rs.getString("estado"));
		obj.setDataInicio(new java.util.Date(rs.getTimestamp("datainicio").getTime()));
		obj.setDataFim(new java.util.Date(rs.getTimestamp("datafim").getTime()));
		obj.setQtdPessoa(rs.getInt("qtdpessoas"));
		obj.setDesconto(rs.getDouble("desconto"));
		obj.setValorFinal(rs.getDouble("valorfinal"));
		obj.setCli(cli);
		obj.setCha(cha);
		return obj;

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
	public Hospedagem findById(Hospedagem id) {
		// TODO Auto-generated method stub
		return null;
	}


}
