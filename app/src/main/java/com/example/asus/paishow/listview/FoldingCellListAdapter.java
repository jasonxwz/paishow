package com.example.asus.paishow.listview;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.R;
import com.example.asus.paishow.ramotion.foldingcell.FoldingCell;


import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<UserInfoBean.UserInfo> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    final ArrayList<UserInfoBean.UserInfo> userInfoList=new ArrayList<UserInfoBean.UserInfo>();
    private Context context ;



    public FoldingCellListAdapter(Context context, ArrayList<UserInfoBean.UserInfo> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get item for selected view
        UserInfoBean.UserInfo item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        System.out.println("-----------------------------123----------");
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);


//            viewHolder.price = (TextView)cell.findViewById(R.id.title_price);
//            viewHolder.time = (TextView) cell.findViewById(R.id.title_time_label);
//            viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
//            viewHolder.fromAddress = (TextView) cell.findViewById(R.id.title_from_address);
//            viewHolder.toAddress = (TextView) cell.findViewById(R.id.title_to_address);


//            viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
//            viewHolder.pledgePrice = (TextView) cell.findViewById(R.id.title_pledge);
            viewHolder.tv_title_place = ((TextView) cell.findViewById(R.id.tv_title_place));
            viewHolder.tv_guanzhuNum_con = ((TextView) cell.findViewById(R.id.tv_guanzhuNum_con));
            viewHolder.tv_guanzhuNum_title = ((TextView) cell.findViewById(R.id.tv_guanzhuNum_title));
            viewHolder.tv_fensiNum_title = ((TextView) cell.findViewById(R.id.tv_fensiNum_title));
            viewHolder.tv_shenglvNum_title = ((TextView) cell.findViewById(R.id.tv_shenglvNum_title));
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            viewHolder.tv_shenglvNum_con = ((TextView) cell.findViewById(R.id.tv_shenglvNum_con));
            viewHolder.tv_userName = ((TextView) cell.findViewById(R.id.tv_userName));
            viewHolder.tv_userLocation = ((TextView) cell.findViewById(R.id.tv_userLocation));
            viewHolder.tv_birthday = ((TextView) cell.findViewById(R.id.tv_birthday));
            viewHolder.tv_gexingqianming_con = ((TextView) cell.findViewById(R.id.tv_gexingqianming_con));
            viewHolder.tv_userImg = ((ImageView) cell.findViewById(R.id.tv_userImg));
            viewHolder.iv_userSex = ((ImageView) cell.findViewById(R.id.iv_userSex));
            viewHolder.title_from_address = ((TextView) cell.findViewById(R.id.title_from_address));
            viewHolder.tv_fensiNum_con = ((TextView) cell.findViewById(R.id.tv_fensiNum_con));
            viewHolder.tv_dengji_con = ((TextView) cell.findViewById(R.id.tv_dengji_con));
            viewHolder.tv_dengji_title = ((TextView) cell.findViewById(R.id.tv_dengji_title));
            //   viewHolder. head_image = ((ImageView) cell.findViewById(R.id.head_image));

            cell.setTag(viewHolder);
           // UserInfoBean.UserInfo userInfo=userInfoList.get(position);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
//        viewHolder.price.setText(item.getPrice());
//        viewHolder.time.setText(item.getTime());
//        viewHolder.date.setText(item.getDate());
//        viewHolder.fromAddress.setText(item.getFromAddress());
//        viewHolder.toAddress.setText(item.getToAddress());
//        viewHolder.requestsCount.setText(String.valueOf(item.getRequestsCount()));
//        viewHolder.pledgePrice.setText(item.getPledgePrice());

        try {
            viewHolder.tv_title_place.setText(URLDecoder.decode(item.userLocation,"utf-8"));
            viewHolder.tv_guanzhuNum_con.setText(item.userRank+"");
            viewHolder.tv_guanzhuNum_title.setText(item.userRank+"");
            viewHolder.tv_dengji_title.setText(item.userDengji+"");
            viewHolder.tv_dengji_con.setText(item.userDengji+"");
            viewHolder.tv_fensiNum_con.setText(item.userRenqi+"");
            viewHolder.tv_fensiNum_title.setText(item.userRenqi+"");
            viewHolder.tv_shenglvNum_title.setText(item.userShenglv);
            viewHolder.tv_shenglvNum_con.setText(item.userShenglv);
            viewHolder.tv_userName.setText(URLDecoder.decode(item.userName,"utf-8"));
            viewHolder.tv_userLocation.setText(URLDecoder.decode(item.userLocation,"utf-8"));
            viewHolder.tv_birthday.setText(URLDecoder.decode(item.userBirthday,"utf-8"));
            viewHolder.tv_gexingqianming_con.setText(URLDecoder.decode(item.userJianjie,"utf-8"));
            viewHolder.title_from_address.setText(URLDecoder.decode(item.userName,"utf-8"));
            x.image().bind(viewHolder.tv_userImg,URLDecoder.decode(item.userTouxiang,"utf-8"));


            if(URLDecoder.decode(item.userSex,"utf-8").equals("男")){
                viewHolder.iv_userSex.setImageResource(R.drawable.man);
            }else if(URLDecoder.decode(item.userSex,"utf-8").equals("女")) {
                viewHolder.iv_userSex.setImageResource(R.drawable.woman);
            }


          //  x.image().bind(viewHolder. head_image, URLDecoder.decode(item.userBackground,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        item.setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });
        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }


        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView price;
        TextView contentRequestBtn;
        TextView pledgePrice;
        TextView fromAddress;
        TextView toAddress;
        TextView requestsCount;
        TextView date;
        TextView time;
        TextView tv_guanzhuNum_title;
        TextView tv_fensiNum_title;
        TextView tv_shenglvNum_title;
        TextView tv_userName;
        TextView tv_userLocation;
        TextView tv_birthday;
        TextView tv_gexingqianming_con;
        ImageView tv_userImg;
        ImageView head_image;
        TextView tv_shenglvNum_con;
        ImageView iv_userSex;
        TextView title_from_address;
        TextView tv_fensiNum_con;
        TextView tv_dengji_con;
        TextView tv_dengji_title;
        TextView tv_guanzhuNum_con;
        TextView tv_title_place;


   }
}
