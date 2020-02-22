package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisActivity extends AppCompatActivity {
    EditText edKullaniciAdi;
    Button btnGirisYap;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimlama();
    }

    public void tanimlama() {
        edKullaniciAdi = findViewById(R.id.edKullaniciAdi);
        btnGirisYap = findViewById(R.id.btnGirisYap);
        // FirebaseDatabase kullanmak için bir nesne oluşturuyoruz

        firebaseDatabase = FirebaseDatabase.getInstance();
        // referans oluşturuyoruz

        reference = firebaseDatabase.getReference();


    }

    public void ekle(final String kAdi) {
        // Kullanicilar -> Parametre Kullanici adi -> kullanici ->> değer atama

        reference.child("Kullanicilar").child(kAdi).child("Kullanici").setValue(kAdi)
                // işlem gerçekleştiyse

                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // işlem başarılı ise

                if (task.isSuccessful()) {

                    Toast.makeText(GirisActivity.this, "Başarıyla giriş Yaptınız ", Toast.LENGTH_SHORT).show();
                    // intent ile Giris activitye git
                    Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                    // GirisActivity'de kullanmak için kullanici adini gönderiyoruz.

                    intent.putExtra("kAdi", kAdi);
                    startActivity(intent);
                }
            }
        });
    }

    public void girisYap(View view) {
        // Giris yapmak için

        String kullaniciAdi = edKullaniciAdi.getText().toString();
        edKullaniciAdi.setText("");
        ekle(kullaniciAdi);
    }
}
