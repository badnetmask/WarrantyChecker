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

package com.wordpress.mteixeira.warrantychecker.dell;

/**
 * Created by mteixeir on 7/19/13.
 */
public class GetAssetWarrantyResult {
    public String a;
    public String i;
    public Object Faults;
    public Response Response;

    public void setA(String a) {
        this.a = a;
    }

    public String getA() {
        return a;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getI() {
        return i;
    }

    public void setFaults(Object faults) {
        this.Faults = faults;
    }

    public Object getFaults() {
        return Faults;
    }

    public void setResponse(Response response) {
        this.Response = response;
    }

    public Response getResponse() {
        return Response;
    }
}