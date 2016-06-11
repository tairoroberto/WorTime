package br.com.trmasolucoes.worktime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import br.com.trmasolucoes.worktime.domain.Configuracao;

/**
 * Created by tairo on 13/04/16.
 */
public class ConfiguracaoDAO {

    private static final String TAG = "Script";
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
        try {
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

            return(list);
        }catch (Exception e){
            Log.i(TAG, "getAll: " + e.getMessage());
            return(list);

        }finally {
            cursor.close();
        }
    }

    public Configuracao getById(long id) {
        Configuracao configuracao = new Configuracao();

        String[] columns = {"_id","empresa","email","senha","notificação"};
        String where = "_id = ?";

        Cursor cursor = db.query("configuracoes", columns, where, new String[]{"" + id}, null, null, null);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                configuracao.setId(cursor.getLong(0));
                configuracao.setEmpresa(cursor.getString(1));
                configuracao.setEmail(cursor.getString(2));
                configuracao.setSenha(Boolean.parseBoolean(cursor.getString(3)));
                configuracao.setNotificacao(Boolean.parseBoolean(cursor.getString(4)));

                return configuracao;
            } else {

                return configuracao;
            }
        }catch (Exception e){
            Log.i(TAG, "getById: " + e.getMessage());
            return(configuracao);

        }finally {
            cursor.close();
        }
    }


    public ArrayList<Configuracao> getByDate(String data) {
        ArrayList<Configuracao> list = new ArrayList<>();

        String[] columns = {"_id","empresa","email","senha","notificação"};
        String where = "data = ?";

        Cursor cursor = db.query("configuracoes", columns, where, new String[]{data}, null, null, null);
        try {
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

            return(list);
        }catch (Exception e){
            Log.i(TAG, "getByDate: " + e.getMessage());
            return(list);

        }finally {
            cursor.close();
        }
    }
}
