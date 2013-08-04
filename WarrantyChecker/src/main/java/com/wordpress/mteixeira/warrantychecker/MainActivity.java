/*
   Copyright 2013 Mauricio Teixeira

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.wordpress.mteixeira.warrantychecker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wordpress.mteixeira.warrantychecker.dell.DellWarranty;
import com.wordpress.mteixeira.warrantychecker.dell.Warranty;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLException;

/*
 *
 * NOTE:
 * FetchWarrantyTask().execute() has been placed inside onActivityResult because this is how
 * the new activity sends data back to the parent calling activity. Is it the best way to do it?
 */

public class MainActivity extends Activity {

    private Button btScanSerial;
    private TextView txManufacturer;
    private TextView txSerialNumber;
    private TextView txMachineType;
    private TextView txWarranty;
    private ProgressBar progressBar;

    private IntentIntegrator scanner;

    /*
     * For whatever URL we want to use (preferably GET), replace the following strings:
     * __MODEL__
     * __SERIAL__
     * (requirements vary depending on vendor)
     */
    private final String LENOVO_URL = "http://support.lenovo.com/templatedata/Web%20Content/JSP/warrantyLookup.jsp??sysMachType=__MODEL__&sysSerial=__SERIAL__";
    private final String IBM_URL = "http://www-947.ibm.com/support/entry/portal/!ut/p/b1/04_SjzQ0szA0MzS2sDDWj9CPykssy0xPLMnMz0vMAfGjzOKN3Z2NvEOcQwJDTb2MDDxD3E3MAv3CjE0cDYEKInErMDAwI06_AQ7gaEBIf3Bqnn64fhQ-ZWBXgBXgscbPIz83VT83KsfNIjggHQAAbDmE/dl4/d5/L2dJQSEvUUt3QS80SmtFL1o2XzNHQzJLVENUUVU1SjIwSVRHNDZRTlYzNEEx/?type=__MODEL__&serial=__SERIAL__";
    private final String DELL_URL = "https://api.dell.com/support/v2/assetinfo/warranty/tags.json?apikey=1adecee8a60444738f280aad1cd87d0e&svctags=__SERIAL__";

    private String warrantyAsString;

