package com.myqueue.myqueue.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.myqueue.myqueue.APIs.TaskChangeCoverProfilePhoto;
import com.myqueue.myqueue.APIs.TaskChangeProfilePhoto;
import com.myqueue.myqueue.APIs.TaskEditUser;
import com.myqueue.myqueue.APIs.TaskEditUserCategory;
import com.myqueue.myqueue.APIs.TaskGetUser;
import com.myqueue.myqueue.APIs.TaskPostFeed;
import com.myqueue.myqueue.Activities.ProfileActivity;
import com.myqueue.myqueue.Callbacks.OnActionbarListener;
import com.myqueue.myqueue.Fragments.Dialogs.CategoryDialog;
import com.myqueue.myqueue.Fragments.Dialogs.FilterDialog;
import com.myqueue.myqueue.Models.APIBaseResponse;
import com.myqueue.myqueue.Models.APIEditUserCategoryRequest;
import com.myqueue.myqueue.Models.APIEditUserRequest;
import com.myqueue.myqueue.Models.APIFeedRequest;
import com.myqueue.myqueue.Models.APILoginRequest;
import com.myqueue.myqueue.Models.APILoginResponse;
import com.myqueue.myqueue.Models.APIUpdateCoverProfile;
import com.myqueue.myqueue.Models.APIUpdatePhotoProfile;
import com.myqueue.myqueue.Models.Shop;
import com.myqueue.myqueue.Models.User;
import com.myqueue.myqueue.Preferences.SessionManager;
import com.myqueue.myqueue.R;
import com.myqueue.myqueue.Views.RoundedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by 高橋六羽 on 2016/03/21.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private int isOwner = 0;
    public static final int REQUEST_CAMERA = 100 ;
    public static final int REQUEST_GALLERY = 200;

    public int CEK_PHOTO = 0;


    private LinearLayout customerContainer;
    private LinearLayout shopOwnerContainer;

    private ImageView imgcover, profilePicture;

    private EditText storeAddress;
    private EditText storePhone;
    private EditText storeEmail;
    private EditText storeCategory;
    private EditText storeName;

    private EditText userPhone;
    private EditText userEmail;
    private EditText userName;

    private String selectedCategory;

    User loginuser;
    Shop loginshopdata;

    private Intent resultIntent;


    private RoundedImage cropCircle;
    private Button updatebtn;

    private String timeStamp;

    ProfileActivity activity;

    StoreLocationFragment storeLocationFragment;

    public HashMap<String,String> userDataDetails;

    LinearLayout btnChangeProfile;
    LinearLayout btnChangeCover;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (ProfileActivity) getActivity();
        SessionManager sessions = new SessionManager(getBaseActivity());
        userDataDetails = sessions.getUserDetails();

        generateTimeStamp();
    }

    private void setupActionBar() {
        ProfileActivity mainActivity = (ProfileActivity) getActivity();
        mainActivity.setDefaultActionbarIcon();
        mainActivity.setRightIcon(0);
        mainActivity.setActionBarTitle(getPageTitle());
    }

    @Override
    public void onClick(View v) {
        if (v == updatebtn) {
            updateProfile();

        } else if (v == storeAddress) {
            storeLocationFragment = new StoreLocationFragment();

            replaceFragment(R.id.fragment_container, storeLocationFragment, true);
        }
        else if(v==btnChangeProfile){
            CEK_PHOTO = 1;
            selectimage();

//            Toast.makeText(getBaseActivity(), "Change profile photo", Toast.LENGTH_SHORT).show();
        }
        else if(v==btnChangeCover){
            CEK_PHOTO = 2;
            selectimage();
        }
        else if(v == storeCategory)
        {
            CategoryDialog categoryDialog = new CategoryDialog(this);
            categoryDialog.show(getBaseActivity().getSupportFragmentManager(), null);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // jika yang dipilih adalah change profile picture
        if(CEK_PHOTO == 1)
        {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap dstBmp;
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (thumbnail.getWidth() >= thumbnail.getHeight()) {
                    dstBmp = Bitmap.createBitmap(
                            thumbnail,
                            thumbnail.getWidth()/2 - thumbnail.getHeight()/2,
                            0,
                            thumbnail.getHeight(),
                            thumbnail.getHeight()
                    );
                }
                else
                {
                    dstBmp = Bitmap.createBitmap(
                            thumbnail,
                            0,
                            thumbnail.getHeight()/2 - thumbnail.getWidth()/2,
                            thumbnail.getWidth(),
                            thumbnail.getWidth()
                    );
                }

//                cropCircle = new RoundedImage(dstBmp);
//                profilePicture.setImageDrawable(cropCircle);
                profilePicture.setImageBitmap(dstBmp);


                //buat upload langsung ke database

                APIUpdatePhotoProfile requestPost = new APIUpdatePhotoProfile();
                requestPost.setUserid(userDataDetails.get(SessionManager.KEY_USERID));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((BitmapDrawable) profilePicture.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                requestPost.setPhotoProfile(encodedImage);

                String photoName;
                photoName= timeStamp+"profile_photo_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg";

                requestPost.setUrlPhotoProfile(photoName.toString());

                TaskChangeProfilePhoto changePhoto = new TaskChangeProfilePhoto(getBaseActivity()) {
                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {
                        if (isSuccess) {
                            resultIntent = new Intent();
                            resultIntent.putExtra("resultkey", "result");
                            activity.setResult(Activity.RESULT_OK, resultIntent);
                            //activity.finish();
                            Toast.makeText(getBaseActivity(), "Success change", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                changePhoto.execute(requestPost);
                activity.sessions.setKeyProfilephoto(timeStamp + "profile_photo_user" + userDataDetails.get(SessionManager.KEY_USERID) + ".jpg");
                updateUI();

            }
            else if (requestCode == REQUEST_GALLERY) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getBaseActivity(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                Bitmap dstBmp;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);


                if (bm.getWidth() >= bm.getHeight()) {
                    dstBmp = Bitmap.createBitmap(
                            bm,
                            bm.getWidth()/2 - bm.getHeight()/2,
                            0,
                            bm.getHeight(),
                            bm.getHeight()
                    );
                }
                else
                {
                    dstBmp = Bitmap.createBitmap(
                            bm,
                            0,
                            bm.getHeight()/2 - bm.getWidth()/2,
                            bm.getWidth(),
                            bm.getWidth()
                    );
                }

//                cropCircle = new RoundedImage(dstBmp);
//                profilePicture.setImageDrawable(cropCircle);
                profilePicture.setImageBitmap(dstBmp);

                APIUpdatePhotoProfile requestPost = new APIUpdatePhotoProfile();
                requestPost.setUserid(userDataDetails.get(SessionManager.KEY_USERID));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((BitmapDrawable) profilePicture.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                requestPost.setPhotoProfile(encodedImage);

                String photoName;
                photoName= timeStamp+"profile_photo_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg";

                requestPost.setUrlPhotoProfile(photoName.toString());

                TaskChangeProfilePhoto changePhoto = new TaskChangeProfilePhoto(getBaseActivity()) {
                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {
                        if (isSuccess) {
                            resultIntent = new Intent();
                            resultIntent.putExtra("resultkey", "result");
                            activity.setResult(Activity.RESULT_OK, resultIntent);
//                            activity.finish();
                            fetchData();
                            Toast.makeText(getBaseActivity(), "Success change", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                changePhoto.execute(requestPost);
                activity.sessions.setKeyProfilephoto(timeStamp + "profile_photo_user" + userDataDetails.get(SessionManager.KEY_USERID) + ".jpg");
                updateUI();



                cropCircle = new RoundedImage(dstBmp);
                profilePicture.setImageDrawable(cropCircle);

            }
        }

        // jika yang dipilih adalah change cover picture
        else if(CEK_PHOTO == 2)
        {
            if (requestCode == REQUEST_CAMERA) {
                Toast.makeText(getBaseActivity(), "Change cover photo", Toast.LENGTH_SHORT).show();
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgcover.setImageBitmap(thumbnail);
                //buat upload langsung ke database

                APIUpdateCoverProfile requestPost = new APIUpdateCoverProfile();
                requestPost.setUserid(userDataDetails.get(SessionManager.KEY_USERID));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((BitmapDrawable) imgcover.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                requestPost.setCoverProfile(encodedImage);

                String photoName;
                photoName= timeStamp+"cover_photo_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg";

                requestPost.setUrlCoverProfile(photoName.toString());

                TaskChangeCoverProfilePhoto changeCoverPhoto = new TaskChangeCoverProfilePhoto(getBaseActivity()) {
                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {
                        if (isSuccess) {
                            resultIntent = new Intent();
                            resultIntent.putExtra("resultkey", "result");
                            activity.setResult(Activity.RESULT_OK, resultIntent);
                            //activity.finish();
                            Toast.makeText(getBaseActivity(), "Success change", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                changeCoverPhoto.execute(requestPost);
                activity.sessions.setKeyCoverphoto(timeStamp + "cover_photo_user" + userDataDetails.get(SessionManager.KEY_USERID) + ".jpg");
                updateUI();


            } else if (requestCode == REQUEST_GALLERY) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(getBaseActivity(), selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imgcover.setImageBitmap(bm);

                APIUpdateCoverProfile requestPost = new APIUpdateCoverProfile();
                requestPost.setUserid(userDataDetails.get(SessionManager.KEY_USERID));

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ((BitmapDrawable) imgcover.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                requestPost.setCoverProfile(encodedImage);

                String photoName;
                photoName= timeStamp+"cover_photo_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg";

                requestPost.setUrlCoverProfile(photoName.toString());

                TaskChangeCoverProfilePhoto changeCoverPhoto = new TaskChangeCoverProfilePhoto(getBaseActivity()) {
                    @Override
                    public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {
                        if (isSuccess) {
                            resultIntent = new Intent();
                            resultIntent.putExtra("resultkey", "result");
                            activity.setResult(Activity.RESULT_OK, resultIntent);
                            //activity.finish();
                            Toast.makeText(getBaseActivity(), "Success change", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                changeCoverPhoto.execute(requestPost);
                activity.sessions.setKeyCoverphoto(timeStamp + "cover_photo_user" + userDataDetails.get(SessionManager.KEY_USERID) + ".jpg");
                updateUI();
            }
//            activity.sessions.setKeyProfilephoto(timeStamp+"profile_photo_user" + userDataDetails.get(SessionManager.KEY_USERID)+ ".jpg");
//            updateUI();

        }
//        updateUI();

    }

    //untuk memilih gambar dari gallery atau camera=======================================================
    private void selectimage(){
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            REQUEST_GALLERY);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //sementara pemanggilan fetchData() disini aku komen karena ngefetch trus jd ga mau keganti dengan yang baru.
//        fetchData();
        updateUI();
    }


    private void fetchData()
    {
        activity.userData = activity.sessions.getUserDetails();
        activity.shopData = activity.sessions.getShopDetails();

        Glide.with(activity).load(activity.userData.get(SessionManager.KEY_COVERPHOTO)).placeholder(R.drawable.coverpics).into(imgcover);
        Glide.with(activity).load(activity.userData.get(SessionManager.KEY_PROFILEPHOTO)).asBitmap()
                .into(new SimpleTarget<Bitmap>(256, 256) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap dstBmp;
                        if (resource.getWidth() >= resource.getHeight()) {
                            dstBmp = Bitmap.createBitmap(
                                    resource,
                                    resource.getWidth()/2 - resource.getHeight()/2,
                                    0,
                                    resource.getHeight(),
                                    resource.getHeight()
                            );
                        }
                        else
                        {
                            dstBmp = Bitmap.createBitmap(
                                    resource,
                                    0,
                                    resource.getHeight()/2 - resource.getWidth()/2,
                                    resource.getWidth(),
                                    resource.getWidth()
                            );
                        }

                        cropCircle = new RoundedImage(dstBmp);
                        profilePicture.setImageDrawable(cropCircle);
                    }
                });


        if(activity.userData.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("1"))
        {
            storeName.setText(activity.userData.get(SessionManager.KEY_NAME));
            storePhone.setText(activity.userData.get(SessionManager.KEY_PHONE));
            storeEmail.setText(activity.userData.get(SessionManager.KEY_EMAIL));
            storeCategory.setText(activity.shopData.get(SessionManager.KEY_CATEGORY));
            if(activity.shopData.get(SessionManager.KEY_ADDRESS)!=null)
                storeAddress.setText(activity.shopData.get(SessionManager.KEY_ADDRESS) +" "+ activity.shopData.get(SessionManager.KEY_NUMBER));

        }
        else
        {
            userName.setText(activity.userData.get(SessionManager.KEY_NAME));
            userPhone.setText(activity.userData.get(SessionManager.KEY_PHONE));
            userEmail.setText(activity.userData.get(SessionManager.KEY_EMAIL));
        }
    }

    @Override
    public void initView(View v) {

        imgcover = (ImageView) v.findViewById(R.id.coverPicture);
        profilePicture = (ImageView) v.findViewById(R.id.profilepicture);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.no_user);
        cropCircle = new RoundedImage(bm);
        profilePicture.setImageDrawable(cropCircle);
        updatebtn = (Button) v.findViewById(R.id.btnUpdateProfile);

        storeName = (EditText) v.findViewById(R.id.txtNamaProfileShop);
        storePhone = (EditText) v.findViewById(R.id.txtPhoneNumberShop);
        storeEmail = (EditText) v.findViewById(R.id.txtEmailShop);
        storeCategory = (EditText) v.findViewById(R.id.txtCategoryShop);
        storeAddress = (EditText) v.findViewById(R.id.txtStoreAdd);

        userName = (EditText) v.findViewById(R.id.txtNamaProfile);
        userPhone = (EditText) v.findViewById(R.id.txtPhoneNumber);
        userEmail = (EditText) v.findViewById(R.id.txtEmail);

        customerContainer = (LinearLayout) v.findViewById(R.id.customer_data_container);
        shopOwnerContainer = (LinearLayout) v.findViewById(R.id.shop_data_container);



        btnChangeProfile = (LinearLayout)v.findViewById(R.id.bagianTextChangePP);
        btnChangeCover = (LinearLayout)v.findViewById(R.id.bagianTextChangeCover);

        btnChangeProfile.setOnClickListener(this);
        btnChangeCover.setOnClickListener(this);

        updatebtn.setOnClickListener(this);
        storeAddress.setOnClickListener(this);
        storeCategory.setOnClickListener(this);
    }

    @Override
    public void setUICallbacks() {
        getBaseActivity().setActionbarListener(new OnActionbarListener() {
            @Override
            public void onLeftIconClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onRightIconClick() {

            }
        });

    }

    @Override
    public void updateUI() {

        setupActionBar();

        if(activity.getIsOwner()==true)
        {
            shopOwnerContainer.setVisibility(View.VISIBLE);
            customerContainer.setVisibility(View.GONE);
            isOwner = 1;
        } else {
            customerContainer.setVisibility(View.VISIBLE);
            shopOwnerContainer.setVisibility(View.GONE);
            isOwner = 0;
        }

        fetchData();

    }

    @Override
    public String getPageTitle() {
        return "Profile";
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    private void updateProfile()
    {
        if(activity.userData.get(SessionManager.KEY_ISOWNER).equalsIgnoreCase("0")) {
            APIEditUserRequest request = new APIEditUserRequest();
            request.setUserid(activity.userData.get(SessionManager.KEY_USERID));
            request.setName(userName.getText().toString());
            request.setPhone(userPhone.getText().toString());

            TaskEditUser editUser = new TaskEditUser(getActivity()) {

                @Override
                public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                    if (isSuccess) {

                        redirectTo();

                    } else {
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            };
            editUser.execute(request);
        }
        else
        {
            APIEditUserCategoryRequest request = new APIEditUserCategoryRequest();
            request.setUserid(activity.userData.get(SessionManager.KEY_USERID));
            request.setName(storeName.getText().toString());
            request.setPhone(storePhone.getText().toString());
            request.setCategory(storeCategory.getText().toString());

            TaskEditUserCategory editUserCategory = new TaskEditUserCategory(getActivity()) {

                @Override
                public void onResult(APIBaseResponse response, String statusMessage, boolean isSuccess) {

                    if (isSuccess) {

                        redirectTo();

                    } else {
                        Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            };
            editUserCategory.execute(request);
        }
    }

    private void redirectTo()
    {
        Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
        APILoginRequest request = new APILoginRequest();
        request.setEmail(activity.userData.get(SessionManager.KEY_EMAIL));
        request.setPassword(activity.userData.get(SessionManager.KEY_PASSWORD));

        TaskGetUser getUser = new TaskGetUser(getActivity()) {

            @Override
            public void onResult(APILoginResponse response, String statusMessage, boolean isSuccess) {

                if(isSuccess)
                {
                    loginuser = response.getUser().get(0);
                    if(response.getShop().size()!=0)
                        loginshopdata = response.getShop().get(0);

                    activity.sessions.createLoginSession(loginuser);

                    if(loginuser.getIsowner().equalsIgnoreCase("1")) {
                        activity.sessions.setShopData(loginshopdata);

                    }

                    fetchData();
                }
                else
                {
                    Toast.makeText(getActivity(), statusMessage, Toast.LENGTH_SHORT).show();
                }

            }
        };
        getUser.execute(request);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        fetchData();
    }
    private void generateTimeStamp(){
        timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }
    public void setSelectedCategory(String category)
    {
        this.selectedCategory = "";

        selectedCategory = category;
        storeCategory.setText(selectedCategory);
    }
}
