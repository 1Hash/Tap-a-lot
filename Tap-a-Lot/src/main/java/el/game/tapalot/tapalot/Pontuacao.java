package el.game.tapalot.tapalot;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Pontuacao extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_pontuacao);
        tableLayout = (TableLayout)findViewById(R.id.tbl_pontuacao);
        construirTabela();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/alegreyasans_extrabold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }

    private void construirTabela(){

        BancoController bc2 = new BancoController(getBaseContext());

        Cursor x = bc2.getTamPontuacao();

        int pontos[] = new int[x.getInt(0)];
        int ids[] = new int[x.getInt(0)];

        Cursor c = bc2.getPosicao();

        int linhas = c.getCount();
        int colunas = c.getColumnCount();

        c.moveToFirst();

        int el = 0;

        do {

            pontos[el] = c.getInt(c.getColumnIndex("pontos"));
            ids[el] = c.getInt(c.getColumnIndex("idUsuario"));

            el++;

        }while(c.moveToNext());

        boolean troca = true;
        int aux, aux2;
        while (troca) {
            troca = false;
            for (int i = 0; i < pontos.length - 1; i++) {
                if (pontos[i] > pontos[i + 1]) {
                    aux = pontos[i];
                    aux2 = ids[i];

                    pontos[i] = pontos[i + 1];
                    ids[i] = ids[i + 1];

                    pontos[i + 1] = aux;
                    ids[i + 1] = aux2;

                    troca = true;
                }
            }
        }

        boolean headc1 = true, headc2 = true, headc3 = true, head = true, hh = false;

        for (int cont2 = -1, i = pontos.length + 1; i >= 0; i--, cont2++){
            TableRow linha = new TableRow(this);
            linha.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 3; j++){

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);

                if(head) {
                    tv.setText("");
                    head = false;
                    hh = true;

                }
                else if(hh){
                    tv.setText("PONTUAÇÃO");
                    hh = false;
                    j = 3;
                }
                else {

                    if (j == 0) {
                        if (headc1) {
                            tv.setText("POSIÇÃO");
                            headc1 = false;
                        } else {
                            tv.setText(Integer.toString(cont2));
                        }
                    } else if (j == 1) {
                        if (headc2) {
                            tv.setText("PONTOS");
                            headc2 = false;
                        } else {
                            tv.setText(Integer.toString(pontos[i]));
                        }
                    } else {
                        if (headc3) {
                            tv.setText("USUÁRIO");
                            headc3 = false;
                        } else {
                            Cursor p = bc2.getNomeUsuario(Integer.toString(ids[i]));
                            tv.setText(p.getString(0));
                        }
                    }
                }

                linha.addView(tv);
            }

            tableLayout.addView(linha);
        }
    }

}
