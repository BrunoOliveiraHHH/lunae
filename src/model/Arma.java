package model;

public class Arma {

	private int ID_Arma;
	private String nome;
	private String tipo;

	public Arma() {

	}

	public int getID_Arma() {
		return ID_Arma;
	}

	public void setID_Arma(int iD_Arma) {
		ID_Arma = iD_Arma;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
