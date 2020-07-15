package APIGRAFICA;

import API.*;
import API.Date;
import APIGRAFOS.*;
import algoritmos.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.DayOfWeek;
import java.util.*;

import static API.Pesquisas.*;

public class GraficaControlador implements Initializable {
    BaseDados bd = new BaseDados(false);
    GrafoUniversidadeV2 grafoUniversidade = new GrafoUniversidadeV2();
    ArrayList<Turma> auxiliar;
    ArrayList<Ponto3D> auxiliarGrafoSet;
    private double radius = 10;
    /*TAB CURSO*/

    @FXML // fx:id="tASeleccaoCursoTabCurso"
    private TextArea tASeleccaoCursoTabCurso; // Value injected by FXMLLoader

    @FXML // fx:id="tfNomeCurso"
    private TextField tfNomeCurso; // Value injected by FXMLLoader

    @FXML // fx:id="tfIdCurso"
    private TextField tfIdCurso; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoCursoTabCurso"
    private ComboBox<String> cbSeleccaoCursoTabCurso; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabCurso"
    private TableView<Curso> tVTabCurso; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabCursoID"
    private TableColumn<Curso, String> tVTabCursoID; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabCursoNome"
    private TableColumn<Curso, String> tVTabCursoNome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabCursoNrUc"
    private TableColumn<Curso, Integer> tVTabCursoNrUc; // Value injected by FXMLLoader

    @FXML
    void handleApresentaDadosTACurso(ActionEvent event) {

        if (cbSeleccaoCursoTabCurso.getValue() != null) {
            if (bd.getCursos().get(cbSeleccaoCursoTabCurso.getValue()) != null) {
                Curso aux = bd.getCursos().get(cbSeleccaoCursoTabCurso.getValue());
                tASeleccaoCursoTabCurso.setWrapText(true);
                tASeleccaoCursoTabCurso.setText(aux.toString());
            }
        } else {
            tASeleccaoCursoTabCurso.clear();
        }
    }

    @FXML
    void handlerAdicionarCurso(ActionEvent event) {

        if (tfIdCurso.getText().length() > 0 && tfNomeCurso.getText().length() > 0) {
            tfNomeCurso.getText();
            String idCurso = tfIdCurso.getText().toUpperCase();
            String nomeCurso = tfNomeCurso.getText().toUpperCase();
            if (!bd.getCursos().contains(idCurso)) {
                Curso novo = new Curso(idCurso, nomeCurso);
                bd.adicionarCursos(novo);
                bd.gravarCursos();
                apresentaDadosNaTvCurso();
                apresentaDadosNaCBCurso();
            }
        }
        tfNomeCurso.clear();
        tfIdCurso.clear();
    }

    @FXML
    void handleRemoverCurso(ActionEvent event) {
        String idCurso = cbSeleccaoCursoTabCurso.getValue();
        if (bd.getCursos().contains(idCurso)) {
            Curso aux = bd.getCursos().get(idCurso);
            bd.deleteCursos(aux.getId());
            bd.getHistoricoCursos().put(aux.getId(), aux);
            bd.gravarHistoricoCursos();
        }
        apresentaDadosNaTvCurso();
        apresentaDadosNaCBCurso();
    }

    void apresentaDadosNaTvCurso() {
        tVTabCurso.getItems().clear();
        if (bd.getCursos().size() != 0) {
            tVTabCurso.getItems().clear();
            Integer size = 0;
            for (String s : bd.getCursos().keys()) {
                tVTabCurso.getItems().add(bd.getCursos().get(s));
            }
        }

    }

    void apresentaDadosNaCBCurso() {
        cbSeleccaoCursoTabCurso.getItems().clear();
        if (bd.getCursos().size() != 0) {
            for (String s : bd.getCursos().keys()) {
                cbSeleccaoCursoTabCurso.getItems().add(bd.getCursos().get(s).getId());
            }
        }

    }

    @FXML
    void handleApresentaDadosCBCurso(Event event) {
        apresentaDadosNaCBCurso();
        apresentaDadosNaTvCurso();
    }

    /**
     * UNIDADE CURRICULAR
     */
    @FXML // fx:id="tfNomeUcUC"
    private TextField tfNomeUcUC; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoAnoTabUC"
    private ComboBox<Integer> cbSeleccaoAnoTabUC; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoSemestreTabUC"
    private ComboBox<Integer> cbSeleccaoSemestreTabUC; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcUC"
    private TableView<UnidadeCurricular> tVTabUcUC; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcUCnome"
    private TableColumn<UnidadeCurricular, String> tVTabUcUCnome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcUCnome"
    private TableColumn<UnidadeCurricular, Integer> tVTabUcUCano; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcUCnome"
    private TableColumn<UnidadeCurricular, Integer> tVTabUcUCsemestre; // Value injected by FXMLLoader

    @FXML
    void handlerAdicionarUcUC(ActionEvent event) {
        if (tfNomeUcUC.getText().length() > 0 && cbSeleccaoSemestreTabUC.getValue() != null && cbSeleccaoAnoTabUC.getValue() != null) {
            String idCurso = tfNomeUcUC.getText().toUpperCase();
            Integer ano = cbSeleccaoSemestreTabUC.getValue();
            Integer semestre = cbSeleccaoAnoTabUC.getValue();
            if (!bd.getUnidadesCurriculares().contains(idCurso)) {
                UnidadeCurricular novo = new UnidadeCurricular(idCurso, ano, semestre);
                bd.adicionarUc(novo);
                bd.gravarUc();
                apresentaDadosNaTvUC();
                apresentaDadosNaCBUC();
            }
        }
        tfNomeUcUC.clear();
    }

    @FXML
    void handleRemoverUcUC(ActionEvent event) {
        String idCurso = tVTabUcUC.getSelectionModel().getSelectedItem().getNome();
        if (bd.getUnidadesCurriculares().contains(idCurso)) {
            UnidadeCurricular aux = bd.getUnidadesCurriculares().get(idCurso);
            bd.deleteUc(aux);
            bd.getHistoricoUnidadesCurriculares().put(aux.getNome(), aux);
            bd.gravarHistoricoUc();
        }
        apresentaDadosNaTvUC();
        apresentaDadosNaCBUC();
    }

    @FXML
    void handleApresentaDadosCBUC(Event event) {
        apresentaDadosNaTvUC();
        apresentaDadosNaCBUC();
    }

    void apresentaDadosNaTvUC() {
        tVTabUcUC.getItems().clear();
        if (bd.getUnidadesCurriculares().size() != 0) {
            tVTabUcUC.getItems().clear();
            for (String s : bd.getUnidadesCurriculares().keys()) {
                tVTabUcUC.getItems().add(bd.getUnidadesCurriculares().get(s));
            }
        }
    }

    void apresentaDadosNaCBUC() {
        cbSeleccaoAnoTabUC.getItems().clear();
        cbSeleccaoSemestreTabUC.getItems().clear();
        cbSeleccaoAnoTabUC.getItems().addAll(1, 2, 3, 4, 5);
        cbSeleccaoSemestreTabUC.getItems().addAll(1, 2);
    }

    /**
     * Turmas
     */

    @FXML // fx:id="tASeleccaoCursoTabTurma"
    private Tab tASeleccaoCursoTabTurma; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoTurmaTabTurma"
    private ComboBox<UnidadeCurricular> cbSeleccaoUCTabTurma; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoProfessorTabTurma"
    private ComboBox<Professor> cbSeleccaoProfessorTabTurma; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoCursoTabTurma"
    private ComboBox<Curso> cbSeleccaoCursoTabTurma; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcTurmas"
    private TableView<UnidadeCurricular> tVTabUcTurmas; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcTurmaNome"
    private TableColumn<UnidadeCurricular, String> tVTabUcTurmaNome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcTurmaAno"
    private TableColumn<UnidadeCurricular, Integer> tVTabUcTurmaAno; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabUcTurmaSemestre"
    private TableColumn<UnidadeCurricular, Integer> tVTabUcTurmaSemestre; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabTurmasTurma"
    private TableView<Turma> tVTabTurmasTurma; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabTurmasTurmaId"
    private TableColumn<Turma, String> tVTabTurmasTurmaId; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabTurmasTurmaNome"
    private TableColumn<Turma, String> tVTabTurmasTurmaNome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabTurmasTurmaProfessor"
    private TableColumn<Turma, String> tVTabTurmasTurmaProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tASeleccaoCursoTabTurmas"
    private TextArea tASeleccaoCursoTabTurmas; // Value injected by FXMLLoader

    @FXML
    void handleApresentaDadosCBTurma(Event event) {
        apresentaDadosNaTvTurmas();
        apresentaDadosNaCBTurmas();
    }

    @FXML
    void handleRemoverTurma(ActionEvent event) {
        if (tVTabTurmasTurma.getSelectionModel().getSelectedItem() != null) {
            String idCurso = tVTabTurmasTurma.getSelectionModel().getSelectedItem().getId();
            if (bd.getTurmas().contains(idCurso)) {
                Turma aux = bd.getTurmas().get(idCurso);
                bd.deleteTurmas(aux);
                bd.getHistoricoTurmas().put(aux.getId(), aux);
                bd.gravarHistoricoTurmas();
            }
            apresentaDadosNaTvTurmas();
            apresentaDadosNaCBTurmas();
        }
    }

    @FXML
    void handlerAdicionarTurma(ActionEvent event) {

        if (cbSeleccaoUCTabTurma.getValue() != null && cbSeleccaoProfessorTabTurma.getValue() != null && cbSeleccaoCursoTabTurma.getValue() != null) {
            String idTurma = bd.verificaCodigoTurma(bd.gerarString());
            UnidadeCurricular unidadeCurricular = cbSeleccaoUCTabTurma.getValue();
            Professor professor = cbSeleccaoProfessorTabTurma.getValue();
            Curso curso = cbSeleccaoCursoTabTurma.getValue();
            if (!bd.getTurmas().contains(idTurma)) {
                Turma novo = new Turma(idTurma, unidadeCurricular, professor);
                curso.adicionaTurma(novo);
                professor.adicionaTurma(novo);
                bd.adicionarTurmas(novo);
                bd.gravarTurmas();

                apresentaDadosNaTvUC();
                apresentaDadosNaCBUC();
            }
        }
        apresentaDadosNaTvTurmas();
        apresentaDadosNaCBTurmas();
        tASeleccaoCursoTabTurmas.clear();
    }


    public void handleApresentaDadosTaTurmas(ActionEvent actionEvent) {
        tASeleccaoCursoTabTurmas.clear();
        tASeleccaoCursoTabTurmas.setWrapText(true);
        tASeleccaoCursoTabTurmas.setText(cbSeleccaoUCTabTurma.getValue() + "\n" + cbSeleccaoProfessorTabTurma.getValue() + "\n" + cbSeleccaoCursoTabTurma.getValue());
    }

    void apresentaDadosNaTvTurmas() {
        tVTabUcTurmas.getItems().clear();
        tVTabTurmasTurma.getItems().clear();

        if (bd.getUnidadesCurriculares().size() > 0) {
            tVTabUcTurmas.getItems().clear();
            for (String s : bd.getUnidadesCurriculares().keys()) {
                tVTabUcTurmas.getItems().add(bd.getUnidadesCurriculares().get(s));
            }
        }

        if (bd.getTurmas().size() > 0 && bd.getProfessores().size() > 0) {
            tVTabTurmasTurma.getItems().clear();
            for (String s : bd.getTurmas().keys()) {
                tVTabTurmasTurma.getItems().add(bd.getTurmas().get(s));
            }
        }

    }

    void apresentaDadosNaCBTurmas() {
        cbSeleccaoUCTabTurma.getItems().clear();
        cbSeleccaoProfessorTabTurma.getItems().clear();
        cbSeleccaoCursoTabTurma.getItems().clear();


        if (bd.getUnidadesCurriculares().size() > 0) {
            for (String s : bd.getUnidadesCurriculares().keys()) {
                cbSeleccaoUCTabTurma.getItems().add(bd.getUnidadesCurriculares().get(s));
            }
        }
        if (bd.getProfessores().size() > 0) {
            for (Integer s : bd.getProfessores().keys()) {
                cbSeleccaoProfessorTabTurma.getItems().add(bd.getProfessores().get(s));
            }
        }
        if (bd.getCursos().size() > 0) {
            for (String s : bd.getCursos().keys()) {
                cbSeleccaoCursoTabTurma.getItems().add(bd.getCursos().get(s));
            }
        }

    }

    /**
     * PROFESSOR
     */


