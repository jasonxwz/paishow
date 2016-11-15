package com.example.asus.paishow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.paishow.utils.LoadDialog;
import com.example.asus.paishow.utils.Void_;
import com.example.asus.paishow.widiget.SelectableRoundedImageView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddContactActivity extends AppCompatActivity {

    private EditText mEtSearch;
    private LinearLayout searchItem;
    private TextView searchName;
    private SelectableRoundedImageView searchImage;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mEtSearch = (EditText) findViewById(R.id.search_edit);
        searchItem = (LinearLayout) findViewById(R.id.search_result);
        searchName = (TextView) findViewById(R.id.search_name);
        searchImage = (SelectableRoundedImageView) findViewById(R.id.search_header);

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    mPhone = s.toString().trim();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           // LoadDialog.show(AddContactActivity.this);
                            RequestParams rp = new RequestParams("http://10.40.5.57:8080/MyPaishow/searchcontactservlet");
                            rp.addBodyParameter("phoneNum", mPhone);
                            x.http().post(rp, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if (result != "null"){
                                        Void_.hideKeyBoard(AddContactActivity.this, mEtSearch);

                                    }else {
                                        Toast.makeText(AddContactActivity.this, "该手机号未注册" + result, Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {

                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    }).start();
                } else {
                    searchItem.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
