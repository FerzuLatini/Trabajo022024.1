package cl.virginiogomez.trabajo022024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    Button btCategoria;
    Button btProducos;
    Button btCliente;
    Button btventa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Inicialización de los botones
        btCategoria = findViewById(R.id.btCategoria);
        btProducos = findViewById(R.id.btProducos);
        btCliente = findViewById(R.id.btCliente);
        btventa = findViewById(R.id.btventa);

        // Crear los intents específicos para cada botón
        Intent intentCategoria = new Intent(this, MainActivity3.class);
        Intent intentProducos = new Intent(this, MainActivity4.class);
        Intent intentCliente = new Intent(this, MainActivity5.class);
        Intent intentVenta = new Intent(this, MainActivity6.class);

        // Configurar el evento de clic para btCategoria
        btCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCategoria);
            }
        });

        // Configurar el evento de clic para btProducos
        btProducos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentProducos);
            }
        });

        // Configurar el evento de clic para btCliente
        btCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCliente);
            }
        });

        // Configurar el evento de clic para btvanta
        btventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentVenta);
            }
        });

        // Ajustar las insets de la ventana para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
