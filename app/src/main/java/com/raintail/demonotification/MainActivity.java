
package com.raintail.demonotification;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.UserHandle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private Button sendNotification;
    private Button sendMorLineNotification;
    private Button sendBigPicNotification;
    private Button sendListNotification;
    private Button sendActionNotification;
    private Button sendProgressNotification;
    private Button sendMediaNotification;
    private Button sendCustomerNotification;
    private Button cancelNotification, cancle_normal_notification, send_customer_chronoNotification;

    private NotificationManager mNotificationManager;
    private Builder builder;

    private ImageView ovaliv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sendNotification = (Button) findViewById(R.id.send_notification);
        sendMorLineNotification = (Button) findViewById(R.id.send_more_line_notification);
        sendBigPicNotification = (Button) findViewById(R.id.send_big_pic_notification);
        sendListNotification = (Button) findViewById(R.id.send_list_notification);
        sendActionNotification = (Button) findViewById(R.id.send_action_notification);
        sendProgressNotification = (Button) findViewById(R.id.send_progress_notification);
        sendMediaNotification = (Button) findViewById(R.id.send_media_notification);
        sendCustomerNotification = (Button) findViewById(R.id.send_customer_notification);
        cancelNotification = (Button) findViewById(R.id.cancle_notification);
        cancle_normal_notification = (Button) findViewById(R.id.cancle_normal_notification);
        send_customer_chronoNotification = (Button) findViewById(
                R.id.send_customer_chronoNotification);

        sendNotification.setOnClickListener(this);
        sendMorLineNotification.setOnClickListener(this);
        sendBigPicNotification.setOnClickListener(this);
        sendListNotification.setOnClickListener(this);
        sendActionNotification.setOnClickListener(this);
        sendProgressNotification.setOnClickListener(this);
        sendMediaNotification.setOnClickListener(this);
        sendCustomerNotification.setOnClickListener(this);
        cancelNotification.setOnClickListener(this);
        cancle_normal_notification.setOnClickListener(this);
        send_customer_chronoNotification.setOnClickListener(this);

        findViewById(R.id.test_clip_bounds).setOnClickListener(this);

        mNotificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);


        ovaliv = (ImageView) findViewById(R.id.ovaliv);
        ovaliv.setImageBitmap(createGoogleBrowser());

        findViewById(R.id.send_headup_notification).setOnClickListener(this);
        findViewById(R.id.send_headup_notification3).setOnClickListener(this);

        register();
    }

    int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onResume() {
        super.onResume();
//        View decorView = getWindow().getDecorView();
////        decorView.setSystemUiVisibility(uiOptions);
    }

    public void createBuilder(String ticker, String title, String content, int smallIcon
            , PendingIntent intent, boolean sound, boolean vibrate, boolean lights) {
        builder = new Builder(this);
        builder.setTicker(ticker);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setColor(Color.DKGRAY);
//        builder.setSubText("subText测试测试");
        builder.setSmallIcon(smallIcon);
//        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(intent);
//        builder.setLargeIcon(
//                BitmapFactory.decodeResource(getResources(), R.drawable.help_ic_delete_p));
        builder.setLargeIcon(createGoogleBrowser());
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true); // TODO
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        int defaults = 0;
        if (true) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        builder.setDefaults(defaults);
//        builder.setOngoing(true); // TODO ？

    }


    private void showCustomerNotification(int flag) {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Customer", "自定义通知", "showCustomerNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.remoteview);
        contentView.setTextViewText(R.id.share_content, "自定义的view");
        contentView.setOnClickPendingIntent(R.id.share_facebook, pendingIntent);
        contentView.setOnClickPendingIntent(R.id.share_twitter, pendingIntent);

        RemoteViews bigContentView = new RemoteViews(getPackageName(), R.layout.bigcontentview);
        bigContentView.setTextViewText(R.id.share_content, "自定义的bigView");
        bigContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
        bigContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);

        builder.setPriority(Notification.PRIORITY_HIGH);
