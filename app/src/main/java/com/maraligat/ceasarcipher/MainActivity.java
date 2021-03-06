package com.maraligat.ceasarcipher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Tejas Maraliga
 */


public class MainActivity extends AppCompatActivity {

    private static final String [] ALPHABETARRAY = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private static final String ALPHABETSTRING = "abcdefghijklmnopqrstuvwxyz";

    //Initizlize Java side variables for corresponding res objects.
    private Button b_encrypt;
    private Button b_decrypt;
    private Button b_clear;

    private EditText et_plainText;
    private EditText et_cipherText;
    private EditText et_shift;

    private TextView tv_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        //Assign "res" ids to the java side variables
        b_encrypt = (Button)findViewById(R.id.encrypt_b);
        b_decrypt = (Button)findViewById(R.id.decrypt_b);
        b_clear = (Button)findViewById(R.id.clear_b);

        et_plainText = (EditText)findViewById(R.id.plainText_et);
        et_cipherText = (EditText)findViewById(R.id.cipherText_et);
        et_shift = (EditText)findViewById(R.id.shift_et);

        tv_key = (TextView)findViewById(R.id.key_tv);

        et_plainText.setText("");
        et_cipherText.setText("");
        et_shift.setText("");
        tv_key.setText("Key");

        b_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Encrypt", "Button Pressed");
                encrypt();
            }
        });

        b_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Decrypt", "Button Pressed");
                decrypt();
            }
        });

        b_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_plainText.setText("");
                et_cipherText.setText("");
                et_shift.setText("");
                tv_key.setText("Key");
            }
        });


    }

    private ArrayList<String> createShiftedAlphabet(int shift){
        int temp = shift;

        //Make copy of the alphabet
        ArrayList<String> _shiftedAlphabet = new ArrayList<>(Arrays.asList(ALPHABETARRAY));

        //create shifted alphabet
        while(temp > 0){
            String _s =_shiftedAlphabet.remove(0);
            _shiftedAlphabet.add(_s);
            --temp;
        }
        Log.d("createShiftedAlphabet: ", _shiftedAlphabet.toString() + "Shift: " + shift);
        return _shiftedAlphabet;
    }

    private void printKey(ArrayList<String> shiftedList){
        String shiftedString = "";
        for(int i=0; i < shiftedList.size(); ++i){
            shiftedString += shiftedList.get(i);
        }
        tv_key.setText(ALPHABETSTRING + "\n" + shiftedString);

    }
    private int loadShift() {
        int _shift = 0;
        //Check if shift box is empty
        if(TextUtils.isEmpty(et_shift.getText().toString())){
            Toast.makeText(MainActivity.this, "Shift field cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            _shift = Integer.parseInt(et_shift.getText().toString());
        }

        //check for proper shift values
        if(_shift > 25 || _shift < 0) {
            Toast.makeText(MainActivity.this, "Illegal shift number: " + _shift, Toast.LENGTH_SHORT ).show();
            _shift = 0;
        }
        return _shift;
    }

    private void decrypt(){
        String answer = "";
        String encryptText = et_cipherText.getText().toString().replaceAll("\\s+","").toLowerCase();
        ArrayList<String> _shifted = new ArrayList<>();

        //Get shift number
        int _shift = loadShift();

        //Create shifted array with said shift number
        _shifted = createShiftedAlphabet(_shift);

        for(int i=0; i<encryptText.length(); ++i){
            Character character = encryptText.charAt(i);
            String s_Char = character.toString();
            int Index = _shifted.indexOf(s_Char);
            System.out.println(ALPHABETARRAY[Index]);
            answer = answer.concat(ALPHABETARRAY[Index]);
            System.out.println(answer);
        }
        tv_key.setText(answer);
    }

    private void encrypt(){
        String answer = "";
        String decryptText = et_plainText.getText().toString().replaceAll("\\s+","").toLowerCase();
        ArrayList<String> _shifted = new ArrayList<>();

        //Get shift number
        int _shift = loadShift();

        //Create shifted array with said shift number
        _shifted = createShiftedAlphabet(_shift);

        for(int i=0; i<decryptText.length(); ++i){
            Character character = decryptText.charAt(i);
            String s_Char = character.toString();
            int Index = ALPHABETSTRING.indexOf(s_Char);
            System.out.println(_shifted.get(Index));
            answer = answer.concat(_shifted.get(Index));
            System.out.println(answer);
        }
        tv_key.setText(answer);
    }
}
