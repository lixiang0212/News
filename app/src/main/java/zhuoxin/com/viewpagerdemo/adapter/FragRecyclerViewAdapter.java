package zhuoxin.com.viewpagerdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.sportsInfo;
import zhuoxin.com.viewpagerdemo.interfaces.RecyclerViewInterface;
import zhuoxin.com.viewpagerdemo.manager.ImageLoadManager;

public class FragRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<sportsInfo> newsLists;
    private RecyclerViewInterface viewInterface;
    private ImageLoadManager imageLoadManager;
    public RecyclerViewInterface getViewInterface() {
        return viewInterface;
    }
    public void setViewInterface(RecyclerViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public FragRecyclerViewAdapter(Context context, List<sportsInfo> newsLists) {
        super();
        this.context = context;
        this.newsLists = newsLists;
        inflater=LayoutInflater.from(context);
        imageLoadManager=new ImageLoadManager();
    }

    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.inflate_news_body_item,parent,false);
        Temp temp = new Temp(view);
        return temp;
    }
    public void remove(int position){
        newsLists.remove(position);
        notifyItemRemoved(position);
    }
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder!=null) {
            //设置没有加载前的默认图片
            ((Temp) holder).iv.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return));
            final sportsInfo info = newsLists.get(position);
            ((Temp) holder).title.setText(info.title);
            ((Temp) holder).time.setText(info.ctime);
            ((Temp) holder).from.setText(info.description);
            ((Temp) holder).iv.setTag(info.picUrl);
//            MyAsyncTaskTwo taskTwo = new MyAsyncTaskTwo(((Temp)holder).iv);
//            taskTwo.execute(info.picUrl);
            //imageLoadManager.LoadImage(info.picUrl,((Temp) holder).iv);//异步加载图片
            new AsyncTask<String,Integer,Bitmap>(){
                protected Bitmap doInBackground(String... strings) {
                    String url = info.picUrl;//获取picUrl
                    Bitmap bitmap = imageLoadManager.GetImage(url);
                    return bitmap;
                }
                protected void onPostExecute(Bitmap bitmap) {
                    super.onPostExecute(bitmap);
                    if(info.picUrl.equals(((Temp) holder).iv.getTag())){
                        ((Temp) holder).iv.setImageBitmap(bitmap);
                        Log.i("AAA","setImageAsync-OK");
                    }
                    else {
                        Log.i("AAA","setImageAsync-NO");
                    }
                }
            }.execute(info.picUrl);
            final int id = holder.getLayoutPosition();
            ((Temp) holder).title.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    viewInterface.OnItemClickListener(view,id);
                }
            });
            ((Temp) holder).title.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    viewInterface.OnLongClickListener(view,id);
                    return true;
                }
            });
        }
    }

    public int getItemCount() {
        return newsLists.size();
    }

    class Temp extends RecyclerView.ViewHolder{
        private TextView title,time,from;
        private ImageView iv;
        public Temp(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.include_item_title);
            time=(TextView)itemView.findViewById(R.id.include_item_time);
            from=(TextView)itemView.findViewById(R.id.include_item_from);
            iv=(ImageView) itemView.findViewById(R.id.include_item_iv);
        }
    }
    private class MyAsyncTaskTwo extends AsyncTask<String,Integer,BitmapDrawable>{
        private ImageView imageView;
        String url;
        public MyAsyncTaskTwo(ImageView imageView){
            this.imageView = imageView;
        }
        protected BitmapDrawable doInBackground(String... params) {
            url = params[0];//获取picUrl
            Bitmap bitmap = imageLoadManager.GetImage(url);
            BitmapDrawable drawable =new BitmapDrawable(context.getResources(),bitmap);
            return drawable;
        }
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onPostExecute(BitmapDrawable drawable) {
            super.onPostExecute(drawable);
            if ( imageView != null && drawable != null){
                imageView.setImageDrawable(drawable);
            }

        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
