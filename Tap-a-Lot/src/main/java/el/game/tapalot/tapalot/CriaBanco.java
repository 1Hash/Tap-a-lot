package el.game.tapalot.tapalot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CriaBanco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "banco03.db";
    private static final int VERSAO = 1;

    //TABELA 01 - USUÁRIOS
    private static final String TABELA = "usuarios";
    private static final String ID = "idUsu";
    public static final String USUARIO = "usuario";
    public static final String NOME = "nome";
    public static final String SENHA = "senha";
    public static final String EMAIL = "email";

    public static String getTABELA() { return TABELA; }
    public static String getID() { return ID; }
    public static String getUSUARIO() { return USUARIO; }
    public static String getNOME() { return NOME; }
    public static String getSENHA() { return SENHA; }
    public static String getEMAIL() { return EMAIL; }

    //TABELA 02 - PONTUAÇÃO
    private static final String TABELA2 = "pontuacao";
    public static final String PONTOS = "pontos";
    private static final String ID2 = "idPont";
    public static final String FK = "idUsuario";

    public static String getID2() { return ID2; }
    public static String getTABELA2() { return TABELA2; }
    public static String getPONTOS() { return PONTOS; }
    public static String getFK() { return FK; }


    public CriaBanco(Context context)  {
        super(context, NOME_BANCO, null, VERSAO);
    }

    //Cria a tabela no banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+" ( "
                + ID + " integer primary key autoincrement,"
                + USUARIO + " text,"
                + NOME + " text,"
                + SENHA + " text,"
                + EMAIL + " text"
                +" )";

        String sql2 = "CREATE TABLE "+TABELA2+" ( "
                + ID2 + " integer primary key autoincrement,"
                + PONTOS + " text,"
                + FK + " integer,"
                + "FOREIGN KEY (" + FK + ") REFERENCES " + TABELA + " (" + ID + ")"
                +" )";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        db.execSQL("DROP TABLE IF EXISTS" + TABELA2);
        onCreate(db);
    }
}
