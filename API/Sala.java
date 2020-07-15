package API;

import APIGRAFOS.Ponto3D;

import java.io.Serializable;

public class Sala implements Serializable, GravarFicheiro {
    private Integer id;
    private Integer locatacao;
    private Integer tomadas;
    private Boolean projector;
    private Integer piso;
    private Ponto3D ponto;

    public Sala(Integer id, Integer locatacao, Boolean projector, Integer piso) {
        this.id = id;
        this.locatacao = locatacao;
        this.projector = projector;
        this.piso = piso;
        this.tomadas=locatacao;
        this.ponto = null;
    }
    public Sala(Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso) {
        this.id = id;
        this.locatacao = locatacao;
        this.projector = projector;
        this.piso = piso;
        this.tomadas=tomadas;
        this.ponto = null;
    }
    public Sala(Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso, Ponto3D ponto) {
        this.id = id;
        this.locatacao = locatacao;
        this.projector = projector;
        this.piso = piso;
        this.tomadas=tomadas;
        this.ponto=ponto;

    }

    public Ponto3D getPonto() {
        return ponto;
    }

    public void setPonto(Ponto3D ponto) {
        this.ponto = ponto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocatacao() {
        return locatacao;
    }

    public void setLocatacao(Integer locatacao) {
        this.locatacao = locatacao;
    }

    public Integer getTomadas() {
        return tomadas;
    }

    public void setTomadas(Integer tomadas) {
        this.tomadas = tomadas;
    }

    public Boolean getProjector() {
        return projector;
    }

    public void setProjector(Boolean projector) {
        this.projector = projector;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public String toStringCompleto() {
        return id +
                " lotacao " + locatacao +
                " numero de tomadas " + tomadas +
                " projector " + projector +
                " piso " + piso;
    }

    @Override
    public String toString() {
        return "Id da sala " + id + " - " + piso + " andar.";
    }
    public String gravar() {
        if (tomadas != locatacao) {
            return  id +";"+ piso + ";"+locatacao+";"+tomadas+";"+projector;
        }else{
            return  id +";"+ piso + ";"+locatacao+";"+projector;
        }
    }
}
