package edu.up.cs301.hex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private Hex_SurfaceView hexSurfaceView;
public HexState hexState;
private HexHumanPlayer humanPlayer;

    private TextView tv;
    private Hex_SurfaceView hv;
    private HexState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hexState = new HexState();
        hv = findViewById(R.id.hex_grid);
        tv = (TextView) findViewById(R.id.textView);
        hv.setHexState(hexState);
        ;

//            TextView myFaveTextView = new TextView(this);
//            myFaveTextView.setText("some text");
        ImageButton btn1 = (ImageButton) findViewById(R.id.settings_button);
        ImageButton btn2 = (ImageButton) findViewById(R.id.rules_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRuleBook();
            }
        });

    }

    //code by Chengen
    //
    //method to show the settings popup
    //contains volume seekbars for volume, sfx
    //
    public void ShowDialog() {
        AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(R.layout.activity_dialog,
                (ViewGroup) findViewById(R.id.layout_dialog));

        TextView item1 = (TextView) viewLayout.findViewById(R.id.textView);
        TextView item2 = (TextView) viewLayout.findViewById(R.id.textView2);

        popDialog.setTitle("Settings");
        popDialog.setView(viewLayout);

        SeekBar seek1 = (SeekBar) viewLayout.findViewById(R.id.seekBar);

        seek1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //tv.setText("Value of :" + progress);
                item1.setText("SFX Volume: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seek2 = (SeekBar) viewLayout.findViewById(R.id.seekBar2);
        seek2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                //tv.setText("Value of :" + progress);
                item2.setText("Music Volume: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        popDialog.create();
        popDialog.show();
    }

    //code by Chengen
    //
    //method for the rules popup
    //contains the textviews for the rules of the game
    //
    public void ShowRuleBook() {
        AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View viewLayout = inflater.inflate(R.layout.activity_rule,
                (ViewGroup) findViewById(R.id.layout_rule));

        TextView item1 = (TextView) viewLayout.findViewById(R.id.rule_title);
        TextView item2 = (TextView) viewLayout.findViewById(R.id.rule_text);

        popDialog.setTitle("How To Play");
        popDialog.setView(viewLayout);


        popDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        popDialog.create();
        popDialog.show();
    }


    /**
     * changeMax
     *
     * is called whenever the taps the Update Count button.
     * It increments the max count by 1.
     */
//        public void changeMax(View v) {
//            //find the TextView that contains the max number
//            TextView maxTextView = findViewById(R.id.textViewMaxValue);
//
//            //retrieve it's value (String)
//            String txtVal = maxTextView.getText().toString();
//
//            //convert the String to an int
//            int intVal = Integer.parseInt(txtVal);
//
//            //increment the int
//            intVal++;
//
//            //convert it back to a String and put back in the TextView
//            maxTextView.setText("" + intVal);
//
//
//            Button wesButton = findViewById(R.id.buttonUpdate);
//            wesButton.setText("Wes was here");
//
//        }
}