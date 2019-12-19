package com.pixelhubllc.contactlistapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pixelhubllc.contactlistapp.MainActivity;
import com.pixelhubllc.contactlistapp.R;
import com.pixelhubllc.contactlistapp.models.Contact;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class ContactPropertyListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "ContactProperty";
    
    private LayoutInflater mInflater;
    private List<String> mProperties = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;

    public ContactPropertyListAdapter(@NonNull Context context, int resource, @NonNull List<String> properties) {
        super(context, resource, properties);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;
        this.mProperties = properties;
    }

    //---------------------------Stuff to change--------------------------------------------
    public static class ViewHolder {
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //viewholder build pattern start
        final ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();

            holder.property = (TextView) convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = (ImageView) convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = (ImageView) convertView.findViewById(R.id.iconLeftCardView);
            //--------------------------------------------------------------------------------------

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //---------------------------Stuff to change--------------------------------------------
        final String property = getItem(position);
        holder.property.setText(property);

        //check if it's an email or a phone number
        //email
        if (property.contains("@")) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_email", null, mContext.getPackageName()));
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //the email we have to sent
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {property});
                    mContext.startActivity(emailIntent);

                    /* optional settings for sending emails
                    String email = property;
                    String subject = "subject";
                    String body = "body...";
                    String uriText = "mailto: + Uri.encode(email) + "?subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(body);
                    Uri uri = Uri.parse(uriText);
                    emailIntent.setData(uri);
                    mContext.startActivitY(emailIntent);
                     */
                }
            });

        } else if ((property.length() != 0)) {
            holder.leftIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_phone", null, mContext.getPackageName()));
            //phn call
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MainActivity)mContext).checkPermission(Init.PHONE_PERMISSION)){
                        Log.d(TAG, "onClick: initiaing phn call... ");
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        mContext.startActivity(callIntent);
                    } else {
                        ((MainActivity)mContext).verifiedPermission(Init.PHONE_PERMISSION);
                    }
                }
            });

            //setupt the icon for sending message
            holder.rightIcon.setImageResource(mContext.getResources().getIdentifier("@drawable/ic_message", null, mContext.getPackageName()));
            holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //the number that we want to send message
                    Intent smseIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", property, null));
                    mContext.startActivity(smseIntent);
                }
            });

        }


        //--------------------------------------------------------------------------------------

        return convertView;
    }
}
