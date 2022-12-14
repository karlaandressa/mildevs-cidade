package br.com.mesttra.cidade.pojo;

/* Represente a tabela cidade(ddd*, nome, nro_habitantes, renda_per_capita, capital**, estado, nome_prefeito) com um POJO. 
	*PK 
	**capital é um booleano.
  create table cidades.cidade{
    ddd integer not null,
    nome character varying not null,
    num_habitantes bigint not null,
    rendaPerCapita double precision not null,
    capital boolean not null,
    estado character varying not null,
    nome_prefeito character varying not null,
    primary key (ddd)
  }; 
  }
  */
public class CidadePOJO{

  private int ddd;
  private String nome;
  private int numHabitantes;
  private double rendaPerCapita;
  private boolean capital;
  private String estado;
  private String nomePrefeito;

  public CidadePOJO() { //ESSE VAZIO ELE CRIA PRA FAZER A PARADINHA DO SET MAIS SIMPLES DO QUE PASSAR MATRICULA E TUDO....
  }

  public CidadePOJO(int ddd, String nome, int numHabitantes, double rendaPerCapita, boolean capital, String estado,
      String nomePrefeito) {
    super();
    this.ddd = ddd;
    this.nome = nome;
    this.numHabitantes = numHabitantes;
    this.rendaPerCapita = rendaPerCapita;
    this.capital = capital;
    this.estado = estado;
    this.nomePrefeito = nomePrefeito;
  }

  public int getDdd() {
    return ddd;
  }

  public void setDdd(int ddd) {
    this.ddd = ddd;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public int getNumHabitantes() {
    return numHabitantes;
  }

  public void setNumHabitantes(int numHabitantes) {
    this.numHabitantes = numHabitantes;
  }

  public double getRendaPerCapita() {
    return rendaPerCapita;
  }

  public void setRendaPerCapita(double rendaPerCapita) {
    this.rendaPerCapita = rendaPerCapita;
  }

  public boolean isCapital() {
    return capital;
  }

  public void setCapital(boolean capital) {
    this.capital = capital;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNomePrefeito() {
    return nomePrefeito;
  }

  public void setNomePrefeito(String nomePrefeito) {
    this.nomePrefeito = nomePrefeito;
  }

  @Override
  public String toString() {
    return "CidadePOJO [ddd=" + ddd + ", nome=" + nome + ", numHabitantes=" + numHabitantes + ", rendaPerCapita="
        + rendaPerCapita + ", capital=" + capital + ", estado=" + estado + ", nomePrefeito=" + nomePrefeito + "]";
  }

    
}