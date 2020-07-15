package API;

import algoritmos.RedBlackBST_AED2;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;

public class Turma implements Serializable, GravarFicheiro {
    private String id;
    private UnidadeCurricular unidadeCurricular;
    private RedBlackBST_AED2<Integer, Aluno> listaAlunosInscritos;
    private Professor professor;
    private ArrayList<Horario> horarios;

    public Turma(String id, UnidadeCurricular unidadeCurricular, Professor professor) {
        this.id = id;
        this.unidadeCurricular = unidadeCurricular;
        this.listaAlunosInscritos = new RedBlackBST_AED2<>();
        this.professor = professor;
        this.horarios = new ArrayList<>();
        this.professor.adicionaTurma(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UnidadeCurricular getUnidadeCurricular() {
        return unidadeCurricular;
    }

    public void setUnidadeCurricular(UnidadeCurricular unidadeCurricular) {
        this.unidadeCurricular = unidadeCurricular;
    }

    public RedBlackBST_AED2<Integer, Aluno> getListaAlunosInscritos() {
        return listaAlunosInscritos;
    }

    public void setListaAlunosInscritos(RedBlackBST_AED2<Integer, Aluno> listaAlunosInscritos) {
        this.listaAlunosInscritos = listaAlunosInscritos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    /**
     * Remove um aluno da turma
     *
     * @param aluno apagar
     */
    public void removeAluno(Aluno aluno) {
        if (listaAlunosInscritos.contains(aluno.getId())) {
            listaAlunosInscritos.delete(aluno.getId());
            aluno.apagaCadeirasAoEstudante(this);
        }
    }

    /**
     * Remove todos os alunos da lista de alunos
     */
    public void removeTodosAluno() {
        for (Integer i : listaAlunosInscritos.keys()) {
            removeAluno(listaAlunosInscritos.get(i));
        }
    }

    /**
     * Imprime os horarios da turma
     */
    public void printHorarios() {
        for (Horario horario : horarios) {
            System.out.println(horario);
        }
    }

    /**
     * Adiciona um horario a turma
     *
     * @param horario a inserir
     */
    public void adicionaHorario(Horario horario) {
        if (horarios.isEmpty()) {
            horarios.add(horario);
            return;
        }
        if (horarios.contains(horario)) {
            System.out.println("Ja existe um horario igual");
            return;
        } else {
            horarios.add(horario);
        }
    }

    /**
     * Apagar um horario a turma
     *
     * @param dia da semana
     * @param horaInicio da aula
     */
    public void apagarHorario(Integer dia, Integer horaInicio, Sala sala) {
        if (horarios.isEmpty()) {
            System.out.println("Não tem horarios na turma");
            return;
        }
        Iterator<Horario> i = this.horarios.iterator();
        while (i.hasNext()) {
            Horario horario = (Horario) i.next();
            if (horario.getHoraInicio().equals(horaInicio) && horario.getDia() == DayOfWeek.of(dia) && sala.getId()==horario.getSala().getId()) {
                i.remove();
            }
        }
    }

    public void apagarHorario(Integer dia, Integer horaInicio) {
        if (horarios.isEmpty()) {
            System.out.println("Não tem horarios na turma");
            return;
        }
        Iterator<Horario> i = this.horarios.iterator();
        while (i.hasNext()) {
            Horario horario = (Horario) i.next();
            if (horario.getHoraInicio().equals(horaInicio) && horario.getDia() == DayOfWeek.of(dia)) {
                i.remove();
            }
        }
    }

    @Override
    public String toString() {
        return "Turma: " + id + " nome da disciplina " + unidadeCurricular.getNome() + " dada por " + professor.getNome();
    }

    public String gravar() {
        return id + ";" + unidadeCurricular.getNome() + ";" + professor.getId();
    }
}
