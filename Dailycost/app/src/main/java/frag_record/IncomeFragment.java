package frag_record;

import com.example.dailycost.R;

import java.util.List;

import db.AccountBean;
import db.DBManager;
import db.TypeBean;

public class IncomeFragment extends BaseRecordFragment {

    @Override
    public void loadDataToGv() {
        super.loadDataToGv();

        //获取数据库当中的数据源
        List<TypeBean> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.in_qt_fs);
        if(flag){
            TypeBean type = new TypeBean();
            type.setKind(accountBean.getKind());
            type.setTypename(accountBean.getTypename());
            int pos =inlist.indexOf(type);
            adapter.selectPos=pos;
            adapter.notifyDataSetChanged();
            typeTv.setText(accountBean.getTypename());
            typeIv.setImageResource(accountBean.getsImageId());
            timeTv.setText(accountBean.getTime());
        }
    }
    public IncomeFragment(){
        super();
    }
    public IncomeFragment(AccountBean accountBean){
        super();
        flag=true;
        this.accountBean = accountBean;
    }

    @Override
    public void saveAccountToDB() {
        accountBean.setKind(1);
        if(flag)
            DBManager.updateItemFromAccount(accountBean);
        else
            DBManager.insertItemToAccounttb(accountBean);
    }
}