//
//        builder.setFullScreenIntent(pendingIntent, true);
//
        // TODO: 16-12-21  可以测试此处
        Notification notification = builder.build();
        notification.contentView = contentView;
        notification.bigContentView = bigContentView;
        if (flag == 19) {
            RemoteViews headUpContentView = new RemoteViews(getPackageName(), R.layout.headupcontentview);
            headUpContentView.setTextViewText(R.id.share_content, "自定义的headUp");
            headUpContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
            headUpContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);
            notification.headsUpContentView = headUpContentView;
            notification.flags |= Notification.FLAG_NO_CLEAR;
        } else if (flag == 3) {
            RemoteViews headUpContentView = new RemoteViews(getPackageName(), R.layout.headupcontentview2);
            headUpContentView.setTextViewText(R.id.share_content, "666773653653333");
            headUpContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
            headUpContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);
            notification.headsUpContentView = headUpContentView;
        }
        send(flag, notification);
    }


    private void showDefaultNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("DefaultNotification", "普通通知", "DefaultNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
        builder.setPriority(Notification.PRIORITY_MAX);
//        builder.setPriority(NotificationCompat.PRIORITY_MAX);
//        builder.setAutoCancel(false);
//        builder.setFullScreenIntent(pendingIntent, true);
//        builder.addAction(R.drawable.ic_launcher, "菜单1", pendingIntent);
        builder.setAutoCancel(false);
//        builder.setProgress(100, 60, false);
        builder.setSubText("sub text"); // show in header, 老版本显示在title下方
//        builder.setContentInfo("content info"); // < N 才会显示在header
//        builder.setUsesChronometer(true); // 定时器，记录发送消息到现在的时长
        Notification notification = builder.build();
//        notification.flags |= Notification.FLAG_NO_CLEAR;
        send(1, notification);
//        View view = View.inflate(this, R.layout.remoteview, null);
//        Log.i("licong10", view.getId() + "--end"); // it is -1

    }

    private void showMoreLineNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("MoreLine", "多行通知", "showMoreLineNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
//        builder.setFullScreenIntent(pendingIntent, true);
//        builder.addAction(R.drawable.ic_launcher, "菜单1", pendingIntent);
        Notification notification = new NotificationCompat.BigTextStyle(builder)
                .bigText("观察者网综合：韩国被弹劾总统朴槿惠近日可谓麻烦不断，韩媒17日曝出她在2005年曾向" +
                        "时任朝鲜国防委员会委员长金正日写信，希望能够促进一些朝韩交流事项。虽然信中内容在" +
                        "当年的情形下并不无妥，但由于在信中语气谦卑，这在当前朝韩半岛局势紧张的背景下又一次" +
                        "拉低了民众对她的印象. 韩国《京乡新闻》12月17日报道称，时任大国家党（现新世界党）" +
                        "党首、欧洲韩国财团理事的朴槿惠，在2005年7月13日向当时的朝鲜国防委员会委员长金正日" +
                        "发送信函。在信中，朴槿惠希望可以尽快落实自己在2002年访朝时敲定的一些朝韩交流事项。" +
                        "为此朴槿惠希望朝鲜方面可以尽快完成欧洲韩国财团在朝办事处的建设，" +
                        "并允许欧洲韩国财团相关人员可以自由访朝。")
                .build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        send(2, notification);
    }

    private void showBigPicNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("BigPic", "大图通知", "showBigPicNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
        Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.big_bitmap);
        Bitmap bigLargeIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.weather_ic_notice_blue);
        Notification notification = new NotificationCompat.BigPictureStyle(builder)
                .bigLargeIcon(bigLargeIcon).bigPicture(bigPicture).build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        send(3, notification);
    }

    private void showListNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("ListNotification", "列表型通知", "showListNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);
        ArrayList<String> contents = new ArrayList<String>();
        contents.add("第一条消息");
        contents.add("第二条消息");
        contents.add("第三条消息");
        contents.add("第四条消息");
        contents.add("第五条消息");
        for (String content : contents) {
            style.addLine(content);
        }
        style.setSummaryText(contents.size() + "条消息");
        style.setBigContentTitle("bigContentTitle");
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        send(4, notification);
    }

    private void showActionNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("ActionNotification", "带按钮的通知", "ActionNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
        builder.addAction(R.drawable.help_ic_delete_p, "第一个按钮", pendingIntent);
        // builder.addAction(R.drawable.weather_ic_notice_orange, "第二个按钮",
        // pendingIntent);
        // builder.addAction(R.drawable.weather_ic_notice_blue, "第三个按钮",
        // pendingIntent);
        // builder.addAction(R.drawable.weather_ic_notice_yellow, "第四个按钮",
        // pendingIntent);
        Notification notification = builder.build();
        send(5, notification);
    }

    private void showProgressNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Progress", "带进度条的通知", "showProgressNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
        final Builder progressBuilder = builder;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    progressBuilder.setProgress(100, i, false);
                    send(6, progressBuilder.build());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBuilder.setContentText("下载完成").setProgress(0, 0, false);
                send(6, progressBuilder.build());
            }
        }).start();
    }

    public final String ACTION_PLAY = "action_play";
    public final String ACTION_PAUSE = "action_pause";
    public final String ACTION_REWIND = "action_rewind";
    public final String ACTION_FAST_FORWARD = "action_fast_foward";
    public final String ACTION_NEXT = "action_next";
    public final String ACTION_PREVIOUS = "action_previous";
    public final String ACTION_STOP = "action_stop";

    /**
     * this is action notification, not media!!! TODO
     */
    private void showMediaNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Media", "媒体通知", "showMediaNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);
