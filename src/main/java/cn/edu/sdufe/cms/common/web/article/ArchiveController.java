package cn.edu.sdufe.cms.common.web.article;

import cn.edu.sdufe.cms.common.entity.article.Archive;
import cn.edu.sdufe.cms.common.entity.article.Article;
import cn.edu.sdufe.cms.common.service.article.ArchiveManager;
import cn.edu.sdufe.cms.common.service.article.ArticleManager;
import cn.edu.sdufe.cms.common.service.article.CategoryManager;
import cn.edu.sdufe.cms.common.service.link.LinkManager;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springside.modules.mapper.JsonMapper;

import java.util.List;

/**
 * 归类功能
 * <p/>
 * User: pengfei.dongpf@gmail.com, baitao.jibt@gmail.com
 * Date: 12-4-2
 * Time: 上午11:43
 */
@Controller
@RequestMapping(value = "/archive")
public class ArchiveController {

    private ArchiveManager archiveManager;
    private CategoryManager categoryManager;
    private ArticleManager articleManager;
    private LinkManager linkManager;

    private static JsonMapper mapper = JsonMapper.nonEmptyMapper();

    /**
     * 显示所有归类
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    public String articleListOfArchive(Model model) {
        model.addAttribute("archives", archiveManager.getTopTen());
        model.addAttribute("categories", categoryManager.getNavCategory());
        model.addAttribute("newArticles", articleManager.getTopTen());
        return "article/archives";
    }

    /**
     * 显示归档编号为id的文章列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "list/{id}")
    public String articleListByArchiveId(Model model, @PathVariable("id") Long id) {
        Archive archive = archiveManager.get(id);
        Long total = Long.valueOf(archive.getArticleList().size());
        int limit = 10;
        Long pageCount;
        if (total % limit == 0) {
            pageCount = total / limit;
        } else {
            pageCount = total / limit + 1;
        }
        model.addAttribute("articles", articleManager.getByArchiveId(id, 0, limit));//文章列表
        model.addAttribute("categories", categoryManager.getNavCategory());//导航菜单
        model.addAttribute("archive", archive);
        model.addAttribute("archives", archiveManager.getTopTen());//边栏归档日志
        model.addAttribute("newArticles", articleManager.getTopTen());//边栏最新文章
        model.addAttribute("total", total);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("links", linkManager.getAll());//页脚友情链接

        return "article/list";
    }

    /**
     * 获取归档编号为id的文章列表
     *
     * @param id
     * @param offset
     * @param limit
     * @return
     */
    @RequestMapping(value = "list/ajax/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Article> ajaxListOfArticleByArchive(@PathVariable("id") Long id, @RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        return articleManager.getByArchiveId(id, offset, limit);
    }

    /**
     * 按照指定月份归档
     *
     * @param dateTime
     */
    @RequestMapping(value = "save/{dateTime}", method = RequestMethod.GET)
    public String saveArchiveByMonth(@PathVariable("dateTime") String dateTime) {
        DateTimeFormatter pattern = DateTimeFormat.forPattern("yyyyMM");
        DateTime time = DateTime.parse(dateTime, pattern);
        archiveManager.save(time);
        return "redirect:/archive/list";
    }

    @Autowired
    public void setArchiveManager(ArchiveManager archiveManager) {
        this.archiveManager = archiveManager;
    }

    @Autowired
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Autowired
    public void setArticleManagerImpl(ArticleManager articleManager) {
        this.articleManager = articleManager;
    }

    @Autowired
    public void setLinkManager(LinkManager linkManager) {
        this.linkManager = linkManager;
    }
}