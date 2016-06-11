package br.com.trmasolucoes.worktime.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tairo on 21/05/16.
 */
public class Registro implements Parcelable{
    private long id;
    private Date data;
    private Date horario;
    private String tipo;
    private String foto;
    private String observacao;

    public Registro() {
    }

    public Registro(long id, Date data, Date horario, String tipo, String foto, String observacao) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.foto = foto;
        this.observacao = observacao;
    }

    protected Registro(Parcel in) {
        id = in.readLong();
        data = (java.util.Date) in.readSerializable();
        horario = (java.util.Date) in.readSerializable();
        tipo = in.readString();
        foto = in.readString();
        observacao = in.readString();
    }

    public static final Creator<Registro> CREATOR = new Creator<Registro>() {
        @Override
        public Registro createFromParcel(Parcel in) {
            return new Registro(in);
        }

        @Override
        public Registro[] newArray(int size) {
            return new Registro[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeSerializable(data);
        dest.writeSerializable(horario);
        dest.writeString(tipo);
        dest.writeString(foto);
        dest.writeString(observacao);
    }
}