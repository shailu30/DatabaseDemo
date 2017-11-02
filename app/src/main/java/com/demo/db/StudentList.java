package com.demo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.demo.db.model.StudModel;
import com.demo.db.model.StudModelOpt;
import java.util.ArrayList;
import java.util.List;

public class StudentList extends Fragment {

  public static final String TAG = StudentList.class.getSimpleName();
  View mView = null, mConvertView = null;
  SQLiteDatabase mDb = null;
  StudModelOpt mStudOpt = null;
  CustomAdapter customAdapter = null;
  List<StudModel> sListItem = null;
  Context mContext = null;
  StudModel studDetail = null;
  ListView listView = null;
  View.OnClickListener updateClick = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      FrDetailForm frDetail = new FrDetailForm();
      studDetail = sListItem.get(Integer.parseInt(v.getTag().toString()));
      Bundle b = new Bundle();
      b.putInt(StudModel.COL_ID, studDetail.getId());
      frDetail.setArguments(b);
      getActivity().getSupportFragmentManager().beginTransaction().
          replace(R.id.fragment_container, frDetail).commit();
    }
  };
  View.OnClickListener deleteClick = new View.OnClickListener() {

    @Override
    public void onClick(View v) {
      studDetail = sListItem.get(Integer.parseInt(v.getTag().toString()));
      mDb.delete(StudModel.TABLE_NAME, StudModel.COL_ID + " = ?",
          new String[]{studDetail.getId() + ""});
      sListItem.remove(Integer.parseInt(v.getTag().toString()));
      customAdapter.notifyDataSetChanged();

    }
  };

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mView = inflater.inflate(R.layout.student_listview, container, false);
    mStudOpt = new StudModelOpt(getActivity());
    mDb = mStudOpt.getReadableDatabase();
    mContext = getActivity();
    sListItem = new ArrayList<>();
    String[] cols = {StudModel.COL_ID, StudModel.COL_NAME, StudModel.COL_ADD, StudModel.COL_GENDER,
        StudModel.COL_PHONE};
    listView = mView.findViewById(R.id.studList);
    Cursor cr = mDb.query(StudModel.TABLE_NAME, cols, null, null, null, null, null);
    if (cr.moveToFirst()) {
      do {
        sListItem.add(new StudModel(cr.getInt(0), cr.getString(1), cr
            .getString(2), cr.getString(3), cr.getString(4)));
      } while (cr.moveToNext());
    }
    customAdapter = new CustomAdapter(getActivity(), R.layout.stud_listitem, sListItem) {
      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        mConvertView = convertView;
        if (mConvertView == null) {
          mConvertView = getActivity().getLayoutInflater().inflate(
              R.layout.stud_listitem, parent, false);
        }
        studDetail = sListItem.get(position);
        TextView name = mConvertView.findViewById(R.id.name);
        TextView add = mConvertView.findViewById(R.id.address);
        TextView gender = mConvertView.findViewById(R.id.gender);
        TextView phone = mConvertView.findViewById(R.id.phone);
        name.setText(studDetail.getName());
        add.setText(studDetail.getAdd());
        gender.setText(studDetail.getGender());
        phone.setText(studDetail.getPhone());
        mConvertView.findViewById(R.id.upd).setTag(position);
        mConvertView.findViewById(R.id.upd).setOnClickListener(updateClick);
        mConvertView.findViewById(R.id.del).setTag(position);
        mConvertView.findViewById(R.id.del).setOnClickListener(deleteClick);
        return mConvertView;
      }
    };
    listView.setAdapter(customAdapter);
    return mView;
  }

  @Override
  public void onResume() {
    super.onResume();
    if (sListItem.size() == 0) {
      Toast.makeText(getContext(), "No record found!", Toast.LENGTH_SHORT).show();
    }
  }
}
