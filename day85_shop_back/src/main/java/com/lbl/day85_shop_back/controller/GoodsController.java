package com.lbl.day85_shop_back.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.lbl.service.IGoodsService;
import com.lbl.service.entity.Goods;
import com.lbl.service.util.HttpClientUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fdfs.host}")
    private String host;

    @RequestMapping("/goodsmananger")
    public String goodsmananger(Model model){
        List<Goods> listGoods = goodsService.goodsManager();
        model.addAttribute("goods", listGoods);
        return "goodsmananger";
    }

    @RequestMapping("/addGoods")
    public String addGoods(Goods goods, MultipartFile file){
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "JPG",
                    null);
            //获得上传到fastDFS的图片路径
            String fullPath = storePath.getFullPath();
            goods.setGimage(fullPath);
            goods.setTid(0);

            //添加商品到数库中
            int addGoods = goodsService.addGoods(goods);
            goods.setId(addGoods);
            System.out.println("addGoods---->"+addGoods);

            //将goods对象转换成json字符串
            String json = new Gson().toJson(goods);
            //将数据同步到搜索库
            String result = HttpClientUtil.dopost("http://localhost:8083/solr/addindex", json);

            //添加商品后，将这个商品的详情生成静态页
            String itemjson = new Gson().toJson(goods);
            String resource = HttpClientUtil.dopost("http://localhost:8084/item/createhtml", itemjson);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/goods/goodsmananger";
    }

    /**
     * 回显图片
     * @param imgpath
     * @param response
     */
    @RequestMapping("/findImg")
    public void findImg(String imgpath, HttpServletResponse response) {
        String path = host + imgpath;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            InputStream inputStream = coon.getInputStream();

            IOUtils.copy(inputStream, response.getOutputStream());

            inputStream.close();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
