package com.example.mealme.controller.user;

import com.example.mealme.dto.CompanyRegistrationFileDto;
import com.example.mealme.service.user.UserFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userFiles/*")
public class UserFileController {
    private final UserFileService userFileService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/imgList")
    public CompanyRegistrationFileDto img(Long companyNumber){
        return userFileService.companyRegistrationFindFile(companyNumber);
    }

    // 서버 컴퓨터에 저장된 파일을 복사하여 넘겨주는 핸들러
    @GetMapping("/display")
    public byte[] display(String fileName) throws IOException {
        return FileCopyUtils.copyToByteArray(new File(fileDir, fileName));
    }

    @GetMapping("/download")
    // HttpServletResponse 와 동일하게 ResponseEntity객체는 응답을 나타내는 객체이다.
    // 스프링에서 지원하는 응답 객체이며 기존의 응답 객체보다 간편하게 설정할 수 있다는 장점이 있다.
    public ResponseEntity<Resource> download(String fileName) throws UnsupportedEncodingException {
        // Resource 객체는 자원을 나타내는 객체이다. -> 스프링에서 지원하는 타입
        // 우리는 파일이라는 리소스를 다운로드 처리하기 위해 사용하고 있으며 File객체보다 많은 종류의
        // 리소스를 다룰 수 있고 스프링과 호환성이 좋다.
        // Resource는 인터페이스 이므로 객체를 만들 때는 자식 클래스를 사용한다.

        // JSP에서 File객체를 사용하여 다운로드를 구현했던 것을 스프링에서 지원하는 Resource 객체체
        Resource resource = new FileSystemResource(fileDir + fileName);
        HttpHeaders headers = new HttpHeaders();
        String name = resource.getFilename();
        System.out.println(name);
        // uuid를 제거하고 다운받도록 한다.
        // indexOf("타겟") : 타겟이 몇 번째 인덱스인지 반환해준다.
        // aaa_bbb : 3
        // substring(index) : 문자열에서 해당 인덱스 번호 까지만 잘라준다.
        name = name.substring(name.indexOf("_") + 1);
        System.out.println(name);
        headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
