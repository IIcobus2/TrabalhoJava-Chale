package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

	private ClienteDao dao = DaoFactory.createClienteDao();

	public List<Cliente> findAll() {
		return dao.findAll();
	}

	public void save(Cliente obj) {	
		dao.insert(obj);
	}
	public void update(Cliente obj) {	
		dao.update(obj);
	}
	public void remove(Cliente obj) {	
		dao.deleteById(obj.getId());
	}
}
