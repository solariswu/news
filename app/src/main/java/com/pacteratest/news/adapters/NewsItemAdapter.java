package com.pacteratest.news.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pacteratest.news.R;
import com.pacteratest.news.models.NewsRowData;


/**
 * Created by yugangwu on 16/9/5.
 *
 */
public class NewsItemAdapter extends ArrayAdapter<NewsRowData> {
        private Context mContext;

        public NewsItemAdapter(Context context, NewsRowData[] objects) {
            super(context, R.layout.newsitems_list, R.id.tvNewsTitle, objects);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rootView = super.getView(position, convertView, parent);

            //Set news image
            ImageView imageView = (ImageView) rootView.findViewById(R.id.ivNewsImg);
          //  imageView.setImageDrawable(MyUtil.mapIconStringToDrawable(mContext,
            //        getItem(position).getIcon()));

            //Set news title
            ((TextView) rootView.findViewById(R.id.tvNewsTitle)).setText(
                    getItem(position).getTitle());

            //Set news content description
            ((TextView) rootView.findViewById(R.id.tvNewsDesc)).setText(
                    getItem(position).getDescription());

            return rootView;
        }
    }
