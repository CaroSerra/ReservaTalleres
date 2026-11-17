package com.example.reservastalleres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Actividad 2
public class ConfiguracionReservaActivity extends AppCompatActivity {

    private Spinner spTaller;
    private RadioGroup rgModalidad;
    private RadioButton rbPresencial;
    private RadioButton rbOnline;
    private CheckBox cbMaterial;
    private CheckBox cbCertificado;
    private EditText etPlazas;
    private DatePicker dpFecha;
    private TimePicker tpHora;
    private CheckBox cbCondiciones;
    private Button btnAnterior;
    private Button btnLimpiar;
    private Button btnRevisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuracion_reserva);

        spTaller = findViewById(R.id.spTaller);
        rgModalidad = findViewById(R.id.rgModalidad);
        rbPresencial = findViewById(R.id.rbPresencial);
        rbOnline = findViewById(R.id.rbOnline);
        cbMaterial = findViewById(R.id.cbMaterial);
        cbCertificado = findViewById(R.id.cbCertificado);
        etPlazas = findViewById(R.id.etPlazas);
        dpFecha = findViewById(R.id.dpFecha);
        tpHora = findViewById(R.id.tpHora);
        cbCondiciones = findViewById(R.id.cbCondiciones);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnRevisar = findViewById(R.id.btnRevisar);

        /* TODO
            1. Botón Limpiar: añadir el listener y restablecer los controles.
            (Spinner a la posición 0, RadioGroup sin selección, 3 CheckBox
            desmarcados, plazas vacías...).

            2. Botón Revisar: validar ANTES de abrir la Activity 3 (ahora mismo funciona
            sin comprobar nada): el taller debe ser distinto de la posición 0, modalidad
            seleccionada, plazas entre 1 y 10 (controlar campo vacío) y
            condiciones aceptadas. Mostrar error y no continuar si falla.

            3. Botón Revisar: guardar en SharedPreferences, al continuar, taller,
            modalidad, material, certificado, plazas, fecha y hora.

            4. Recuperar los datos guardados al volver a esta pantalla para conservar lo introducido,
            también tras cerrar y reabrir la app.
         */

        // Spinner: carga las opciones de strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.talleres_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTaller.setAdapter(adapter);

       // TimePicker en 24 horas
        tpHora.setIs24HourView(true);

        // Botón Anterior: Vuelve a la activity de DatosPersonales
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracionReservaActivity.this, DatosPersonalesActivity.class);
                startActivity(i);
            }
        });

        // Botón Revisar: Va a la actividad de Resumen Reserva
        // TODO pasar los datos seleccionados al SharedPreferences
        btnRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracionReservaActivity.this, ResumenReservaActivity.class);
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}