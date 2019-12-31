package stylist.com.glamhub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchBy extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
 Spinner sItems,sItemsb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by);

        List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Select Model");
        spinnerArray.add("LEXUS");
        spinnerArray.add("B M W");
        spinnerArray.add("TOYOTA");
        spinnerArray.add("MERCEREDES BENZ");
        spinnerArray.add("Any");

        List<String> price = new ArrayList<String>();
        price.add("Select Price");
        price.add("1000");
        price.add("1500");
        price.add("20000");
        price.add("5000");
        price.add("Any");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems = (Spinner) findViewById(R.id.spnrgender);
        // final Button btnVerify=(Button) findViewById(R.id.btn_verify);
        sItems.setAdapter(adapter);
        // final EditText phoneNumber=(EditText) findViewById(R.id.txt_phone_number);
        //  final EditText countyCode=(EditText) findViewById(R.id.edt_country_code);
        // ArrayAdapter<String> adapter1=ArrayAdapter.createFromResource(this,R.)

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = sItems.getSelectedItem().toString();
                if (selected.equals("LEXUS")) {

                    Toast.makeText(SearchBy.this, "lexus", Toast.LENGTH_SHORT).show();
                } else if (selected.equals("BMW")) {

                    Toast.makeText(SearchBy.this, "bmw", Toast.LENGTH_SHORT).show();
                } else if (selected.equals("TOYOTA")) {

                    Toast.makeText(SearchBy.this, "toyota", Toast.LENGTH_SHORT).show();
                } else if (selected.equals("Rwanda")) {

                    Toast.makeText(SearchBy.this, "Kenya", Toast.LENGTH_SHORT).show();
                } else if (selected.equals("Burundi")) {

                    Toast.makeText(SearchBy.this, "Kenya", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchBy.this, "not pressed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> bb = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, price);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItemsb = (Spinner) findViewById(R.id.spnrcost);
        // final Button btnVerify=(Button) findViewById(R.id.btn_verify);
        sItemsb.setAdapter(bb);

        sItemsb.setOnItemSelectedListener(this);

        /*{
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = sItemsb.getSelectedItem().toString();
                if (selected.equals("LEXUS")) {

                    Toast.makeText(SearchBy.this, "lexus", Toast.LENGTH_SHORT).show();
                }
                else if (selected.equals("BMW")) {

                    Toast.makeText(SearchBy.this,"bmw", Toast.LENGTH_SHORT).show();
                }
                else if (selected.equals("TOYOTA")) {

                    Toast.makeText(SearchBy.this,"toyota", Toast.LENGTH_SHORT).show();
                }
                else if (selected.equals("Rwanda")) {

                    Toast.makeText(SearchBy.this, "Kenya", Toast.LENGTH_SHORT).show();
                }
                else if (selected.equals("Burundi")) {

                    Toast.makeText(SearchBy.this, "Kenya", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SearchBy.this, "not pressed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sItems=(Spinner)parent;
        sItemsb=(Spinner)parent;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
