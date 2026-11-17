package com.example.reservastalleres;

import android.os.Bundle;
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}