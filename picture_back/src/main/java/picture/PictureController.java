package picture;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class PictureController {
	@Autowired
	PictureMapper mapper;
	
	// 모든 파일의 정보를 불러와요(나중엔 파일 그자체를 불러올 수 있도록 해야되요)
	@GetMapping("/loadAllPicture")
	public List loadAllPicture() {
		List<PictureDto> list = mapper.loadAllPicture();
		System.out.println("파일리스트 : " + list);
		return list;
	}
	
	@PostMapping("/uploadPicture")
	public void uploadPicture(@Value("${file.path}") String filePath, @RequestParam("file") MultipartFile file) throws Exception{
		// 먼저 데이터베이스 파일들의 용량전체를 파악해요 폴더 전체 용량이 50MB까지만 되도록 하고 싶어요 
		
		// 받은 파일의 정보를 dto에 저장해요
		PictureDto dto = new PictureDto(UUID.randomUUID().toString(), file.getOriginalFilename(), filePath, file.getSize());
		// 파일의 정보를 데이터 베이스에 저장해요
		mapper.setPictureDto(dto);
		// 실제로 파일을 로컬환경에 저장해요
		file.transferTo(new File(filePath, dto.getFileName()));
	}
	
//	@GetMapping("/pictureLoad")
//	public ResponseEntity<Resource> pictureLoad(@Value("${file.path}") String filePath) throws IOException {
//		System.out.println("파일 불러오기 메서드 실행해요");
//		System.out.println(filePath + "\\" + "다운로드.png");
//		String imageRoot = filePath + "\\" + "다운로드.png";
//		
//		Resource resource = new FileSystemResource(imageRoot);
//		
//		HttpHeaders header = new HttpHeaders();
//        Path Path = null;
//        try {
//            Path = Paths.get(imageRoot);
//            // 인풋으로 들어온 파일명 .png / .jpg 에 맞게 헤더 타입 설정
//            header.add("Content-Type", Files.probeContentType(Path));
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//		
//	    return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
//	}
//	@GetMapping("/pictureLoad")
//	public byte[] pictureLoad(@Value("${file.path}") String filePath) throws IOException {
//		System.out.println("파일 불러오기 메서드 실행해요");
//		System.out.println(filePath + "\\" + "다운로드.png");
//		String imageRoot = filePath + "\\" + "다운로드.png";
//		
//		InputStream imageStream = new FileInputStream(imageRoot);
//		byte[] imageByteArray = IOUtils.toByteArray(imageStream);
//		
//		System.out.println(Arrays.toString(imageByteArray));
//		File file = new File(imageRoot);
//		byte[] fileContent = Files.readAllBytes(file.toPath());
//		System.out.println(Arrays.toString(fileContent));
//	    return imageByteArray;
//	}
	@GetMapping("/pictureLoad")
	public String pictureLoad(@Value("${file.path}") String filePath) throws IOException {
		System.out.println("파일 불러오기 메서드 실행해요");
		System.out.println(filePath + "\\" + "다운로드.png");
		String imageRoot = filePath + "\\" + "다운로드.png";
		
		byte[] fileContent = FileUtils.readFileToByteArray(new File(imageRoot));
		String encodedString = Base64.getEncoder().encodeToString(fileContent);

		System.out.println(encodedString);
		return encodedString;
	}
}
