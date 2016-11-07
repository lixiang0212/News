package zhuoxin.com.viewpagerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.info.ContentlistBean;
import zhuoxin.com.viewpagerdemo.interfaces.NewsReturnInterface;

public abstract class BaseLoadingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ContentlistBean> datas;
    private Context context;
    protected LayoutInflater inflater;
    private RecyclerView recyclerView;
    private boolean isLoading;
    private LoadingTemp temp;
    private static final int TYPE_BODY = 0;
    private static final int TYPE_BODYS = 1;
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_Loading = 3;
    private static final int TYPE_NULL = 4;
    private NewsReturnInterface anInterface;

    public NewsReturnInterface getAnInterface() {
        return anInterface;
    }

    public void setAnInterface(NewsReturnInterface anInterface) {
        this.anInterface = anInterface;
    }

    public BaseLoadingAdapter(List<ContentlistBean> datas, Context context, RecyclerView recyclerView) {
        super();
        this.datas = datas;
        this.context = context;
        this.recyclerView = recyclerView;
        this.inflater = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        Log.i("AAA",position+"getItemViewType");
        ContentlistBean t = datas.get(position);
        if(t==null){
            return TYPE_Loading;
        } else if(position==0){
            return TYPE_HEADER;
        }else if(position<5){
            return TYPE_NULL;
        }
        else if(datas.get(position)!=null&&datas.get(position).imageurls.size()>=3){
            return TYPE_BODYS;
        }else {
            return TYPE_BODY;
        }
    }
    public abstract RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup viewGroup);
    public abstract void onBindHeaderHolder(RecyclerView.ViewHolder viewHolder,int position);
    public abstract RecyclerView.ViewHolder onCreateBodyHolder(ViewGroup viewGroup);
    public abstract void onBindBodyHolder(RecyclerView.ViewHolder viewHolder,int position);
    public abstract RecyclerView.ViewHolder onCreateBodysHolder(ViewGroup viewGroup);
    public abstract void onBindBodysHolder(RecyclerView.ViewHolder viewHolder,int position);
    private RecyclerView.ViewHolder onCreateLoadingHolder(ViewGroup parent){
        View itemView = inflater.inflate(R.layout.inflate_news_loading,parent,false);
        LoadingTemp temp= new LoadingTemp(itemView);
        return temp;
    }
    private void onBindLoadingHolder(RecyclerView.ViewHolder holder){
    }
    private RecyclerView.ViewHolder onCreateNullHolder(ViewGroup parent){
        View itemView = inflater.inflate(R.layout.inflate_news_header_null,parent,false);
        NullTemp temp = new NullTemp(itemView);
        return temp;
    }
    private void onBindNullHolder(RecyclerView.ViewHolder holder){
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType){
            case TYPE_BODYS:
                viewHolder = onCreateBodysHolder(parent);
                break;
            case TYPE_NULL:
                viewHolder = onCreateNullHolder(parent);
                break;
            case TYPE_HEADER:
                viewHolder = onCreateHeaderHolder(parent);
                break;
            case TYPE_BODY:
                viewHolder = onCreateBodyHolder(parent);
                break;
            case TYPE_Loading:
                viewHolder = onCreateLoadingHolder(parent);
                temp =(LoadingTemp)viewHolder;
                break;
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_BODYS:
                onBindBodysHolder(holder,position);
                break;
            case TYPE_HEADER:
                onBindHeaderHolder(holder,position);
                break;
            case TYPE_BODY:
                onBindBodyHolder(holder,position);
                break;
            case TYPE_Loading:
                onBindLoadingHolder(holder);
                break;
            case TYPE_NULL:
                onBindNullHolder(holder);
                break;
        }
    }

    private class LoadingTemp extends RecyclerView.ViewHolder{
        public TextView textView;
        public ProgressBar progressBar;
        public RelativeLayout relativeLayout;
        public LoadingTemp(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.loading_text);
            progressBar=(ProgressBar)itemView.findViewById(R.id.loading_progress);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.loading_relative);
        }
    }
    private class NullTemp extends RecyclerView.ViewHolder{
        public NullTemp(View itemView) {
            super(itemView);

        }
    }
    public void notifyLoading() {
        if (datas.size() != 0 && datas.get(datas.size() - 1) != null) {
            datas.add(null);
            notifyItemInserted(datas.size() - 1);
        }
    }
    public void setLoadingComplete() {
        if (datas.size() > 0 && datas.get(datas.size() - 1) == null) {
            isLoading = false;
            datas.remove(datas.size() - 1);
            notifyItemRemoved(datas.size() - 1);
        }
    }
    public void setLoadingNoMore() {
        isLoading = false;
        if (temp != null) {
            temp.progressBar.setVisibility(View.GONE);
            temp.textView.setText("没有更多的数据，点击加载最新数据！");
            Log.i("AAA","noMore");
            temp.relativeLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    temp.progressBar.setVisibility(View.VISIBLE);
                    temp.textView.setText("努力加载中！");
                    anInterface.Return();
                }
            });
        }
    }
}



