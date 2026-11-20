package com.example.reservastalleres;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

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
    private static final String PREF_NAME = "PreferenciasReserva";

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

        // Si hay algo guardado
        cargarDatosGuardados();

        // Botón Anterior: Vuelve a la activity de DatosPersonales
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracionReservaActivity.this, DatosPersonalesActivity.class);
                startActivity(i);
            }
        });

        // Botón Revisar: Va a la actividad de Resumen Reserva
        btnRevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 1. Taller: la posición 0 no es válida
                if (spTaller.getSelectedItemPosition() == 0) {
                    Toast.makeText(ConfiguracionReservaActivity.this,
                            "Selecciona un taller", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. Modalidad: debe haber una marcada, el -1 es "ninguna"
                if (rgModalidad.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(ConfiguracionReservaActivity.this,
                            "Selecciona una modalidad", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. Plazas: no debe estar vacío y debe estar entre 1 y 10
                String textoPlazas = etPlazas.getText().toString().trim();
                if (textoPlazas.isEmpty()) {
                    etPlazas.setError("Introduce el número de plazas"); // Esto hace que aparezca el error en rojo en el et
                    etPlazas.requestFocus();
                    return;
                }
                int plazas = Integer.parseInt(textoPlazas); // Pasamos a int para comparar
                if (plazas < 1 || plazas > 10) {
                    etPlazas.setError("Las plazas deben estar entre 1 y 10");
                    etPlazas.requestFocus();
                    return;
                }

                // 4. Condiciones: es obligatorio marcarlas
                if (!cbCondiciones.isChecked()) {
                    Toast.makeText(ConfiguracionReservaActivity.this,
                            "Debes aceptar las condiciones de inscripción", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ------ Guardar con SharedPreferences ----------
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Taller (texto y posición)
                editor.putString("KEY_TALLER", spTaller.getSelectedItem().toString());
                editor.putInt("KEY_TALLER_POS", spTaller.getSelectedItemPosition());

                // Modalidad: texto del RadioButton marcado
                RadioButton rbSeleccionado = findViewById(rgModalidad.getCheckedRadioButtonId());
                editor.putString("KEY_MODALIDAD", rbSeleccionado.getText().toString());

                // Servicios adicionales
                editor.putBoolean("KEY_MATERIAL", cbMaterial.isChecked());
                editor.putBoolean("KEY_CERTIFICADO", cbCertificado.isChecked());

                // Plazas
                editor.putInt("KEY_PLAZAS", plazas);

                // Fecha (dd/MM/yyyy): getMonth() empieza en 0, por eso el +1, enero sería 0
                // lo del "%" es el formato de los números, esto significa
                // "un número de 2 digitos/un nº de 2 digitos/un nº de 4 digitos"
                String fecha = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                        dpFecha.getDayOfMonth(), dpFecha.getMonth() + 1, dpFecha.getYear());
                editor.putString("KEY_FECHA", fecha);

                // Hora (HH:mm)
                String hora = String.format(Locale.getDefault(), "%02d:%02d",
                        tpHora.getHour(), tpHora.getMinute());
                editor.putString("KEY_HORA", hora);

                // Condiciones aceptadas
                editor.putBoolean("KEY_CONDICIONES", cbCondiciones.isChecked());

                editor.apply();

                Intent i = new Intent(ConfiguracionReservaActivity.this, ResumenReservaActivity.class);
                startActivity(i);
            }
        });

        // Botón Limpiar: Restablecer las selecciones a cero
        btnLimpiar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               spTaller.setSelection(0);
                rgModalidad.clearCheck();
                cbMaterial.setChecked(false);
                cbCertificado.setChecked(false);
                etPlazas.setText("");
                etPlazas.setError(null);
                cbCondiciones.setChecked(false);

                // Lo quitamos del sp para que no salgan de nuevo al abrir la app
                // o al volver a la pantalla
                SharedPreferences sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                sp.edit()
                        .remove("KEY_TALLER").remove("KEY_TALLER_POS").remove("KEY_MODALIDAD")
                        .remove("KEY_MATERIAL").remove("KEY_CERTIFICADO").remove("KEY_PLAZAS")
                        .remove("KEY_FECHA").remove("KEY_HORA").remove("KEY_CONDICIONES")
                        .apply();
            }
        }));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargarDatosGuardados() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Taller: la posición que guardamos, o 0 si nada
        spTaller.setSelection(sharedPreferences.getInt("KEY_TALLER_POS", 0));

        // Modalidad: se marca el RadioButton cuyo texto coincide
        String modalidad = sharedPreferences.getString("KEY_MODALIDAD", "");
        if (modalidad.equals("Presencial")) {
            rbPresencial.setChecked(true);
        } else if (modalidad.equals("Online")) {
            rbOnline.setChecked(true);
        }

        // Servicios adicionales
        cbMaterial.setChecked(sharedPreferences.getBoolean("KEY_MATERIAL", false));
        cbCertificado.setChecked(sharedPreferences.getBoolean("KEY_CERTIFICADO", false));

        // Plazas
        int plazas = sharedPreferences.getInt("KEY_PLAZAS", 0);
        if (plazas != 0) {
            etPlazas.setText(String.valueOf(plazas));
        }

        // Fecha guardada como "dd/MM/yyyy"
        String fecha = sharedPreferences.getString("KEY_FECHA", "");
        if (!fecha.isEmpty()) {
            String[] partes = fecha.split("/");
            int dia = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]) - 1; // el Datepicker cuenta desde 0 así que restamos
            int anio = Integer.parseInt(partes[2]);
            dpFecha.updateDate(anio, mes, dia);
        }

        // Hora guardada como "HH:mm"
        String hora = sharedPreferences.getString("KEY_HORA", "");
        if (!hora.isEmpty()) {
            String[] partes = hora.split(":");
            tpHora.setHour(Integer.parseInt(partes[0]));
            tpHora.setMinute(Integer.parseInt(partes[1]));
        }

        // Las condiciones
        cbCondiciones.setChecked(sharedPreferences.getBoolean("KEY_CONDICIONES", false));
    }
}