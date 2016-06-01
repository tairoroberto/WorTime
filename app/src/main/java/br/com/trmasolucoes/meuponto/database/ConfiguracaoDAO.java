package br.com.trmasolucoes.meuponto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.trmasolucoes.meuponto.domain.Configuracao;

/**
 * Created by tairo on 13/04/16.
 */
public class ConfiguracaoDAO {

    private SQLiteDatabase db;
    private Context context;

    public ConfiguracaoDAO(Context context) {
        DBCore dbCore = new  DBCore(context);
        db = dbCore.getWritableDatabase();
        this.context = context;
    }

    public void insert(Configuracao configuracao) {
        ContentValues values = new ContentValues();
        values.put("empresa", configuracao.getEmpresa());
        values.put("email", configuracao.getEmail());
        values.put("senha", String.valueOf(configuracao.getSenha()));
        values.put("notificação", String.valueOf(configuracao.getNotificacao()));

        db.insert("configuracoes", null, values);
    }


    public void update(Configuracao configuracao) {
        ContentValues values = new ContentValues();
        values.put("empresa", configuracao.getEmpresa());
        values.put("email", configuracao.getEmail());
        values.put("senha", String.valueOf(configuracao.getSenha()));
        values.put("notificação", String.valueOf(configuracao.getNotificacao()));

        db.update("configuracoes", values, "_id = ?", new String[]{"" + configuracao.getId()});
    }


    public void delete(Configuracao configuracao) {
        db.delete("configuracoes", "_id = ?", new String[]{"" + configuracao.getId()});
    }



    public ArrayList<Configuracao> getAll() {
        ArrayList<Configuracao> list = new ArrayList<>();

        String[] columns = {"_id","empresa","email","senha","notificação"};
        Cursor cursor = db.query("configuracoes", columns, null, null, null, null, "_id");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Configuracao configuracao = new Configuracao();

                configuracao.setId(cursor.getLong(0));
                configuracao.setEmpresa(cursor.getString(1));
                configuracao.setEmail(cursor.getString(2));
                configuracao.setSenha(Boolean.parseBoolean(cursor.getString(3)));
                configuracao.setNotificacao(Boolean.parseBoolean(cursor.getString(4)));

                list.add(configuracao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return(list);
    }

    public Configuracao getById(long id) {
        Configuracao configuracao = new Configuracao();

        String[] columns = {"_id","empresa","email","senha","notificação"};
        String where = "_id = ?";

        Cursor cursor = db.query("configuracoes", columns, where, new String[]{"" + id}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            configuracao.setId(cursor.getLong(0));
            configuracao.setEmpresa(cursor.getString(1));
            configuracao.setEmail(cursor.getString(2));
            configuracao.setSenha(Boolean.parseBoolean(cursor.getString(3)));
            configuracao.setNotificacao(Boolean.parseBoolean(cursor.getString(4)));

            cursor.close();
            return configuracao;
        } else {
            cursor.close();
            return configuracao;
        }
    }


    public ArrayList<Configuracao> getByDate(String data) {
        ArrayList<Configuracao> list = new ArrayList<>();

        String[] columns = {"_id","empresa","email","senha","notificação"};
        String where = "data = ?";

        Cursor cursor = db.query("configuracoes", columns, where, new String[]{data}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Configuracao configuracao = new Configuracao();

                configuracao.setId(cursor.getLong(0));
                configuracao.setEmpresa(cursor.getString(1));
                configuracao.setEmail(cursor.getString(2));
                configuracao.setSenha(Boolean.parseBoolean(cursor.getString(3)));
                configuracao.setNotificacao(Boolean.parseBoolean(cursor.getString(4)));

                list.add(configuracao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return(list);
    }
}
