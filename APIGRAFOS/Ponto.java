package APIGRAFOS;

import java.io.Serializable;

public class Ponto  implements Serializable {
    private double x;

    private double y;

    public Ponto(double x,double y ){ //a e b
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Ponto{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Ponto (){}

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    //Um ponto chama esta funçao com outro ponto
    public double distX(Ponto P)
    {
        double dx=this.x-P.getX();//pego no x do meu ponto e subtraio ao x do outro ponto
        return dx;
    }

    public double distY(Ponto P)
    {
        double dy=this.y-P.getY();//pego no x do meu ponto e subtraio ao x do outro ponto
        return dy;
    }

    public double dist (Ponto p)
    {
        double dx=this.distX(p);
        double dy=p.distY(this);
//        return  Math.sqrt(dx*dx+(dy*dy)) ;
        return 0;
    }

    public void move(double dx,double dy)
    {
        this.x+=dx;
        this.y+=dy;
    }

    public boolean xBetweenPontos(Ponto up, Ponto down)
    {
        if(this.getX()>=up.getX() && this.getX()<=down.getX())
        {
            return true;
        } else {
            return false;
        }
    }

    public boolean yBetweenPontos(Ponto up, Ponto down)
    {
        if(this.getY()>=down.getX() && this.getX()<=up.getX()){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Distancia entre dois pontos, peso das arestas
     * @param p ponto
     * @return distancia
     */
    public Double distPontos(Ponto p)
    {
        return Math.sqrt(p.distX(this) * p.distX(this) + p.distY(this) * p.distY(this));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
