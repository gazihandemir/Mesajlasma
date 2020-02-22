package com.example.mesajlasma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String kAdi;
    // Kullanıcı adlarına tutacağımız arrayList'imizi tanımlıyoruz

    List<String> list;
    //RecyclerViewde istediğimiz şeylerin gözükmesi ve düzenlenmesi için Manager tanımlıyoruz.

    RecyclerView.LayoutManager layoutManager;
    //Yazdığımız Adaptorü kullanmak için tanımlıyoruz

    UserAdapter userAdapter;
    // RecyclerView'de verileri göstermek için tanımlıyoruz.

    RecyclerView userRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanımla();
        listele();

    }

    public void tanımla() {
        // Database kullanmak için nesne ve referans oluşturma.

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        // GirisActivity'den gelen yani kullanicinin EditText'e girdiği kullanıcı adını alıyoruz.

        kAdi = getIntent().getExtras().getString("kAdi");
        // Kullanicilar tutacağımız arrayList'imizi burada oluşturuyoruz ->initialize

        list = new ArrayList<>();
        userRecyclerView = findViewById(R.id.userRecyclerView);
        // LayoutManagere İhtiyaç duyuyoruz ve grid şeklinde gözükmesi için GridLayoutManager nesnesi oluşturuyoruz.
        // parametre -> Hangi activity'nin contexi , 1 satırda kaç tane eleman olsun

      layoutManager = new GridLayoutManager(MainActivity.this, 2);
        // gerekli bilgilerimizi Adapterde tanımladık ve parametrelerini yolluyoruz.

        userAdapter = new UserAdapter(MainActivity.this, list, MainActivity.this, kAdi);
        // adapter ve Manager set
        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(userAdapter);
    }

    public void listele() {
        // Kullanıcı resimleri static olarak herkes için aynı verilmiştir.
        // Kullanıcı isimleri dinamik olarak veri tabanına kaydediliyor ve oradan isimleri referans'ımız ile çekiyoruz.
        // Kullanicilar'ın verilerine referans gönderip verilerimizi çekiyoruz.

        reference.child("Kullanicilar").addChildEventListener(new ChildEventListener() {
            @Override
            // Eklenmiş childeri almak

            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Kayıt olan kullanıcılar kendileri ile sohbet edemesin diye oluşturulmuş if  koşulu
                // GirisActivity'deki EditText'e girilen kullanici adina eşit olmayanları ekle.

                if (!dataSnapshot.getKey().equals(kAdi)) {
                    // Kullanicilarimizi listemize ekliyoruz.

                    list.add(dataSnapshot.getKey());
                    Log.i("Kullanicilar", dataSnapshot.getKey());
                    // Güncel verileri alabilmek

                    userAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
