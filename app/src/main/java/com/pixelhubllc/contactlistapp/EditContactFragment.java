package com.pixelhubllc.contactlistapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.pixelhubllc.contactlistapp.models.Contact;
import com.pixelhubllc.contactlistapp.utils.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditContactFragment extends Fragment {
    private static final String TAG = "EditContactFragment";

    //this will avoid the null point exception when adding  a new bundle from mainactivity
    public EditContactFragment(){
        super();
        setArguments(new Bundle());
    }

    private Contact mContact;
    private EditText mPhoneNumber, mName, mEmail;
    private CircleImageView mContactImage;
    private Spinner mSelectedDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editcontact, container, false);

        mPhoneNumber = (EditText) view.findViewById(R.id.etContactPhone);
        mName = (EditText) view.findViewById(R.id.etContactName);
        mEmail = (EditText) view.findViewById(R.id.etContactEmail);
        mSelectedDevice = (Spinner) view.findViewById(R.id.selectDevice);
        toolbar = (Toolbar) view.findViewById(R.id.editcontactstoolbar);
        mContactImage = (CircleImageView) view.findViewById(R.id.contactImage);
        Log.d(TAG, "onCreateView: Started");

        //get the contact from bundle
        mContact = getContactFromBundle();

        if (mContact != null){
            mPhoneNumber.setText(mContact.getPhoneNumber());
            init();
        }

        return view;
    }

    private void init(){
        mPhoneNumber.setText(mContact.getPhoneNumber());
        mName.setText(mContact.getName());
        mEmail.setText(mContact.getEmail());
        UniversalImageLoader.setImage(mContact.getProfileImage(),mContactImage, null, "https://");

        //setting the selected device to the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.device_options,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectedDevice.setAdapter(adapter);
        int position = adapter.getPosition(mContact.getDevice());
        mSelectedDevice.setSelection(position);

    }

    //retrieves the selected contact from the bundle from MainActivity
    private Contact getContactFromBundle(){
        Log.e(TAG, "getContctsfrom argumnet" + getArguments());
        Bundle bundle = this.getArguments();

        if (bundle != null){
            return bundle.getParcelable(getString(R.string.contact));
        } else {
            return null;
        }
    }

}
