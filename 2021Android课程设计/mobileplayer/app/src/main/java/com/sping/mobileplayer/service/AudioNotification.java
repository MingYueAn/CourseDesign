package com.sping.mobileplayer.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.activity.AudioPlayerActivity;


/**
 * 通知
 */
class AudioNotification {
    /**
     * 音乐播放通知栏ID
     */
    private static final int NOTIFY_ID = 1010;
    private static final String CHANNEL_ID = "AudioNotification";
    private final NotificationManager mNotificationManager;

    AudioNotification(AudioPlayService context) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    //开始播放
    void notifyStart(AudioPlayService service) {
        if (buildNotification(service) != null) {
            service.startForeground(NOTIFY_ID, buildNotification(service));
            //mNotificationManager.notify(NOTIFY_ID,buildNotification(service));
        }
    }

    //播放暂停通知
    void notifyPause(AudioPlayService service) {
        service.stopForeground(false);
        if (buildNotification(service) != null) {
            mNotificationManager.notify(NOTIFY_ID, buildNotification(service));
        }
    }

    //取消通知
    void stopNotify(AudioPlayService service) {
        service.stopForeground(true);
        mNotificationManager.cancelAll();
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        String name = "媒体播放";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription("媒体播放控制");
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        channel.setSound(null, null);//关闭通知声音
        mNotificationManager.createNotificationChannel(channel);
    }

    //
    private Notification buildNotification(AudioPlayService audioPlayService) {
        PendingIntent playIntent = createAction(audioPlayService, AudioPlayService.ACTION_PLAY);
        PendingIntent pauseIntent = createAction(audioPlayService, AudioPlayService.ACTION_PAUSE);
        PendingIntent nextIntent = createAction(audioPlayService, AudioPlayService.ACTION_NEXT);
        PendingIntent preIntent = createAction(audioPlayService, AudioPlayService.ACTION_PREV);

        Intent intent = new Intent(audioPlayService, AudioPlayerActivity.class);
        intent.setAction(AudioPlayService.ACTION_NOTIFY);
        PendingIntent contentIntent = PendingIntent.getActivity(audioPlayService, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        boolean isPlaying = audioPlayService.isPlaying();
        Drawable drawable = ContextCompat.getDrawable(audioPlayService, R.drawable.icon_notification_bitmap);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        MediaSession session = audioPlayService.getMediaSession();
        MediaController controller = session.getController();

        if (controller.getMetadata() != null) {
            MediaMetadata metadata = controller.getMetadata();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(audioPlayService, CHANNEL_ID);
            builder.setContentTitle(metadata.getString(MediaMetadata.METADATA_KEY_TITLE))
                    .setContentText(metadata.getString(MediaMetadata.METADATA_KEY_ARTIST))
                    .setSmallIcon(R.drawable.ic_notification)
                    .setShowWhen(false)
                    .setOngoing(isPlaying)
                    .setContentIntent(contentIntent)
                    .setLargeIcon(bitmap)
                    .addAction(R.drawable.ic_notification_pre, "", preIntent)
                    .addAction(isPlaying ? R.drawable.ic_notification_pause : R.drawable.ic_notification_play, "", isPlaying ? pauseIntent : playIntent)
                    .addAction(R.drawable.ic_notification_next, "", nextIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setStyle(new MediaStyle().setShowActionsInCompactView(0, 1, 2));//按添加顺序显示

            return builder.build();
        }


        return null;
    }

    private PendingIntent createAction(Context context, String action) {
        Intent intent = new Intent(context, AudioPlayService.class);
        intent.setAction(action);
        return PendingIntent.getService(context, 0, intent, 0);
    }

}
