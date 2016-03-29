package com.myqueue.myqueue.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.APIs.TaskPostFeed;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIFeedRequest;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit.mime.TypedFile;

public class NewsFeedFormActivity extends BaseActivity implements View.OnClickListener {

    private static SessionManager sessions;

    private Toolbar myActionBar;
    EditText edfeedStatus;
    TextView txtWhoPost;
    ImageView imgFeed1;
    ImageView imgShopIcon;
    ImageView deletePhoto;
    RoundedImage profPics;
    public HashMap<String,String> shopDataDetails;
    public HashMap<String,String> userDataDetails;
    public static int COUNT_CAMERA = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "MyQueue_CapturedPhoto";
    private Uri fileUri;

    private String timeStamp;

    RelativeLayout btnAddPhoto;

    private Intent resultIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        sessions = new SessionManager(this);

        shopDataDetails = sessions.getShopDetails();
        userDataDetails = sessions.getUserDetails();

        edfeedStatus = (EditText)findViewById(R.id.feedStatus);
        btnAddPhoto = (RelativeLayout)findViewById(R.id.btnAddphoto);
        txtWhoPost = (TextView)findViewById(R.id.whoPostThis);
        imgShopIcon = (ImageView)findViewById(R.id.logoWhoPost);
        deletePhoto = (ImageView)findViewById(R.id.deletePhoto);

        Glide.with(context).load(userDataDetails.get(SessionManager.KEY_PROFILEPHOTO)).asBitmap()
                .into(new SimpleTarget<Bitmap>(35, 35) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        profPics = new RoundedImage(resource);
                        imgShopIcon.setImageDrawable(profPics);
                    }
                });

        //imageview untuk nampung foto abis Add Photos
        imgFeed1 = (ImageView)findViewById(R.id.imgFeed1);

        txtWhoPost.setText(userDataDetails.get(SessionManager.KEY_NAME));

        //set imageview jadi Visible kalo mo nampilin fotonya , kalo dibuang ganti GONE (DEFAULT : GONE)
        imgFeed1.setVisibility(View.VISIBLE);

        btnAddPhoto.setOnClickListener(this);
        deletePhoto.setOnClickListener(this);

        setRightIconEnabled(false);
        edfeedStatus.addTextChangedListener(new MyTextWatcher(edfeedStatus));

        setDefaultActionbarIcon();
        setActionBarTitle("Post Feed");
        setRightIcon(R.drawable.submitfeed_pressed);

        generateTimeStamp();
    }

    @Override
    public void onClick(View v) {
        if(v==btnAddPhoto){
            selectImage();
        }else if(v==deletePhoto){
            imgFeed1.setImageDrawable(null);
            deletePhoto.setVisibility(View.GONE);
            btnAddPhoto.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setUICallbacks() {
        setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                onBackPressed();
            }

            @Override
            public void onRightIconClick() {
                if (imgFeed1.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Posting Feed must include a Photo", Toast.LENGTH_SHORT).show();
                } else {
                    String photoName;

                    APIFeedRequest requestPost = new APIFeedRequest();
                    requestPost.setUserid(userDataDetails.get(SessionManager.KEY_USERID));
                    requestPost.setDescription(edfeedStatus.getText().toString());

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ((BitmapDrawable) imgFeed1.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                    requestPost.setPhotoFeed(encodedImage);

                    photoName= "feed_" + timeStamp +"_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg";

                    requestPost.setUrlPhoto(photoName.toString());

                    TaskPostFeed postthisfeed = new TaskPostFeed(NewsFeedFormActivity.this) {
                        @Override
                        public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {
                            if (isSuccess) {
                                resultIntent = new Intent();
                                resultIntent.putExtra("resultkey", "result");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Your Feed has been Posted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), statusMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    postthisfeed.execute(requestPost);
                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_news_feed_form;
    }

    @Override
    public void updateUI() {

    }

    //CODE TAMBAHAN DARI EDHO -=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            if(edfeedStatus.getText().toString().equals("") && imgFeed1.getDrawable()==null) {
                setRightIconEnabled(false);
                setRightIcon(R.drawable.ic_newsfeedbutton_pressed);
            }else{
                setRightIconEnabled(true);
                setRightIcon(R.drawable.submitfeed_pressed);
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeedFormActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, COUNT_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            2);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == COUNT_CAMERA) {

                Bitmap bitmap;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(fileUri.getPath(), options);
                final int REQUIRED_SIZE = 450;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bitmap =  BitmapFactory.decodeFile(fileUri.getPath(), options);

                imgFeed1.setImageBitmap(bitmap);
                imgFeed1.setScaleType(ImageView.ScaleType.FIT_XY);

            } else if (requestCode == 2) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 450;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;

                if(bm.getWidth()<width){
                    int heighttoAdd = bm.getHeight()+(width-bm.getWidth());
                    Bitmap newResizedBitmap = getResizedBitmap(bm,width,heighttoAdd);
                    imgFeed1.setImageBitmap(newResizedBitmap);
                }else{
                    imgFeed1.setImageBitmap(bm);
                    imgFeed1.setScaleType(ImageView.ScaleType.FIT_START);
                }
            }
            deletePhoto.setVisibility(View.VISIBLE);
            btnAddPhoto.setVisibility(View.INVISIBLE);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp +"_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg");
        } else {
            return null;
        }
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        return mediaFile;
    }

    private void generateTimeStamp(){
        timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }
}
