package APIGRAFOS;

import algoritmos.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

public class GrafoUniversidadeV2 implements Serializable {

    static String DELIMITER = ";";

    static Integer numeroDePontos = 0;

    RedBlackBST_AED2<Integer, Integer> mapeamentoIndiceGrafo; // serve para mapear os vertices antigos com os novos

    EdgeWeightedDigraphVersaoEditada grafoGeral; // mapa geral

    EdgeWeightedDigraphVersaoEditada subGrafo; // mapa subgrafo

    ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditadaGrafo; // guarda todas as ligacoes do grafo original

    ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditadaSubgrafo; // guarda todas as ligacoes d

    ArrayList<Ponto3D> listaPonto3DGrafo; // guardar todos os pontos

    ArrayList<Ponto3D> listaPonto3DSubGrafo; // guardar todos os pontos

    public GrafoUniversidadeV2() {

        this.mapeamentoIndiceGrafo = new RedBlackBST_AED2<>();

        this.grafoGeral = null;

        this.subGrafo = null;

        this.listaDirectedEdgeVersaoEditadaGrafo = new ArrayList<>();

        this.listaDirectedEdgeVersaoEditadaSubgrafo = new ArrayList<>();

        this.listaPonto3DGrafo = new ArrayList<>();

        this.listaPonto3DSubGrafo = new ArrayList<>();

    }

    public ArrayList<Ponto3D> getListaPonto3DGrafo() {

        return listaPonto3DGrafo;

    }

    public void setListaPonto3DGrafo(ArrayList<Ponto3D> listaPonto3DGrafo) {

        this.listaPonto3DGrafo = listaPonto3DGrafo;

    }

    public ArrayList<Ponto3D> getListaPonto3DSubGrafo() {

        return listaPonto3DSubGrafo;

    }

    public void setListaPonto3DSubGrafo(ArrayList<Ponto3D> listaPonto3DSubGrafo) {

        this.listaPonto3DSubGrafo = listaPonto3DSubGrafo;

    }

    public static String getDELIMITER() {
        return DELIMITER;
    }

    public static void setDELIMITER(String DELIMITER) {
        GrafoUniversidadeV2.DELIMITER = DELIMITER;
    }

    public static Integer getNumeroDePontos() {
        return numeroDePontos;
    }

    public static void setNumeroDePontos(Integer numeroDePontos) {
        GrafoUniversidadeV2.numeroDePontos = numeroDePontos;
    }

    public RedBlackBST_AED2<Integer, Integer> getMapeamentoIndiceGrafo() {
        return mapeamentoIndiceGrafo;
    }

    public void setMapeamentoIndiceGrafo(RedBlackBST_AED2<Integer, Integer> mapeamentoIndiceGrafo) {
        this.mapeamentoIndiceGrafo = mapeamentoIndiceGrafo;
    }

    public EdgeWeightedDigraphVersaoEditada getGrafoGeral() {
        return grafoGeral;
    }

    public void setGrafoGeral(EdgeWeightedDigraphVersaoEditada grafoGeral) {
        this.grafoGeral = grafoGeral;
    }

    public EdgeWeightedDigraphVersaoEditada getSubGrafo() {
        return subGrafo;
    }

    public void setSubGrafo(Integer vertices) {
        this.subGrafo = new EdgeWeightedDigraphVersaoEditada(vertices);
    }

    public ArrayList<DirectedEdgeVersaoEditada> getListaDirectedEdgeVersaoEditadaGrafo() {
        return listaDirectedEdgeVersaoEditadaGrafo;
    }

    public void setListaDirectedEdgeVersaoEditadaGrafo(ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditadaGrafo) {
        this.listaDirectedEdgeVersaoEditadaGrafo = listaDirectedEdgeVersaoEditadaGrafo;
    }

    public ArrayList<DirectedEdgeVersaoEditada> getListaDirectedEdgeVersaoEditadaSubgrafo() {
        return listaDirectedEdgeVersaoEditadaSubgrafo;
    }

