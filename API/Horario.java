package API;

import java.io.Serializable;
import java.time.DayOfWeek;

public class Horario implements Serializable,Comparable<Horario>, GravarFicheiro {
    private Sala sala;
    private Integer horaInicio;
    private Integer horaFim;
    private Integer minutoInicio;
    private Integer minutoFim;
    private DayOfWeek dia;
    private Turma turma;

    public Horario(Sala sala, Integer horaInicio, Integer horaFim, Integer minutoInicio, Integer minutoFim, Integer dia) {
        this.sala = sala;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.minutoInicio = minutoInicio;
        this.minutoFim = minutoFim;
        this.dia = DayOfWeek.of(dia);
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Horario(Sala sala, Integer horaInicio, Integer horaFim, Integer minutoInicio, Integer minutoFim, Integer dia, Turma turma) {
        this.sala = sala;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.minutoInicio = minutoInicio;
        this.minutoFim = minutoFim;
        this.dia = DayOfWeek.of(dia);
        this.turma=turma;
    }

    public Horario(Sala sala, Integer horaInicio, Integer horaFim, Integer dia) {
        this.sala = sala;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.minutoInicio = 0;
        this.minutoFim = 0;
        this.dia = DayOfWeek.of(dia);
    }

    public Horario(Sala sala, Integer horaInicio, Integer dia) {
        this.sala = sala;
        this.horaInicio = horaInicio;
        this.horaFim = horaInicio + 2;
        this.minutoInicio = 0;
        this.minutoFim = 0;
        this.dia = DayOfWeek.of(dia);
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Integer getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Integer horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Integer horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getMinutoInicio() {
        return minutoInicio;
    }

    public void setMinutoInicio(Integer minutoInicio) {
        this.minutoInicio = minutoInicio;
    }

    public Integer getMinutoFim() {
        return minutoFim;
    }

    public void setMinutoFim(Integer minutoFim) {
        this.minutoFim = minutoFim;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }
    public String preencherDiaSemanaPT(DayOfWeek dia) {
        switch (dia.getValue()) {
            case 1: {
                return "Segunda-Feira";
            }
            case 2: {
                return "Terça-Feira";
            }
            case 3: {
                return "Quarta-Feira";
            }
            case 4: {
                return "Quinta-Feira";
            }
            case 5: {
                return "Sexta-Feira";
            }
        }
        return "";
    }
    @Override
    public String toString() {
        return "Horario: \tsala " + sala.getId() +" na "+ preencherDiaSemanaPT(dia) + " das " + horaInicio +
                ":" +  minutoInicio +
                " ate " + horaFim +
                ":" + minutoFim;

    }

    @Override
    public int compareTo(Horario o) {
        if (o.getSala().getId().equals(this.sala.getId()) && this.dia == o.getDia()) {
            if (o.getHoraFim() <= this.horaInicio && o.getHoraInicio() <= this.horaInicio) {
                return -1;
            } else if (o.getHoraInicio() >= this.horaFim && o.getHoraFim() >= this.horaFim) {
                return 1;
            } else if (o.getHoraInicio() == this.horaInicio && o.getHoraFim() == this.horaFim) {
                return 0;
            } else if (o.getHoraInicio() > this.horaInicio && o.getHoraFim() < this.horaFim) {
                return 0;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Sala um = new Sala(1, 100, true, 1);
        Sala dois = new Sala(2, 100, 50, true, 1);
        Horario primeiro = new Horario(um, 12, 1);
        Horario segundo = new Horario(um, 12, 1);
        Horario terceiro = new Horario(um, 14, 1);
        Horario quarto = new Horario(um, 8, 1);
        Horario quinto = new Horario(um, 12, 2);
        Horario sexto = new Horario(dois, 12, 1);

        System.out.println(primeiro.compareTo(segundo));
        System.out.println(primeiro.compareTo(terceiro));
        System.out.println(primeiro.compareTo(quarto));
        System.out.println(primeiro.compareTo(quinto));
        System.out.println(primeiro.compareTo(sexto));
    }

    @Override
    public String gravar() {
        return sala.getId() + ";" + horaInicio + ";" +minutoInicio  + ";" + horaFim + ";" + minutoFim + ";" + dia;
    }
}