    @FXML // fx:id="tVTabProfessor"
    private TableView<Professor> tVTabProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabProfessorId"
    private TableColumn<Professor, Integer> tVTabProfessorId; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabProfessorNome"
    private TableColumn<Professor, String> tVTabProfessorNome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabProfessorDataNascimento"
    private TableColumn<Professor, Date> tVTabProfessorDataNascimento; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabProfessorEmail"
    private TableColumn<Professor, String> tVTabProfessorEmail; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabProfessorNumeroTurmas"
    private TableColumn<?, ?> tVTabProfessorNumeroTurmas; // Value injected by FXMLLoader

    @FXML // fx:id="dpDataNascimentoProfessor"
    private DatePicker dpDataNascimentoProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tfNomeProfessor"
    private TextField tfNomeProfessor; // Value injected by FXMLLoader

    public void handleApresentaDadosTVTurma(Event event) {
        apresentaDadosNaTvProfessor();
    }

    public Boolean verificaDataNascimento(Integer ano) {
        Calendar gregCalendar = new GregorianCalendar();
        Integer year = gregCalendar.get(Calendar.YEAR);
        return year - ano >= 18;
    }

    @FXML
    void handlerAdicionarProfessor(ActionEvent event) {

        if (tfNomeProfessor.getText().length() != 0 && dpDataNascimentoProfessor.getValue() != null) {
            Integer dia = dpDataNascimentoProfessor.getValue().getDayOfMonth();
            Integer mes = dpDataNascimentoProfessor.getValue().getMonth().getValue();
            Integer ano = dpDataNascimentoProfessor.getValue().getYear();
            if (verificaDataNascimento(ano)) {
                bd.adicionarProfessores(tfNomeProfessor.getText(), new Date(dia, mes, ano));
                bd.gravarProfessores();
                apresentaDadosNaTvProfessor();
                tfNomeProfessor.clear();
                dpDataNascimentoProfessor.setValue(null);
            }
        }

    }

    @FXML
    void handleRemoverProfessor(ActionEvent event) {
        if (tVTabProfessor.getSelectionModel().getSelectedItem() != null) {
            Professor aux = tVTabProfessor.getSelectionModel().getSelectedItem();
            bd.deleteProfessores(aux);
            bd.getHistoricoProfessores().put(aux.getId(), aux);
            bd.gravarHistoricoProfessores();
            apresentaDadosNaTvProfessor();
        }
    }

    void apresentaDadosNaTvProfessor() {
        tVTabProfessor.getItems().clear();

        if (bd.getProfessores().size() > 0) {
            tVTabProfessor.getItems().clear();
            for (Integer s : bd.getProfessores().keys()) {
                tVTabProfessor.getItems().add(bd.getProfessores().get(s));
            }
        }
    }

    /**
     * Alunos
     */

    @FXML // fx:id="tfNomeAluno"
    private TextField tfNomeAluno; // Value injected by FXMLLoader

    @FXML // fx:id="dpDataNascimentoAluno"
    private DatePicker dpDataNascimentoAluno; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoAnoTabAluno"
    private ComboBox<Integer> cbSeleccaoAnoTabAluno; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoCursoTabAluno"
    private ComboBox<Curso> cbSeleccaoCursoTabAluno; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoCadeiraTabAluno"
    private ComboBox<Turma> cbSeleccaoCadeiraTabAluno; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAlunos"
    private TableView<Aluno> tVTabAlunos; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAlunoId"
    private TableColumn<Aluno, Integer> tVTabAlunoId; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAlunoNome"
    private TableColumn<Aluno, String> tVTabAlunoNome; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAlunoEmail"
    private TableColumn<Aluno, String> tVTabAlunoEmail; // Value injected by FXMLLoader
    @FXML // fx:id="tVTabAlunoCurso"
    private TableColumn<Aluno, Curso> tVTabAlunoCurso; // Value injected by FXMLLoader
    @FXML // fx:id="tVTabAlunoDataNascimento"
    private TableColumn<Aluno, Date> tVTabAlunoDataNascimento; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAlunoUnidadesInscritos"
    private TableColumn<Aluno, Integer> tVTabAlunoUnidadesInscritos; // Value injected by FXMLLoader

    @FXML // fx:id="tASeleccaoCadeirasTabAluno"
    private TextArea tASeleccaoCadeirasTabAluno; // Value injected by FXMLLoader

    public void handlerAnoCursoAluno(ActionEvent actionEvent) {
        Curso curso = cbSeleccaoCursoTabAluno.getValue();
        Integer ano = cbSeleccaoAnoTabAluno.getValue();
        if (tVTabAlunos.getSelectionModel().getSelectedItem() != null) {
            Aluno aluno = tVTabAlunos.getSelectionModel().getSelectedItem();
            if (cbSeleccaoAnoTabAluno.getValue() != null && cbSeleccaoCursoTabAluno.getValue() != null) {
                aluno.setAno(ano);
                aluno.setCurso(curso);
            }
            if (cbSeleccaoAnoTabAluno.getValue() != null) {
                aluno.setAno(ano);
            }
            if (cbSeleccaoCursoTabAluno.getValue() != null) {
                aluno.setCurso(curso);
            }
        }
        apresentaDadosNaTvAlunos();
    }

    public void handlerRemoverCursoAluno(ActionEvent actionEvent) {
        if (tVTabAlunos.getSelectionModel().getSelectedItem() != null) {
            Aluno aluno = tVTabAlunos.getSelectionModel().getSelectedItem();
            if (aluno.getCurso() != null) {
                aluno.setCurso(null);
            }
        }
        apresentaDadosNaTvAlunos();
    }

    @FXML
    void handleApresentaDadosCBAluno(Event event) {
        auxiliar = new ArrayList<>();
        apresentaDadosNaTvAlunos();
        apresentaDadosNaCBAlunos();
    }

    @FXML
    void handleAdicionarCadeiraAluno(ActionEvent event) {
        if (!auxiliar.contains(cbSeleccaoCadeiraTabAluno.getValue())) {
            auxiliar.add(cbSeleccaoCadeiraTabAluno.getValue());
        }
        tASeleccaoCadeirasTabAluno.clear();
        if (auxiliar.size() > 0) {
            for (Turma h : auxiliar) {
                tASeleccaoCadeirasTabAluno.setWrapText(true);
                tASeleccaoCadeirasTabAluno.setText(h.toString());
            }
        }
    }

    @FXML
    void handlerAdicionarAluno(ActionEvent event) {

        if (tfNomeAluno.getText().length() != 0 && dpDataNascimentoAluno.getValue() != null) {
            Integer dia = dpDataNascimentoAluno.getValue().getDayOfMonth();
            Integer mes = dpDataNascimentoAluno.getValue().getMonth().getValue();
            Integer ano = dpDataNascimentoAluno.getValue().getYear();
            Integer id = bd.getNumeroUsers() + 1;
            Curso aux = bd.getCursos().get(cbSeleccaoCursoTabAluno.getValue().getId());
            if (verificaDataNascimento(ano)) {
                Aluno novo = new Aluno(id, tfNomeAluno.getText(), new Date(dia, mes, ano), aux, cbSeleccaoAnoTabAluno.getValue());
                bd.adicionarAlunos(novo);
                System.out.println(novo);
                bd.setNumeroUsers(id);
                bd.gravarAlunos();
                tfNomeAluno.clear();
                dpDataNascimentoAluno.setValue(null);
                cbSeleccaoCursoTabAluno.getItems().clear();
                cbSeleccaoAnoTabAluno.getItems().clear();
            }
        }
        apresentaDadosNaTvAlunos();
        apresentaDadosNaCBAlunos();
        bd.printAlunos();
        System.out.println("Depois alunos\n");
    }

    @FXML
    void handlerRemoverAluno(ActionEvent event) {
        if (tVTabAlunos.getSelectionModel().getSelectedItem() != null) {
            Aluno aux = tVTabAlunos.getSelectionModel().getSelectedItem();
            bd.getHistoricoAlunos().put(aux.getId(), aux);
            bd.deleteAlunos(aux);
            bd.gravarHistoricoAlunos();
        }
        apresentaDadosNaTvAlunos();
        apresentaDadosNaCBAlunos();

    }


    void apresentaDadosNaTvAlunos() {
        tVTabAlunos.getItems().clear();
        cbSeleccaoCursoTabAluno.getItems().clear();
        cbSeleccaoAnoTabAluno.getItems().clear();
        if (bd.getAlunos().size() > 0) {
            for (Integer s : bd.getAlunos().keys()) {
                tVTabAlunos.getItems().add(bd.getAlunos().get(s));
            }
        }

    }

    void apresentaDadosNaCBAlunos() {
        cbSeleccaoCursoTabAluno.getItems().clear();
        cbSeleccaoAnoTabAluno.getItems().clear();
        if (bd.getCursos().size() > 0) {
            for (String s : bd.getCursos().keys()) {
                cbSeleccaoCursoTabAluno.getItems().add(bd.getCursos().get(s));
            }
        }
        cbSeleccaoAnoTabAluno.getItems().addAll(1, 2, 3, 4, 5);
    }

    /**
     * Salas
     */
    @FXML // fx:id="tVTabSalas"
    private TableView<Sala> tVTabSalas; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaId"
    private TableColumn<Sala, Integer> tVTabSalaId; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaLotacao"
    private TableColumn<Sala, Integer> tVTabSalaLotacao; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaTomadas"
    private TableColumn<Sala, Integer> tVTabSalaTomadas; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaPiso"
    private TableColumn<Sala, Integer> tVTabSalaPiso; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaProjecto"
    private TableColumn<Sala, Boolean> tVTabSalaProjecto; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaX"
    private TableColumn<Sala, String> tVTabSalaX; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaIY"
    private TableColumn<Sala, String> tVTabSalaY; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabSalaZ"
    private TableColumn<Sala, Integer> tVTabSalaZ; // Value injected by FXMLLoader


    //  @FXML // fx:id="tVTabSalaPonto"
    @FXML
    private TableColumn<Sala, Ponto3D> tVTabSalaPonto; // Value injected by FXMLLoader

    @FXML // fx:id="tfIdSala"
    private TextField tfIdSala; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoPisoSala"
    private ComboBox<Integer> cbSeleccaoPisoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfLotacao"
    private TextField tfLotacao; // Value injected by FXMLLoader

    @FXML // fx:id="tfTomadas"
    private TextField tfTomadas; // Value injected by FXMLLoader

    @FXML // fx:id="tfXSala"
    private TextField tfXSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfYSala"
    private TextField tfYSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfZSala"
    private TextField tfZSala; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoProjectorSala"
    private ComboBox<String> cbSeleccaoProjectorSala; // Value injected by FXMLLoader


    public void handlerAdicionarSala(ActionEvent actionEvent) {
        Integer id = Integer.parseInt(tfIdSala.getText());
        Integer lotacao = Integer.parseInt(tfLotacao.getText());
        Integer tomadas = Integer.parseInt(tfTomadas.getText());
        Integer piso = cbSeleccaoPisoSala.getValue();
        Boolean projector;
        if (cbSeleccaoProjectorSala.getValue().compareTo("Sim") == 0) {
            projector = true;
        } else {
            projector = false;
        }
        Ponto3D novoPonto = new Ponto3D(Double.parseDouble(tfXSala.getText()), Double.parseDouble(tfYSala.getText()), piso, id, "Sala " + id, true);

        if (tfIdSala.getText().length() > 0 && tfLotacao.getText().length() > 0 && cbSeleccaoProjectorSala.getValue().length() > 0 &&
                cbSeleccaoPisoSala.getValue() != null && tfIdSala.getText().length() > 0 && tfXSala.getText().length() > 0
                && tfXSala.getText().length() > 0 && tfTomadas.getText().length() > 0 && !bd.getSalas().contains(id)) {
            Sala novaSala = new Sala(id, lotacao, tomadas, projector, piso, novoPonto);
            bd.adicionarSalas(novaSala);

        } else if (tfIdSala.getText().length() > 0 && tfLotacao.getText().length() > 0 && cbSeleccaoProjectorSala.getValue().length() > 0 &&
                cbSeleccaoPisoSala.getValue() != null && tfIdSala.getText().length() > 0 && tfXSala.getText().length() > 0
                && tfYSala.getText().length() > 0 && !bd.getSalas().contains(id)) {

            Sala novaSala1 = new Sala(id, lotacao, projector, piso);
            novaSala1.setPonto(novoPonto);
            bd.adicionarSalas(novaSala1);
        }
        tfIdSala.clear();
        tfLotacao.clear();
        tfTomadas.clear();
        tfXSala.clear();
        tfYSala.clear();
        bd.gravarSalas();
        apresentaDadosNaCBSalas();
        apresentaDadosNaTvSala();
    }

