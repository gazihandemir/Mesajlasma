package com.example.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<String> list;
    Activity activity;
    String userName;

    public UserAdapter(Context context, List<String> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName = userName;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // MainActivity'de Göstereceğimiz her bir View ne olsun.

        View view = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Pozisyona göre ekleme yapma

        holder.textView.setText(list.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent ile ChatActivity'e gidiyoruz

                Intent intent = new Intent(activity, ChatActivity.class);
                // ChatActivity'de kullanmak üzere kullanici ismini ve tıkladığı kullanicinin index değerindeli String'ini ChatActivity'ye yolluyoruz

                intent.putExtra("userName", userName);
                intent.putExtra("otherName", list.get(position).toString());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Kullanicilarimiz ne kadar ise o kadar uzunlukta veri göster

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvUserName);
            linearLayout = itemView.findViewById(R.id.userRootLayout);
        }
    }
}
