package API;

import algoritmos.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;

public class Aluno extends Pessoa implements Serializable {
    private Curso curso;
    private SeparateChainingHashST<String, Turma> listaTurmas;
    private ArrayList<Horario> horarioAulas;
    private Integer ano;
    private Integer numeroCadeirasInscritas;

    public Aluno(Integer id, String nome, Date dataNascimento, Curso curso, Integer ano) {
        super(id, nome, dataNascimento);
        this.setEmail(id + "@UFP.EDU.PT".toUpperCase());
        this.curso = curso;
        this.listaTurmas = new SeparateChainingHashST<>();
        this.horarioAulas = new ArrayList<>();
        this.ano = ano;
        this.numeroCadeirasInscritas = 0;
    }
    public Aluno(Integer id ,String nome, Date dataNascimento) {
        super(id, nome, dataNascimento);
        this.setEmail(id + "@UFP.EDU.PT".toUpperCase());
        this.curso = null;
        this.listaTurmas = new SeparateChainingHashST<>();
        this.horarioAulas = new ArrayList<>();
        this.ano = null;
        this.numeroCadeirasInscritas = 0;
    }

    public Aluno(Integer id, String nome, Date dataNascimento, Curso curso) {
        super(id, nome, dataNascimento);
        this.setEmail(id + "@UFP.EDU.PT".toUpperCase());
        this.curso = curso;
        this.listaTurmas = new SeparateChainingHashST<>();
        this.horarioAulas = new ArrayList<>();
        this.ano = 1;
        this.numeroCadeirasInscritas = 0;

    }

    public Integer getNumeroCadeirasInscritas() {
        return numeroCadeirasInscritas;
    }

    public void setNumeroCadeirasInscritas(Integer numeroCadeirasInscritas) {
        this.numeroCadeirasInscritas = numeroCadeirasInscritas;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public SeparateChainingHashST<String, Turma> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(SeparateChainingHashST<String, Turma> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }

    public ArrayList<Horario> getHorarioAulas() {
        return horarioAulas;
    }

    public void setHorarioAulas(ArrayList<Horario> horarioAulas) {
        this.horarioAulas = horarioAulas;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    /**
     * Esta funcão é para quando se cria um novo aluno independente do ano ele faz uma copia integral
     * de todas as cadeiras de um determinado ano. Inscrevendo o aluno na turma.
     * Adiciona o aluno na turma e a turma na lista de cadeiras do aluno.
     *
     * @param ano na qual o aluno vai ser inscrito
     */
    public void adicionarCadeirasAoEstudante(Integer ano) {
        for (String t : curso.getListaTurmas().keys()) {
            if (curso.getListaTurmas().get(t).getUnidadeCurricular().getAno() == ano) {
                curso.getListaTurmas().get(t).getListaAlunosInscritos().put(this.getId(), this);
                this.listaTurmas.put(curso.getListaTurmas().get(t).getId(), curso.getListaTurmas().get(t));
                horarioAulas.addAll(curso.getListaTurmas().get(t).getHorarios());
                this.numeroCadeirasInscritas++;
            }
        }
    }

    /**
     * Adiciona o aluno na turma e a turma na lista de cadeiras do aluno.
     *
     * @param turma na qual vai adicionar o aluno
     */
    public void adicionarCadeirasAoEstudante(Turma turma) {
        if (curso.getListaTurmas().contains(turma.getId())) { // caso exista a turma ele adiciona ao aluno e a turma
            curso.getListaTurmas().get(turma.getId()).getListaAlunosInscritos().put(this.getId(), this);
            this.listaTurmas.put(turma.getId(), turma);
            horarioAulas.addAll(curso.getListaTurmas().get(turma.getId()).getHorarios());
            numeroCadeirasInscritas++;
        }
    }

    public void adicionarCadeirasAoEstudante(ArrayList<Turma> turmas) {
        if (turmas.size() > 0) {
            for (Turma t : turmas) {
                if (!this.getListaTurmas().contains(t.getId())) { // caso exista a turma ele adiciona ao aluno e a turma
                    curso.getListaTurmas().get(t.getId()).getListaAlunosInscritos().put(this.getId(), this);
                    this.listaTurmas.put(t.getId(), t);
                    this.numeroCadeirasInscritas++;
                    horarioAulas.addAll(curso.getListaTurmas().get(t.getId()).getHorarios());

                }
            }
        }

    }

    /**
     * Remove o aluno na turma e a turma na lista de cadeiras do aluno.
     *
     * @param turma na qual vai remover o aluno
     */
    public void apagaCadeirasAoEstudante(Turma turma) {
        if (curso.getListaTurmas().contains(turma.getId())) { // caso exista a turma ele adiciona ao aluno e a turma
            curso.getListaTurmas().get(turma.getId()).getListaAlunosInscritos().delete(this.getId());
            this.listaTurmas.delete(turma.getId());
        }
    }

    /**
     * Imprime todas as cadeiras inscritas
     */
    public void printCadeirasInscritas() {
        for (String t : curso.getListaTurmas().keys()) {
            System.out.println(curso.getListaTurmas().get(t));
        }
    }

    /**
     * Apaga todos os horarios
     */
    public void apagaTodosOsHorarios() {
        for (Horario h : horarioAulas) {
            horarioAulas.remove(h);
        }
    }

    public Boolean retornarProfessor(Professor professor) {
        for (String turma : listaTurmas.keys()) {
            if (professor.getEmail().compareTo(listaTurmas.get(turma).getProfessor().getEmail()) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String gravar() {
        return super.gravar();
    }
}
