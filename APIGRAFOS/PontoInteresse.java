package APIGRAFOS;

import java.io.Serializable;

public class PontoInteresse extends Ponto3D implements Serializable {

    private Integer lotacao;

    private Integer ocupacao;

    public PontoInteresse(double x, double y, Integer id, Integer z, String descricao, Boolean indoor, Integer lotacao,Integer ocupacao) {
        super(x, y, id, z, descricao, indoor);
        this.lotacao = lotacao;
        this.ocupacao = ocupacao;
    }

    public PontoInteresse(Integer id, Integer z, String descricao, Boolean indoor, Integer lotacao,Integer ocupacao) {
        super(id, z, descricao, indoor);
        this.lotacao = lotacao;
        this.ocupacao = ocupacao;
    }

    public void setOcupacao(Integer ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Integer getLotacao() {
        return lotacao;
    }

    public void setLotacao(Integer lotacao) {
        this.lotacao = lotacao;
    }

    public Integer getOcupacao() {
        return ocupacao;
    }

    public void setOcupacaoSaida() {
        if (this.ocupacao == 0) {
            return;
        }
        this.ocupacao -= 1;
    }

    public void setOcupacaoEntrada() {
        if (this.ocupacao == this.lotacao) {
            System.out.println("Esta cheio!");
            return;
        }
        this.ocupacao += 1;
    }

    @Override
    public String toString() {
        return "Ponto de interesse - " + this.getDescricao() + " Tem uma lotacao de " + lotacao + " tem uma ocupacao de " + ocupacao + " fica localizada no " +
                this.getZ() + "piso - " + super.toString();

    }
}
