package API;

import algoritmos.RedBlackBST_AED2;
import algoritmos.SeparateChainingHashST;

import java.io.Serializable;

public class Curso implements Serializable,GravarFicheiro {

    private String id;

    private String nome;

    private RedBlackBST_AED2<Integer , Aluno> alunosInscritos;

    private SeparateChainingHashST<String, Turma> listaTurmas;

    private RedBlackBST_AED2<Integer,Professor> listaProfessores;

    private Integer numeroDeTurmas =0;

    public Curso(String id, String nome) {
        this.id = id.toUpperCase();
        this.nome = nome.toUpperCase();
        this.alunosInscritos = new RedBlackBST_AED2<>();
        this.listaTurmas = new SeparateChainingHashST<>();
        this.listaProfessores =  new RedBlackBST_AED2<>();
        this.numeroDeTurmas =0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RedBlackBST_AED2<Integer, Aluno> getAlunosInscritos() {
        return alunosInscritos;
    }

    public void setAlunosInscritos(RedBlackBST_AED2<Integer, Aluno> alunosInscritos) {
        this.alunosInscritos = alunosInscritos;
    }

    public SeparateChainingHashST<String, Turma> getListaTurmas() {
        return listaTurmas;
    }

    public void setListaTurmas(SeparateChainingHashST<String, Turma> listaTurmas) {
        this.listaTurmas = listaTurmas;
    }

    public RedBlackBST_AED2<Integer, Professor> getListaProfessores() {
        return listaProfessores;
    }

    public void setListaProfessores(RedBlackBST_AED2<Integer, Professor> listaProfessores) {
        this.listaProfessores = listaProfessores;
    }

    /**
     * Apaga o aluno do curso e as cadeiras na qual ele esta inscrito
     * @param aluno que recebe por parametro para ser removido
     */
    public void removeAlunoCurso(Aluno aluno){
        if(alunosInscritos.contains(aluno.getId())){
            for(String s : listaTurmas.keys()){
                listaTurmas.get(s).removeAluno(aluno);
            }
        }
    }

    /**
     * Remove turma de um curso
     * @param turma apagar
     */
    public void removeTurma(Turma turma){
        if(listaTurmas.contains(turma.getId())){
            listaTurmas.delete(turma.getId());
        }
    }
    /**
     * Remove todas turma de um curso
     */
    public void removeTodasTurma(){
        for(String s : listaTurmas.keys()){
            listaTurmas.get(s).removeTodosAluno();
            listaTurmas.delete(s);
            numeroDeTurmas--;
        }
    }

    public Integer getNumeroDeTurmas() {
        return numeroDeTurmas;
    }

    public void setNumeroDeTurmas(Integer numeroDeTurmas) {
        this.numeroDeTurmas = numeroDeTurmas;
    }

    /**
     * Adiciona turma
     * @param turma Turma;
     */
    public void adicionaTurma(Turma turma){
        if(!this.listaTurmas.contains(turma.getId())){
            this.listaTurmas.put(turma.getId(),turma);
            numeroDeTurmas++;
        }
    }
    @Override
    public String toString() {
        return "Curso id: "+id+" nome: "+nome;
    }

    public String gravar() {
        return this.id+";"+this.nome;
    }
}
