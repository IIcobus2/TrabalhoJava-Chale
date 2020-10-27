package model.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Cliente implements Serializable {
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final long serialVersionUID = 1L;

	private Integer Id;
	private String Name;
	private String RG;
	private String Endereco;
	private String Bairro;
	private String Cidade;
	private String Estado;
	private String CEP;
	private Date DataNascimento;
	
	public Cliente() {
		
	}
	
	public Cliente(Integer id, String name, String rG, String endereco, String bairro, String cidade, String estado,
			String cEP, Date dataNascimento) {
		Id = id;
		Name = name;
		RG = rG;
		Endereco = endereco;
		Bairro = bairro;
		Cidade = cidade;
		Estado = estado;
		CEP = cEP;
		DataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Cliente [Id=" + Id + ", Name=" + Name + ", RG=" + RG + ", Endereco=" + Endereco + ", Bairro=" + Bairro
				+ ", Cidade=" + Cidade + ", Estado=" + Estado + ", CEP=" + CEP + ", DataNascimento=" + DataNascimento
				+ "]";
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}

	public String getBairro() {
		return Bairro;
	}

	public void setBairro(String bairro) {
		Bairro = bairro;
	}

	public String getCidade() {
		return Cidade;
	}

	public void setCidade(String cidade) {
		Cidade = cidade;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public Date getDataNascimento() {
		return DataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		DataNascimento = dataNascimento;
	}
	
	
	
	
}