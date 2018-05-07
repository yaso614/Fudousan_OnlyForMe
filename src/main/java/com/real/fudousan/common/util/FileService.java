package com.real.fudousan.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.real.fudousan.common.exception.DuplicateFileNameException;

/**
 * 파일 관련 유틸 업로드한 파일의 저장 & 서버에 저장된 파일 삭제 등의 기능 제공
 */
public class FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	/**
	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴
	 * 
	 * @param mfile
	 *            업로드된 파일
	 * @param path
	 *            저장할 경로
	 * @param useOriginalName 중복이여도 파일 이름 안 바꿀것인가?(무조건 원래 파일 명 사용할 것인가?)
	 * @return 저장된 파일명
	 * @throws DuplicateFileNameException
	 *             원래 이름을 사용할 수 없을 때 발생
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath, boolean useOriginalName) {
		logger.info("saveFile("+mfile+", "+uploadPath+", "+useOriginalName+") Start");
		// 업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			return null;
		}
		
		// 저장 폴더가 없으면 생성
		File path = new File(uploadPath);
		if (!path.isDirectory()) {
			path.mkdirs();
		}
		
		// 원본 파일명
		String originalFilename = mfile.getOriginalFilename();

		String savedFilename = originalFilename;
		if (!useOriginalName) {
			// 저장할 파일명을 오늘 날짜의 년월일로 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			savedFilename = sdf.format(new Date());
		}

		// 원본 파일의 확장자
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.');
		logger.info("fileName : " + originalFilename + ", lastIndex : " + lastIndex);
		
		// 확장자가 없는 경우
		if (lastIndex == -1) {
			ext = "";
		}
		// 확장자가 있는 경우
		else {
			ext = "";
			//ext = "." + originalFilename.substring(lastIndex + 1);
			//ext = originalFilename.substring(lastIndex + 1);
		}

		// 저장할 전체 경로를 포함한 File 객체
		File serverFile = null;

		// 같은 이름의 파일이 있는 경우의 처리
		while (true) {
			//serverFile = new File(uploadPath + "/" + savedFilename + ext);
			serverFile = new File(uploadPath + "/" + savedFilename);
			// 같은 이름의 파일이 없으면 나감.
			if (!serverFile.isFile())
				break;
			if (!useOriginalName) {
				// 같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
				savedFilename = savedFilename + new Date().getTime();
			} else {
				throw new DuplicateFileNameException();
			}
		}

		// 파일 저장
		FileOutputStream fos = null;
		try {
			// mfile.transferTo(serverFile);
			fos = new FileOutputStream(serverFile);

			fos.write(mfile.getBytes());
		} catch (Exception e) {
			savedFilename = null;
			ext = null;
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		logger.info("saveFile("+mfile+", "+uploadPath+", "+useOriginalName+") End");
		return savedFilename + ext;
	}
	
	/**
	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴
	 * 
	 * @param mfile
	 *            업로드된 파일
	 * @param path
	 *            저장할 경로
	 * @param useOriginalName 중복이여도 파일 이름 안 바꿀것인가?(무조건 원래 파일 명 사용할 것인가?)
	 * @param useResize 이미지 파일일 경우 크기 제한 할 것인가?
	 * @return 저장된 파일명
	 * @throws DuplicateFileNameException
	 *             원래 이름을 사용할 수 없을 때 발생
	 */
	public static String saveFile(MultipartFile mfile, String uploadPath, boolean useOriginalName, boolean useResize) {
		// 업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			return null;
		}
		
		// 저장 폴더가 없으면 생성
		File path = new File(uploadPath);
		if (!path.isDirectory()) {
			path.mkdirs();
		}
		
		// 원본 파일명
		String originalFilename = mfile.getOriginalFilename();

		String savedFilename = originalFilename;
		if (!useOriginalName) {
			// 저장할 파일명을 오늘 날짜의 년월일로 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			savedFilename = sdf.format(new Date());
		}

		// 원본 파일의 확장자
		String ext;
		int lastIndex = originalFilename.lastIndexOf('.');
		logger.info("fileName : " + originalFilename + ", lastIndex : " + lastIndex);
		
		// 확장자가 없는 경우
		if (lastIndex == -1) {
			ext = "";
		}
		// 확장자가 있는 경우
		else {
			//ext = "";
			//ext = "." + originalFilename.substring(lastIndex + 1);
			ext = originalFilename.substring(lastIndex + 1);
		}

		// 저장할 전체 경로를 포함한 File 객체
		File serverFile = null;

		// 같은 이름의 파일이 있는 경우의 처리
		while (true) {
			//serverFile = new File(uploadPath + "/" + savedFilename + ext);
			serverFile = new File(uploadPath + "/" + savedFilename);
			// 같은 이름의 파일이 없으면 나감.
			if (!serverFile.isFile())
				break;
			if (!useOriginalName) {
				// 같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
				savedFilename = savedFilename + new Date().getTime();
			} else {
				throw new DuplicateFileNameException();
			}
		}

		// 파일 저장
		FileOutputStream fos = null;
		try {
			// mfile.transferTo(serverFile);
			fos = new FileOutputStream(serverFile);

			fos.write(mfile.getBytes());
			
			if ( useResize ) {
				// 파일이 그림 파일 인 경우, 2배로 조정
				switch( ext ){
				case "png":
				case "jpg":
				case "bmp":
			        File input = serverFile;
			        BufferedImage image = ImageIO.read(input);

			        BufferedImage resized = resize(image);

			        File output = serverFile;
			        ImageIO.write(resized, ext, output);
					break;
				}
			}
		} catch (Exception e) {
			savedFilename = null;
			ext = null;
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return savedFilename + ext;
	}

	/**
	 * 서버에 저장된 파일의 전체 경로를 전달받아, 해당 파일을 삭제
	 * 
	 * @param fullpath
	 *            삭제할 파일의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteFile(String fullpath) {
		logger.info("deleteFile("+fullpath+") Start");
		// 파일 삭제 여부를 리턴할 변수
		boolean result = false;

		// 전달된 전체 경로로 File객체 생성
		File delFile = new File(fullpath);

		// 해당 파일이 존재하면 삭제
		if (delFile.isFile()) {
			delFile.delete();
			result = true;
		}

		logger.info("deleteFile("+fullpath+") End");
		return result;
	}

	/**
	 * 서버에 저장된 디렉토리의 전체 경로를 전달받아, 해당 디렉토리를 삭제
	 * 
	 * @param fullpath
	 *            삭제할 디렉토리의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteDirectory(String fullpath) {
		// 파일 삭제 여부를 리턴할 변수
		boolean result = false;

		File file = new File(fullpath);
		// 폴더내 파일을 배열로 가져온다.
		File[] tempFile = file.listFiles();
		if(tempFile == null) return true;

		if ( tempFile.length > 0) {

			for (int i = 0; i < tempFile.length; i++) {

				if (tempFile[i].isFile()) {
					if (!tempFile[i].delete()) {
						logger.info("file(" + tempFile[i].exists() + ") delete fail : " + tempFile[i]);
					}
				} else {
					// 재귀함수
					deleteDirectory(tempFile[i].getPath());
				}
				tempFile[i].delete();
			}
			result = file.delete();
		}

		return result;
	}

	public static File[] getFilesInDirectory(String fullpath) {
		File file = new File(fullpath);

		// 폴더내 파일을 배열로 가져온다.
		File[] tempFile = file.listFiles();

		return tempFile;

	}

	public static void writeFile(String fullPath, OutputStream os) {
		File file = new File(fullPath);
		if (!file.exists()) {
			logger.warn(fullPath+"가 없습니다.");
			return;
		}
		FileInputStream is = null;
		try {
			// get your file as InputStream
			is = new FileInputStream(file);
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, os);
			os.flush();
		} catch (IOException ex) {
			logger.info("Error writing file to output stream. Filename was '{}'", fullPath, ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static BufferedImage resize(BufferedImage img) {
		int height = 1;
		for ( int i = img.getHeight(); i >= 2; i/=2 ) {
			height *= 2;
		}
		int width = 1;
		for ( int i = img.getWidth(); i >= 2; i/=2 ) {
			width *= 2;
		}
		// 최대 사이즈 제한
		while( height > 512 || width > 512 ) {
			height /= 2;
			width /= 2;
		}
		//logger.debug("그림 파일("+img.getWidth()+"x"+img.getHeight()+") Resize : " + width + "x" + height);
		if ( height == img.getHeight() && width == img.getWidth() ) return img;
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}
