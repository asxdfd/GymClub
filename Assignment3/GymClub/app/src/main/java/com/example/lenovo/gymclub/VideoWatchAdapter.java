package com.example.lenovo.gymclub;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * File: ${file_name}
 * Name: 张袁峰
 * Student ID: 16301170
 * date: ${date}
 */
public class VideoWatchAdapter extends RecyclerView.Adapter<VideoWatchAdapter.VideoViewHolder> {

    private final List<VideoListItem> mList; // 视频项列表

    // 构造器
    public VideoWatchAdapter(List<VideoListItem> list) {
        mList = list;
    }

    @NonNull
    @Override
    public VideoWatchAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        // 必须要设置Tag, 否则无法显示
        VideoWatchAdapter.VideoViewHolder holder = new VideoWatchAdapter.VideoViewHolder(view);
        view.setTag(holder);
        return new VideoWatchAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoWatchAdapter.VideoViewHolder holder, int position) {
        VideoListItem videoItem = mList.get(position);
        holder.bindTo(videoItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoPlayerView mVpvPlayer; // 播放控件
        ImageView mIvCover; // 覆盖层
        TextView mTvTitle; // 标题

        private Context mContext;
        private MediaPlayerWrapper.MainThreadMediaPlayerListener mPlayerListener;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVpvPlayer = (VideoPlayerView) itemView.findViewById(R.id.video_player); // 播放控件
            mTvTitle = (TextView) itemView.findViewById(R.id.video_title);
            mIvCover = (ImageView) itemView.findViewById(R.id.video_cover);

            mContext = itemView.getContext().getApplicationContext();
            mPlayerListener = new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {
                }

                @Override
                public void onVideoPreparedMainThread() {
                    // 视频播放隐藏前图
                    mIvCover.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {
                    mIvCover.setVisibility(View.VISIBLE);
                }

                @Override
                public void onErrorMainThread(int what, int extra) {
                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {
                }

                @Override
                public void onVideoStoppedMainThread() {
                    // 视频暂停显示前图
                    mIvCover.setVisibility(View.VISIBLE);
                }
            };

            mVpvPlayer.addMediaPlayerListener(mPlayerListener);
        }

        public void bindTo(VideoListItem vli) {
            mTvTitle.setText(vli.getTitle());
            mIvCover.setImageResource(R.drawable.cover);
//            final VideoListItem v = vli;
//            new Thread() {
//
//                @Override
//                public void run() {
//                    super.run();
//                    mIvCover.setImageBitmap(getImageFromNet(v.getCoverImageUrl()));
//                    ImageView vvv = itemView.findViewById(R.id.img_test);
//                    vvv.setImageBitmap(getImageFromNet(v.getCoverImageUrl()));
//                }
//            }.start();
        }

        // 返回播放器
        public VideoPlayerView getVpvPlayer() {
            return mVpvPlayer;
        }

        private Bitmap getImageFromNet(String btp) {
            HttpURLConnection conn = null;
            try {
                URL myUri = new URL(btp); // 创建URL对象
                // 创建链接
                conn = (HttpURLConnection) myUri.openConnection();
                conn.setConnectTimeout(10000);// 设置链接超时
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");// 设置请求方法为get
                conn.connect();// 开始连接
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    InputStream is = conn.getInputStream();
                    // 根据流数据创建 一个Bitmap位图对象
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    System.out.println("Img: " + bitmap);
                    return bitmap;
                    // 访问成功
                } else {
                    Log.i("Error: ", "访问失败：responseCode=" + responseCode);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();

                }
            }
            return null;
        }
    }

}
