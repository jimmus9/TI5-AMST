package com.example.amstti5jjrg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ConsumerIrManager cIr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarAccionX(); // Llama a tu función aquí
            }
        });

        // Inicializa ConsumerIrManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            cIr = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
        }

        // Verifica si el dispositivo tiene un emisor IR
        if (cIr != null && cIr.hasIrEmitter()) {
            Log.i("IRTest", "Dispositivo con emisor IR");
            Toast.makeText(this, "Dispositivo con emisor IR", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("IRTest", "Dispositivo sin emisor IR");
            Toast.makeText(this, "Dispositivo sin emisor IR", Toast.LENGTH_SHORT).show();
            // Considera deshabilitar funcionalidades relacionadas con IR aquí
        }
    }


    public void ejecutarAccionX() {

        if (cIr == null || !cIr.hasIrEmitter()) {
            Toast.makeText(this, "Este dispositivo no tiene componente de hardware IR", Toast.LENGTH_SHORT).show();
            return; // Detener la ejecución si no hay emisor IR
        }

        // Define un patrón  para la transmisión IR
        int[] pattern = {
                500, 7300, 500, 200 // Encender durante 1000 microsegundos, luego apagar durante 1000 microsegundos
        };


        int frequency = 38000; // Frecuencia más típica para IR

        //IrCommand necCommand = IrCommand.NEC.buildNEC(32,0x723F);

        try {
            cIr.transmit(frequency, pattern);
        } catch (Exception e) {
            e.printStackTrace();  // Imprime la traza de la pila en el logcat

            // Construye un mensaje de error más detallado
            String errorMessage = "Error en la transmisión IR: " + e.getMessage() + "\nVer logcat para más detalles.";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }


    }

}