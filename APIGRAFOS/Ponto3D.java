package APIGRAFOS;

import java.io.Serializable;

public class Ponto3D extends Ponto  implements Serializable {

    private static Integer valor = 0; // increment no do grafo

    private Integer id; // id do vertice

    private Integer z; // vai ser o piso

    private String descricao; // descricao do ponto

    private Boolean indoor; // se é dentro do edificio

    public Ponto3D(double x, double y, Integer z, Integer id, String descricao, Boolean indoor) {
        super(x, y);
        this.id = id;
        this.z = z;
        this.descricao = descricao;
        this.indoor = indoor;
    }
    public Ponto3D(double x, double y, Integer z, String descricao, Boolean indoor) {
        super(x, y);
        this.id = valor++;
        this.z = z;
        this.descricao = descricao;
        this.indoor = indoor;
    }

    public static Integer getValor() {
        return valor;
    }

    public static void setValor(Integer valor) {
        Ponto3D.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getIndoor() {
        return indoor;
    }

    public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }

    public double distZ(Ponto3D P) {
        double dz=this.z-P.getZ();//pego no x do meu ponto e subtraio ao x do outro ponto
        return dz;
    }

    public Ponto3D(Integer id, Integer z, String descricao, Boolean indoor) {
        this.id = id;
        this.z = z;
        this.descricao = descricao;
        this.indoor = indoor;
    }
    /**
     * Distancia entre dois pontos, peso das arestas 3 coordenadas
     * @param p ponto
     * @return distancia
     */
    public Double distPontos(Ponto3D p)
    {
        return Math.sqrt(p.distX(this) * p.distX(this) + p.distY(this) * p.distY(this) + p.distZ(this) * p.distZ(this));
    }

    @Override
    public String toString() {
        if(indoor){
            return /*Ponto com coordenadas */"( " + getX() + "," + getY()+"," +getZ() +" ) com id " + id + " com descricao='" + descricao + " fica dentro do edificio";
        }
        return /*"Ponto com coordenadas */"( " + getX() + "," + getY()+"," +getZ() +" ) com id " + id + " com descricao='" + descricao + " fica fora do edificio";
    }

    public int compareTo(Ponto3D point) {
        if(point.getZ() == this.getZ() && point.getY() == this.getY() && point.getX() == this.getX())
            return 0;
        return 1;
    }
}
