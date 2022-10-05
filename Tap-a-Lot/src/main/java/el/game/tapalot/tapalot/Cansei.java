package el.game.tapalot.tapalot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Cansei extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cansei);

        Intent cansei = getIntent();
        Bundle bundle = cansei.getExtras();
        String taps = bundle.getString("tap");
        String posicao = bundle.getString("posicao");
        String pontos = bundle.getString("pontos");

        TextView pos = (TextView)findViewById(R.id.lblPos);
        pos.setText(posicao);

        TextView pont = (TextView)findViewById(R.id.lblPont);
        pont.setText(pontos);

        TextView Taps = (TextView)findViewById(R.id.lblTaps);
        Taps.setText(taps);
    }

    public void sair(View view) {
        Intent sair = new Intent(Cansei.this, MainActivity.class);
        startActivity(sair);
    }

    public void continuar(View view) {
        Intent cansei = getIntent();
        Bundle bundle = cansei.getExtras();
        String usuario = bundle.getString("usuario");

        Intent entrar = new Intent(Cansei.this, Jogo.class);
        Bundle bundleJ = new Bundle();
        bundleJ.putString("usuario", usuario);
        entrar.putExtras(bundleJ);

        startActivity(entrar);
    }


}
