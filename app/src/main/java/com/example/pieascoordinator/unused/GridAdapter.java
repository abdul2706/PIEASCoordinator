//package com.example.pieascoordinator.Unused;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.pieascoordinator.R;
//import com.example.pieascoordinator.testingdata.OurDatabase;
//
//import java.util.Random;
//
//public class GridAdapter extends BaseAdapter {
//
//    private static final String TAG = "GridAdapter";
//
//    private String classNames[];
//    private Context context;
//    private LayoutInflater inflater;
//
//    public GridAdapter(Context context, String[] classNames) {
//        this.context = context;
//        this.classNames = classNames;
//    }
//
//    @Override
//    public int getCount() {
//        return classNames.length;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return classNames[i];
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        View gridView = view;
//
//        if (view == null) {
//            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            gridView = inflater.inflate(R.layout.z_grid_card, null);
//        }
//
//        TextView textView = gridView.findViewById(R.id.theTextId);
//        TextView teacherName = gridView.findViewById(R.id.teacherNameTextViewId);
//        TextView nUnreadMsgs = gridView.findViewById(R.id.nUnreadMsgsTextViewId);
//        TextView notif = gridView.findViewById(R.id.notif_id);
//        textView.setText(classNames[i]);
//        String instructorName = OurDatabase.getInstructorNameOf(classNames[i]);
//        if(instructorName.toLowerCase().equals("instructor Name Not Found!".toLowerCase())){
//            instructorName = "Sir Nauman Shamim";
//        }
//        teacherName.setText(instructorName);
//        int min = 2, max = 10, rnd;
//        Random r = new Random();
//        rnd = r.nextInt((max - min) + 1) + min;
//        nUnreadMsgs.setText(rnd + " Unread Messages");
//        min = 1;
//        max = 4;
//        r = new Random();
//        rnd = r.nextInt((max - min) + 1) + min;
//        switch (rnd) {
//            case 1:
//                notif.setBackgroundResource(R.drawable.notif_red);
//                break;
//            case 2:
//                notif.setBackgroundResource(R.drawable.notif_green);
//                break;
//            case 3:
//                notif.setBackgroundResource(R.drawable.notif_black);
//                break;
//            case 4:
//                notif.setBackgroundResource(R.drawable.notif_blue);
//                break;
//        }
//        return gridView;
//    }
//
//}
