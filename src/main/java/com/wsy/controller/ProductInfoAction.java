package com.wsy.controller;

import com.github.pagehelper.PageInfo;
import com.wsy.pojo.ProductInfo;
import com.wsy.service.ProductInfoService;
import com.wsy.utils.FileNameUtil;
import com.wsy.vo.ProductInfoVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("prod")
public class ProductInfoAction {
    //controller层调用service层，这里是ProductInfoService
    //每页显示的记录数
    public static final int PAGE_SIZE = 5;

    String saveFileName = "";

    @Autowired
    ProductInfoService productInfoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request) {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    //显示第一页的5个商品
    @RequestMapping("/split")
    public String split(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("prodVo");
        if(vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
            request.getSession().removeAttribute("prodVo");
        }
        else
            info = productInfoService.splitPage(1, PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }

    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    //这里是HttpSession，不是HttpRequest，是因为生命周期的原因
    public void ajaxSplit(ProductInfoVo vo, HttpSession session) {
        //每一页都应该是一个新的pageinfo对象，因为这个对象中的当前页，前一页，后一页等属性是根据当前页来的
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info", info);
    }

    //异步ajax的上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage, HttpServletRequest request) {
        //提取生成的文件名+后缀
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到图片存储的路径:F:\mimissm\image_big
        String realPath = request.getServletContext().getRealPath("/image_big");
        //转存,File.separator是 \
        try {
            pimage.transferTo(new File(realPath + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端json对象，封装图片的路径，为了在页面回显
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl", saveFileName);
        return jsonObject.toString();
    }

    //增加商品
    @RequestMapping("/save")
    public String save(ProductInfo productInfo, HttpServletRequest request) {
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());
        //productInfo中有5个传过来的数据和图片，时间
        int num = -1;
        try {
            num = productInfoService.save(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0)
            request.setAttribute("msg", "增加成功");
        else
            request.setAttribute("msg", "增加失败");
        //清空saveFileName，为了下次增加或修改异步ajax的上传处理
        saveFileName = "";
        //增加完重新访问数据库，所以跳转到分页显示的action上，即/Split
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int id,ProductInfoVo vo, Model model,HttpSession session) {
        ProductInfo info = productInfoService.getById(id);
        model.addAttribute("prod", info);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request) {
        if (saveFileName.equals(""))
            info.setpImage(saveFileName);
        //完成更新操作
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0)
            request.setAttribute("msg", "更新成功");
        else
            request.setAttribute("msg", "更新失败");
        //清空saveFileName，为了下次增加或修改异步ajax的上传处理
        saveFileName = "";
        //增加完重新访问数据库，所以跳转到分页显示的action上，即/Split
        return "forward:/prod/split.action";
    }

    @RequestMapping("/delete")
    public String delete(int id, ProductInfoVo vo, HttpServletRequest request) {
        int num = -1;
        try {
            num = productInfoService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0) {
            request.setAttribute("msg", "删除成功");
            request.getSession().setAttribute("deleteProdVo", vo);
        }else
            request.setAttribute("msg", "删除失败");
        //删除结束后跳到
        return "forward:/prod/deleteAjaxSplit.action";
    }

    //删除请求对应的ajax
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request) {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProdVo");
        if (vo != null){
            info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
        }else
            //获得第一页的数据
         info = productInfoService.splitPage(1, PAGE_SIZE);
        request.getSession().setAttribute("info", info);
        return request.getAttribute("msg");
    }

    @RequestMapping("/deleteBatch")
    //批量删除商品
    public String deleteBatch(String pids, HttpServletRequest request) {
        //字符串转为字符数组
        String[] ids = pids.split(",");
        int num = -1;
        try {
            num = productInfoService.deleteBatch(ids);
            if (num > 0)
                request.setAttribute("msg", "删除成功");
            else
                request.setAttribute("msg", "删除失败");
        } catch (Exception e) {
           request.setAttribute("msg", "不可删除");
        }//删除结束后跳到
        return "forward:/prod/deleteAjaxSplit.action";
    }
    @ResponseBody
    @RequestMapping("/condition")
    //多条件查询
    public void condition(ProductInfoVo vo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
}