//        builder.setSubText("SubText");
        builder.addAction(
                generateAction(android.R.drawable.ic_media_previous, "Previous", ACTION_PREVIOUS));
        builder.addAction(generateAction(android.R.drawable.ic_media_rew, "Rewind", ACTION_REWIND));
        builder.addAction(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY));
        builder.addAction(
                generateAction(android.R.drawable.ic_media_ff, "Fast Foward", ACTION_FAST_FORWARD));
        builder.addAction(generateAction(android.R.drawable.ic_media_next, "Next", ACTION_NEXT));
        send(7, builder.build());
    }

    private Action generateAction(int icon, String title, String intentAction) {
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        intent.setAction(intentAction);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new Action(icon, title, pendingIntent);
    }

    private void showChronometerNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Action", "Chronometer", "showChronometerNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);

//        builder.setFullScreenIntent(pendingIntent, true);
        builder.addAction(R.drawable.ic_launcher, "菜单1", pendingIntent);
//        builder.setUsesChronometer(true);

        Notification notification = builder.build();
        send(9, notification);
        Toast.makeText(this, "myClick", Toast.LENGTH_LONG).show();
    }

    private void send(int id, Notification notification) {
        mNotificationManager.notify(id, notification);
    }

    private void cancelNotification() {
        mNotificationManager.cancelAll();
//        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification:
                showDefaultNotification();
                break;
            case R.id.send_more_line_notification:
                showMoreLineNotification();
                break;
            case R.id.send_big_pic_notification:
                showBigPicNotification();
                break;
            case R.id.send_list_notification:
                showListNotification();
                break;
            case R.id.send_action_notification:
                showActionNotification();
                break;
            case R.id.send_progress_notification:
                showProgressNotification();
                break;
            case R.id.send_media_notification:
                showMediaNotification();
                break;
            case R.id.send_customer_notification:
                showCustomerNotification(8);
                break;
            case R.id.cancle_notification:
                cancelNotification();
                break;
            case R.id.test_clip_bounds:
                test_clip_bounds(v);
                break;
            case R.id.cancle_normal_notification:
                mNotificationManager.cancel(1);
                break;
            case R.id.send_customer_chronoNotification:
                showChronometerNotification();
                break;
            case R.id.send_headup_notification:
//                showCustomerNotification(19);
                showCustomerHeadUpNotification();
//                ffSend();
                break;
            case R.id.send_headup_notification3:
                showCustomerNotification(3);
                break;

            default:
                break;
        }
    }


    int flagNum = 10;
    Rect mRect;

    /**
     * clip 属性, 裁剪了显示区域
     * @param view
     */
    public void test_clip_bounds(View view) {
        view.getDrawingRect(new Rect());

        flagNum = flagNum + 10;
        if(mRect == null) {
            mRect = new Rect();
        }
        if(flagNum > 50) {
            flagNum = 0;
        }
        mRect.set(flagNum, flagNum, view.getWidth()-flagNum, view.getHeight()-flagNum);
        view.setClipBounds(mRect);

        Log.i(TAG, "" + view.getWidth() + ":" + view.getHeight());


//        startActivity(new Intent(this, TestActivity.class));

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("title")
                .setCancelable(true)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialogM.dismiss();
                    }
                });
        dialogM = builder.create();
        WindowManager.LayoutParams layoutParams = dialogM.getWindow().getAttributes();
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        dialogM.show();
        dialogM.getWindow().getDecorView().setSystemUiVisibility(uiOptions);

        sendNotification.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    WindowManager.LayoutParams layoutParams = dialogM.getWindow().getAttributes();
                    layoutParams.flags &= ~WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    getWindowManager().updateViewLayout(dialogM.getWindow().getDecorView(), layoutParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);

    }

    AlertDialog dialogM;


    private Bitmap createGoogleBrowser() {
        OvalShape ovalShape = new OvalShape();
        int value = (int) getResources().getDimension(R.dimen.notification_large_icon_width);
        ovalShape.resize(value, value);
        Paint paint = new Paint();
        paint.setColor(0xff607d8b);

        Bitmap localBitmap = Bitmap.createBitmap(value, value, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(localBitmap);
        ovalShape.draw(canvas, paint);

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.offline_pin),
                value/ 2, value/ 2, null);
        return localBitmap;
    }

    private void register() {
        registerReceiver(new MyBroadCast(), new IntentFilter("com.android.licong.demo"));
    }

    private PendingIntent buildPendingIntent() {
        final Intent intent = new Intent("com.android.licong.demo");
//        intent.setClassName(getPackageName(), "com.raintail.demonotification.MyBroadCast");
        return PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    int count = 100;

    private void showCustomerHeadUpNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        createBuilder("Customer", "自定义通知", "showCustomerNotification",
                R.drawable.help_ic_car_guide, pendingIntent, false, false, false);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.remoteview);
        contentView.setTextViewText(R.id.share_content, "自定义的view");
        contentView.setOnClickPendingIntent(R.id.share_facebook, pendingIntent);
        contentView.setOnClickPendingIntent(R.id.share_twitter, pendingIntent);

        RemoteViews bigContentView = new RemoteViews(getPackageName(), R.layout.bigcontentview);
        bigContentView.setTextViewText(R.id.share_content, "自定义的bigView");
        bigContentView.setOnClickPendingIntent(R.id.share_facebook_big, pendingIntent);
        bigContentView.setOnClickPendingIntent(R.id.share_twitter_big, pendingIntent);

        builder.setPriority(Notification.PRIORITY_MAX);
