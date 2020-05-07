package br.com.tampinhalegal.control;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

import br.com.tampinhalegal.R;
import br.com.tampinhalegal.model.entidades.Patrocinio;
import br.com.tampinhalegal.view.PontosDeColetaActivity;

public class MainControl {
    private Activity activity;
    private CarouselView carouselView;
    private ArrayList<Patrocinio> patrocinios;

    public MainControl(Activity activity) {
        this.activity = activity;
        initComponents();
    }

    private void initComponents() {
        carouselView = activity.findViewById(R.id.carouselView);
//        btPontosColeta = activity.findViewById(R.id.btPontosColeta);
        addNovidadesLista();
        configCarrousel();

    }

    public void btPontosColeta() {
        Intent it = new Intent(activity, PontosDeColetaActivity.class);
        activity.startActivity(it);
    }


    private void configCarrousel() {
        carouselView.setPageCount(patrocinios.size());
        carouselView.setImageListener(imageListener);

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(activity.getApplicationContext()).load(patrocinios.get(position).getImage()).into(imageView);

//            imageView.setImageURI(patrocinios.get(position).getImage());
//            imageView.setImageResource(novidades.get(position).getImage());
        }
    };


    private void addNovidadesLista() {
        patrocinios = new ArrayList<>();
        patrocinios.add(new Patrocinio("https://i.ibb.co/zmnH1GS/braskem-site-patrocinio3.png"));
        patrocinios.add(new Patrocinio("https://i.ibb.co/yyG1wvx/banner2.png"));


    }

}
