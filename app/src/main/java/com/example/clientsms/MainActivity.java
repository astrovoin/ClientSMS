package com.example.clientsms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

// 1. Как подцепить класс SmsReceiver в MainActivity, чтобы входящие сообщения отсылались в textViev?
// 2. Не работает отправка смс, хотя все пермиссии включены и разрешены. Как с этим быть?

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.editText);
        final EditText phone = findViewById(R.id.phone);
        final TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String message = editText.getText().toString();
                String phoneM = phone.getText().toString();

                textView.setText("From " + phoneM + ": " + message);
                SmsManager.getDefault().sendTextMessage(phoneM, null, message, null, null);

            }
        });

    }


}
