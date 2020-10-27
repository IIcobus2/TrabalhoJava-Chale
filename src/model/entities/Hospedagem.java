package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Hospedagem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer Id;
	private String Estado;
	private Date DataInicio;
	private Date DataFim;
	private Integer QtdPessoa;
	private Double Desconto;
	private Double ValorFinal;
	
	private Cliente cli;
	private Chale cha;
	
	
	
	
	public Hospedagem(){}

	public Hospedagem(Integer id, String estado, Date dataInicio, Date dataFim, Integer qtdPessoa, Double desconto,
			Double valorFinal, Cliente cli, Chale cha) {
		Id = id;
		Estado = estado;
		DataInicio = dataInicio;
		DataFim = dataFim;
		QtdPessoa = qtdPessoa;
		Desconto = desconto;
		ValorFinal = valorFinal;
		this.cli = cli;
		this.cha = cha;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public Date getDataInicio() {
		return DataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		DataInicio = dataInicio;
	}

	public Date getDataFim() {
		return DataFim;
	}

	public void setDataFim(Date dataFim) {
		DataFim = dataFim;
	}

	public Integer getQtdPessoa() {
		return QtdPessoa;
	}

	public void setQtdPessoa(Integer qtdPessoa) {
		QtdPessoa = qtdPessoa;
	}

	public Double getDesconto() {
		return Desconto;
	}

	public void setDesconto(Double desconto) {
		Desconto = desconto;
	}

	public Double getValorFinal() {
		return ValorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		ValorFinal = valorFinal;
	}

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	public Chale getCha() {
		return cha;
	}

	public void setCha(Chale cha) {
		this.cha = cha;
	}

	@Override
	public String toString() {
		return "Hospedagem [Id=" + Id + ", Estado=" + Estado + ", DataInicio=" + DataInicio + ", DataFim=" + DataFim
				+ ", QtdPessoa=" + QtdPessoa + ", Desconto=" + Desconto + ", ValorFinal=" + ValorFinal + ", cli=" + cli
				+ ", cha=" + cha + "]";
	}
	
	


}
