package br.com.trmasolucoes.meuponto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import br.com.trmasolucoes.meuponto.domain.Registro;
import br.com.trmasolucoes.meuponto.util.DateUtil;

/**
 * Created by tairo on 13/04/16.
 */
public class RegistroDAO {

    private static final String TAG = "Script";
    private SQLiteDatabase db;
    private Context context;

    public RegistroDAO(Context context) {
        DBCore dbCore = new  DBCore(context);
        db = dbCore.getWritableDatabase();
        this.context = context;
    }

    public void insert(Registro registro) {
        try {
            ContentValues values = new ContentValues();
            values.put("data", DateUtil.getDateToString(registro.getData()));
            values.put("horario", DateUtil.getDateToString(registro.getHorario()));
            values.put("tipo", registro.getTipo());
            values.put("foto", registro.getFoto());
            values.put("observacao", registro.getObservacao());

            db.insert("registros", null, values);
        }catch (Exception e){
            Log.i(TAG, "insert: " + e.getMessage());
        }
    }


    public void update(Registro registro) {
        try {
            ContentValues values = new ContentValues();
            values.put("data", DateUtil.getDateToString(registro.getData()));
            values.put("horario", DateUtil.getDateToString(registro.getHorario()));
            values.put("tipo", registro.getTipo());
            values.put("foto", registro.getFoto());
            values.put("observacao", registro.getObservacao());

            db.update("registros", values, "_id = ?", new String[]{"" + registro.getId()});
        }catch (Exception e){
            Log.i(TAG, "update: " + e.getMessage());
        }
    }


    public void delete(Registro registro) {
        try {
            db.delete("registros", "_id = ?", new String[]{"" + registro.getId()});
        }catch (Exception e){
            Log.i(TAG, "delete: "+ e.getMessage());
        }
    }



    public ArrayList<Registro> getAll() {
        ArrayList<Registro> list = new ArrayList<Registro>();
        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        Cursor cursor = db.query("registros", columns, null, null, null, null, "_id");

        try{
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Registro registro = new Registro();

                    registro.setId(cursor.getLong(0));
                    registro.setData(DateUtil.getStringToDate(cursor.getString(1)));
                    registro.setHorario(DateUtil.getStringToDate(cursor.getString(2)));
                    registro.setTipo(cursor.getString(3));
                    registro.setFoto(cursor.getString(4));
                    registro.setObservacao(cursor.getString(5));

                    list.add(registro);
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

    public Registro getById(long id) {
        Registro registro = new Registro();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        String where = "_id = ?";

        Cursor cursor = db.query("registros", columns, where, new String[]{"" + id}, null, null, null);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                registro.setId(cursor.getLong(0));
                registro.setData(DateUtil.getStringToDate(cursor.getString(1)));
                registro.setHorario(DateUtil.getStringToDate(cursor.getString(2)));
                registro.setTipo(cursor.getString(3));
                registro.setFoto(cursor.getString(4));
                registro.setObservacao(cursor.getString(5));

                return registro;
            } else {
                return registro;
            }
        }catch (Exception e){
            Log.i(TAG, "getById: " + e.getMessage());
            return(registro);

        }finally {
            cursor.close();
        }
    }

    /** Pega os registro do dia e hotario (entrada,almoco, almoco_retorno, saida) */
    public Registro getByDateType(String data, String tipo) {
        Registro registro = new Registro();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        String where = "data BETWEEN ? AND ? AND tipo = ?";

        Cursor cursor = db.query("registros", columns, where, new String[]{data+ " 00:00:00",data+ " 23:59:59", tipo}, null, null, null);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                registro.setId(cursor.getLong(0));
                registro.setData(DateUtil.getStringToDate(cursor.getString(1)));
                registro.setHorario(DateUtil.getStringToDate(cursor.getString(2)));
                registro.setTipo(cursor.getString(3));
                registro.setFoto(cursor.getString(4));
                registro.setObservacao(cursor.getString(5));

                return registro;
            } else {
                return registro;
            }
        }catch (Exception e){
            Log.i(TAG, "getByDateType: " + e.getMessage());
            return(registro);

        }finally {
            cursor.close();
        }
    }

    public ArrayList<Registro> getByDate(String data) {
        ArrayList<Registro> list = new ArrayList<Registro>();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        String where = "data BETWEEN ? AND ?";

        Cursor cursor = db.query("registros", columns, where, new String[]{data+ " 00:00:00",data+ " 23:59:59"}, null, null, null);
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                do {
                    Registro registro = new Registro();

                    registro.setId(cursor.getLong(0));
                    registro.setData(DateUtil.getStringToDate(cursor.getString(1)));
                    registro.setHorario(DateUtil.getStringToDate(cursor.getString(2)));
                    registro.setTipo(cursor.getString(3));
                    registro.setFoto(cursor.getString(4));
                    registro.setObservacao(cursor.getString(5));

                    list.add(registro);
                } while (cursor.moveToNext());
            }

            return(list);
        }catch (Exception e){
            Log.i(TAG, "getByDate: " + e.getMessage());
            return(list);

        } finally {
            cursor.close();
        }
    }
}