//
//        builder.setFullScreenIntent(pendingIntent, true);
//
        Notification notification = builder.build();
        notification.contentView = contentView;
        notification.bigContentView = bigContentView;
        RemoteViews headUpContentView = new RemoteViews(getPackageName(), R.layout.headupcontentview);
        headUpContentView.setTextViewText(R.id.share_content, "count: " + count);
        headUpContentView.setOnClickPendingIntent(R.id.share_facebook_big, buildPendingIntent());
        headUpContentView.setOnClickPendingIntent(R.id.share_twitter_big, buildPendingIntent());
        notification.headsUpContentView = headUpContentView;
//        notification.extras.putBoolean("showHeadUpAlways", true);
        send(99, notification);

        sendNotification.postDelayed(new Runnable() {
            @Override
            public void run() {
                count++;
                showCustomerHeadUpNotification();
            }
        }, 2500);

    }

    private void ffSend() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.headupcontentview);
        remoteViews.setTextViewText(R.id.share_content, "count: " + count);
        remoteViews.setOnClickPendingIntent(R.id.share_facebook_big, buildPendingIntent());
        remoteViews.setOnClickPendingIntent(R.id.share_twitter_big, buildPendingIntent());
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                (int) SystemClock.uptimeMillis(), new Intent(MainActivity.this, TestActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);


        Notification.Builder builder =  new Notification.Builder(this)
                .setTicker("aa")
                .setContentTitle("title")
                .setContentText("content")
                .setSmallIcon(R.drawable.help_ic_car_guide)
                .setContentIntent(pendingIntent)
                .setLargeIcon(createGoogleBrowser())
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setCustomContentView(remoteViews);
        Notification notification = builder.build();
        notification.contentView = remoteViews;
        notification.bigContentView = remoteViews;
        send(99, notification);
    }

}
