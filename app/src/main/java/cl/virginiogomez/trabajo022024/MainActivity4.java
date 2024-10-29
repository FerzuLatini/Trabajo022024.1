package cl.virginiogomez.trabajo022024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.widget.Toast;

import cl.virginiogomez.trabajo022024.ModeloFB.categoria;
import cl.virginiogomez.trabajo022024.ModeloFB.producto;

public class MainActivity4 extends AppCompatActivity {

    Button btCanselarA4, btNuevo4, btModificar4, btEliminar4, btGuardar4;
    EditText editTextNombre, editTextPresio, editTextCantidad, editTextNonbreCt;
    ListView listV4;

    // Referencias a la base de datos Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    // Variables para manejar la categoría seleccionada y su ID
    producto productoselected;
    String selectedproductoId;


    // Lista y adaptador para mostrar productos en la ListView
    List<producto> listproducto;
    ArrayAdapter<producto> arrayAdapterproducto;

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

        // Inicialización de la lista de productos
        listproducto = new ArrayList<>();

        // Inicialización de Firebase y carga de datos
        inicializarFirebase();
        listarDatos();

        listV4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtención del elemento seleccionado
                productoselected = (producto) parent.getItemAtPosition(position);
                editTextNombre.setText(productoselected.getNombre());
                editTextPresio.setText(String.valueOf(productoselected.getPrecio())); // Convierte el precio a String
                editTextCantidad.setText(String.valueOf(productoselected.getCantidad())); // Convierte la cantidad a String
                editTextNonbreCt.setText(productoselected.getNombreCategoria());
            }
        });

        btModificar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de los nuevos valores de los campos de texto
                String nuevoNombre = editTextNombre.getText().toString().trim();
                String nuevoNombreCategoria = editTextNonbreCt.getText().toString().trim();
                double nuevoPrecio;
                int nuevaCantidad;

                // Conversión de precio y cantidad, y manejo de errores
                try {
                    nuevoPrecio = Double.parseDouble(editTextPresio.getText().toString().trim());
                    nuevaCantidad = Integer.parseInt(editTextCantidad.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity4.this, "Error en los valores ingresados", Toast.LENGTH_SHORT).show();
                    return; // Detiene el proceso si hay un error en la conversión
                }

                // Verificación de datos y actualización del producto en Firebase
                if (!nuevoNombre.isEmpty() && !nuevoNombreCategoria.isEmpty() && productoselected != null) {
                    // Actualiza los valores del producto en Firebase
                    databaseReference.child("Productos").child(productoselected.getId())
                            .child("nombre").setValue(nuevoNombre);
                    databaseReference.child("Productos").child(productoselected.getId())
                            .child("precio").setValue(nuevoPrecio);
                    databaseReference.child("Productos").child(productoselected.getId())
                            .child("cantidad").setValue(nuevaCantidad);
                    databaseReference.child("Productos").child(productoselected.getId())
                            .child("nombreCategoria").setValue(nuevoNombreCategoria);

                    // Limpia los campos de texto
                    editTextNombre.setText("");
                    editTextPresio.setText("");
                    editTextCantidad.setText("");
                    editTextNonbreCt.setText("");
                    productoselected = null; // Reinicia la selección del producto
                } else {
                    Toast.makeText(MainActivity4.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btEliminar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificación de que hay un producto seleccionado
                if (productoselected != null) {
                    // Elimina el producto de Firebase usando su ID
                    databaseReference.child("Productos").child(productoselected.getId()).removeValue()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(MainActivity4.this, "Producto eliminado con éxito", Toast.LENGTH_SHORT).show();
                                // Limpia los campos de texto
                                editTextNombre.setText("");
                                editTextPresio.setText("");
                                editTextCantidad.setText("");
                                editTextNonbreCt.setText("");
                                productoselected = null; // Reinicia la selección del producto
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(MainActivity4.this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            });
                } else {
                    Toast.makeText(MainActivity4.this, "Por favor selecciona un producto para eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
























        btNuevo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNombre.setText("");
                editTextPresio.setText("");
                editTextCantidad.setText("");
                editTextNonbreCt.setText("");
            }
        });

        btGuardar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtención de texto de los campos
                String nombre = editTextNombre.getText().toString().trim();
                String nombreCategoria = editTextNonbreCt.getText().toString().trim();

                // Conversión de precio y cantidad
                double precio;
                int cantidad;
                try {
                    precio = Double.parseDouble(editTextPresio.getText().toString().trim());
                    cantidad = Integer.parseInt(editTextCantidad.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return; // Detiene el proceso si hay un error en la conversión
                }

                // Verificación de datos y creación de un nuevo producto
                if (!nombre.isEmpty() && !nombreCategoria.isEmpty()) {
                    String id = databaseReference.push().getKey();
                    producto prod = new producto(id, nombre, precio, cantidad, nombreCategoria);

                    // Almacena el producto en Firebase y limpia los campos
                    databaseReference.child("Productos").child(id).setValue(prod)
                            .addOnSuccessListener(aVoid -> {
                                editTextNombre.setText("");
                                editTextPresio.setText("");
                                editTextCantidad.setText("");
                                editTextNonbreCt.setText("");
                            })
                            .addOnFailureListener(e -> e.printStackTrace());
                }
            }
        });

        // Crear el intent específico para cancelar y volver al menú
        Intent intentbtcanselarA4 = new Intent(this, MainActivity2.class);
        btCanselarA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentbtcanselarA4);
            }
        });

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

    // Método para listar todas las productos de la base de datos
    private void listarDatos() {
        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Asegúrate de que la lista de productos esté inicializada
                if (listproducto == null) {
                    listproducto = new ArrayList<>();
                }

                // Limpia la lista para evitar datos duplicados
                listproducto.clear();

                // Recorre los datos de Firebase y los agrega a la lista
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    producto p = objSnapshot.getValue(producto.class);
                    if (p != null) {
                        listproducto.add(p);
                    }
                }

                // Configura el adaptador una vez que todos los datos han sido cargados
                if (arrayAdapterproducto == null) {
                    arrayAdapterproducto = new ArrayAdapter<>(MainActivity4.this, android.R.layout.simple_list_item_1, listproducto);
                    listV4.setAdapter(arrayAdapterproducto);
                } else {
                    // Notifica al adaptador para actualizar la lista de productos
                    arrayAdapterproducto.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Registro del error en caso de fallo al acceder a Firebase
                Log.e("listarDatos", "Error al cargar datos: " + error.getMessage());
                Toast.makeText(MainActivity4.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}





