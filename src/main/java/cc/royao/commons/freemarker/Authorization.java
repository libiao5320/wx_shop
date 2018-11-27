package cc.royao.commons.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

@SuppressWarnings("deprecation")
public class Authorization implements TemplateMethodModel {

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arg0) throws TemplateModelException {

//        boolean hasAuthority = false;
//        if (arg0.size() == 2 && arg0.get(1) != null && !"".equals(arg0.get(1).toString()) && arg0.get(0) != null) {
//            String[] authorities = arg0.get(0).toString().split(";");
//            String authorityString = arg0.get(1).toString();
//            // 验证用户是否有权限
//            for (String at : authorities) {
//                if (at.matches("[0-9]+") && AuthHelper.hasAuthority(Integer.valueOf(at), authorityString)) {
//                    hasAuthority = true;
//                    break;
//                }
//            }
//
//        }
//        return hasAuthority;
        return null;
    }

}
