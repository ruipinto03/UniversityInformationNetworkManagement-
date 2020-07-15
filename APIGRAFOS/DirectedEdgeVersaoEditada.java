package APIGRAFOS;

import algoritmos.DirectedEdge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DirectedEdgeVersaoEditada extends DirectedEdge implements Serializable {

    private double tempo;

    private Boolean sentido;

    public DirectedEdgeVersaoEditada(int v, int w, double weight, double tempo) {
        super(v, w, weight);
        this.tempo = tempo;
        this.sentido = false;
    }

    public Boolean getSentido() {
        return sentido;
    }

    public void setSentido(Boolean sentido) {
        this.sentido = sentido;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public Double minutos(){
        return tempo/60;
    }
    public Double segundos(){
        return tempo%60;
    }

    @Override
    public String toString() {
        BigDecimal bd = new BigDecimal(weight()).setScale(3, RoundingMode.HALF_EVEN);
        BigDecimal bd1 = new BigDecimal(minutos()).setScale(3, RoundingMode.HALF_EVEN);
        return "DirectedEdgeVersaoEditada de " + getV()+ " ate " + getW() +" tem distancia de "+bd +" demora " + bd1+ " minutos ";
    }
}
