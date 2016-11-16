import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
 
import javax.imageio.ImageIO;
 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
 
 
public class qrs {
	  
	public static void main(String[] args) {
		
		 String[] dataList = {"www.chamath.tk","www.surfedge.lk"
		 };
		 
		 String[] nameList = {"ChamathP",
				 "SurfEdge"};
		
		String folder ="QR"; // make sure the folder is already available 
		
		runAndGenerate(dataList, nameList, folder);
		
	}
	
	public static void runAndGenerate(String[] dataList,String[] nameList,String folder){
		
		for (int i = 0; i < nameList.length; i++) {
			generateQR(dataList[i], folder+"/"+nameList[i]+"_"+dataList[i]+".png");
		}
	}
	
	public static void generateQR(String data, String filePath){
		int size = 512;
		String fileType = "png";
		File myFile = new File(filePath);
		try {
			
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
 
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size,
					size, hintMap);
			int width = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(width, width,
					BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
 
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, width, width);
			graphics.setColor(Color.BLACK);
 
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < width; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, myFile);
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\nQR For ."+data+" Generated");
	}
}