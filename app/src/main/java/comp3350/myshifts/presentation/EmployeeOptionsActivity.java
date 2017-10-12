package comp3350.myshifts.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import comp3350.myshifts.R;

public class EmployeeOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_options);

        final Button empOpt1 = (Button) findViewById(R.id.btnManagerOpt1);
        final Button empOpt2 = (Button) findViewById(R.id.btnManagerOpt2);

        empOpt1.setText(getResources().getString(R.string.viewSchedules));
        empOpt2.setText(getResources().getString(R.string.viewEmployees));

        setButtonListener(empOpt1,EmployeeScheduleActivity.class);
        setButtonListener(empOpt2,EmployeeActivity.class);
    }

    public void setButtonListener(final Button b, final Class c){
        b.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    b.setBackgroundColor(Color.WHITE);
                    b.setTextSize(20);
                    EmployeeOptionsActivity.this.startActivity(new Intent(EmployeeOptionsActivity.this, c));
                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    b.setBackgroundColor(getResources().getColor(R.color.homeButtonColor));
                    b.setTextSize(26);
                }
                return false;
            }
        });
    }

}