package br.com.trmasolucoes.worktime.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tairo on 23/05/16.
 */
public class Empresa implements Parcelable{
    private long id;
    private String nome;
    private Date entrada;
    private Date almoco;
    private Date almocoRetorno;
    private Date saida;


    public Empresa() {
    }

    public Empresa(long id, String nome, Date entrada, Date almoco, Date almocoRetorno, Date saida) {
        this.id = id;
        this.nome = nome;
        this.entrada = entrada;
        this.almoco = almoco;
        this.almocoRetorno = almocoRetorno;
        this.saida = saida;
    }

    protected Empresa(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        entrada = (java.util.Date) in.readSerializable();
        almoco = (java.util.Date) in.readSerializable();
        almocoRetorno = (java.util.Date) in.readSerializable();
        saida = (java.util.Date) in.readSerializable();
    }

    public static final Creator<Empresa> CREATOR = new Creator<Empresa>() {
        @Override
        public Empresa createFromParcel(Parcel in) {
            return new Empresa(in);
        }

        @Override
        public Empresa[] newArray(int size) {
            return new Empresa[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getEntrada() {
        return entrada;
    }

    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    public Date getAlmoco() {
        return almoco;
    }

    public void setAlmoco(Date almoco) {
        this.almoco = almoco;
    }

    public Date getAlmocoRetorno() {
        return almocoRetorno;
    }

    public void setAlmocoRetorno(Date almocoRetorno) {
        this.almocoRetorno = almocoRetorno;
    }

    public Date getSaida() {
        return saida;
    }

    public void setSaida(Date saida) {
        this.saida = saida;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeSerializable(entrada);
        dest.writeSerializable(almoco);
        dest.writeSerializable(almocoRetorno);
        dest.writeSerializable(saida);
    }
}
