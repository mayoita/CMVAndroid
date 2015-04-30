package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by massimomoro on 02/04/15.
 */
public class CustomEditText extends EditText {

    private final static String GOTHAM_XLIGHT = "fonts/GothamXLight.otf";

    public CustomEditText(Context context) {
        super(context);


    }
    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), GOTHAM_XLIGHT);
            setTypeface(typeface);
        }
    }
}

