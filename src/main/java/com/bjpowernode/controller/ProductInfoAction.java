package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductInfoService;
import com.bjpowernode.service.ProductTypeService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author wzxstart
 * @create 2022-11-09 20:21
 */
@Controller
@RequestMapping("/prod")
public class ProductInfoAction {
    String fileName="";
//    每页显示的记录数
    public static final int PAGE_SIZE=5;
//    切记，在界面层一定要有业务逻辑层的对象
    @Autowired
    ProductInfoService productInfoService;
//    显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list= productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
//    显示第一页的五条记录
@RequestMapping("/split")
    public String split(HttpServletRequest request){
        PageInfo info=null;
    Object vo = request.getSession().getAttribute("prodVo");
    if(vo!=null){
        info=productInfoService.splitPageVo((ProductInfoVo) vo,PAGE_SIZE);
        request.getSession().removeAttribute("prodVo");
    }else{
        info=productInfoService.splitPage(1,PAGE_SIZE);
    }

    request.setAttribute("info",info);
        return "product";
}
//ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public  void ajaxSplit(ProductInfoVo vo, HttpSession session){
//        取得当前page页码的数据
        PageInfo info = productInfoService.splitPageVo(vo,PAGE_SIZE);
   session.setAttribute("info",info);
    }
    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("ajaxImg")
    public  Object ajaxImg(MultipartFile pimage, HttpServletRequest request)  {
////提取生成文件名 UUID+上传文件的后缀.jpg .png
//        String  saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getName());
//        //得到项目中图片存储的路径
//        String path = request.getServletContext().getRealPath("/image_big");
//        //转存
//        try {
//            pimage.transferTo(new File(path+File.separator+saveFileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        返回客户端JSON对象，封装图片的路径，为了在页面立刻实现回显
//        JSONObject object = new JSONObject();
//        object.put("imgurl",saveFileName);
//        return object.toString();

//1.提取生成文件名 UUID+上传图片的后缀 .jpg
        fileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());

//2.得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
//注意，此为target/目录下的资源
//转存-->path: D:\Java架构师资料\Study\项目\MMSM\image_big  File.separator: \  fileName:  fehuwhjf.jpg
        try {
            pimage.transferTo(new File(path + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
//返回客户端json对象，封装图片的路经，为在页面中立即回显
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("imgurl", fileName);
        return jsonObject.toString();




    }
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(fileName);
        info.setpDate(new Date());
        int num = -1;
        try {
            num= productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","增加成功");
        }else{
            request.setAttribute("msg","增加失败");
        }
        //清空fileName变量中的内容，为了下次增加或修改的异步ajax的上传处理
        fileName="";
        //增加成功后，应该重新返回数据库，所以要跳到分页显示的action上



        return "forward:/prod/split.action";
    }

    @RequestMapping ("/one")
    public String one(int pid, ProductInfoVo vo,HttpSession session,Model model){
            ProductInfo info = productInfoService.getByID(pid);
            model.addAttribute("prod",info);
            //将多条件和页码放入session中，更新处理结束后分页读取条件和页码进行处理
        session.setAttribute("prodVo",vo);
            return "update";
        }
        @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        //因为ajax的异步图片上传，如果有上传过，
            //则fileName中有上传过来的图片的名称，如果没有使用ajax上传过图片，则fileName=""
            //实体类info使用隐藏表单域提供上来的pImage原始图片的名称
            if(!fileName.equals("")){
                info.setpImage(fileName);
            }
            //完成更新操作
            int num=-1;
            try {
                num=productInfoService.update(info);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(num>0){
                request.setAttribute("msg","更新成功");
            }else{
                request.setAttribute("msg","更新成功");
            }
            //处理完更新后，fileName里可能有数据
            //而下次更新时要使用这个变量作为判断的依据。就会出错，所以必须清空fileName.
            fileName="";
            return "forward:/prod/split.action";
        }
       @PostMapping ("/delete")
       public String delete(Integer pid,ProductInfoVo vo,HttpServletRequest request){
        int num=-1;

           try {
               num=productInfoService.delete(pid);
           } catch (Exception e) {
               e.printStackTrace();
           }
           if(num>0){
               request.setAttribute("msg","删除成功");
               request.getSession().setAttribute("deleteProdVo",vo);
           }else{
               request.setAttribute( "msg","删除失败");
           }
           return "forward:/prod/deleteAjaxSplit.action";

       }
       @ResponseBody
      @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
      public Object deleteAjaxSplit (HttpServletRequest request){
//取得第1页的数据
           PageInfo info =null;
           Object vo= request.getSession().getAttribute("deleteProdVo");
          if(vo!=null){
              info = productInfoService.splitPageVo((ProductInfoVo) vo, PAGE_SIZE);
          }else {
              info = productInfoService.splitPage(1, PAGE_SIZE);
          }
              request.getSession().setAttribute("info",info);
              return request.getAttribute("msg");
       }
       //批量删除商品功能
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String pids,HttpServletRequest request) {
        String[] ps=pids.split(",");


        try {
            int num=productInfoService.deleteBatch(ps);
            if (num > 0) {
                request.setAttribute("msg","批量删除成功");
            }else{
                request.setAttribute("msg","批量删除失败");
            }
        } catch (Exception e) {
            request.setAttribute("msg","商品不可删除");
        }


        return "forward:/prod/deleteAjaxSplit.action";
    }
    //多条件查询功能实现
    @ResponseBody
    @RequestMapping("/condition")
    public void condition(ProductInfoVo vo,HttpSession session) {
        List<ProductInfo> list = productInfoService.selectCondition(vo);
        session.setAttribute("list",list);

    }



}
