package com.app.deliverieslogs.presentation.delieverylist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.deliverieslogs.R;
import com.app.deliverieslogs.common.Utils;
import com.app.deliverieslogs.models.DeliveryItem;
import com.app.deliverieslogs.presentation.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

public class DeliveryListAdapter  extends BaseAdapter {

    private BaseActivity activity;
    private List<DeliveryItem> deliveryItems;
    private DisplayImageOptions options;

    public DeliveryListAdapter(BaseActivity activity, List<DeliveryItem> deliveryItems) {
        this.activity = activity;
        this.deliveryItems = deliveryItems;
        options = Utils.displayImageOption();
    }

    @Override
    public int getCount() {
        if (deliveryItems != null)
            return deliveryItems.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return deliveryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return deliveryItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                activity.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_delivery_item, null);
            holder = new ViewHolder();
            holder.dImg = (ImageView) convertView.findViewById(R.id.itemImg);
            holder.dDesc = (TextView) convertView.findViewById(R.id.itemDesc);
            holder.dAddress = (TextView) convertView.findViewById(R.id.itemAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DeliveryItem deliveryItem = (DeliveryItem) getItem(position);
        holder.dDesc.setText(deliveryItem.getDescription());
        holder.dAddress.setText(deliveryItem.getLocation().getAddress());

        // render images of stores
        Utils.renderImageUIL(activity.getImageLoader(), options, deliveryItem.getImageUrl(), holder.dImg);

        return convertView;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView dImg;
        TextView dDesc;
        TextView dAddress;
    }
}
