package com.digiapt.videorecorder.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.digiapt.videorecorder.R;

public class HomeActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void onButtonClick(View view) {
        if(view.getId() == R.id.record) {
            startActivity(new Intent(this, RecordActivity.class));
        } else if(view.getId() == R.id.browse) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, GALLERY_REQUEST);
        } else if(view.getId() == R.id.show_triangle) {
            startActivity(new Intent(this, TriangleActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getStringExtra("result");
                Uri selectedImageUri = data.getData( );
                String videoPath = getPath( getApplicationContext( ), selectedImageUri );
                Log.d("Video Path", videoPath);

                Intent i = new Intent(this, PlayerActivity.class);
                i.putExtra("video_path", videoPath);
                startActivity(i);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }
}