    public void handlerRemoverSala(ActionEvent actionEvent) {
        if (tVTabSalas.getSelectionModel().getSelectedItem() != null) {
            Sala sala = tVTabSalas.getSelectionModel().getSelectedItem();
            bd.getHistoricoSalas().put(sala.getId(), sala);
            bd.deleteSalas(sala);
            bd.gravarHistoricoSalas();
        }
        apresentaDadosNaTvSala();
    }

    public void handleApresentaDadosCBSala(Event event) {
        apresentaDadosNaCBSalas();
        apresentaDadosNaTvSala();
    }

    void apresentaDadosNaCBSalas() {
        cbSeleccaoProjectorSala.getItems().clear();
        cbSeleccaoPisoSala.getItems().clear();
        cbSeleccaoPisoSala.getItems().addAll(-1, 0, 1, 2, 3);
        cbSeleccaoProjectorSala.getItems().addAll("Sim", "Não");
    }

    void apresentaDadosNaTvSala() {
        tVTabSalas.getItems().clear();
        if (bd.getSalas().size() > 0) {
            for (Integer s : bd.getSalas().keys()) {
                tVTabSalas.getItems().add(bd.getSalas().get(s));
            }
        }

    }

    /**
     * Horario
     */

    @FXML // fx:id="tVTabHorario"
    private TableView<Horario> tVTabHorario; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabHorarioId"
    private TableColumn<Horario, String> tVTabHorarioId; // Value injected by FXMLLoader
    @FXML // fx:id="tVTabHorarioCadeira"
    private TableColumn<Horario, String> tVTabHorarioCadeira; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabHorarioSala"
    private TableColumn<Horario, String> tVTabHorarioSala; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabHorarioHoraInicio"
    private TableColumn<Horario, String> tVTabHorarioHoraInicio; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabHorarioHoraFim"
    private TableColumn<Horario, String> tVTabHorarioHoraFim; // Value injected by FXMLLoader
    @FXML // fx:id="tVTabHorariorDiaSemana"
    private TableColumn<Horario, String> tVTabHorariorDiaSemana; // Value injected by FXMLLoader


    @FXML // fx:id="cbHIHorario"
    private ComboBox<Integer> cbHIHorario; // Value injected by FXMLLoader

    @FXML // fx:id="cbHFHorario"
    private ComboBox<Integer> cbHFHorario; // Value injected by FXMLLoader

    @FXML // fx:id="cbMIHorario"
    private ComboBox<Integer> cbMIHorario; // Value injected by FXMLLoader

    @FXML // fx:id="cbMFHorario"
    private ComboBox<Integer> cbMFHorario; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoSalaTabHorario"
    private ComboBox<Sala> cbSeleccaoSalaTabHorario; // Value injected by FXMLLoader
    @FXML // fx:id="cbDiaSemanaHorario"
    private ComboBox<String> cbDiaSemanaHorario; // Value injected by FXMLLoader
    @FXML // fx:id="cbSeleccaoTurmaTabHorario"
    private ComboBox<Turma> cbSeleccaoTurmaTabHorario; // Value injected by FXMLLoader


    @FXML // fx:id="cbHIHorarioDel"
    private ComboBox<Integer> cbHIHorarioDel; // Value injected by FXMLLoader

    @FXML // fx:id="cbHFHorarioDel"
    private ComboBox<Integer> cbHFHorarioDel; // Value injected by FXMLLoader

    @FXML // fx:id="cbMIHorarioDel"
    private ComboBox<Integer> cbMIHorarioDel; // Value injected by FXMLLoader

    @FXML // fx:id="cbMFHorarioDel"
    private ComboBox<Integer> cbMFHorarioDel; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoSalaTabHorarioDel"
    private ComboBox<Sala> cbSeleccaoSalaTabHorarioDel; // Value injected by FXMLLoader
    @FXML // fx:id="cbDiaSemanaHorarioDel"
    private ComboBox<String> cbDiaSemanaHorarioDel; // Value injected by FXMLLoader
    @FXML // fx:id="cbSeleccaoTurmaTabHorarioDel"
    private ComboBox<Turma> cbSeleccaoTurmaTabHorarioDel; // Value injected by FXMLLoader

    @FXML
    void handlerRemoverHorario(ActionEvent event) {
        Turma aux = cbSeleccaoTurmaTabHorarioDel.getValue();
        aux.apagarHorario(preencherDiaSemana(cbDiaSemanaHorarioDel.getValue()), cbHIHorarioDel.getValue(), cbSeleccaoSalaTabHorarioDel.getValue());
        preencherComboBoxHorario();
        preencherTvHorario();
        preencherSalaHorario();
        preencherTurmaHorario();
    }

    @FXML
    void handlerAdicionarHorario(ActionEvent event) {
        Turma aux = cbSeleccaoTurmaTabHorario.getValue();
        if (cbHIHorario.getValue() < cbHFHorario.getValue()) {
            Horario novoHorario = new Horario(cbSeleccaoSalaTabHorario.getValue(), cbHIHorario.getValue(), cbHFHorario.getValue()
                    , cbMIHorario.getValue(), cbMFHorario.getValue(), preencherDiaSemana(cbDiaSemanaHorario.getValue()), aux);
            aux.adicionaHorario(novoHorario);
            //  System.out.println(aux);
            aux.printHorarios();
            preencherComboBoxHorario();
            preencherTvHorario();
            preencherSalaHorario();
            preencherTurmaHorario();
        }
    }

    void preencherComboBoxHorario() {
        cbHIHorario.getItems().clear();
        cbHFHorario.getItems().clear();
        cbMIHorario.getItems().clear();
        cbMFHorario.getItems().clear();
        cbHIHorarioDel.getItems().clear();
        cbHFHorarioDel.getItems().clear();

        cbDiaSemanaHorario.getItems().clear();
        for (int i = 8; i <= 22; i++) {
            cbHIHorario.getItems().add(i);
            cbHFHorario.getItems().add(i);
            cbHIHorarioDel.getItems().add(i);
            cbHFHorarioDel.getItems().add(i);
        }
        for (int i = 0; i <= 59; i++) {
            cbMIHorario.getItems().add(i);
            cbMFHorario.getItems().add(i);
        }
        cbDiaSemanaHorario.getItems().addAll("Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira");
        cbDiaSemanaHorarioDel.getItems().addAll("Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira");
    }

    void preencherTurmaHorario() {
        cbSeleccaoTurmaTabHorario.getItems().clear();
        cbSeleccaoTurmaTabHorarioDel.getItems().clear();
        for (String i : bd.getTurmas().keys()) {
            cbSeleccaoTurmaTabHorario.getItems().add(bd.getTurmas().get(i));
            cbSeleccaoTurmaTabHorarioDel.getItems().add(bd.getTurmas().get(i));
        }
    }

    void preencherSalaHorario() {
        cbSeleccaoSalaTabHorario.getItems().clear();
        cbSeleccaoSalaTabHorarioDel.getItems().clear();
        for (Integer i : bd.getSalas().keys()) {
            cbSeleccaoSalaTabHorario.getItems().add(bd.getSalas().get(i));
        }
        for (Integer i : bd.getSalas().keys()) {
            cbSeleccaoSalaTabHorarioDel.getItems().add(bd.getSalas().get(i));
        }
    }

    void preencherTvHorario() {
        tVTabHorario.getItems().clear();
        for (String i : bd.getTurmas().keys()) {
            for (Horario h : bd.getTurmas().get(i).getHorarios()) {
                tVTabHorario.getItems().add(h);

            }
        }
    }

    public void handleApresentaDadosCBHorario(Event event) {
        preencherComboBoxHorario();
        preencherTvHorario();
        preencherSalaHorario();
        preencherTurmaHorario();
    }

    /**
     * Atendimentos
     */
    @FXML // fx:id="cbSeleccaoProfessorAtendimento"
    private ComboBox<Professor> cbSeleccaoProfessorAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbHIAtendimento"
    private ComboBox<Integer> cbHIAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbHFAtendimento"
    private ComboBox<Integer> cbHFAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbMIAtendimento"
    private ComboBox<Integer> cbMIAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbMFAtendimento"
    private ComboBox<Integer> cbMFAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoSalaAtendimento"
    private ComboBox<Sala> cbSeleccaoSalaAtendimento; // Value injected by FXMLLoader
    @FXML // fx:id="cbDiaSemanaAtendimento"
    private ComboBox<String> cbDiaSemanaAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="cbSeleccaoAlunoAtendimento"
    private ComboBox<Aluno> cbSeleccaoAlunoAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAtendimentos"
    private TableView<Atendimento> tVTabAtendimentos; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAtendimentoProfessor"
    private TableColumn<Atendimento, String> tVTabAtendimentoProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAtendimentoHorario"
    private TableColumn<Atendimento, String> tVTabAtendimentoHorario; // Value injected by FXMLLoader

    @FXML // fx:id="tVTabAtendimentoAluno"
    private TableColumn<Atendimento, String> tVTabAtendimentoAluno; // Value injected by FXMLLoader


    @FXML
    void handlerRemoverAtendimento(ActionEvent event) {
        if (tVTabAtendimentos.getSelectionModel().getSelectedItem().getProfessor() != null) {
            tVTabAtendimentos.getSelectionModel().getSelectedItem().getProfessor().removerAtendimento(tVTabAtendimentos.getSelectionModel().getSelectedItem());
            preencherTvAtendimentos();
        }
        preencherTvAtendimentos();
    }

    @FXML
    void handlerMarcarAtendimento(ActionEvent event) {
        if (tVTabAtendimentos.getSelectionModel().getSelectedItem().getAluno() == null && tVTabAtendimentos.getSelectionModel().getSelectedItem() != null) {
            tVTabAtendimentos.getSelectionModel().getSelectedItem().setAluno(cbSeleccaoAlunoAtendimento.getValue());
            preencherTvAtendimentos();
        }
        preencherTvAtendimentos();
    }

    @FXML
    void handlerAdicionarAtendimento(ActionEvent event) {
        Professor professor = cbSeleccaoProfessorAtendimento.getValue();
        System.out.println(professor);
        if (cbHIAtendimento.getValue() < cbHFAtendimento.getValue()) {
            Horario novoHorario = new Horario(cbSeleccaoSalaAtendimento.getValue(), cbHIAtendimento.getValue(), cbHFAtendimento.getValue()
                    , cbMIAtendimento.getValue(), cbMFAtendimento.getValue(), preencherDiaSemana(cbDiaSemanaAtendimento.getValue()));
            System.out.println(novoHorario);
            professor.adicionarHorarioAtendimento(novoHorario);
            preencherTvAtendimentos();
        }
    }

    public void handleApresentaDadosCBAtendimentos(Event event) {
        preencherComboBoxAtendimento();
        preencherTvAtendimentos();
        preencherAtendimentosProfessor();
        preencherAtendimentosAlunos();
        preencherAtendimentosSala();
    }

    void preencherAtendimentosProfessor() {
        cbSeleccaoProfessorAtendimento.getItems().clear();
        for (Integer i : bd.getProfessores().keys()) {
            cbSeleccaoProfessorAtendimento.getItems().add(bd.getProfessores().get(i));
        }
    }

    void preencherAtendimentosAlunos() {
        cbSeleccaoAlunoAtendimento.getItems().clear();
        for (Integer i : bd.getAlunos().keys()) {
            cbSeleccaoAlunoAtendimento.getItems().add(bd.getAlunos().get(i));
        }
    }

    void preencherAtendimentosSala() {
        cbSeleccaoSalaAtendimento.getItems().clear();
        for (Integer i : bd.getSalas().keys()) {
            cbSeleccaoSalaAtendimento.getItems().add(bd.getSalas().get(i));
        }
    }

    void preencherComboBoxAtendimento() {
        cbHIAtendimento.getItems().clear();
        cbHFAtendimento.getItems().clear();
        cbMIAtendimento.getItems().clear();
        cbMFAtendimento.getItems().clear();
        cbDiaSemanaAtendimento.getItems().clear();
        for (int i = 8; i <= 22; i++) {
            cbHFAtendimento.getItems().add(i);
            cbHIAtendimento.getItems().add(i);
        }
        for (int i = 0; i <= 59; i++) {
            cbMIAtendimento.getItems().add(i);
            cbMFAtendimento.getItems().add(i);
        }
        cbDiaSemanaAtendimento.getItems().addAll("Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira");
    }

    void preencherTvAtendimentos() {
        tVTabAtendimentos.getItems().clear();
        for (Integer i : bd.getProfessores().keys()) {
            for (Atendimento a : bd.getProfessores().get(i).getListaAtendimentosAlunos()) {
                tVTabAtendimentos.getItems().add(a);
                System.out.println(a);
            }
        }
    }


