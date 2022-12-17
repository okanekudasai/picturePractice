package picture;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PictureMapper {

	List<PictureDto> loadAllPicture();

	void setPictureDto(PictureDto dto);

}
