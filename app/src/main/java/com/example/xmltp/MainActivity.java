package com.example.xmltp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.BEG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Read_XML();
            }
        });
    }
    class MyText {
        public int Taille;
        public String Val;

        public MyText(int taille, String val)
        {
            Taille = taille;
            Val = val;
        }

        @Override
        public String toString() {
            return "Text de taille :"+Taille+", valeur :"+Val;
        }
    }
    public void Read_XML()  {
        String Str="<LAY><Group><Text Taille=\"45\">HI THERE</Text><button>Ready</button><Text Taille=\"50\">Seconde me</Text></Group><Group><button>Go</button><Text Taille=\"20\">Third</Text><Text Taille=\"30\">4th</Text></Group><LAY>";
        InputStream Stre=new ByteArrayInputStream(Str.getBytes(StandardCharsets.UTF_8));
        XmlPullParser par= Xml.newPullParser();
        List<MyText> Data=new ArrayList<MyText>();
        List<String> DataBut=new ArrayList<String>();
        try {
            par.setInput(Stre,null);
            par.next();
            while(true)
            {
                par.next();
                if(!par.getName().equals("Group"))break;
                while (true)
                {
                    par.next();
                    if (par.getName().equals("Text")) {
                        int taille=Integer.parseInt( par.getAttributeValue(null,"Taille"));
                        String value=par.nextText();
                        MyText txt = new MyText(taille,value);
                        Data.add(txt);
                    }else if(par.getName().equals("button"))
                    {
                        DataBut.add(par.nextText());
                    }
                    else if(par.getName().equals("Group"))
                    { break;}
                }
            }
        }
        catch (Exception E){
            Log.i(null, "Read_XML: EXITED WITH EXEP E="+E.getMessage() );
        }
        for (MyText Text: Data) {
            Log.i("Texts", "\nRead_XML: "+Text.toString());
        }
        for (String S :
                DataBut) {
            Log.i("Buttons", "Read_XML: button "+S);
        }
    }
}