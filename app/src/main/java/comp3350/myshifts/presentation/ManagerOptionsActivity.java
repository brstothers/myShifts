package comp3350.myshifts.presentation;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import comp3350.myshifts.R;

public class ManagerOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_options);

        final Button btnManagerOpt1 = (Button) findViewById(R.id.btnManagerOpt1);
        setButtonListener(btnManagerOpt1,ManagerScheduleActivity.class);
        final Button btnManagerOpt2 = (Button) findViewById(R.id.btnManagerOpt2);
        setButtonListener(btnManagerOpt2,ManagerActivity.class);
    }

    public void setButtonListener(final Button b, final Class c){
        b.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    b.setBackgroundColor(Color.WHITE);
                    b.setTextSize(20);
                    ManagerOptionsActivity.this.startActivity(new Intent(ManagerOptionsActivity.this, c));
                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    b.setBackgroundColor(getResources().getColor(R.color.homeButtonColor));
                    b.setTextSize(26);
                }
                return false;
            }
        });
    }
}