    public void setListaDirectedEdgeVersaoEditadaSubgrafo(ArrayList<DirectedEdgeVersaoEditada> listaDirectedEdgeVersaoEditadaSubgrafo) {
        this.listaDirectedEdgeVersaoEditadaSubgrafo = listaDirectedEdgeVersaoEditadaSubgrafo;
    }

    public ArrayList<Ponto3D> getListaPonto3D() {
        return listaPonto3DGrafo;
    }

    public void setListaPonto3D(ArrayList<Ponto3D> listaPonto3D) {
        this.listaPonto3DGrafo = listaPonto3D;
    }

    /**
     * Passando um inteiro verifica na lista qual o ponto referente
     *
     * @param id do vertice
     * @return retorna um ponto com o id respectivo
     */
    public Ponto3D retornaNo(Integer id) {

        for (Ponto3D ponto : listaPonto3DGrafo) {
            if (ponto.getId() == id) {
                return ponto;
            }
        }
        return null;
    }

    public void resetSubARestasVertices() {
        this.listaDirectedEdgeVersaoEditadaSubgrafo = new ArrayList<>();
        this.listaPonto3DSubGrafo = new ArrayList<>();
    }

    /**
     * Esta funcao verifica se um determinado valor antigo de um vertice foi mapeado para outro valor
     *
     * @param valorNovo a pesquisar
     * @return o novo valor do vertice ou então null caso seja null é sinal que o vertice não foi mapeado para um novo valor
     */
    public Integer retornaValorNovoVertice(Integer valorNovo) {

        for (Integer i : this.mapeamentoIndiceGrafo.keys()) {

            if (valorNovo == i) {

                return this.mapeamentoIndiceGrafo.get(i);

            }

        }

        return null;
    }

    public Integer retornaValorNovoVerticeSubGrafo(Integer valorNovo) {

        for (Integer i : this.mapeamentoIndiceGrafo.keys()) {

            if (valorNovo == this.mapeamentoIndiceGrafo.get(i)) {

                return this.mapeamentoIndiceGrafo.get(i);

            }

        }

        return null;
    }

    /**
     * Imprime a lista completa das arestas do grafo
     */
    public void imprimeTodasDirectedEdgeVersaoEditada() {

        for (DirectedEdgeVersaoEditada d : this.listaDirectedEdgeVersaoEditadaGrafo) {

            System.out.println(d);

        }

    }

    /**
     * Imprime todos os vertices registados
     */
    public void imprimeTodosPontos3D() {

        for (Ponto3D p : this.listaPonto3DGrafo) {

            System.out.println(p);

        }

    }

    /**
     * Carregar as arestas do grafo para a lista de arestas
     */
    public void carregarDirectedEdgeVersaoEditada() {

        String arestasPath = "../DataGrafos/edgesgrafo1.txt";

        In in = new In(arestasPath);

        String s = in.readLine();

        while (!in.isEmpty()) { //String id, Integer id, distancia , tempo

            s = in.readLine();

            String[] fields = s.split(DELIMITER);

            int id = Integer.parseInt(fields[0]);

            int id1 = Integer.parseInt(fields[1]);

            Ponto3D um = retornaNo(id);
            System.out.println(um);
            Ponto3D dois = retornaNo(id1);
            System.out.println(dois);

            boolean sentido = Boolean.parseBoolean(fields[2]);


            adicionarArestaUnidirecional(um, dois, sentido);

        }

    }

