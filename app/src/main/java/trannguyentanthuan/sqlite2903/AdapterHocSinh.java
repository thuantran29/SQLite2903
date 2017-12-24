package trannguyentanthuan.sqlite2903;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterHocSinh extends BaseAdapter {

    private Context context;
    private int layout;
    private List<HocSinh> hocSinhList;

    public AdapterHocSinh(Context context, int layout, List<HocSinh> hocSinhList) {
        this.context = context;
        this.layout = layout;
        this.hocSinhList = hocSinhList;
    }

    @Override
    public int getCount() {
        return hocSinhList.size();
    }

    @Override
    public Object getItem(int i) {
        return hocSinhList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(layout, null);

        TextView txtHoTen   = (TextView) view.findViewById(R.id.textView);
        TextView txtNamSinh = (TextView) view.findViewById(R.id.textView2);

        HocSinh hocSinh = (HocSinh) getItem(i);

        txtHoTen.setText(hocSinh.HoTen);
        txtNamSinh.setText("Năm sinh: " + hocSinh.NamSinh);

        return view;
    }
}
