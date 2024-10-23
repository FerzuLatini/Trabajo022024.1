package cl.virginiogomez.trabajo022024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.FirebaseApp;

import cl.virginiogomez.trabajo022024.ModeloFB.producto;

public class MainActivity4 extends AppCompatActivity {

    Button btCanselarA4, btNuevo4, btModificar4, btEliminar4, btGuardar4;
    EditText editTextNombre, editTextPresio, editTextCantidad, editTextNonbreCt;
    ListView listV4;

    // Referencias a Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        // Inicialización de los botones
        btCanselarA4 = findViewById(R.id.btCnselarA4);
        btNuevo4 = findViewById(R.id.btNuevo4);
        btModificar4 = findViewById(R.id.btModificar4);
        btEliminar4 = findViewById(R.id.btEliminar4);
        btGuardar4 = findViewById(R.id.btGuardar4);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPresio = findViewById(R.id.editTextPresio);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        editTextNonbreCt = findViewById(R.id.editTextNonbreCt);

        listV4 = findViewById(R.id.listV4);

        // Inicializa Firebase
        inicializarFirebase();







        // Crear los intents específicos para cada botón
        Intent intentbtcanselarA4 = new Intent(this, MainActivity2.class);

        // Configurar el evento de clic para btCanselarA4
        btCanselarA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentbtcanselarA4);
            }
        });


        // Evento de botón para limpiar los campos de texto
        btNuevo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNombre.setText("");
                editTextPresio.setText("");
                editTextCantidad.setText("");
                editTextNonbreCt.setText("");

            }
        });





        // Evento para guardar un nuevo producto
        btGuardar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString().trim();
                String precioStr = editTextPresio.getText().toString().trim();
                String cantidadStr = editTextCantidad.getText().toString().trim();
                String nombreCategoria = editTextNonbreCt.getText().toString().trim();

                // Validar los campos
                if (nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty() || nombreCategoria.isEmpty()) {
                    Toast.makeText(MainActivity4.this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio = Double.parseDouble(precioStr);
                int cantidad = Integer.parseInt(cantidadStr);

                // Verificar si la categoría existe en la lista de categorías (puedes agregar esta lógica según tu implementación)
                // Aquí deberías verificar si 'nombreCategoria' existe en las categorías almacenadas
                // Para este ejemplo, simplemente asumimos que la categoría es válida.

                String id = databaseReference.child("Productos").push().getKey();
                producto nuevoProducto = new producto(id, nombre, precio, cantidad, nombreCategoria);

                // Guardar el nuevo producto en Firebase
                databaseReference.child("Productos").child(id).setValue(nuevoProducto)
                        .addOnSuccessListener(aVoid -> {
                            editTextNombre.setText("");
                            editTextPresio.setText("");
                            editTextCantidad.setText("");
                            editTextNonbreCt.setText("");
                            Toast.makeText(MainActivity4.this, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(MainActivity4.this, "Error al guardar el producto", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        // Configuración de la compatibilidad con pantallas completas
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Inicialización de Firebase en la aplicación
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