    /**
     * Carrega a lista de pontos sem ser as salas. Só os pontos estrategicos da universidade
     */
    public void carregarPontos3D() {

        String nosPath = "../DataGrafos/nosgrafo1.txt";

        In in = new In(nosPath);

        String s = in.readLine();

        while (!in.isEmpty()) { //double x, double y, Integer z, String descricao, Boolean indoor

            s = in.readLine();

            String[] fields = s.split(DELIMITER);
            //      System.out.println(fields.length);

            Integer id = Integer.parseInt(fields[0]);

            double x = Double.parseDouble(fields[1]);

            double y = Double.parseDouble(fields[2]);

            Integer z = Integer.parseInt(fields[3]);

            boolean indoor = Boolean.parseBoolean(fields[4]);

            String descricao = fields[5];

            Ponto3D novo = new Ponto3D(x, y, z, descricao, indoor);

            novo.setId(id);

            numeroDePontos++;

            listaPonto3DGrafo.add(novo);

        }

        this.grafoGeral = new EdgeWeightedDigraphVersaoEditada(listaPonto3DGrafo.size());

    }

    /**
     * Recebe dois pontos para criar uma aresta com uma só direcção
     *
     * @param p1      ponto de origem
     * @param p2      ponto de destino
     * @param sentido para a sair da universidade
     */
    public void adicionarArestaUnidirecional(Ponto3D p1, Ponto3D p2, Boolean sentido) {

        double tempo = 1.25;
        System.out.println(p1 + " - >" + p2);
        double distancia = p1.distPontos(p2);

        double tempoDistancia = distancia * tempo;

        DirectedEdgeVersaoEditada nova = new DirectedEdgeVersaoEditada(p1.getId(), p2.getId(), distancia, tempoDistancia);

        DirectedEdgeVersaoEditada nova1 = new DirectedEdgeVersaoEditada(p2.getId(), p1.getId(), distancia, tempoDistancia);

        nova1.setSentido(sentido);

        listaDirectedEdgeVersaoEditadaGrafo.add(nova);

        this.grafoGeral.addEdge(nova);
    }

    /**
     * Recebe dois pontos para criar duas arestas.
     *
     * @param p1       ponto de origem e destino dependendo da aresta a ser criada
     * @param p2       ponto de origem e destino dependendo da aresta a ser criada
     * @param sentido1 cria sentido de saida para os dois sentidos
     * @param sentido2 cria sentido de saida para os dois sentidos
     */
    public void adicionarArestaBidirecional(Ponto3D p1, Ponto3D p2, Boolean sentido1, Boolean sentido2) {

        double tempo = 1.25;

        double distancia = p1.distPontos(p2);

        double tempoDistancia = distancia * tempo;

        DirectedEdgeVersaoEditada nova = new DirectedEdgeVersaoEditada(p1.getId(), p2.getId(), distancia, tempoDistancia);

        DirectedEdgeVersaoEditada nova1 = new DirectedEdgeVersaoEditada(p2.getId(), p1.getId(), distancia, tempoDistancia);

        nova.setSentido(sentido1);

        nova1.setSentido(sentido2);

        listaDirectedEdgeVersaoEditadaGrafo.add(nova);

        listaDirectedEdgeVersaoEditadaGrafo.add(nova1);

        this.grafoGeral.addEdge(nova);

        this.grafoGeral.addEdge(nova1);

    }

    /**
     * Esta funcão retorna todos os pisos existente no grafo
     *
     * @return floors que é um ArrayList de pisos
     */
    public ArrayList<Integer> getDistinctExistingFloors() {

        ArrayList<Integer> floors = new ArrayList<>();

        for (Ponto3D p : this.listaPonto3DGrafo) {

            if (!floors.contains(p.getZ()))

                floors.add(p.getZ());

        }

        return floors;

    }

    /**
     * Esta função retorna todos os arestas de um determinado piso
     *
     * @param floor o piso a retirar os pontos
     * @return retorna um ArrayList de pontos
     */
    public ArrayList<DirectedEdgeVersaoEditada> getEdgeByFloor(int floor) {
        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<>();
        for (DirectedEdgeVersaoEditada p : listaDirectedEdgeVersaoEditadaGrafo) {
            Ponto3D v = retornaNoById(p.getV());
            Ponto3D w = retornaNoById(p.getW());
            if (v != null && w != null) {
                if (v.getZ() == floor && w.getZ() == floor) {
                    aux.add(p);
                }
            }

        }
        return aux;
    }


