package API;

import APIGRAFOS.Ponto3D;
import algoritmos.In;
import algoritmos.Out;
import algoritmos.RedBlackBST_AED2;
import algoritmos.SeparateChainingHashST;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Random;

import static API.Pesquisas.pesquisaCadeiraCurso;

public class BaseDados implements Serializable {
    static String DELIMITER = ";";
    static String DELIMITERDATA = "/";
    static Integer numeroUsers = 0;
    private RedBlackBST_AED2<Integer, Aluno> alunos;
    private RedBlackBST_AED2<Integer, Professor> professores;
    private RedBlackBST_AED2<Integer, Sala> salas;
    private SeparateChainingHashST<String, Curso> cursos;
    private SeparateChainingHashST<String, UnidadeCurricular> unidadesCurriculares;
    private SeparateChainingHashST<String, Turma> turmas;

    private RedBlackBST_AED2<Integer, Aluno> historicoAlunos;
    private RedBlackBST_AED2<Integer, Professor> historicoProfessores;
    private RedBlackBST_AED2<Integer, Sala> historicoSalas;
    private SeparateChainingHashST<String, UnidadeCurricular> historicoUnidadesCurriculares;
    private SeparateChainingHashST<String, Turma> historicoTurmas;
    private SeparateChainingHashST<String, Curso> historicoCursos;


    public BaseDados(Boolean carregar) {
        if (!carregar) {
            this.alunos = new RedBlackBST_AED2<>();
            this.professores = new RedBlackBST_AED2<>();
            this.salas = new RedBlackBST_AED2<>();
            this.cursos = new SeparateChainingHashST<>();
            this.unidadesCurriculares = new SeparateChainingHashST<>();
            this.turmas = new SeparateChainingHashST<>();
            this.historicoAlunos = new RedBlackBST_AED2<>();
            this.historicoProfessores = new RedBlackBST_AED2<>();
            this.historicoSalas = new RedBlackBST_AED2<>();
            this.historicoUnidadesCurriculares = new SeparateChainingHashST<>();
            this.historicoTurmas = new SeparateChainingHashST<>();
            this.historicoCursos = new SeparateChainingHashST<>();
            return;
        }
        this.alunos = new RedBlackBST_AED2<>();
        this.professores = new RedBlackBST_AED2<>();
        this.salas = new RedBlackBST_AED2<>();
        this.cursos = new SeparateChainingHashST<>();
        this.unidadesCurriculares = new SeparateChainingHashST<>();
        this.turmas = new SeparateChainingHashST<>();
        this.historicoAlunos = new RedBlackBST_AED2<>();
        this.historicoProfessores = new RedBlackBST_AED2<>();
        this.historicoSalas = new RedBlackBST_AED2<>();
        this.historicoUnidadesCurriculares = new SeparateChainingHashST<>();
        this.historicoTurmas = new SeparateChainingHashST<>();
        this.historicoCursos = new SeparateChainingHashST<>();


        carregarUc();
        carregarCursos();
        carregarSalas();
        carregarProfessores();
        carregarTurmas();
        carregarAlunos();
        carregarAtendimentos();
        carregarAlunosTurmas();
        carregarHorarios();
    }

    public static String getDELIMITER() {
        return DELIMITER;
    }

    public static void setDELIMITER(String DELIMITER) {
        BaseDados.DELIMITER = DELIMITER;
    }

    public static String getDELIMITERDATA() {
        return DELIMITERDATA;
    }

    public static void setDELIMITERDATA(String DELIMITERDATA) {
        BaseDados.DELIMITERDATA = DELIMITERDATA;
    }

    public static Integer getNumeroUsers() {
        return numeroUsers;
    }

    public static void setNumeroUsers(Integer numeroUsers) {
        BaseDados.numeroUsers = numeroUsers;
    }

    public RedBlackBST_AED2<Integer, Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(RedBlackBST_AED2<Integer, Aluno> alunos) {
        this.alunos = alunos;
    }

    public RedBlackBST_AED2<Integer, Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(RedBlackBST_AED2<Integer, Professor> professores) {
        this.professores = professores;
    }

    public RedBlackBST_AED2<Integer, Sala> getSalas() {
        return salas;
    }

    public void setSalas(RedBlackBST_AED2<Integer, Sala> salas) {
        this.salas = salas;
    }

    public SeparateChainingHashST<String, Curso> getCursos() {
        return cursos;
    }

    public void setCursos(SeparateChainingHashST<String, Curso> cursos) {
        this.cursos = cursos;
    }

