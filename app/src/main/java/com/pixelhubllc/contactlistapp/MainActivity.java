package com.pixelhubllc.contactlistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pixelhubllc.contactlistapp.models.Contact;
import com.pixelhubllc.contactlistapp.utils.UniversalImageLoader;

public class MainActivity extends AppCompatActivity implements ViewContactsFragment.onContactSelectedListener, ContactFragment.onEditContactListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;

    @Override
    public void onEditContactSelected(Contact contact) {
        Log.e(TAG,"OnContactSelected: contact selected from" + getString(R.string.edit_contact_fragment ) + " " +contact.getName());
        EditContactFragment fragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.edit_contact_fragment),contact);
        fragment.setArguments(args);

        //fragment trasnaction are send it to contactfragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.edit_contact_fragment));
        transaction.commit();
    }

    //reciving selected contact from viewcontactfragment and send it to contactfragment
    @Override
    public void onContactSelected(Contact contact) {
        Log.e("contact" , getString(R.string.view_contact_fragment ) + " " +contact.getName());
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.contact),contact);
        fragment.setArguments(args);

        //fragment trasnaction are send it to contactfragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(getString(R.string.contact_fragment));
        transaction.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageLoader();
        init();
    }

    //initializing first fragment
    private void init(){
        ViewContactsFragment fragment = new ViewContactsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //replce whatever is the fragment_container with is fragment
        //add add the transaction to the backstack so user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    /**
     * Generalized method for asking permission. Can pass any array of permissions
     * @param permission
     */
    public void verifiedPermission(String[] permission){
        Log.d(TAG, "verifiedPermission: asking user for permission");
        ActivityCompat.requestPermissions(MainActivity.this, permission, REQUEST_CODE);

    }

    /**
     * Checks to see if permission was granted for the passed parameters
     * ONLY ONE PERMISSION MAYT BE CHECKED AT A TIME
     * @param permission
     * @return
     */
    public boolean checkPermission(String[] permission){
        Log.d(TAG, "checkPermission: checking permission for permission:" + permission[0]);

        int permissionRequest = ActivityCompat.checkSelfPermission(MainActivity.this, permission[0]);
        if (permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checking permission: \n permission was not granted for: " + permission[0]);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult:" + requestCode);

        switch (requestCode){
            case REQUEST_CODE:
                for (int i = 0 ; i < permissions.length; i++ ){
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: User has allowed permission to access:" + permissions[i]);
                    } else {
                        break;
                    }
                }
        }
    }
}
