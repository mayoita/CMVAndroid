package it.casinovenezia.casinodivenezia;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by massimomoro on 30/03/15.
 */
public class CustomMenuTitleView extends TextView {

    private final static String GOTHAM_XLIGHT = "fonts/GothamXLight.otf"; //"fonts/Giorgio-Thin.ttf"

    public CustomMenuTitleView(Context context) {
        super(context);
    }

    public CustomMenuTitleView(Context context, AttributeSet attrs) {
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


    public CustomMenuTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


}
