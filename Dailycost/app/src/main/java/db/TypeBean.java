package db;

//收入或者支出具体支出的类
public class TypeBean {
    int id;             //自增主码
    String typename;    //类型名称
    int imageId;        //未被选中图片id
    int simageId;       //被选中图片id
    int kind;           //收入=1 支出=0

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getSimageId() {
        return simageId;
    }

    public void setSimageId(int simageId) {
        this.simageId = simageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public TypeBean() {}
    public boolean equals(Object obj) {
        if (obj instanceof TypeBean) {
            TypeBean typeBean = (TypeBean) obj;
            return this.kind==typeBean.kind&&this.typename.equals(typeBean.typename);
        }
        return false;
    }
    public TypeBean(int id, String typename, int imageId, int simageId, int kind) {
        this.id = id;
        this.typename = typename;
        this.imageId = imageId;
        this.simageId = simageId;
        this.kind = kind;
    }
}