    private String laptopManufacturer;
    private String laptopModel;
    private String laptopSerial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btScanSerial = (Button)findViewById(R.id.scanButton);
        btScanSerial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.MODEL.equals("sdk")) {
                    // this is an emulator!
                    // add a barcode representation here to test
                    extractLaptopData("XXXXXXX");
                    new FetchWarrantyTask().execute();
                } else {
                    // this is a real device!
                    // the results of this action will come to us via onActivityResult
                    scanner = new IntentIntegrator(MainActivity.this);
                    scanner.initiateScan();
                }
            }
        });

        txManufacturer = (TextView)findViewById(R.id.textManufacturer);
        txSerialNumber = (TextView)findViewById(R.id.textSerialNumber);
        txMachineType = (TextView)findViewById(R.id.textMachineType);
        txWarranty = (TextView)findViewById(R.id.textWarranty);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                // returning from scanner.initiateScan()
                if(resultCode == RESULT_OK) {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                    if(scanResult != null) {
                        String out = scanResult.getContents();
                        if(out != null) {
                            extractLaptopData(out);
                            new FetchWarrantyTask().execute();
                        } else {
                            // no data found
                        }
                    } else {
                        // no data found
                    }
                } else {
                    // cancelled
                }
        }
    }

    public void extractLaptopData(String barCode) {
        Matcher m;
        // LENOVO
        m = Pattern.compile("^(1S)(\\d{4})(\\w{3})(\\w+)$").matcher(barCode.toUpperCase());
        if(m.find()) {
            laptopManufacturer = "IBM";
            laptopModel = m.group(2);
            laptopSerial = m.group(4);
        } else {
            m = Pattern.compile("^(\\w{7})$").matcher(barCode.toUpperCase());
            if(m.find()) {
                laptopManufacturer = "Dell";
                laptopModel = null;
                laptopSerial = m.group(1);
            } else {
                laptopManufacturer = "NONE";
                laptopSerial = barCode;
            }
        }
        txManufacturer.setText(laptopManufacturer);
        txSerialNumber.setText(laptopSerial);
        txMachineType.setText(laptopModel);
    }

    public String fetchWebData(String url) {
        String webData = null;
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);
            DefaultHttpClient client = new DefaultHttpClient(httpParameters);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            webData = sb.toString();
        } catch (UnsupportedEncodingException e) {
            Log.e("LWC", String.valueOf(e));
        } catch (ClientProtocolException e) {
            Log.e("LWC", String.valueOf(e));
        } catch (SSLException e) {
            // put an error dialog here
            // this happens when the device doesnt have the website CA
            Log.e("LWC", String.valueOf(e));
        } catch (IOException e) {
            Log.e("LWC", String.valueOf(e));
        }
        return webData;
    }

    private class FetchWarrantyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            btScanSerial.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... args) {
            String svcURL;
            String webData;
            if(laptopManufacturer.equals("Lenovo")) {
                svcURL = LENOVO_URL.replace("__MODEL__", laptopModel);
                svcURL = svcURL.replace("__SERIAL__", laptopSerial);
                webData = fetchWebData(svcURL);
                Matcher m = Pattern.compile(".*Expiration date:.*(\\d{4}-\\d{2}-\\d{2}).*").matcher(webData);
                if(m.find()) {
                    warrantyAsString = m.group(1);
                } else {
                    warrantyAsString = "bgtask (" + laptopModel + "/" + laptopSerial + ")";
                }
            } else if(laptopManufacturer.equals("Dell")) {
                svcURL = DELL_URL.replace("__SERIAL__", laptopSerial);
                webData = fetchWebData(svcURL);
                if(webData == null) {
                    // something went terribly wrong...
                } else {
                    Gson gson = JsonUtil.getGson();
                    DellWarranty dell = gson.fromJson(webData, DellWarranty.class);
                    if(dell.getGetAssetWarrantyResponse().getGetAssetWarrantyResult().Faults == null) {
                        laptopModel = dell.getGetAssetWarrantyResponse().getGetAssetWarrantyResult().getResponse().getDellAsset().getMachineDescription().split(",")[0];
                        List<Warranty> warrantyList = dell.getGetAssetWarrantyResponse().getGetAssetWarrantyResult().getResponse().getDellAsset().getWarranties().getWarranty();
                        if(warrantyList.size() == 1) {
                            // if there is only one warranty information (usually, INITIAL)
                            warrantyAsString = warrantyList.get(0).getEndDate().toString();
                        } else {
                            // if there is more than one (usually, EXTENDED), look for the highest
                            Date warranty = warrantyList.get(0).getEndDate();
                            for(Warranty temp : warrantyList) {
                                if(temp.getEndDate().after(warranty)) {
                                    warranty = temp.getEndDate();
                                }
                            }
                            warrantyAsString = new SimpleDateFormat("yyyy-MM-dd").format(warranty);
                        }
                    } else {
                        // FRAGMENTS!
                        //DialogFragment almd = new AlertMessageDialog(dell.getGetAssetWarrantyResponse().getGetAssetWarrantyResult().getFaults().getFaultException().getMessage());
                        //almd.show(getFragmentManager().getFragment().getS);
                    }
                }
            } else if(laptopManufacturer.equals("IBM")) {
                svcURL = IBM_URL.replace("__MODEL__", laptopModel);
                svcURL = svcURL.replace("__SERIAL__", laptopSerial);
                webData = fetchWebData(svcURL);
                Document doc = Jsoup.parse(webData);
                Elements tableRows = doc.select("table.ibm-data-table tr");
                // row with data below "Warranty Status / Expiration date"
                Elements tableDatas = tableRows.get(3).getElementsByTag("td");
                warrantyAsString = tableDatas.get(1).text();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar.setVisibility(View.GONE);
            btScanSerial.setVisibility(View.VISIBLE);
            txMachineType.setText(laptopModel);
            txWarranty.setText(warrantyAsString);
        }
    }
}