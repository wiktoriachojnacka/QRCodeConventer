# **QR Code Generator - Java Application**

This Java application allows you to easily convert URLs into QR codes, providing a user-friendly interface to generate and save the QR images.

![image](https://github.com/user-attachments/assets/4a68b60d-295e-4da9-9254-b9776241e79b)



---

## **Features**  
- **Generate QR code**: Enter a valid URL and click the **"Generate QR"** button to create a QR code representation.  
- **Save QR code**: Specify a file name and location using the **"Save QR"** button to save the generated QR image.  

---

## **Requirements**

To run this application, you'll need the following:  

- **Java Development Kit (JDK)**:  
  Download and install the latest version of JDK from the official Oracle website:  
  [Oracle Java SE Downloads](https://www.oracle.com/java/technologies/javase/downloads.html)  

- **ZXing QR Code Library**:  
  This project utilizes the ZXing library to handle QR code generation. You can download the appropriate JAR file from the ZXing website:  
  [ZXing Library](https://mvnrepository.com/artifact/com.google.zxing)

---

## **Installation**

1. **Download the ZXing JAR file** (e.g., `zxing-core-3.X.X.jar`). Version in project is 3.3.3.  
2. Place the JAR file in the same directory as your Java source files (`QRCodeDisplay.java`, `QRCodeGenerator.java`, and `Main.java`).  

---

## **Running the application**

1. **Open a terminal window** and navigate to the directory containing your Java source files and the ZXing JAR file.  
   
2. **Compile the source code** using a Java compiler like `javac` (ensure you have the JDK installed):

    ```bash
    javac -cp zxing-core-3.X.X.jar *.java
    ```

    Replace `zxing-core-3.X.X.jar` with the actual filename you downloaded.  

3. **Execute the application** using the Java runtime environment (`java`):  

    ```bash
    java -cp zxing-core-3.X.X.jar:. org.wiktoria.Main
    ```

---

## **Usage**

The application will launch a window with a central area for displaying the generated QR code.  
- **Enter a URL** in the text field and click **"Generate QR"** to create a QR code for that URL.  
- You can then use the **"Save QR"** button to save the generated image to your desired location.


