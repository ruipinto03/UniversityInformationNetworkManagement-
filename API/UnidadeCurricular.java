package API;

import java.io.Serializable;

public class UnidadeCurricular implements Serializable, GravarFicheiro {

    private String nome;
    private Integer ano;
    private Integer semestre;

    public UnidadeCurricular(String nome, Integer ano, Integer semestre) {
        this.nome = nome.toUpperCase();
        this.ano = ano;
        this.semestre = semestre;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String toStringCompleto() {
        return nome + " Ano - " + ano +" - " +  semestre+ "º semestres";
    }

    @Override
    public String gravar() {
        return nome + ";" + ano +";" +  semestre;
    }
}
