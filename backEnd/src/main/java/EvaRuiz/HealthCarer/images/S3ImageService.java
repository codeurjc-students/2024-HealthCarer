package EvaRuiz.HealthCarer.images;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.io.File;
import java.util.UUID;

@Service("storageService")
@Profile("production")
public class S3ImageService implements ImageService {

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Value("${amazon.s3.endpoint}")
    private String url;

    @Value("${amazon.s3.region}")
    private String region;

    private S3Client s3;

    @PostConstruct
    public void initS3Client() {
        this.s3 = S3Client.builder()
            .region(Region.of(region))
            .build(); 

        createBucketIfNotExist();
    }

    private void createBucketIfNotExist() {

        try {
            s3.headBucket(
                HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );
        } catch (NoSuchBucketException e) {
            s3.createBucket(
                CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build()
            );
        }
    }

    @Override
    public String createImage(MultipartFile multiPartFile) {
        String fileName = "image_" + UUID.randomUUID() + "_" +multiPartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        try {
            multiPartFile.transferTo(file);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save image on S3", ex);
        }

        s3.putObject(
            PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build(), 
            RequestBody.fromFile(file)
        );

        file.delete();
        return url + fileName;
    }

    @Override
    public void deleteImage(String image) {
        String[] tokens = image.split("/");
        String fileName = tokens[tokens.length - 1];

        s3.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()
        );
    }

}