    public SeparateChainingHashST<String, UnidadeCurricular> getUnidadesCurriculares() {
        return unidadesCurriculares;
    }

    public void setUnidadesCurriculares(SeparateChainingHashST<String, UnidadeCurricular> unidadesCurriculares) {
        this.unidadesCurriculares = unidadesCurriculares;
    }

    public SeparateChainingHashST<String, Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(SeparateChainingHashST<String, Turma> turmas) {
        this.turmas = turmas;
    }

    public RedBlackBST_AED2<Integer, Aluno> getHistoricoAlunos() {
        return historicoAlunos;
    }

    public void setHistoricoAlunos(RedBlackBST_AED2<Integer, Aluno> historicoAlunos) {
        this.historicoAlunos = historicoAlunos;
    }

    public RedBlackBST_AED2<Integer, Professor> getHistoricoProfessores() {
        return historicoProfessores;
    }

    public void setHistoricoProfessores(RedBlackBST_AED2<Integer, Professor> historicoProfessores) {
        this.historicoProfessores = historicoProfessores;
    }

    public RedBlackBST_AED2<Integer, Sala> getHistoricoSalas() {
        return historicoSalas;
    }

    public void setHistoricoSalas(RedBlackBST_AED2<Integer, Sala> historicoSalas) {
        this.historicoSalas = historicoSalas;
    }

    public SeparateChainingHashST<String, UnidadeCurricular> getHistoricoUnidadesCurriculares() {
        return historicoUnidadesCurriculares;
    }

    public void setHistoricoUnidadesCurriculares(SeparateChainingHashST<String, UnidadeCurricular> historicoUnidadesCurriculares) {
        this.historicoUnidadesCurriculares = historicoUnidadesCurriculares;
    }

    public SeparateChainingHashST<String, Turma> getHistoricoTurmas() {
        return historicoTurmas;
    }

    public void setHistoricoTurmas(SeparateChainingHashST<String, Turma> historicoTurmas) {
        this.historicoTurmas = historicoTurmas;
    }

    /**
     * Funcao para carregar alunos na base de dados
     */
    public void carregarAlunos() {
        String alunosPath = "../DadosIniciais/alunos.txt";
        In in = new In(alunosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento, Curso curso, Integer ano
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[2].split(DELIMITERDATA);
            Integer id = Integer.parseInt(fields[0]);
            String nome = fields[1];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Curso curso = cursos.get(fields[3]);
            if (fields.length == 5) {
                Integer anoCurso = Integer.parseInt(fields[4]);
                Aluno aluno = new Aluno(id, nome, dataNascimento, curso, anoCurso);
                alunos.put(id, aluno);
                //   System.out.println("adicionei o " + id + " " + nome);
                numeroUsers++;
            } else {
                Aluno aluno = new Aluno(id, nome, dataNascimento, curso);
                alunos.put(id, aluno);
                numeroUsers++;

            }
        }
    }

    public void carregarAlunosVersaoSimplificada() {
        String alunosPath = "../DadosIniciais/alunos.txt";
        In in = new In(alunosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento, Curso curso, Integer ano
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[1].split(DELIMITERDATA);
            String nome = fields[0];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Aluno aluno = new Aluno(numeroUsers, nome, dataNascimento);
            alunos.put(numeroUsers, aluno);
            numeroUsers++;
        }
    }

    /**
     * Funcao para carregar professores na base de dados
     */
    public void carregarProfessores() {
        String professoresPath = "../DadosIniciais/professores.txt";
        In in = new In(professoresPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[2].split(DELIMITERDATA);
            Integer id = Integer.parseInt(fields[0]);
            String nome = fields[1];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Professor novo = new Professor(id, nome, dataNascimento);
            professores.put(id, novo);
            numeroUsers++;

        }
    }
    public void carregarProfessoresVersaoSimplificada() {
        String professoresPath = "../DadosIniciais/professores.txt";
        In in = new In(professoresPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[1].split(DELIMITERDATA);
            String nome = fields[0];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Professor novo = new Professor(numeroUsers, nome, dataNascimento);
            professores.put(numeroUsers, novo);
            numeroUsers++;

        }
    }

