package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BaiHocAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<BaiHoc> baiHocList;

    public BaiHocAdapter(MainActivity context, int layout, List<BaiHoc> baiHocList) {
        this.context = context;
        this.layout = layout;
        this.baiHocList = baiHocList;
    }

    @Override
    public int getCount() {
        return baiHocList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvTen;
        ImageView imvSua, imvXoa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            holder.tvTen = (TextView) convertView.findViewById(R.id.tvTen);
            holder.imvSua = (ImageView) convertView.findViewById(R.id.imgSua);
            holder.imvXoa = (ImageView) convertView.findViewById(R.id.imgXoa);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BaiHoc baiHoc = baiHocList.get(position);
        holder.tvTen.setText(baiHoc.getTenBai());

        holder.imvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogUpdate(baiHoc.getId(), baiHoc.getTenBai());
            }
        });

        holder.imvXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogDelete(baiHoc.getId(), baiHoc.getTenBai());
            }
        });
        return convertView;
    }
}

