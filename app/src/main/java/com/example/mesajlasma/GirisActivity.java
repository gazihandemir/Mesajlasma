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
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();


    }

    public void ekle(final String kAdi) {
        reference.child("Kullanicilar").child(kAdi).child("Kullanici").setValue(kAdi).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(GirisActivity.this, "Başarıyla giriş Yaptınız ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                    intent.putExtra("kAdi", kAdi);
                    startActivity(intent);
                }
            }
        });
    }

    public void girisYap(View view) {
        String kullaniciAdi = edKullaniciAdi.getText().toString();
        edKullaniciAdi.setText("");
        ekle(kullaniciAdi);
    }
}
