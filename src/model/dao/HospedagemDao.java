package model.dao;

import java.util.List;
import model.entities.Hospedagem;

public interface HospedagemDao {
	void insert (Hospedagem obj);
	void update (Hospedagem obj);
	void deleteById(Integer id);
	Hospedagem findById(Hospedagem id);
	List<Hospedagem> findAll();

}
