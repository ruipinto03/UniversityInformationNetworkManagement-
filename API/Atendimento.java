package API;

import java.io.Serializable;

public class Atendimento implements Serializable, GravarFicheiro{
    private Professor professor;
    private Aluno aluno;
    private Horario horario;

    public Atendimento(Professor professor, Horario horario) {
        this.professor = professor;
        this.aluno = null;
        this.horario = horario;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    @Override
    public String gravar() {
        return professor.getId()+";"+horario.gravar();
    }

    @Override
    public String toString() {
            return professor.getNome()+" - " + horario;
    }
}
