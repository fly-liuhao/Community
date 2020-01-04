package cn.edu.tit.community.provider;

import cn.edu.tit.community.enums.CustomizeErrorCodeEnum;
import cn.edu.tit.community.exception.CustomizeException;
import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
public class UCloudProvider {

    // 公钥
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    // 私钥
    @Value("${ucloud.ufile.private-key}")
    private String perivateKey;

    // 域名范围
    @Value("${ucloud.ufile.region}")
    private String region;

    // 域名后缀
    @Value("${ucloud.ufile.proxy-suffix}")
    private String proxySuffix;

    // 桶名称
    @Value("${ucloud.ufile.bucket-name}")
    private String bucketName;

    // 文件URL过期时间
    @Value("${ucloud.ufile.expires-duration}")
    private Integer expiresDuration;


    /**
     * @param fileStream 待上传的文件流
     * @param mimeType   文件的类型contentType
     * @param fileName   文件名称
     * @return 存储后用于访问的文件URL
     */
    public String upLoad(InputStream fileStream, String mimeType, String fileName) {
        // 对象相关API的授权器
        ObjectAuthorization objectAuthorizer = new UfileObjectLocalAuthorization(publicKey, perivateKey);

        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(region, proxySuffix);

        // 上传文件
        String generatedFileName = "";
        String[] fileSpliter = fileName.split("\\.");
        if (fileSpliter.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + fileSpliter[fileSpliter.length - 1];
        } else {
            return null;
        }
        try {
            PutObjectResultBean response = UfileClient.object(objectAuthorizer, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucketName)
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String fileURL = UfileClient.object(objectAuthorizer, config)
                        .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, expiresDuration)
                        .createUrl();
                return fileURL;
            } else {
                throw new CustomizeException(CustomizeErrorCodeEnum.FILE_UPLOAD_FAIL);
            }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCodeEnum.FILE_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCodeEnum.FILE_UPLOAD_FAIL);
        }
    }
}