    /**
     * Esta função retorna todos os Ponto de um determinado piso
     *
     * @param floor o piso a retirar os pontos
     * @return retorna um ArrayList de pontos
     */
    public ArrayList<Ponto3D> getPointsByFloor(int floor) {
        ArrayList<Ponto3D> aux = new ArrayList<>();
        for (Ponto3D p : listaPonto3DGrafo) {
            if (p.getZ() == floor) {
                aux.add(p);
            }
        }
        return aux;
    }

    /**
     * Esta função vai retornar um ArrayList de todos os pontos validos
     *
     * @param ignorar ArrayList que contem os pontos a ignorar
     * @return retorna um ArrayList de vertices validos
     */
    public ArrayList<Ponto3D> retiraPontosDoGrafo(ArrayList<Ponto3D> ignorar) {

        ArrayList<Ponto3D> aux = new ArrayList<>();

        for (Ponto3D t : listaPonto3DGrafo) {

            if (!ignorar.contains(t)) {

                aux.add(t);

            }

        }

        return aux;

    }


    public RedBlackBST_AED2<Integer, Integer> mapeamento_grafoPontosEvitar(ArrayList<Ponto3D> evitar) {
        int count = 0, validacao = 0;
        RedBlackBST_AED2<Integer, Integer> aux = new RedBlackBST_AED2<>();
        for (Ponto3D p : listaPonto3DGrafo) {
            for (Ponto3D p1 : evitar) {
                if (p.getId() == p1.getId()) {
                    validacao = 1;
                }
            }
            if (validacao == 0) {
                aux.put(p.getId(),count );
                count++;
            }
            validacao = 0;
        }
        return aux;
    }

    /**
     * Adicionar um ponto na lista de vertices
     *
     * @param point recebe um ponto novo
     */
    public void adicionarPonto3d(Ponto3D point) {

        if (!this.listaPonto3DGrafo.contains(point)) {

            numeroDePontos++;

            this.listaPonto3DGrafo.add(point);

        }

    }

    /**
     * Remove um ponto do ArrayList de pontos
     *
     * @param point Recebe o ponto a remover
     */
    public void removePoints(Ponto3D point) {

        Iterator<Ponto3D> i = this.listaPonto3DGrafo.iterator();

        while (i.hasNext()) {

            Ponto3D a = (Ponto3D) i.next();

            if (a.compareTo(point) == 0) {

                i.remove();
                Iterator<DirectedEdgeVersaoEditada> x = this.listaDirectedEdgeVersaoEditadaGrafo.iterator();

                while (x.hasNext()) {

                    DirectedEdgeVersaoEditada d = (DirectedEdgeVersaoEditada) x.next();

                    if (d.getV() == a.getId() || d.getW() == a.getId()) {

                        x.remove();

                    }

                }
            }

        }


    }

    /**
     * Cria um ponto para ser adicionado a uma sala por exemplo ou usar para calcular a distancia
     * de um aluno com o ponto mais proximo para o grafo
     *
     * @param x         coordenada em x
     * @param y         coordenada em y
     * @param z         coordenada em z que é o piso
     * @param descricao sobre o ponto
     * @param indoor    se esta dentro ou fora da universidade
     * @return um novo ponto
     */
    public Ponto3D novoPonto(double x, double y, Integer z, String descricao, Boolean indoor) {

        Ponto3D aux = new Ponto3D(x, y, z, numeroDePontos, descricao, indoor);

        numeroDePontos++;

        return aux;

    }

