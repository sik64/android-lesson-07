package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.AWSKeyDto;
import kr.easw.lesson06.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rest/aws")
public class AWSConroller {
    private final AWSService awsController;

    @PostMapping("/auth")
    private ModelAndView onAuth(AWSKeyDto awsKey) {
        try {
            awsController.initAWSAPI(awsKey);
            return new ModelAndView("redirect:/");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }

    @GetMapping("/list")
    private List<String> onFileList() {
        System.out.println(awsController.getFileList());
        System.out.println(awsController.getFileInfoList());
        return awsController.getFileList();
    }

    @PostMapping("/upload")
    private ModelAndView onUpload(@RequestParam MultipartFile file) {
        try {
            awsController.upload(file);
            return new ModelAndView("redirect:/admin?success=true");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModelAndView("redirect:/server-error?errorStatus=" + ex.getMessage());
        }
    }


    @PostMapping("/download")
    private ModelAndView onDownload(@RequestParam String fileName) {
        try {
           // 이곳에 파일 다운로드 로직, 혹은 서비스를 통한 다운로드 호출을 구현하십시오.
            System.out.format("Passed KEY : %s",fileName);
            awsController.download(fileName);
            return new ModelAndView("redirect:/download");
            //throw new IllegalStateException("기능이 구현되지 않았습니다.");
        } catch (Throwable e) {
            return new ModelAndView("redirect:/server-error?errorStatus=" + e.getMessage());
        }
    }
    @GetMapping("/info")
    private List<String> onFileInfoList() {
        System.out.println(awsController.getFileInfoList());
        return awsController.getFileInfoList();
    }

}
