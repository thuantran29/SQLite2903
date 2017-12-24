package trannguyentanthuan.sqlite2903;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button btnThem,btnXoa,btnCapNhat;
    ArrayList<HocSinh> arrayHocSinh;
    EditText edtHoTen, edtNamSinh;
    ListView lstDanhSach;
    AdapterHocSinh adapter = null;
    SQLite database;
    int vitri=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        //khởi tạo adapter
        adapter = new AdapterHocSinh(this, R.layout.layout_hocsinh, arrayHocSinh);
        lstDanhSach.setAdapter(adapter);
        //khởi tạo database
         database = new SQLite(MainActivity.this, "truonghoc.sqlite", null, 1);

        //tạo bảng Nằm trong function QueryData
        database.QueryData("CREATE TABLE IF NOT EXISTS HocSinh(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HoTen VARCHAR,NamSinh INTEGER)");
        Cursor cursorHS = database.GetData("SELECT * FROM HocSinh");
        // thêm dữ liệu vào bảng
//         database.QueryData("INSERT INTO HocSinh VALUES(null,'Cu Tèo',1994)");
        loadData();



        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtHoTen.getText().toString();
                int nsinh = Integer.parseInt(edtNamSinh.getText().toString().trim());
                database.QueryData("INSERT INTO HocSinh VALUES(null,'" + ten + "','" + nsinh + "')");
                Toast.makeText(MainActivity.this, "ĐÃ Thêm", Toast.LENGTH_SHORT).show();

                loadData();
                resettext();
            }
        });


        lstDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Đã Hiện",Toast.LENGTH_SHORT).show();
                edtHoTen.setText(arrayHocSinh.get(position).HoTen);
                edtNamSinh.setText(arrayHocSinh.get(position).NamSinh+"");
                vitri=arrayHocSinh.get(position).Id;
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cập nhật dữ liệu vào database
                if (vitri>0) {
                    String tenMoi = edtHoTen.getText().toString().trim();
                    int nsMoi = Integer.parseInt(edtNamSinh.getText().toString().trim());

                    database.QueryData("UPDATE HocSinh SET HoTen='" + tenMoi + "'," +
                            "NamSinh='" + nsMoi + "' WHERE Id='" + vitri + "'");
                    Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    //Load lại dữ liệu từ database ra mảng, rồi mảng mới ra listview
                    loadData();
                    vitri = 0;
                    resettext();
                }else {
                    Toast.makeText(MainActivity.this,"Chưa chọn Học Sinh",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lstDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vitri=arrayHocSinh.get(position).Id;//xet lai vị trí cho mang ko thì nó kko xóa
                XacNhan(arrayHocSinh.get(position).HoTen);
                return false;
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xóa dữ liệu trong database
                database.QueryData("DELETE FROM HocSinh WHERE Id='"+vitri+"'");
                Toast.makeText(MainActivity.this,"Đã Xóa ",Toast.LENGTH_SHORT).show();
                //load lại dữ liệu qua mảng rồi mảng mới dổ dữ liệu qua listview
                loadData();
                resettext();
            }
        });
    }

    private void resettext(){
        edtHoTen.setText("");
        edtNamSinh.setText("");
    }
    private void XacNhan(String hoten){
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setTitle("Thông báo");
        dialogbuilder.setMessage("Bạn Có muốn Xóa Học Sinh "+hoten+" không?");
        dialogbuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                database.QueryData("DELETE FROM HocSinh WHERE Id='"+vitri+"'");
                Toast.makeText(MainActivity.this,"Đã Xóa",Toast.LENGTH_SHORT).show();
                //load lại dữ liệu qua mảng rồi mảng mới dổ dữ liệu qua listview
                loadData();
            }
        });
        dialogbuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogbuilder.show();
    }
    private void loadData(){//khai báo database toàn cục
        Cursor cursorHS = database.GetData("SELECT * FROM HocSinh");
        // SELECT * :id=0 hoten=1 namsinh=2
        //SELECT hoten,namsinh  :hoten=0,namsinh=1
        arrayHocSinh.clear();
        while (cursorHS.moveToNext()) {
            String hoten = cursorHS.getString(1);
            int id = cursorHS.getInt(0);
            int namsinh = cursorHS.getInt(2);
            arrayHocSinh.add(new HocSinh(id,hoten, namsinh));
        }
        adapter.notifyDataSetChanged();
    }

    private void AnhXa() {
        btnXoa =(Button) findViewById(R.id.buttonXoa);
        btnCapNhat =(Button) findViewById(R.id.buttonCapNhat);
        btnThem = (Button) findViewById(R.id.button);
        edtHoTen = (EditText) findViewById(R.id.editTextHoTen);
        edtNamSinh = (EditText) findViewById(R.id.editTextNamSinh);
        lstDanhSach = (ListView) findViewById(R.id.listViewHocSinh);
        arrayHocSinh = new ArrayList<>();
    }
}