    /**
     * Faz o mapeamente do e um piso
     * A key vai ser o valor antigo de vertice, o value vai receber o valor novo
     *
     * @param piso recebe o piso a ser mapeado
     * @return retorna uma RedBlackBST com os novos valores dos vertices
     */
    public RedBlackBST_AED2<Integer, Integer> mapeamento_grafo(int piso) {
        int count = 0;
        RedBlackBST_AED2<Integer, Integer> aux = new RedBlackBST_AED2<>();
        for (Ponto3D p : listaPonto3DGrafo) {
            if ((p.getZ() == piso)) {
                aux.put(p.getId(), count);
                count++;
            }
        }

        for (int i : aux.keys()) {
            System.out.println(i + " ->" + aux.get(i));
        }
        setMapeamentoIndiceGrafo(aux);
        return aux;
    }

    public RedBlackBST_AED2<Integer, Integer> mapeamento_grafoTotal() {
        int count = 0;
        RedBlackBST_AED2<Integer, Integer> aux = new RedBlackBST_AED2<>();
        for (Ponto3D p : listaPonto3DGrafo) {
            aux.put(count, p.getId());
            count++;
        }
        setMapeamentoIndiceGrafo(aux);
        return aux;
    }

/**
 * Esta função vai ver qual os pontos que estão a ser mapeados e criar um lista nova
 * com as arestas correspondentes ao novo subgrafo
 *
 * @return ArrayList com os novos valores das arestas
 */
/*
    public ArrayList<DirectedEdgeVersaoEditada> mapeamento_arestas(RedBlackBST_AED2<Integer, Integer > mapaVertices) {

        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<DirectedEdgeVersaoEditada>();

        for (Integer i : mapaVertices.keys()) {

            for (DirectedEdgeVersaoEditada d : listaDirectedEdgeVersaoEditadaGrafo) {

                if (i == d.getV() && retornaNo(d.getW()).getZ()==retornaNo(d.getV()).getZ()) {

                    DirectedEdgeVersaoEditada editada = new DirectedEdgeVersaoEditada(mapaVertices.get(i), mapaVertices.get(d.getW()), d.getWeight(), d.getTempo());

                    aux.add(editada);

                }

            }

        }
        for(DirectedEdgeVersaoEditada d : aux ){
            System.out.println(d);
        }

        return aux;

    }
*/

    /**
     * Função cria um arraylist com todas as arestas de emergencia são aquelas que tem o valor a true
     * representa a saida de um determinado piso
     *
     * @param piso a receber as arestas
     * @return ArrayList com todas as Arestas com sentido para a saida
     */
    public ArrayList<DirectedEdgeVersaoEditada> mapeamento_arestasEmergencia(int piso) {

        ArrayList<DirectedEdgeVersaoEditada> aux = new ArrayList<DirectedEdgeVersaoEditada>();

        for (DirectedEdgeVersaoEditada d : this.listaDirectedEdgeVersaoEditadaGrafo) { // vai andar nas arestas

            if (d.getSentido()) { // vai verificar se aresta pertence aquele id

                aux.add(d);

            }

        }

        return aux;

    }

    /**
     * Esta função retorna todos os pontos outdoor representados no grafo
     *
     * @return ArrayList com os pontos outdoor
     */
    public ArrayList<Ponto3D> outdoor() {

        ArrayList<Ponto3D> aux = new ArrayList<>();

        for (Ponto3D x : listaPonto3DGrafo) {

            if (!x.getIndoor()) {

                aux.add(x);

            }

        }

        return aux;

    }

    /**
     * Esta função retorna me o ponto mais proximo que tem ligação ao grafo
     *
     * @param ponto3D ponto de coordenadas que se quer saber onde a local mais proximo
     * @return o ponto mais proximo
     */
    public Ponto3D retornaPontoMaisProximo(Ponto3D ponto3D) {

        Ponto3D referencia = listaPonto3DGrafo.get(0); // primeiro no do arraylist

        double dist = ponto3D.distPontos(referencia);

        for (Ponto3D x : listaPonto3DSubGrafo) {

            if (ponto3D.distPontos(x) < dist) {

                dist = ponto3D.distPontos(x);

                referencia = x;

            }

        }

        return referencia;

    }

