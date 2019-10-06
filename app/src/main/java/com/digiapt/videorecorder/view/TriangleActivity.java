package com.digiapt.videorecorder.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.digiapt.videorecorder.R;

public class TriangleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        showTriangle(7);
    }

    private TextView triangle;
    private void showTriangle(int columns) {
        triangle = (TextView) findViewById(R.id.triangle);

        int centralElement = (columns+1)/2 - 1;
        StringBuilder triangleBuilder = new StringBuilder();
        for(int i=0; i<(columns+1)/2;i++)
        {
            for (int j=0;j<columns;j++)
            {
                if (i==0&&j==centralElement){
                    triangleBuilder.append("*");
                }
                else if ((i!=0&&j==centralElement+i+1)||(i!=0&&j==centralElement-i-1)){
                    triangleBuilder.append("*");
                }
                else if (i==(columns+1)/2-1){
                    if(j%2==0)
                        triangleBuilder.append("*");
                    else
                        triangleBuilder.append("  ");
                }
                else {
                    triangleBuilder.append(" ");
                }
            }
            triangleBuilder.append("\n");
        }
        triangle.setText(triangleBuilder);
    }
}
