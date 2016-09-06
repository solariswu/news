package com.pacteratest.news.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pacteratest.news.R;
import com.pacteratest.news.models.NewsRowData;
import com.pacteratest.news.ui.ImageLoader;


/**
 * Created by yugangwu on 16/9/5.
 *
 */
public class NewsItemAdapter extends BaseAdapter implements OnScrollListener{

    private List<NewsRowData> mList;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;

    private int mStart;
    private int mEnd;
    private boolean isFirstIn;



    public NewsItemAdapter(Context context,List<NewsRowData> data,ListView listView){

        mList=data;
        mInflater=LayoutInflater.from(context);
        isFirstIn = true;

        imageLoader=new ImageLoader(listView);
        imageLoader.mUrls = new String[mList.size()];
        for(int i=0;i<mList.size();i++){
            imageLoader.mUrls[i] = mList.get(i).getImageHref();
        }
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.newsitems_list,parent,false);
            viewHolder.ivImage=(ImageView) convertView.findViewById(R.id.ivNewsImg);
            viewHolder.tvContent=(TextView) convertView.findViewById(R.id.tvNewsDesc);
            viewHolder.tvTitle=(TextView) convertView.findViewById(R.id.tvNewsTitle);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        String mImgUrl = mList.get(position).getImageHref();
        viewHolder.ivImage.setTag(mImgUrl);
        viewHolder.ivImage.setImageDrawable(null);
        //viewHolder.ivImage.setImageResource(null);

        if (null != mImgUrl)
            imageLoader.showImage(viewHolder.ivImage,mImgUrl);

        viewHolder.tvTitle.setText(mList.get(position).getTitle());
        viewHolder.tvContent.setText(mList.get(position).getDescription());

        return convertView;
    }

    class ViewHolder{
        ImageView ivImage;
        TextView tvTitle;
        TextView tvContent;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if(scrollState==SCROLL_STATE_IDLE){
            imageLoader.loadImages(mStart,mEnd);
        }else{
            imageLoader.cancelAllAsyncTask();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        mStart=firstVisibleItem;
        mEnd=firstVisibleItem+visibleItemCount;

        if(isFirstIn&&visibleItemCount>0){
            imageLoader.loadImages(mStart,mEnd);
            isFirstIn=false;
        }

    }

}