    /**
     * Esta função retorna me a distancia entre o ponto mais proximo que tem ligação ao grafo
     *
     * @param ponto3D ponto de coordenadas que se quer saber onde a local mais proximo
     * @return o ponto mais proximo
     */
    public Double retornaPontoMaisProximoDistancia(Ponto3D ponto3D) {

        Ponto3D referencia = listaPonto3DGrafo.get(0); // primeiro no do arraylist

        double dist = ponto3D.distPontos(referencia);

        for (Ponto3D x : listaPonto3DSubGrafo) {

            if (ponto3D.distPontos(x) < dist) {

                dist = ponto3D.distPontos(x);

                referencia = x;

            }

        }
        return ponto3D.distPontos(referencia);

    }

    /**
     * Esta função retorna me a tempo entre o ponto mais proximo que tem ligação ao grafo
     *
     * @param ponto3D ponto de coordenadas que se quer saber onde a local mais proximo
     * @return o ponto mais proximo
     */
    public Double retornaPontoMaisProximoDistanciaTempo(Ponto3D ponto3D) {

        Ponto3D referencia = listaPonto3DGrafo.get(0); // primeiro no do arraylist

        double dist = ponto3D.distPontos(referencia);

        for (Ponto3D x : listaPonto3DSubGrafo) {

            if (ponto3D.distPontos(x) < dist) {

                dist = ponto3D.distPontos(x);

                referencia = x;

            }

        }

        return referencia.distPontos(ponto3D) * 1.25;

    }

    /**
     * Verificar se um grafo é conexo. Um grafo conexo tem um componente fortemente ligado.
     * O que quer dizer que apartir de um ponto consigo chegar a todos os outros
     *
     * @param grafo recebe um grafo para fazer o teste
     */
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
        for (int v = 0; v < grafoTeste.getGraGeral().criarDigrafo().V(); v++) {
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

    /**
     * Verifica o caminho mais proximo até a saida
     *
     * @param ponto recebe um ponto de origem
     */
    public void caminhoEmergencia(Ponto3D ponto) {
        int j = 0;
        Ponto3D referencia = retornaPontoMaisProximo(ponto); // retorna o ponto mais proximo

        ArrayList<Ponto3D> saida = outdoor(); // retorna as saidas

        DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(this.grafoGeral, referencia.getId()); // aplica dijkstraSP

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

    public void copiaGrafoOriginalParaSubGrafo() {
        //System.out.println(grafoGeral.V());
        setSubGrafo(grafoGeral.V());
        this.listaPonto3DSubGrafo = this.listaPonto3DGrafo;
        this.listaDirectedEdgeVersaoEditadaSubgrafo = this.listaDirectedEdgeVersaoEditadaGrafo;

        mapeamento_grafoTotal();
        for (DirectedEdgeVersaoEditada i : this.listaDirectedEdgeVersaoEditadaGrafo)
            subGrafo.addEdge(i);
    }

    public void mudaValorVertice(Integer novoValor, Integer ValorAntigo) {
        for (Ponto3D p : this.listaPonto3DSubGrafo) {
            if (p.getId() == ValorAntigo) {
                p.setId(novoValor);
            }
        }

    }

    public void mudaValorArestasV(Integer novoValor, Integer ValorAntigo) {
        for (DirectedEdgeVersaoEditada p : this.listaDirectedEdgeVersaoEditadaSubgrafo) {
            if (p.getV() == ValorAntigo) {
                p.setV(novoValor);
            }
            if (p.getW() == ValorAntigo) {
                p.setW(novoValor);
            }
        }

    }

    public Ponto3D retornaNoById(Integer id) {
        for (Ponto3D p : this.listaPonto3DGrafo) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Integer retornaNoByIdMapeado(Integer id) {
        if (this.mapeamentoIndiceGrafo.contains(id)) {
            return this.mapeamentoIndiceGrafo.get(id);
        }
        return null;
    }

}
