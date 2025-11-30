package com.zcs.zcssdkdemo;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import android.util.Base64;
import java.io.ByteArrayOutputStream;

public class APICallTask extends AsyncTask<String, Void, String> {

    private Bitmap image;
    private Integer typeCard;
    private APICallback callback;


    public APICallTask(APICallback callback, Bitmap image, Integer typeCard) {
        this.image = image;
        this.typeCard = typeCard;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String apiUrl = params[0];
            String idThai = params[1];
            String nameThai = params[2].trim();
            String nameEng = params[3].trim();
            String address = params[4].trim();
            String sex = params[5];
            String hbd = params[6];
            String localcode = params[7];

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            String boundary = "-------------" + System.currentTimeMillis();

            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Token", "Ce9fd9212ac64521cfba6108b9d7564ed");
            //Tq3SYF1pwBCnWPVY659PwlOgrcUPfAZ7rido1OOSKNA :: ระบบผู้มาสมัค 14/03/2025
            ///dpg5hCyh6HFuVLGeXoowT8FehbxJHyPQp0gyyrcAFLx :: ระบบสมัคงาน
            //C284addd9b6cad01d5d7395663c4a58b1 :: id Group // ทดสอบระบบ
            //C32ddb01f8eb3a06abe5e33c04e834265 :: id Group // ระบบสมัคงาน PRN
            //C5cf867544b0863b190504dc914db6027 :: id Group // AMS
            //Cc088f09ef2dcd7c81c83a11e0fd37a6d :: id Group // (X)
            //C104853dff02148c222a8acaea5a852ac :: id Group // ห้องเย็นโตดี
            //      "groupId": "Ca208550533f4deeab470e3294fc86a97", // ระบบสมัคงาน PRN - ใหม
            //Ce9fd9212ac64521cfba6108b9d7564ed  :: ::: id Group - KSB -
            //PZjnqW4Ewwnblbvk4Ydx8Ykyuh87ARkKVjKUM7lZOYQ :: จงลี่
            ///C69ddd08c66769fe1a931dcbd960e574e ::: id Group - ไฮโดร เเม็ก
            //Cdff8b8a9f1b9a667060a0cb82b6cbece ::: id Group - ผู้มาติดต่อเดอะวิลเลจ
            //C8639220683165460809535ed908c68b5 ::: id Group - นันทิญา
            //Cfc9c57ed5e1db86453b86b62293a5987 ::: id Group - SM สายสมร
            //Cc04c80d3214561eb20d33de0eefa6bf7 ::: -id Group  - สเตท อิน ดัสทรี"
            //C8d93f4ee4cfd1e6b77e9facc9927b23a ::: -id Group -บริษัท ไทย ออโตโมทีฟ แอนด์ แอพพลิแอนซ์ จำกัด
            //C66ac287c75f557262ddad8ccc5de3c03 :: id Group เตียงวูนไทย
            //C66ac287c75f557262ddad8ccc5de3c03
            //C774780c5272ddb11b149e35ee1c271d7 :: id Group จงลี่
            //C1d67b61fb8b6f45e1c8ba8531f37e7a2 :: id Group  อภิญญา ฟู้ดส์
            //C8d93f4ee4cfd1e6b77e9facc9927b23a",:: id Group บริษัท ไทย ออโตโมทีฟ แอนด์ แอพพลิแอนซ์ จำกัด
            ///"groupId": "C9d9b402c22a1666993b433aa339c83dc",บริษัท ไทย ออโตโมทีฟ แอนด์ แอพพลิแอนซ์ จำกัด (2) - ใหม่
            //"groupId": "C774780c5272ddb11b149e35ee1c271d7", :: จงลี่
            //  "groupId": "C66ac287c75f557262ddad8ccc5de3c03",
            //"groupId": "C75db0c27c6bf88cd107f145b6ffde1f6", //JINDONGFANG AUTOMOTIVE INTERIOR SYSTEMS (THAILAND) CO., LTD.
            //groupId": "C382c268cf29afe711ff291046744546c",nexteer automotive (thailand) limited
            //   "groupId": "C1560905992e46b2dd04b0cf30a6823a8", SLOT- NANKAI COMPANY LIMITED
            //Cc78a3bf4adf8ec0671844251df0cfac0 :: SIW - QR-Code
            //Cc0023e9d67338ba826c56364111d6b8a :: NFC
            // "groupId": "C6a36ca93eb701c32cf0db2fabd3243f1", - SIW ระบบผู้มาติดต่อ
            //C6140ac4ceb689c24f65a09ca4ec8bfb0 - TSN
            // "groupId": "C793e19f1dc8e5f7720b10fd74ca6c939", - NFC - Security
            //        "groupId": "C23c2a8fb75f9f327405212c776d6fe1c", - รอยอล ระยอง
            //  "groupId": "C1a3f5fe9b64fa55e4a3d584fe01eb022", PKD

            connection.setDoOutput(true);
            connection.setDoInput(true);


            StringBuilder formDataBuilder = new StringBuilder();

            if (this.typeCard != 4) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                this.image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String img_b64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                formDataBuilder.append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"id_thai\"\r\n\r\n")
                        .append(idThai).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        
                        .append("Content-Disposition: form-data; name=\"name_thai\"\r\n\r\n")
                        .append(nameThai).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"name_eng\"\r\n\r\n")
                        .append(nameEng).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"address\"\r\n\r\n")
                        .append(address).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"sex\"\r\n\r\n")
                        .append(sex).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"hbd\"\r\n\r\n")
                        .append(hbd).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"typecard\"\r\n\r\n")
                        .append(this.typeCard).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"localcode\"\r\n\r\n")
                        .append(localcode).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"image\"\r\n\r\n")
                        .append(img_b64).append("\r\n")
                        .append("--").append(boundary).append("--\r\n");
            } else {
                formDataBuilder.append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"id_thai\"\r\n\r\n")
                        .append(idThai).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"name_eng\"\r\n\r\n")
                        .append(nameEng).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"typecard\"\r\n\r\n")
                        .append(this.typeCard).append("\r\n")
                        .append("--").append(boundary).append("\r\n")
                        .append("Content-Disposition: form-data; name=\"localcode\"\r\n\r\n")
                        .append(localcode).append("\r\n")
                        .append("--").append(boundary).append("--\r\n");
            }


            byte[] formDataBytes = formDataBuilder.toString().getBytes("UTF-8");

            connection.setRequestProperty("Content-Length", String.valueOf(formDataBytes.length));

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                outputStream.write(formDataBytes);
                outputStream.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                System.out.println("Error Response Code: " + responseCode);
                System.out.println("Error Response Body: " + errorResponse.toString());

                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String responseData) {
        if (responseData != null) {
            callback.onSuccess(responseData);
        } else {
            callback.onFailure();
        }
    }
}


