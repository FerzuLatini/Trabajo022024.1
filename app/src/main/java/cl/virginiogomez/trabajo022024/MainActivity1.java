package cl.virginiogomez.trabajo022024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity1 extends AppCompatActivity {

    Button btEntrar;
    EditText editTexUser, editTextTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main1);

        editTexUser = findViewById(R.id.editTexUser);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btEntrar = findViewById(R.id.btEntrar);

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto de los campos de usuario y contraseña
                String username = editTexUser.getText().toString();
                String password = editTextTextPassword.getText().toString();

                // Verificamos si son correctos
                if (username.equals("ferzu") && password.equals("123456")) {
                    // Si son correctos, pasamos a la siguiente actividad
                    Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                    startActivity(intent);
                } else {
                    // Si no coinciden, mostramos un mensaje de error
                    Toast.makeText(MainActivity1.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}

