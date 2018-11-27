package cc.royao.commons;

public class Constants {
    public static final String MESSAGE ="__message__";
    // 正常状态值
    public static final int STATUS_NORMAL = 200;

    // 删除状态值
    public static final int STATUS_DELETE = 404;

    // 彻底删除
    public static final int STATUS_COMPLETELY_DELETE = 999;

    // 正常分页大小
    public static final int PAGE_LIMIT = 10;

    // cms栏目根分类ID
    public static final long CMS_CATEGORY_ROOT_ID = 1L;

    // 默认的栏目页面模板
    public static final String CMS_DETAULT_CHANNEL_TPL = "list";

    // 默认的内容页面模板
    public static final String CMS_DETAULT_CONTENT_TPL = "content";

    // 默认的站点模板路径
    public static final String CMS_DETAULT_TEMPLATE_PATH = "default";
}
