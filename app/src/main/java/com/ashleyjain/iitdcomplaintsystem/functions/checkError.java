package com.ashleyjain.iitdcomplaintsystem.functions;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by ashleyjain on 27/03/16.
 */
public class checkError implements TextWatcher {

    EditText editTextI;

    public checkError(EditText nameEdText ){            //Class constructor takes an edittext element as an argument
        this.editTextI = nameEdText;
        if(editTextI.getText().toString().length()==0){editTextI.setError("field cannot be left blank");}
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(editTextI.getText().toString().length()==0){
            editTextI.setError("Field cannot be left blank.");  //sets error if field is left blank
        }
    }
}
