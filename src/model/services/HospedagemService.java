package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.HospedagemDao;
import model.entities.Hospedagem;

public class HospedagemService {

	private HospedagemDao dao = DaoFactory.createHospedagemDao();

	public List<Hospedagem> findAll() {
		return dao.findAll();
	}

	public void save(Hospedagem obj) {	
		dao.insert(obj);
	}
	public void update(Hospedagem obj) {	
		dao.update(obj);
	}
	public void remove(Hospedagem obj) {	
		dao.deleteById(obj.getId());
	}
}