    /**
     * Funcao para carregar unidades curriculares na base de dados
     */
    public void carregarUc() {
        String unidadesCurricularesPath = "../DadosIniciais/unidadeCurriculares.txt";
        In in = new In(unidadesCurricularesPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //String nome, Integer ano, Integer semestre
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String nome = fields[0];
            Integer ano = Integer.parseInt(fields[1]);
            Integer semestre = Integer.parseInt(fields[2]);
            UnidadeCurricular unidadeCurricular = new UnidadeCurricular(nome, ano, semestre);
            unidadesCurriculares.put(nome, unidadeCurricular);
        }
    }

    /**
     * Funcao para carregar cursos na base de dados
     */
    public void carregarCursos() {
        String cursosPath = "../DadosIniciais/cursos.txt";
        In in = new In(cursosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //String id, String nome
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String id = fields[0];
            String nome = fields[1];
            Curso novo = new Curso(id, nome);
            cursos.put(id, novo);
        }
    }

    /**
     * Função para carregar salas na base de dados
     */
    public void carregarSalas() {
        String salasPath = "../DadosIniciais/salas.txt";
        In in = new In(salasPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso ou Integer id, Integer locatacao, Boolean projector, Integer piso
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            Integer id = Integer.parseInt(fields[0]);
            Integer piso = Integer.parseInt(fields[1]);
            Integer lotacao = Integer.parseInt(fields[2]); //1;0;70;15;false
            if (fields.length == 5) {
                Integer tomadas = Integer.parseInt(fields[3]);
                Boolean projector = Boolean.parseBoolean(fields[4]);
                Sala sala = new Sala(id, lotacao, tomadas, projector, piso);
                salas.put(id, sala);
            } else {
                Boolean projector = Boolean.parseBoolean(fields[3]);
                Sala sala = new Sala(id, lotacao, projector, piso);
                salas.put(id, sala);
            }
        }
    }
    public void carregarSalasV2() {
        String salasPath = "../DadosIniciais/salasV2.txt";
        In in = new In(salasPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso ou Integer id, Integer locatacao, Boolean projector, Integer piso
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            Integer size = fields.length;
            Integer id = Integer.parseInt(fields[0]);
            Integer piso = Integer.parseInt(fields[1]);
            Integer lotacao = Integer.parseInt(fields[2]);
            String descricao = "Sala "+id;
            if(size==7){ // Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso, Ponto3D ponto
                Integer tomadas = Integer.parseInt(fields[3]);
                Boolean projector = Boolean.parseBoolean(fields[4]);
                Double x = Double.parseDouble(fields[5]);
                Double y = Double.parseDouble(fields[6]);
                Ponto3D ponto = new Ponto3D(x,y,piso,descricao,true);
                Sala aux = new Sala(id,lotacao,tomadas,projector,piso,ponto);
                if(!this.getSalas().contains(id)){
                    this.getSalas().put(id,aux);
                }
            }else{
                Integer tomadas = lotacao;
                Boolean projector = Boolean.parseBoolean(fields[3]);
                Double x = Double.parseDouble(fields[4]);
                Double y = Double.parseDouble(fields[5]);
                Ponto3D ponto = new Ponto3D(x,y,piso,descricao,true);
                Sala aux = new Sala(id,lotacao,tomadas,projector,piso,ponto);
                if(!this.getSalas().contains(id)){
                    this.getSalas().put(id,aux);
                }
            }
        }
    }

    /**
     * Função para carregar turmas na base de dados
     */
    public void carregarTurmas() {
        String turmasPath = "../DadosIniciais/turmas.txt";
        In in = new In(turmasPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String id = fields[0];
            UnidadeCurricular unidadeCurricular = unidadesCurriculares.get(fields[2].toUpperCase());
            Professor professor = professores.get(Integer.parseInt(fields[1]));
            Turma turma = new Turma(id, unidadeCurricular, professor);
            turmas.put(id, turma);
        }
    }

    /**
     * Função para carregar horarios das aulas na base de dados
     */
    public void carregarHorarios() {
        String horariosPath = "../DadosIniciais/horarios.txt";
        In in = new In(horariosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //codturma;sala;dia;inicio;minInicio;fim;minutosFim

            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            Turma turma = turmas.get(fields[0]);
            Sala sala = salas.get(Integer.parseInt(fields[1]));
            Integer dayOfWeek = Integer.parseInt(fields[2]);
            Integer horaInicio = Integer.parseInt(fields[3]);
            Integer minInicio = Integer.parseInt(fields[4]);
            Integer horaFim = Integer.parseInt(fields[5]);
            Integer minFim = Integer.parseInt(fields[6]);
            Horario horario = new Horario(sala, horaInicio, horaFim, minInicio, minFim, dayOfWeek);
            turma.adicionaHorario(horario);
            horario.setTurma(turma);
        }
    }

