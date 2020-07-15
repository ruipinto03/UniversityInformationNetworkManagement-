package APIGRAFOS;

import API.Sala;
import algoritmos.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GrafoUniversidade implements Serializable {
    static String DELIMITER = ";";
    static Integer numeroDePontos = 0;
        ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditada;
        ArrayList<Ponto3D> listaPonto3D;
    EdgeWeightedDigraphVersaoEditada graphGeral;
    EdgeWeightedDigraphVersaoEditada subGrafo;
    RedBlackBST<Integer, Integer> mapeamento_grafoGeral;

    public GrafoUniversidade() {
        this.listaDirectedEdgeVersaoEditada = new ArrayList<>();
        this.listaPonto3D = new ArrayList<>();
        this.graphGeral = null;
        this.subGrafo = null;
        this.mapeamento_grafoGeral = new RedBlackBST<>();
    }

    public ArrayList<DirectedEdgeVersaoEditada> getListaDirectedEdgeVersaoEditada() {
        return listaDirectedEdgeVersaoEditada;
    }

    public void setListaDirectedEdgeVersaoEditada(ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditada) {
        this.listaDirectedEdgeVersaoEditada = listaDirectedEdgeVersaoEditada;
    }

    public static String getDELIMITER() {
        return DELIMITER;
    }

    public static Integer getNumeroDePontos() {
        return numeroDePontos;
    }

    public static void setNumeroDePontos(Integer numeroDePontos) {
        GrafoUniversidade.numeroDePontos = numeroDePontos;
    }

    public RedBlackBST<Integer, Integer> getMapeamento_grafoGeral() {
        return mapeamento_grafoGeral;
    }

    public void setMapeamento_grafoGeral(RedBlackBST<Integer, Integer> mapeamento_grafoGeral) {
        this.mapeamento_grafoGeral = mapeamento_grafoGeral;
    }

    public static void setDELIMITER(String DELIMITER) {
        GrafoUniversidade.DELIMITER = DELIMITER;
    }

    public ArrayList<Ponto3D> getListaPonto3D() {
        return listaPonto3D;
    }

    public void setListaPonto3D(ArrayList<Ponto3D> listaPonto3D) {
        this.listaPonto3D = listaPonto3D;
    }

    public EdgeWeightedDigraphVersaoEditada getGraphGeral() {
        return graphGeral;
    }

    public void setGraphGeral(EdgeWeightedDigraphVersaoEditada graphGeral) {
        this.graphGeral = graphGeral;
    }

    public EdgeWeightedDigraphVersaoEditada getSubGrafo() {
        return subGrafo;
    }

    public void setSubGrafo(EdgeWeightedDigraphVersaoEditada subGrafo) {
        this.subGrafo = subGrafo;
    }

    public Ponto3D retornaNo(int id) {
        for (Ponto3D t : listaPonto3D) {
            if (t.getId() == id)
                return t;
        }
        return null;
    }
    public void imprimeTodosPontos3D() {
        for (Ponto3D p : this.listaPonto3D) {
            System.out.println(p);
        }
    }
    public ArrayList<DirectedEdgeVersaoEditada> imprimeTodasDirectedEdgeVersaoEditada() {
        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<>();
        for (DirectedEdgeVersaoEditada d : this.listaDirectedEdgeVersaoEditada) {
            System.out.println(d);
            aux.add(d);
        }
        return aux;
    }
    public void carregarDirectedEdgeVersaoEditada() {
        String arestasPath = "../DataGrafos/edgesgrafo.txt";
        In in = new In(arestasPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //String id, Integer id, distancia , tempo
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            int id = Integer.parseInt(fields[0]);
            int id1 = Integer.parseInt(fields[1]);
            Ponto3D um = retornaNo(id);
            Ponto3D dois = retornaNo(id1);
            boolean sentido = Boolean.parseBoolean(fields[2]);
            adicionarArestaUnidirecional(um, dois, sentido);
        }
    }
    public void carregarPontos3D() {
        String nosPath = "../DataGrafos/nosgrafo.txt";
        In in = new In(nosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //double x, double y, Integer z, String descricao, Boolean indoor
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            //      System.out.println(fields.length);
            Integer id = Integer.parseInt(fields[0]);
            Double x = Double.parseDouble(fields[1]);
            Double y = Double.parseDouble(fields[2]);
            Integer z = Integer.parseInt(fields[3]);
            boolean indoor = Boolean.parseBoolean(fields[4]);
            String descricao = fields[5];
            Ponto3D novo = new Ponto3D(x, y, z, descricao, indoor);
            novo.setId(id);
            numeroDePontos++;
            listaPonto3D.add(novo);
        }
        this.graphGeral = new EdgeWeightedDigraphVersaoEditada(listaPonto3D.size());
    }

    public void adicionarArestaUnidirecional(Ponto3D p1, Ponto3D p2, Boolean sentido) {
        double tempo = 1.25;
        double distancia = p1.distPontos(p2);
        double tempoDistancia = distancia * tempo;
        DirectedEdgeVersaoEditada nova = new DirectedEdgeVersaoEditada(p1.getId(), p2.getId(), distancia, tempoDistancia);
        DirectedEdgeVersaoEditada nova1 = new DirectedEdgeVersaoEditada(p2.getId(), p1.getId(), distancia, tempoDistancia);
        nova1.setSentido(sentido);
        listaDirectedEdgeVersaoEditada.add(nova);
        this.graphGeral.addEdge(nova);
    }

    public void adicionarArestaBidirecional(Ponto3D p1, Ponto3D p2, Boolean sentido1, Boolean sentido2) {
        double tempo = 1.25;
        double distancia = p1.distPontos(p2);
        double tempoDistancia = distancia * tempo;
        DirectedEdgeVersaoEditada nova = new DirectedEdgeVersaoEditada(p1.getId(), p2.getId(), distancia, tempoDistancia);
        DirectedEdgeVersaoEditada nova1 = new DirectedEdgeVersaoEditada(p2.getId(), p1.getId(), distancia, tempoDistancia);
        nova.setSentido(sentido1);
        nova1.setSentido(sentido2);
        listaDirectedEdgeVersaoEditada.add(nova);
        listaDirectedEdgeVersaoEditada.add(nova1);
        this.graphGeral.addEdge(nova);
        this.graphGeral.addEdge(nova1);
    }



    public ArrayList<Integer> getDistinctExistingFloors() {
        ArrayList<Integer> floors = new ArrayList<>();
        for (Ponto3D p : this.listaPonto3D) {
            if (!floors.contains(p.getZ()))
                floors.add(p.getZ());
        }
        return floors;
    }

    public int size() {
        return this.listaPonto3D.size();
    }

    public ArrayList<Ponto3D> getPointsByFloor(int floor) {
        ArrayList<Ponto3D> aux = new ArrayList<>();
        for (Ponto3D p : listaPonto3D) {
            if (p.getZ() == floor) {
                aux.add(p);
            }
        }
        return aux;
    }

    public ArrayList<Ponto3D> getPointsByEvitar(ArrayList<Ponto3D> ignorar) {
        ArrayList<Ponto3D> aux = new ArrayList<>();
        for (Ponto3D t : listaPonto3D) {
            if (!ignorar.contains(t)) {
                aux.add(t);
            }
        }
        return aux;
    }

    public void adicionarPonto3d(Ponto3D point) {
        if (!this.listaPonto3D.contains(point)) {
            numeroDePontos++;
            this.listaPonto3D.add(point);
        }
    }

    public void removePoints(Ponto3D point) {
        Iterator<Ponto3D> i = this.listaPonto3D.iterator();
        while (i.hasNext()) {
            Ponto3D a = (Ponto3D) i.next();
            if (a.compareTo(point) == 0) {
                i.remove();
            }
        }
    }

    public Ponto3D novoPonto(double x, double y, Integer z, String descricao, Boolean indoor) {
        Ponto3D aux = new Ponto3D(x, y, z, numeroDePontos, descricao, indoor);
        numeroDePontos++;
        return aux;
    }

    public Sala novoSala(Integer idSala, Integer locatacao, Integer tomadas, Boolean projector, Integer piso, double x, double y, Integer z, String descricao, Boolean indoor) {
        Ponto3D aux1 = new Ponto3D(x, y, z, numeroDePontos, descricao, indoor);
        numeroDePontos++;
        Sala aux = new Sala(idSala, locatacao, tomadas, projector, z, aux1);
        return aux;
    }

    public RedBlackBST_AED2<Integer, Integer> mapeamento_grafo(int piso) {
        int count = 0;
        RedBlackBST_AED2<Integer, Integer> aux = new RedBlackBST_AED2<>();
        for (Ponto3D p : listaPonto3D) {
            if ((p.getZ() == piso)) {
                aux.put(count, p.getId());
                count++;
            }
        }
        return aux;
    }

    public ArrayList<DirectedEdgeVersaoEditada> mapeamento_arestas(int piso) {
        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<DirectedEdgeVersaoEditada>();
        for (Ponto3D t : listaPonto3D) {
            if ((t.getZ() == piso)) { // vai ver todos vertices de um piso
                for (DirectedEdgeVersaoEditada d : listaDirectedEdgeVersaoEditada) { // vai andar nas arestas
                    if (d.getV() == t.getId() || d.getW() == t.getId()) { // vai verificar se aresta pertence aquele id
                        aux.add(d);
                    }
                }
            }
        }
        return aux;
    }

    public ArrayList<DirectedEdgeVersaoEditada> mapeamento_arestasEmergencia(int piso) {

        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<DirectedEdgeVersaoEditada>();

        for (DirectedEdgeVersaoEditada d : listaDirectedEdgeVersaoEditada) { // vai andar nas arestas

            if (d.getSentido()) { // vai verificar se aresta pertence aquele id

                aux.add(d);

            }

        }

        return aux;

    }

    public ArrayList<Ponto3D> outdoor() {

        ArrayList<Ponto3D> aux = new ArrayList<>();

        for (Ponto3D x : listaPonto3D) {

            if (!x.getIndoor()) {

                aux.add(x);

            }

        }

        return aux;

    }

    public Ponto3D retornaPontoMaisProximo(Ponto3D ponto3D) {
        Ponto3D referencia = listaPonto3D.get(0); // primeiro no do arraylist
        double dist = ponto3D.distPontos(referencia);
        for (Ponto3D x : listaPonto3D) {
            if (ponto3D.distPontos(x) < dist) {
                dist = ponto3D.distPontos(x);
                referencia = x;
            }
        }
        return referencia;
    }

    public void caminhoEmergenciaDistancia(Ponto3D ponto3D) {
        int j = 0;
        Ponto3D referencia = retornaPontoMaisProximo(ponto3D); // retorna o ponto mais proximo

        ArrayList<Ponto3D> saida = outdoor(); // retorna as saidas

        DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(this.graphGeral, referencia.getId()); // aplica dijkstraSP

        Ponto3D imprimir = saida.get(0); // primeiro no do arraylist
        double dist = 100000000.00;

        for (Ponto3D p : saida) {
            if (sp.hasPathTo(p.getId())) {
                if (sp.distTo(p.getId()) < dist) {
                    dist = sp.distTo(p.getId());
                    imprimir = p;
                }
            }
        }

        StdOut.printf("%d to %d (%.2f)  \n", referencia.getId(), imprimir.getId(), sp.distTo(imprimir.getId()));
        for (DirectedEdgeVersaoEditada e : sp.pathTo(imprimir.getId())) {
            j++;
            BigDecimal bd = new BigDecimal(e.weight()).setScale(2, RoundingMode.HALF_EVEN);
            StdOut.print(j + "º ->" + " " + e.getV() + "->" + e.getW()
                    + " com distancia de " + bd + "\n");

        }
        System.out.println("\n" + imprimir.getDescricao());
    }

    public void caminhoEmergenciaTempo(Ponto3D ponto3D) {
        int j = 0;
        Ponto3D referencia = retornaPontoMaisProximo(ponto3D); // retorna o ponto mais proximo

        ArrayList<Ponto3D> saida = outdoor(); // retorna as saidas

        DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(this.graphGeral, referencia.getId()); // aplica dijkstraSP

        Ponto3D imprimir = saida.get(0); // primeiro no do arraylist
        double dist = 100000000.00;

        for (Ponto3D p : saida) {
            if (sp.hasPathTo(p.getId())) {
                if (sp.distTo(p.getId()) < dist) {
                    dist = sp.distTo(p.getId());
                    imprimir = p;
                }
            }
        }

        StdOut.printf("%d to %d (%.2f)  \n", referencia.getId(), imprimir.getId(), sp.distTo(imprimir.getId()));
        for (DirectedEdgeVersaoEditada e : sp.pathTo(imprimir.getId())) {
            j++;
            BigDecimal bd = new BigDecimal(e.weight()).setScale(2, RoundingMode.HALF_EVEN);
            StdOut.print(j + "º ->" + " " + e.getV() + "->" + e.getW()
                    + " com distancia de " + bd + "\n");

        }
        System.out.println("\n" + imprimir.getDescricao());
    }

    public void caminhoEvitandoPontos(Ponto3D ponto3D, ArrayList<Integer> pontos) {
        Ponto3D referencia = retornaPontoMaisProximo(ponto3D);

        EdgeWeightedDigraphVersaoEditada aux = new EdgeWeightedDigraphVersaoEditada(this.graphGeral.V());
        ArrayList<DirectedEdgeVersaoEditada> aux1 = listaDirectedEdgeVersaoEditada;
        for (DirectedEdgeVersaoEditada e : aux1) {
            for (Integer i : pontos) {
                if (e.getV() == i || e.getW() == i) {
                    e.setWeight(Double.POSITIVE_INFINITY);
                }
            }
        }

        DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(aux, referencia.getId());

        for (int t = 0; t < aux.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", referencia.getId(), t, sp.distTo(t));
                for (DirectedEdgeVersaoEditada e : sp.pathTo(t)) {
                    StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + e.weight() + " segundos ");
                }
                StdOut.println();
            }
        }
    }
    public void caminhoEvitandoPontos(EdgeWeightedDigraphVersaoEditada grafo,Integer id, ArrayList<Integer> pontos) {
        ArrayList<DirectedEdgeVersaoEditada> aux1 = listaDirectedEdgeVersaoEditada;
        for (DirectedEdgeVersaoEditada e : aux1) {
            for (Integer i : pontos) {
                if (e.getV() == i || e.getW() == i) {
                    e.setWeight(Double.POSITIVE_INFINITY);
                }
            }
        }

        DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(grafo, id);

        for (int t = 0; t < grafo.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", id, t, sp.distTo(t));
                for (DirectedEdgeVersaoEditada e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", id, t);
            }
        }
    }


    public void caminhoMaisCurtoEntreDoisPontosDistancia(Sala s1, Sala s2, EdgeWeightedDigraphVersaoEditada graphGeral) {

        DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(graphGeral, s1.getPonto().getId());

        if (sp.hasPathTo(s2.getId())) {
            StdOut.printf("%d to %d (%.2f)  ", s1.getPonto().getId(), s2.getPonto().getId(), sp.distTo(s2.getId()));
            for (DirectedEdgeVersaoEditada e : sp.pathTo(s2.getPonto().getId())) {
                StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + e.weight() + " segundos ");
            }
            StdOut.println();
        }
    }

    public void caminhoMaisCurtoEntreDoisPontosTempo(Sala s1, Sala s2, EdgeWeightedDigraphVersaoEditada graphGeral) {
        DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(graphGeral, s1.getPonto().getId());

        if (sp.hasPathTo(s2.getId())) {
            StdOut.printf("%d to %d (%.2f)  ", s1.getPonto().getId(), s2.getPonto().getId(), sp.distTo(s2.getPonto().getId()));
            for (DirectedEdgeVersaoEditada e : sp.pathTo(s2.getPonto().getId())) {
                StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + e.weight() + " segundos ");
            }
            StdOut.println();
        }
    }

    public void caminhosUmParaTodosTempo(Ponto3D p1,EdgeWeightedDigraphVersaoEditada graphGeral){

        DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(graphGeral, p1.getId());


        for (int t = 0; t < graphGeral.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", p1.getId(), t, sp.distTo(t));
                for (DirectedEdgeVersaoEditada e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", p1, t);
            }
        }
    }

    public void caminhosUmParaTodosDistancia(EdgeWeightedDigraphVersaoEditada graphGeral,Integer s1){



        DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(graphGeral, s1);


        // print shortest path
        for (int t = 0; t < graphGeral.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s1, t, sp.distTo(t));
                for (DirectedEdgeVersaoEditada e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s1, t);
            }
        }
    }

    public String grafoConexo(EdgeWeightedDigraphVersaoEditada grafo) {
        KosarajuSharirSCC scc = new KosarajuSharirSCC(grafo.criarDigrafo());
        // number of connected components
        int m = scc.count();
        //StdOut.println(m + " strong components");
        if (m == 1) {
            System.out.println("Grafo é conexo"); // so é conexo quando tem um so elemento fortemente ligado
            return "Grafo é conexo";
        } else {
            System.out.println("Nao é conexo");
            return "Não é conexo";
        }
  /*      // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
        for (int i = 0; i < m; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < grafoTeste.getGraphGeral().criarDigrafo().V(); v++) {
            components[scc.id(v)].enqueue(v);
        }

        // print results
        for (int i = 0; i < m; i++) {
            for (int v : components[i]) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }*/
    }

    public static void main(String[] args) {
        GrafoUniversidade grafoTeste = new GrafoUniversidade();
        grafoTeste.carregarPontos3D();
        grafoTeste.carregarDirectedEdgeVersaoEditada();
        //  grafoTeste.imprimeTodasDirectedEdgeVersaoEditada();
        //  grafoTeste.imprimeTodosPontos3D();
        grafoTeste.grafoConexo(grafoTeste.graphGeral);
        grafoTeste.caminhoEmergenciaDistancia(new Ponto3D(20.0, 15.0, 3, "teste", true));
        grafoTeste.caminhoEmergenciaTempo(new Ponto3D(20.0, 15.0, 3, "teste", true));
        grafoTeste.caminhosUmParaTodosDistancia(grafoTeste.graphGeral,2);
        ArrayList<Integer> teste = new ArrayList<>();
        Random random = new Random();
        for(int i = 0 ; i<5;i++){
            teste.add(random.nextInt(46));
        }

        for(Integer i : teste)
            System.out.println(i);


        grafoTeste.caminhoEvitandoPontos(grafoTeste.graphGeral,26,teste);
    }

}
