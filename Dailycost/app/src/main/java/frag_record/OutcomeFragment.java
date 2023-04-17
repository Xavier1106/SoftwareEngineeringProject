package frag_record;

import com.example.dailycost.R;

import java.util.List;

import db.AccountBean;
import db.DBManager;
import db.TypeBean;

public class OutcomeFragment extends BaseRecordFragment  {
    List<TypeBean> outlist;
    @Override
    public void loadDataToGv() {
        super.loadDataToGv();
        //获取数据库当中的数据源
        outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);
        adapter.notifyDataSetChanged();
        typeTv.setText("其他");
        typeIv.setImageResource(R.mipmap.ic_qita_fs);
        if(flag){
            TypeBean type = new TypeBean();
            type.setKind(accountBean.getKind());
            type.setTypename(accountBean.getTypename());
            int pos =outlist.indexOf(type);
            adapter.selectPos=pos;
            adapter.notifyDataSetChanged();
            typeTv.setText(accountBean.getTypename());
            typeIv.setImageResource(accountBean.getsImageId());
            timeTv.setText(accountBean.getTime());
        }
    }
    public OutcomeFragment(){
        super();
    }
    public OutcomeFragment(AccountBean accountBean){
        super();
        flag=true;
        this.accountBean=accountBean;
    }
    @Override
    public void saveAccountToDB() {
        accountBean.setKind(0);
        if(flag)
            DBManager.updateItemFromAccount(accountBean);
        else
            DBManager.insertItemToAccounttb(accountBean);
    }
}
