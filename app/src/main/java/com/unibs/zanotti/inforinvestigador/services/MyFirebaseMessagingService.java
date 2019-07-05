package com.unibs.zanotti.inforinvestigador.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.paperdetail.PaperDetailActivity;
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private final static AtomicInteger notificationIdGenerator = new AtomicInteger(1);
    private static final String TAG = String.valueOf(MyFirebaseMessagingService.class);
    public static final String COMMENTS_CHANNEL = "com.unibs.zanotti.inforinvestigador.notifications.channel.comments";
    private static final String NOTIFICATION_TYPE_COMMENT = "comment";

    private IUserRepository userRepository;

    public MyFirebaseMessagingService() {
        userRepository = Injection.provideUserRepository();
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, String.format("Recevied firebase notification: \n%s", remoteMessage.toString()));

        Map<String, String> data = remoteMessage.getData();
        String notificationType = data.get("notificationType");
        if (StringUtils.isNotBlank(notificationType) && notificationType.equals(NOTIFICATION_TYPE_COMMENT)) {
            createCommentsNotificationChannel(this);
            showCommentNotification(data);
        }
    }

    private void showCommentNotification(Map<String, String> data) {
        String commentAuthorId = data.get("commentAuthorId");
        if(commentAuthorId != null && !commentAuthorId.equals(userRepository.getCurrentUserId())) {
            String notificationTitle = String.format("%s %s",
                    data.get("commentAuthorName"),
                    getString(R.string.comment_notifications_title));

            Intent showPaperIntent = new Intent(Actions.SHOW_PAPER_DETAILS);
            showPaperIntent.putExtra(PaperDetailActivity.STRING_EXTRA_PAPER_ID, data.get("commentedPaperId"));
            PendingIntent contentIntent = TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(showPaperIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, COMMENTS_CHANNEL);
            builder.setContentTitle(notificationTitle)
                    .setContentText(data.get("body"))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("body")))
                    .setSmallIcon(R.drawable.ic_comment_black_24dp)
                    .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                    .setContentIntent(contentIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(notificationIdGenerator.getAndIncrement(), builder.build());
        }
    }

    public void createCommentsNotificationChannel(Context context) {
        // From Android 8.0 Oreo (API Level 26) all Notifications must be associated with a Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    COMMENTS_CHANNEL,
                    context.getString(R.string.comments_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
