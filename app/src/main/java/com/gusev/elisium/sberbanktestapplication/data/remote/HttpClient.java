package com.gusev.elisium.sberbanktestapplication.data.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.gusev.elisium.sberbanktestapplication.data.model.BaseXmlModel;
import com.gusev.elisium.sberbanktestapplication.data.model.Hab;
import com.gusev.elisium.sberbanktestapplication.tools.SrcStr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by elisiumGusev
 *
 * @Date 21/07/2017
 * @Autor Andrei Gusev
 */
public class HttpClient {

    public static byte[] getUrlBytes(@NonNull String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public static String getUrlString(@NonNull String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public static List<BaseXmlModel> getItems() {
        List<BaseXmlModel> result = null;
        try {
            String url = Uri.parse(SrcStr.BASE_URL)
                    .buildUpon()
                    .build().toString();

            String jsonString = getUrlString(url);

            result = XmlParser.parse(jsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
