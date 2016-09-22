package com.nile.naliweather.view.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.DraweeView;
import com.nalib.fwk.utils.DeviceUtil;
import com.nile.app.naliweather.R;
import com.nile.naliweather.api.bean.Item;
import com.nile.naliweather.api.bean.Item1;
import com.nile.naliweather.api.bean.Item2;

import java.util.ArrayList;
import java.util.List;

/**
 * @actor:taotao
 * @DATE: 16/9/2
 */
public class ListAdpater extends BaseAdapter {

    private List<Item> objects = new ArrayList();
    private Context context;
    private LayoutInflater layoutInflater;
    private int mScreanHight;
    private DataChangeListener dataChangeListener;

    public ListAdpater(Context context, DataChangeListener dataChangeListener) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        mScreanHight = DeviceUtil.getScreenHeight(context);
        this.dataChangeListener = dataChangeListener;
    }

    public void setData(List<Item> data) {
        this.objects = data;
    }

    @Override
    public int getCount() {
        if (dataChangeListener != null) {
            objects = dataChangeListener.getData();
        }
        int size = 0;
        if (objects != null) {
            size = objects.size();
        }
        return size;
    }

    @Override
    public Item getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return Item.TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (item.getType() == Item.ITEM_1) {
            if (convertView == null || !(convertView.getTag() instanceof Item1Holder)) {
                convertView = layoutInflater.inflate(R.layout.layout_item_1, null);
                convertView.setBackgroundResource(R.color.colorPrimary);
                convertView.setTag(new Item1Holder(convertView));
            }
        } else if (item.getType() == Item.ITEM_2) {
            if (convertView == null || !(convertView.getTag() instanceof Item2Holder)) {
                convertView = layoutInflater.inflate(R.layout.layout_item_2, null);
                convertView.setTag(new Item2Holder(convertView));
            }
        }

        initializeViews(item, convertView.getTag());
        return convertView;
    }

    private void initializeViews(Item item, Object obj) {
        if (item.getType() == Item.ITEM_1) {
            Item1Holder holder = (Item1Holder) obj;
            Item1 it1 = (Item1) item;
            holder.tvCityWeather.setText(it1.getCityName() + " | " + it1.getWeather());
            holder.tvTemperature.setText(it1.getTemperature());
            holder.tvWindName.setText(it1.getInfos().get(0).getName());
            holder.tvWindValue.setText(it1.getInfos().get(0).getValue());
            holder.tvHumidityName.setText(it1.getInfos().get(1).getName());
            holder.tvHumidityValue.setText(it1.getInfos().get(1).getValue());
            holder.tvPm25Name.setText(it1.getInfos().get(2).getName());
            holder.tvPm25Value.setText(it1.getInfos().get(2).getValue());
        } else if (item.getType() == Item.ITEM_2) {
            Item2Holder holder = (Item2Holder) obj;
            Item2 it2 = (Item2) item;
            holder.tvToday.setText(it2.getDate());
            holder.tvWeather.setText(it2.getWeather());
            holder.tvTemperature.setText(it2.getTemperature());
        }
    }

    public interface DataChangeListener {
        List<Item> getData();
    }

    protected class Item1Holder {
        private TextView tvTemperature;
        private TextView tvCityWeather;
        private LinearLayout llInfo;
        private RelativeLayout rlWind;
        private TextView tvWindName;
        private TextView tvWindValue;
        private RelativeLayout rlHumidity;
        private TextView tvHumidityName;
        private TextView tvHumidityValue;
        private RelativeLayout rlPm25;
        private TextView tvPm25Name;
        private TextView tvPm25Value;

        public Item1Holder(View view) {
            tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
            tvCityWeather = (TextView) view.findViewById(R.id.tvCityWeather);
            llInfo = (LinearLayout) view.findViewById(R.id.llInfo);
            rlWind = (RelativeLayout) view.findViewById(R.id.rlWind);
            tvWindName = (TextView) view.findViewById(R.id.tvWindName);
            tvWindValue = (TextView) view.findViewById(R.id.tvWindValue);
            rlHumidity = (RelativeLayout) view.findViewById(R.id.rlHumidity);
            tvHumidityName = (TextView) view.findViewById(R.id.tvHumidityName);
            tvHumidityValue = (TextView) view.findViewById(R.id.tvHumidityValue);
            rlPm25 = (RelativeLayout) view.findViewById(R.id.rlPm25);
            tvPm25Name = (TextView) view.findViewById(R.id.tvPm25Name);
            tvPm25Value = (TextView) view.findViewById(R.id.tvPm25Value);
        }
    }

    protected class Item2Holder {
        private DraweeView dvImg;
        private TextView tvToday;
        private TextView tvWeather;
        private TextView tvTemperature;

        public Item2Holder(View view) {
            dvImg = (DraweeView) view.findViewById(R.id.dvImg);
            tvToday = (TextView) view.findViewById(R.id.tvToday);
            tvWeather = (TextView) view.findViewById(R.id.tvWeather);
            tvTemperature = (TextView) view.findViewById(R.id.tvTemperature);
        }
    }
}
