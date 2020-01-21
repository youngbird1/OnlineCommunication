package com.ct.websocket.controller;

import com.ct.websocket.common.util.FastdfsClientUtil;
import com.ct.websocket.common.util.R;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther:chent69
 * @date: 2020/1/21-10 :45
 */
@RestController
@RequestMapping("file")
public class FastdfsController {
    @Resource
    FastdfsClientUtil fastdfsClientUtil;

    @PostMapping("uploadImage1")
    public R uploadImage1(@RequestParam("file")MultipartFile file){
        String[] formats = {"jpg","gif","bmp","png"};
        String suffixName = FilenameUtils.getExtension(file.getOriginalFilename());
        boolean fileIsValid = false;
        //文件格式判断
        for(String format: formats){
            if(StringUtils.equals(suffixName , format)){
                fileIsValid = true;
                break;
            }
        }
        if(!fileIsValid){
            return R.error("file format is not supported!");
        }

        String imageStorePath = null;
        try{
            imageStorePath = fastdfsClientUtil.uploadFile(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.ok().put("imageStoreUrl",imageStorePath);
    }

    //todo 要解决的问题:上传失败  空指针异常
    @PostMapping("uploadImage")
    public R uploadImage(@RequestParam("file")MultipartFile file){
        String imageStorePath = null;
        try{
            imageStorePath = fastdfsClientUtil.uploadImage(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.ok().put("imageStoreUrl" , imageStorePath);
    }

    @PostMapping("uploadImageAndCrtThumb")
    public R uploadImageAndCrtThumb(@RequestParam("file")MultipartFile file){
        String imageStorePath = null;
        try{
            imageStorePath = fastdfsClientUtil.uploadImageAndCrtThumb(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        return R.ok().put("imageStoreUrl" , imageStorePath);
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response , @RequestParam("fileUrl") String fileUrl){
        try{
            fastdfsClientUtil.downLoadFile(response , fileUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @RequestMapping("delete")
    public R delete(@RequestParam("fileUrl") String fileUrl){
        if( fastdfsClientUtil.deleteFile(fileUrl)){
            return R.ok();
        }
        return R.error();
    }
}
