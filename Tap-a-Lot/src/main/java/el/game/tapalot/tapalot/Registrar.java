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

public class Registrar extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static String teste = "Nao deu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }

    public void clickRegistrando(View view) {
        BancoController cr = new BancoController(getBaseContext()); //quase crud rsrsxD

        String resultado;
        EditText nome = (EditText)findViewById(R.id.txtNome);
        EditText usuario = (EditText)findViewById((R.id.txtUsuario));
        EditText senha = (EditText)findViewById(R.id.txtSenha);
        EditText email = (EditText)findViewById(R.id.txtEmail);
        String nomeString = nome.getText().toString();
        String usuarioString = usuario.getText().toString();
        String senhaString = senha.getText().toString();
        String emailString = email.getText().toString();

        Cursor res;

        res = cr.validaRegistro(usuarioString);

        if(nomeString.equals("") || usuarioString.equals("") || senhaString.equals("") || emailString.equals("")) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_LONG).show();
        }
        else {

            if (res.getCount() == 0) {
                resultado = cr.insereDados(usuarioString, nomeString, senhaString, emailString);
                Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                Intent voltaMain = new Intent(Registrar.this, MainActivity.class);
                startActivity(voltaMain);
            } else {
                Toast.makeText(getApplicationContext(), "Usuário já existe!", Toast.LENGTH_LONG).show();
                nome.setText("");
                usuario.setText("");
                senha.setText("");
                email.setText("");
                nome.requestFocus();
            }
        }
    }
}
