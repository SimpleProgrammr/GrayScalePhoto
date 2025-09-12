package space.badluck

import java.io.File
import java.util.stream.IntStream
import javax.imageio.ImageIO

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main(args: Array<String>) {
    if(args.isEmpty())
    {
        println("No input image provided")
        return
    }
    if(args[0]=="-h")
    {
        println("For help look to repository :P\n" +
                "Just use pth to your image as parameter.")
        return
    }
    val file = File(args[0])
    if(!file.exists()){
        println("Error 404. File do NOT exist")
        return
    }

val supportedFileExtensions = arrayOf(
    "png", "jpg","tiff","gif","bmp"
    )

    if(!supportedFileExtensions.contains(file.extension)){
        println("Unsupported extension")
        return
    }

    val image = ImageIO.read(file)
    val height = image.height
    val width = image.width

    IntStream.range(0,height).parallel().forEach { y ->
        IntStream.range(0,width).parallel().forEach { x ->
            val pixel = image.getRGB(x,y) and 0x00FFFFFF

            val R = (pixel and 0xff0000)/0xffff
            val G = (pixel and 0x00ff00)/0xff
            val B = (pixel and 0x0000ff)
            val avg = ((R+G+B)/3).toHexString().substring(6)

            val avgColor: Int =  ("FF" + avg + avg + avg).toLong(16).toInt()

            image.setRGB(x,y,avgColor)
        }
    }
    val outputFile = File("${file.path.replace("."+file.extension,"")}-GrayScale.${file.extension}")
    ImageIO.write(image,file.extension,outputFile)

}