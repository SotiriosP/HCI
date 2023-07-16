package com.example.test2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Button;


public class MenuButtonView extends RelativeLayout {

    public MenuButtonView(Context context) {
        super(context);
        init();
    }

    public MenuButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Create the button
        Button button = new Button(getContext());
        button.setText("Change Color Mode");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implement the functionality for changing the color mode
            }
        });

        // Add the button to the view
        addView(button);
    }
}
