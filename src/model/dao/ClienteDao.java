package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {
	
	void insert (Cliente obj);
	void update (Cliente obj);
	void deleteById(Integer id);
	Cliente findById(Cliente id);
	List<Cliente> findAll();
}
