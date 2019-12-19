package com.pixelhubllc.contactlistapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixelhubllc.contactlistapp.models.Contact;
import com.pixelhubllc.contactlistapp.utils.ContactListAdapter;

import java.util.ArrayList;

public class ViewContactsFragment extends Fragment {

    //for sending data fragment to in the mainactivity
    public interface onContactSelectedListener{
        public void onContactSelected(Contact con);
    }
    onContactSelectedListener mContactListener;


    //variable and widget
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;
    private String testImg = "encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSymMfsjizr6IFIfgxWK9lkdpZkk0Tg5u9O9g28WwNSlTQYNi7-";

    private AppBarLayout contactAppBar, searchAppBar;
    private ContactListAdapter adapter;
    private ListView contactList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);

        contactAppBar = view.findViewById(R.id.view_contactstoolbar);
        searchAppBar = view.findViewById(R.id.searchToolbar);
        contactList = view.findViewById(R.id.contactlist);

        setAppBarState(STANDARD_APPBAR);
        setupContactsList();

        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click","Click fab");
            }
        });

        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click","Click search");
                toggleAppBar();
            }
        });

        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Click","Click back");
                toggleAppBar();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mContactListener = (onContactSelectedListener) getActivity();
        } catch (ClassCastException e){
            Log.e("ClassCastException", "error"+ e.getMessage());

        }
    }

    private void setupContactsList(){
        final ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));
        contacts.add(new Contact("TOm","1234","mobile","tarekint1@gmail.com",testImg));

        adapter = new ContactListAdapter(getActivity(),R.layout.layout_contactslistitem,contacts,"https://");
        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //pass the contact to interface and send it mainactivtty
                mContactListener.onContactSelected(contacts.get(position));
            }
        });
    }

    private void toggleAppBar() {
        if (mAppBarState == STANDARD_APPBAR){
            setAppBarState(SEARCH_APPBAR);
        } else{
            setAppBarState(STANDARD_APPBAR);
        }
    }

    //sets the appbar state for either the search mode or standard mode
    private void setAppBarState(int state) {
        mAppBarState = state;
        if (state == STANDARD_APPBAR){
            searchAppBar.setVisibility(View.GONE);
            contactAppBar.setVisibility(View.VISIBLE);

            //hide the keyboard
            View view = getView();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            } catch (NullPointerException e){
                Log.e("Keyboard error",e.getMessage());
            }

        } else if (state == SEARCH_APPBAR){
            contactAppBar.setVisibility(View.GONE);
            searchAppBar.setVisibility(View.VISIBLE);

            //show the keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}
