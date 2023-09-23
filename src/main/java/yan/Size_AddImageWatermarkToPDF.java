package yan;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

public class Size_AddImageWatermarkToPDF {
    public static void main(String[] args) {
        String srcPdfFile = "D:\\TTTT\\Test.pdf";
        String destPdfFile = "D:\\TTTT\\PDF_Add_WaterMark.pdf";
        String watermarkImageFile = "D:\\TTTT\\logo.png";

        try {
            // 创建PdfDocument对象
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(srcPdfFile), new PdfWriter(destPdfFile));

            // 获取文档总页数
            int numPages = pdfDoc.getNumberOfPages();

            // 循环为每页添加水印
            for (int i = 1; i <= numPages; i++) {
                // 获取页面
                PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(i));

                // 创建ImageData对象
                com.itextpdf.io.image.ImageData watermarkImageData = ImageDataFactory.create(watermarkImageFile);

                // 创建Image对象
                com.itextpdf.layout.element.Image watermarkImage = new com.itextpdf.layout.element.Image(watermarkImageData);

                // 设置透明度
                watermarkImage.setOpacity(0.5f);

                // 缩放图片以适应页面大小
                float documentWidth = pdfDoc.getDefaultPageSize().getWidth();
                float documentHeight = pdfDoc.getDefaultPageSize().getHeight();
                float imageWidth = watermarkImage.getImageWidth();
                float imageHeight = watermarkImage.getImageHeight();
                float scale = Math.min(documentWidth / imageWidth, documentHeight / imageHeight);
                // 调整缩放比例
                float newScale = scale * 0.4f; // 缩小到原大小的80%
                watermarkImage.scaleToFit(imageWidth * newScale, imageHeight * newScale);

                // 设置水印位置（居中）
                float x = (documentWidth - watermarkImage.getImageScaledWidth()) / 2;
                float y = (documentHeight - watermarkImage.getImageScaledHeight()) / 2;

                // 添加水印图片到页面
                watermarkImage.setFixedPosition(i, x, y);
                new com.itextpdf.layout.Document(pdfDoc).add(watermarkImage);

                // 关闭画布
                canvas.release();
            }

            // 关闭文档
            pdfDoc.close();

            System.out.println("水印添加完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
