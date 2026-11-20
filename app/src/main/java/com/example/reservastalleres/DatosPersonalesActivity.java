package com.example.reservastalleres;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// /////////// //
// Actividad 1 //
// /////////// //

public class DatosPersonalesActivity extends AppCompatActivity {
    private Button btnContinuar;
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etTelefono;
    @SuppressLint("UseSwitchCompatOrMaterialCode") // Show Context lo puso
    private Switch swNotificaciones;

    private static final String PREF_NAME = "PreferenciasReserva";
    private CheckBox cbInformacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_personales);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llPrincipal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        swNotificaciones = findViewById(R.id.swNotificaciones);
        cbInformacion = findViewById(R.id.cbInformacion);
        btnContinuar = findViewById(R.id.btnContinuar);

        // Por si habían datos guardados previamente
        cargarDatosGuardados();

        // Botonnnnnn
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String nombre = etNombre.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                boolean recibeNotificaciones = swNotificaciones.isChecked();
                boolean deseaTalleres = cbInformacion.isChecked();

                // ///////// //
                // COMPROBAR //
                // ///////// //

                if (nombre.isEmpty()) {
                    // El nombre no puede estar vacío
                    etNombre.setError("El nombre no puede estar vacío.");
                    etNombre.requestFocus();
                    return;
                }
                if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    // El correo debe ser válido
                    etCorreo.setError("Introduce un correo electrónico válido.");
                    etCorreo.requestFocus();
                    return;
                }
                if (telefono.isEmpty()) {
                    // Y el tlfono tmb
                    etTelefono.setError("El teléfono no puede estar vacío.");
                    etTelefono.requestFocus();
                    return;
                }

                // Guardar con SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("KEY_NOMBRE", nombre);
                editor.putString("KEY_CORREO", correo);
                editor.putString("KEY_TELEFONO", telefono);
                editor.putBoolean("KEY_NOTIFICACIONES", recibeNotificaciones);
                editor.putBoolean("KEY_INFO", deseaTalleres);

                editor.apply();

                // Toast para mostrar que se han guardao los datos
                Toast.makeText(DatosPersonalesActivity.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

                // Sig actividad :D
                Intent intent = new Intent(DatosPersonalesActivity.this, ConfiguracionReservaActivity.class);
                startActivity(intent);
            }
        });

    }
    private void cargarDatosGuardados() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        String nombreGuardado = sharedPreferences.getString("KEY_NOMBRE", "");
        String correoGuardado = sharedPreferences.getString("KEY_CORREO", "");
        String telefonoGuardado = sharedPreferences.getString("KEY_TELEFONO", "");
        boolean notifGuardado = sharedPreferences.getBoolean("KEY_NOTIFICACIONES", false);
        boolean infoGuardado = sharedPreferences.getBoolean("KEY_INFO", false);

        // Rellenar campos con info guardada
        etNombre.setText(nombreGuardado);
        etCorreo.setText(correoGuardado);
        etTelefono.setText(telefonoGuardado);
        swNotificaciones.setChecked(notifGuardado);
        cbInformacion.setChecked(infoGuardado);
    }
}