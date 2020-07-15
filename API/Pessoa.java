package API;

import java.io.Serializable;

public abstract class Pessoa implements Serializable, GravarFicheiro{
    private Integer id;
    private String nome;
    private Date dataNascimento;
    private String email;

    public Pessoa(Integer id, String nome, Date dataNascimento) {
        this.id = id;
        this.nome = nome.toUpperCase();
        this.dataNascimento = dataNascimento;
        this.email=null;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return id +" - " + nome;
    }

    public String toStringCompleto() {
        return id +" - " + nome +" - " + email  +" - " + dataNascimento;
    }

    public String gravar() {
        return id +";" + nome +";" + email  +";" + dataNascimento;
    }
}
