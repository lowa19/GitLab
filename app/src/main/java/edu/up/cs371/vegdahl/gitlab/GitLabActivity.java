package edu.up.cs371.vegdahl.gitlab;
/**
 * class GitModActivity
 *
 * Allow text to be modified in simple ways with button-presses, and
 * images to be displayed.
 */


import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

public class GitLabActivity extends AppCompatActivity {

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image
    private Spinner spinner;
    private EditText editText;
    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_lab);

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editText);
        Button upperCase = (Button)findViewById(R.id.button6);
        Button random = (Button)findViewById(R.id.randomButton);
        random.setOnClickListener(new randomButtonListener());
        upperCase.setOnClickListener(new upperCaseListener());

        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new clearButtonListener());
        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        spinner = (Spinner)findViewById(R.id.spinner);
        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }
        

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

        Button copyButton = (Button)findViewById(R.id.copyNameButton);
        copyButton.setOnClickListener(new CopyButtonListener());

        Button lowerButton = (Button)findViewById(R.id.button7);
        lowerButton.setOnClickListener(new LowerButtonListener());

        Button reverseButton = (Button)findViewById(R.id.button4);
        reverseButton.setOnClickListener(new ReverseButtonListener());

        Button noPuncButton = (Button)findViewById(R.id.noPuncButton);
        noPuncButton.setOnClickListener(new PunctuationButtonListener());
    }

    private class randomButtonListener implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            String temp = editText.getText().toString();
            int length = temp.length();
            //int rand = (int)Math.random()*length;
            Random r = new Random();
            int index = r.nextInt(length);
            char c = (char)(r.nextInt(26) + 'a');
            temp = temp.substring(0,index+1) + c + temp.substring(index+1);
            editText.setText(temp);

        }
    }
    private class CopyButtonListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            editText.append(spinner.getSelectedItem().toString());
        }
    }

    private class upperCaseListener implements Button.OnClickListener
    {
        public void onClick(View v)
        {
            String temp;
            temp = editText.getText().toString().toUpperCase();
            editText.setText(temp);
        }
    }

    private class clearButtonListener implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            editText.setText("");
        }
    }
    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_git_lab, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }

    }

    private class LowerButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            String text = "" + editText.getText();
            text = text.toLowerCase();
            editText.setText(text);
        }
    }

    private class ReverseButtonListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            String text = "" + editText.getText();
            text = reverseString(text);
            editText.setText(text);
        }
    }

    private class PunctuationButtonListener implements Button.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            editText.setText(editText.getText().toString().replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " "));
        }
    }

    private String reverseString(String orig)
    {
        String reverse = "";

        for (int i = 0; i < orig.length(); i++)
        {
            reverse = reverse + orig.charAt(orig.length() - i - 1);
        }

        return reverse;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_git_lab);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_git_lab, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
