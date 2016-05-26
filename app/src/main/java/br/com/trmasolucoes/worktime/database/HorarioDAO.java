package br.com.trmasolucoes.worktime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.trmasolucoes.worktime.domain.Horario;

/**
 * Created by tairo on 13/04/16.
 */
public class HorarioDAO {

    private SQLiteDatabase db;
    private Context context;

    public HorarioDAO(Context context) {
        DBCore dbCore = new  DBCore(context);
        db = dbCore.getWritableDatabase();
        this.context = context;
    }

    public void insert(Horario horario) {
        ContentValues values = new ContentValues();
        values.put("dia_semana", horario.getDiaSemana());
        values.put("entrada", horario.getEntrada());
        values.put("almoco", horario.getAlmoco());
        values.put("almoco_retorno", horario.getAlmocoRetorno());
        values.put("saida", horario.getSaida());

        db.insert("horarios", null, values);
    }


    public void update(Horario horario) {
        ContentValues values = new ContentValues();
        values.put("dia_semana", horario.getDiaSemana());
        values.put("entrada", horario.getEntrada());
        values.put("almoco", horario.getAlmoco());
        values.put("almoco_retorno", horario.getAlmocoRetorno());
        values.put("saida", horario.getSaida());

        db.update("horarios", values, "_id = ?", new String[]{"" + horario.getId()});
    }

    public void updateByWeekDay(Horario horario, String campo, String hora) {
        Horario h = this.getByWeekDay(horario.getDiaSemana(), campo, hora);
        if (h.getDiaSemana() == null) {
            this.insert(horario);

        }else {

            ContentValues values = new ContentValues();
            values.put("dia_semana", horario.getDiaSemana());
            values.put("entrada", horario.getEntrada());
            values.put("almoco", horario.getAlmoco());
            values.put("almoco_retorno", horario.getAlmocoRetorno());
            values.put("saida", horario.getSaida());

            db.update("horarios", values, "dia_semana = ? and "+campo+" = ?", new String[]{horario.getDiaSemana(), hora});
        }
    }


    public void delete(Horario horario) {
        db.delete("horarios", "_id = ?", new String[]{"" + horario.getId()});
    }



    public ArrayList<Horario> getAll() {
        ArrayList<Horario> list = new ArrayList<>();

        String[] columns = {"_id","dia_semana","entrada","almoco","almoco_retorno","saida"};
        Cursor cursor = db.query("horarios", columns, null, null, null, null, "_id");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Horario horario = new Horario();

                horario.setId(cursor.getLong(0));
                horario.setDiaSemana(cursor.getString(1));
                horario.setEntrada(cursor.getString(2));
                horario.setAlmoco(cursor.getString(3));
                horario.setAlmocoRetorno(cursor.getString(4));
                horario.setSaida(cursor.getString(5));

                list.add(horario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return(list);
    }

    public Horario getById(long id) {
        Horario horario = new Horario();

        String[] columns = {"_id","dia_semana","entrada","almoco","almoco_retorno","saida"};
        String where = "_id = ?";

        Cursor cursor = db.query("horarios", columns, where, new String[]{"" + id}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            horario.setId(cursor.getLong(0));
            horario.setDiaSemana(cursor.getString(1));
            horario.setEntrada(cursor.getString(2));
            horario.setAlmoco(cursor.getString(3));
            horario.setAlmocoRetorno(cursor.getString(4));
            horario.setSaida(cursor.getString(5));

            cursor.close();
            return horario;
        } else {
            cursor.close();
            return horario;
        }
    }

    public Horario getByWeekDay(String day, String campo, String hora) {
        Horario horario = new Horario();

        String[] columns = {"_id","dia_semana","entrada","almoco","almoco_retorno","saida"};
        String where = "dia_semana = ? and "+campo+" = ?";

        Cursor cursor = db.query("horarios", columns, where, new String[]{day, hora}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            horario.setId(cursor.getLong(0));
            horario.setDiaSemana(cursor.getString(1));
            horario.setEntrada(cursor.getString(2));
            horario.setAlmoco(cursor.getString(3));
            horario.setAlmocoRetorno(cursor.getString(4));
            horario.setSaida(cursor.getString(5));

            cursor.close();
            return horario;
        } else {
            cursor.close();
            return horario;
        }
    }

    public ArrayList<Horario> getByDay(String dia) {
        ArrayList<Horario> list = new ArrayList<>();

        String[] columns = {"_id","dia_semana","entrada","almoco","almoco_retorno","saida"};
        String where = "data = ?";

        Cursor cursor = db.query("horarios", columns, where, new String[]{dia}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Horario horario = new Horario();

                horario.setId(cursor.getLong(0));
                horario.setDiaSemana(cursor.getString(1));
                horario.setEntrada(cursor.getString(2));
                horario.setAlmoco(cursor.getString(3));
                horario.setAlmocoRetorno(cursor.getString(4));
                horario.setSaida(cursor.getString(5));

                list.add(horario);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return(list);
    }
}
