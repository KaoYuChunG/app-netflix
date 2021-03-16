package com.kao.netflixremake.adapter;

import android.content.Context;
import android.widget.ImageView;

public interface OnItemClickListener {
    void onClick(int position, final Context context, ImageView image);
}
