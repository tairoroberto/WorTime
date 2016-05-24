package br.com.trmasolucoes.worktime.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCore extends SQLiteOpenHelper {
    private static final String NOME_DB = "worktime_db";
    private static final int VERSAO_DB = 1;

    public DBCore(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("create table registros("
                + "_id integer primary key autoincrement,"
                + "data text not null,"
                + "horario text not null,"
                + "tipo text not null,"
                + "foto text,"
                + "observacao text,"
                + "id_empresa integer not null);");


        db.execSQL("create table empresa("
                + "_id integer primary key autoincrement,"
                + "nome text not null,"
                + "entrada text not null,"
                + "almoco text not null,"
                + "retorno_almoco text not null,"
                + "saida text not null);");

        db.execSQL("create table settings("
                + "_id integer primary key autoincrement,"
                + "nome text not null,"
                + "valor text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table registros");
        db.execSQL("drop table empresa");
        db.execSQL("drop table settings");
        onCreate(db);
    }
}
