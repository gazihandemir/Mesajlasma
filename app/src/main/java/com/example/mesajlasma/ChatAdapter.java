package com.example.mesajlasma;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    Context context;
    List<MesajModel> list;
    Activity activity;
    String userName;
    Boolean state;
    // Kullanicinin gördügü ekran alıcının aldığı ekranı ayırt etmek için

    int viewSend = 1, viewReceiver = 2;

    public ChatAdapter(Context context, List<MesajModel> list, Activity activity, String userName) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.userName = userName;
        state = false;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == viewSend) {
            // Kullanıcı bakış açısı
            view = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.received_layout, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Layoutlarda yolladığımız ve aldıgımız mesajların gözükmesi.
        holder.textView.setText(list.get(position).getText().toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if (state) {
                //Kullanicinin gördüğü mesajlara göre TextView dizaynı

                textView = itemView.findViewById(R.id.tvSend);
            } else {
                textView = itemView.findViewById(R.id.tvReceived);
            }


        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(userName)) {
            // Eğer kullanici yazıklarına bakıyorsa

            state = true;
            return viewSend;
        } else {
            // Eğer kullanici aldıklarına bakıyorsa

            state = false;
            return viewReceiver;
        }
    }
}
