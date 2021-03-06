package br.com.trmasolucoes.worktime.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tairo on 23/05/16.
 */
public class Configuracao implements Parcelable{
    private long id;
    private String empresa;
    private String email;
    private Boolean senha;
    private Boolean notificacao;

    public Configuracao() {
    }

    public Configuracao(long id, String empresa, String email, Boolean senha, Boolean notificacao) {
        this.id = id;
        this.empresa = empresa;
        this.email = email;
        this.senha = senha;
        this.notificacao = notificacao;
    }

    protected Configuracao(Parcel in) {
        id = in.readLong();
        empresa = in.readString();
        email = in.readString();
        senha = in.readInt() != 0;
        notificacao = in.readInt() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(empresa);
        dest.writeString(email);
        dest.writeInt(senha ? 1 : 0);
        dest.writeInt(notificacao ? 1 : 0);
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSenha() {
        return senha;
    }

    public void setSenha(Boolean senha) {
        this.senha = senha;
    }

    public Boolean getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Boolean notificacao) {
        this.notificacao = notificacao;
    }
}