    public Integer preencherDiaSemana(String dia) {
        switch (dia) {
            case "Segunda-Feira": {
                return 1;
            }
            case "Terça-Feira": {
                return 2;
            }
            case "Quarta-Feira": {
                return 3;
            }
            case "Quinta-Feira": {
                return 4;
            }
            case "Sexta-Feira": {
                return 5;
            }
        }
        return 0;
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

    /*** TURMAS ALUNOS*/
    @FXML // fx:id="tVTurmaAluno"
    private TableView<Turma> tVTurmaAluno; // Value injected by FXMLLoader

    @FXML // fx:id="tVTurmaAlunoId"
    private TableColumn<Turma, String> tVTurmaAlunoId; // Value injected by FXMLLoader

    @FXML // fx:id="tVTurmaAlunoTurma"
    private TableColumn<Turma, String> tVTurmaAlunoTurma; // Value injected by FXMLLoader

    @FXML // fx:id="tVTurmaAlunoUc"
    private TableColumn<Turma, String> tVTurmaAlunoUc; // Value injected by FXMLLoader

    @FXML // fx:id="tVTurmaAlunoProfessor"
    private TableColumn<Turma, String> tVTurmaAlunoProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="cbAlunoTurmasAlunoTA"
    private ComboBox<Aluno> cbAlunoTurmasAlunoTA; // Value injected by FXMLLoader

    @FXML // fx:id="cbTurmaTurmasAlunoTA"
    private ComboBox<Turma> cbTurmaTurmasAlunoTA; // Value injected by FXMLLoader


    @FXML
    void handlerPesquisarTurmasAlunoTA(ActionEvent event) {
        tVTurmaAluno.getItems().clear();
        Aluno aux = cbAlunoTurmasAlunoTA.getValue();
        for (String t : aux.getListaTurmas().keys()) {
            if (bd.getTurmas().get(t) != null)
                tVTurmaAluno.getItems().add(bd.getTurmas().get(t));
        }
    }

    @FXML
    void handlerInserirTurmasAlunoTA(ActionEvent event) {
        tVTurmaAluno.getItems().clear();
        Aluno aux = cbAlunoTurmasAlunoTA.getValue();
        Turma aux1 = cbTurmaTurmasAlunoTA.getValue();
        aux.adicionarCadeirasAoEstudante(aux1);
        for (String t : aux.getListaTurmas().keys()) {
            if (bd.getTurmas().get(t) != null)
                tVTurmaAluno.getItems().add(bd.getTurmas().get(t));
        }
    }

    public void handlerSelecionarAlunoTurmasAlunoTA(ActionEvent actionEvent) {
        preencheCadeirasParaAluno();
    }

    @FXML
    void handlerRemoverTurmasAlunoTA(ActionEvent event) {
        tVTurmaAluno.getItems().clear();
        Aluno aux = cbAlunoTurmasAlunoTA.getValue();
        Turma aux1 = cbTurmaTurmasAlunoTA.getValue();
        aux.apagaCadeirasAoEstudante(aux1);
        for (String t : aux.getListaTurmas().keys()) {
            if (bd.getTurmas().get(t) != null)
                tVTurmaAluno.getItems().add(bd.getTurmas().get(t));
        }
    }

    void preencheCBAlunoTurma() {
        cbAlunoTurmasAlunoTA.getItems().clear();
        for (Integer t : bd.getAlunos().keys()) {
            cbAlunoTurmasAlunoTA.getItems().add(bd.getAlunos().get(t));
        }
    }

    void preencheCadeirasParaAluno() {
        cbTurmaTurmasAlunoTA.getItems().clear();
        Aluno aux = cbAlunoTurmasAlunoTA.getValue();
        for (String t : aux.getCurso().getListaTurmas().keys()) {
            cbTurmaTurmasAlunoTA.getItems().add(aux.getCurso().getListaTurmas().get(t));
        }
    }

    public void handleApresentaDadosCBAlunoTurmaAluno(Event event) {
        preencheCBAlunoTurma();
    }

    /**
     * PESQUISA SALAS
     */

    public void handleApresentaDadosCBPesquisas(Event event) {
    }

    @FXML // fx:id="tVPesquisaSalasLivreId"
    private TableColumn<Sala, Integer> tVPesquisaSalasLivreId; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivreLotacao"
    private TableColumn<Sala, Integer> tVPesquisaSalasLivreLotacao; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivreTomadas"
    private TableColumn<Sala, Integer> tVPesquisaSalasLivreTomadas; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivrePiso"
    private TableColumn<Sala, Integer> tVPesquisaSalasLivrePiso; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivreProjector"
    private TableColumn<Sala, Boolean> tVPesquisaSalasLivreProjector; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivreX"
    private TableColumn<Sala, String> tVPesquisaSalasLivreX; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivreY"
    private TableColumn<Sala, String> tVPesquisaSalasLivreY; // Value injected by FXMLLoader


    @FXML // fx:id="tVPesquisaSalasOcupadasId"
    private TableColumn<Sala, Integer> tVPesquisaSalasOcupadasId; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasLotacao"
    private TableColumn<Sala, Integer> tVPesquisaSalasOcupadasLotacao; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasTomadas"
    private TableColumn<Sala, Integer> tVPesquisaSalasOcupadasTomadas; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasPiso"
    private TableColumn<Sala, Integer> tVPesquisaSalasOcupadasPiso; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasProjector"
    private TableColumn<Sala, Boolean> tVPesquisaSalasOcupadasProjector; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasX"
    private TableColumn<Sala, String> tVPesquisaSalasOcupadasX; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadasY"
    private TableColumn<Sala, String> tVPesquisaSalasOcupadasY; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaSalasLHI1"
    private ComboBox<Integer> cbPesquisaSalasLHI1; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaSalasLHF1"
    private ComboBox<Integer> cbPesquisaSalasLHF1; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaSalasLDiaSemana"
    private ComboBox<String> cbPesquisaSalasLDiaSemana; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaSalasLHI2"
    private ComboBox<Integer> cbPesquisaSalasLHI2; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaSalasLHF2"
    private ComboBox<Integer> cbPesquisaSalasLHF2; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasLivre"
    private TableView<Sala> tVPesquisaSalasLivre; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaSalasOcupadas"
    private TableView<Sala> tVPesquisaSalasOcupadas; // Value injected by FXMLLoader


    @FXML
    void handleApresentaDadosCBPesquisaSalasL(Event event) {
        preencheComboBoxDiaSemanaPesquisaSalas();
    }

    @FXML
    void handlePesquisaSalaLivreEntreHorarios(ActionEvent event) {
        RedBlackBST_AED2<Integer, Sala> aux = salasLivresHorario(preencherDiaSemana(cbPesquisaSalasLDiaSemana.getValue()), cbPesquisaSalasLHI1.getValue(), bd);
        System.out.println(aux.size());
        tVPesquisaSalasLivre.getItems().clear();
        for (Integer i : aux.keys()) {
            tVPesquisaSalasLivre.getItems().add(aux.get(i));
        }
        preencheComboBoxDiaSemanaPesquisaSalas();
        Double asdad = 2.00;
        String value = String.valueOf(asdad);
    }

    @FXML
    void handlePesquisaSalaOcupadasEntreHorarios(ActionEvent event) {

        RedBlackBST_AED2<Integer, Sala> aux = ocupacaoSalasEntreDatas(cbPesquisaSalasLHI2.getValue(), cbPesquisaSalasLHF2.getValue(), bd);
        tVPesquisaSalasOcupadas.getItems().clear();
        for (Integer i : aux.keys()) {
            tVPesquisaSalasOcupadas.getItems().add(aux.get(i));
        }
        preencheComboBoxDiaSemanaPesquisaSalas();
    }

    void preencheComboBoxDiaSemanaPesquisaSalas() {
        cbPesquisaSalasLDiaSemana.getItems().clear();
        cbPesquisaSalasLDiaSemana.getItems().addAll("Segunda-Feira", "Terça-Feira", "Quarta-Feira", "Quinta-Feira", "Sexta-Feira");

        cbPesquisaSalasLHI1.getItems().clear();
//        cbPesquisaSalasLHF1.getItems().clear();
        cbPesquisaSalasLHI2.getItems().clear();
        cbPesquisaSalasLHF2.getItems().clear();

        for (int i = 8; i < 22; i++) {
            cbPesquisaSalasLHI1.getItems().add(i);
            //  cbPesquisaSalasLHF1.getItems().add(i);
            cbPesquisaSalasLHI2.getItems().add(i);
            cbPesquisaSalasLHF2.getItems().add(i);
        }
        //preencherDiaSemana(intval)
    }

    /**
     * PESQUISA PROFESSOR
     */
    @FXML // fx:id="tVPesquisaUCProfessor"
    private TableView<Turma> tVPesquisaUCProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaUCProfessorIDTurma"
    private TableColumn<Turma, String> tVPesquisaUCProfessorIDTurma; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaUCProfessorID"
    private TableColumn<Turma, String> tVPesquisaUCProfessorID; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaUCProfessorProfessor"
    private TableColumn<Turma, String> tVPesquisaUCProfessorProfessor; // Value injected by FXMLLoader


    @FXML // fx:id="tVPesquisaProfessorProfessor"
    private TableView<UnidadeCurricular> tVPesquisaProfessorProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaProfessorProfessorId"
    private TableColumn<UnidadeCurricular, String> tVPesquisaProfessorProfessorUc; // Value injected by FXMLLoader


    @FXML // fx:id="cbPesquisaUCProfessor"
    private ComboBox<UnidadeCurricular> cbPesquisaUCProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="cbPesquisaProfessorProfessor"
    private ComboBox<Professor> cbPesquisaProfessorProfessor; // Value injected by FXMLLoader

    @FXML
    void handleApresentaDadosCBPesquisaProfessor(Event event) {
        preencherComboBoxPesquisaProfessor();
    }

    @FXML
    void handlerPesquisaUCProfessor(ActionEvent event) {
        ArrayList<Turma> aux = professoresTurmaSub(cbPesquisaUCProfessor.getValue().getNome(), bd);
        tVPesquisaUCProfessor.getItems().clear();
        if (aux != null) {
            for (Turma t : aux) {
                tVPesquisaUCProfessor.getItems().add(t);
            }
        }
    }

    @FXML
    void handlerPesquisaProfessorProfessor(ActionEvent event) {
        ArrayList<UnidadeCurricular> aux = todasTurmasProfessor(cbPesquisaProfessorProfessor.getValue().getId(), bd);
        tVPesquisaProfessorProfessor.getItems().clear();
        for (UnidadeCurricular t : aux) {
            tVPesquisaProfessorProfessor.getItems().add(t);
        }
    }

    void preencherComboBoxPesquisaProfessor() {
        cbPesquisaUCProfessor.getItems().clear();
        cbPesquisaProfessorProfessor.getItems().clear();
        for (String s : bd.getUnidadesCurriculares().keys()) {
            cbPesquisaUCProfessor.getItems().add(bd.getUnidadesCurriculares().get(s));

        }
        for (Integer s : bd.getProfessores().keys()) {
            cbPesquisaProfessorProfessor.getItems().add(bd.getProfessores().get(s));

        }
    }

    /**
     * PESQUISA ATENDIMENTO
     */


    @FXML // fx:id="cbPesquisaAtendimento"
    private ComboBox<Aluno> cbPesquisaAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaAtendimento"
    private TableView<Atendimento> tVPesquisaAtendimento; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaAtendimentoProfessor"
    private TableColumn<Atendimento, String> tVPesquisaAtendimentoProfessor; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaAtendimentoHorario"
    private TableColumn<Atendimento, String> tVPesquisaAtendimentoHorario; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaAtendimentoAluno"
    private TableColumn<Atendimento, String> tVPesquisaAtendimentoAluno; // Value injected by FXMLLoader

    @FXML
    void handleApresentaDadosCBPesquisaAtendimento(Event event) {
        preenchementoComboBoxPesquisaAtendimento();
    }

    @FXML
    void alunoPesquisaAtendimento(ActionEvent event) {
        ArrayList<Atendimento> aux = alunosDisponiveisParaAtendimento(cbPesquisaAtendimento.getValue().getId(), bd);
        tVPesquisaAtendimento.getItems().clear();
        for (Atendimento a : aux) {
            tVPesquisaAtendimento.getItems().add(a);
        }
    }

    void preenchementoComboBoxPesquisaAtendimento() {
        cbPesquisaAtendimento.getItems().clear();
        for (Integer i : bd.getAlunos().keys()) {
            cbPesquisaAtendimento.getItems().add(bd.getAlunos().get(i));
        }
    }

    /**
     * Pesquisa tipo de salas
     */
    @FXML // fx:id="tVPesquisaTipoSala"
    private TableView<Sala> tVPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaId"
    private TableColumn<Sala, Integer> tVPesquisaTipoSalaId; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaLotacao"
    private TableColumn<Sala, Integer> tVPesquisaTipoSalaLotacao; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaTomados"
    private TableColumn<Sala, Integer> tVPesquisaTipoSalaTomados; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaPiso"
    private TableColumn<Sala, Integer> tVPesquisaTipoSalaPiso; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaProjector"
    private TableColumn<Sala, Boolean> tVPesquisaTipoSalaProjector; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaX"
    private TableColumn<Sala, String> tVPesquisaTipoSalaX; // Value injected by FXMLLoader

    @FXML // fx:id="tVPesquisaTipoSalaY"
    private TableColumn<Sala, String> tVPesquisaTipoSalaY; // Value injected by FXMLLoader
    @FXML // fx:id="tfNrMinTomadasPesquisaTipoSala"
    private TextField tfNrMinTomadasPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfNrMaxTomadasPesquisaTipoSala"
    private TextField tfNrMaxTomadasPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfMinIntervaloTomadasPesquisaTipoSala"
    private TextField tfMinIntervaloTomadasPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfMaxIntervaloTomadasPesquisaTipoSala"
    private TextField tfMaxIntervaloTomadasPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfMinOcupadaPesquisaTipoSala"
    private TextField tfMinOcupadaPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="tfMaxOcupadaPesquisaTipoSala"
    private TextField tfMaxOcupadaPesquisaTipoSala; // Value injected by FXMLLoader

    @FXML // fx:id="cbProjectorPesquisaTipoSala"
    private ComboBox<String> cbProjectorPesquisaTipoSala; // Value injected by FXMLLoader


    @FXML
    private TextField tfMinIntervaloLotacaoPesquisaTipoSala;

    @FXML
    private TextField tfMaxIntervaloLotacaoPesquisaTipoSala;

    @FXML
    void handlerMaxOcupadaPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();
        Integer valor = Integer.parseInt(tfMaxOcupadaPesquisaTipoSala.getText());
        if (tfMaxOcupadaPesquisaTipoSala.getText() != null) {
            ArrayList<Sala> aux = salasOcupacaoSuperiorValor(valor, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
            tfMaxOcupadaPesquisaTipoSala.clear();
        }

    }

    @FXML
    void handlerMinOcupadaPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();
        Integer valor = Integer.parseInt(tfMinOcupadaPesquisaTipoSala.getText());
        if (tfMinOcupadaPesquisaTipoSala.getText() != null) {
            ArrayList<Sala> aux = salasOcupacaoInferiorValor(valor, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
            tfMinOcupadaPesquisaTipoSala.clear();
        }
    }

    @FXML
    void handlerNrMaxTomadasPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();
        Integer valor = Integer.parseInt(tfNrMaxTomadasPesquisaTipoSala.getText());
        if (tfNrMaxTomadasPesquisaTipoSala != null) {
            ArrayList<Sala> aux = salasTomadasSuperiorValor(valor, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
            tfNrMaxTomadasPesquisaTipoSala.clear();
        }
    }

    @FXML
    void handlerNrMinTomadasPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();
        Integer valor = Integer.parseInt(tfNrMinTomadasPesquisaTipoSala.getText());
        if (tfNrMinTomadasPesquisaTipoSala != null) {
            ArrayList<Sala> aux = salasTomadasInferiorValor(valor, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
            tfNrMinTomadasPesquisaTipoSala.clear();
        }
    }

    @FXML
    void handlerIntervaloTomadasPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();
        Integer valor = Integer.parseInt(tfMinIntervaloTomadasPesquisaTipoSala.getText());
        Integer valor1 = Integer.parseInt(tfMaxIntervaloTomadasPesquisaTipoSala.getText());
        if (tfMinIntervaloTomadasPesquisaTipoSala.getText() != null && tfMaxIntervaloTomadasPesquisaTipoSala.getText() != null) {
            ArrayList<Sala> aux = salasTomadasEntreValores(valor, valor1, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
        }
        tfMinIntervaloTomadasPesquisaTipoSala.clear();
        tfMaxIntervaloTomadasPesquisaTipoSala.clear();
    }

    @FXML
    void handlerProjectorPesquisaTipoSala(ActionEvent event) {
        tVPesquisaTipoSala.getItems().clear();

        ArrayList<Sala> aux = salasComProjetor(bd);

        if (cbProjectorPesquisaTipoSala.getValue().compareTo("Sim") == 0) {
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
        } else {
            for (Integer i : bd.getSalas().keys()) {
                if (!bd.getSalas().get(i).getProjector())
                    tVPesquisaTipoSala.getItems().add(bd.getSalas().get(i));
            }
        }
    }

    @FXML
    void handleApresentaDadosCBPesquisaSalasTipo(Event event) {
        cbProjectorPesquisaTipoSala.getItems().clear();
        cbProjectorPesquisaTipoSala.getItems().addAll("Sim", "Não");
        preencheTvSalasPesquisa();
    }

    public void preencheTvSalasPesquisa() {
        tVPesquisaTipoSala.getItems().clear();
        for (Integer i : bd.getSalas().keys()) {
            tVPesquisaTipoSala.getItems().add(bd.getSalas().get(i));
        }
    }

    @FXML
    void handlerIntervaloLotacaoPesquisaTipoSala(ActionEvent event) {
        Integer valor = null, valor1 = null;
        tVPesquisaTipoSala.getItems().clear();
        System.out.println("max  ->" + tfMaxIntervaloLotacaoPesquisaTipoSala.getText());
        System.out.println("min  ->" + tfMinIntervaloLotacaoPesquisaTipoSala.getText());
        if (!tfMinIntervaloLotacaoPesquisaTipoSala.getText().isEmpty()) {
            valor = Integer.parseInt(tfMinIntervaloLotacaoPesquisaTipoSala.getText());
        }
        System.out.println("max  ->" + tfMaxIntervaloLotacaoPesquisaTipoSala.getText());
        System.out.println("min  ->" + tfMinIntervaloLotacaoPesquisaTipoSala.getText());
        if (!tfMaxIntervaloLotacaoPesquisaTipoSala.getText().isEmpty()) {
            valor1 = Integer.parseInt(tfMaxIntervaloLotacaoPesquisaTipoSala.getText());
        }
        System.out.println("max  ->" + tfMaxIntervaloLotacaoPesquisaTipoSala.getText());
        System.out.println("min  ->" + tfMinIntervaloLotacaoPesquisaTipoSala.getText());
        if (valor != null && valor1 != null) {

            ArrayList<Sala> aux = salasLotacaoEntreValores(valor, valor1, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
        } else if (valor == null && valor1 != null) {
            //  Integer valor1 = Integer.parseInt(tfMaxIntervaloLotacaoPesquisaTipoSala.getText());
            ArrayList<Sala> aux = salasLotacaoMax(valor1, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
        } else if (valor != null && valor1 == null) {
            //   Integer valor = Integer.parseInt(tfMinIntervaloLotacaoPesquisaTipoSala.getText());
            ArrayList<Sala> aux = salasLotacaoMin(valor, bd);
            for (Sala i : aux) {
                tVPesquisaTipoSala.getItems().add(i);
            }
        } else if (valor == null && valor1 == null) {
            preencheTvSalasPesquisa();
        }
        tfMinIntervaloLotacaoPesquisaTipoSala.clear();
        tfMaxIntervaloLotacaoPesquisaTipoSala.clear();
    }

    /**
     * Pesquisa Now
     */

    @FXML // fx:id="tANow"
    private TextArea tANow; // Value injected by FXMLLoader

    public void handleApresentaDadosCBPesquisaNow(Event event) {
        tANow.clear();
        Calendar gregorianCalendar = new GregorianCalendar();
        Integer dia = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
        Integer hora = gregorianCalendar.get(Calendar.HOUR_OF_DAY);

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
        for (String turma : bd.getTurmas().keys()) {
            for (Horario h : bd.getTurmas().get(turma).getHorarios()) {
                if (pesquisaDia == h.getDia() && hora >= h.getHoraInicio() && hora <= h.getMinutoFim()) {
                    for (Integer i : bd.getTurmas().get(turma).getListaAlunosInscritos().keys()) {
                        if (x == 0) {
                            cursos.put(bd.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso().getId(), bd.getTurmas().get(turma).getListaAlunosInscritos().get(i).getCurso());
                            x += 1;
                        }
                        alunosOcupados.put(bd.getTurmas().get(turma).getListaAlunosInscritos().get(i).getId(), bd.getTurmas().get(turma).getListaAlunosInscritos().get(i));
                    }
                    professoresOcupados.put(bd.getTurmas().get(turma).getProfessor().getId(), bd.getTurmas().get(turma).getProfessor());
                    salasOcupadas.put(h.getSala().getId(), h.getSala());
                    turmas.put(bd.getTurmas().get(turma).getId(), bd.getTurmas().get(turma));

                }
                x = 0;
            }
        }
        for (Integer alunos : bd.getAlunos().keys()) {
            if (!alunosOcupados.contains(bd.getAlunos().get(alunos).getId())) {
                alunosLivres.put(bd.getAlunos().get(alunos).getId(), bd.getAlunos().get(alunos));
            }
        }
        for (Integer professor : bd.getProfessores().keys()) {
            if (!professoresOcupados.contains(bd.getProfessores().get(professor).getId())) {
                professoresLivres.put(bd.getProfessores().get(professor).getId(), bd.getProfessores().get(professor));
            }
        }

        for (Integer sala : bd.getSalas().keys()) {
            if (!salasOcupadas.contains(bd.getSalas().get(sala).getId())) {
                salasLivres.put(bd.getSalas().get(sala).getId(), bd.getSalas().get(sala));
            }
        }

        System.out.println("LISTA DE TUDO QUE ESTA A FUNCiONAR NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        tANow.appendText("LISTA DE TUDO QUE ESTA A FUNCiONAR NO MOMENTO\n\t ALUNOS A TER AULAS:\n");

        for (Integer aux : alunosOcupados.keys()) {
            System.out.println(alunosOcupados.get(aux));
            tANow.appendText(alunosOcupados.get(aux) + "\n");

        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        tANow.appendText("\t PROFESSORES  A DAR AULAS:\n");
        for (Integer aux : professoresOcupados.keys()) {
            System.out.println(professoresOcupados.get(aux));
            tANow.appendText(professoresOcupados.get(aux) + "\n");
        }
        System.out.println("\t SALAS OCUPADAS");
        tANow.appendText("\t SALAS OCUPADAS\n");
        for (Integer aux : salasOcupadas.keys()) {
            System.out.println(salasOcupadas.get(aux));
            tANow.appendText(salasOcupadas.get(aux) + "\n");

        }
        System.out.println("\t TURMAS OCUPADAS E DISCIPLINA");
        tANow.appendText("\t TURMAS OCUPADAS E DISCIPLINA\n");

        for (String aux : turmas.keys()) {
            System.out.println(turmas.get(aux).getId() + " " + turmas.get(aux).getUnidadeCurricular().getNome());
            tANow.appendText(turmas.get(aux).getId() + " " + turmas.get(aux).getUnidadeCurricular().getNome() + "\n");
        }
        System.out.println("\t CURSOS OCUPADAS");
        tANow.appendText("\t CURSOS OCUPADAS\n");

        for (String aux : cursos.keys()) {
            System.out.println(cursos.get(aux));
            tANow.appendText(cursos.get(aux) + "\n");

        }

        System.out.println("LISTA DE TUDO QUE ESTA A LIVRES NO MOMENTO");
        System.out.println("\t ALUNOS A TER AULAS:");
        tANow.appendText("LISTA DE TUDO QUE ESTA A LIVRES NO MOMENTO\n" + "\t ALUNOS A TER AULAS:\n");
        for (Integer aux : alunosLivres.keys()) {
            System.out.println(alunosLivres.get(aux));
            tANow.appendText(alunosLivres.get(aux) + "\n");
        }
        System.out.println("\t PROFESSORES  A DAR AULAS:");
        tANow.appendText("\t PROFESSORES  A DAR AULAS:\n");
        for (Integer aux : professoresLivres.keys()) {
            System.out.println(professoresLivres.get(aux));
            tANow.appendText(professoresLivres.get(aux) + "\n");
        }
        System.out.println("\t SALAS Livres");
        tANow.appendText("\t SALAS Livres \n");
        for (Integer aux : salasLivres.keys()) {
            System.out.println(salasLivres.get(aux));
            tANow.appendText(salasLivres.get(aux) + "\n");
        }
    }

    public void handlerCarregarDisciplinas(ActionEvent actionEvent) {
        bd.carregarUc();
    }

    public void handlerCarregarAlunos(ActionEvent actionEvent) {
        bd.carregarAlunosVersaoSimplificada();
    }

    public void handlerCarregarProfessores(ActionEvent actionEvent) {
        bd.carregarProfessoresVersaoSimplificada();
    }

    public void handlerCarregarCursos(ActionEvent actionEvent) {
        bd.carregarCursos();
    }

    public void handlerCarregarSalas(ActionEvent actionEvent) {
        bd.carregarSalasV2();
    }

    public void handlerCarregarAtendimentos(ActionEvent actionEvent) {
        bd.carregarAtendimentos();
    }

    public void handlerCarregarTurmas(ActionEvent actionEvent) {
        bd.carregarTurmas();
    }

    public void handlerCarregarHorario(ActionEvent actionEvent) {
        bd.carregarHorarios();
    }

    public void handlerCarregamentoTotal(ActionEvent actionEvent) {
        bd.carregarProfessoresVersaoSimplificada();
        bd.carregarAlunosVersaoSimplificada();
        bd.carregarUc();
        bd.carregarCursos();
        bd.carregarSalasV2();
        bd.carregarAtendimentos();
        bd.carregarTurmas();
        bd.carregarHorarios();
        grafoUniversidade.carregarPontos3D();
        //   grafoUniversidade.imprimeTodosPontos3D();
        grafoUniversidade.carregarDirectedEdgeVersaoEditada();
        //     grafoUniversidade.imprimeTodasDirectedEdgeVersaoEditada();

    }

    public void gravarBin() {
        File f = new File("C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosBin\\DadosBin.bin");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(bd);
            oos.writeObject(grafoUniversidade);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void carregarBin() {
        File f = new File("C:\\Users\\Rui Pinto\\Documents\\GitHub\\projecto\\src\\DadosBin\\DadosBin.bin");
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            bd = (BaseDados) ois.readObject();
            grafoUniversidade = (GrafoUniversidadeV2) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handlerGravarTotalBin(ActionEvent actionEvent) {
        gravarBin();
    }

    public void handlerGravarTotal(ActionEvent actionEvent) {
        bd.gravarHistoricoAlunos();
        bd.gravarHistoricoProfessores();
        bd.gravarHistoricoUc();
        bd.gravarHistoricoSalas();
        bd.gravarHistoricoTurmas();
        bd.gravarHistoricoAtendimentos();
        bd.gravarHistoricoCursos();
        bd.gravarAlunos();
        bd.gravarProfessores();
        bd.gravarUc();
        bd.gravarSalas();
        bd.gravarTurmas();
        bd.gravarHorarios();
        bd.gravarCursos();
    }

    public void handlerCarregamentoTotalBin(ActionEvent actionEvent) {
        carregarBin();
    }

    /**
     * Grafos
     */


    @FXML
    private ComboBox<Integer> cbtabGrafosPiso;

    @FXML
    private ComboBox<Ponto3D> cbtabGrafosOrigem;

    @FXML
    private ComboBox<String> cbtabSubGrafosPiso;

    @FXML
    private ComboBox<Ponto3D> cbtabGrafosDestino;
    @FXML
    private ComboBox<Ponto3D> cbtabGrafosPontoDestino;
    @FXML
    private ComboBox<Ponto3D> tFTabSubGrafosEvitarPontos;

    @FXML
    private TextField tFtabGrafosX;

    @FXML
    private TextField tFtabGrafosY;


    @FXML
    private TextField tFtabGrafosConexo;

    @FXML
    private TextField tFtabGrafosDistancia;

    @FXML
    private TextField tFtabGrafosTempo;

    @FXML
    private TextField tFtabGrafosVertice;

    @FXML
    private TableView<Ponto3D> tVtabGrafos;

    @FXML
    private TableColumn<Ponto3D, Integer> tVtabGrafosId;

    @FXML
    private TableColumn<Ponto3D, String> tVtabGrafosDescricao;

    @FXML
    private TableColumn<Ponto3D, String> tVtabGrafosCoordenadas;

    @FXML
    private TextArea tAtabGrafos;

    @FXML
    private Group gtabGrafos;
    @FXML
    private Group groupGrafoDesenho;


    @FXML
    void handleApresentaDadosCBGrafos(Event event) {
        preencheCBPisoGrafos();
        auxiliarGrafoSet = new ArrayList<>();
    }


    @FXML
    void handlerCriarTabSubGrafosEvitarPontos(ActionEvent event) {
        if (auxiliarGrafoSet.size() > 0) {
            grafoUniversidade.resetSubARestasVertices();
            grafoUniversidade.setListaDirectedEdgeVersaoEditadaSubgrafo(new ArrayList<>());
            for (Ponto3D p : auxiliarGrafoSet) {
                Iterator<DirectedEdgeVersaoEditada> x = grafoUniversidade.getListaDirectedEdgeVersaoEditadaSubgrafo().iterator();

                while (x.hasNext()) {

                    DirectedEdgeVersaoEditada d = (DirectedEdgeVersaoEditada) x.next();

                    if (d.getV() == p.getId() || d.getW() == p.getId()) {

                        x.remove();

                    }
                }
            }

            grafoUniversidade.setListaPonto3DSubGrafo(grafoUniversidade.getListaPonto3DGrafo());
            RedBlackBST_AED2<Integer, Integer> mapVertices = new RedBlackBST_AED2<>();
            for (Ponto3D p : auxiliarGrafoSet) {
                Iterator<Ponto3D> x = grafoUniversidade.getListaPonto3DSubGrafo().iterator();

                while (x.hasNext()) {

                    Ponto3D d = (Ponto3D) x.next();

                    if (p.getId() == d.getId()) {

                        x.remove();

                    }
                }
            }
            int i = 0;
            for (Ponto3D p : auxiliarGrafoSet) {
                if (!mapVertices.contains(p.getId())) {
                    mapVertices.put(p.getId(), i);
                    i++;
                }

            }
            for (Integer v : mapVertices.keys()) {
                grafoUniversidade.mudaValorVertice(mapVertices.get(v), v);
            }

            for (Integer v : mapVertices.keys()) {
                grafoUniversidade.mudaValorArestasV(mapVertices.get(v), v);
            }

            for (DirectedEdgeVersaoEditada p : grafoUniversidade.getListaDirectedEdgeVersaoEditadaSubgrafo()) {
                grafoUniversidade.getSubGrafo().addEdge(p);
            }
            grafoConexo();
            desenharGrafoSubGrafo();
        }
        grafoConexo();
        desenharGrafo();
    }

    @FXML
    void handlerAdicionarTabSubGrafosEvitarPontos(ActionEvent event) {
        if (tFTabSubGrafosEvitarPontos.getValue() != null && !auxiliarGrafoSet.contains(tFTabSubGrafosEvitarPontos.getValue())) {
            auxiliarGrafoSet.add(tFTabSubGrafosEvitarPontos.getValue());
        }
    }

    @FXML
    void handlerCriarTabSubGrafosEmergencia(ActionEvent event) {
        tAtabGrafos.clear();

        int j = 0;
        if (!tFtabGrafosX.getText().isEmpty() && !tFtabGrafosY.getText().isEmpty() && cbtabGrafosPiso.getValue() != null) {
            Ponto3D ponto3D = new Ponto3D(Double.parseDouble(tFtabGrafosX.getText()), Double.parseDouble(tFtabGrafosY.getText()),
                    cbtabGrafosPiso.getValue(), null, null, null);
            Ponto3D aux = grafoUniversidade.retornaPontoMaisProximo(ponto3D);

            ArrayList<Ponto3D> saida = grafoUniversidade.outdoor(); // retorna as saidas

            DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(grafoUniversidade.getSubGrafo(), aux.getId()); // aplica dijkstraSP

            Ponto3D auxReferencia = saida.get(0); // vai guardar o final o vertice final com menor distancia

            double dist = 100000000.00;

            for (Ponto3D p : saida) {
                if (sp.hasPathTo(p.getId())) {
                    if (sp.distTo(p.getId()) < dist) {
                        dist = sp.distTo(p.getId());
                        auxReferencia = p;
                    }
                }
            }
            StringBuilder paraTextArea = new StringBuilder();
            StdOut.printf("%d to %d (%.2f)  \n", aux.getId(), auxReferencia.getId(), sp.distTo(auxReferencia.getId()));
            paraTextArea = new StringBuilder(aux.getId() + " to" + auxReferencia.getId() + "%d " + sp.distTo(auxReferencia.getId()) + " \n");
            for (DirectedEdgeVersaoEditada e : sp.pathTo(auxReferencia.getId())) {
                j++;
                BigDecimal bd = new BigDecimal(e.weight()).setScale(2, RoundingMode.HALF_EVEN);
                StdOut.print(j + "º ->" + " " + e.getV() + "->" + e.getW()
                        + " com distancia de " + bd + "\n");
                paraTextArea.append(j).append("º -> ").append(e.getV()).append(" -> ").append(e.getW()).append(" com distancia de").append(bd).append(" \n");


            }
            System.out.println("\n" + auxReferencia.getDescricao());
            tAtabGrafos.appendText(paraTextArea.toString());

        }

    }

    @FXML
    void handlertabGrafosTempo(ActionEvent event) {
        tAtabGrafos.clear();
        tFtabGrafosDistancia.clear();
        if (!tFtabGrafosX.getText().isEmpty() && !tFtabGrafosY.getText().isEmpty() && cbtabGrafosPiso.getValue() != null) {
            Ponto3D ponto3D = new Ponto3D(Double.parseDouble(tFtabGrafosX.getText()), Double.parseDouble(tFtabGrafosY.getText()),
                    cbtabGrafosPiso.getValue(), -1, null, null);
            grafoUniversidade.copiaGrafoOriginalParaSubGrafo();
            Ponto3D aux = grafoUniversidade.retornaPontoMaisProximo(ponto3D);
            Double distanciaFinal = aux.distPontos(ponto3D);
            //Double tempoFinal = aux.distPontos(ponto3D);
            StringBuilder paraTextArea = new StringBuilder();
            DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(grafoUniversidade.getSubGrafo(), aux.getId());
            if (cbtabGrafosPontoDestino.getValue() != null) {
                if (sp.hasPathTo(cbtabGrafosPontoDestino.getValue().getId())) {
                    BigDecimal bd = new BigDecimal(sp.distTo(cbtabGrafosPontoDestino.getValue().getId())).setScale(2, RoundingMode.HALF_EVEN);
                    StdOut.printf("%d to %d (%.2f)  ", aux.getId(), cbtabGrafosPontoDestino.getValue().getId(), bd);
                    paraTextArea = new StringBuilder(aux.getId() + " to " + cbtabGrafosPontoDestino.getValue().getId() + " (" + bd + ")\n");
                    for (DirectedEdgeVersaoEditada e : sp.pathTo(cbtabGrafosPontoDestino.getValue().getId())) {
                        BigDecimal bd1 = new BigDecimal(e.getTempo()).setScale(2, RoundingMode.HALF_EVEN);

                        StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + bd1 + " segundos ");
                        BigDecimal bd2 = new BigDecimal(e.getTempo()).setScale(2, RoundingMode.HALF_EVEN);

                        paraTextArea.append(e.getV()).append("->").append(e.getW()).append(" com distancia de ").append(bd2).append(" segundos \n");
                        distanciaFinal += e.getTempo();
                    }
                    StdOut.println();
                }
            }
            BigDecimal bd3 = new BigDecimal(distanciaFinal).setScale(2, RoundingMode.HALF_EVEN);

            tAtabGrafos.appendText("A distancia do ponto " + ponto3D.getId() + " até " + cbtabGrafosPontoDestino.getValue().getId() + "tem a distancia total de " + bd3 + " percorrendo este caminho :\n");
            tAtabGrafos.appendText(paraTextArea.toString());
            tFtabGrafosDistancia.setText(String.valueOf(bd3));
        }
    }

    @FXML
    void handlertabGrafosDistanciaOD(ActionEvent event) {
        tAtabGrafos.clear();

        if (cbtabGrafosOrigem.getValue() != null && cbtabGrafosDestino.getValue() != null) {
            Ponto3D pontoOrigem = grafoUniversidade.retornaNo(cbtabGrafosOrigem.getValue().getId());
            Ponto3D pontoDestino = grafoUniversidade.retornaNo(cbtabGrafosDestino.getValue().getId());
            System.out.println(pontoOrigem);
            System.out.println(pontoDestino);
            Double distanciaFinal = pontoOrigem.distPontos(pontoDestino);
            StringBuilder paraTextArea = new StringBuilder();
            DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(grafoUniversidade.getSubGrafo(), pontoOrigem.getId());
            if (cbtabGrafosDestino.getValue() != null) {
                if (sp.hasPathTo(pontoDestino.getId())) {
                    StdOut.printf("%d to %d (%.2f)  ", pontoOrigem.getId(), pontoDestino.getId(), sp.distTo(pontoDestino.getId()));
                    paraTextArea = new StringBuilder(pontoOrigem.getId() + " to " + pontoDestino.getId() + " (" + sp.distTo(pontoDestino.getId()) + ")\n");
                    for (DirectedEdgeVersaoEditada e : sp.pathTo(pontoDestino.getId())) {
                        StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + e.weight() + " segundos ");
                        paraTextArea.append(e.getV()).append("->").append(e.getW()).append(" com distancia de ").append(e.weight()).append(" segundos \n");
                        distanciaFinal += e.weight();
                    }
                    StdOut.println();
                }
            }
            tAtabGrafos.appendText("A distancia do ponto" + pontoOrigem.getId() + " até " + pontoDestino.getId() + "tem a distancia total de " + distanciaFinal + " percorrendo este caminho :\n");
            tAtabGrafos.appendText(paraTextArea.toString());

        }
    }

    @FXML
    void handlertabGrafosTempoOD(ActionEvent event) {
        tAtabGrafos.clear();

        if (cbtabGrafosOrigem.getValue() != null && cbtabGrafosDestino.getValue() != null) {
            Ponto3D pontoOrigem = grafoUniversidade.retornaNo(cbtabGrafosOrigem.getValue().getId());
            Ponto3D pontoDestino = grafoUniversidade.retornaNo(cbtabGrafosDestino.getValue().getId());
            Double distanciaFinal = pontoOrigem.distPontos(pontoDestino);
            StringBuilder paraTextArea = new StringBuilder();
            DijkstraSPVersaoEditadaTempo sp = new DijkstraSPVersaoEditadaTempo(grafoUniversidade.getSubGrafo(), pontoOrigem.getId());
            if (cbtabGrafosDestino.getValue() != null) {
                if (sp.hasPathTo(pontoDestino.getId())) {
                    StdOut.printf("%d to %d (%.2f)  ", pontoOrigem.getId(), pontoDestino.getId(), sp.distTo(pontoDestino.getId()));
                    paraTextArea = new StringBuilder(pontoOrigem.getId() + " to " + pontoDestino.getId() + " (" + sp.distTo(pontoDestino.getId()) + ")\n");
                    for (DirectedEdgeVersaoEditada e : sp.pathTo(pontoDestino.getId())) {
                        StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + e.getTempo() + " segundos ");
                        paraTextArea.append(e.getV()).append("->").append(e.getW()).append(" com distancia de ").append(e.getTempo()).append(" segundos \n");
                        distanciaFinal += e.getTempo();
                    }
                    StdOut.println();
                }
            }
            tAtabGrafos.appendText("A distancia do ponto" + pontoOrigem.getId() + " até " + pontoDestino.getId() + "tem a distancia total de " + distanciaFinal + " percorrendo este caminho :\n");
            tAtabGrafos.appendText(paraTextArea.toString());

        }
    }

    @FXML
    void handlertabGrafosDistancia(ActionEvent event) {
        tAtabGrafos.clear();
        tFtabGrafosDistancia.clear();
        if (!tFtabGrafosX.getText().isEmpty() && !tFtabGrafosY.getText().isEmpty() && cbtabGrafosPiso.getValue() != null) {
            Ponto3D ponto3D = new Ponto3D(Double.parseDouble(tFtabGrafosX.getText()), Double.parseDouble(tFtabGrafosY.getText()),
                    cbtabGrafosPiso.getValue(), -1, null, null);
            grafoUniversidade.copiaGrafoOriginalParaSubGrafo();
            Ponto3D aux = grafoUniversidade.retornaPontoMaisProximo(ponto3D);
            Double distanciaFinal = aux.distPontos(ponto3D);
            //Double tempoFinal = aux.distPontos(ponto3D);
            StringBuilder paraTextArea = new StringBuilder();
            DijkstraSPVersaoEditadaWeight sp = new DijkstraSPVersaoEditadaWeight(grafoUniversidade.getSubGrafo(), aux.getId());
            if (cbtabGrafosPontoDestino.getValue() != null) {
                if (sp.hasPathTo(cbtabGrafosPontoDestino.getValue().getId())) {
                    BigDecimal bd = new BigDecimal(sp.distTo(cbtabGrafosPontoDestino.getValue().getId())).setScale(2, RoundingMode.HALF_EVEN);
                    StdOut.printf("%d to %d (%.2f)  ", aux.getId(), cbtabGrafosPontoDestino.getValue().getId(), bd);
                    paraTextArea = new StringBuilder(aux.getId() + " to " + cbtabGrafosPontoDestino.getValue().getId() + " (" + bd + ")\n");
                    for (DirectedEdgeVersaoEditada e : sp.pathTo(cbtabGrafosPontoDestino.getValue().getId())) {
                        BigDecimal bd1 = new BigDecimal(e.weight()).setScale(2, RoundingMode.HALF_EVEN);

                        StdOut.print(e.getV() + "->" + e.getW() + " com distancia de " + bd1 + " segundos ");
                        BigDecimal bd2 = new BigDecimal(e.weight()).setScale(2, RoundingMode.HALF_EVEN);

                        paraTextArea.append(e.getV()).append("->").append(e.getW()).append(" com distancia de ").append(bd2).append(" segundos \n");
                        distanciaFinal += e.weight();
                    }
                    StdOut.println();
                }
            }
            BigDecimal bd3 = new BigDecimal(distanciaFinal).setScale(2, RoundingMode.HALF_EVEN);

            tAtabGrafos.appendText("A distancia do ponto " + ponto3D.getId() + " até " + cbtabGrafosPontoDestino.getValue().getId() + "tem a distancia total de " + bd3 + " percorrendo este caminho :\n");
            tAtabGrafos.appendText(paraTextArea.toString());
            tFtabGrafosDistancia.setText(String.valueOf(bd3));
        }
    }

    public void handlerPreencherDestinoGrafo(ActionEvent actionEvent) {
        tAtabGrafos.clear();
        cbtabGrafosDestino.getItems().clear();
        if (cbtabGrafosOrigem.getValue() != null) {
            for (Ponto3D p : grafoUniversidade.getListaPonto3DSubGrafo()) {
                if (p.compareTo(cbtabGrafosOrigem.getValue()) != 0) {
                    cbtabGrafosDestino.getItems().add(p);
                }
            }
        }


    }

    void preencheCBPisoGrafos() {
        grafoUniversidade.copiaGrafoOriginalParaSubGrafo();
        cbtabGrafosPontoDestino.getItems().clear();
        cbtabGrafosPiso.getItems().clear();
        cbtabGrafosOrigem.getItems().clear();
        cbtabGrafosDestino.getItems().clear();
        cbtabSubGrafosPiso.getItems().clear();
        tFTabSubGrafosEvitarPontos.getItems().clear();
        for (Ponto3D p : grafoUniversidade.getListaPonto3DSubGrafo()) {

            cbtabGrafosOrigem.getItems().add(p);

        }

        for (Integer p : grafoUniversidade.getDistinctExistingFloors()) {

            cbtabGrafosPiso.getItems().add(p);

        }
        cbtabSubGrafosPiso.getItems().addAll("Todos");
        for (Integer p : grafoUniversidade.getDistinctExistingFloors()) {

            cbtabSubGrafosPiso.getItems().add(String.valueOf(p));

        }

        for (Ponto3D p : grafoUniversidade.getListaPonto3DGrafo()) {

            cbtabGrafosPontoDestino.getItems().add(p);

        }

        for (Ponto3D p : grafoUniversidade.getListaPonto3DGrafo()) {

            tFTabSubGrafosEvitarPontos.getItems().add(p);

        }


    }

    @FXML
    void handlertabSubGrafosPiso(ActionEvent event) {
        cbtabSubGrafosPiso.getValue();
        if (cbtabSubGrafosPiso.getValue().compareTo("Todos") == 0) {
            grafoUniversidade = new GrafoUniversidadeV2();
            grafoUniversidade.carregarPontos3D();
            grafoUniversidade.carregarDirectedEdgeVersaoEditada();
            grafoUniversidade.copiaGrafoOriginalParaSubGrafo();
            grafoConexo();
            desenharGrafo();
        } else {
            Integer piso = Integer.parseInt(cbtabSubGrafosPiso.getValue());
            //   System.out.println("Piso ->>>>"+ piso);
            grafoUniversidade.resetSubARestasVertices();
            grafoUniversidade.setListaDirectedEdgeVersaoEditadaSubgrafo(grafoUniversidade.getEdgeByFloor(piso));
            grafoUniversidade.setListaPonto3DSubGrafo(grafoUniversidade.getPointsByFloor(piso));
            RedBlackBST_AED2<Integer, Integer> mapVertices = grafoUniversidade.mapeamento_grafo(piso);
            grafoUniversidade.setSubGrafo(grafoUniversidade.getPointsByFloor(piso).size());

            for (Integer v : mapVertices.keys()) {
                grafoUniversidade.mudaValorVertice(mapVertices.get(v), v);
            }

            for (Integer v : mapVertices.keys()) {
                grafoUniversidade.mudaValorArestasV(mapVertices.get(v), v);
            }

            for (DirectedEdgeVersaoEditada p : grafoUniversidade.getListaDirectedEdgeVersaoEditadaSubgrafo()) {
                grafoUniversidade.getSubGrafo().addEdge(p);
            }
            grafoConexo();
            desenharGrafoSubGrafo();
        }

    }

    void desenharGrafo() {

        int escala = 220;

        gtabGrafos.getChildren().clear();
        groupGrafoDesenho.getChildren().clear();
        for (int i = 0; i < grafoUniversidade.getSubGrafo().V(); i++) {
            Ponto3D aux = grafoUniversidade.getListaPonto3DSubGrafo().get(grafoUniversidade.retornaValorNovoVertice(i));
            System.out.println(aux);
            Circle c = new Circle(aux.getX() + (escala * aux.getZ()), aux.getY(), radius);
            if (aux.getIndoor()) {
                c.setFill(Color.WHITE);
            } else {
                c.setFill(Color.RED);
            }
            Text text = new Text(String.valueOf(i));
            text.setX((aux.getX()) + (escala * aux.getZ()) - radius / 2);
            text.setY((aux.getY()) + radius / 2);


            gtabGrafos.getChildren().addAll(c, text);
            groupGrafoDesenho.getChildren().addAll(c, text);

            for (DirectedEdgeVersaoEditada ep : grafoUniversidade.getSubGrafo().adj(i)) {
                Ponto3D aux1 = grafoUniversidade.getListaPonto3DSubGrafo().get(grafoUniversidade.retornaValorNovoVertice(ep.getW()));
           /*     Circle d = new Circle(aux.getX() + (escala * aux1.getZ()), aux.getY(), radius);
                if (aux.getIndoor()) {
                    d.setFill(Color.WHITE);
                } else {
                    d.setFill(Color.RED);
                }
                Text textD = new Text(String.valueOf(i));
                textD.setX((aux1.getX()) + (escala * aux1.getZ()) - radius / 2);
                textD.setY((aux1.getY()) + radius / 2);*/

                Line line = new Line(aux.getX() + (escala * aux.getZ()), aux.getY(), aux1.getX() + (escala * aux1.getZ()), aux1.getY());
                line.setStroke(Color.BLACK);

                if (aux.getZ() != aux1.getZ())
                    line.setStroke(Color.GREEN);
                line.setStrokeWidth(1);
                System.out.println(c);
                //System.out.println(d);
                System.out.println(text);
                //   System.out.println(textD);

                gtabGrafos.getChildren().add(line/*, d, textD*/);
                groupGrafoDesenho.getChildren().add(line/*, d, textD*/);
            }
        }
    }

    void desenharGrafoSubGrafo() {
        System.out.println("Arestas ");
        for (DirectedEdgeVersaoEditada d : grafoUniversidade.getListaDirectedEdgeVersaoEditadaSubgrafo())
            System.out.println(d);
        System.out.println("Vertices");
        for (Ponto3D p : grafoUniversidade.getListaPonto3DSubGrafo())
            System.out.println(p);


        int escala = 220;

        gtabGrafos.getChildren().clear();
        groupGrafoDesenho.getChildren().clear();
        for (int i = 0; i < grafoUniversidade.getSubGrafo().V(); i++) {
            Ponto3D aux = grafoUniversidade.getListaPonto3DSubGrafo().get(grafoUniversidade.retornaValorNovoVerticeSubGrafo(i));
            // System.out.println(aux);
            Circle c = new Circle(aux.getX() + (escala * aux.getZ()), aux.getY(), radius);
            if (aux.getIndoor()) {
                c.setFill(Color.WHITE);
            } else {
                c.setFill(Color.RED);
            }
            Text text = new Text(String.valueOf(i));
            text.setX((aux.getX()) + (escala * aux.getZ()) - radius / 2);
            text.setY((aux.getY()) + radius / 2);


            gtabGrafos.getChildren().addAll(c, text);
            groupGrafoDesenho.getChildren().addAll(c, text);

            for (DirectedEdgeVersaoEditada ep : grafoUniversidade.getSubGrafo().adj(i)) {
                if (null != grafoUniversidade.getListaPonto3DSubGrafo().get(grafoUniversidade.retornaValorNovoVerticeSubGrafo(ep.getW()))) {
                    Ponto3D aux1 = grafoUniversidade.getListaPonto3DSubGrafo().get(grafoUniversidade.retornaValorNovoVerticeSubGrafo(ep.getW()));
           /*     Circle d = new Circle(aux.getX() + (escala * aux1.getZ()), aux.getY(), radius);
                if (aux.getIndoor()) {
                    d.setFill(Color.WHITE);
                } else {
                    d.setFill(Color.RED);
                }
                Text textD = new Text(String.valueOf(i));
                textD.setX((aux1.getX()) + (escala * aux1.getZ()) - radius / 2);
                textD.setY((aux1.getY()) + radius / 2);*/

                    Line line = new Line(aux.getX() + (escala * aux.getZ()), aux.getY(), aux1.getX() + (escala * aux1.getZ()), aux1.getY());
                    line.setStroke(Color.BLACK);

                    if (aux.getZ() != aux1.getZ())
                        line.setStroke(Color.GREEN);
                    line.setStrokeWidth(1);
                    //       System.out.println(c);
                    //System.out.println(d);
                    //     System.out.println(text);
                    //   System.out.println(textD);

                    gtabGrafos.getChildren().add(line/*, d, textD*/);
                    groupGrafoDesenho.getChildren().add(line/*, d, textD*/);
                }
            }
        }
        preencheCBPisoGrafos();
    }


    void grafoConexo() {
        tFtabGrafosConexo.clear();
        tFtabGrafosConexo.setText(grafoUniversidade.grafoConexo(grafoUniversidade.getSubGrafo()));
    }


    /**
     * LISTAGENS GRAFOS
     */

    @FXML
    private TextField grafosListagemX;

    @FXML
    private TextField grafosListagemY;

    @FXML
    private TextField grafosListagemDescricao;

    @FXML
    private ComboBox<Integer> grafosListagemPiso;

    @FXML
    private ComboBox<String> grafosListagemIndoor;

    @FXML
    private ComboBox<Ponto3D> grafosListagemCbO;

    @FXML
    private ComboBox<Ponto3D> grafosListagemCbD;


    @FXML
    private TableColumn<Ponto3D, String> grafosListagemTvDescricao;


    @FXML
    private TableView<Ponto3D> grafosListagemTV;

    @FXML
    private TableColumn<Ponto3D, Integer> grafosListagemID;

    @FXML
    private TableColumn<Ponto3D, Double> grafosListagemXTv;

    @FXML
    private TableColumn<Ponto3D, Double> grafosListagemYTv;

    @FXML
    private TableColumn<Ponto3D, Integer> grafosListagemZTv;

    @FXML
    private TableView<DirectedEdgeVersaoEditada> grafosListagemTvArestas;

    @FXML
    private TableColumn<DirectedEdgeVersaoEditada, Integer> grafosListagemTvArestaV;

    @FXML
    private TableColumn<DirectedEdgeVersaoEditada, Integer> grafosListagemTvArestaW;

    @FXML
    private TableColumn<DirectedEdgeVersaoEditada, Double> grafosListagemTvArestaWeight;

    @FXML
    private TableColumn<DirectedEdgeVersaoEditada, Double> grafosListagemTvArestaTempo;


    public void handleApresentaDadosCBGrafosListagem(Event event) {
        preencherTvGrafoListagens();
    }

    public void handleApresentaDadosCBGrafosLst(Event event) {
    }

    public void handlerRemoverVertice(ActionEvent actionEvent) {
        Ponto3D aux = grafosListagemTV.getSelectionModel().getSelectedItem();
        grafoUniversidade.removePoints(aux);
        preencherTvGrafoListagens();
    }

    public void handlerRemoverArestas(ActionEvent actionEvent) {
        if (grafosListagemTvArestas.getSelectionModel().getSelectedItem() != null) {
            Iterator<DirectedEdgeVersaoEditada> x = grafoUniversidade.getListaDirectedEdgeVersaoEditadaGrafo().iterator();

            while (x.hasNext()) {

                DirectedEdgeVersaoEditada d = (DirectedEdgeVersaoEditada) x.next();

                if (d.getV() == grafosListagemTvArestas.getSelectionModel().getSelectedItem().getV() || d.getW() == grafosListagemTvArestas.getSelectionModel().getSelectedItem().getW()) {

                    x.remove();

                }
            }
        }
        preencherTvGrafoListagens();

    }

    public void handlergrafosListagemAdicionarVertice(ActionEvent actionEvent) {
        Double x = Double.parseDouble(grafosListagemX.getText());
        Double y = Double.parseDouble(grafosListagemY.getText());
        Integer i = grafosListagemPiso.getValue();
        String descricao = grafosListagemDescricao.getText();
        Boolean aux;
        if (grafosListagemIndoor.getValue().compareTo("Sim") == 0)
            aux = true;
        else
            aux = false;
        Ponto3D ponto = new Ponto3D(x, y, i, descricao, aux);

        grafoUniversidade.adicionarPonto3d(ponto);
        preencherTvGrafoListagens();
        grafosListagemY.clear();
        grafosListagemX.clear();
        grafosListagemDescricao.clear();
    }

    public void handlerAdicionarArestas(ActionEvent actionEvent) {
        if (grafosListagemCbO.getValue() != null && grafosListagemCbD.getValue() != null) {
            grafoUniversidade.adicionarArestaBidirecional(grafosListagemCbO.getValue(), grafosListagemCbD.getValue(), true, true);
        }
        preencherTvGrafoListagens();

    }

    public void preencherTvGrafoListagens() {
        grafosListagemCbO.getItems().clear();
        grafosListagemCbD.getItems().clear();
        grafosListagemTV.getItems().clear();
        grafosListagemTvArestas.getItems().clear();

        if (grafoUniversidade.getListaPonto3DGrafo().size() > 0 && grafoUniversidade.getListaDirectedEdgeVersaoEditadaGrafo().size() > 0) {
            for (Ponto3D p : grafoUniversidade.getListaPonto3DGrafo()) {
                grafosListagemTV.getItems().add(p);
            }
            for (DirectedEdgeVersaoEditada d : grafoUniversidade.getListaDirectedEdgeVersaoEditadaGrafo()) {
                grafosListagemTvArestas.getItems().add(d);
            }
            for (Ponto3D p : grafoUniversidade.getListaPonto3DGrafo()) {
                grafosListagemCbO.getItems().add(p);
            }
            for (Ponto3D p : grafoUniversidade.getListaPonto3DGrafo()) {
                grafosListagemCbD.getItems().add(p);
            }
        }
        grafosListagemPiso.getItems().clear();
        for (int i = 1; i <= 3; i++)
            grafosListagemPiso.getItems().add(i);
        grafosListagemIndoor.getItems().clear();
        grafosListagemIndoor.getItems().addAll("Sim", "Nao");


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /**CURSO*/
        tVTabCursoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTabCursoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tVTabCursoNrUc.setCellValueFactory(new PropertyValueFactory<>("numeroDeTurmas"));

        /**TURMA*/
        tVTabUcUCnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tVTabUcUCano.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tVTabUcUCsemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        /** TURMAS*/

        tVTabUcTurmaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tVTabUcTurmaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tVTabUcTurmaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));

        tVTabTurmasTurmaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTabTurmasTurmaNome.setCellValueFactory(new PropertyValueFactory<>("unidadeCurricular"));
        tVTabTurmasTurmaProfessor.setCellValueFactory(new PropertyValueFactory<>("professor"));

        /** PROFESSOR */
        tVTabProfessorId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTabProfessorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tVTabProfessorDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tVTabProfessorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tVTabProfessorNumeroTurmas.setCellValueFactory(new PropertyValueFactory<>("numeroTurmas"));

        /** ALUNO */
        tVTabAlunoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTabAlunoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tVTabAlunoDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tVTabAlunoEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tVTabAlunoCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tVTabAlunoUnidadesInscritos.setCellValueFactory(new PropertyValueFactory<>("numeroCadeirasInscritas"));

        /** Sala */
        tVTabSalaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTabSalaLotacao.setCellValueFactory(new PropertyValueFactory<>("locatacao"));
        tVTabSalaTomadas.setCellValueFactory(new PropertyValueFactory<>("tomadas"));
        tVTabSalaPiso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tVTabSalaProjecto.setCellValueFactory(new PropertyValueFactory<>("projector"));
        tVTabSalaZ.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tVTabSalaY.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getY()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabSalaX.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getX()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        /**Horario*/

        tVTabHorarioId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getTurma().getId()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabHorarioCadeira.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getTurma().getUnidadeCurricular().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabHorarioSala.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getSala().getId()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabHorarioHoraInicio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getHoraInicio()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabHorarioHoraFim.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getHoraFim()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabHorariorDiaSemana.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Horario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Horario, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(preencherDiaSemanaPT(p.getValue().getDia())));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        /**ATENDIMENTO */

        tVTabAtendimentoProfessor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getProfessor().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabAtendimentoHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getHorario()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTabAtendimentoAluno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue().getAluno() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getAluno().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        /**TURMA ALUNO */

        tVTurmaAlunoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVTurmaAlunoUc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Turma, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Turma, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getUnidadeCurricular().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVTurmaAlunoProfessor.setCellValueFactory(new PropertyValueFactory<>("professor"));


        /**PESQUISA SALAS*/

        tVPesquisaSalasLivreId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVPesquisaSalasLivreLotacao.setCellValueFactory(new PropertyValueFactory<>("locatacao"));
        tVPesquisaSalasLivreTomadas.setCellValueFactory(new PropertyValueFactory<>("tomadas"));
        tVPesquisaSalasLivrePiso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tVPesquisaSalasLivreProjector.setCellValueFactory(new PropertyValueFactory<>("projector"));
        tVPesquisaSalasLivreX.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getX()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaSalasLivreY.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getY()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        tVPesquisaSalasOcupadasId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVPesquisaSalasOcupadasLotacao.setCellValueFactory(new PropertyValueFactory<>("locatacao"));
        tVPesquisaSalasOcupadasTomadas.setCellValueFactory(new PropertyValueFactory<>("tomadas"));
        tVPesquisaSalasOcupadasPiso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tVPesquisaSalasOcupadasProjector.setCellValueFactory(new PropertyValueFactory<>("projector"));
        tVPesquisaSalasOcupadasX.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getX()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaSalasOcupadasY.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getY()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        /**PESQUISA PROFESSOR*/

        tVPesquisaUCProfessorIDTurma.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVPesquisaUCProfessorID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Turma, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Turma, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getProfessor().getId()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaUCProfessorProfessor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Turma, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Turma, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getProfessor().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaProfessorProfessorUc.setCellValueFactory(new PropertyValueFactory<>("nome"));

        /** PESQUISA ATENDIMENTO */

        tVPesquisaAtendimentoProfessor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getProfessor().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaAtendimentoHorario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getHorario()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaAtendimentoAluno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Atendimento, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Atendimento, String> p) {
                if (p.getValue().getAluno() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getAluno().getNome()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });


        /** Pesquisa tipo de salas */

        tVPesquisaTipoSalaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tVPesquisaTipoSalaLotacao.setCellValueFactory(new PropertyValueFactory<>("locatacao"));
        tVPesquisaTipoSalaTomados.setCellValueFactory(new PropertyValueFactory<>("tomadas"));
        tVPesquisaTipoSalaPiso.setCellValueFactory(new PropertyValueFactory<>("piso"));
        tVPesquisaTipoSalaProjector.setCellValueFactory(new PropertyValueFactory<>("projector"));
        tVPesquisaTipoSalaX.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getX()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        tVPesquisaTipoSalaY.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sala, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sala, String> p) {
                if (p.getValue() != null) {
                    return new SimpleStringProperty(String.valueOf(p.getValue().getPonto().getY()));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });

        /**
         * Grafos
         */
        grafosListagemID.setCellValueFactory(new PropertyValueFactory<>("id"));


        grafosListagemXTv.setCellValueFactory(new PropertyValueFactory<>("x"));


        grafosListagemYTv.setCellValueFactory(new PropertyValueFactory<>("y"));

        grafosListagemZTv.setCellValueFactory(new PropertyValueFactory<>("z"));

        grafosListagemTvArestaV.setCellValueFactory(new PropertyValueFactory<>("v"));


        grafosListagemTvArestaW.setCellValueFactory(new PropertyValueFactory<>("w"));

        grafosListagemTvArestaWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        grafosListagemTvArestaTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));

    }


}