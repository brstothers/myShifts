package comp3350.myshifts.presentation;

import comp3350.myshifts.R;
import comp3350.myshifts.application.Main;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        copyDatabaseToDevice();
        setContentView(R.layout.activity_home);
        Main.startUp();

        final Button btnManager = (Button) findViewById(R.id.buttonMainManager);
        setButtonListener(btnManager,ManagerOptionsActivity.class);

        final Button btnEmployee = (Button) findViewById(R.id.buttonMainEmployee);
        setButtonListener(btnEmployee,EmployeeOptionsActivity.class);
    }


    public void setButtonListener(final Button b, final Class c){
        b.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    b.setBackgroundColor(Color.WHITE);
                    b.setTextSize(20);
                    HomeActivity.this.startActivity(new Intent(HomeActivity.this, c));
                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    b.setBackgroundColor(getResources().getColor(R.color.homeButtonColor));
                    b.setTextSize(26);
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Main.shutDown();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException{
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;
            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }
                out.close();
                in.close();
            }
        }
    }

    private void copyDatabaseToDevice(){
        final String DB_PATH = "db";
        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try{
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++)
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            copyAssetsToDirectory(assetNames, dataDirectory);
            Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);
        } catch (IOException ioe) {
            //Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

}