package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {

    private Button plus;
    private Button minus;
    private Button navigateToSecondaryActivityButton;
    Integer val1;
    Integer val2;
    EditText text1;
    EditText text2;
    EditText text3;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    static int serviceStatus;

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);

        Button plus = findViewById(R.id.left_button);
        Button minus = findViewById(R.id.right_button);

        text1 = (EditText)findViewById(R.id.editText);
        text2 = (EditText)findViewById(R.id.editText5);
        text3 = (EditText)findViewById(R.id.left_edit_text);

        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateToSecondaryActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02SecondaryActivity.class);
                int numberOfClicks = Integer.parseInt(text1.getText().toString());
                        //Integer.parseInt(text2.getText().toString());
                int numberOfClicks2 = Integer.parseInt(text2.getText().toString());
                intent.putExtra("bla1", numberOfClicks);
                intent.putExtra("bla2", numberOfClicks2);

                startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String my_text1 = text1.getText().toString();
                String my_text2 = text2.getText().toString();
                try {
                    val1 = Integer.parseInt(my_text1);
                    val2 = Integer.parseInt(my_text2);
                    text3.setText(val1.toString() + " + " + val2.toString());
                } catch(NumberFormatException e) {
                    String resultCode = "Nuts";
                    Toast nuts = Toast.makeText(getApplicationContext(), "NUTS", Toast.LENGTH_LONG);
                    nuts.show();
                }

                if (serviceStatus == 0) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                    intent.putExtra("firstNumber", val1);
                    intent.putExtra("secondNumber", val2);
                    getApplicationContext().startService(intent);
                    serviceStatus = 1;
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String my_text1 = text1.getText().toString();
                String my_text2 = text2.getText().toString();
                try {
                    val1 = Integer.parseInt(my_text1);
                    val2 = Integer.parseInt(my_text2);
                    text3.setText(val1.toString() + " - " + val2.toString());
                } catch(NumberFormatException e) {
                    String resultCode = "Nuts";
                    Toast nuts = Toast.makeText(getApplicationContext(), "NUTS", Toast.LENGTH_LONG);
                    nuts.show();
                }

                if (serviceStatus == 0) {
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
                    intent.putExtra("firstNumber", val1);
                    intent.putExtra("secondNumber", val2);
                    getApplicationContext().startService(intent);
                    serviceStatus = 1;
                }
            }
        });

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("leftCount", text1.getText().toString());
        savedInstanceState.putString("rightCount", text2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("leftCount")) {
            text1.setText(savedInstanceState.getString("leftCount"));
        } else {
            text1.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
            text2.setText(savedInstanceState.getString("rightCount"));
        } else {
            text2.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
