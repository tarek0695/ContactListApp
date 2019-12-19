package com.pixelhubllc.contactlistapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pixelhubllc.contactlistapp.models.Contact;
import com.pixelhubllc.contactlistapp.utils.ContactPropertyListAdapter;
import com.pixelhubllc.contactlistapp.utils.UniversalImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactFragment extends Fragment {
    private static final String TAG = "ContactFragment";

    public interface onEditContactListener{
        public void onEditContactSelected(Contact contact);
    }
    onEditContactListener mOnEditContactListener;

    //this will avoid the null point exception when adding  a new bundle from mainactivity
    public ContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Toolbar toolbar;
    private Contact mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        Log.e(TAG, "onCreateView started." );

        mContactName = (TextView) view.findViewById(R.id.contactName);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        mList = view.findViewById(R.id.lvContactProperties);

        mContact = getContactFromBundle();

        toolbar =  (Toolbar) view.findViewById(R.id.contactToolbar);

        //required for setting up the toolbar
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        init();


        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click","Click back");
                //remove previous fragment from the backstack (therefore navigating back)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        //navigate to the edit contact fragment to edit contact selected
        ImageView ivEdit = (ImageView) view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click","Click edit icon");
                mOnEditContactListener.onEditContactSelected(mContact);
            }
        });

        return view;
    }

    private void init(){
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(), mContactImage, null, "https://");

        ArrayList<String> properties= new ArrayList<>();
        properties.add(mContact.getPhoneNumber());
        properties.add(mContact.getEmail());
        ContactPropertyListAdapter contactPropertyListAdapter = new ContactPropertyListAdapter(getActivity(), R.layout.layout_cardview,properties);
        mList.setAdapter(contactPropertyListAdapter);
        mList.setDivider(null);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuitem_delete:
                Log.d(TAG, "onOptionsItemSelected: deleted");
        }
        return super.onOptionsItemSelected(item);
    }

    //retrieves the selected contact from the bundle from MainActivity
    private Contact getContactFromBundle(){
        Log.e(TAG ,"getContctsfrom argumnet" + getArguments());
        Bundle bundle = this.getArguments();

        if (bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        } else {
            return null;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mOnEditContactListener =(onEditContactListener) getActivity();
        } catch (ClassCastException e){
            Log.d(TAG, "onAttach: ClassCastException" + e.getMessage());
        }
    }
}
