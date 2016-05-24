package br.com.trmasolucoes.worktime.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tairo on 23/05/16.
 */
public class Configuracao implements Parcelable{
    private long id;
    private String nome;
    private String valor;

    public Configuracao() {
    }

    public Configuracao(long id, String nome, String valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    protected Configuracao(Parcel in) {
        id = in.readLong();
        nome = in.readString();
        valor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(nome);
        dest.writeString(valor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Configuracao> CREATOR = new Creator<Configuracao>() {
        @Override
        public Configuracao createFromParcel(Parcel in) {
            return new Configuracao(in);
        }

        @Override
        public Configuracao[] newArray(int size) {
            return new Configuracao[size];
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
