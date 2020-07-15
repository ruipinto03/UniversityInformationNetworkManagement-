package API;

import algoritmos.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Professor extends Pessoa implements Serializable {

    private SeparateChainingHashST<String,Turma> listaTurmasDarAulas;
    private ArrayList<Atendimento> listaAtendimentosAlunos;
    private ArrayList<Horario> horariosDasAulas;
    private ArrayList<Atendimento> historioDeAtendimentos;
    private Integer numeroTurmas;
    public Professor(Integer id, String nome, Date dataNascimento) {
        super(id, nome, dataNascimento);
        String aux = nome.replaceAll(" ","").toUpperCase()+"@UFP.EDU.PT".toUpperCase();
        this.setEmail(aux);
        this.listaAtendimentosAlunos = new ArrayList<>();
        this.horariosDasAulas = new ArrayList<>();
        this.listaTurmasDarAulas = new SeparateChainingHashST<>();
        this.historioDeAtendimentos = new ArrayList<>();
        this.numeroTurmas = 0;
    }

    public Integer getNumeroTurmas() {
        return numeroTurmas;
    }

    public void setNumeroTurmas(Integer numeroTurmas) {
        this.numeroTurmas = numeroTurmas;
    }

    public ArrayList<Atendimento> getHistorioDeAtendimentos() {
        return historioDeAtendimentos;
    }

    public void setHistorioDeAtendimentos(ArrayList<Atendimento> historioDeAtendimentos) {
        this.historioDeAtendimentos = historioDeAtendimentos;
    }

    public SeparateChainingHashST<String, Turma> getListaTurmasDarAulas() {
        return listaTurmasDarAulas;
    }

    public void setListaTurmasDarAulas(SeparateChainingHashST<String, Turma> listaTurmasDarAulas) {
        this.listaTurmasDarAulas = listaTurmasDarAulas;
    }

    public ArrayList<Atendimento> getListaAtendimentosAlunos() {
        return listaAtendimentosAlunos;
    }

    public void setListaAtendimentosAlunos(ArrayList<Atendimento> listaAtendimentosAlunos) {
        this.listaAtendimentosAlunos = listaAtendimentosAlunos;
    }

    public ArrayList<Horario> getHorariosDasAulas() {
        return horariosDasAulas;
    }

    public void setHorariosDasAulas(ArrayList<Horario> horariosDasAulas) {
        this.horariosDasAulas = horariosDasAulas;
    }

    /**
     * Se quiser apagar todos os atendimentos e guardar num historico
     */
    public void resetTotalHorarios(){
        historioDeAtendimentos.addAll(listaAtendimentosAlunos);
        for(Atendimento a : listaAtendimentosAlunos){
            historioDeAtendimentos.add(a);
            listaAtendimentosAlunos.remove(a);
        }
    }

    /**
     * Se quiser limpar a disponibilidade de um atendimento
     */
    public void resetAlunosHorarios(){
        for(Atendimento a : listaAtendimentosAlunos){
            a.setAluno(null);
        }
    }

    /**
     * Cria um novo horario de atendimento
     * @param horario é composto pela sala e hora do atendimento
     */
    public void adicionarHorarioAtendimento(Horario horario){
        Atendimento novo = new Atendimento(this,horario);
        if(!listaAtendimentosAlunos.contains(novo)){
            listaAtendimentosAlunos.add(novo);
            return;
        }
        System.out.println("Ja tem um atendimento marcado nesse horario");
    }

    /**
     * Imprime todos os atendimentos
     */
    public void imprimeAtendimento(){
        for(Atendimento a : listaAtendimentosAlunos){
            System.out.println(a);
        }
    }

    /**
     * Remove um atendimento da lista
     * @param atendimento do professor
     */
    public void removerAtendimento(Atendimento atendimento){
        Iterator<Atendimento> i = this.listaAtendimentosAlunos.iterator();
        while (i.hasNext()) {
            Atendimento atendimento1 = (Atendimento) i.next();
            if (atendimento1.getHorario().compareTo(atendimento.getHorario())==0) {
                historioDeAtendimentos.add(atendimento);
                i.remove();
            }
        }
    }

    public void imprimeTurmas(){
        for(String turmas : listaTurmasDarAulas.keys()){
            System.out.println(listaTurmasDarAulas.get(turmas).getUnidadeCurricular());
        }
    }

    /**
     * Adiciona turma no professor
     * @param turma adicionar
     */
    public void adicionaTurma(Turma turma){
        if(!listaTurmasDarAulas.contains(turma.getId())){
            listaTurmasDarAulas.put(turma.getId(),turma);
            numeroTurmas++;
        }
    }

    public void removerTurma(Turma turma){
        if(listaTurmasDarAulas.contains(turma.getId())){
            listaTurmasDarAulas.delete(turma.getId());
            numeroTurmas--;
        }
    }
}
