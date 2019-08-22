package com.example.clientsms;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsMessage;


public class SmsReceiver extends BroadcastReceiver {
    int messageId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
// Минимальные проверки 
        if (intent != null && intent.getAction() != null) {
// Получаем сообщения

            Object[] pdus = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            String smsFromPhone = messages[0].getDisplayOriginatingAddress();
            StringBuilder body = new StringBuilder();

            for (int i = 0; i < messages.length; i++) {
                body.append(messages[i].getMessageBody());
            }

            String bodyText = body.toString();
            makeNote(context, smsFromPhone, bodyText);


// Это будет работать только на Android ниже 4.4 
            abortBroadcast();

        }
    }

    private void makeNote(Context context, String addressFrom, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "2")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(String.format("Sms [%s]", addressFrom))
                .setContentText(message);

        Intent resultIntent = new Intent(context, SmsReceiver.class);

        android.support.v4.app.TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(messageId++, builder.build());


    }


}