package br.com.trmasolucoes.worktime.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tairo on 23/05/16.
 */
public class Horario implements Parcelable{
    private long id;
    private String diaSemana;
    private String entrada;
    private String almoco;
    private String almocoRetorno;
    private String saida;


    public Horario() {
    }

    public Horario(long id, String diaSemana, String entrada, String almoco, String almocoRetorno, String saida) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.entrada = entrada;
        this.almoco = almoco;
        this.almocoRetorno = almocoRetorno;
        this.saida = saida;
    }

    public Horario(String diaSemana, String entrada, String almoco, String almocoRetorno, String saida) {
        this.diaSemana = diaSemana;
        this.entrada = entrada;
        this.almoco = almoco;
        this.almocoRetorno = almocoRetorno;
        this.saida = saida;
    }

    protected Horario(Parcel in) {
        id = in.readLong();
        diaSemana = in.readString();
        entrada = in.readString();
        almoco = in.readString();
        almocoRetorno = in.readString();
        saida = in.readString();
    }

    public static final Creator<Horario> CREATOR = new Creator<Horario>() {
        @Override
        public Horario createFromParcel(Parcel in) {
            return new Horario(in);
        }

        @Override
        public Horario[] newArray(int size) {
            return new Horario[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getAlmoco() {
        return almoco;
    }

    public void setAlmoco(String almoco) {
        this.almoco = almoco;
    }

    public String getAlmocoRetorno() {
        return almocoRetorno;
    }

    public void setAlmocoRetorno(String almocoRetorno) {
        this.almocoRetorno = almocoRetorno;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(diaSemana);
        dest.writeString(entrada);
        dest.writeString(almoco);
        dest.writeString(almocoRetorno);
        dest.writeString(saida);
    }
}
