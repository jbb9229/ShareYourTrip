package com.shareyourtrip.web.system;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@NoArgsConstructor
public class AWSS3Service {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(this.region)
            .build();
    }

    public String upload(MultipartFile file, String userEmail) throws IOException {
        String fileName = file.getOriginalFilename();
        LocalDate today = LocalDate.now();

        String saveDirectory = "images/posts/" + today + "/" + userEmail + "/" + fileName;

        Matcher fileTypeMatcher = Pattern.compile("(jpg|png|jpeg|bmp|gif|tiff)").matcher(fileName);
        String fileType = "";

        while (fileTypeMatcher.find()) {
            fileType = fileTypeMatcher.group();
        }

        ObjectMetadata metadata = new ObjectMetadata();

        if (fileType.length() > 0) {
            metadata.setContentType("image/" + fileType);
        }

        s3Client.putObject(new PutObjectRequest(bucket, saveDirectory, file.getInputStream(), metadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, saveDirectory).toString();
    }

}