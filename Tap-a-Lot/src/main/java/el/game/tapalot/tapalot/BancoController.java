package el.game.tapalot.tapalot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

    public class BancoController {
    private static SQLiteDatabase db;
    private static CriaBanco banco;

    public BancoController(Context context){

        banco = new CriaBanco(context);
    }

    //Carrega os dados da tabela
    public static Cursor carregaDados()
    {
        Cursor cursor;
        String[] campos = { banco.getID(), banco.getUSUARIO(), banco.getNOME(), banco.getSENHA(), banco.getEMAIL() };
        db = banco.getReadableDatabase();
        cursor = db.query(banco.getTABELA(), campos, null, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    //Faz a validação do login e senha
    public static Cursor validaLogin(String usuario, String senha)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        String[] selectionArgs = new String[]{usuario, senha};
        cursor = db.rawQuery("select * from usuarios where usuario=? and senha=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    //Faz a validação do registro
    public static Cursor validaRegistro(String usuario)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        String[] selectionArgs = new String[]{usuario};
        cursor = db.rawQuery("select * from usuarios where usuario=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }


    public static Cursor validaNovoUsuario(String usuario)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        String[] selectionArgs = new String[]{usuario};
        cursor = db.rawQuery("select * from pontuacao where idUsuario=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public static Cursor getNomeUsuario(String id)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        String[] selectionArgs = new String[]{id};
        cursor = db.rawQuery("select usuario from usuarios where idUsu=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public static Cursor getTamPontuacao()
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        cursor = db.rawQuery("select count(*) from pontuacao", null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public static Cursor getPosicao()
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        cursor = db.rawQuery("select idPont, pontos, idUsuario, (select count(*) from pontuacao p2 where p1.idPont >= p2.idPont) as pos from pontuacao p1 order by pontos desc", null);
        //cursor = db.rawQuery("select * from pontuacao order by pontos desc", null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public static Cursor getPontos(String id)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();
        String[] selectionArgs = new String[]{id};
        cursor = db.rawQuery("select * from pontuacao where idUsuario=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    public static Cursor getIDUsuario(String usuario)
    {
        Cursor cursor;
        db = banco.getReadableDatabase();

        String[] selectionArgs = new String[]{usuario};
        cursor = db.rawQuery("select * from usuarios where usuario=?", selectionArgs);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();

        return cursor;
    }

    //Insere os dados
    public static String insereDados(String usuario, String nome, String senha, String email)
    {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.getUSUARIO(), usuario);
        valores.put(CriaBanco.getNOME(), nome);
        valores.put(CriaBanco.getSENHA(), senha);
        valores.put(CriaBanco.getEMAIL(), email);
        resultado = db.insert(CriaBanco.getTABELA(), null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro!";
        else
            return "Registro Inserido com sucesso!";
    }

    public static String inserePontos(int idUsuario, int pontos)
    {
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.getPONTOS(), pontos);
        valores.put(CriaBanco.getFK(), idUsuario);
        resultado = db.insert(CriaBanco.getTABELA2(), null, valores);
        db.close();

        if (resultado ==-1)
            return "Erro ao inserir registro!";
        else
            return "Registro Inserido com sucesso!";
    }

    //Altera dados
    public void alteraPontos(int id, int pontos){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();
        where = CriaBanco.getFK() + "=" + id;
        valores = new ContentValues();
        valores.put(CriaBanco.getPONTOS(), pontos);
        db.update(CriaBanco.getTABELA2(), valores, where, null);
        db.close();
    }

    //Deleta os dados
    public void deletaRegistro(int id){
        String where = CriaBanco.getID() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.getTABELA(), where, null);
        db.close();
    }

    //Deleta tudo
    public void deletaTudo(){
        //String where = CriaBanco.getID() + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.getTABELA(), null, null);

        db.close();
    }

    public void atualiza() {
        //banco.onCreate(db);
        //db.rawQuery("select * from usuarios where usuario=?", selectionArgs);

        db.execSQL("SELECT * FROM usuarios");
    }
}
