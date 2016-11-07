package zhuoxin.com.viewpagerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import zhuoxin.com.viewpagerdemo.R;
import zhuoxin.com.viewpagerdemo.interfaces.RecyclerViewInterface;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<String> datas;
    private RecyclerViewInterface viewInterface;
    private List<Integer> lists;
    public RecyclerViewInterface getViewInterface() {
        return viewInterface;
    }
    public void setViewInterface(RecyclerViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public MyRecyclerViewAdapter(Context context, List<String> datas) {
        super();
        this.context = context;
        this.datas = datas;
        inflater=LayoutInflater.from(context);
        lists = new ArrayList<>();
        for (int i=0;i<datas.size();i++){
            lists.add((int)(100+Math.random()*400));
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_view_holder_item,parent,false);
        Temp temp = new Temp(view);
        return temp;
    }
    public void remove(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder!=null) {
            ((Temp) holder).tv.setText(datas.get(position));
            ViewGroup.LayoutParams params = ((Temp) holder).tv.getLayoutParams();
            params.height=lists.get(position);
            ((Temp) holder).tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    viewInterface.OnItemClickListener(view,holder.getLayoutPosition());
                   // Log.i("BBB",id+"-");
                }
            });
            ((Temp) holder).tv.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    viewInterface.OnLongClickListener(view,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    public int getItemCount() {
        return datas.size();
    }

    class Temp extends RecyclerView.ViewHolder{
        private TextView tv;
        public Temp(View itemView) {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.my_holder_item_tv);
        }
    }
}
