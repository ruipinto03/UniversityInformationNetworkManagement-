package API;

import algoritmos.RedBlackBST;
import algoritmos.RedBlackBST_AED2;
import algoritmos.SeparateChainingHashST;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Pesquisas implements Serializable {

    /**
     * Pesquisa se existe uma turma num determinado curso
     *
     * @param curso lista de todos os cursos
     * @param turma turma a pesquisar
     * @return um arraylist de todos os cursos com esta turma
     */
    public static ArrayList<Curso> pesquisaCadeiraCurso(SeparateChainingHashST<String, Curso> curso, Turma turma) {
        ArrayList<Curso> cursos = new ArrayList<>();
        for (String s : curso.keys()) {
            if (curso.get(s).getListaTurmas().contains(turma.getId())) {
                cursos.add(curso.get(s));
            }
        }
        return cursos;
    }

    /**
     * R8 a) -  Todas as salas livres num determinado horario
     *
     * @param dia        a pesquisar
     * @param horaInicio a pesquisar
     * @param baseDados  local onde tem todos os dados armazenados
     */
    public static RedBlackBST_AED2<Integer, Sala> salasLivresHorario(Integer dia, Integer horaInicio, BaseDados baseDados) {

        RedBlackBST_AED2<Integer, Sala> aux = baseDados.getSalas();
        //  int x = aux.size(),z=0;
        // System.out.println(aux.size());
        for (String turmas : baseDados.getTurmas().keys()) {
            for (Horario horarios : baseDados.getTurmas().get(turmas).getHorarios()) {
                if (horarios.getDia() == DayOfWeek.of(dia) && horaInicio >= horarios.getHoraInicio() && horaInicio <= horarios.getHoraFim()) {
                    aux.delete(horarios.getSala().getId());
                    //    z++;
                }
            }
        }
        /*System.out.println(z);
        System.out.println(x-aux.size());*/
        if (aux.size() == 0) {
            System.out.println("Vazia a lista tudo ocupado");
        } else {
            for (Integer i : aux.keys()) {
                System.out.println(aux.get(i).toStringCompleto());
            }
        }
        return aux;
    }

    /**
     * R8 b) Todos os professores de uma unidade curricular
     *
     * @param pesquisa  turma a pesquisar
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static ArrayList<Turma> professoresTurmaSub(String pesquisa, BaseDados baseDados) {
        ArrayList<Turma> aux = new ArrayList<>();
        int i = 1;
        for (String turmas : baseDados.getTurmas().keys()) {
            if (baseDados.getTurmas().get(turmas).getUnidadeCurricular().getNome().contains(pesquisa.toUpperCase())) {
                aux.add(baseDados.getTurmas().get(turmas));
                i++;
            }
        }
        if (aux.isEmpty()) {
            System.out.println("Lista vazia");
            return null;
        }
        for (Turma turma : aux) {
            System.out.println(i + " - " + turma.getProfessor().getNome() + " " + turma.getUnidadeCurricular().getNome());
            //   i++;
        }
        return aux;
    }

    /**
     * R8 b) Todos os professores de uma unidade curricular
     *
     * @param pesquisa  turma a pesquisar
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static void professoresTurma(String pesquisa, BaseDados baseDados) {
        ArrayList<Turma> aux = new ArrayList<>();
        int i = 1;
        for (String turmas : baseDados.getTurmas().keys()) {
            if (baseDados.getTurmas().get(turmas).getUnidadeCurricular().getNome().compareTo(pesquisa.toUpperCase()) == 0) {
                aux.add(baseDados.getTurmas().get(turmas));
                i++;
            }
        }
        if (aux.isEmpty()) {
            System.out.println("Lista vazia");
            return;
        }
        for (Turma turma : aux) {
            System.out.println(i + " - " + turma.getProfessor().getNome() + " " + turma.getUnidadeCurricular().getNome());
            //   i++;
        }
    }

    /**
     * R8 c) Todas as turmas de um professor
     *
     * @param id        professor a pesquisar
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static ArrayList<UnidadeCurricular> todasTurmasProfessor(Integer id, BaseDados baseDados) {
        ArrayList<UnidadeCurricular> aux = new ArrayList<>();
        if (baseDados.getProfessores().get(id) != null) {
            //  System.out.println(baseDados.getProfessores().get(id).getListaTurmasDarAulas().size());
            baseDados.getProfessores().get(id).imprimeTurmas();
            for (String t : baseDados.getProfessores().get(id).getListaTurmasDarAulas().keys()) {
                aux.add(baseDados.getProfessores().get(id).getListaTurmasDarAulas().get(t).getUnidadeCurricular());
            }
        } else {
            System.out.println("Professor nao encontrado");
        }
        return aux;
    }

    /**
     * R8 d) Todos os horários disponíveis para marcação de atendimento (cruzar informação
     * de horário disponível do aluno com o horário de atendimento do professor)
     *
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static void alunosDisponiveisParaAtendimento(BaseDados baseDados) {
        int verificacao = 0;
        for (Integer professor : baseDados.getProfessores().keys()) {
            if (!baseDados.getProfessores().get(professor).getListaAtendimentosAlunos().isEmpty()) {
                for (Atendimento atendimento : baseDados.getProfessores().get(professor).getListaAtendimentosAlunos()) {
                    for (Integer alunos : baseDados.getAlunos().keys()) {
                        for (Horario alunosHorario : baseDados.getAlunos().get(alunos).getHorarioAulas()) {
                            if (atendimento.getHorario().compareTo(alunosHorario) == 0) {
                                verificacao = 1;
                            }
                        }
                        if (verificacao == 0) {
                            System.out.println(baseDados.getAlunos().get(alunos).getNome() + " " + atendimento);
                        } else {
                            verificacao = 0;
                        }
                    }
                }
            }
        }
    }

    public static ArrayList<Atendimento> alunosDisponiveisParaAtendimento(Integer id, BaseDados baseDados) {
        int verificacao = 0;
        ArrayList<Atendimento> aux = new ArrayList<>();
        for (Integer professor : baseDados.getProfessores().keys()) {
            if (!baseDados.getProfessores().get(professor).getListaAtendimentosAlunos().isEmpty()) {
                for (Atendimento atendimento : baseDados.getProfessores().get(professor).getListaAtendimentosAlunos()) {
                    for (Horario alunosHorario : baseDados.getAlunos().get(id).getHorarioAulas()) {
                        if (atendimento.getHorario().compareTo(alunosHorario) == 0) {
                            verificacao = 1;
                        }
                    }
                    if (verificacao == 0) {
                        System.out.println(baseDados.getAlunos().get(id).getNome() + " " + atendimento);
                        if (atendimento.getAluno() == null)
                            aux.add(atendimento);

                    } else {
                        verificacao = 0;
                    }
                }
            }
        }
        return aux;
    }

    /**
     * R8 e) A ocupação de uma sala entre datas
     *
     * @param pesquisaMin valor minimo
     * @param pesquisaMax valor maximo
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static RedBlackBST_AED2<Integer, Sala> ocupacaoSalasEntreDatas(Integer pesquisaMin, Integer pesquisaMax, BaseDados baseDados) {
        RedBlackBST_AED2<Integer, Sala> aux = new RedBlackBST_AED2<>();
        for (String turmas : baseDados.getTurmas().keys()) {
            for (Horario horario : baseDados.getTurmas().get(turmas).getHorarios()) {
                if (pesquisaMin >= horario.getHoraInicio() && horario.getHoraFim() <= pesquisaMax) {
                    System.out.println(baseDados.getTurmas().get(turmas).getId() + " ->>> " + horario);
                    aux.put(horario.getSala().getId(), horario.getSala());
                }
            }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios número de tomadas
     *
     * @param pesquisaMin valor minimo de lotacao
     * @param pesquisaMax valor maximo
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasTomadasEntreValores(Integer pesquisaMin, Integer pesquisaMax, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if (pesquisaMin >= baseDados.getSalas().get(salas).getTomadas() &&  baseDados.getSalas().get(salas).getTomadas()<= pesquisaMax) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }
    public static ArrayList<Sala> salasLotacaoEntreValores(Integer pesquisaMin, Integer pesquisaMax, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if (baseDados.getSalas().get(salas).getLocatacao() >= pesquisaMin &&  baseDados.getSalas().get(salas).getLocatacao()<= pesquisaMax) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }
    public static ArrayList<Sala> salasLotacaoMin(Integer pesquisaMin, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if (baseDados.getSalas().get(salas).getLocatacao() >= pesquisaMin) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }
    public static ArrayList<Sala> salasLotacaoMax(Integer pesquisaMax, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if ( baseDados.getSalas().get(salas).getLocatacao()<=pesquisaMax) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios número de tomadas
     *
     * @param pesquisaMin valor minimo de lotacao
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasTomadasSuperiorValor(Integer pesquisaMin, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();

        for (Integer salas : baseDados.getSalas().keys()) {
            if (pesquisaMin >= baseDados.getSalas().get(salas).getLocatacao()) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios número de tomadas
     *
     * @param pesquisaMax valor maximo de lotacao
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasTomadasInferiorValor(Integer pesquisaMax, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if (pesquisaMax <= baseDados.getSalas().get(salas).getLocatacao()) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios ocupação
     *
     * @param pesquisaMin valor minimo de lotacao
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasOcupacaoSuperiorValor(Integer pesquisaMin, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();

        for (String turmas : baseDados.getTurmas().keys()) {
            for (Horario horario : baseDados.getTurmas().get(turmas).getHorarios())
                if (((baseDados.getTurmas().get(turmas).getListaAlunosInscritos().size() * 100) / horario.getSala().getLocatacao()) >= pesquisaMin) {
                    System.out.println(baseDados.getTurmas().get(turmas).getUnidadeCurricular() + " " + horario);
                    aux.add(horario.getSala());
                }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios ocupação
     *
     * @param pesquisaMax valor maximo de lotacao
     * @param baseDados   local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasOcupacaoInferiorValor(Integer pesquisaMax, BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (String turmas : baseDados.getTurmas().keys()) {
            for (Horario horario : baseDados.getTurmas().get(turmas).getHorarios())
                if (((baseDados.getTurmas().get(turmas).getListaAlunosInscritos().size() * 100) / horario.getSala().getLocatacao()) <= pesquisaMax) {
                    System.out.println(baseDados.getTurmas().get(turmas).getUnidadeCurricular() + " " + horario);
                    aux.add(horario.getSala());
                }
        }
        return aux;
    }

    /**
     * R8 f) Pesquisas de salas por diferentes critérios projector
     *
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static ArrayList<Sala> salasComProjetor(BaseDados baseDados) {
        ArrayList<Sala> aux = new ArrayList<>();
        for (Integer salas : baseDados.getSalas().keys()) {
            if (baseDados.getSalas().get(salas).getProjector()) {
                System.out.println(baseDados.getSalas().get(salas));
                aux.add(baseDados.getSalas().get(salas));
            }
        }
        return aux;
    }

    /**
     * R9. Deverão considerar definir um horário para cada entidade do sistema (e.g.
     * professores, alunos, etc.). Recorrendo a todas os horários, o sistema deverá construir
     * uma visão global do funcionamento da instituição. Por exemplo, o método now()
     * deverá apresentar o estado da instituição naquele instante: ocupação de salas, UCs a
     * serem lecionadas, professores disponíveis/ocupados, etc.
     *
     * @param baseDados local onde tem todos os dados armazenados
     */
    public static void now(BaseDados baseDados) {
        Calendar gregCalendar = new GregorianCalendar();
        DayOfWeek dia = DayOfWeek.of(gregCalendar.get(Calendar.DAY_OF_WEEK));
        int hora = gregCalendar.get(Calendar.HOUR);

        RedBlackBST_AED2<Integer, Aluno> alunosLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Aluno> alunosOcupados = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Professor> professoresOcupados = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Professor> professoresLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Sala> salasLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Sala> salasOcupadas = new RedBlackBST_AED2<>();
        SeparateChainingHashST<String, Curso> cursos = new SeparateChainingHashST<>();
        SeparateChainingHashST<String, Turma> turmas = new SeparateChainingHashST<>();
        int x = 0;
        for (String turma : baseDados.getTurmas().keys()) {
            for (Horario h : baseDados.getTurmas().get(turma).getHorarios()) {
                if (dia == h.getDia() && hora >= h.getHoraInicio() && hora <= h.getMinutoFim()) {
                    for (Integer i : baseDados.getTurmas().get(turma).getListaAlunosInscritos().keys()) {
                        if (x == 0) {
                            cursos.put(baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso().getId(), baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso());
                            x += 1;
                        }
                        alunosOcupados.put(baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getId(), baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i));
                    }
                    professoresOcupados.put(baseDados.getTurmas().get(turma).getProfessor().getId(), baseDados.getTurmas().get(turma).getProfessor());
                    salasOcupadas.put(h.getSala().getId(), h.getSala());
                    turmas.put(baseDados.getTurmas().get(turma).getId(), baseDados.getTurmas().get(turma));

                }
                x = 0;
            }
        }
        for (Integer alunos : baseDados.getAlunos().keys()) {
            if (!alunosOcupados.contains(baseDados.getAlunos().get(alunos).getId())) {
                alunosLivres.put(baseDados.getAlunos().get(alunos).getId(), baseDados.getAlunos().get(alunos));
            }
        }
        for (Integer professor : baseDados.getProfessores().keys()) {
            if (!professoresOcupados.contains(baseDados.getProfessores().get(professor).getId())) {
                professoresLivres.put(baseDados.getProfessores().get(professor).getId(), baseDados.getProfessores().get(professor));
            }
        }
        for (Integer sala : baseDados.getSalas().keys()) {
            if (!salasOcupadas.contains(baseDados.getSalas().get(sala).getId())) {
                salasLivres.put(baseDados.getSalas().get(sala).getId(), baseDados.getSalas().get(sala));
            }
        }
        System.out.println("LISTA DE TUDO QUE ESTA A FUNCiONAR NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        for (Integer aux : alunosOcupados.keys()) {
            System.out.println(alunosOcupados.get(aux));
        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        for (Integer aux : professoresOcupados.keys()) {
            System.out.println(professoresOcupados.get(aux));
        }
        System.out.println("\t SALAS OCUPADAS");
        for (Integer aux : salasOcupadas.keys()) {
            System.out.println(salasOcupadas.get(aux));
        }
        System.out.println("\t TURMAS OCUPADAS E DISCIPLINA");
        for (String aux : turmas.keys()) {
            System.out.println(turmas.get(aux).getId() + " " + turmas.get(aux).getUnidadeCurricular().getNome());
        }
        System.out.println("\t CURSOS OCUPADAS");
        for (String aux : cursos.keys()) {
            System.out.println(cursos.get(aux));
        }

        System.out.println("LISTA DE TUDO QUE ESTA A LIVRES NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        for (Integer aux : alunosLivres.keys()) {
            System.out.println(alunosLivres.get(aux));
        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        for (Integer aux : professoresLivres.keys()) {
            System.out.println(professoresLivres.get(aux));
        }
        System.out.println("\t SALAS OCUPADAS");
        for (Integer aux : salasLivres.keys()) {
            System.out.println(salasLivres.get(aux));
        }

    }

    /**
     * R9. Deverão considerar definir um horário para cada entidade do sistema (e.g.
     * professores, alunos, etc.). Recorrendo a todas os horários, o sistema deverá construir
     * uma visão global do funcionamento da instituição. Por exemplo, o método now()
     * deverá apresentar o estado da instituição naquele instante: ocupação de salas, UCs a
     * serem lecionadas, professores disponíveis/ocupados, etc.
     *
     * @param baseDados local onde tem todos os dados armazenados
     * @param dia       da semana
     * @param hora      atual
     */
    public static void now(BaseDados baseDados, Integer dia, Integer hora) {
        DayOfWeek pesquisaDia = DayOfWeek.of(dia);
        RedBlackBST_AED2<Integer, Aluno> alunosLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Aluno> alunosOcupados = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Professor> professoresOcupados = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Professor> professoresLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Sala> salasLivres = new RedBlackBST_AED2<>();
        RedBlackBST_AED2<Integer, Sala> salasOcupadas = new RedBlackBST_AED2<>();
        SeparateChainingHashST<String, Curso> cursos = new SeparateChainingHashST<>();
        SeparateChainingHashST<String, Turma> turmas = new SeparateChainingHashST<>();
        int x = 0;
        for (String turma : baseDados.getTurmas().keys()) {
            for (Horario h : baseDados.getTurmas().get(turma).getHorarios()) {
                if (pesquisaDia == h.getDia() && hora >= h.getHoraInicio() && hora <= h.getMinutoFim()) {
                    for (Integer i : baseDados.getTurmas().get(turma).getListaAlunosInscritos().keys()) {
                        if (x == 0) {
                            cursos.put(baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso().getId(), baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso());
                            x += 1;
                        }
                        alunosOcupados.put(baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i).getId(), baseDados.getTurmas().get(turma).getListaAlunosInscritos().get(i));
                    }
                    professoresOcupados.put(baseDados.getTurmas().get(turma).getProfessor().getId(), baseDados.getTurmas().get(turma).getProfessor());
                    salasOcupadas.put(h.getSala().getId(), h.getSala());
                    turmas.put(baseDados.getTurmas().get(turma).getId(), baseDados.getTurmas().get(turma));

                }
                x = 0;
            }
        }
        for (Integer alunos : baseDados.getAlunos().keys()) {
            if (!alunosOcupados.contains(baseDados.getAlunos().get(alunos).getId())) {
                alunosLivres.put(baseDados.getAlunos().get(alunos).getId(), baseDados.getAlunos().get(alunos));
            }
        }
        for (Integer professor : baseDados.getProfessores().keys()) {
            if (!professoresOcupados.contains(baseDados.getProfessores().get(professor).getId())) {
                professoresLivres.put(baseDados.getProfessores().get(professor).getId(), baseDados.getProfessores().get(professor));
            }
        }
        for (Integer sala : baseDados.getSalas().keys()) {
            if (!salasOcupadas.contains(baseDados.getSalas().get(sala).getId())) {
                salasLivres.put(baseDados.getSalas().get(sala).getId(), baseDados.getSalas().get(sala));
            }
        }
        System.out.println("LISTA DE TUDO QUE ESTA A FUNCiONAR NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        for (Integer aux : alunosOcupados.keys()) {
            System.out.println(alunosOcupados.get(aux));
        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        for (Integer aux : professoresOcupados.keys()) {
            System.out.println(professoresOcupados.get(aux));
        }
        System.out.println("\t SALAS OCUPADAS");
        for (Integer aux : salasOcupadas.keys()) {
            System.out.println(salasOcupadas.get(aux));
        }
        System.out.println("\t TURMAS OCUPADAS E DISCIPLINA");
        for (String aux : turmas.keys()) {
            System.out.println(turmas.get(aux).getId() + " " + turmas.get(aux).getUnidadeCurricular().getNome());
        }
        System.out.println("\t CURSOS OCUPADAS");
        for (String aux : cursos.keys()) {
            System.out.println(cursos.get(aux));
        }

        System.out.println("LISTA DE TUDO QUE ESTA A LIVRES NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        for (Integer aux : alunosLivres.keys()) {
            System.out.println(alunosLivres.get(aux));
        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        for (Integer aux : professoresLivres.keys()) {
            System.out.println(professoresLivres.get(aux));
        }
        System.out.println("\t SALAS OCUPADAS");
        for (Integer aux : salasLivres.keys()) {
            System.out.println(salasLivres.get(aux));
        }
    }

}
