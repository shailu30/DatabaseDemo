package com.demo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.demo.db.model.StudModel;
import com.demo.db.model.StudModelOpt;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

  public static final String TAG = CustomAdapter.class.getSimpleName();
  StudModelOpt mStud = null;
  SQLiteDatabase mDb = null;
  ListView listView = null;
  List<StudModel> listItems = null;
  List<StudModel> mAllListItems = null;
  Context mContext = null;
  ArrayAdapter<StudModel> ad = null;
  int mResource = 0;
  StudModel stud = null;

  public CustomAdapter(Context context, int resource, List<StudModel> modelItems) {
    super(context, resource, modelItems);
    mContext = context;
    mResource = resource;
    listItems = modelItems;
  }
}
