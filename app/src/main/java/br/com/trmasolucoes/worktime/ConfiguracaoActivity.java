package br.com.trmasolucoes.worktime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.trmasolucoes.worktime.database.ConfiguracaoDAO;
import br.com.trmasolucoes.worktime.database.HorarioDAO;
import br.com.trmasolucoes.worktime.domain.Configuracao;
import br.com.trmasolucoes.worktime.domain.Horario;

public class ConfiguracaoActivity extends AppCompatActivity {
    private static Button btnSalvarConfig;
    private static Button btnSegEntrada, btnSegAlmoco, btnSegAlmocoRetorno, btnSegSaida, btnSegTotal;
    private static Button btnTerEntrada, btnTerAlmoco, btnTerAlmocoRetorno, btnTerSaida, btnTerTotal;
    private static Button btnQuaEntrada, btnQuaAlmoco, btnQuaAlmocoRetorno, btnQuaSaida, btnQuaTotal;
    private static Button btnQuiEntrada, btnQuiAlmoco, btnQuiAlmocoRetorno, btnQuiSaida, btnQuiTotal;
    private static Button btnSexEntrada, btnSexAlmoco, btnSexAlmocoRetorno, btnSexSaida, btnSexTotal;
    private static Button btnSabEntrada, btnSabAlmoco, btnSabAlmocoRetorno, btnSabSaida, btnSabTotal;
    private static Button btnDomEntrada, btnDomAlmoco, btnDomAlmocoRetorno, btnDomSaida, btnDomTotal;
    private EditText edtEmpresa, edtEmail;
    private RadioButton rdSenhaHabilitar, rdSenhaDesabilitar, rdNotificacaoHabilitar, rdNotificacaoDesabilitar;
    static HorarioDAO horarioDAO;
    static ConfiguracaoDAO configuracaoDAO;
    Configuracao configuracao;
    private static final String TAG = "Script";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalvarConfig = (Button) findViewById(R.id.btnSalvarConfig);
        edtEmpresa = (EditText) findViewById(R.id.edtEmpresa);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        rdSenhaHabilitar = (RadioButton) findViewById(R.id.rd_senha_habilitar);
        rdSenhaDesabilitar= (RadioButton) findViewById(R.id.rd_senha_desabilitar);
        rdNotificacaoHabilitar = (RadioButton) findViewById(R.id.rd_notificacao_habilitar);
        rdNotificacaoDesabilitar = (RadioButton) findViewById(R.id.rd_notificacao_desabilitar);

        inicializarViews();
        horarioDAO = new HorarioDAO(ConfiguracaoActivity.this);
        configuracaoDAO = new ConfiguracaoDAO(ConfiguracaoActivity.this);
        configuracao = configuracaoDAO.getById(1);

        if (configuracao.getEmpresa() != null){
            edtEmpresa.setText(configuracao.getEmpresa());
            edtEmail.setText(configuracao.getEmail());
            if (configuracao.getSenha()){
                rdSenhaHabilitar.setChecked(true);
                rdSenhaDesabilitar.setChecked(false);
            }else {
                rdSenhaHabilitar.setChecked(false);
                rdSenhaDesabilitar.setChecked(true);
            }

            if (configuracao.getNotificacao()){
                rdNotificacaoHabilitar.setChecked(true);
                rdNotificacaoDesabilitar.setChecked(false);
            }else {
                rdNotificacaoHabilitar.setChecked(false);
                rdNotificacaoDesabilitar.setChecked(true);
            }
        }

        /** Salva as configurações feitas no banco de dados */
        assert btnSalvarConfig != null;
        btnSalvarConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuracao = configuracaoDAO.getById(1);
                configuracao.setEmpresa(edtEmpresa.getText().toString());
                configuracao.setEmail(edtEmail.getText().toString());
                configuracao.setSenha(rdSenhaHabilitar.isChecked());
                configuracao.setNotificacao(rdNotificacaoHabilitar.isChecked());

