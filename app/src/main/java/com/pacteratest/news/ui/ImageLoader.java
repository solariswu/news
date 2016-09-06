package com.pacteratest.news.ui;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;


import com.pacteratest.news.utils.NewsConstants;

/**
 * Created by yungang wu on 16/9/6.
 *
 */

public class ImageLoader {

    private LruCache<String, Bitmap> mMemoryCaches;
    private Set<NewsAsyncTask> mTasks;
    private ListView mListView;

    public String mUrls[];

    public ImageLoader(ListView listView) {

        this.mListView = listView;

        mTasks = new HashSet<>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSizes = maxMemory / 5;

        mMemoryCaches = new LruCache<String, Bitmap>(cacheSizes) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    public Bitmap getBitmapFromLrucache(String url) {

        return mMemoryCaches.get(url);
    }

    public void addBitmapToLrucaches(String url, Bitmap bitmap) {

        if (getBitmapFromLrucache(url) == null) {
            mMemoryCaches.put(url, bitmap);
        }

    }

    public void loadImages(int start, int end) {

        for (int i = start; i < end; i++) {
            String loadUrl = mUrls[i];
            if (null != loadUrl) {
                if (getBitmapFromLrucache(loadUrl) != null) {
                    ImageView imageView = (ImageView) mListView
                            .findViewWithTag(loadUrl);
                    imageView.setImageBitmap(getBitmapFromLrucache(loadUrl));
                } else {
                    NewsAsyncTask mNewsAsyncTask = new NewsAsyncTask(loadUrl);
                    mTasks.add(mNewsAsyncTask);
                    mNewsAsyncTask.execute(loadUrl);
                }
            }
        }
    }

    public void showImage(ImageView imageView, String url) {

        //try to get image from cache
        Bitmap bitmap = getBitmapFromLrucache(url);
        if (bitmap != null) {
            //image exists in cache, using it to fill the image view
            imageView.setImageBitmap(bitmap);
        }
        //otherwise the image will be updated in the async-task callback
    }

    public void cancelAllAsyncTask() {
        if (mTasks != null) {
            for (NewsAsyncTask newsAsyncTask : mTasks) {
                newsAsyncTask.cancel(false);
            }
        }
    }

    public Bitmap getBitmapFromUrl(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            //construct http connection to get image content
            URL mUrl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) mUrl
                    .openConnection();
            connection.setConnectTimeout(NewsConstants.CONNECTION_TIMEOUT);
            connection.setReadTimeout(NewsConstants.HTTP_READ_TIMEOUT);
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is, null, new BitmapFactory.Options());
            if (null == bitmap) {
                Log.d(NewsConstants.LOG, "Image Loader, "+urlString);
                Log.e(NewsConstants.LOG, "Image Loader, input stream can't be decoded to Bitmap");
            }
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            Log.e(NewsConstants.LOG, "Image url:" + urlString + " is in wrong format");
        } catch (IOException e) {
            Log.d(NewsConstants.LOG, "Image Loader, "+urlString);
            Log.e(NewsConstants.LOG, "Get Image URL meets IO error");
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                Log.e(NewsConstants.LOG, "Close image input stream error");
                //e.printStackTrace();
            }
        }
        return null;
    }


    class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private String mUrl;

        public NewsAsyncTask(String url) {
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            Bitmap bitmap;

            // retrieve bitmap content from net
            bitmap = getBitmapFromUrl(url);
            // add it into the Lrucaches after downloading
            if (bitmap != null) {
                addBitmapToLrucaches(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //update image view with the tag after retrieve the bitmap
            if (null != mUrl) {
                ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);

                if (bitmap != null && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
                mTasks.remove(this);
            }

        }

    }

}
