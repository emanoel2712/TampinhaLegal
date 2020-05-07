package br.com.tampinhalegal.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.View;

import br.com.tampinhalegal.R;
import br.com.tampinhalegal.control.PontosDeColetaControl;

public class PontosDeColetaActivity extends FragmentActivity {

    private PontosDeColetaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos_de_coleta);

        control = new PontosDeColetaControl(this);

    }

    public void clickUsarGPS(View v) {
        control.clickUsarGPS();
    }

    public void clickPesquisar(View v){
        control.clickPesquisar();
    }
}
