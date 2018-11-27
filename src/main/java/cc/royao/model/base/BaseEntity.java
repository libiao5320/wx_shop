package cc.royao.model.base;

import java.io.Serializable;

/**
 * Created by ntz on 2015/11/18.
 */
public class BaseEntity implements Serializable {

    //分页对象
    protected  PageObject object  ;

    public void setObject(PageObject object) {
        this.object = object;
    }

    public PageObject getObject() {
        return object;
    }
}
