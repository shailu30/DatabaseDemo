package com.demo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.demo.db.model.StudModel;
import com.demo.db.model.StudModelOpt;


public class FrDetailForm extends Fragment implements View.OnClickListener {

  public static final String TAG = FrDetailForm.class.getSimpleName();
  View mView = null;
  SQLiteDatabase mDb = null;
  StudModelOpt studModelOpt = null;
  private EditText address, name, phone;
  private RadioGroup gender;
  private int id = 0;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.frg_create_record, container, false);
    Button create = mView.findViewById(R.id.sCreate);
    Button viewList = mView.findViewById(R.id.viewList);
    create.setOnClickListener(this);
    viewList.setOnClickListener(this);
    studModelOpt = new StudModelOpt(getActivity());
    mDb = studModelOpt.getWritableDatabase();
    name = mView.findViewById(R.id.sName);
    address = mView.findViewById(R.id.sAdd);
    phone = mView.findViewById(R.id.sPhone);
    gender = mView.findViewById(R.id.sGender);
    if (getArguments() != null) {
      setUpdateForm();
    }
    return mView;
  }

  private void setUpdateForm() {
    id = getArguments().getInt(StudModel.COL_ID);
    mDb = studModelOpt.getReadableDatabase();
    Cursor cr = mDb.query(StudModel.TABLE_NAME, new String[]{StudModel.COL_ID,
            StudModel.COL_NAME, StudModel.COL_ADD, StudModel.COL_GENDER, StudModel.COL_PHONE},
        "id = ?", new String[]{String.valueOf(id)}, null, null, null);
    ((Button) mView.findViewById(R.id.sCreate)).setText("Update");
    if (cr.moveToFirst()) {
      name.setText(cr.getString(cr.getColumnIndex(StudModel.COL_NAME)));
      address.setText(cr.getString(cr.getColumnIndex(StudModel.COL_ADD)));
      phone.setText(cr.getString(cr.getColumnIndex(StudModel.COL_PHONE)));
      name.setText(cr.getString(cr.getColumnIndex(StudModel.COL_NAME)));
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.sCreate:
        int gender_id, updateDone = 0;
        long done = 0;
        String sname, saddress, sphone, sgender, action;
        saddress = address.getText().toString();
        sphone = phone.getText().toString();
        sname = ((EditText) mView.findViewById(R.id.sName)).getText().toString();
        sgender = getGender(gender);
        ContentValues cv = new ContentValues();
        cv.put(StudModel.COL_NAME, sname);
        cv.put(StudModel.COL_ADD, saddress);
        cv.put(StudModel.COL_PHONE, sphone);
        cv.put(StudModel.COL_NAME, sname);
        cv.put(StudModel.COL_GENDER, sgender);
        if (getArguments() != null) {
          updateDone = mDb
              .update(StudModel.TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
          action = "Updated";
          ((Button) mView.findViewById(R.id.sCreate)).setText("Create");
        } else {
          done = mDb.insert(StudModel.TABLE_NAME, null, cv);
          action = "created";
        }

        if (done > 0 || updateDone > 0) {
          Toast.makeText(getActivity(), "Data " + action, Toast.LENGTH_LONG).show();
          ((EditText) mView.findViewById(R.id.sName)).setText("");
          address.setText("");
          phone.setText("");
          gender.clearCheck();
        }
        break;
      case R.id.viewList:
        StudentList list = new StudentList();
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("").
            replace(R.id.fragment_container, list).commit();
        break;
    }
  }

  private String getGender(RadioGroup gender) {
    int id = gender.getCheckedRadioButtonId();
    if (id < 0) {
      return "Male";
    }
    View radioButton = gender.findViewById(id);
    int radioId = gender.indexOfChild(radioButton);
    RadioButton btn = (RadioButton) gender.getChildAt(radioId);
    String selection = btn.getText().toString();
    return selection;
  }
}
