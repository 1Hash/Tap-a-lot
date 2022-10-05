package el.game.tapalot.tapalot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Jogo extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static int contadorTaps = 0;
    public static int contadorPontos = 0;

    public int pontos[] = new int[100];
    public int ids[] = new int[100];

    BancoController bc = new BancoController(getBaseContext());

    public int getUsuario() {
        Intent entrar = getIntent();
        Bundle bundle = entrar.getExtras();
        String usuario = bundle.getString("usuario");

        BancoController bc2 = new BancoController(getBaseContext());
        Cursor cursor = bc2.getIDUsuario(usuario);

        return cursor.getInt(0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        contadorTaps = 0;

        BancoController bc2 = new BancoController(getBaseContext());

        int id = getUsuario();

        Cursor v = bc2.validaNovoUsuario(String.valueOf(id));

        if(v.getCount() > 0) {

            Cursor p = bc2.getPontos(String.valueOf(id));

            contadorPontos = p.getInt(p.getColumnIndex("pontos"));

            TextView pontuacao = (TextView)findViewById(R.id.txtPontuacao);
            pontuacao.setText(Integer.toString(contadorPontos));

            Cursor c = bc2.getPosicao();
            int i = 0, el2 = 0;

            do {
                pontos[i] = c.getInt(c.getColumnIndex("pontos"));
                ids[i] = c.getInt(c.getColumnIndex("idUsuario"));

                i++;

            }while(c.moveToNext());

            bubbleSort();

            for(int el = pontos.length - 1; el >= 0; el--) {
                el2++;
                if(ids[el] == id) {
                    break;
                }
            }

            TextView posicao = (TextView)findViewById(R.id.txtPosicao);
            posicao.setText(Integer.toString(el2));
        }
        else {
            contadorPontos = 0;
            bc2.inserePontos(id, contadorPontos);
        }
    }

    public void bubbleSort(){
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
    }

    public void tap(View view) {

        TextView pontos = (TextView)findViewById(R.id.txtPontos);
        TextView pontuacao = (TextView)findViewById(R.id.txtPontuacao);

        getUsuario();

        contadorTaps++;
        pontos.setText(Integer.toString(contadorTaps));

        if(contadorTaps > 10) {

        }
        else if(contadorTaps > 50) {

        }
    }

    public void cansei(View view) {

        TextView pontos2 = (TextView)findViewById(R.id.txtPontos);
        TextView pontuacao = (TextView)findViewById(R.id.txtPontuacao);

        contadorPontos += contadorTaps;
        int auxtaps = contadorTaps;
        contadorTaps = 0;
        pontos2.setText(Integer.toString(contadorTaps));
        pontuacao.setText(Integer.toString(contadorPontos));

        BancoController bc2 = new BancoController(getBaseContext());

        int id = getUsuario();

        bc2.alteraPontos(id, contadorPontos);

        //Cursor p = bc2.getPontos(String.valueOf(id));

        Cursor c = bc2.getPosicao();
        int i = 0, el2 = 0;

        do {
            pontos[i] = c.getInt(c.getColumnIndex("pontos"));
            ids[i] = c.getInt(c.getColumnIndex("idUsuario"));

            i++;

        }while(c.moveToNext());

        bubbleSort();

        for(int el = pontos.length - 1; el >= 0; el--) {
            el2++;
            if(ids[el] == id) {
                break;
            }
        }

        TextView posicao = (TextView)findViewById(R.id.txtPosicao);
        posicao.setText(Integer.toString(el2));

        Intent entrar = getIntent();
        Bundle bundle = entrar.getExtras();
        String usuario = bundle.getString("usuario");

        Intent entrarC = new Intent(Jogo.this, Cansei.class);
        Bundle bundleC = new Bundle();
        bundleC.putString("usuario", usuario);
        bundleC.putString("tap", Integer.toString(auxtaps));
        bundleC.putString("pontos", Integer.toString(contadorPontos));
        bundleC.putString("posicao", Integer.toString(el2));

        entrarC.putExtras(bundleC);

        startActivity(entrarC);
    }
}
