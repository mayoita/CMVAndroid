package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.widget.LikeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Locale;

/**
 * Created by massimomoro on 30/01/17.
 */

public class QRCode extends Fragment {

    private CallbackManager mCallbackManager;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("QRCode");
    private static final String TAG = "QRCode";

    public static QRCode newInstance(String param1, String param2) {
        QRCode fragment = new QRCode();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public QRCode() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StarterApplication application = (StarterApplication) getActivity().getApplication();
        // mTracker = application.getDefaultTracker();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCallbackManager = CallbackManager.Factory.create();
        Typeface XLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GothamXLight.otf");
        View rootView = inflater.inflate(R.layout.qr_code, container, false);
        final ImageView qrCode = (ImageView)rootView.findViewById(R.id.qr_imageView);
        final TextView qr_text = (TextView)rootView.findViewById(R.id.qr_textView);
        qr_text.setText("No Internet Connection");
        qr_text.setMovementMethod(new ScrollingMovementMethod());
        final TextView footer_text = (TextView)rootView.findViewById(R.id.qr_footer);
        footer_text.setMovementMethod(new ScrollingMovementMethod());
        footer_text.setText("");
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final int width = display.getWidth();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Bitmap bitmap = null;
                try {

                    bitmap = encodeAsBitmap(dataSnapshot.child("code").getValue(String.class), BarcodeFormat.QR_CODE, width, width);
                    //bitmap = encodeAsBitmap(barcode_data);
                    qrCode.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
                switch (Locale.getDefault().getLanguage()) {
                    case "it":
                        qr_text.setText(dataSnapshot.child("textIT").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerIT").getValue(String.class));
                        break;
                    case "es":
                        qr_text.setText(dataSnapshot.child("textES").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerES").getValue(String.class));
                        break;
                    case "fr":
                        qr_text.setText(dataSnapshot.child("textFR").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerFR").getValue(String.class));
                        break;
                    case "de":
                        qr_text.setText(dataSnapshot.child("textDE").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerDE").getValue(String.class));
                        break;
                    case "ru":
                        qr_text.setText(dataSnapshot.child("textRU").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerRU").getValue(String.class));
                        break;
                    case "zh":
                        qr_text.setText(dataSnapshot.child("textZH").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footerZH").getValue(String.class));
                        break;
                    default:
                        qr_text.setText(dataSnapshot.child("text").getValue(String.class));
                        footer_text.setText(dataSnapshot.child("footer").getValue(String.class));
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return rootView;
    }


    /**************************************************************
     * getting from com.google.zxing.client.android.encode.QRCodeEncoder
     *
     * See the sites below
     * http://code.google.com/p/zxing/
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/EncodeActivity.java
     * http://code.google.com/p/zxing/source/browse/trunk/android/src/com/google/zxing/client/android/encode/QRCodeEncoder.java
     */
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 1000, 1000, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 1000, 0, 0, w, h);
        return bitmap;
    }
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        java.util.Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
