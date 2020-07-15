import API.BaseDados;

import static API.Cliente.*;

public class Main {

    public static void main(String[] args) {
        BaseDados bd = new BaseDados(false);
       // ClienteAlunos(bd);
      //  ClienteProfessores(bd);
        ClientePesquisas(bd);

    }
}
