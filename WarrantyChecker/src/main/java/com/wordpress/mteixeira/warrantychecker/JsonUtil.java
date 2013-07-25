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

/*
   Based on https://github.com/rodrigovidalxc/ticketRestauranteBase/blob/master/src/br/com/fbs/json/JsonUtil.java
 */

package com.wordpress.mteixeira.warrantychecker;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

/**
 * Created by mteixeir on 7/20/13.
 */
public class JsonUtil {

    private static Gson gson;

    static {
        // both setFieldNamingStrategy and setDateFormat have been prepared for Dell
        // double check when adding more checks
        gson = new GsonBuilder()
                .setFieldNamingStrategy(new FieldNamingStrategy() {
                    @Override
                    public String translateName(Field field) {
                        // some fields in the Dell web site start with @, which is not a valid variable name
                        String fieldName =
                                FieldNamingPolicy.UPPER_CAMEL_CASE.translateName(field);
                        if (fieldName.startsWith("@"))
                        {
                            fieldName = fieldName.substring(1);
                        }
                        return fieldName;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setPrettyPrinting()
                .create();
    }

    public static Gson getGson() {
        return gson;
    }
}