                if(configuracao.getEmpresa().equals("")){
                    Toast.makeText(ConfiguracaoActivity.this, "Preencha a empresa!", Toast.LENGTH_SHORT).show();
                }else if (configuracao.getEmail().equals("")){
                    Toast.makeText(ConfiguracaoActivity.this, "Preencha a email!", Toast.LENGTH_SHORT).show();
                }else {
                    configuracaoDAO.update(configuracao);
                    Toast.makeText(ConfiguracaoActivity.this, "Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /** Insere os registros padrões */
        if (horarioDAO.getAll().isEmpty()){
            Horario horario = new Horario();
            horario.setDiaSemana("Seg");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Ter");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Qua");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Qui");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Sex");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Sab");
            horarioDAO.insert(horario);
            horario.setDiaSemana("Dom");
            horarioDAO.insert(horario);


        }
        //Insere as configurações padrões
        if (configuracaoDAO.getById(1).getEmpresa() == null){
            configuracao.setEmpresa("");
            configuracao.setEmail("");
            configuracao.setSenha(true);
            configuracao.setNotificacao(true);
            configuracaoDAO.insert(configuracao);
        }

        /** Percorro os horarios e mostro na tela os totais */
        calcularTotais(horarioDAO.getAll());

        /** ENTRADA */
        btnSegEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSegTotal.getId());
                bundle.putString("dia", "Seg");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnTerEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnTerTotal.getId());
                bundle.putString("dia", "Ter");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuaEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuaTotal.getId());
                bundle.putString("dia", "Qua");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuiEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuiTotal.getId());
                bundle.putString("dia", "Qui");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSexEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSexTotal.getId());
                bundle.putString("dia", "Sex");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSabEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSabTotal.getId());
                bundle.putString("dia", "Sab");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnDomEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnDomTotal.getId());
                bundle.putString("dia", "Dom");
                bundle.putString("horario", "Entrada");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        /** ALMOÇO */
        btnSegAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSegTotal.getId());
                bundle.putString("dia", "Seg");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnTerAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnTerTotal.getId());
                bundle.putString("dia", "Ter");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuaAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuaTotal.getId());
                bundle.putString("dia", "Qua");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuiAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuiTotal.getId());
                bundle.putString("dia", "Qui");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSexAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSexTotal.getId());
                bundle.putString("dia", "Sex");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSabAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSabTotal.getId());
                bundle.putString("dia", "Sab");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnDomAlmoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnDomTotal.getId());
                bundle.putString("dia", "Dom");
                bundle.putString("horario", "Almoco");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        /** RETORNO ALMOÇO */
        btnSegAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSegTotal.getId());
                bundle.putString("dia", "Seg");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnTerAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnTerTotal.getId());
                bundle.putString("dia", "Ter");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuaAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuaTotal.getId());
                bundle.putString("dia", "Qua");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuiAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuiTotal.getId());
                bundle.putString("dia", "Qui");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSexAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSexTotal.getId());
                bundle.putString("dia", "Sex");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSabAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSabTotal.getId());
                bundle.putString("dia", "Sab");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnDomAlmocoRetorno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnDomTotal.getId());
                bundle.putString("dia", "Dom");
                bundle.putString("horario", "AlmocoRetorno");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        /** SAÌDA */
        btnSegSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSegTotal.getId());
                bundle.putString("dia", "Seg");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnTerSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnTerTotal.getId());
                bundle.putString("dia", "Ter");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuaSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuaTotal.getId());
                bundle.putString("dia", "Qua");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnQuiSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnQuiTotal.getId());
                bundle.putString("dia", "Qui");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSexSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSexTotal.getId());
                bundle.putString("dia", "Sex");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnSabSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnSabTotal.getId());
                bundle.putString("dia", "Sab");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        btnDomSaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePiker = new TimePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("view", v.getId());
                bundle.putInt("view_total",btnDomTotal.getId());
                bundle.putString("dia", "Dom");
                bundle.putString("horario", "Saida");
                datePiker.setArguments(bundle);
                datePiker.show(getSupportFragmentManager(), "timePicker");
            }
        });
    }

    /** Chama o dialog com o DatePicker os horarios */
    public void selecionarHorario(View view){
        DialogFragment datePiker = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("view", view.getId());
        datePiker.setArguments(bundle);
        datePiker.show(getSupportFragmentManager(), "timePicker");
    }

    /** Inicializa todos os botões para serem usados na activity */
    public void inicializarViews(){
        btnSegEntrada = (Button) findViewById(R.id.btnSegEntrada);
        btnTerEntrada = (Button) findViewById(R.id.btnTerEntrada);
        btnQuaEntrada = (Button) findViewById(R.id.btnQuaEntrada);
        btnQuiEntrada = (Button) findViewById(R.id.btnQuiEntrada);
        btnSexEntrada = (Button) findViewById(R.id.btnSexEntrada);
        btnSabEntrada = (Button) findViewById(R.id.btnSabEntrada);
        btnDomEntrada = (Button) findViewById(R.id.btnDomEntrada);

        btnSegAlmoco = (Button) findViewById(R.id.btnSegAlmoco);
        btnTerAlmoco = (Button) findViewById(R.id.btnTerAlmoco);
        btnQuaAlmoco = (Button) findViewById(R.id.btnQuaAlmoco);
        btnQuiAlmoco = (Button) findViewById(R.id.btnQuiAlmoco);
        btnSexAlmoco = (Button) findViewById(R.id.btnSexAlmoco);
        btnSabAlmoco = (Button) findViewById(R.id.btnSabAlmoco);
        btnDomAlmoco = (Button) findViewById(R.id.btnDomAlmoco);

        btnSegAlmocoRetorno = (Button) findViewById(R.id.btnSegAlmocoRetorno);
        btnTerAlmocoRetorno = (Button) findViewById(R.id.btnTerAlmocoRetorno);
        btnQuaAlmocoRetorno = (Button) findViewById(R.id.btnQuaAlmocoRetorno);
        btnQuiAlmocoRetorno = (Button) findViewById(R.id.btnQuiAlmocoRetorno);
        btnSexAlmocoRetorno = (Button) findViewById(R.id.btnSexAlmocoRetorno);
        btnSabAlmocoRetorno = (Button) findViewById(R.id.btnSabAlmocoRetorno);
        btnDomAlmocoRetorno = (Button) findViewById(R.id.btnDomAlmocoRetorno);

        btnSegSaida = (Button) findViewById(R.id.btnSegSaida);
        btnTerSaida = (Button) findViewById(R.id.btnTerSaida);
        btnQuaSaida = (Button) findViewById(R.id.btnQuaSaida);
        btnQuiSaida = (Button) findViewById(R.id.btnQuiSaida);
        btnSexSaida = (Button) findViewById(R.id.btnSexSaida);
        btnSabSaida = (Button) findViewById(R.id.btnSabSaida);
        btnDomSaida = (Button) findViewById(R.id.btnDomSaida);

        btnSegTotal = (Button) findViewById(R.id.btnSegTotal);
        btnTerTotal = (Button) findViewById(R.id.btnTerTotal);
        btnQuaTotal = (Button) findViewById(R.id.btnQuaTotal);
        btnQuiTotal = (Button) findViewById(R.id.btnQuiTotal);
        btnSexTotal = (Button) findViewById(R.id.btnSexTotal);
        btnSabTotal = (Button) findViewById(R.id.btnSabTotal);
        btnDomTotal = (Button) findViewById(R.id.btnDomTotal);
    }

    private static void calcularTotais(ArrayList<Horario> horarios){
        for (Horario horario :horarios) {
            if (horario.getDiaSemana().equalsIgnoreCase("Seg")){
                if (horario.getEntrada() != null) {
                    btnSegEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnSegAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnSegAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnSegSaida.setText(horario.getSaida());
                }
                setHorario(btnSegTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Ter")){
                if (horario.getEntrada() != null) {
                    btnTerEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnTerAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnTerAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnTerSaida.setText(horario.getSaida());
                }
                setHorario(btnTerTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Qua")){

                if (horario.getEntrada() != null) {
                    btnQuaEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnQuaAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnQuaAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnQuaSaida.setText(horario.getSaida());
                }

                setHorario(btnQuaTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Qui")){
                if (horario.getEntrada() != null) {
                    btnQuiEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnQuiAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnQuiAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnQuiSaida.setText(horario.getSaida());
                }

                setHorario(btnQuiTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Sex")){
                if (horario.getEntrada() != null) {
                    btnSexEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnSexAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnSexAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnSexSaida.setText(horario.getSaida());
                }

                setHorario(btnSexTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Sab")){
                if (horario.getEntrada() != null) {
                    btnSabEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnSabAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnSabAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnSabSaida.setText(horario.getSaida());
                }

                setHorario(btnSabTotal, horario);
            }
            if (horario.getDiaSemana().equalsIgnoreCase("Dom")){
                if (horario.getEntrada() != null) {
                    btnDomEntrada.setText(horario.getEntrada());
                }
                if (horario.getAlmoco() != null) {
                    btnDomAlmoco.setText(horario.getAlmoco());
                }
                if (horario.getAlmocoRetorno() != null) {
                    btnDomAlmocoRetorno.setText(horario.getAlmocoRetorno());
                }
                if (horario.getSaida() != null) {
                    btnDomSaida.setText(horario.getSaida());
                }

                setHorario(btnDomTotal, horario);
            }
        }
    }

    /** Mostra um data picker pata seleçoã de horário */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), R.style.datepicker, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        /** Pega o id da view enviada pelo getArguments() do fragment */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hora, minuto;
            minuto = (minute < 10)?"0"+minute:""+minute;
            hora = (hourOfDay < 10)?"0"+hourOfDay:""+hourOfDay;

            Toast.makeText(getActivity(), hora +":"+minuto, Toast.LENGTH_SHORT).show();
            Bundle bundle = this.getArguments();
            Button btn = (Button) getActivity().findViewById(bundle.getInt("view"));
            Button btnTotal = (Button) getActivity().findViewById(bundle.getInt("view_total"));

            Horario horario = new Horario();
            horario.setDiaSemana(bundle.getString("dia"));
            if (bundle.getString("horario") != null){
                if (bundle.getString("horario").equalsIgnoreCase("Entrada")) {

                    /** Insere no banco o horario selecionado */
                    horario.setEntrada(hora +":"+minuto);
                    horarioDAO.updateByWeekDay(horario,"entrada", btn.getText().toString());
                    horario = horarioDAO.getByWeekDay(horario.getDiaSemana(),"entrada", horario.getEntrada());
                    btn.setText(horario.getEntrada());

                }else if (bundle.getString("horario").equalsIgnoreCase("Almoco")){

                    /** Insere no banco o horario selecionado */
                    horario.setAlmoco(hora +":"+minuto);
                    horarioDAO.updateByWeekDay(horario, "almoco", btn.getText().toString());
                    horario = horarioDAO.getByWeekDay(horario.getDiaSemana(),"almoco", horario.getAlmoco());
                    btn.setText(horario.getAlmoco());

                }else if (bundle.getString("horario").equalsIgnoreCase("AlmocoRetorno")){

                    /** Insere no banco o horario selecionado */
                    horario.setAlmocoRetorno(hora +":"+minuto);
                    horarioDAO.updateByWeekDay(horario,"almoco_retorno", btn.getText().toString());
                    horario = horarioDAO.getByWeekDay(horario.getDiaSemana(),"almoco_retorno", horario.getAlmocoRetorno());
                    btn.setText(horario.getAlmocoRetorno());

                }else if (bundle.getString("horario").equalsIgnoreCase("Saida")){

                    /** Insere no banco o horario selecionado */
                    horario.setSaida(hora +":"+minuto);
                    horarioDAO.updateByWeekDay(horario,"saida", btn.getText().toString());
                    horario = horarioDAO.getByWeekDay(horario.getDiaSemana(),"saida", horario.getSaida());
                    btn.setText(horario.getSaida());
                }

                /** Calcula o total e colocal no botão */
                ConfiguracaoActivity.calcularTotais(horarioDAO.getByDay(bundle.getString("dia")));
            }
        }
    }

    public static void setHorario(Button btn, Horario horario){
        float entrada = getHorarioFloat(horario.getEntrada());
        float almoco = getHorarioFloat(horario.getAlmoco());
        float almocoRetorno = getHorarioFloat(horario.getAlmocoRetorno());
        float saida = getHorarioFloat(horario.getSaida());
        float total;

        if (entrada > 0 && almoco > 0 && almocoRetorno > 0 && saida > 0){
            total = (saida - almocoRetorno) + (almoco - entrada);

        }else if (entrada > 0 && almoco > 0 && almocoRetorno > 0){
            total = almocoRetorno + (almoco - entrada);

        }else if (entrada > 0 && almoco > 0){
            total = almoco - entrada;

        }else {
            total = 0;
        }
        DecimalFormat df = new DecimalFormat("0.00");

         btn.setText(String.valueOf(df.format(total)));
    }

    private static float getHorarioFloat(String horario){
        if (horario == null){
            return 0;
        }
        return Float.parseFloat(horario.replace(":","."));
    }
}
