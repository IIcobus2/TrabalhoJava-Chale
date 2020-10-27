package model.services;

import java.util.List;

import model.dao.ChaleDao;
import model.dao.DaoFactory;
import model.entities.Chale;

public class ChaleService {

	private ChaleDao dao = DaoFactory.createChaleDao();

	public List<Chale> findAll() {
		return dao.findAll();
	}

	public void save(Chale obj) {	
		dao.insert(obj);
	}
	public void update(Chale obj) {	
		dao.update(obj);
	}
	public void remove(Chale obj) {	
		dao.deleteById(obj.getId());
	}
}
