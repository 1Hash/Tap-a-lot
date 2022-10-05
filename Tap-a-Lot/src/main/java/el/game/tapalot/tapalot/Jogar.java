package el.game.tapalot.tapalot;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Jogar extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogar);
    }

    public void clickRegistrar(View view) {
        Intent registrar = new Intent(Jogar.this, Registrar.class);
        startActivity(registrar);
    }

    public void clickEntrar(View view) {

        BancoController cr = new BancoController(getBaseContext());
        Cursor resultado = null;

        EditText usuario = (EditText)findViewById((R.id.txtUsuario));
        usuario.requestFocus();
        EditText senha = (EditText)findViewById(R.id.txtSenha);
        String usuarioString = usuario.getText().toString();
        String senhaString = senha.getText().toString();


        if(usuarioString.equals("") && senhaString.equals("")) {
            Toast.makeText(getApplicationContext(), "Insira seu usuário e senha!", Toast.LENGTH_LONG).show();
        }
        else if(usuarioString.equals("")) {
            Toast.makeText(getApplicationContext(), "Insira seu usuário!", Toast.LENGTH_LONG).show();
            senha.setText("");
        }
        else if(senhaString.equals("")) {
            Toast.makeText(getApplicationContext(), "Insira sua senha!", Toast.LENGTH_LONG).show();
            usuario.setText("");
        }
        else {
            resultado = cr.validaLogin(usuarioString, senhaString);

            if(resultado.getCount() > 0) {
                Intent entrar = new Intent(Jogar.this, Jogo.class);
                Bundle bundle = new Bundle();
                bundle.putString("usuario", resultado.getString(1));
                entrar.putExtras(bundle);

                startActivity(entrar);
            } else {
                Toast.makeText(getApplicationContext(), "Usuário ou senha inválido!", Toast.LENGTH_LONG).show();
                usuario.setText("");
                senha.setText("");
                usuario.requestFocus();
            }
        }

    }
}
