package el.game.tapalot.tapalot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/alegreyasans_extrabold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);

        BancoController cr = new BancoController(getBaseContext());

    }

    public void jogar(View view) {
        Intent login = new Intent(MainActivity.this, Jogar.class);
        startActivity(login);
    }

    public void pontuacao(View view) {
        Intent pontuacao = new Intent(MainActivity.this, Pontuacao.class);
        startActivity(pontuacao);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
