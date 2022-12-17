package picture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PictureDto {
	private String fileName;
	private String fileOriginal;
	private String fileUrl;
	private long fileVolume;
}
