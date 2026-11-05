
package com.emmasoul.aviator;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<Double> rounds = new ArrayList<>();
    LinearLayout layout;
    TextView stats, history;
    EditText multiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(28, 35, 28, 28);
        layout.setBackgroundColor(Color.rgb(12, 17, 29));

        TextView title = new TextView(this);
        title.setText("AVIATOR ANALYZER");
        title.setTextColor(Color.WHITE);
        title.setTextSize(26);
        title.setGravity(Gravity.CENTER);
        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("OUR OWN MULTIPLIER TRACKER");
        subtitle.setTextColor(Color.CYAN);
        subtitle.setGravity(Gravity.CENTER);
        layout.addView(subtitle);

        multiplier = new EditText(this);
        multiplier.setHint("Enter multiplier e.g. 2.45");
        multiplier.setSingleLine(true);
        multiplier.setInputType(8194);
        multiplier.setTextColor(Color.WHITE);
        multiplier.setHintTextColor(Color.GRAY);
        layout.addView(multiplier);

        Button add = new Button(this);
        add.setText("RECORD ROUND");
        layout.addView(add);

        stats = new TextView(this);
        stats.setTextColor(Color.WHITE);
        stats.setTextSize(16);
        layout.addView(stats);

        history = new TextView(this);
        history.setTextColor(Color.GREEN);
        history.setTextSize(15);
        layout.addView(history);

        add.setOnClickListener(v -> {
            try {
                double value = Double.parseDouble(
                    multiplier.getText().toString()
                );

                if (value < 1 || value > 1000000) {
                    Toast.makeText(this, "Enter a valid multiplier",
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                rounds.add(value);
                multiplier.setText("");
                updateDisplay();

            } catch (Exception e) {
                Toast.makeText(this, "Enter a multiplier first",
                    Toast.LENGTH_SHORT).show();
            }
        });

        updateDisplay();
        setContentView(layout);
    }

    void updateDisplay() {
        int above = 0;

        StringBuilder list = new StringBuilder("ROUND HISTORY\n");

        for (int i = rounds.size() - 1; i >= 0; i--) {
            double value = rounds.get(i);
            if (value >= 2.0) above++;

            list.append(String.format(java.util.Locale.US,
                "%.2fx\n", value));
        }

        int below = rounds.size() - above;
        double percentage = rounds.isEmpty() ? 0 :
            (above * 100.0 / rounds.size());

        stats.setText(String.format(java.util.Locale.US,
            "\nTotal rounds: %d\nAbove 2x: %d\nBelow 2x: %d\nAbove 2x rate: %.1f%%\n",
            rounds.size(), above, below, percentage));

        history.setText(list.toString());
    }
}
