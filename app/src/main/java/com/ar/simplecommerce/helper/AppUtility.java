package com.ar.simplecommerce.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by RadyaLabs PC on 28/09/2017.
 */

public class AppUtility {

    public static boolean ENABLE_LOG = true;

    public AppUtility() {
    }

    public static void showToast(Activity context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToastFailedGetingData(Activity context) {
        Toast.makeText(context, "Failed getting data from server", Toast.LENGTH_SHORT).show();
    }

    public static void showToastExpiredToken(Activity context) {
        Toast.makeText(context, "Token expired", Toast.LENGTH_SHORT).show();
    }

    public static void showToastConnectionProblem(Activity context) {
        Toast.makeText(context, "Connection problem", Toast.LENGTH_SHORT).show();
    }

    public static String formatMoney(String prefix, long money, char separator, String end) {
        return prefix + String.format("%,d", new Object[]{Long.valueOf(money)}).replace(',', separator) + end;
    }

    public static void logD(String TAG, String msg) {
        if (ENABLE_LOG) {
            try {
                if (msg == null) {
                    throw new NullPointerException();
                }

                Log.d(TAG, msg);
            } catch (NullPointerException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void runSplashDelay(final Activity from, final Class<?> to, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                intentTo(from, to, true);
            }
        }, delay);
    }

    public static void intentTo(final Activity from, final Class<?> to, final boolean isFinish){
        from.startActivity(new Intent(from, to));
        if (isFinish){
            from.finish();
        }
    }

    public static void intentTo(final Activity from, final Class<?> to, final boolean isFinish, Serializable data){
        from.startActivity(new Intent(from, to)
                .putExtra("data", data));
        if (isFinish){
            from.finish();
        }
    }

    public static float pixelsToSp(Context context, Float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px.floatValue() / scaledDensity;
    }

    public static float spToPixel(Context context, Float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp.floatValue() * scaledDensity;
    }

    public static int getDip(int value, Context context) {
        return (int) TypedValue.applyDimension(1, (float) value, context.getResources().getDisplayMetrics());
    }

    public static int getDip(int value, DisplayMetrics display) {
        return (int) TypedValue.applyDimension(1, (float) value, display);
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((double) ((float) dp * displayMetrics.density) + 0.5D);
    }

    public static ProgressDialog showLoading(ProgressDialog _progress, Context context, String title, String message, boolean indeterminate) {
        if (_progress == null) {
            _progress = ProgressDialog.show(context, title, message, indeterminate);
        } else {
            if (_progress.isShowing()) {
                _progress.dismiss();
            }

            _progress = ProgressDialog.show(context, title, message, indeterminate);
        }

        return _progress;
    }

    public static ProgressDialog showLoading(ProgressDialog _progress, Activity activity) {
        return showLoading(_progress, activity, "", "Loading...", true);
    }

    public static ProgressDialog showLoading(ProgressDialog _progress, Activity activity, boolean indeterminate) {
        return showLoading(_progress, activity, "", "Loading...", indeterminate);
    }

    public static ProgressDialog showLoading(ProgressDialog _progress, Activity activity, String text) {
        return showLoading(_progress, activity, "", text, true);
    }

    public static AlertDialog showAlertOneNeutral(Activity act, String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setMessage(text);
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        return alert.show();
    }

    public static AlertDialog showAlertOneNeutral(Activity act, String text, final Runnable onNeutralPressed, final Runnable onCancelListener) {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setMessage(text);
        alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (onNeutralPressed != null) {
                    onNeutralPressed.run();
                }

            }
        });
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (onCancelListener != null) {
                    onCancelListener.run();
                }

            }
        });
        return alert.show();
    }

    public static AlertDialog showAlertYesNo(Activity act, String textContent, String textYes, String textNo, final Runnable onYesPressed, final Runnable onNoPressed) {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setMessage(textContent);
        alert.setPositiveButton(textYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (onYesPressed != null) {
                    onYesPressed.run();
                }

            }
        });
        alert.setNegativeButton(textNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (onNoPressed != null) {
                    onNoPressed.run();
                }

            }
        });
        return alert.show();
    }

    @TargetApi(13)
    public static Point getScreenSize(Activity act) {
        Point size = new Point();
        WindowManager w = act.getWindowManager();
        if (Build.VERSION.SDK_INT >= 13) {
            w.getDefaultDisplay().getSize(size);
        } else {
            Display d = w.getDefaultDisplay();
            size.x = d.getWidth();
            size.y = d.getHeight();
        }

        return size;
    }

    public static Point getScreenSize(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Point size = new Point();
        size.x = metrics.widthPixels;
        size.y = metrics.heightPixels;
        return size;
    }

    @TargetApi(16)
    public static void removeGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT < 16) {
            view.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }

    }

    @TargetApi(16)
    public static void setBackground(View view, Drawable draw) {
        if (Build.VERSION.SDK_INT < 16) {
            view.setBackgroundDrawable(draw);
        } else {
            view.setBackground(draw);
        }

    }

    public static String getStringFile(InputStream input) throws IOException {
        InputStreamReader is = new InputStreamReader(input);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);

        for (String read = br.readLine(); read != null; read = br.readLine()) {
            sb.append(read);
        }

        return sb.toString();
    }

    public static Point getSizeAspecRatioWithInputWidth(int widthDesired, int widthBitmap, int heightBitmap) {
        int heightDesired = widthDesired * heightBitmap / widthBitmap;
        Point point = new Point(widthDesired, heightDesired);
        return point;
    }

    public static Point getSizeAspecRatioWithInputHeight(int heightDesired, int widthBitmap, int heightBitmap) {
        int widthDesired = heightDesired * widthBitmap / heightBitmap;
        Point point = new Point(widthDesired, heightDesired);
        return point;
    }

    public static Double getJarak(Double latitude, Double longitude, Double cabangLatitude, Double cabangLongitude) {
        double R = 6371.0D;
        double dLat = Math.toRadians(cabangLatitude.doubleValue() - latitude.doubleValue());
        double dLon = Math.toRadians(cabangLongitude.doubleValue() - longitude.doubleValue());
        double lat1 = Math.toRadians(latitude.doubleValue());
        double lat2 = Math.toRadians(cabangLatitude.doubleValue());
        double a = Math.sin(dLat / 2.0D) * Math.sin(dLat / 2.0D) + Math.sin(dLon / 2.0D) * Math.sin(dLon / 2.0D) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2.0D * Math.atan2(Math.sqrt(a), Math.sqrt(1.0D - a));
        double d = R * c;
        return Double.valueOf(d);
    }

    public static boolean deleteFolder(File dir) {
        if (dir.exists()) {
            File[] fileList = dir.listFiles();

            for (int i = 0; i < fileList.length; ++i) {
                if (fileList[i].isDirectory()) {
                    deleteFolder(fileList[i]);
                } else {
                    fileList[i].delete();
                }
            }

            return dir.delete();
        } else {
            return false;
        }
    }

    @TargetApi(11)
    public static void setAlpha(float alpha, View view) {
        if (Build.VERSION.SDK_INT >= 11) {
            view.setAlpha(alpha);
        } else {
            int intAlpha = (int) (255.0F * alpha);
            view.setAlpha((float) intAlpha);
        }

    }

    @TargetApi(16)
    public static void setBackgroundDrawable(Drawable drawable, View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }

    }

}
