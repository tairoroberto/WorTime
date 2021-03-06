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
                + "observacao text );");


        db.execSQL("create table horarios("
                + "_id integer primary key autoincrement,"
                + "dia_semana text not null,"
                + "entrada text ,"
                + "almoco text ,"
                + "almoco_retorno text ,"
                + "saida text );");

        db.execSQL("create table configuracoes("
                + "_id integer primary key autoincrement,"
                + "empresa text not null,"
                + "email text not null,"
                + "senha text not null,"
                + "notificação text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table registros");
        db.execSQL("drop table horarios");
        db.execSQL("drop table configuracoes");
        onCreate(db);
    }
}
