package model.entities;

import java.io.Serializable;


public class Chale implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	private String Loc;
	private Integer Capacidade;
	private Double ValorAlta;
	private Double ValorBaixa;

	public Chale () {}

	public Chale(Integer id, String loc, Integer capacidade, Double valorAlta, Double valorBaixa) {
		super();
		Id = id;
		Loc = loc;
		Capacidade = capacidade;
		ValorAlta = valorAlta;
		ValorBaixa = valorBaixa;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getLoc() {
		return Loc;
	}

	public void setLoc(String loc) {
		Loc = loc;
	}

	public Integer getCapacidade() {
		return Capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		Capacidade = capacidade;
	}

	public Double getValorAlta() {
		return ValorAlta;
	}

	public void setValorAlta(Double valorAlta) {
		ValorAlta = valorAlta;
	}

	public Double getValorBaixa() {
		return ValorBaixa;
	}

	public void setValorBaixa(Double valorBaixa) {
		ValorBaixa = valorBaixa;
	}

	@Override
	public String toString() {
		return "Chale [Id=" + Id + ", Loc=" + Loc + ", Capacidade=" + Capacidade + ", ValorAlta=" + ValorAlta
				+ ", ValorBaixa=" + ValorBaixa + "]";
	}

	
	
	
	
	
}