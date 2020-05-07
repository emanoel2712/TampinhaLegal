package br.com.tampinhalegal.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import br.com.tampinhalegal.R;
import br.com.tampinhalegal.control.MainControl;

public class MainActivity extends AppCompatActivity {
    private MainControl mainControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainControl = new MainControl(this);
    }

    public void telaPontosColeta(View v) {
       mainControl.btPontosColeta();

    }
}
