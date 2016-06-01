package br.com.trmasolucoes.meuponto.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import br.com.trmasolucoes.meuponto.domain.Registro;
import br.com.trmasolucoes.meuponto.util.DateUtil;

/**
 * Created by tairo on 13/04/16.
 */
public class RegistroDAO {

    private SQLiteDatabase db;
    private Context context;

    public RegistroDAO(Context context) {
        DBCore dbCore = new  DBCore(context);
        db = dbCore.getWritableDatabase();
        this.context = context;
    }

    public void insert(Registro registro) {
        ContentValues values = new ContentValues();
        values.put("data", DateUtil.getDateToString(registro.getData()));
        values.put("horario", DateUtil.getDateToString(registro.getHorario()));
        values.put("tipo", registro.getTipo());
        values.put("foto", registro.getFoto());
        values.put("observacao", registro.getObservacao());

        db.insert("registros", null, values);
    }


    public void update(Registro registro) {
        ContentValues values = new ContentValues();
        values.put("data", DateUtil.getDateToString(registro.getData()));
        values.put("horario", DateUtil.getDateToString(registro.getHorario()));
        values.put("tipo", registro.getTipo());
        values.put("foto", registro.getFoto());
        values.put("observacao", registro.getObservacao());

        db.update("registros", values, "_id = ?", new String[]{"" + registro.getId()});
    }


    public void delete(Registro registro) {
        db.delete("registros", "_id = ?", new String[]{"" + registro.getId()});
    }



    public ArrayList<Registro> getAll() {
        ArrayList<Registro> list = new ArrayList<Registro>();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        Cursor cursor = db.query("registros", columns, null, null, null, null, "_id");
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
        cursor.close();
        return(list);
    }

    public Registro getById(long id) {
        Registro registro = new Registro();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        String where = "_id = ?";

        Cursor cursor = db.query("registros", columns, where, new String[]{"" + id}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            registro.setId(cursor.getLong(0));
            registro.setData(DateUtil.getStringToDate(cursor.getString(1)));
            registro.setHorario(DateUtil.getStringToDate(cursor.getString(2)));
            registro.setTipo(cursor.getString(3));
            registro.setFoto(cursor.getString(4));
            registro.setObservacao(cursor.getString(5));

            cursor.close();
            return registro;
        } else {
            cursor.close();
            return registro;
        }
    }


    public ArrayList<Registro> getByDate(String data) {
        ArrayList<Registro> list = new ArrayList<Registro>();

        String[] columns = {"_id","data","horario","tipo","foto","observacao"};
        String where = "data = ?";

        Cursor cursor = db.query("registros", columns, where, new String[]{data}, null, null, null);
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
        cursor.close();
        return(list);
    }
}
