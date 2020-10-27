package model.dao;

import db.ConnectionFactory;
import model.dao.impl.ChaleDaoJBC;
import model.dao.impl.ClienteDaoJBC;
import model.dao.impl.HospedagemDaoJBC;

public class DaoFactory {

	
	public static ClienteDao createClienteDao() {
		return new ClienteDaoJBC(ConnectionFactory.getConnection());
	}

	public static ChaleDao createChaleDao() {
		return new ChaleDaoJBC(ConnectionFactory.getConnection());
	}
	
	public static HospedagemDao createHospedagemDao() {
		return new HospedagemDaoJBC(ConnectionFactory.getConnection());
	}
}
