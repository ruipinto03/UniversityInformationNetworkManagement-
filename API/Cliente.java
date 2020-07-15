package API;

import java.io.Serializable;

import static API.Pesquisas.*;

public class Cliente implements Serializable {
    public static void ClienteAlunos(BaseDados baseDados){
        // testes sem dados carregados
        /*baseDados.printAlunos();
        Curso novo = new Curso("Inf","Engenharia informatica");
        baseDados.adicionarCursos(novo);
        baseDados.adicionarAlunos("Rui",new Date(7,6,1989),novo);
        baseDados.adicionarAlunos("Fabio",new Date(7,6,1989),novo);
        baseDados.printAlunos();
        baseDados.deleteAlunos(1);
        baseDados.printAlunos();*/

        // carregar dados
      //  baseDados.printAlunos();
        baseDados.carregarProfessores();
        baseDados.carregarCursos();
        baseDados.carregarUc();
        baseDados.carregarTurmas();
        baseDados.carregarAlunos();
        baseDados.carregarSalas();
        baseDados.carregarHorarios();
        baseDados.printTurmasComHorarios();
        baseDados.adicionarHorario(baseDados.getTurmas().get("3A6KMI"),new Horario(baseDados.getSalas().get(301),10,3));
        baseDados.printTurmasComHorarios();
     //   baseDados.apagarHorario(baseDados.getTurmas().get("4D42OP"),1,12);
        baseDados.printTurmasComHorarios();

        // baseDados.printAlunos();
      //  baseDados.printSalas();
       /* baseDados.printCursos();
        baseDados.printProfessores();
        baseDados.printTurmas();*/


    }
    public static void ClienteProfessores(BaseDados baseDados){
        // testes sem dados carregados
        baseDados.printProfessores();
        baseDados.adicionarProfessores("Rui",new Date(12,12,1940));
        baseDados.adicionarProfessores("Tiago",new Date(12,12,1940));
        baseDados.printProfessores();
        baseDados.deleteProfessores(3);
      //  baseDados.printProfessores();



    }
    public static void ClienteCurso(BaseDados baseDados)
    {

    }
    public static void ClientePesquisas(BaseDados baseDados) {
        baseDados.carregarProfessores();
        baseDados.carregarCursos();
        baseDados.carregarUc();
        baseDados.carregarTurmas();
        baseDados.carregarAlunos();
        baseDados.carregarSalas();
        baseDados.carregarHorarios();
        baseDados.carregarAtendimentos();
        //salasLivresHorario(1,12,baseDados); // retira 11 salas de 12 horarios encontrados devido a sala 214 ser repetida
        //professoresTurmaSub("MAT",baseDados); // procura substring 9 resultados
       // professoresTurma("MATEMATICA I",baseDados); // resultado duas turmas
       // todasTurmasProfessor(60,baseDados); // resultado duas turmas
        baseDados.printAtendimentos();
    }
    public static void ClienteTurma(BaseDados baseDados){
        baseDados.carregarProfessores();
        baseDados.carregarCursos();
        baseDados.carregarUc();
        baseDados.carregarTurmas();
        baseDados.carregarAlunos();
        baseDados.carregarSalas();
        baseDados.carregarHorarios();
        baseDados.printTurmasComHorarios();
        baseDados.adicionarHorario(baseDados.getTurmas().get("3A6KMI"),new Horario(baseDados.getSalas().get(301),10,3));
        baseDados.printTurmasComHorarios();
        baseDados.apagarHorario(baseDados.getTurmas().get("4D42OP"),1,12);
        baseDados.printTurmasComHorarios();
    }


}
