package com.demo.db;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.demo.db.model.StudModelOpt;

public class MainActivity extends AppCompatActivity implements OnClickListener {

  private FragmentManager fManager = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button cDatabase = findViewById(R.id.cDatabase);
    cDatabase.setOnClickListener(this);
    Button cRecords = findViewById(R.id.cRecords);
    cRecords.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.cDatabase:
        StudModelOpt modelOpt = new StudModelOpt(this);
        Toast.makeText(this, "Database & Model Created", Toast.LENGTH_SHORT).show();
        break;
      case R.id.cRecords:
        findViewById(R.id.buttons).setVisibility(View.GONE);
        FrDetailForm detailForm = new FrDetailForm();
        fManager = getSupportFragmentManager();
        fManager.beginTransaction().replace(R.id.fragment_container, detailForm).commit();
        break;
    }
  }
}