    /**
     * Função para carregar atendimentos dos professores na base de dados
     */
    public void carregarAtendimentos() {
        String AtendimentosPath = "../DadosIniciais/atendimentos.txt";
        In in = new In(AtendimentosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);//PROFESSOR;DIA SEMANA;INICIO;MININICIO;FIM;MINFIM;SALA
            Integer idProfessor = Integer.parseInt(fields[0]);
            Integer dia = Integer.parseInt(fields[1]);
            Integer horasi = Integer.parseInt(fields[2]);
            Integer mini = Integer.parseInt(fields[3]);
            Integer horasf = Integer.parseInt(fields[4]);
            Integer minf = Integer.parseInt(fields[5]);
            Sala sala = salas.get(Integer.parseInt(fields[6]));
            Horario horario = new Horario(sala, horasi, horasf, mini, minf, dia);
            professores.get(idProfessor).adicionarHorarioAtendimento(horario);
        }
    }

    /**
     * Função para carregar alunos para turma que seja só unidades curriculares especificas
     */
    public void carregarAlunosTurmas() {
/*        String professoresPath = "../DadosIniciais/professores.txt";
        In in = new In(professoresPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            int i = 1;
            Aluno aluno = alunos.get(Integer.parseInt(fields[0]));
            while (i <= fields.length) {
                Turma turma = turmas.get(fields[i]);
///                aluno.adicionarCadeirasAoEstudante(turma);
                i += 1;
            }
        }*/
    }

    /**
     * Função para carregar o historico de alunos
     */
    public void carregarHistoricoAlunos() {
        String alunosPath = "../DadosIniciais/HistoricoAlunos.txt";
        In in = new In(alunosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento, Curso curso, Integer ano
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[2].split(DELIMITERDATA);
            Integer id = Integer.parseInt(fields[0]);
            String nome = fields[1];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Curso curso = cursos.get(fields[3]);
            if (fields.length == 5) {
                Integer anoCurso = Integer.parseInt(fields[4]);
                Aluno aluno = new Aluno(id, nome, dataNascimento, curso, anoCurso);
                alunos.put(id, aluno);
                numeroUsers++;
            } else {
                Aluno aluno = new Aluno(id, nome, dataNascimento, curso);
                alunos.put(id, aluno);
                numeroUsers++;

            }
        }
    }

    ;

    /**
     * Função para carregar o historico de professores
     */
    public void carregarHistoricoProfessores() {
        String professoresPath = "../DadosIniciais/historicoProfessores.txt";
        In in = new In(professoresPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[2].split(DELIMITERDATA);
            Integer id = Integer.parseInt(fields[0]);
            String nome = fields[1];
            Integer dia = Integer.parseInt(fields1[0]);
            Integer mes = Integer.parseInt(fields1[1]);
            Integer ano = Integer.parseInt(fields1[2]);
            Date dataNascimento = new Date(dia, mes, ano);
            Professor novo = new Professor(id, nome, dataNascimento);
            professores.put(id, novo);
            numeroUsers++;

        }
    }

    ;

    /**
     * Função para carregar o historico de unidades curriculares
     */
    public void carregarHistoricoUc() {
        String unidadesCurricularesPath = "../DadosIniciais/historicoUnidadesCurriculares.txt";
        In in = new In(unidadesCurricularesPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //String nome, Integer ano, Integer semestre
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String nome = fields[0];
            Integer ano = Integer.parseInt(fields[1]);
            Integer semestre = Integer.parseInt(fields[2]);
            UnidadeCurricular unidadeCurricular = new UnidadeCurricular(nome, ano, semestre);
            unidadesCurriculares.put(nome, unidadeCurricular);
        }
    }

    ;

    /**
     * Função para carregar o historico de salas
     */
    public void carregarHistoricoSalas() {
        String cursosPath = "../DadosIniciais/HistoricoSalas.txt";
        In in = new In(cursosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //String id, String nome
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            Integer id = Integer.parseInt(fields[0]);
            Integer lotacao = Integer.parseInt(fields[1]);
            Integer piso = Integer.parseInt(fields[2]);


            if (fields.length == 5) {
                Integer tomadas = Integer.parseInt(fields[3]);
                Boolean projector = Boolean.parseBoolean(fields[4]);
                Sala sala = new Sala(id, lotacao, tomadas, projector, piso);
                salas.put(id, sala);
            } else {
                Boolean projector = Boolean.parseBoolean(fields[3]);
                Sala sala = new Sala(id, lotacao, projector, piso);
                salas.put(id, sala);
            }
        }
    }

    ;

    /**
     * Função para carregar o historico de turmas
     */
    public void carregarHistoricoTurmas() {
        String salasPath = "../DadosIniciais/historicoSalas.txt";
        In in = new In(salasPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, Integer locatacao, Integer tomadas, Boolean projector, Integer piso ou Integer id, Integer locatacao, Boolean projector, Integer piso
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            String[] fields1 = fields[2].split(DELIMITERDATA);
            Integer id = Integer.parseInt(fields[0]);
            Integer lotacao = Integer.parseInt(fields[1]);
            if (fields.length == 5) {
                Integer tomadas = Integer.parseInt(fields[2]);
                Boolean projector = Boolean.parseBoolean(fields[3]);
                Integer piso = Integer.parseInt(fields[4]);

            } else {
                Boolean projector = Boolean.parseBoolean(fields[2]);
                Integer piso = Integer.parseInt(fields[3]);
                Sala sala = new Sala(id, lotacao, projector, piso);
                salas.put(id, sala);
            }
        }
    }


    /**
     * Função para carregar o historico de atedimentos
     */
    public void carregarHistoricoAtendimentos() {
        String AtendimentosPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosHistoricos\\historicoAtendimentos.txt";
        In in = new In(AtendimentosPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);

        }
    }


    /**
     * Função para carregar o historico de cursos
     */
    public void carregarHistoricoCursos() {
        String professoresPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosHistoricos\\HistoricoCurso.txt";
        In in = new In(professoresPath);
        String s = in.readLine();
        while (!in.isEmpty()) { //Integer id, String nome, Date dataNascimento
            s = in.readLine();
            String[] fields = s.split(DELIMITER);
            int i = 1;
            Aluno aluno = alunos.get(Integer.parseInt(fields[0]));
            while (i <= fields.length) {
                Turma turma = turmas.get(fields[i]);
                aluno.adicionarCadeirasAoEstudante(turma);
                i += 1;
            }
        }
    }


    public void gravarHistoricoAlunos() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoAlunos.txt";
        Out out = new Out(auxPath);
        for (Integer i : historicoAlunos.keys()) {
            out.println(historicoAlunos.get(i).gravar());
        }
        out.close();

    }

    public void gravarHistoricoProfessores() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoProfessores.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (Integer i : historicoProfessores.keys()) {
            out.println(j + " - > " + historicoProfessores.get(i).gravar());
            j++;
        }
        out.close();
    }


    public void gravarHistoricoUc() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoUc.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (String i : historicoUnidadesCurriculares.keys()) {
            out.println(j + " -> " + historicoUnidadesCurriculares.get(i).gravar());
            j++;
        }
        out.close();
    }


    public void gravarHistoricoSalas() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoSalas.txt";
        Out out = new Out(auxPath);
        for (Integer i : historicoSalas.keys()) {
            out.println(historicoSalas.get(i).gravar());
        }
        out.close();

    }


    public void gravarHistoricoTurmas() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoTurmas.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (String i : historicoTurmas.keys()) {
            out.println(j + " -> " + historicoTurmas.get(i).gravar());
            j++;
        }
        out.close();
    }


    public void gravarHistoricoAtendimentos() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoAtendimentos.txt";
        Out out = new Out(auxPath);
        for (Integer i : this.getProfessores().keys()) {
            out.println(this.getProfessores().get(i).gravar());
            for (Atendimento j : this.getProfessores().get(i).getHistorioDeAtendimentos())
                out.println(j.gravar());
        }
        out.close();
    }

    public void gravarHistoricoCursos() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\Historicos\\HistoricoCursos.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (String i : historicoCursos.keys()) {
            out.println(j + " -> " + historicoCursos.get(i).gravar());
            j++;
        }
        out.close();
    }


    public void gravarAlunos() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\alunos.txt";
        Out out = new Out(auxPath);
        for (Integer i : alunos.keys()) {
            out.println(alunos.get(i).gravar());
        }
        out.close();
    }

    public void gravarProfessores() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\professores.txt";
        Out out = new Out(auxPath);
        for (Integer i : professores.keys()) {
            out.println(professores.get(i).gravar());
        }
        out.close();
    }

    public void gravarUc() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\Uc.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (String s : unidadesCurriculares.keys()) {
            out.println(j + " -> " + unidadesCurriculares.get(s).gravar());
            j++;
        }
        out.close();
    }


    public void gravarSalas() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\salas.txt";
        Out out = new Out(auxPath);
        for (Integer i : salas.keys()) {
            out.println(salas.get(i).gravar());
        }
        out.close();
    }


    public void gravarTurmas() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\turmas.txt";
        Out out = new Out(auxPath);
        int j = 0;
        for (String s : turmas.keys()) {
            out.println(j + " -> " + turmas.get(s).gravar());
            j++;
        }
        out.close();
    }

    public void gravarHorarios() {
        String auxPath = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\horarios.txt";
        Out out = new Out(auxPath);
        for (String s : turmas.keys()) {
            for (Horario h : turmas.get(s).getHorarios()) {
                out.println(turmas.get(s).getId() + ";" + h.gravar());
            }
        }
        out.close();
    }


    public void gravarCursos() {
        String auxAbs = "C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosAlterados\\Cursos.txt";
        String aux = "../DadosAlterados/Cursos.txt";
        Out out = new Out(auxAbs);
        int i = 0;
        out.println("dados de " + this.historicoCursos.size() + " cursos : ");
        for (String s : this.cursos.keys()) {
            out.println(i + " -> " + this.cursos.get(s).gravar());
            i++;
        }
        out.close();
    }


    /**
     * imprime todos os alunos
     */
    public void printAlunos() {
        if (alunos.size() == 0) {
            System.out.println("Não tem dados de alunos registados");
            return;
        }
        System.out.println("\nLista de alunos:");
        for (Integer i : alunos.keys()) {
            System.out.println("\t" + alunos.get(i));
        }
    }

    /**
     * imprime todos os professores
     */
    public void printProfessores() {
        if (professores.size() == 0) {
            System.out.println("Não tem dados de professores registados");
            return;
        }
        System.out.println("\nLista de professores:");
        for (Integer i : professores.keys()) {
            System.out.println("\t" + professores.get(i));
        }
    }


    /**
     * imprime todos os unidades curriculares
     */
    public void printUc() {
        System.out.println("\nLista de todas as cadeiras:");
        for (String i : unidadesCurriculares.keys()) {
            System.out.println("\t" + unidadesCurriculares.get(i));
        }
    }


    /**
     * imprime todos os salas
     */
    public void printSalas() {
        System.out.println("\nLista de salas:");
        for (Integer i : salas.keys()) {
            System.out.println("\t" + salas.get(i).toStringCompleto());
        }
    }

    /**
     * imprime todos os turmas
     */
    public void printTurmas() {
        System.out.println("\nLista de turmas:");
        for (String i : turmas.keys()) {
            System.out.println("\t" + turmas.get(i));
        }
    }

    /**
     * imprime todos os turmas com os horarios
     */
    public void printTurmasComHorarios() {
        System.out.println("\nLista de turmas:");
        for (String i : turmas.keys()) {
            System.out.println("\t" + turmas.get(i));
            turmas.get(i).printHorarios();
        }
    }

    /**
     * imprime todos os atendimentos
     */
    public void printAtendimentos() {
        for (Integer i : professores.keys()) {
            //System.out.println(professores.get(i));
            professores.get(i).imprimeAtendimento();
        }
    }


    /**
     * imprime todos os cursos
     */
    public void printCursos() {
        System.out.println(" Lista de todos os cursos:");
        for (String i : cursos.keys()) {
            System.out.println("\t" + cursos.get(i));
        }
    }


    /**
     * Adiciona um aluno na lista
     *
     * @param aluno adicionar
     */
    public void adicionarAlunos(Aluno aluno) {
        if (!alunos.contains(aluno.getId())) {
            alunos.put(aluno.getId(), aluno);
        }
    }


    /**
     * adicionar um professor
     *
     * @param professor adicionar
     */
    public void adicionarProfessores(Professor professor) {
        if (!professores.contains(professor.getId())) {
            professores.put(professor.getId(), professor);
        }
    }

    /**
     * Adiciona um aluno na lista
     *
     * @param nome           aluno
     * @param dataNascimento aluno
     * @param curso          aluno
     * @param ano            aluno
     */
    public void adicionarAluno(String nome, Date dataNascimento, Curso curso, Integer ano) {
        Aluno aluno = new Aluno(numeroUsers + 1, nome, dataNascimento, curso, ano);
        if (!alunos.contains(aluno.getId())) {
            alunos.put(aluno.getId(), aluno);
            numeroUsers++;
        }
    }

    /**
     * Adiciona um aluno na lista
     *
     * @param nome           aluno
     * @param dataNascimento aluno
     * @param curso          aluno
     */
    public void adicionarAlunos(String nome, Date dataNascimento, Curso curso) {
        Aluno aluno = new Aluno(numeroUsers + 1, nome, dataNascimento, curso);
        if (!alunos.contains(aluno.getId())) {
            alunos.put(aluno.getId(), aluno);
            numeroUsers++;
        }
    }

    /**
     * Adiciona um horario a uma turma
     *
     * @param turma   que vai receber o novo horario
     * @param horario adicionar
     */
    public void adicionarHorario(Turma turma, Horario horario) {
        if (turmas.contains(turma.getId())) {
            turmas.get(turma.getId()).adicionaHorario(horario);
        }
    }

    /**
     * adicionar um professor
     *
     * @param nome           professor
     * @param dataNascimento professor
     */
    public void adicionarProfessores(String nome, Date dataNascimento) {
        Professor professor = new Professor(numeroUsers + 1, nome, dataNascimento);
        if (!professores.contains(numeroUsers + 1) && !verificaEmailProfessor(professor.getEmail())) {
            professores.put(numeroUsers + 1, professor);
            numeroUsers++;
        }
    }

    public Boolean verificaEmailProfessor(String email) {
        for (Integer i : this.professores.keys()) {
            if (this.professores.get(i).getEmail().compareTo(email) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * adicionar uma unidade curricular
     *
     * @param unidadeCurricular adicionar
     */
    public void adicionarUc(UnidadeCurricular unidadeCurricular) {
        if (!unidadesCurriculares.contains(unidadeCurricular.getNome())) {
            unidadesCurriculares.put(unidadeCurricular.getNome(), unidadeCurricular);
        }
    }


    /**
     * adicionar uma sala
     *
     * @param sala adicionar
     */
    public void adicionarSalas(Sala sala) {
        if (!salas.contains(sala.getId())) {
            salas.put(sala.getId(), sala);
        }
    }


    /**
     * adicionar uma turma
     *
     * @param turma adicionar
     */
    public void adicionarTurmas(Turma turma) {
        if (!turmas.contains(turma.getId())) {
            turmas.put(turma.getId(), turma);
        }
    }


    /**
     * adicionar um atendimento
     *
     * @param atendimento adicionar
     * @param professor   na qual vai adicionar
     */
    public void adicionarAtendimentos(Atendimento atendimento, Professor professor) {
        if (professores.contains(professor.getId())) {
            if (!professores.get(professor.getId()).getListaAtendimentosAlunos().contains(atendimento)) {
                professores.get(professor.getId()).getListaAtendimentosAlunos().add(atendimento);
            }
        }
    }


    /**
     * adicionar um curso
     *
     * @param curso adicionar
     */
    public void adicionarCursos(Curso curso) {
        if (!cursos.contains(curso.getId())) {
            cursos.put(curso.getId(), curso);
        }
    }


    /**
     * Apaga o aluno da lista de alunos
     *
     * @param aluno apagar
     */
    public void deleteAlunos(Aluno aluno) {
        if (alunos.contains(aluno.getId())) {
            Aluno aux = alunos.get(aluno.getId());
            aluno.getCurso().removeAlunoCurso(aluno);
            alunos.delete(aluno.getId());
            historicoAlunos.put(aux.getId(), aux);
        }
    }

    /**
     * Apaga o aluno da lista de alunos
     *
     * @param alunoId apagar
     */
    public void deleteAlunos(Integer alunoId) {
        if (alunos.contains(alunoId)) {
            Aluno aux = alunos.get(alunoId);
            alunos.get(alunoId).getCurso().removeAlunoCurso(aux);
            alunos.delete(alunoId);
            historicoAlunos.put(aux.getId(), aux);
        }
    }

    /**
     * Apaga o professor da lista de professor
     *
     * @param professor apagar
     */
    public void deleteProfessores(Professor professor) {
        if (professores.contains(professor.getId())) {
            Professor aux = professores.get(professor.getId());
            professores.delete(professor.getId());
            historicoProfessores.put(aux.getId(), aux);
        }
    }

    /**
     * Apaga o professor da lista de professor
     *
     * @param professorId apagar
     */
    public void deleteProfessores(Integer professorId) {
        if (professores.contains(professorId)) {
            Professor aux = professores.get(professorId);
            professores.delete(professorId);
            historicoProfessores.put(aux.getId(), aux);
        }
    }

    /**
     * Apaga o unidadeCurricular da lista de unidade curriculares
     *
     * @param unidadeCurricular apagar
     */
    public void deleteUc(UnidadeCurricular unidadeCurricular) {
        if (unidadesCurriculares.contains(unidadeCurricular.getNome())) {
            UnidadeCurricular aux = unidadesCurriculares.get(unidadeCurricular.getNome());
            unidadesCurriculares.delete(aux.getNome());
        }
    }


    /**
     * Apaga o aluno da lista de sala
     *
     * @param sala apagar
     */
    public void deleteSalas(Sala sala) {
        if (salas.contains(sala.getId())) {
            Sala aux = salas.get(sala.getId());
            salas.delete(sala.getId());
            historicoSalas.put(aux.getId(), aux);
        }
    }


    /**
     * Apaga uma turma
     * Remove dos alunos e do curso
     *
     * @param turma apagar
     */
    public void deleteTurmas(Turma turma) {
        if (turmas.contains(turma.getId())) {
            if (turmas.get(turma.getId()).getListaAlunosInscritos().size() > 0) {
                turmas.get(turma.getId()).removeTodosAluno();
            }
            turmas.delete(turma.getId());
            /*ArrayList<Curso> auxCursos = pesquisaCadeiraCurso(cursos, turma);
            for (Curso auxCurso : auxCursos) {
                auxCurso.removeTurma(turma);
            }*/
        }
    }


    /**
     * Apaga um atendimento do professor
     *
     * @param atendimento apagar
     * @param professor   na qual vai apagar o atendimento
     */
    public void deleteAtendimentos(Atendimento atendimento, Professor professor) {
        if (professores.contains(professor.getId())) {
            if (professores.get(professor.getId()).getListaAtendimentosAlunos().contains(atendimento)) {
                professores.get(professor.getId()).removerAtendimento(atendimento);
            }
        }
    }


    /**
     * Apagar um curso
     *
     * @param curso apagar
     */
    public void deleteCursos(Curso curso) {
        if (cursos.contains(curso.getId())) {
            Curso aux = cursos.get(curso.getId());
            cursos.get(curso.getId()).removeTodasTurma();
            for (Integer i : alunos.keys()) {
                if (alunos.get(i).getCurso().getId().compareTo(curso.getId()) == 0) {
                    alunos.get(i).setCurso(null);
                    alunos.get(i).apagaTodosOsHorarios();
                }
            }
            cursos.delete(curso.getId());
            cursos.put(aux.getId(), aux);
        }
    }

    public void deleteCursos(String idCurso) {
        if (cursos.contains(idCurso)) {
            cursos.delete(idCurso);
        }
    }

    /**
     * Apagar um horario a uma turma
     *
     * @param turma      que vai apagar o novo horario
     * @param dia        da semana
     * @param horaInicio da aula
     */
    public void apagarHorario(Turma turma, Integer dia, Integer horaInicio) {
        if (turmas.contains(turma.getId())) {
            turmas.get(turma.getId()).apagarHorario(dia, horaInicio);
        }
    }

    public void apagarHorario(Turma turma, Integer dia, Integer horaInicio, Sala sala) {
        if (turmas.contains(turma.getId())) {
            turmas.get(turma.getId()).apagarHorario(dia, horaInicio, sala);
        }
    }

    public String gerarString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public String verificaCodigoCurso(String nome) {
        String aux = nome.toUpperCase();
        while (cursos.contains(aux)) {
            aux = gerarString();
        }
        return aux.toUpperCase();
    }

    public String CriarCodigoCurso(String nome) {
        String aux = nome.toUpperCase();
        while (cursos.contains(aux)) {
            aux = gerarString();
        }
        return aux.toUpperCase();
    }

    public String verificaCodigoTurma(String nome) {
        String aux = nome;
        while (turmas.contains(aux)) {
            aux = gerarString();
        }
        return aux.toUpperCase();
    }


    public SeparateChainingHashST<String, Curso> getHistoricoCursos() {
        return historicoCursos;
    }

    public void setHistoricoCursos(SeparateChainingHashST<String, Curso> historicoCursos) {
        this.historicoCursos = historicoCursos;
    }

    public static void main(String[] args) {
        BaseDados bd = new BaseDados(true);
        bd.printTurmas();
        String nova = bd.gerarString();
        System.out.println(nova);
    }
}
