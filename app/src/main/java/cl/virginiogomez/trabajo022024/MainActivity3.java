package cl.virginiogomez.trabajo022024;

// Importación de clases necesarias para el funcionamiento de la aplicación y Firebase
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;

import cl.virginiogomez.trabajo022024.ModeloFB.categoria;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.Toast;





// Clase principal de la actividad que extiende AppCompatActivity
public class MainActivity3 extends AppCompatActivity {




    // Declaración de botones, campos de texto y lista para elementos de UI
    Button btCanselar, buttonNuebo3, buttonModificar3, buttonEliminar3, buttonGuardar;
    EditText editTextC, editTextD;
    ListView listV;



    // Referencias a la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    // Variables para manejar la categoría seleccionada y su ID
    categoria categoriaselected;
    String selectedCategoriaId;




    // Lista y adaptador para mostrar categorías en la ListView
    List<categoria> listcategoria;
    ArrayAdapter<categoria> arrayAdaptercategoria;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuración para el modo de pantalla completa
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);


        // Inicialización de botones y otros elementos de UI
        btCanselar = findViewById(R.id.btCanselar);
        buttonNuebo3 = findViewById(R.id.buttonNuebo3);
        buttonModificar3 = findViewById(R.id.buttonModificar3);
        buttonEliminar3 = findViewById(R.id.buttonEliminar3);
        buttonGuardar = findViewById(R.id.buttonGuardar);




        // Inicialización de campos de texto y lista
        editTextC = findViewById(R.id.editTextC);
        editTextD = findViewById(R.id.editTextD);
        listV = findViewById(R.id.listV);



        // Configuración de la lista y su adaptador
        listcategoria = new ArrayList<>();
        arrayAdaptercategoria = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listcategoria);
        listV.setAdapter(arrayAdaptercategoria);




        // Inicialización de Firebase y carga de datos
        inicializarFirebase();
        listarDatos();



        // Configuración del evento de selección de item en la lista
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtención del elemento seleccionado y su ID
                categoriaselected = (categoria) parent.getItemAtPosition(position);
                selectedCategoriaId = categoriaselected.getId();

                // Muestra los datos seleccionados en los campos de texto
                editTextC.setText(categoriaselected.getNombre());
                editTextD.setText(categoriaselected.getDescripcion());
            }
        });




        // Evento de botón para guardar una nueva categoría
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de texto de los campos
                String nombreCategoria = editTextC.getText().toString().trim();
                String descripcionCategoria = editTextD.getText().toString().trim();

                // Verificación de datos y creación de una nueva categoría
                if (!nombreCategoria.isEmpty() && !descripcionCategoria.isEmpty()) {
                    String id = databaseReference.push().getKey();
                    categoria cat = new categoria(id, nombreCategoria, descripcionCategoria);

                    // Almacena la categoría en Firebase y limpia los campos
                    databaseReference.child("Categorias").child(id).setValue(cat)
                            .addOnSuccessListener(aVoid -> {
                                editTextC.setText("");
                                editTextD.setText("");
                            })
                            .addOnFailureListener(e -> e.printStackTrace());
                }
            }
        });





        // Evento de botón para modificar la categoría seleccionada
        buttonModificar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de los nuevos valores de los campos de texto
                String nuevoNombre = editTextC.getText().toString().trim();
                String nuevaDescripcion = editTextD.getText().toString().trim();

                // Verificación de datos y actualización de la categoría en Firebase
                if (!nuevoNombre.isEmpty() && !nuevaDescripcion.isEmpty() && selectedCategoriaId != null) {
                    databaseReference.child("Categorias").child(selectedCategoriaId)
                            .child("nombre").setValue(nuevoNombre);
                    databaseReference.child("Categorias").child(selectedCategoriaId)
                            .child("descripcion").setValue(nuevaDescripcion);

                    // Limpia los campos de texto y reinicia el ID de selección
                    editTextC.setText("");
                    editTextD.setText("");
                    selectedCategoriaId = null;
                }
            }
        });


        buttonEliminar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica que hay una categoría seleccionada y que el ID no sea nulo
                if (selectedCategoriaId != null) {
                    // Elimina la categoría en Firebase utilizando su ID
                    databaseReference.child("Categorias").child(selectedCategoriaId).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                // Limpia los campos de texto y la selección
                                editTextC.setText("");
                                editTextD.setText("");
                                selectedCategoriaId = null;  // Reinicia el ID de la categoría seleccionada
                            })
                            .addOnFailureListener(e -> e.printStackTrace());
                } else {
                    // Manejo de caso cuando no hay una categoría seleccionada (opcional)
                    Toast.makeText(MainActivity3.this, "Seleccione una categoría para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });









        // Evento de botón para limpiar los campos de texto
        buttonNuebo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextC.setText("");
                editTextD.setText("");
            }
        });




        // Configuración del botón para cancelar y regresar a MainActivity2
        Intent intentbtcanselar = new Intent(this, MainActivity2.class);

        btCanselar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentbtcanselar);
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



    // Método para listar todas las categorías de la base de datos
    private void listarDatos() {
        databaseReference.child("Categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Limpia la lista y agrega los datos actualizados
                listcategoria.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    categoria c = objSnapshot.getValue(categoria.class);
                    if (c != null) {
                        listcategoria.add(c);
                    }
                }
                arrayAdaptercategoria.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores en caso de problemas con Firebase
            }
        });
    }
}
