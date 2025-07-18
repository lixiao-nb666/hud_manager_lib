package com.nrmyw.hud_manager.activity.t800;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;

import com.nrmyw.hud_manager.R;

import java.util.List;

;

public class T800TestDataAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";

    private ItemClick itemClick;
    private LayoutInflater layoutInflater;
    private List<T800TestDataType> dataTypeList;

    public T800TestDataAdapter(Context context, ItemClick itemClick,List<T800TestDataType> dataTypeList) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.dataTypeList=dataTypeList;
    }





    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_t800_test_data, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        T800TestDataType t800TestDataType= dataTypeList.get(position);
        viewHodler.sendBt.setText(viewHodler.itemView.getContext().getResources().getText(t800TestDataType.getTitleRsId()));
        viewHodler.sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.nowSelect(t800TestDataType);
            }
        });
    }




    @Override
    public int getItemCount() {

        return  dataTypeList.size();
    }


    class ViewHodler extends RecyclerView.ViewHolder {
        private Button sendBt;


        public ViewHodler(View itemView) {
            super(itemView);
            sendBt=itemView.findViewById(R.id.bt_send);
        }
    }

    public interface ItemClick {
        void nowSelect( T800TestDataType t800TestDataType);
    }


}